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

package com.gy.hsxt.uc.bs.api.consumer;



import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.bean.consumer.BsNoCardHolder;

/**
 * 
 * @Package: com.gy.hsxt.uc.bs.api.consumer
 * @ClassName: IUCBsNoCardHolderService
 * @Description: 非持卡人基本信息管理(BS业务系统调用)
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午3:56:39
 * @version V1.0
 */
public interface IUCBsNoCardHolderService { 
	/**
	 * 非持卡人注销
	 * 
	 * @param perCustId
	 *            持卡人客户号
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	public void closeAccout(String perCustId, String operCustId)
			throws HsException;
	/**
	 * 通过手机号码查询非持卡人的信息
	 * @param mobile  手机号码
	 * @return 非持卡人信息
	 */
	public BsNoCardHolder searchNoCardHolderInfoByMobile(String mobile);
	/**
	 * 通过手机号码查询客户号
	 * @param mobile	手机号码
	 * @return 客户号
	 */
	public String findCustIdByMobile(String mobile);
	
	/**
	 * 更新登录IP和时间
	 * @param custId	客户号
	 * @param loginIp	登录IP
	 * @param dateStr		登录日期   格式 yyyy-mm-dd hh:mm:ss
	 */
	public void updateLoginInfo(String custId,String loginIp,String dateStr);
}
