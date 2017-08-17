package com.gy.hsxt.access.web.mcs.controllers.codeclaration;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IEntDeclareReviewService;
import com.gy.hsxt.access.web.mcs.services.codeclaration.IRegInfoService;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.bs.bean.apply.ResNo;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.controllers.codeclaration
 * @className     : EntDeclareReviewController.java
 * @description   : 复核业务
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entDeclareReviewController")
public class EntDeclareReviewController extends BaseController{
	
	@Resource
    private IEntDeclareReviewService reviewService;//企业申报查询服务类
	
	@Resource
    private IRegInfoService regInfoService;//企业申报-系统注册信息服务类
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request,Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			if(request.getAttribute("methodName") == null){
				RequestUtil.verifyQueryDate((String) filterMap.get("startDate"), (String) filterMap.get("endDate"));//校验时间
			}
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	/**
     * 
     * 方法名称：管理公司复核
     * 方法描述：管理公司复核
     * @param request HttpServletRequest对象
     * @param apprParam 审批内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/managerReviewDeclare")
    public HttpRespEnvelope managerReviewDeclare(HttpServletRequest request, @ModelAttribute ApprParam apprParam) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            String custId = request.getParameter("custId");//获取登陆客户号
            String custName = request.getParameter("cookieOperNoName");//获取登陆用户名称
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID},
                new Object[] {apprParam.getIsPass(), RespCode.MW_ENT_REVIEW_ISPASS_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {apprParam.getApprRemark(), 0, 300, RespCode.MW_ENT_REVIEW_LENGTH_INVALID}
            );
            apprParam.setOptCustId(custId);
            apprParam.setOptEntName(optEntName);
            apprParam.setOptName(custName);
            this.reviewService.managerReviewDeclare(apprParam);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号列表
     * 方法描述：查询服务公司可用互生号列表
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSerResNoList")
    public HttpRespEnvelope findSerResNoList(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            DeclareRegInfo regInfo = regInfoService.findDeclareByApplyId(applyId);
            if(regInfo == null){
            	throw new HsException(RespCode.MW_REGINFO_NOT_FOUND);
            }
            List<String> resList = this.reviewService.findSerResNoList(regInfo.getCountryNo(), regInfo.getProvinceNo(), regInfo.getCityNo());
            if(resList == null || resList.isEmpty()){
            	throw new HsException(RespCode.MW_REGINFO_FW_AVAIL_QUOTA_INVALID);
            }
            return new HttpRespEnvelope(resList.get(0));
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：管理公司选号
     * 方法描述：管理公司选号
     * @param request HttpServletRequest对象
     * @param apprParam 审批内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/managePickResNo")
    public HttpRespEnvelope managePickResNo(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            String custId = request.getParameter("custId");//获取登陆客户号
            String pickResNo = request.getParameter("pickResNo");//服务公司互生号
            Integer toSelectMode = CommonUtils.toInteger(request.getParameter("toSelectMode"));//服务公司互生号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.MW_APPLYID_INVALID}
            );
            this.reviewService.managePickResNo(applyId, pickResNo, custId, toSelectMode);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param request HttpServletRequest对象
     * @param request HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSerResNos")
    public HttpRespEnvelope findSerResNos(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifySecureToken(request);
            request.setAttribute("serviceName", reviewService);
            request.setAttribute("methodName", "findSerResNos");
            return super.doList(request, response);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号--顺序选号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param request HttpServletRequest对象
     * @param request HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSerResNo")
    public HttpRespEnvelope findSerResNo(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifySecureToken(request);
            String countryNo = request.getParameter("countryNo");//国家代码
        	String provinceNo = request.getParameter("provinceNo");//省份代码
        	String cityNo = request.getParameter("cityNo");//城市代码
        	PageData<ResNo> pd = this.reviewService.findSerResNos(countryNo, provinceNo, cityNo);
        	if(pd == null || pd.getCount() == 0){
        		throw new HsException(ASRespCode.MW_REGINFO_FW_AVAIL_QUOTA_INVALID);
        	}
        	return new HttpRespEnvelope(pd.getResult().get(0).getEntResNo());
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }

	@Override
	protected IBaseService getEntityService() {
		return reviewService;
	}

}