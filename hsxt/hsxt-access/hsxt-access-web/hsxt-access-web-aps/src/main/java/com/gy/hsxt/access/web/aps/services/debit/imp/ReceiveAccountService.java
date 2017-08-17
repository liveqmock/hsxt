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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.aps.services.debit.IReceiveAccountService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tempacct.IBSAccountService;
import com.gy.hsxt.bs.bean.tempacct.AccountOption;
import com.gy.hsxt.bs.bean.tempacct.AccountInfo;
import com.gy.hsxt.bs.bean.tempacct.AccountInfoQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit.imp
 * @className : ReceiveAccountService.java
 * @description : 收款账户服务类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("receiveAccountService")
public class ReceiveAccountService extends
		BaseServiceImpl<ReceiveAccountService> implements
		IReceiveAccountService {
	@Autowired
	private IBSAccountService iBsAcountService;// 临帐帐户信息接口类

	/**
	 * 查询所有收款账户记录
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
	public PageData<AccountInfo> findScrollResult(Map filterMap, Map sortMap,
			Page paramPage) throws HsException {
		AccountInfoQuery accountingQuery = new AccountInfoQuery();
		if (filterMap != null) {
			// 银行ID
			String bankId = (String) filterMap.get("bankId");
			if (!StringUtils.isEmpty(bankId) && !"null".equals(bankId)) {
				accountingQuery.setBankId(bankId);
			}
			// 收款账户名
			String receiveAccountName = (String) filterMap
					.get("receiveAccountName");
			if (!StringUtils.isEmpty(receiveAccountName)) {
				accountingQuery.setReceiveAccountName(receiveAccountName);
			}
			// 收款账号
			String receiveAccountNo = (String) filterMap
					.get("receiveAccountNo");
			if (!StringUtils.isEmpty(receiveAccountNo)) {
				accountingQuery.setReceiveAccountNo(receiveAccountNo);
			}

		}

		return iBsAcountService.queryAccountInfoListForPage(paramPage,
				accountingQuery);
	}

	/**
	 * 新增收款账户
	 * 
	 * @param accountInfo
	 *            收款账户Bean
	 *             
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void createAccounting(AccountInfo accountInfo) throws HsException {
		iBsAcountService.createAccountInfo(accountInfo);
	}

	/**
	 * 根据 Id查询收款账户
	 * 
	 * @param receiveAccountInfoId
	 *            收款账户Id
	 * 
	 *             无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public AccountInfo getAccountingBean(String receiveAccountInfoId)
			throws HsException {
		return iBsAcountService.queryAccountInfoBean(receiveAccountInfoId);
	}

	/**
	 * 更新收款账户
	 * 
	 * @param accountInfo
	 *            收款账户Bean
	 * 
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void updateAccounting(AccountInfo accountInfo) throws HsException {
		iBsAcountService.modifyAccountInfo(accountInfo);

	}

	/**
	 * 查询所有收款账户记录
	 * 
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public List<AccountOption> getAccountingDropdownMenu() throws HsException {
		return iBsAcountService.queryAccountInfoDropdownMenu();
	}
	/**
	 * 禁用收款账户信息
	 * @param receiveAccountInfoId
	 * @throws HsException
	 */
	@Override
	public void forbidAccountInfo(String receiveAccountInfoId) throws HsException{
		iBsAcountService.forbidAccountInfo(receiveAccountInfoId);
	}
}
