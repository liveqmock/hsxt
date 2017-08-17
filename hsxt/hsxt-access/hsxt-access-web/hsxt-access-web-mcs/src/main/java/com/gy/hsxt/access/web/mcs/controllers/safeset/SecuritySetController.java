/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.safeset;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.safeset.PwdQuestionBean;
import com.gy.hsxt.access.web.bean.safeset.ReserveInfoBaen;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.mcs.services.safeset.ISecuritySetService;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.safeSet
 * @ClassName: SecuritySetController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-30 下午3:24:26
 * @version V1.0
 */
@Controller
@RequestMapping("securitySet")
public class SecuritySetController extends BaseController {

    @Resource
    private ISecuritySetService securitySetService; // 密保设置服务类

    /**
     * 根据互生号查找密保问题
     * @param request
     * @param resNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_question_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getQuestionList(HttpServletRequest request, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、 Token验证
            super.verifySecureToken(request);
            
            // 2、获取密保列表
            hre = new HttpRespEnvelope(securitySetService.getQuestionList(mcsBase));
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 设置密保问题
     * @param request
     * @param pqb
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "set_pwd_question", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope setPWDQuestion(HttpServletRequest request, PwdQuestionBean pqb) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            
            // 2、非空验证
            pqb.validateEmptyData();
            
            // 3、设置密保答案
            securitySetService.setQuestion(pqb);
            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取预留信息
     * @param request
     * @param custId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_reserve_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getReserveInfo(HttpServletRequest request, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            
            // 2、返回预留信息内容
            hre = new HttpRespEnvelope(securitySetService.getReserveInfo(mcsBase));
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 设置预留信息
     * @param request
     * @param rib
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "set_reserve_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope setReserveInfo(HttpServletRequest request, ReserveInfoBaen rib) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            
            // 2、非空验证
            rib.validateEmptyData();
            
            // 3、设置预留信息
            securitySetService.setReserveInfo(rib);

            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 修改预留信息
     * @param request
     * @param rib
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update_reserve_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope updateReserveInfo(HttpServletRequest request, ReserveInfoBaen rib) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            
            // 2、非空验证
            rib.validateEmptyData();
            
            // 3、设置预留信息
            securitySetService.updateReserveInfo(rib);
            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }
}
