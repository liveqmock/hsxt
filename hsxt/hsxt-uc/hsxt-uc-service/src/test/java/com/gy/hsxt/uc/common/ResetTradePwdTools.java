/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.common;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsSysOperLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.service.UCAsEmailService;
import com.gy.hsxt.uc.common.service.UCAsLoginService;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.common.service.UCAsTokenService;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService;
import com.gy.hsxt.uc.ent.mapper.EntTaxRateMapper;
import com.gy.hsxt.uc.ent.service.AsEntService;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.util.RedisCache;
import com.gy.hsxt.uc.util.StringEncrypt;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class ResetTradePwdTools {
	@Autowired
	private UCAsPwdService pwdService;

	@Autowired
	UCAsTokenService tokenService;
	@Autowired
	UCAsNoCardHolderService noCardHolderService;
	

	@Autowired
	CommonCacheService commonCacheService;
	
	
	@Autowired
	ILCSBaseDataService baseDataService;
	@Autowired
	OperatorMapper operatorMapper;
	
	@Autowired
	UCAsEmailService emailService;
	
	@Autowired
	AsEntService entService;
	
	@Autowired
	EntTaxRateMapper entTaxRateMapper;
	
	@Autowired
	private RedisCache<Object> cacheUtil;
	
	@Autowired
	UCAsLoginService loginService;
	
	

	public static void main(String[] args) {
		String[] pwdArray = new String[]{"00000000","11111111","22222222","33333333","44444444","55555555","66666666","77777777","88888888","99999999","12345678","11223344","12341234","12348765","44445555","11112222","33334444","222233333","98765432","77778888","88889999","11110000","00001111","66667777","55667788","56785678"};
		
		String key = "7ed570d9876dc18e"; //登陆后token
		String tradeSalt = "16236336";//交易密码盐值  06002110000=14702698

		for(int i=0;i<pwdArray.length;i++){
			String pwdTrade = pwdArray[i];
			String md5Trade = StringEncrypt.string2MD5(pwdTrade);//交易密码
			String traAes = StringEncrypt.encrypt(md5Trade, key);
			String pwdTran = md5Trade + tradeSalt;
			pwdTran = StringEncrypt.sha256(pwdTran);
			System.out.println("明文交易密码[" + pwdTrade + "]");
			System.out.println("MD5交易密码[" + md5Trade + "]");
			System.out.println("AES秘钥（交易密码）[" + key + "]");
			System.out.println("AES交易密码[" + traAes + "]");
			System.out.println("交易密码密码盐值[" + tradeSalt + "]");
			System.out.println("SHA256加密后的交易密码[" + pwdTran + "]");
			System.out.println();
			System.out.println();
		}
	}
	



}
