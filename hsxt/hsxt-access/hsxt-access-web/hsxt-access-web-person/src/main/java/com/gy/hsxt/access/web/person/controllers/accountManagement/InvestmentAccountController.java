/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.accountManagement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.person.services.accountManagement.IInvestmentAccountService;
import com.gy.hsxt.access.web.person.services.accountManagement.ITransInnerService;
import com.gy.hsxt.access.web.person.services.common.IPubParamService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.DoubleUtil;

/***************************************************************************
 * <PRE>
 * Description 		: 投资账户操作类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.accountManagement  
 * 
 * File Name        : IntegralAccountController 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-26 下午5:22:16
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-26 下午5:22:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("investmentAccount")
public class InvestmentAccountController extends BaseController {
	@Resource
	private IInvestmentAccountService investmentAccountService; // 投资账户

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类

	@Autowired
	private ITransInnerService ransInnerService; // 内部转账

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

	/**
	 * 查询余额信息
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_investment_blance" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findInvestmentBlance(String hsResNo,
			HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;
		Map<String, Object> map = null;
		CustomPointDividend customPointDividend = null;

		// 执行查询方法
		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取积分投资分红信息
			customPointDividend = this.investmentAccountService
					.findInvestDividendInfo(hsResNo, DateUtil.getAssignYear(-1));

			map = new HashMap<String, Object>();

			if (customPointDividend != null) {
				// 投资分红互生币=计算定向消费币+流通币
				BigDecimal addNum = DoubleUtil.add(
						customPointDividend.getDirectionalDividend(),
						customPointDividend.getNormalDividend());

				// 保存数据到map
				map.put("dividendsHsb", addNum.toString()); // 投资分红互生币
				map.put("dividendYear", customPointDividend.getDividendYear()); // 年份
				map.put("directionalHsb",
						customPointDividend.getDirectionalDividend()); // 定向消费币
				map.put("circulationHsb",
						customPointDividend.getNormalDividend()); // 流通币
				map.put("dividendsRate",
						customPointDividend.getYearDividendRate()); // 投资分红回报率
				map.put("investmentTotal",
						customPointDividend.getAccumulativeInvestCount()); // 累计积分投资总数
			}

			hre = new HttpRespEnvelope(map);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询积分投资列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/point_invest_page" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope pointInvestPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);

			// 分页查询
			request.setAttribute("serviceName", investmentAccountService);
			request.setAttribute("methodName", "pointInvestPage");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询积分投资分红列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/point_dividend_page" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope pointDividendPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);

			// 分页查询
			request.setAttribute("serviceName", investmentAccountService);
			request.setAttribute("methodName", "pointDividendPage");
			hre = super.doList(request, response);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询积分投资分红计算明细
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/dividend_detail_page" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope dividendDetailPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);

			// 分页查询
			request.setAttribute("serviceName", investmentAccountService);
			request.setAttribute("methodName", "dividendDetailPage");
			hre = super.doList(request, response);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询余额信息
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/dividendDetailPageTotal" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope dividendDetailPageTotal(String hsResNo, String year,
			HttpServletRequest request) {
		// 变量声明
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取积分投资分红信息
			CustomPointDividend customPointDividend = this.investmentAccountService
					.getInvestDividendInfo(hsResNo, year);

			hre = new HttpRespEnvelope(customPointDividend);

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
		return investmentAccountService;
	}
}
