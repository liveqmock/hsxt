/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.EntInfoService;
import com.gy.hsxt.access.web.aps.services.toolorder.PlatformPurchasToolService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

/**
 * 平台代购工具
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder  
 * @ClassName: PlatformPurchasToolController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-26 上午11:02:38 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("platform")
public class PlatFormPurchasToolController extends BaseController {

    @Resource
    private PlatformPurchasToolService platformPurchasToolService;
    
    @Resource
    private EntInfoService entInfoService;
    
    /**
     * 查询可以购买的工具类型
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_tooltype" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryToolTypes(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                List<ToolProduct> result = platformPurchasToolService.queryMayBuyToolProduct(CustType.AREA_PLAT.getCode());
                
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
     * 查询该地区某种工具类型的可购买数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_toolnum" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryToolNum(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                //企业客户号
                String custId = request.getParameter("custId");
                //工具类别
                String categoryCode = request.getParameter("categoryCode");
                
                int result = platformPurchasToolService.queryMayBuyToolNum(custId, categoryCode);
                
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
    @RequestMapping(value = { "/query_address" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryAddress(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                //企业客户号
                String custId = request.getParameter("custId");
                
                AsEntAllInfo result = entInfoService.searchEntAllInfo(custId);  
                
                Map<String,Object> contact = new HashMap<String,Object>();
                contact.put("userName", result.getMainInfo().getContactPerson());
                contact.put("mobile", result.getMainInfo().getContactPhone());
                contact.put("address", result.getBaseInfo().getContactAddr());
                contact.put("companlyName", result.getBaseInfo().getEntName());
                
                Map<String,Object> enter = new HashMap<String,Object>();
                enter.put("userName", result.getMainInfo().getContactPerson());
                enter.put("mobile", result.getMainInfo().getContactPhone());
                enter.put("address", result.getMainInfo().getEntRegAddr());
                enter.put("companlyName", result.getMainInfo().getEntName());
                
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("contact", contact);
                map.put("enter", enter);
                
                httpRespEnvelope = new HttpRespEnvelope(map);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 查询互生卡卡样
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_car_style" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryCarStyles(HttpServletRequest request, @ModelAttribute ProxyOrder bean) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                String entResNo = request.getParameter("entResNo");
                List<CardStyle> list = platformPurchasToolService.queryBuyToolCardStyle(entResNo);
                
                httpRespEnvelope = new HttpRespEnvelope(list);
            }
            catch (HsException e)
            {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    /**
     * 提交平台代购订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/addplatproxyorder" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addPlatProxyOrder(HttpServletRequest request, @ModelAttribute ProxyOrder bean) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            try
            { 
                platformPurchasToolService.addPlatProxyOrder(bean);
                
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
        return platformPurchasToolService;
    }

}
