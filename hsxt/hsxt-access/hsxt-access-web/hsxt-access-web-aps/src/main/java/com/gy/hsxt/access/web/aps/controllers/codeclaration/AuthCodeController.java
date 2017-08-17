package com.gy.hsxt.access.web.aps.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.codeclaration.IAuthCodeService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.codeclaration
 * @className     : AuthCodeController.java
 * @description   : 授权码查询
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
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
	
	/**
     * 
     * 方法名称：重发申报授权码
     * 方法描述：重发申报授权码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendAuthCode")
    public HttpRespEnvelope sendAuthCode(HttpServletRequest request) {
    	try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.APS_APPLYID_INVALID}
            );
            this.authCodeService.sendAuthCode(applyId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
	
	@Override
	protected IBaseService getEntityService() {
		return this.authCodeService;
	}

}
