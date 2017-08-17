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

import java.util.Map;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;

/**
 * 
 * @Package: com.gy.hsxt.uc.bs.api.consumer
 * @ClassName: IUCBsCardHolderAuthInfoService
 * @Description: 持卡人证件信息管理（BS系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午3:45:21
 * @version V1.0
 */
public interface IUCBsNoCardHolderAuthInfoService {
	/**
	 * 实名认证
	 * @param bsRealNameAuthInfo
	 *            实名认证bean
	 * @param operCustId
	 *            操作员客户号
	 * @return   key="resNo" 互生号  key = "isRealNameAuth" 实名认证状态
	 * @throws HsException
	 */
	public Map<String,String> authByRealName(BsRealNameAuth bsRealNameAuthInfo,
			String operCustId) throws HsException;
	/**
	 * 查询实名认证信息
	 * @param custId
	 *            持卡人客户号
	 * @return 实名认证信息
	 * @throws HsException
	 */
	public BsRealNameAuth  searchRealNameAuthInfo(String custId)
			throws HsException;

	/**
	 * 修改实名认证信息同时更改实名状态为已实名认证
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	public void updateRealNameAuthInfo(BsRealNameAuth bsRealNameAuthInfo,
			String operCustId) throws HsException;
	
	/** 通过客户号查询持卡人实名认证状态
	 * @param custId	客户号
	 * @return	String  实名认证状态
	 * @throws HsException 
	 */
	public String findAuthStatusByCustId(String perCustId) throws HsException;
	
	/**
	 * 修改实名认证信息同时更改字段是否重要信息变更申请期间标识为"否"
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 */
	public void updateMainInfoApplyInfo(BsRealNameAuth bsRealNameAuthInfo,
			String operCustId) throws HsException;
	
	/**
	 * 检查证件是否已被他人使用
	 * @param perCustId 消费者客户号
	 * @param idType 证件类型1：身份证 2：护照 3：营业执照
	 * @param idNo 证件号码
	 * @return 证件已被他人实名注册则返回true
	 */
	public boolean isIdNoExist(String perCustId,Integer idType,String idNo);
	
}
