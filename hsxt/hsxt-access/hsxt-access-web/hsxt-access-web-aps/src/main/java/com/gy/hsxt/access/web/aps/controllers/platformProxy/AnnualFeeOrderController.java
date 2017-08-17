package com.gy.hsxt.access.web.aps.controllers.platformProxy;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.common.IPubParamService;
import com.gy.hsxt.access.web.aps.services.platformProxy.IAnnualFeeOrderService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.OrderChannel;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;

@Controller
@RequestMapping("annualFeeOrder")
public class AnnualFeeOrderController extends BaseController<IAnnualFeeOrderService> {
	@Resource
	private IAnnualFeeOrderService service;

	@Resource
	private IPubParamService pubParamService;

	/**
	 * 查询某一个企业年费信息
	 * 
	 * @param invoiceId
	 *            :企业客户号ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value =
	{ "/queryAnnualFeeInfo" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryAnnualFeeInfo(String companyCustId, HttpServletRequest request)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// 验证安全令牌
			verifySecureToken(request);

			AnnualFeeInfo annualfee = service.queryAnnualFeeInfo(companyCustId);
			hre = new HttpRespEnvelope(annualfee);
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 提交年费业务订单
	 * 
	 * @param annualFeeOrder
	 *            :年费业务订单Bean
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/submitAnnualFeeOrder" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope submitAnnualFeeOrder(String companyCustId, String orderHsbAmount, String custId, ApsBase apsBase, HttpServletRequest request)
	{
		HttpRespEnvelope hre = null;
		try
		{
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			Order order = new Order();
			order.setCustId(companyCustId);
			order.setOrderHsbAmount(orderHsbAmount);
			order.setOrderChannel(Channel.WEB.getCode());
			order.setOrderOperator(apsBase.getCustName()+"("+apsBase.getOperName()+")");
			order.setPayChannel(PayChannel.TRANSFER_REMITTANCE.getCode());
			AnnualFeeOrder o = service.submitAnnualFeeOrder(AnnualFeeOrder.bulid(order));
			hre = new HttpRespEnvelope(o);
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 提交兑换互生币业务订单
	 * 
	 * @param annualFeeOrder
	 *            :年费业务订单Bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value =
	{ "/saveBuyHsb" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope saveBuyHsb(String companyCustId, String companyCustName, Order order, String custName, String custId,
			HttpServletRequest request)
	{
		HttpRespEnvelope hre = null;
		try
		{
			// 验证安全令牌
			verifySecureToken(request);

			order.setCustId(companyCustId);
			order.setHsResNo(companyCustId.substring(0, 11));
			order.setCustName(companyCustName);
			order.setCustType(HsResNoUtils.getCustTypeByHsResNo(companyCustId.substring(0, 11)));
			order.setOrderType(OrderType.BUY_HSB.getCode());
			order.setIsProxy(false);
			order.setOrderChannel(OrderChannel.WEB.getCode());
			order.setOrderOperator(custId);
			order.setPayChannel(PayChannel.TRANSFER_REMITTANCE.getCode());
			service.saveBuyHsb(order);
			hre = new HttpRespEnvelope();
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取企业购买互生币限额
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月22日 下午3:29:01
	 * @param hsCustId
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value =
	{ "/getEntBuyHsbLimit" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope getEntBuyHsbLimit(String hsCustId, HttpServletRequest request)
	{
		HttpRespEnvelope hre = null;
		try
		{
			// 验证安全令牌
			verifySecureToken(request);
			hre = new HttpRespEnvelope(pubParamService.getEntBuyHsbLimit(hsCustId));
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		} catch (Exception ex)
		{
			throw new HsException(ASRespCode.AS_DUBBO_INVOKE_ERROR, ex.getMessage());
		}
		return hre;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return service;
	}

}
