/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.infomanage;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.infomanage.IMembercompApprovalService;
import com.gy.hsxt.access.web.aps.services.infomanage.InfoManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 成员企业资格注销申请审批
 * 
 * @Package: com.gy.hsxt.access.web.scs.controllers.businessService  
 * @ClassName: MembercompApprovalController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-3 下午5:25:05 
 * @version V3.0.0
 */
@Controller
@RequestMapping("membercompquit")
@SuppressWarnings("rawtypes")
public class MembercompApprovalController extends InfoManageBaseController {

    @Resource
    private IMembercompApprovalService iMembercompApprovalService;
    
    /** 
     * 查询注销申请详细信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_detail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryDetail(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            String applyId = request.getParameter("applyId");
            try
            { 
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {applyId, RespCode.SW_ENT_CYQYZGZX_APPLYID_.getCode(), RespCode.SW_ENT_CYQYZGZX_APPLYID_.getDesc()}
                        
                );
                
                Map<String,Object> result = iMembercompApprovalService.findApprovalDetail(applyId);
                
                httpRespEnvelope = new HttpRespEnvelope(result);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 审批注销申请
     * @param request
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/approval" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope approval(HttpServletRequest request,@ModelAttribute ApprParam param) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            {
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                    new Object[] {param.getApplyId(), RespCode.SW_ENT_CYQYZGZX_APPLYID_.getCode(), RespCode.SW_ENT_CYQYZGZX_APPLYID_.getDesc()},
                    new Object[] {param.getIsPass(), RespCode.SW_ENT_CYQYZGZX_ISPASS_.getCode(), RespCode.SW_ENT_CYQYZGZX_ISPASS_.getDesc()}
                
                );
                
                iMembercompApprovalService.apprMemberQuit(param);
                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 复核注销申请
     * @param request
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/review" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope review(HttpServletRequest request,@ModelAttribute ApprParam param) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            {
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {param.getApplyId(), RespCode.SW_ENT_CYQYZGZX_APPLYID_.getCode(), RespCode.SW_ENT_CYQYZGZX_APPLYID_.getDesc()},
                        new Object[] {param.getIsPass(), RespCode.SW_ENT_CYQYZGZX_ISPASS_.getCode(), RespCode.SW_ENT_CYQYZGZX_ISPASS_.getDesc()}
                        
                        );
                
                iMembercompApprovalService.reviewMemberQuit(param);
                httpRespEnvelope = new HttpRespEnvelope();
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @Override
    protected IBaseService getEntityService() {
        return iMembercompApprovalService;
    }

    @Override
    protected InfoManageService getInfoManageService() {
        return iMembercompApprovalService;
    }

}
