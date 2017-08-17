/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.accountPersonNoCard;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.accountPerNoCard.IAccountNoCarService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 非持卡人账户流水查询
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.accountPersonNoCard  
 * @ClassName: HsbAccPerNoCardController 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-5-9 下午4:45:57 
 * @version V1.0
 */
@Controller
@RequestMapping("hbPerNoCardAccount")
public class HbAccPerNoCardController extends BaseController<Object> {
	
    @Resource
    private IAccountNoCarService accountNoCarService; // 非持卡人互生币服务类

	/**
	 * 非持卡人查询货币流水
	 * 
	 * @Description:
	 * @author: weixz
	 * @created: 2016年2月18日 上午11:45:03
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/detailed_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchAccEntryPage(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;
		try
		{
			// Token验证
			super.verifySecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", accountNoCarService);
			request.setAttribute("methodName", "searchPerNoCardAccPage");
			hre = super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService<?> getEntityService()
	{
		return null;
	}
}
