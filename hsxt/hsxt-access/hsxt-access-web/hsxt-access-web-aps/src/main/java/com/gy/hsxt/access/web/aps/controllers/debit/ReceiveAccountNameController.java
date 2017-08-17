/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.controllers.debit;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.debit.IReceiveAccountNameService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tempacct.AccountName;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.debit
 * @className     : ReceiveAccountcontroller.java
 * @description   : 收款账户名称管理
 * @author        : chenli
 * @createDate    : 2015-12-18
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("receiveAccountName")
public class ReceiveAccountNameController extends BaseController {
	@Resource
	private IReceiveAccountNameService service;

	/**
	 * 创建 收款账户名称 
	 * @param accountName:收款账户名称 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/create" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope create(AccountName accountName,String custName,String custId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		
		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
				new Object[] {accountName.getReceiveAccountType(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_EMPTY.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_EMPTY.getDesc() },
				new Object[] {accountName.getReceiveAccountType(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_ERROR.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_ERROR.getDesc() },
				new Object[] {accountName.getReceiveAccountName(),
									RespCode.APS_DEBIT_ACCOUNT_NAME_EMPTY.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_NAME_EMPTY.getDesc()},
				new Object[] {accountName.getAbbreviation(),
									RespCode.APS_DEBIT_ACCOUNT_APP_NAME_EMPTY.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_APP_NAME_EMPTY.getDesc() }
					);
			// 设置操作员
			accountName.setCreatedBy(custId);
			accountName.setCreatedName(custName);
			
			//调用服务创建收款账户名称
			service.create(accountName);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 修改 收款账户名称---需求修改该功能废弃
	 * @param accountName:收款账户名称 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/update" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope updateAccount(AccountName accountName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		
		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
				new Object[] {accountName.getReceiveAccountType(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_EMPTY.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_EMPTY.getDesc() },
				new Object[] {accountName.getReceiveAccountType(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_ERROR.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_STATUS_TYPE_ERROR.getDesc() },
				new Object[] {accountName.getReceiveAccountName(),
									RespCode.APS_DEBIT_ACCOUNT_NAME_EMPTY.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_NAME_EMPTY.getDesc() },
				new Object[] {accountName.getAbbreviation(),
									RespCode.APS_DEBIT_ACCOUNT_APP_NAME_EMPTY.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_APP_NAME_EMPTY.getDesc() },
				new Object[] {accountName.getReceiveAccountId(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY.getCode(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY.getDesc() }
					);
			// 设置操作员
			accountName.setUpdatedBy(request.getParameter("custId"));

			//调用服务更新收款账户名称
			service.update(accountName);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取一条收款账户名称
	 * @param receiveAccountId:收款账户名称Id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findById" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findById(String receiveAccountId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		
		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { receiveAccountId,
					RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY.getCode(),
					RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY.getDesc() });
			
			AccountName accountName = service.findById(receiveAccountId);

			hre = new HttpRespEnvelope(accountName);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取 收款账户名称下拉菜单列表---需求修改该功能废弃
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getAccountMenu" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope getAccountMenu(HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			List<AccountName> accounts = service.getByMenu();
			List<Menu> menus = new ArrayList<Menu>();
			for (AccountName account : accounts) {
				menus.add(new Menu(account.getReceiveAccountName(), account
						.getReceiveAccountId()));
			}
			hre = new HttpRespEnvelope(menus);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	@Override
	protected IBaseService<IReceiveAccountNameService> getEntityService() {
		return service;
	}
}
