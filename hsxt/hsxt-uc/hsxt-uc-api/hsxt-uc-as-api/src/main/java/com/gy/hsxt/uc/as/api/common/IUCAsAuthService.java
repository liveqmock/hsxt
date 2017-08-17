/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * 鉴权
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsAuthService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午2:23:49
 * @version V1.0
 */
public interface IUCAsAuthService {
	/**
	 * 鉴权，从页面过来的需要调用该方法进行鉴权
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
	public void consumerAuth(String custId, ChannelTypeEnum channelType,
			UserTypeEnum userType, String randomToken, String loginToken)
			throws HsException;

	/**
	 * 操作员鉴权
	 * @param custId 客户号
	 * @param entResNo 企业资源号
	 * @param channelType 渠道类型
	 * @param randomToken 随机token
	 * @param loginToken 已登录token
	 * @throws HsException
	 */
	public void operatorAuth(String custId,String entResNo, ChannelTypeEnum channelType,
			String randomToken, String loginToken) throws HsException;
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
	public void consumerAuth(String custId, ChannelTypeEnum channelType,
			String loginToken) throws HsException;
	
	/**
	 * 操作员鉴权：通过接口调用
	 * @param custId 客户号
	 * @param entResNo 企业资源号
	 * @param channelType 渠道类型
	 * @param loginToken 已登录token
	 * @throws HsException
	 */
	public void operatorAuth(String custId, String entResNo,
			ChannelTypeEnum channelType, String loginToken) throws HsException;
}
