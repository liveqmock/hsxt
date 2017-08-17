/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/**
 * 预留信息
 * 
 * @Package: com.gy.hsxt.uc.as.api.common  
 * @ClassName: IUCAsReserveInfoService 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-25 下午12:14:54 
 * @version V1.0
 */
public interface IUCAsReserveInfoService {
	/**
	 * 通过用户客户号查询用户的预留信息
	 * 
	 * @param custId(包含企业客户号、持卡人客户号、非持卡人客户号)
	 *            客户号（包含持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型 非持卡人 1、持卡人2、企业3
	 * @return 预留信息
	 * @throws HsException
	 */
	public String findReserveInfoByCustId(String custId, String userType)
			throws HsException;

	/**
	 * 设置预留信息
	 * 
	 * @param custId(包含企业客户号、持卡人客户号、非持卡人客户号)
	 *            客户号（包含持卡人、非持卡人、企业）
	 * @param reserveInfo
	 *            预留信息
	 * @param userType 非持卡人 1、持卡人2、企业3
	 *            用户类型
	 * @throws HsException
	 */
	public void setReserveInfo(String custId, String reserveInfo,
			String userType) throws HsException;


}
