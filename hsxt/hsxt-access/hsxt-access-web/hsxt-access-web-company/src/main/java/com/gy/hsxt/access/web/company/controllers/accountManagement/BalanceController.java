/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.accountManagement;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.common.exception.HsException;

/**
 * 账户明细查询
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.accountManagement
 * @ClassName: BalanceController
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-28 下午4:57:35
 * @version V3.0
 */
@Controller
@RequestMapping("balanceController")
public class BalanceController extends BaseController {

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类

	/**
	 * 货币账户明细查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/detailed_page" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchAccEntryPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "searchAccEntryPage");
			hre = super.doList(request, response);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 所有详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月26日 下午7:40:12
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_acc_opt_detailed" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getAccOptDetailed(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);
			String transNo = request.getParameter("transNo");
			String transType = request.getParameter("transType");
			hre = new HttpRespEnvelope(balanceService.queryAccOptDetailed(transNo, transType));
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 企业积分转互生币
	 * @param request
	 * @param response
	 * @param transNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getPvToHsbInfo" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPvToHsbInfo(HttpServletRequest request,
			HttpServletResponse response, String transNo) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.verifySecureToken(request);
			PvToHsb pvToHsb = balanceService.getPvToHsbInfo(transNo);
			hre = new HttpRespEnvelope(pvToHsb);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 企业积分转互生币 
	 * @param request
	 * @param response
	 * @param transNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getBuyHsbInfo" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getBuyHsbInfo(HttpServletRequest request,
			HttpServletResponse response, String transNo) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.verifySecureToken(request);
			BuyHsb buyhsb = balanceService.getBuyHsbInfo(transNo);
			hre = new HttpRespEnvelope(buyhsb);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService getEntityService() {
		return balanceService;
	}
}
