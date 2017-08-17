/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.systemService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.services.systemService.ISystemService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-person
 * @package       : com.gy.hsxt.access.web.person.controllers.systemService
 * @className     : SystemServiceController.java
 * @description   : 系统服务接口
 * @author        : maocy
 * @createDate    : 2016-01-21
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("systemServiceController")
public class SystemServiceController extends BaseController {
	
	@Resource 
	private ISystemService systemService;//系统服务类
	
	/**
	 * 方法名称：系统服务查询
	 * 方法描述：系统服务查询
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/list")
	@Override
	public HttpRespEnvelope doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			RequestUtil.verifyQueryDate(request.getParameter("search_startDate"), request.getParameter("search_endDate"));//校验时间
			String methodName = request.getParameter("search_methodName");//查询方法名称
			request.setAttribute("serviceName", systemService);
			request.setAttribute("methodName", methodName);
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	@Override
	protected IBaseService getEntityService() {
		return this.systemService;
	}

}
