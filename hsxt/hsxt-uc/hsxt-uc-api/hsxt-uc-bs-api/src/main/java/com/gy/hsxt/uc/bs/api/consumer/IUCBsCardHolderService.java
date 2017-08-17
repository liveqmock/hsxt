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


import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.bean.consumer.BsCardHolder;
import com.gy.hsxt.uc.bs.bean.consumer.BsHsCard;

/**
 * 
 * @Package: com.gy.hsxt.uc.bs.api.consumer
 * @ClassName: IUCBsCardHolderServic
 * @Description: 持卡人基本信息管理（BS业务系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午2:23:18
 * @version V1.0
 */
public interface IUCBsCardHolderService {
	
	/**
	 * 批量发卡
	 * @param operCustId	地区平台操作员客户号
	 * @param list			互生卡列表
	 * @param entResNo     隶属托管企业的互生号
	 * @throws HsException
	 */
	public void batchAddCards(String operCustId, List<BsHsCard> list,String entResNo)
			throws HsException;
	
	/**
	 * 批量重发
	 * @param operCustId   地区平台操作员客户号
	 * @param list         互生卡列表
	 * @param entResNo     隶属托管企业的互生号
	 * @throws HsException
	 */
	public void batchResendCards(String operCustId, List<BsHsCard> list,String entResNo)throws HsException;

	/**
	 * 销户
	 * @param perCustId    持卡人客户号
	 * @param operCustId   地区平台操作员客户号
	 * @throws HsException
	 */
	public void closeAccout(String perCustId, String operCustId) throws HsException;
	
	/**
	 * 批量销户
	 * @param operCustId	操作者客户号
	 * @param list	客户号列表
	 * @throws HsException
	 */
    public void batchCloseAccout(String operCustId,List<String> list) throws HsException;

	/**
	 * 持卡人补卡
	 * 
	 * @param operCustId
	 *            地区平台操作员客户号
	 * @param bsHsCard
	 *            补卡信息
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService#remakeCard(java.lang.String,
	 *      com.gy.hsxt.uc.bs.bean.consumer.BsRegHsCard)
	 */
	public void remakeCard(String operCustId, BsHsCard bsHsCard)
			throws HsException;

	
	/**
	 * 根据互生号查询持卡人的信息
	 * @param resNo 互生号
	 * @return	CardHolder(持卡人信息)
	 */
	public BsCardHolder searchCardHolderInfoByResNo(String resNo) throws HsException ;
	
	/**
	 * 根据客户号查询持卡人的信息
	 * @param custId 客户号
	 * @return	CardHolder(持卡人信息)
	 */
	public BsCardHolder searchCardHolderInfoByCustId(String custId) throws HsException ;
	
	/**
	 * 通过互生号查询互生卡的信息
	 * @param resNo	互生号
	 * @return 互生卡信息
	 */
	public BsHsCard searchHsCardInfoByResNo(String resNo) throws HsException ;
	
	/**
	 * 通过客户号查询互生卡信息
	 * @param custId   客户号
	 * @return     互生卡信息
	 */
	public BsHsCard searchHsCardInfoByCustId(String custId) throws HsException ;
	
	/**  通过互生号查询客户号
	 * @param resNo	互生号
	 * @return	持卡人客户号
	 * @throws HsException 
	 */
	public String findCustIdByResNo(String resNo) throws HsException ;
	
	/**
	 * 更新登录IP和时间
	 * @param custId	客户号
	 * @param loginIp	登录IP
	 * @param dateStr		登录日期   格式 yyyy-mm-dd hh:mm:ss
	 */
	public void updateLoginInfo(String custId,String loginIp,String dateStr) throws HsException ;
}
