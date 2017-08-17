/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.userpassword;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.userpassword.ConsumerPwdService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.userpassword
 * @ClassName: ConsumerPwdController
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:23:54
 * @version V3.0
 */
@Controller
@RequestMapping("consumerPwd")
public class ConsumerPwdController extends BaseController<ConsumerPwdService> {

	@Resource
	private ConsumerPwdService consumerPwdService;

	@SuppressWarnings("rawtypes")
	protected IBaseService getEntityService() {
		return consumerPwdService;
	}

	/**
	 * 查询分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/listconsumerinfopage" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope listConsumerInfoPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", consumerPwdService);
			request.setAttribute("methodName", "findScrollResult");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	@ResponseBody
	@RequestMapping(value = { "/resetLogPwdForCarderByReChecker" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope resetLogPwdForCarderByReChecker(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			String regionalResNo = request.getParameter("pointNo");
			String  operCustId=request.getParameter("custId");
			String userName = request.getParameter("userName");
			String loginPwd = request.getParameter("loginPwd");
			String secretKey = request.getParameter("randomToken");
			String perCustId = request.getParameter("perCustId");
			consumerPwdService.resetLogPwdForCarderByReChecker(regionalResNo,
					userName, loginPwd, secretKey, perCustId,operCustId);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/resetTradePwdForCarderByReChecker" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope resetTradePwdForCarderByReChecker(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			String regionalResNo = request.getParameter("pointNo");
			String  operCustId=request.getParameter("custId");
			String userName = request.getParameter("userName");
			String loginPwd = request.getParameter("loginPwd");
			String secretKey = request.getParameter("randomToken");
			String perCustId = request.getParameter("perCustId");
			consumerPwdService.resetTradePwdForCarderByReChecker(regionalResNo,
					userName, loginPwd, secretKey, perCustId,operCustId);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}
}
