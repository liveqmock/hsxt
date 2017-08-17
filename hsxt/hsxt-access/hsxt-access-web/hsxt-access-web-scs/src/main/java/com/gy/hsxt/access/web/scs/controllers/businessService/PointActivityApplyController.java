/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.controllers.businessService;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.businessService.IPointActivityApplyService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("activityapply")
public class PointActivityApplyController extends BaseController {

    @Resource
    private IPointActivityApplyService ipointActivityApplyService;
    
    /** 
     * 查询（参与/停止）活动积分申请详细信息
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
                Map<String,Object> result = ipointActivityApplyService.findActivityApplyDetail(applyId);
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
     * 审批活动申请
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
                ipointActivityApplyService.pointActivityAppr(param);
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
        return ipointActivityApplyService;
    }

}
