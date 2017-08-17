/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.companyInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.scs.services.common.SCSConfigService;
import com.gy.hsxt.access.web.scs.services.companyInfo.IEntMainInfoService;
import com.gy.hsxt.access.web.scs.services.companyInfo.IImportantInfoService;
import com.gy.hsxt.access.web.scs.services.companyInfo.IRealNameAuthService;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/***
 * 实名认证
 * 
 * @Package: com.gy.hsxt.access.web.business.companySystemInfo.controller
 * @ClassName: RealNameAuthController
 * @Description: TODO
 * 
 * @author: liuxy
 * @date: 2015-9-29 上午11:44:01
 * @version V3.0.0
 */
@Controller
@RequestMapping("realNameAuth")
public class RealNameAuthController extends BaseController {

    @Resource
    private IRealNameAuthService service;

    @Resource
    private IImportantInfoService ifService;
    
    @Resource
    private IEntMainInfoService emsService;

    /***
     * 查实名认证信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/findRealNameAuthInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findRealNameAuthInfo(String entCustId, HttpServletRequest request) {

        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            hre = new HttpRespEnvelope(emsService.findEntMainInfo(entCustId));
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /***
     * 提交实名认证资料
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/submitRealNameAuthData" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope submitApply(@ModelAttribute EntRealnameAuth info, HttpServletRequest request) {

        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            if ((hre = validate(info)) != null)
            {
                return hre;
            }

            service.submit(info);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 验证提交 的数据
     * 
     * @param info
     * @return
     */
    private HttpRespEnvelope validate(EntRealnameAuth info) {
        HttpRespEnvelope hre = null;
        if (StringUtils.isBlank(info.getLrcFacePic()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_FRONT_PIC.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_FRONT_PIC.getDesc());
        }
        else if (StringUtils.isBlank(info.getLrcBackPic()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_BACK_PIC.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_BACK_PIC.getDesc());

        }
        else if (StringUtils.isBlank(info.getLicensePic()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LICENCE_PIC.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LICENCE_PIC.getDesc());

        }
        else if (StringUtils.isBlank(info.getOrgPic()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_OGR_PIC.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_OGR_PIC.getDesc());

        }
        else if (StringUtils.isBlank(info.getTaxPic()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_TAXP_NO.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_TAXP_NO.getDesc());

        }
        return hre;
    }

    /***
     * 查找实名认识的申请单信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/findRealNameAuthApply" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findApplyOrder(String entCustId, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            hre = new HttpRespEnvelope(service.findApplyOrder(entCustId));
        }
        catch (HsException e)
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
