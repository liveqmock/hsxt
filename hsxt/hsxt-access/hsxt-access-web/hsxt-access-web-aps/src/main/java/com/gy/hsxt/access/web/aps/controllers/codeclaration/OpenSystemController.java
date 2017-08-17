package com.gy.hsxt.access.web.aps.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.codeclaration.IOpenSystemService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.DebtOpenSys;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.codeclaration
 * @className     : OpenSystemController.java
 * @description   : 开系统欠费审核
 * @author        : maocy
 * @createDate    : 2015-12-30
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("openSystemController")
public class OpenSystemController extends BaseController {
	
	@Resource
    private IOpenSystemService openSystemService;//开系统欠费审核服务类
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			RequestUtil.verifyQueryDate((String) filterMap.get("startDate"), (String) filterMap.get("endDate"));//校验时间
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	/**
	 * 方法名称：开系统欠费审核
	 * 方法描述：开系统欠费审核
	 * @param request HttpServletRequest
	 * @param debtOpenSys 审核信息
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/apprDebtOpenSys")
	public HttpRespEnvelope apprDebtOpenSys(HttpServletRequest request, @ModelAttribute DebtOpenSys debtOpenSys) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String applyId = request.getParameter("applyId");//申请编号
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
	        	new Object[] {debtOpenSys.getDblOptCustId(), RespCode.AS_DOULBE_USERID_INVALID},
	        	new Object[] {debtOpenSys.getFeeFlag(), RespCode.APS_DEBTSYSTEM_STATUS_INVALID}
	        );
	        //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {debtOpenSys.getOptRemark(), 0, 300, RespCode.APS_OPENSYSTEM_APPR_LENGTH_INVALID}
            );
            debtOpenSys.setOptCustId(custId);
            debtOpenSys.setOptName(custName);
            debtOpenSys.setOptEntName(optEntName);
	        this.openSystemService.apprDebtOpenSys(debtOpenSys);
	        return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	@Override
	protected IBaseService getEntityService() {
		return openSystemService;
	}

}
