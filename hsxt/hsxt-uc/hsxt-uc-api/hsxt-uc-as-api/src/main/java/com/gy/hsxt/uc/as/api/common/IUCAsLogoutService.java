/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/**
 * 
 * 登出接口
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsLogoutService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午2:11:35
 * @version V1.0
 */
public interface IUCAsLogoutService {
	/**
	 * 消费者登出
	 * 
	 * @param channelType
	 *            渠道类型
	 * @param custId
	 *            客户号
	 * 
	 * @throws HsException
	 */
	public void logout(String custId, ChannelTypeEnum channelType,
			UserTypeEnum userType) throws HsException;

	/**
	 * 操作员登出
	 * 
	 * @param channelType
	 *            渠道类型
	 * @param entResNo
	 *            企业资源号
	 * @param custId
	 *            客户号
	 * @throws HsException
	 */
	public void operatorLogout(ChannelTypeEnum channelType, String entResNo,
			String custId) throws HsException;
	
	/**
	 * 根据客户号登出
	 * @param custId 客户号
	 * @param channelType 渠道类型
	 * @throws HsException
	 */
	public void logoutByCustId(String custId, ChannelTypeEnum channelType) throws HsException;
}
