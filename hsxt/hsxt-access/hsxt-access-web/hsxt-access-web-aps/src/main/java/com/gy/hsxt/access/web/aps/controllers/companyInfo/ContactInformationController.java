/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.companyInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.companyInfo.IContactInfoService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;

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
    private IContactInfoService service;

  

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
            this.service.checkEmailCode(param);
            hre = new HttpRespEnvelope("恭喜您，验证通过，邮箱绑定成功！");
        }
        catch (HsException e)
        {
        	if(160460 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getMessage());
        	}else if(160137 == e.getErrorCode()){
        		hre = new HttpRespEnvelope("邮件已过期");
        	}
        	else{
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
            service.validEmail(email, userName, entResNo, "4");
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
        return service;
    }

}
