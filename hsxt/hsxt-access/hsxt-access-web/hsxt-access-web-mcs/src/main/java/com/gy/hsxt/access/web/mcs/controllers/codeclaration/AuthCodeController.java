package com.gy.hsxt.access.web.mcs.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IAuthCodeService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.controllers.codeclaration
 * @className     : AuthCodeController.java
 * @description   : 授权码查询
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("authCodeController")
public class AuthCodeController extends BaseController{
	
	@Resource
    private IAuthCodeService authCodeService; //授权码查询服务类
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	@Override
	protected IBaseService getEntityService() {
		return authCodeService;
	}

}
