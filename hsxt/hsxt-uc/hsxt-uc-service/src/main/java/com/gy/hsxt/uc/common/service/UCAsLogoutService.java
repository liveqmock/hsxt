/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.Constants;
import com.gy.hsxt.uc.as.api.common.IUCAsLogoutService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 登出处理类
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsLogoutService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-14 下午12:16:24
 * @version V1.0
 */
@Service
public class UCAsLogoutService implements IUCAsLogoutService {

	private static final String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsLogoutService";

	@Autowired
	CommonCacheService commonCacheService;
	/**
	 * 持卡人
	 */
	@Autowired
	IUCAsCardHolderService cardHolderService;
	/**
	 * 非持卡人
	 */
	@Autowired
	IUCAsNoCardHolderService noCardHolderService;
	/**
	 * 系统操作员
	 */
	@Autowired
	IUCAsSysOperInfoService sysOperInfoService;

	/**
	 * 登出通用方法，包括持卡人、非持卡人
	 * 
	 * @param userName
	 * @param channelType
	 * @param userType
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsLogoutService#logout(java.lang.String,
	 *      com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	public void logout(String userName, ChannelTypeEnum channelType,
			UserTypeEnum userType) throws HsException {
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数userName为空");
		}
		String custId = "";
		if (UserTypeEnum.CARDER.getType().equals(userType.getType())) {
			custId = commonCacheService.findCustIdByResNo(userName);
			CustIdGenerator.isCarderExist(custId, userName);
		} else if (UserTypeEnum.NO_CARDER.getType().equals(userType.getType())) {
			custId = commonCacheService.findCustIdByMobile(userName);
			CustIdGenerator.isNoCarderExist(custId, userName);
		} else if (UserTypeEnum.ENT.getType().equals(userType.getType())) {
			custId = sysOperInfoService.findCustIdByUserName(userName);
		}
		commonCacheService.removeLoginTokenCache(channelType, custId);// 删除登录缓存
		String methodName = "logout(包括持卡人、非持卡人登出)";
		StringBuffer columns = new StringBuffer();
		columns.append("userName").append(Constants.VERTICAL)
				.append("channelType").append(Constants.VERTICAL)
				.append("userType");
		StringBuffer contents = new StringBuffer();
		String channel = String.valueOf(channelType.getType());
		contents.append(userName).append(Constants.VERTICAL).append(channel)
				.append(Constants.VERTICAL).append(userType.getType());
		BizLog.biz(MOUDLENAME, methodName, columns.toString(),
				contents.toString());
	}

	/**
	 * 登出通用方法，包括持卡人、非持卡人
	 * 
	 * @param custId
	 *            登出客户号
	 * @param channelType
	 * @param userType
	 * @throws HsException
	 */
	public void logoutByCustId(String custId, ChannelTypeEnum channelType)
			throws HsException {
		commonCacheService.removeLoginTokenCache(channelType, custId);// 删除登录缓存
		String methodName = "logoutByCustId(包括持卡人、非持卡人登出)";
		StringBuffer columns = new StringBuffer();
		columns.append("custId").append(Constants.VERTICAL)
				.append("channelType");
		StringBuffer contents = new StringBuffer();
		String channel = String.valueOf(channelType.getType());
		contents.append(custId).append(Constants.VERTICAL).append(channel);
		BizLog.biz(MOUDLENAME, methodName, columns.toString(),
				contents.toString());
	}

	/**
	 * 操作员登出方法
	 * 
	 * @param channelType
	 *            渠道类型
	 * @param entResNo
	 *            企业资源号，该值可为空
	 * @param custId
	 *            客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsLogoutService#operatorLogout(com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum,
	 *      java.lang.String, java.lang.String)
	 */
	public void operatorLogout(ChannelTypeEnum channelType, String entResNo,
			String custId) throws HsException {
		commonCacheService.removeLoginTokenCache(channelType, custId);// 删除登录缓存
		String methodName = "operatorLogout(操作员登出)";
		StringBuffer columns = new StringBuffer();
		columns.append("entResNo").append(Constants.VERTICAL).append("custId")
				.append(Constants.VERTICAL).append("channelType");
		StringBuffer contents = new StringBuffer();
		String channel = String.valueOf(channelType.getType());
		contents.append(entResNo).append(Constants.VERTICAL).append(custId)
				.append(Constants.VERTICAL).append(channel);
		BizLog.biz(MOUDLENAME, methodName, columns.toString(),
				contents.toString());
	}
}
