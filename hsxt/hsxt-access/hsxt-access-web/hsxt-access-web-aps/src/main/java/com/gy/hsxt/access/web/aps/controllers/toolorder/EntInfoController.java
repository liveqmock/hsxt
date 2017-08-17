/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.EntInfoService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 查询企业信息
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder  
 * @ClassName: EntInfoController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-26 上午11:02:07 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("entinfo")
public class EntInfoController extends BaseController {

    @Resource
    private EntInfoService entInfoService;
    
    /**
     * 查询企业重要信息(根据客户号)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_entmain" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryMainInfoByCustId(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            String entCustId = request.getParameter("entCustId");
            try
            { 
                AsEntMainInfo result = entInfoService.searchEntMainInfo(entCustId);
                
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
     * 查询企业一般信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_entbase" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryBaseInfoByCustId(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            String entCustId = request.getParameter("entCustId");
            try
            { 
                AsEntBaseInfo result = entInfoService.searchEntBaseInfo(entCustId);
                
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
     * 查询企业所有信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_entall" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryAllInfoByCustId(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            String entCustId = request.getParameter("entCustId");
            try
            { 
                AsEntAllInfo result = entInfoService.searchEntAllInfo(entCustId);
                
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
     * 查询企业重要信息(根据客户号)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_entmain_byresno" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryMainInfoByResNo(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null)
        {
            String resNo = request.getParameter("resNo");
            try
            { 
                //非空验证
                RequestUtil.verifyParamsIsNotEmpty(
                        
                        new Object[] {resNo, RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getCode(), RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getDesc()}
                        
                );
                
                AsEntMainInfo result = entInfoService.searchEntMainInfoByResNo(resNo);
                
                httpRespEnvelope = new HttpRespEnvelope(result);
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
        return entInfoService;
    }

}
