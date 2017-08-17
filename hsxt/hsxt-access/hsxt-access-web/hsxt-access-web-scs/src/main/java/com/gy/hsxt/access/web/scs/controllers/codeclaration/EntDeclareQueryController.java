package com.gy.hsxt.access.web.scs.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.codeclaration.IEntDeclareQueryService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.controllers.codeclaration
 * @className     : EntDeclareQueryController.java
 * @description   : 企业申报查询
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entDeclareQueryController")
public class EntDeclareQueryController extends BaseController{
	
	@Resource
    private IEntDeclareQueryService enterReportService; //企业申报查询服务类
	
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
     * 
     * 方法名称：查询申报进行步骤
     * 方法描述：依据企业申请编号查询申报进行步骤
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDeclareStep")
    public HttpRespEnvelope findDeclareStep(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID}
            );
            Integer step = this.enterReportService.findDeclareStep(applyId);
            return new HttpRespEnvelope(step);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：删除申报信息
     * 方法描述：依据企业申请编号删除待提交状态的申报信息
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delUnSubmitDeclare")
    public HttpRespEnvelope delUnSubmitDeclare(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID}
            );
            this.enterReportService.delUnSubmitDeclare(applyId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }

	@Override
	protected IBaseService getEntityService() {
		return enterReportService;
	}

}
