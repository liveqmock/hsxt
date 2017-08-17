/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.companyInfo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.access.web.company.services.companyInfo.IEntBaseInfoService;
import com.gy.hsxt.access.web.company.services.companyInfo.IEntMainInfoService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 联系信息
 * 
 * @Package: com.gy.hsxt.access.web.business.companySystemInfo.controller
 * @ClassName: ContactInformation
 * @Description: TODO
 * 
 * @author: liuxy
 * @date: 2015-9-29 上午11:42:58
 * @version V3.0.0
 */
@Controller
@RequestMapping("contactInfo")
public class ContactInformationController extends BaseController {

    @Resource
    private IEntBaseInfoService ebsService;
    @Resource
    private IEntMainInfoService emsService;

    /***
     * 查找联系信息
     * 
     * @param custId
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { "/findContactInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findContactInfo(String entCustId, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            verifySecureToken(request);
            AsEntMainInfo info = emsService.findEntMainInfo(entCustId);
            AsEntBaseInfo binfo = ebsService.findEntBaseInfo(entCustId);
            
            Map<String,Object> map = new HashMap<>();
            map.put("authProxyFile", info.getAuthProxyFile());
            map.put("contactPerson", info.getContactPerson());
            map.put("contactPhone", info.getContactPhone());
            String jsonStr=JsonUtil.createJsonString(binfo);
            map.putAll(JsonUtil.getObject(jsonStr, Map.class));
            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /***
     * 修改企业信息
     * 
     * @param map
     * @return
     * @throws HsException
     */
    @RequestMapping(value = { "/updateContactInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope updateContactInfo(@ModelAttribute AsEntBaseInfo srInfo,String custId,String entCustId,String authProxyFile, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);

            if ((hre = validate(srInfo)) != null)
            {
                return hre;
            }

            ebsService.updateEntBaseInfo(srInfo,custId);
            
            if(!StringUtils.isEmpty(authProxyFile)){
                AsEntMainInfo info = new AsEntMainInfo();
                info.setAuthProxyFile(authProxyFile);
                emsService.updateAuthProxyFile(authProxyFile, custId, entCustId);
            }
            
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

    /**
     * 校验邮件
     * 
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/checkEmailCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope checkEmailCode(HttpServletRequest request,String param) {
        HttpRespEnvelope hre = null;
        try
        {
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { param, ASRespCode.AS_PARAM_INVALID } // 验证参数
                    );

            // 执行验证邮箱
            this.emsService.checkEmailCode(param);
            hre = new HttpRespEnvelope("恭喜您，验证通过，邮箱绑定成功！");
        }
        catch (HsException e)
        {
        	if(160460 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getMessage());
        	}else if(160137 == e.getErrorCode()){
        		hre = new HttpRespEnvelope("邮件已过期");
        	}else{
        		hre = new HttpRespEnvelope(e);
        	}
            
        }

        return hre;
    }
    /**
     * 
     * 方法名称：发送验证邮件
     * 方法描述：企业系统信息-验证邮箱
     * @param request HttpServletRequest对象
     * @param accId 银行卡编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/validEmail")
	public HttpRespEnvelope val(HttpServletRequest request, @ModelAttribute String email, String userName, String entResNo){
    	HttpRespEnvelope hre = null;
    	try{
    		email=request.getParameter("email");
    		//非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {email, ASRespCode.PW_EMAIL_INVALID},
            	new Object[] {userName, ASRespCode.PW_OPTCUSTID_INVALID},
            	new Object[] {entResNo, ASRespCode.MW_OPRATOR_ENTRESNO}
            );
            emsService.validEmail(email, userName, entResNo, "4");
    		return new HttpRespEnvelope();
    	}catch(HsException e){
    		if(160460 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getMessage());
    		}else{
    			hre = new HttpRespEnvelope(e);
    		}
    	}
    	return hre;
	}

    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }


}
