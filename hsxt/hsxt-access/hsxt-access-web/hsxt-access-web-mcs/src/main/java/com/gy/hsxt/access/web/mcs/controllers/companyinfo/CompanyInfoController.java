/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.companyinfo;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.companyinfo.ICompanyInfoService;
import com.gy.hsxt.access.web.mcs.services.companyinfo.IUCBankfoService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.controllers.companyinfo
 * @className     : CompanyInfoController.java
 * @description   : 企业系统信息
 * @author        : maocy
 * @createDate    : 2016-01-12
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("companyInfoController")
public class CompanyInfoController extends BaseController {
	
	@Resource
    private IUCBankfoService ucBankService;//银行信息服务类
	
	@Resource
    private ICompanyInfoService companyService;//企业信息服务类
	
	/**用户类型*/
	private final static String USER_TYPE = "4";
	
	/**
     * 
     * 方法名称：查询信息
     * 方法描述：企业系统信息-查询信息
     * @param request HttpServletRequest对象
     * @param entCustId  企业客户号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findEntAllInfo")
    public HttpRespEnvelope findEntAllInfo(HttpServletRequest request, String entCustId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {entCustId, RespCode.MW_OPRATOR_ENTCUSNTID}
            );
            AsEntExtendInfo info = this.companyService.findEntAllInfo(entCustId);
            return new HttpRespEnvelope(info);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存企业联系信息
     * 方法描述：企业系统信息-保存企业联系信息
     * @param request HttpServletRequest对象
     * @param entInfo 企业信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateLinkInfo")
    public HttpRespEnvelope updateLinkInfo(HttpServletRequest request, @ModelAttribute AsEntExtendInfo entInfo) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//操作员客户号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {custId, RespCode.MW_OPRATOR_OPTCUSTID},
                new Object[] {entInfo.getEntCustId(), RespCode.MW_OPRATOR_ENTCUSNTID}
            //    new Object[] {entInfo.getContactProxy(), RespCode.MW_ENT_LINKINFO_CERT_INVALID}
            );
            //长度验证
            if(!StringUtils.isBlank(entInfo.getContactAddr())){
                RequestUtil.verifyParamsLength(
                    new Object[] {entInfo.getContactAddr(), 2, 128, RespCode.MW_ENT_LINKINFO_ADDRESS_LENGTH_INVALID}
                );
            }
            this.companyService.updateEntExtInfo(entInfo, custId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存企业信息
     * 方法描述：企业系统信息-保存企业信息
     * @param request HttpServletRequest对象
     * @param entInfo 企业信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateEntExtInfo")
    public HttpRespEnvelope updateEntExtInfo(HttpServletRequest request, @ModelAttribute AsEntExtendInfo entInfo) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//操作员客户号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {custId, RespCode.MW_OPRATOR_OPTCUSTID},
                new Object[] {entInfo.getEntCustId(), RespCode.MW_OPRATOR_ENTCUSNTID},
                new Object[] {entInfo.getNature(), RespCode.MW_BIZREG_ENTTYPE_INVALID},
                new Object[] {entInfo.getBuildDate(), RespCode.MW_BIZREG_ESTADATE_FORM_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {entInfo.getNature(), 0, 20, RespCode.MW_BIZREG_ENTTYPE_LENGTH_INVALID},
                new Object[] {entInfo.getEndDate(), 0, 50, RespCode.MW_BIZREG_LIMITDATE_LENGTH_INVALID},
                new Object[] {entInfo.getBusinessScope(), 0, 300, RespCode.MW_ENT_FILING_DEALAREA_LENGTH_INVALID}
            );
            this.companyService.updateEntExtInfo(entInfo, custId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：添加银行卡
     * 方法描述：企业系统信息-添加银行卡
     * @param request HttpServletRequest对象
     * @param acctInfo  银行账户对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBank")
    public HttpRespEnvelope addBank(HttpServletRequest request, @ModelAttribute AsBankAcctInfo acctInfo) {
        try {
        	String resNo = super.verifyPointNo(request);
            String entCustId = request.getParameter("entCustId");//企业客户号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {entCustId, RespCode.MW_OPRATOR_ENTCUSNTID},
                new Object[] {acctInfo.getBankAccNo(), RespCode.MW_BANK_NO_INVALID},
                new Object[] {acctInfo.getBankCode(), RespCode.MW_BANKINFO_BANKCODE_INVALID},
                new Object[] {acctInfo.getProvinceNo(), ASRespCode.MW_BANKINFO_PROVINCENO_INVALID},
                new Object[] {acctInfo.getCityNo(), ASRespCode.MW_BANKINFO_CITYNO_INVALID}
            );
            acctInfo.setResNo(resNo);
            acctInfo.setCustId(entCustId);
            this.ucBankService.addBank(acctInfo, USER_TYPE);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：删除银行卡
     * 方法描述：企业系统信息-删除银行卡
     * @param request HttpServletRequest对象
     * @param accId 银行卡编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delBank")
    public HttpRespEnvelope delBank(HttpServletRequest request, String accId) {
        try {
        	super.verifySecureToken(request);
        	Long accId_ = CommonUtils.toLong(accId);
        	//非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {accId_, RespCode.MW_BANKINFO_ACCID_INVALID}
            );
            this.ucBankService.delBank(accId_, USER_TYPE);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询银行卡
     * 方法描述：企业系统信息-查询银行卡
     * @param request HttpServletRequest对象
     * @param accId 银行卡编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findBanksByCustId")
    public HttpRespEnvelope findBanksByCustId(HttpServletRequest request, String custId) {
        try {
            super.verifySecureToken(request);
            String entCustId = request.getParameter("entCustId");//企业客户号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {entCustId, RespCode.MW_OPRATOR_ENTCUSNTID}
            );
            List<AsBankAcctInfo> list = this.ucBankService.findBanksByCustId(entCustId, USER_TYPE);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
	
	@Override
	protected IBaseService getEntityService() {
		return null;
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
            this.companyService.checkEmailCode(param);
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
    	HttpRespEnvelope href = null;
    	try{
    		email=request.getParameter("email");
    		//非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {email, ASRespCode.PW_EMAIL_INVALID},
            	new Object[] {userName, ASRespCode.PW_OPTCUSTID_INVALID},
            	new Object[] {entResNo, ASRespCode.MW_OPRATOR_ENTRESNO}
            );
    		companyService.validEmail(email, userName, entResNo, "4");
    		return new HttpRespEnvelope();
    	}catch(HsException e){
    		if(160460 == e.getErrorCode()){
    			href = new HttpRespEnvelope(e.getMessage());
    		}else{
    			href = new HttpRespEnvelope(e);
    		}
    	}
    	return href;
	}
}
