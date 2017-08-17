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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.debit.IRefundTempAcctService;
import com.gy.hsxt.access.web.aps.services.debit.ITempAcctService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tempacct.TempAcctRefund;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.debit
 * @className     : RefundTempDebitController.java
 * @description   : 临账退款管理
 * @author        : chenli
 * @createDate    : 2015-12-18
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("refundTempDebit")
public class RefundTempDebitController extends BaseController<ITempAcctService> {
	@Autowired
	private IRefundTempAcctService service;

	/**
	 * 临账退款录入
	 * @param tempAcctRefund :临账退款Bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/create" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope create(TempAcctRefund tempAcctRefund,String custId,String custName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { tempAcctRefund.getDebitId(),
							RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getCode(),
							RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getDesc() },
					new Object[] { tempAcctRefund.getRefundAmount(),
							RespCode.APS_DEBIT_REFUND_AMOUNT_EMPTY.getCode(),
							RespCode.APS_DEBIT_REFUND_AMOUNT_EMPTY.getDesc() },
					new Object[] { tempAcctRefund.getReqRemark(),
							RespCode.APS_DEBIT_REQ_REMARK_EMPTY.getCode(),
							RespCode.APS_DEBIT_REQ_REMARK_EMPTY.getDesc() });
			// 设置操作员
			tempAcctRefund.setReqOperator(custId);
			tempAcctRefund.setReqOperatorName(custName);
			// 增加临账记录
			service.createTempAcctRefund(tempAcctRefund);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 复核临账退款 
	 * @param tempAcctRefund :临账退款Bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/approve" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope approveDebit(TempAcctRefund tempAcctRefund,String custId,String custName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { tempAcctRefund.getDebitId(),
							RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getCode(),
							RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getDesc() },
					new Object[] { tempAcctRefund.getStatus(),
							RespCode.APS_DEBIT_DEBIT_STATUS_EMPTY.getCode(),
							RespCode.APS_DEBIT_DEBIT_STATUS_EMPTY.getDesc() },
					new Object[] { tempAcctRefund.getApprRemark(),
							RespCode.APS_DEBIT_APPR_REMARK_EMPTY.getCode(),
							RespCode.APS_DEBIT_APPR_REMARK_EMPTY.getDesc() });
			// 设置操作员
			tempAcctRefund.setApprOperator(custId);
			tempAcctRefund.setApprOperatorName(custName);
			// 审核
			service.apprTempAcctRefund(tempAcctRefund);

			hre = new HttpRespEnvelope();
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
