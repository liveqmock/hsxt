/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

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
import com.gy.hsxt.access.web.company.services.systemBusiness.IDeliverOrderService;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("deliverorder")
public class DeliverOrderController extends BaseController {

    @Resource
    private IDeliverOrderService ideliverOrderService;
    
    /**
     * 确认发货单收货
     * @param request
     * @param param
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/tool_accept_confirm" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope toolAcceptConfirm(HttpServletRequest request,@ModelAttribute Shipping  param) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {   
            try
            {
                ideliverOrderService.toolAcceptConfirm(param);
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
        return ideliverOrderService;
    }

}
