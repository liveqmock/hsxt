/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.debit.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.aps.services.debit.ITempAcctService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tempacct.IBSTempAcctService;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.DebitQuery;
import com.gy.hsxt.bs.common.enumtype.tempacct.DebitStatus;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit.imp
 * @className : TempAcctService.java
 * @description : 临账服务类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("tempAcctService")
public class TempAcctService extends BaseServiceImpl<TempAcctService> implements
		ITempAcctService {
	@Autowired
	private IBSTempAcctService BStempAcctService;// 临帐管理 接口类

	/**
	 * 查询所有临账记录
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public PageData<Debit> findScrollResult(Map filterMap, Map sortMap,
			Page paramPage) throws HsException {
		DebitQuery debitQuery = new DebitQuery();

		// 临账状态
		String debitStatus = (String) filterMap.get("debitStatus");
		if (!StringUtils.isEmpty(debitStatus)
				&& !debitStatus.equals("undefined")) {
			debitQuery.setDebitStatus(Integer.parseInt(debitStatus));
		}
		// 收款账户名称
		String accountName = (String) filterMap.get("accountName");
		if (!StringUtils.isEmpty(accountName)
				&& !accountName.equals("undefined")) {
			debitQuery.setAccountName(accountName);
		}
		// 收款账户信息ID
		String accountInfoId = (String) filterMap.get("accountInfoId");
		if (!StringUtils.isEmpty(accountInfoId)
				&& !accountInfoId.equals("undefined")) {
			debitQuery.setAccountInfoId(accountInfoId);
		}
		// 付款时间 - 开始时间
		String payStartDate = (String) filterMap.get("startTime");
		if (!StringUtils.isEmpty(payStartDate)) {
			debitQuery.setStartDate(payStartDate);
		}
		// 付款时间 - 结束时间
		String payEndDate = (String) filterMap.get("endTime");
		if (!StringUtils.isEmpty(payEndDate)) {
			debitQuery.setEndDate(payEndDate);
		}
		// 付款企业名称
		String payEntCustName = (String) filterMap.get("payEntCustName");
		if (!StringUtils.isEmpty(payEntCustName)) {
			debitQuery.setPayEntCustName(payEntCustName);
		}

		// 拟用企业名称
		String useEntCustName = (String) filterMap.get("useEntCustName");
		if (!StringUtils.isEmpty(useEntCustName)) {
			debitQuery.setUseEntCustName(useEntCustName);
		}
		return BStempAcctService.queryDebitListPage(paramPage, debitQuery);
	}

	/**
	 * 分页查询临账录入复核/退款复核列表
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public PageData<Debit> queryDebitTaskListPage(Map filterMap, Map sortMap,
			Page paramPage) throws HsException {
		DebitQuery debitQuery = new DebitQuery();

		// 临账状态
		String debitStatus = (String) filterMap.get("debitStatus");
		if (!StringUtils.isEmpty(debitStatus)
				&& !debitStatus.equals("undefined")) {
			debitQuery.setDebitStatus(Integer.parseInt(debitStatus));
		}
		// 收款账户名称ID
		String accountName = (String) filterMap.get("accountName");
		if (!StringUtils.isEmpty(accountName)
				&& !accountName.equals("undefined")) {
			debitQuery.setAccountName(accountName);
		}
		// 收款账户信息ID
		String accountInfoId = (String) filterMap.get("accountInfoId");
		if (!StringUtils.isEmpty(accountInfoId)
				&& !accountInfoId.equals("undefined")) {
			debitQuery.setAccountInfoId(accountInfoId);
		}
		// 付款时间 - 开始时间
		String payStartDate = (String) filterMap.get("payStartDate");
		if (!StringUtils.isEmpty(payStartDate)) {
			debitQuery.setStartDate(payStartDate);
		}
		// 付款时间 - 结束时间
		String payEndDate = (String) filterMap.get("payEndDate");
		if (!StringUtils.isEmpty(payEndDate)) {
			debitQuery.setEndDate(payEndDate);
		}
		// 付款企业名称
		String payEntCustName = (String) filterMap.get("payEntCustName");
		if (!StringUtils.isEmpty(payEntCustName)) {
			debitQuery.setPayEntCustName(payEntCustName);
		}

		// 拟用企业名称
		String useEntCustName = (String) filterMap.get("useEntCustName");
		if (!StringUtils.isEmpty(useEntCustName)) {
			debitQuery.setUseEntCustName(useEntCustName);
		}
		// 操作员客户号
		String custId = (String) filterMap.get("custId");
		if (!StringUtils.isEmpty(custId)) {
			debitQuery.setOptCustId(custId);
		}
		return BStempAcctService.queryDebitTaskListPage(paramPage, debitQuery);
	}

	/**
	 * 查询所有未付款的订单记录
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void createDebit(Debit debit) throws HsException {
		BStempAcctService.createDebit(debit);
	}

	/**
	 * 查询所有未付款的订单记录
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void updateDebit(Debit debit) throws HsException {
		BStempAcctService.modifyDebit(debit);

	}

	/**
	 * 查询所有未付款的订单记录
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public Debit findbyId(String debitId) throws HsException {

		return BStempAcctService.queryDebitDetail(debitId);
	}

	/**
	 * 查询所有未付款的订单记录
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void approveDebit(Debit debit) throws HsException {
		BStempAcctService.approveDebit(debit);
	}

	/**
	 * 查询所有未付款的订单记录
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void turnNotMovePayment(Debit debit, String dblOptCustId)
			throws HsException {
		BStempAcctService.turnNotMovePayment(debit, dblOptCustId);
	}

	/**
	 * 查询所有未付款的订单记录
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public String notMovePaymentSum() throws HsException {
		return BStempAcctService.notMovePaymentSum();
	}

	/**
	 * 根据拟使用企业互生号查询待关联临账记录
	 * 
	 * @param useEntCustName
	 *            拟使用企业互生号
	 * @throws HsException
	 */
	@Override
	public PageData<Debit> queryDebitListByQuery(Map filterMap, Map sortMap,
			Page paramPage)
			throws HsException {
		DebitQuery debitQuery = new DebitQuery();
		String useEntCustName=(String) filterMap.get("useEntCustName");
		// 临账状态
		debitQuery.setDebitStatus(DebitStatus.LINKABLE.ordinal());
		if (!StringUtils.isEmpty(useEntCustName)) {
			debitQuery.setUseEntCustName(useEntCustName);
		}
		List<Debit> debitList = BStempAcctService.queryDebitListByQuery(debitQuery);
		return new PageData(debitList.size(),debitList);
	}
}
