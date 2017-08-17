/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.PlatFormPurchasAuditService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.common.exception.HsException;

/**
 * 平台代购工具复核
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder  
 * @ClassName: PlatFormPurchasAuditController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-27 下午4:19:19 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("auditing")
public class PlatFormPurchasAuditController extends BaseController {

    @Resource
    private PlatFormPurchasAuditService platFormPurchasAuditService;
    
    /**
     * 查询代购订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryproxyorder" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryProxyOrder(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                //企业客户号
                String proxyOrderNo = request.getParameter("proxyOrderNo");
                
                ProxyOrder result = platFormPurchasAuditService.queryPlatProxyOrderById(proxyOrderNo);
                
                httpRespEnvelope = new HttpRespEnvelope(result);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @ResponseBody
    @RequestMapping(value = { "/auditingproxyorder" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope auditingProxyOrder(HttpServletRequest request, @ModelAttribute ProxyOrder  bean) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                
                platFormPurchasAuditService.platProxyOrderAppr(bean);
                
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
        return platFormPurchasAuditService;
    }

}
