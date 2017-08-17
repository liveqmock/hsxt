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
import com.gy.hsxt.access.web.aps.services.debit.IReceiveAccountNameService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tempacct.IBSAccountService;
import com.gy.hsxt.bs.bean.tempacct.AccountName;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit.imp
 * @className : ReceiveAccountNameService.java
 * @description :收款账户名称服务类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("receiveAccountNameService")
public class ReceiveAccountNameService extends
		BaseServiceImpl<ReceiveAccountNameService> implements
		IReceiveAccountNameService {
	@Autowired
	private IBSAccountService iBsAcountService;// 临帐帐户信息接口类

	/**
	 * 查询所有收款账户名称记录
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
	public PageData<AccountName> findScrollResult(Map paramMap1, Map paramMap2,
			Page paramPage) throws HsException {
		return iBsAcountService.queryAccountNameListForPage(paramPage, null);
	}

	/**
	 * 新增收款账户名称
	 * 
	 * @param accountName
	 *            收款账户名称
	 *            
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void create(AccountName accountName) {

		iBsAcountService.createAccountName(accountName);
	}

	/**
	 * 根据 Id查询收款账户名称
	 * 
	 * @param receiveAccountId
	 *            收款账户名称Id
	 * 
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public AccountName findById(String receiveAccountId) throws HsException {
		return iBsAcountService.queryAccountNameDetail(receiveAccountId);
	}

	/**
	 * 修改收款账户名称
	 * 
	 * @param accountName
	 *            收款账户名称Bean
	 * 
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void update(AccountName accountName) throws HsException {
		iBsAcountService.modifyAccountName(accountName);
	}

	/**
	 * 查询收款账户名称
	 * 
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public List<AccountName> getByMenu() throws HsException {
		return iBsAcountService.queryAccountNameList(null);
	}
}
