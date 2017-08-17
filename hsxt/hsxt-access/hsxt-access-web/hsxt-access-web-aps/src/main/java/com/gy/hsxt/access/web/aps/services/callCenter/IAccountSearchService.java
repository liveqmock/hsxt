package com.gy.hsxt.access.web.aps.services.callCenter;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.common.exception.HsException;

import java.util.Map;

/**   
 * 账户余额查询服务接口
 * @category      账户余额查询服务接口
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.callCenter.IAccountSearchService.java
 * @className     IAccountSearchService
 * @description   账户余额查询服务接口
 * @author        leiyt
 * @createDate    2016-1-27 下午6:05:09  
 * @updateUser    leiyt
 * @updateDate    2016-1-27 下午6:05:09
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
public interface IAccountSearchService {
	/**
	 * 账户余额查询接口
	 * @param custId
	 * @param accType 账户类型编号
	 * @return
	 * @throws HsException
	 */
	AccountBalance searchAccNormal(String custId,String accType) throws HsException;


	/**
	 * 查询余额
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 */
	Map<String, AccountBalance> searchAccBalanceByCustIdAndAccType(String custId, String[] accType) throws HsException;

	/**
	 * 企业昨日积分查询
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 */
	AccountEntrySum searchEntIntegralByYesterday(String custId,String accType) throws HsException;

	/**
	 * 消费者今日积分
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 */
	AccountEntrySum searchPerIntegralByToday(String custId,String accType) throws HsException;

	/**
	 * 投资分红明细查询
	 * @param hsResNo   资源号
	 * @param dividendYear  年份
	 * @return
	 */
	CustomPointDividend findInvestDividendInfo(String hsResNo, String dividendYear);
}

	