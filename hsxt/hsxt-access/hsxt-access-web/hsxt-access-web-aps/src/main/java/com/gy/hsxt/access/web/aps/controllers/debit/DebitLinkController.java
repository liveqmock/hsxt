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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.debit.IDebitLinkService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkDebit;
import com.gy.hsxt.bs.bean.tool.OrderBean;
import com.gy.hsxt.bs.common.enumtype.tempacct.LinkStatus;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.debit
 * @className : DebitLinkController.java
 * @description : 临账关联
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("debitLink")
public class DebitLinkController extends BaseController<IDebitLinkService> {
	@Autowired
	private IDebitLinkService service;

	/**
	 * 创建临帐关联申请
	 * 
	 * @param tempAcctLink
	 *            :临账关联Bean
	 * @param debitIds
	 *            ：临账ID数组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/create" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope create(TempAcctLink tempAcctLink, String debitIds,String custId,String custName,String linkAmount,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { tempAcctLink.getOrderNo(),
							RespCode.APS_DEBIT_ORDER_NO_EMPTY.getCode(),
							RespCode.APS_DEBIT_ORDER_NO_EMPTY.getDesc() },
					new Object[] {
							tempAcctLink.getTotalLinkAmount(),
							RespCode.APS_DEBIT_TOTAL_LINK_AMOUNT_EMPTY
									.getCode(),
							RespCode.APS_DEBIT_TOTAL_LINK_AMOUNT_EMPTY
									.getDesc() },
					new Object[] { tempAcctLink.getCashAmount(),
							RespCode.APS_DEBIT_CASH_AMOUNT_EMPTY.getCode(),
							RespCode.APS_DEBIT_CASH_AMOUNT_EMPTY.getDesc() },
					new Object[] { tempAcctLink.getReqRemark(),
							RespCode.APS_DEBIT_REQ_REMARK_EMPTY.getCode(),
							RespCode.APS_DEBIT_REQ_REMARK_EMPTY.getDesc() },
					new Object[] { debitIds,
							RespCode.APS_DEBIT_DEBIT_IDS_EMPTY.getCode(),
							RespCode.APS_DEBIT_DEBIT_IDS_EMPTY.getDesc() });
			// 设置关联状态
			tempAcctLink.setStatus(LinkStatus.PENDING.ordinal());

			// 解析debitIds，关设置到关联bean的关联列中
			String debitIdArray[] = debitIds.split(",");
			String linkAmounts[] = linkAmount.split(",");
			double totalLinkAmount = 0.0;
			List<TempAcctLinkDebit> linkDebits = new ArrayList<>();
			for (int i = 0; i < debitIdArray.length; i++) { 
				TempAcctLinkDebit linkDebit = new TempAcctLinkDebit();
				linkDebit.setDebitId(debitIdArray[i]);
				linkDebit.setLinkAmount(linkAmounts[i]);
				linkDebit.setOrderNo(tempAcctLink.getOrderNo());
				linkDebits.add(linkDebit);
				totalLinkAmount += Double.parseDouble(linkAmounts[i]);
			}
			tempAcctLink.setTempAcctLinkDebits(linkDebits);
			tempAcctLink.setTotalLinkAmount("" + totalLinkAmount);
			// 设置操作员
			tempAcctLink.setReqOperator(custId);
			tempAcctLink.setReqOperatorName(custName);

			// 调用服务创建关联申请
			service.createTempAcctLink(tempAcctLink);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/** 复核临帐关联申请 */
	/**
	 * 
	 * @param tempAcctLink
	 *            :临账关联Bean
	 * @param linkAmount
	 *            :关联金额数组
	 * @param debitIds
	 *            ：临账ID数组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/approve" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope approve(TempAcctLink tempAcctLink,
			String linkAmount, String debitIds,String custId,String custName, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { tempAcctLink.getOrderNo(),
							RespCode.APS_DEBIT_ORDER_NO_EMPTY.getCode(),
							RespCode.APS_DEBIT_ORDER_NO_EMPTY.getDesc() },
					new Object[] { tempAcctLink.getCashAmount(),
							RespCode.APS_DEBIT_CASH_AMOUNT_EMPTY.getCode(),
							RespCode.APS_DEBIT_CASH_AMOUNT_EMPTY.getDesc() },
					new Object[] { tempAcctLink.getApprRemark(),
							RespCode.APS_DEBIT_APPR_REMARK_EMPTY.getCode(),
							RespCode.APS_DEBIT_APPR_REMARK_EMPTY.getDesc() },
					new Object[] { debitIds,
							RespCode.APS_DEBIT_DEBIT_IDS_EMPTY.getCode(),
							RespCode.APS_DEBIT_DEBIT_IDS_EMPTY.getDesc() },
					new Object[] { linkAmount,
							RespCode.APS_DEBIT_LINK_AMOUNTS_EMPTY.getCode(),
							RespCode.APS_DEBIT_LINK_AMOUNTS_EMPTY.getDesc() });

			// 解析debitIds，关设置到关联bean的关联列中
			String linkAmounts[] = linkAmount.split(",");
			String debitId[] = debitIds.split(",");
			double totalLinkAmount = 0.0;
			List<TempAcctLinkDebit> linkDebits = new ArrayList<TempAcctLinkDebit>();
			for (int i = 0; i < linkAmounts.length; i++) {
				TempAcctLinkDebit linkDebit = new TempAcctLinkDebit();
				linkDebit.setDebitId(debitId[i]);
				linkDebit.setOrderNo(tempAcctLink.getOrderNo());
				linkDebit.setLinkAmount(linkAmounts[i]);
				linkDebits.add(linkDebit);
				totalLinkAmount += Double.parseDouble(linkAmounts[i]);
			}

			tempAcctLink.setTempAcctLinkDebits(linkDebits);
			tempAcctLink.setTotalLinkAmount("" + totalLinkAmount);

			// 设置操作员
			tempAcctLink.setApprOperator(custId);
			tempAcctLink.setApprOperatorName(custName);

			// 调用服务复核关联申请
			service.apprTempAcctLink(tempAcctLink);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据订单编号查询对应的临账关联申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/findByOrderNo" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findByOrderNo(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		String orderNo = request.getParameter("search_orderNo");

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo,
					RespCode.APS_DEBIT_ORDER_NO_EMPTY.getCode(),
					RespCode.APS_DEBIT_ORDER_NO_EMPTY.getDesc() });

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "queryLinkDebitByOrderNo");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

    /**
     * 分页查询临帐关联未复核的业务订单
     * 
     * @param request
     * @param response
     * @return
     */
	@ResponseBody
    @RequestMapping(value = { "/noApproveList" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope noApproveList(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证安全令牌
            verifySecureToken(request);

            // 设置服务名和方法名，调用父类查询分页方法
            request.setAttribute("serviceName", service);
            request.setAttribute("methodName", "queryTempOrderListPage");
            return super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

	/**
	 * 分页查询临帐关联的业务订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryTempOrderByDebitId" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryTempOrderByDebitId(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// 验证安全令牌
			verifySecureToken(request);

			String debitId = request.getParameter("search_debitId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { debitId,
					RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getCode(),
					RespCode.APS_DEBIT_DEBIT_ID_EMPTY.getDesc() });

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "queryTempOrderByDebitId");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据 订单号码查询订单详情
	 * 
	 * @param orderNo:订单号码
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getOrderByOrderId" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope getOrderByOrderId(String orderNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo,
					RespCode.APS_DEBIT_ORDER_NO_EMPTY.getCode(),
					RespCode.APS_DEBIT_ORDER_NO_EMPTY.getDesc() });

			OrderBean orderBean = service.findOrderByNo(orderNo);
			hre=new HttpRespEnvelope(orderBean);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 
	 * 查询单个临账的所有关联申请信息
	 * 
	 * @param debitId:临账ID
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryTempAcctLinkListByDebitId" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryTempAcctLinkListByDebitId(String debitId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// 验证安全令牌
			verifySecureToken(request);

			List<TempAcctLink> debitLinks = service.queryTempAcctLinkListByDebitId(debitId);
			hre=new HttpRespEnvelope(debitLinks);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 查询单个临账关联信息(待关联、已关联、审批信息)
	 * @param debitId
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = { "/queryDebitLinkInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryDebitLinkInfo(String debitId, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // 安全令牌校验
            super.verifySecureToken(request);
            //参数验证
            RequestUtil.checkParamIsNotEmpty(new Object[]{debitId, RespCode.APS_DEBIT_ORDER_NO_EMPTY});
            //临帐明细
            Map<String, Object> deptMap = service.queryDebitLinkInfo(debitId);
            hre = new HttpRespEnvelope(deptMap);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
	   
	@Override
	protected IBaseService getEntityService() {
		return service;
	}

}
