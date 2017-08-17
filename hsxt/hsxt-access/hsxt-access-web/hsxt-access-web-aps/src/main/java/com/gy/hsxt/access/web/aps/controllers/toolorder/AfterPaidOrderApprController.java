/**
 * 
 */
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.IAfterPaidOrderApprService;
import com.gy.hsxt.access.web.aps.services.workflow.IWorkOrderService;
import com.gy.hsxt.access.web.bean.workflow.ApsStatusUpdateBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("afterPaidOrderAppr")
public class AfterPaidOrderApprController extends BaseController {

	@Autowired
	private IAfterPaidOrderApprService afterPaidOrderApprService;

	@Resource
	private IWorkOrderService iWorkOrderService;

	/**
	 * 分页查询售后订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月22日 下午5:49:39
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_after_order_plat_page" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findAfterOrderPlatPage(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				super.verifyPointNo(request);// 校验互生卡号
				request.setAttribute("serviceName", afterPaidOrderApprService);
				request.setAttribute("methodName", "queryAfterOrderPlatPage");
				httpRespEnvelope = super.doList(request, response);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@ResponseBody
	@RequestMapping(value = { "/searchOrderDetail" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchOrderDetail(HttpServletRequest request, String orderNo)
	{
		try
		{
			verifySecureToken(request);
			AfterService as = afterPaidOrderApprService.searchOrderDetail(orderNo);
			if (as.getDetail() == null || as.getDetail().isEmpty())
			{
				throw new HsException(ASRespCode.AS_NOT_QUERY_DATA);
			}
			return new HttpRespEnvelope(as);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}

	}

	@ResponseBody
	@RequestMapping(value = { "/apprAfterService" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope apprAfterService(HttpServletRequest request, AfterService asObj, String listDetail)
	{
		try
		{
			verifySecureToken(request);
			afterPaidOrderApprService.apprAfterService(asObj, listDetail);
			return new HttpRespEnvelope();
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 挂起
	 * 
	 * @param request
	 * @param workOrderNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "suspend", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope suspend(HttpServletRequest request, ApsStatusUpdateBean asub)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// 1、验证token
			super.verifySecureToken(request);

			// 2、验证有效数据
			asub.checkBizNo();

			// 3、设置状态
			asub.setStatus(TaskStatus.HANG_UP.getCode());

			// 4、挂起
			iWorkOrderService.updateStatus(asub);
			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 拒绝受理
	 * 
	 * @param request
	 * @param asub
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "door", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope door(HttpServletRequest request, ApsStatusUpdateBean asub)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// 1、验证token
			super.verifySecureToken(request);

			// 2、验证有效数据
			asub.checkBizNo();

			// 3、设置状态
			asub.setStatus(TaskStatus.UNACCEPT.getCode());

			// 4、更新为未受理状态
			iWorkOrderService.updateStatus(asub);
			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return afterPaidOrderApprService;
	}
}
