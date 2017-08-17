/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.companyInfo;

import java.util.HashMap;
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
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.scs.services.companyInfo.IEntBaseInfoService;
import com.gy.hsxt.access.web.scs.services.companyInfo.IEntMainInfoService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 工商登记信息
 * 
 * @Package: com.gy.hsxt.access.web.business.companySystemInfo.controller
 * @ClassName: SaicRegisterController
 * @Description: TODO
 * 
 * @author: liuxy
 * @date: 2015-9-29 上午11:44:16
 * @version V3.0.0
 */

@Controller
@RequestMapping("saicRegister")
public class SaicRegisterController extends BaseController {
    @Resource
    private IEntBaseInfoService ebsService;
    @Resource
    private IEntMainInfoService emsService;

    /***
     * 获得在工商局注册信息
     * 
     * @param custId
     * @return
     * @throws HsException
     */

    @RequestMapping(value = { "/findSaicRegisterInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findSaicRegisterInfo(String entCustId, HttpServletRequest request) {

        HttpRespEnvelope hre = null;
        String jsonStr=null;
        try
        {
            verifySecureToken(request);
            
            AsEntBaseInfo  bInfo =  ebsService.findEntBaseInfo(entCustId);
            AsEntMainInfo eInfo = emsService.findEntMainInfo(entCustId);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("bInfo", bInfo);
            map.put("eInfo", eInfo);
            jsonStr=JsonUtil.createJsonString(bInfo);
            jsonStr=JsonUtil.putJsonValue(jsonStr, "creName", eInfo.getCreName());
            jsonStr=JsonUtil.putJsonValue(jsonStr, "contactPhone", eInfo.getContactPhone());
            jsonStr=JsonUtil.putJsonValue(jsonStr, "busiLicenseNo", eInfo.getBusiLicenseNo());
            jsonStr=JsonUtil.putJsonValue(jsonStr, "orgCodeNo", eInfo.getOrgCodeNo());
            jsonStr=JsonUtil.putJsonValue(jsonStr, "taxNo", eInfo.getTaxNo());
            
            hre = new HttpRespEnvelope(JsonUtil.getObject(jsonStr, Map.class));
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /***
     * 修改在工商局注册的信息
     * 
     * @param map
     * @return
     * @throws HsException
     */
    @RequestMapping(value = { "/updateEntBaseInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope updateEntBaseInfo(@ModelAttribute AsEntBaseInfo srInfo,String custId,
            HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);

            if ((hre = validate(srInfo)) != null)
            {
                return hre;
            }

            ebsService.updateEntBaseInfo(srInfo,custId);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    private HttpRespEnvelope validate(AsEntBaseInfo info) {
        HttpRespEnvelope hre = null;
        // 联系人信息
        if (StringUtils.isBlank(info.getPostCode()) && ValidateUtil.validatePostcode(info.getPostCode()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_POSTCODE.getCode(), RespCode.SW_ILLEGAL_POSTCODE.getDesc());
        }
        else if (StringUtils.isBlank(info.getWebSite()) && ValidateUtil.validateWebsite(info.getWebSite()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_WEBSITE.getCode(), RespCode.SW_ILLEGAL_WEBSITE.getDesc());
        }
        else if (StringUtils.isBlank(info.getContactEmail()) && ValidateUtil.validateMail(info.getContactEmail()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_MAIL.getCode(), RespCode.SW_ILLEGAL_MAIL.getDesc());
        }
        else if (StringUtils.isBlank(info.getOfficeTel()) && ValidateUtil.validateOfficePhone(info.getOfficeTel()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_OFFICE_PHONE.getCode(), RespCode.SW_ILLEGAL_OFFICE_PHONE
                    .getDesc());
        }
        else if (StringUtils.isBlank(info.getOfficeFax()) && ValidateUtil.validateFax(info.getOfficeFax()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_FIX.getCode(), RespCode.SW_ILLEGAL_FIX.getDesc());
        }
        else if (StringUtils.isBlank(info.getOfficeQq()) && ValidateUtil.validateQQ(info.getOfficeQq()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_QQ.getCode(), RespCode.SW_ILLEGAL_QQ.getDesc());
        }
        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        return null;
    }

}
