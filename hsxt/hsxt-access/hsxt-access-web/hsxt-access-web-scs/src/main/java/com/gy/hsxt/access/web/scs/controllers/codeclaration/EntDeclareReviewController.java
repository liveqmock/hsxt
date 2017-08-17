package com.gy.hsxt.access.web.scs.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.codeclaration.IEntDeclareReviewService;
import com.gy.hsxt.bs.bean.apply.DeclareAppInfo;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.controllers.codeclaration
 * @className     : EntDeclareReviewController.java
 * @description   : 企业申报复核
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entDeclareReviewController")
public class EntDeclareReviewController extends BaseController{
	
	@Resource
    private IEntDeclareReviewService reviewService; //企业申报查询服务类
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request,Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			//只在查询企业申报复核才校验日期
			if(StringUtils.isBlank(request.getAttribute("serviceName"))){
			    RequestUtil.verifyQueryDate((String) filterMap.get("startDate"), (String) filterMap.get("endDate"));//校验时间
			}
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	 /**
     * 
     * 方法名称：查询办理状态信息
     * 方法描述：依据企业申请编号查询办理状态信息
     * @param request HttpServletRequest对象
     * @param request HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findOperationHisList")
    public HttpRespEnvelope findOperationHisList(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifySecureToken(request);
            request.setAttribute("serviceName", reviewService);
            request.setAttribute("methodName", "findOperationHisList");
            return super.doList(request, response);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询申报信息
     * 方法描述：依据企业申请编号查询申报信息
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDeclareAppInfoByApplyId")
    public HttpRespEnvelope findDeclareAppInfoByApplyId(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID}
            );
            DeclareAppInfo obj = reviewService.findDeclareAppInfoByApplyId(applyId);
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：服务公司审批申报
     * 方法描述：服务公司审批申报
     * @param request HttpServletRequest对象
     * @param apprParam 审批内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/serviceApprDeclare")
    public HttpRespEnvelope serviceApprDeclare(HttpServletRequest request, @ModelAttribute ApprParam apprParam) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID},
                new Object[] {apprParam.getIsPass(), RespCode.SW_ENT_REVIEW_ISPASS_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {apprParam.getApprRemark(), 0, 300, RespCode.SW_ENT_REVIEW_LENGTH_INVALID}
            );
            apprParam.setOptCustId(custId);
            apprParam.setOptEntName(optEntName);
            apprParam.setOptName(custName);
            this.reviewService.serviceApprDeclare(apprParam);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }

	@Override
	protected IBaseService getEntityService() {
		return reviewService;
	}

}
