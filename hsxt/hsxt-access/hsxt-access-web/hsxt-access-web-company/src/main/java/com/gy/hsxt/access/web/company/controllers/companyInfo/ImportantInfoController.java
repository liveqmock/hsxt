/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.companyInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.access.web.company.services.companyInfo.IEntMainInfoService;
import com.gy.hsxt.access.web.company.services.companyInfo.IImportantInfoService;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/***
 * 企业的重要信息
 * 
 * @Package: com.gy.hsxt.access.web.business.companySystemInfo.controller
 * @ClassName: ImportantInformationController
 * @Description: TODO
 * 
 * @author: liuxy
 * @date: 2015-9-29 上午11:43:36
 * @version V3.0.0
 */
@Controller
@RequestMapping("importantInfo")
public class ImportantInfoController extends BaseController {

    @Resource
    private IImportantInfoService service;

    @Resource
    private IEntMainInfoService emsService;

    /***
     * 查企业重要信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/findImportantInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findImportantInfo(String entCustId, HttpServletRequest request) {
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
     * 查企业重要信息变更申请单信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/findChangeApplyOrder" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
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

    /***
     * 查企业重要信息变更的详细
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/findEntInfoChangedDetail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findApplyDetail(String applyId, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            hre = new HttpRespEnvelope(service.findApplyDetail(applyId));
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }


    /***
     * 更新
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/submitChangeApply" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope submitChangeApply(@ModelAttribute EntChangeInfo info, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {

            verifySecureToken(request);

            if ((hre = validate(info)) != null)
            {
                return hre;
            }

            service.submitChangeApply(info);

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
    private HttpRespEnvelope validate(EntChangeInfo info) {

        HttpRespEnvelope hre = null;

        if (StringUtils.isBlank(info.getCustNameNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_NAME.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_NAME.getDesc());
        }
        else if (StringUtils.isBlank(info.getCustAddressNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_ADDRESS.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_ADDRESS.getDesc());
        }
        else if (StringUtils.isBlank(info.getLegalRepNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL.getDesc());
        }
        else if (StringUtils.isBlank(info.getLegalRepCreTypeNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_CRE_TYPE.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_CRE_TYPE.getDesc());
        }
        else if (StringUtils.isBlank(info.getLegalRepCreNoNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_CRE_NO.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_CRE_NO.getDesc());
        }
        else if (StringUtils.isBlank(info.getBizLicenseNoNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LICENCE_NO.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_LICENCE_NO.getDesc());
        }
        else if (StringUtils.isBlank(info.getOrganizerNoNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_ORG_NO.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_ORG_NO.getDesc());
        }
        else if (StringUtils.isBlank(info.getTaxpayerNoNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_TAXP_NO.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_TAXP_NO.getDesc());
        }
        else if (StringUtils.isBlank(info.getImptappPic()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_APPLY_PIC.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_APPLY_PIC.getDesc());
        }
        else if (!ValidateUtil.validateMobile(info.getLinkmanMobileNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_MOBILE.getCode(), RespCode.SW_ILLEGAL_MOBILE.getDesc());
        }
        else if (StringUtils.isBlank(info.getLinkmanNew()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ENT_CAHNGE_NOT_EMPTY_CONTACT.getCode(),
                    RespCode.SW_ENT_CAHNGE_NOT_EMPTY_CONTACT.getDesc());
        }

        return hre;
    }

    /***
     * 加载验证码
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/loadVerifiedCode" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope loadVerifiedCode(HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            Random rand = new Random();
            int randNum = rand.nextInt(9999) + 1000;
            hre = new HttpRespEnvelope();
            Map<String, Integer> map = new HashMap<>();
            map.put("verifiedCode", randNum);
            hre.setData(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }



    @Override
    protected IBaseService getEntityService() {
        return service;
    }
}
