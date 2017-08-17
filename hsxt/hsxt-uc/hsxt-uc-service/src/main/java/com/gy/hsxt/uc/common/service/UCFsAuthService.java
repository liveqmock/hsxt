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
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.fs.api.common.IUCFsAuthService;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 为文件系统提供的用户鉴权
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCFsAuthService
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-11-15 上午11:57:25
 * @version V1.0
 */
@Service
public class UCFsAuthService implements IUCFsAuthService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCFsAuthService";

	@Autowired
	CustIdGenerator generator;

	@Autowired
	CommonCacheService commonCacheService;
	/**
	 * 是否登录
	 * 
	 * @param custId
	 *            客户号
	 * @param channelType
	 *            渠道类型
	 * @param loginToken
	 *            登录token
	 * @return
	 * @see com.gy.hsxt.uc.fs.api.common.IUCFsAuthService#isLogin(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isLogin(String custId, String channelType, String loginToken) {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号为空");
		}
		if (StringUtils.isBlank(loginToken)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数已登录Token为空");
		}
		if (StringUtils.isBlank(channelType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数channelType为空");
		}
		String token = loginToken.trim();
		ChannelTypeEnum chanTypeEnum = null;
		if(StringUtils.isNumer(channelType)){
			chanTypeEnum = ChannelTypeEnum.get(Integer
					.parseInt(channelType));
		}else{
			chanTypeEnum = ChannelTypeEnum.get(channelType);
		}
		if(null == chanTypeEnum){
			SystemLog.warn(MOUDLENAME, "isLogin", "渠道类型错误,channelType["+channelType+"],custId["+custId+"],loginToken["+loginToken+"]");
			return false;
		}
		String correntLoginToken = commonCacheService.getLoginTokenCache(chanTypeEnum, custId);
		if (StringUtils.isBlank(correntLoginToken)
				|| !(token.equals(correntLoginToken))) {
			SystemLog.info(MOUDLENAME, "isLogin", "登陆验证失败，客户号：["+custId+"]，渠道类型["+chanTypeEnum.getPerfix()+"],参数登录token["+token+"],缓存登录token["+correntLoginToken+"]");
			return false;
		}
		return true;
	}

}
