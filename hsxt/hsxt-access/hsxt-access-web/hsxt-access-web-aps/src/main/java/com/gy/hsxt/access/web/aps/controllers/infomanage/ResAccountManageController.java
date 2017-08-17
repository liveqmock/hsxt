/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.infomanage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.infomanage.IResAccountManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.infomanage
 * @className     : ResAccountManageController.java
 * @description   : 账户资源管理
 * @author        : maocy
 * @createDate    : 2016-02-19
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("resAccountManageController")
public class ResAccountManageController extends BaseController {
	
	@Resource
    private IResAccountManageService resAccountManage;//账户资源管理服务类
	
	 /**
     * 
     * 方法名称：账户资源管理-消费者资源
     * 方法描述：账户资源管理-消费者资源
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findConsumerInfoList")
	public HttpRespEnvelope listConsumerInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", resAccountManage);
			request.setAttribute("methodName", "findConsumerInfoList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：资源名录-企业资源
     * 方法描述：资源名录-企业资源
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findReportResEntInfoList")
	public HttpRespEnvelope findReportResEntInfoList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", resAccountManage);
			request.setAttribute("methodName", "findReportResEntInfoList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	
	/**
     * 
     * 方法名称：资源名录-消费者资源
     * 方法描述：资源名录-消费者资源
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findReportConsumerInfoList")
	public HttpRespEnvelope findReportConsumerInfoList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", resAccountManage);
			request.setAttribute("methodName", "findReportConsumerInfoList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：账户资源管理-企业资源
     * 方法描述：账户资源管理-企业资源
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list")
	public HttpRespEnvelope doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", resAccountManage);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	@Override
	protected IBaseService getEntityService() {
		return this.resAccountManage;
	}

}
