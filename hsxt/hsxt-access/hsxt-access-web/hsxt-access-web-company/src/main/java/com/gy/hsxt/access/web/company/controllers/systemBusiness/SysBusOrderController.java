/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.common.PubParamService;
import com.gy.hsxt.access.web.company.services.systemBusiness.ISysBusOrderService;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**
 * 系统业务订单管理
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.systemBusiness
 * @ClassName: SysBusOrderController
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-22 下午6:42:20
 * @version V3.0.0
 */
@RequestMapping("sysbusorder")
@SuppressWarnings("rawtypes")
@Controller
public class SysBusOrderController extends BaseController {

	@Resource
	private ISysBusOrderService isysBusOrderService;

	@Resource
	private IBalanceService balanceService; // 账户信息服务类

	@Resource
	private PubParamService pubParamService;

	/**
	 * 系统业务订单去付款
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/tool_order_topay" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryPointNum(HttpServletRequest request, @ModelAttribute OrderQueryParam param,
			@ModelAttribute Page page)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String custId = request.getParameter("custId");
			try
			{
				Map<String, Object> result = new HashMap<String, Object>();
				// 查询企业互生币账户余额
				AccountBalance account = balanceService
						.findAccNormal(custId, AccountType.ACC_TYPE_POINT20110.getCode());

				// 获取平台当地信息
				LocalInfo local = pubParamService.findSystemInfo();

				// 比较企业互生币余额账户是否足够支付订单中的应付金额
				String totalAmount = request.getParameter("totalAmount");
				String accBalance = account.getAccBalance() == null || "".equals(account.getAccBalance()) ? "0"
						: account.getAccBalance();
				// 账户余额
				result.put("accblance", accBalance);
				if (Float.parseFloat(accBalance) < Float.parseFloat(totalAmount))
				{
					result.put("isEnough", "0");
				} else
				{
					result.put("isEnough", "1");
				}

				// 当地货币币种
				result.put("currenceName", local.getCurrencyNameCn());

				// 货币转换率
				result.put("rate", local.getExchangeRate());

				// 折合货币金额
				// Double money = DoubleUtil.mul(local.getExchangeRate(),
				// Float.parseFloat(totalAmount));
				// result.put("money", money);

				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 获取年费订单详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月2日 下午5:36:49
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_annualfee_order_detail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getAnnualFeeOrderDetail(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");// 订单号
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo,
						ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(), ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(isysBusOrderService.queryAnnualFeeOrderDetail(orderNo));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return isysBusOrderService;
	}

}
