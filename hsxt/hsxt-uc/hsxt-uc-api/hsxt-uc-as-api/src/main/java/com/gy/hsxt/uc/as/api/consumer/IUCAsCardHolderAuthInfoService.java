/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.api.consumer;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.api.consumer
 * @ClassName: IUCAsCardHolderAuthInfoService
 * @Description: 持卡人证件信息管理（AS接入系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午4:40:47
 * @version V1.0
 */
public interface IUCAsCardHolderAuthInfoService {

	/**
	 * 实名注册
	 * 
	 * @param asRealNameRegInfo
	 *            实名注册信息
	 * @throws HsException
	 */
	public void regByRealName(AsRealNameReg asRealNameRegInfo)	throws HsException;

	/**
	 * 查询实名认证状态
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return 实名认证状态
	 * @throws HsException
	 */
	public String findAuthStatusByCustId(String custId) throws HsException;

	
	/**
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return 实名注册信息
	 * @throws HsException
	 */
	public AsRealNameReg  searchRealNameRegInfo(String custId)
			throws HsException;

	/**
	 * 
	 * @param custId
	 *            持卡人客户号
	 * @return  实名认证信息
	 * @throws HsException
	 */
	public AsRealNameAuth  searchRealNameAuthInfo(String custId)
			throws HsException;
	
	/**
	 * 批量查询持卡人的实名状态
	 * @param list	持卡人客户号列表
	 * @return	持卡人的实名状态	key=持卡人客户号	value=持卡人的实名状态 （实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证）
	 * @throws HsException
	 */
	public Map<String,String> listAuthStatus(List<String> list)throws HsException;
	
}
