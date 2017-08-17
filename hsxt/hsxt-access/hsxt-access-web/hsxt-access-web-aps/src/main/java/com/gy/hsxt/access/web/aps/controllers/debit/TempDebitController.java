/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.controllers.debit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.debit.ITempAcctService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.debit
 * @className : TempDebitController.java
 * @description : 临账管理
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("tempDebit")
public class TempDebitController extends BaseController<ITempAcctService> {
	@Autowired
	private ITempAcctService service;

	/**
	 * 临账录入
	 * 
	 * @param debit
	 *            :临账bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/create" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope create(Debit debit, String custId, String custName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									debit.getAccountReceiveTime(),
									RespCode.APS_DEBIT_ACCOUNT_RECEIVETIME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_RECEIVETIME_EMPTY
											.getDesc() },
							new Object[] {
									debit.getAccountInfoId(),
									RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY
											.getDesc() },
							new Object[] {
									debit.getPayEntCustName(),
									RespCode.APS_DEBIT_PAY_ENT_CUST_NAME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_PAY_ENT_CUST_NAME_EMPTY
											.getDesc() },
							new Object[] {
									debit.getPayType(),
									RespCode.APS_DEBIT_PAY_TYPE_ERROR.getCode(),
									RespCode.APS_DEBIT_PAY_TYPE_ERROR.getDesc() },
							new Object[] {
									debit.getPayTime(),
									RespCode.APS_DEBIT_PAY_TIME_EMPTY.getCode(),
									RespCode.APS_DEBIT_PAY_TIME_EMPTY.getDesc() },
							new Object[] {
									debit.getPayAmount(),
									RespCode.APS_DEBIT_PAY_AMOUNT_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_PAY_AMOUNT_EMPTY
											.getDesc() });
			// 设置操作员
			debit.setCreatedBy(custId);
			debit.setCreatedName(custName);
			// 增加临账记录
			service.createDebit(debit);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 临账更新
	 * 
	 * @param debit
	 *            :临账bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/update" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope update(Debit debit, String custId, String custName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									debit.getAccountReceiveTime(),
									RespCode.APS_DEBIT_ACCOUNT_RECEIVETIME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_RECEIVETIME_EMPTY
											.getDesc() },
							new Object[] {
									debit.getAccountInfoId(),
									RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_ACCOUNT_INFO_ID_EMPTY
											.getDesc() },
							new Object[] {
									debit.getPayEntCustName(),
									RespCode.APS_DEBIT_PAY_ENT_CUST_NAME_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_PAY_ENT_CUST_NAME_EMPTY
											.getDesc() },
							new Object[] {
									debit.getPayType(),
									RespCode.APS_DEBIT_PAY_TYPE_ERROR.getCode(),
									RespCode.APS_DEBIT_PAY_TYPE_ERROR.getDesc() },
							new Object[] {
									debit.getPayTime(),
									RespCode.APS_DEBIT_PAY_TIME_EMPTY.getCode(),
									RespCode.APS_DEBIT_PAY_TIME_EMPTY.getDesc() },
							new Object[] {
									debit.getPayAmount(),
									RespCode.APS_DEBIT_PAY_AMOUNT_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_PAY_AMOUNT_EMPTY
											.getDesc() },
							new Object[] {
									debit.getDebitId(),
									RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getCode(),
									RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getDesc() });

			// 设置操作员
			debit.setUpdatedBy(custId);
			debit.setUpdatedName(custName);
			// 修改临账记录
			service.updateDebit(debit);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 复核临账录入
	 * 
	 * @param debit
	 *            :临账bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/approve" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope approve(Debit debit, String custId,
			String custName, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { debit.getAuditRecord(),
							RespCode.APS_DEBIT_AUDIT_RECORD_EMPTY.getCode(),
							RespCode.APS_DEBIT_AUDIT_RECORD_EMPTY.getDesc() },
					new Object[] { debit.getDebitStatus(),
							RespCode.APS_DEBIT_DEBIT_STATUS_EMPTY.getCode(),
							RespCode.APS_DEBIT_DEBIT_STATUS_EMPTY.getDesc() },
					new Object[] { debit.getDebitId(),
							RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getCode(),
							RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getDesc() });
			// 设置操作员
			debit.setUpdatedBy(custId);
			debit.setUpdatedName(custName);
			// 审核
			service.approveDebit(debit);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取一条临账
	 * 
	 * @param debitId
	 *            :临账Id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findById" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findById(String debitId, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { debitId,
					RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getCode(),
					RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getDesc() });

			Debit debit = service.findbyId(debitId);

			hre = new HttpRespEnvelope(debit);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 临账与不动款互转
	 * 
	 * @param dblOptCustId
	 *            :双签账号
	 * @param password
	 *            : 双签账号密码
	 * @param debit
	 *            : 临账bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/turnNotMovePayment" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope turnNotMovePayment(String dblOptCustId,
			String password, Debit debit, String custId, String custName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 设置操作员
			debit.setUpdatedBy(custId);
			debit.setUpdatedName(custName);
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									debit.getBalanceRecord(),
									RespCode.APS_DEBIT_BALANCE_RECORD_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_BALANCE_RECORD_EMPTY
											.getDesc() },
							new Object[] {
									debit.getDebitStatus(),
									RespCode.APS_DEBIT_DEBIT_STATUS_EMPTY
											.getCode(),
									RespCode.APS_DEBIT_DEBIT_STATUS_EMPTY
											.getDesc() },
							new Object[] {
									debit.getDebitId(),
									RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getCode(),
									RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getDesc() });

			service.turnNotMovePayment(debit, dblOptCustId);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询不动款总额
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/notMoveSum" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope notMoveSum(HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			String noMoveSum = service.notMovePaymentSum();

			hre = new HttpRespEnvelope(noMoveSum);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据拟使用企业互生号查询待关联临账记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/queryDebitListByQuery" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryDebitListByQuery(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "queryDebitListByQuery");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 分页查询临账录入复核/退款复核列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryDebitTaskListPage" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryTempOrderByDebitId(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "queryDebitTaskListPage");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	@Override
	protected IBaseService getEntityService() {
		return service;
	}
}
