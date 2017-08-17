/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.access.web.person.controllers.hsc;


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
import com.gy.hsxt.access.web.person.bean.CardHolder;
import com.gy.hsxt.access.web.person.services.hsc.ICustomerInfoService;
import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-access-web-person
 * 
 *  Package Name    : com.gy.hsxt.access.web.person.hsc.controller
 * 
 *  File Name       : CustomerInfoController.java
 * 
 *  Creation Date   : 2015-9-22 18:54:45  
 *  
 *  updateUse
 * 
 *  Author          : zhangcy
 * 
 *  Purpose         : <br/>
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("customerinfo")
public class CustomerInfoController extends BaseController{

    @Resource
    private ICustomerInfoService customerInfoService;
    
    /**
     * 鏌ヨ鐢ㄦ埛淇℃伅
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_user_info" }, method = { RequestMethod.GET,RequestMethod.POST },produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryUserImportantInfo(HttpServletRequest request){
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if(httpRespEnvelope == null){
            String custId = request.getParameter("custId");
            try {
                CardHolder result = customerInfoService.findCustomerInfoByCustId(custId);
                httpRespEnvelope = new HttpRespEnvelope(result);
            } catch (HsException e) {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    /**
     * 鏌ヨ鐢ㄦ埛閲嶈淇℃伅鍙樻洿鐘舵�
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_important_status" }, method = { RequestMethod.GET,RequestMethod.POST },produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryUserImportantStatus(HttpServletRequest request){
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if(httpRespEnvelope == null){
            String custId = request.getParameter("custId");
            try {
                Map<String,Object> result = customerInfoService.findCustomerImportantStatus(custId);
                httpRespEnvelope = new HttpRespEnvelope(result);
            } catch (HsException e) {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @ResponseBody
    @RequestMapping(value = { "/change_important_status" }, method = { RequestMethod.GET,RequestMethod.POST },produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope changeUserImportantStatus(HttpServletRequest request,@ModelAttribute CardHolder cardHolder){
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if(httpRespEnvelope == null){
            try {
                customerInfoService.changeCustomerImportInfo(cardHolder);
                httpRespEnvelope = new HttpRespEnvelope("1");
            } catch (HsException e) {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }
    
    @Override
    protected IBaseService getEntityService() {
        return customerInfoService;
    }

}
