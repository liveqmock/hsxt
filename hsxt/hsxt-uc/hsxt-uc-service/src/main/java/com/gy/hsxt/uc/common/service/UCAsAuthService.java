/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsAuthService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;

/**
 * 用户鉴权类
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsAuthService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-19 下午5:06:12
 * @version V1.0
 */
@Service
public class UCAsAuthService implements IUCAsAuthService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.common.service.IUCAsAuthService";

	@Autowired
	CommonCacheService commonCacheService;

	/**
	 * 通过页面的方式调用，需要验证随机token
	 * 
	 * @param custId
	 *            客户号
	 * @param channelType
	 *            渠道类型
	 * @param randomToken
	 *            随机token
	 * @param loginToken
	 *            已登录token
	 * @return
	 * @throws HsException
	 */
	@Override
	public void consumerAuth(String custId, ChannelTypeEnum channelType,
			UserTypeEnum userType, String randomToken, String loginToken)
			throws HsException {
		if(StringUtils.isBlank(custId)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"custId 不能为null");
		}
		if(null == channelType){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"channelType 不能为null");
		}
		if(StringUtils.isBlank(loginToken)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"loginToken 不能为null");
		}
		if(StringUtils.isBlank(randomToken)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"randomToken 不能为null");
		}
		// 获取登录缓存，验证登录token
		String ceLoginToken = commonCacheService.getLoginTokenCache(
				channelType, custId);
		String funName = "consumerAuth";
		String trimLoginToken = loginToken.trim();
		if (StringUtils.isBlank(ceLoginToken)
				|| !trimLoginToken.equals(ceLoginToken)) {
			String msg = "消费者鉴权失败，登录Token不正确，客户号[" + custId + "],渠道类型[" + channelType.getPerfix()
					+ "],参数登录token[" + loginToken
					+ "],缓存中登录token[" + ceLoginToken + "]";
			SystemLog.debug(MOUDLENAME, funName, msg);
			// 如果登录token不一致
			throw new HsException(ErrorCodeEnum.SESSION_IS_ILLEGAL.getValue(),
					msg);

		}
		// 获取随机缓存，验证随机token
		boolean isExists = commonCacheService.isRandomTokenExists(custId,
				randomToken);
		if (!isExists) {
			String msg = "消费者鉴权失败，随机Token不正确，客户号[" + custId + "],随机token["
					+ randomToken + "]";
//			SystemLog.debug(MOUDLENAME, funName, msg);
			throw new HsException(ErrorCodeEnum.SESSION_IS_ILLEGAL.getValue(),
					msg);
		}
	}

	/**
	 * 
	 * 操作员鉴权
	 * 
	 * @param custId
	 *            客户号
	 * @param entResNo
	 *            企业资源号
	 * @param channelType
	 *            渠道类型
	 * @param randomToken
	 *            随机token
	 * @param loginToken
	 *            已登录token
	 * @throws HsException
	 */
	@Override
	public void operatorAuth(String custId, String entResNo,
			ChannelTypeEnum channelType, String randomToken, String loginToken)
			throws HsException {
		if(StringUtils.isBlank(custId)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"custId 不能为null");
		}
		if(null == channelType){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"channelType 不能为null");
		}
		if(StringUtils.isBlank(loginToken)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"loginToken 不能为null");
		}
		if(StringUtils.isBlank(randomToken)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"randomToken 不能为null");
		}
		
		// 获取登录缓存，验证登录token
		String ceLoginToken = commonCacheService.getLoginTokenCache(
				channelType, custId);
		String msg = null;
		String funName = "operatorAuth";
		String trimLoginToken = loginToken.trim();
		if (StringUtils.isBlank(ceLoginToken)
				|| !trimLoginToken.equals(ceLoginToken)) {
			// 如果登录token不一致
			msg = "操作员鉴权失败，登录Token不正确，客户号[" + custId + "],渠道类型["+channelType.getPerfix()+"],登录token["
					+ loginToken + "], 缓存中token[" + ceLoginToken + "]";
			SystemLog.debug(MOUDLENAME, funName, msg);
			throw new HsException(ErrorCodeEnum.SESSION_IS_ILLEGAL.getValue(),
					msg);
		}
		// 获取随机缓存，验证随机token
		boolean isExists = commonCacheService.isRandomTokenExists(custId,
				randomToken);
		if (!isExists) {
			msg = "操作员鉴权失败，随机Token不正确，客户号[" + custId + "],随机token["
					+ randomToken + "]";
//			SystemLog.debug(MOUDLENAME, funName, msg);
			throw new HsException(ErrorCodeEnum.SESSION_IS_ILLEGAL.getValue(),
					msg);
		}
	}

	/**
	 * 通过接口调用的使用该方法鉴权
	 * 
	 * @param custId
	 *            客户号
	 * @param channelType
	 *            渠道类型
	 * @param loginToken
	 *            已登录token
	 * @return
	 * @throws HsException
	 */
	@Override
	public void consumerAuth(String custId, ChannelTypeEnum channelType,
			String loginToken) throws HsException {
		if(StringUtils.isBlank(custId)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"custId 不能为null");
		}
		if(null == channelType){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"channelType 不能为null");
		}
		if(StringUtils.isBlank(loginToken)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"loginToken 不能为null");
		}
		// 获取登录缓存，验证登录token
		String ceLoginToken = commonCacheService.getLoginTokenCache(
				channelType, custId);
		String trimLoginToken = loginToken.trim();
		if (StringUtils.isBlank(ceLoginToken)
				|| !trimLoginToken.equals(ceLoginToken)) {// 如果登录token不一致
			String funName = "consumerAuth";
			String msg = "消费者鉴权失败，登录Token不正确, custId[" + custId + "], 渠道类型前缀["
					+ channelType.getPerfix() + "]，登录token[" + loginToken
					+ "],缓存中的登录token[" + ceLoginToken + "]";
//			SystemLog.debug(MOUDLENAME, funName, msg);
			throw new HsException(ErrorCodeEnum.SESSION_IS_ILLEGAL.getValue(),
					msg);
		}
	}

	/**
	 * 
	 * @param custId
	 * @param entResNo
	 * @param channelType
	 * @param loginToken
	 * @throws HsException
	 */
	@Override
	public void operatorAuth(String custId, String entResNo,
			ChannelTypeEnum channelType, String loginToken) throws HsException {
		if(StringUtils.isBlank(custId)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"custId 不能为null");
		}
		if(null == channelType){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"channelType 不能为null");
		}
		if(StringUtils.isBlank(loginToken)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),"loginToken 不能为null");
		}
		// 获取登录缓存，验证登录token
		String ceLoginToken = commonCacheService.getLoginTokenCache(
				channelType, custId);
		String trimLoginToken = loginToken.trim();
		if (StringUtils.isBlank(ceLoginToken)
				|| !trimLoginToken.equals(ceLoginToken)) {// 如果登录token不一致
			String funName = "operatorAuth";
			String msg = "操作员鉴权失败，登录Token不正确，custId[" + custId + "]，渠道类型前缀["
					+ channelType.getPerfix() + "], 登录token[" + loginToken
					+ "]，缓存中登录token[" + ceLoginToken + "]";
//			SystemLog.debug(MOUDLENAME, funName, msg);
			throw new HsException(ErrorCodeEnum.SESSION_IS_ILLEGAL.getValue(),
					msg);
		}
	}

}
