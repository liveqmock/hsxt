/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.debit.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.aps.services.debit.ITempDebitSumService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tempacct.IBSTempAcctService;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.DebitQuery;
import com.gy.hsxt.bs.bean.tempacct.DebitSum;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit.imp
 * @className : TempDebitSumService.java
 * @description : 临账统计服务类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("tempDebitSumService")
public class TempDebitSumService extends BaseServiceImpl<TempDebitSumService>
		implements ITempDebitSumService {
	@Autowired
	private IBSTempAcctService BStempAcctService;// 临帐管理 接口类

	/**
	 * 查询所有临账记录分组列表
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
	public PageData<DebitSum> findScrollResult(Map filterMap, Map sortMap,
			Page paramPage) throws HsException {
		DebitQuery debitQuery = new DebitQuery();
		// 收款账户名称ID
		String accountName = (String) filterMap.get("accountName");
		if (!StringUtils.isEmpty(accountName)
				&& !accountName.equals("undefined")) {
			debitQuery.setAccountName(accountName);
		}
		// 付款时间 - 开始时间
		String startDate = (String) filterMap.get("startDate");
		if (!StringUtils.isEmpty(startDate)) {
			debitQuery.setStartDate(startDate);
		}
		// 付款时间 - 结束时间
		String endDate = (String) filterMap.get("endDate");
		if (!StringUtils.isEmpty(endDate)) {
			debitQuery.setEndDate(endDate);
		}
		return BStempAcctService.queryDebitSumListPage(paramPage, debitQuery);
	}

	/**
	 * 根据 收款账户名称查询所有的临账统计
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
	public PageData<Debit> queryDebitCountDetailListPage(Map filterMap,	Map sortMap, Page paramPage) throws HsException {
		DebitQuery date = new DebitQuery();
		date.setStartDate((String) filterMap.get("startDate"));//开始时间
		date.setEndDate((String) filterMap.get("endDate"));//结束时间
		String receiveAccountName = (String) filterMap.get("receiveAccountName");
		List<Debit> debits = BStempAcctService.queryDebitSumDetail(receiveAccountName, date);
		return new PageData<Debit>(debits.size(), debits);
	}

	/**
	 * 根据 收款账户Id查询所有的临账统计
	 * 
	 * @param receiveAccountName
	 *            收款账户名称
	 * 
	 *             无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public List<Debit> exportDebitData(String receiveAccountName, String startDate, String endDate) throws HsException {
		DebitQuery date = new DebitQuery();
		date.setStartDate(startDate);//开始时间
		date.setEndDate(endDate);//结束时间
		return BStempAcctService.queryDebitSumDetail(receiveAccountName, date);
	}
}
