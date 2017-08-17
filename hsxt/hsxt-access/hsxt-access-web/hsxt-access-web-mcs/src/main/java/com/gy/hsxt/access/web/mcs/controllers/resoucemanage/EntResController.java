/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.resoucemanage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.resoucemanage.IEntService;
import com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

/***
 * 企业资源管理控制器
 * 
 * @Package: com.gy.hsxt.access.web.mcs.controllers.resoucemanage
 * @ClassName: EntResController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-21 下午12:20:57
 * @version V1.0
 */
@Controller
@RequestMapping("ent_res")
public class EntResController extends BaseController {
    /** 资源配额管理 */
    @Resource
    private IResouceQuotaService iResouceQuotaService;

    /** 企业服务 */
    @Resource
    private IEntService iEntService;

    /**
     * 企业资源一览(2 成员企业;3 托管企业;4 服务公司)
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/ent_res_page" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope entResPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证token
            super.verifySecureToken(request);
            // 分页查询
            request.setAttribute("serviceName", iResouceQuotaService);
            request.setAttribute("methodName", "entResPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取企业明细
     * @param request
     * @param operateEntCustId
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/get_ent_all_info" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getEntAllInfo(HttpServletRequest request, String operateEntCustId) throws HsException {
        HttpRespEnvelope hre = null;
        try
        {
            // 验证token
            super.verifySecureToken(request);
            // 验证数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { operateEntCustId, RespCode.GL_PARAM_ERROR });
            // 企业明细
            AsEntAllInfo aeai = iEntService.getEntAllInfo(operateEntCustId);
            hre = new HttpRespEnvelope(aeai);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取企业银行列表
     * @param request
     * @param entCustId
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/get_ent_bank_list" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getEntBankList(HttpServletRequest request, String operateEntCustId) throws HsException {
        HttpRespEnvelope hre = null;
        try
        {
            // 验证token
            super.verifySecureToken(request);
            // 验证数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { operateEntCustId, RespCode.GL_PARAM_ERROR });
            // 银行列表
            List<AsBankAcctInfo> abciList = iEntService.getEntBankList(operateEntCustId);
            hre = new HttpRespEnvelope(abciList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    } 

    @Override
    protected IBaseService getEntityService() {
        return iResouceQuotaService;
    }

}
