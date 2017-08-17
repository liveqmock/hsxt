package com.gy.hsxt.access.web.aps.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.codeclaration.IStaffQueryService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.codeclaration
 * @className     : StaffQueryController.java
 * @description   : 开启系统业务
 * @author        : maocy
 * @createDate    : 2015-12-19
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("staffQueryController")
public class StaffQueryController extends BaseController {
	
	@Resource
    private IStaffQueryService staffQueryService; //授权码查询服务类
	
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
	 * 方法名称：开启系统
	 * 方法描述：开启系统业务-开启系统
	 * @param request HttpServletRequest
	 * @param apprParam 审批信息
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/openSystem")
	public HttpRespEnvelope openSystem(HttpServletRequest request, @ModelAttribute ApprParam apprParam) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String applyId = request.getParameter("applyId");//申请编号
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
	        	new Object[] {apprParam.getDblOptCustId(), RespCode.AS_DOULBE_USERID_INVALID},
	        	new Object[] {apprParam.getIsPass(), RespCode.APS_OPENSYSTEM_STATUS_INVALID}
	        );
	        //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {apprParam.getApprRemark(), 0, 300, RespCode.APS_OPENSYSTEM_APPR_LENGTH_INVALID}
            );
            apprParam.setOptCustId(custId);
            apprParam.setOptName(custName);
            apprParam.setOptEntName(optEntName);
	        this.staffQueryService.openSystem(apprParam);
	        return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	@Override
	protected IBaseService getEntityService() {
		return staffQueryService;
	}

}
