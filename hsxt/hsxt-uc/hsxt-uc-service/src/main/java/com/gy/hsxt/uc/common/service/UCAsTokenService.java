/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * token操作类，主要用于生成token、验证token等操作
 * 
 * @Package: com.gy.hsxt.uc.common.service  
 * @ClassName: UCAsTokenService 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-10-14 上午10:34:28 
 * @version V1.0
 */
@Service
public class UCAsTokenService implements IUCAsTokenService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsTokenService";

	@Autowired
	CommonCacheService commonCacheService;
	/**
	 * 验证登录token
	 * 
	 * @param channelType
	 *            渠道类型
	 * @param custId
	 *            客户号
	 * @param loginToken
	 *            登录token
	 * @return
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsTokenService#checkLoginToken(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public boolean checkLoginToken(String channelType, String custId,
			String loginToken) {
		ChannelTypeEnum type = ChannelTypeEnum
				.get(Integer.valueOf(channelType));
		String token = commonCacheService.getLoginTokenCache(type, custId);
		if (loginToken.equals(token)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成随机token
	 * @param custId 客户号 
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsTokenService#getRandomToken()
	 */
	@Override
	public String getRandomToken(String custId) throws HsException {
		//生成8位随机数  , 使用sha256,取前16位
//		String token =genRandomToken();
		//16位唯一数字
		String token =genNumToken();
		// 存入缓存
		commonCacheService.addRandomTokenCache(custId, token);
		return token;
	}

	/**
	 * 验证随机token
	 * 
	 * @param custId 客户号
	 * @param token 随机token
	 * @return
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsTokenService#checkRandomToken(java.lang.String)
	 */
	@Override
	public boolean checkRandomToken(String custId, String token) {
		if (StringUtils.isNotBlank(custId)) {
			return commonCacheService.isRandomTokenExists(custId, token);
		} else {
			if (StringUtils.isBlank(token)) {
				return false;
			}
			return commonCacheService.isRandomTokenExists(custId, token);
			
		}
	}
	
	private static String genRandomToken(){
		// 生成8位随机数  , 使用sha256,取前16位
		String token = StringEncrypt.sha256(CSPRNG.generateRandom(SysConfig.getCsprLen()))
				.substring(0, 16);
		return token;
	}
	/**
	 * 生成16位数字token
	 * @return
	 */
	private static String genNumToken(){
//		String id=RandomGuidAgent.getGuid("token", "", SysConfig.getSystemInstanceNo()); //自增
		String id=RandomGuidAgent.getStringGuid( SysConfig.getSystemInstanceNo());  //不自增,系统编号不宜超过10
		int len=id.length();
		if(len<16){
			id="0000000000000000"+id;
			len=id.length();
		}
		//返回最后16位
		return id.substring(len-16);
	}
	
	public static void main(String[] args ){
		int total=100000;
		HashSet<String> set= new HashSet<>(total);
		int i=0;
		String token=null;
		for( i=0;i<total;i++){
			token =genNumToken();//genRandomToken();
			set.add(token);
		}
		System.out.println(token+",total="+total+",fact="+set.size());
		
		
	}
}
