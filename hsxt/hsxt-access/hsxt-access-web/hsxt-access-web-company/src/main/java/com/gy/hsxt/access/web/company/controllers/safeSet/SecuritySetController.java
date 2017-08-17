/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.safeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.safeSet.PwdQuestionBean;
import com.gy.hsxt.access.web.bean.safeSet.ReserveInfoBaen;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.company.services.safeSet.ISecuritySetService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IMemberEnterpriseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

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
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("securitySet")
public class SecuritySetController extends BaseController {

    @Resource
    private ISecuritySetService securitySetService; // 密保设置服务类
    
    @Resource
    private IMemberEnterpriseService imemberEnterpriseService;

    /**
     * 根据互生号查找密保问题
     * 
     * @param request
     * @param resNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_question_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getQuestionList(HttpServletRequest request, CompanyBase companyBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、 Token验证
            super.verifySecureToken(request);
            // 2、获取密保列表
            hre = new HttpRespEnvelope(securitySetService.getQuestionList(companyBase));
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 设置密保问题
     * 
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
            //企业状态验证
            verificationEntStatus(pqb.getEntCustId());
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
     * 
     * @param request
     * @param custId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_reserve_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getReserveInfo(HttpServletRequest request, CompanyBase companyBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、返回预留信息内容
            hre = new HttpRespEnvelope(securitySetService.getReserveInfo(companyBase));
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 设置预留信息
     * 
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
            //验证企业状态
            verificationEntStatus(rib.getEntCustId());
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
     * 
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
            //验证企业状态
            verificationEntStatus(rib.getEntCustId());
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

    /**
     * 验证企业状态
     * 
     * @param custId 企业客户编号
     * @throws HsException
     */
    public void verificationEntStatus(String entCustId) throws HsException
    {
        AsEntStatusInfo status = imemberEnterpriseService.searchEntStatusInfo(entCustId);
        if (status != null)
        {
            switch (status.getBaseStatus())
            {
            case 5:
                throw new HsException(ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getCode(),
                        ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getDesc());
            case 8:
                throw new HsException(ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getCode(),
                        ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getDesc());
            }
        }
    }
    
    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }
}
