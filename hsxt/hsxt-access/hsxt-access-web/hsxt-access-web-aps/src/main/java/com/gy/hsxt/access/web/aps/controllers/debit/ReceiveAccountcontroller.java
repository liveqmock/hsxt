package com.gy.hsxt.access.web.aps.controllers.debit;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.debit.IReceiveAccountService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tempacct.AccountOption;
import com.gy.hsxt.bs.bean.tempacct.AccountInfo;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.debit
 * @className : ReceiveAccountcontroller.java
 * @description : 收款账户管理
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("receiveAccount")
public class ReceiveAccountcontroller extends
		BaseController<IReceiveAccountService> {
	@Resource
	private IReceiveAccountService service;

	/**
	 * 创建 收款账户
	 * 
	 * @param accountInfo
	 *            :收款账户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/create" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope create(AccountInfo accountInfo,String custName,String custId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									accountInfo.getBankBranchName(),
									RespCode.APS_DEBIT_BANK_BRANCH_NAME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_BANK_BRANCH_NAME_EMPTY
											.getDesc() },
							new Object[] {
									accountInfo.getBankName(),
									RespCode.APS_DEBIT_BANK_NAME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_BANK_NAME_EMPTY
											.getDesc() },
							new Object[] { accountInfo.getBankId(),
									RespCode.APS_DEBIT_BANK_ID_EMPTY.getCode(),
									RespCode.APS_DEBIT_BANK_ID_EMPTY.getDesc() },
							new Object[] {
									accountInfo.getReceiveAccountNo(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_NO_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_NO_EMPTY
											.getDesc() },
							new Object[] {
									accountInfo.getReceiveAccountId(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY
											.getDesc() });
			// 设置操作员
			accountInfo.setCreatedBy(custId);
			accountInfo.setCreatedName(custName);
			// 调用服务创建收款账户
			service.createAccounting(accountInfo);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 修改 收款账户名称---需求修改该功能废弃
	 * 
	 * @param accountInfo
	 *            :收款账户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/update" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope update(AccountInfo accountInfo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									accountInfo.getBankBranchName(),
									RespCode.APS_DEBIT_BANK_BRANCH_NAME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_BANK_BRANCH_NAME_EMPTY
											.getDesc() },
							new Object[] {
									accountInfo.getBankName(),
									RespCode.APS_DEBIT_BANK_NAME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_BANK_NAME_EMPTY
											.getDesc() },
							new Object[] { accountInfo.getBankId(),
									RespCode.APS_DEBIT_BANK_ID_EMPTY.getCode(),
									RespCode.APS_DEBIT_BANK_ID_EMPTY.getDesc() },
							new Object[] {
									accountInfo.getReceiveAccountNo(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_NO_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_NO_EMPTY
											.getDesc() },
							new Object[] {
									accountInfo.getReceiveAccountId(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY
											.getDesc() },
							new Object[] {
									accountInfo.getReceiveAccountInfoId(),
									RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY
											.getDesc() });
			// 设置操作员
			accountInfo.setUpdatedBy(request.getParameter("custId"));

			// 调用服务修改收款账户
			service.updateAccounting(accountInfo);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取一条收款账户名称
	 * 
	 * @param receiveAccountInfoId
	 *            :收款账户信息Id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findById" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findById(String receiveAccountInfoId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					receiveAccountInfoId,
					RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY.getCode(),
					RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY.getDesc() });

			AccountInfo accounting = service
					.getAccountingBean(receiveAccountInfoId);

			hre = new HttpRespEnvelope(accounting);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取 收款账户下拉菜单列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getAccountingMenu" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope getAccountingMenu(HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			List<AccountOption> accounts = service.getAccountingDropdownMenu();
			List<Menu> menus = new ArrayList<Menu>();
			for (AccountOption account : accounts) {
				menus.add(new Menu(account.getName(), account.getValue()));
			}
			hre = new HttpRespEnvelope(menus);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 
	 * 禁用收款账户信息
	 * 
	 * @param receiveAccountInfoId
	 *            :收款账户信息Id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/forbidAccountInfo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope forbidAccountInfo(String receiveAccountInfoId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					receiveAccountInfoId,
					RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY.getCode(),
					RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY.getDesc() });

			service.forbidAccountInfo(receiveAccountInfoId);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	@Override
	protected IBaseService<IReceiveAccountService> getEntityService() {
		return service;
	}
}
