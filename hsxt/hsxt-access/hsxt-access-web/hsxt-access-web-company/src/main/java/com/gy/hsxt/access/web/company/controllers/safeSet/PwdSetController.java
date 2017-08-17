/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.safeSet;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean;
import com.gy.hsxt.access.web.bean.safeSet.RequestResetTradPwdBean;
import com.gy.hsxt.access.web.bean.safeSet.TradePwdBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.access.web.company.services.safeSet.IPwdSetService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IMemberEnterpriseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.safeSet
 * @ClassName: PwdSetController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-30 下午3:23:51
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("pwdSet")
public class PwdSetController extends BaseController {

    @Autowired
    private CompanyConfigService companyConfigService; // 全局配置文件

    @Resource
    private IPwdSetService pwdSetService; // 系统安全设置
    
    @Resource
    private IMemberEnterpriseService imemberEnterpriseService;

    /**
     * 获取密码配置文件
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_password_config", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getLoginPasswordConfig(HttpServletRequest request) {
        Map<String, Object> tempMap = new HashMap<>();
        HttpRespEnvelope returnHre = null;// 返回数据对象

        try
        {
            // 检验token
            super.verifySecureToken(request);
            // 登录密码长度限制
            tempMap.put("loginPassLength", companyConfigService.getLoginPasswordLength());
            // 交易密码长度限制
            tempMap.put("tradingPasswordLength", companyConfigService.getTradingPasswordLength());

            returnHre = new HttpRespEnvelope(tempMap);
        }
        catch (Exception e)
        {
            returnHre = new HttpRespEnvelope(e);
        }

        return returnHre;
    }

    /**
     * 修改登录密码
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update_login_password", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope updateLoginPassword(HttpServletRequest request, LoginPasswordBean lpb) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、空数据验证
            lpb.validateEmptyData(request);
            //验证企业状态
            verificationEntStatus(lpb.getEntCustId());
            // 3、修改登录密码
            pwdSetService.updateLoginPassword(lpb);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = handleModifyPasswordException(e);
        }
        return hre;
    }
    
    private HttpRespEnvelope handleModifyPasswordException(HsException e){
    	HttpRespEnvelope hre = new HttpRespEnvelope();
    	int errorCode = e.getErrorCode();
    	
    	if(160359 == errorCode || 160108 == errorCode){
    		
    		hre = new HttpRespEnvelope(e.getErrorCode(),"登录密码不正确");
    	}else if(160467 == errorCode){
    		hre = new HttpRespEnvelope(e.getErrorCode(),"账户被锁定");
    	}else{
    		hre = new HttpRespEnvelope(e);
    	}
    	return hre;
    }
    /**
     * 新增交易密码
     * 
     * @param request
     * @param tpb
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add_trading_password", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addTradingPassword(HttpServletRequest request, TradePwdBean tpb) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、空数据验证
            tpb.addValidate();
            //验证企业状态
            verificationEntStatus(tpb.getEntCustId());
            // 3、新增交易密码
            pwdSetService.addTradingPassword(tpb);
            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /***
     * 交易密码修改
     * 
     * @param request
     * @param tpb
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update_trading_password", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope updateTradingPassword(HttpServletRequest request, TradePwdBean tpb) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、空数据验证
            tpb.updateValidate();
            //企业状态验证
            verificationEntStatus(tpb.getEntCustId());
            // 3、修改交易密码
            pwdSetService.updateTradingPassword(tpb);
            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 查找企业信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_ent_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getEntInfo(HttpServletRequest request, CompanyBase companyBase) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、获取信息并返回
            hre = new HttpRespEnvelope(pwdSetService.getEntInfo(companyBase));
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 重置交易密码
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "reset_trading_password", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope resetTradingPassword(HttpServletRequest request, TradePwdBean tpb) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、非空验证
            tpb.resetValidate();
            //企业状态验证
            verificationEntStatus(tpb.getEntCustId());
            // 3、重置交易密码
            pwdSetService.resetTradingPassword(tpb);
            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
            // 异常信息封装
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 申请重置交易密码
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "requested_reset_trading_password", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope requestedResetTradingPassword(HttpServletRequest request, RequestResetTradPwdBean rrtpb) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、非空验证
            rrtpb.validateEmptyData();
            //企业状态验证
            verificationEntStatus(rrtpb.getEntCustId());
            // 3、申请重置交易密码
            pwdSetService.requestedResetTradingPassword(rrtpb);
            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取交易密码重置申请业务文件下载地址
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_tradPwd_request_file", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getTradPwdRequestFile(HttpServletRequest request, RequestResetTradPwdBean rrtpb) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、申请重置交易密码
            hre = new HttpRespEnvelope(companyConfigService.getTradPwdRequestFile());
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取交易密码和密保问题已设置
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_is_safe_set", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getIsSafeSet(HttpServletRequest request, CompanyBase companyBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、交易密码、预留信息设置 是否设置 true:已设置 false:未设置
            Map<String, Object> tmpMap = pwdSetService.getIsSafeSet(companyBase);
            hre = new HttpRespEnvelope(tmpMap);
        }
        catch (Exception e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 获取交易密码和密保问题已设置
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "query_tradPwd_lastApply", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryTradPwdLastApply(HttpServletRequest request, CompanyBase companyBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.verifySecureToken(request);
            // 获取交易密码重置申请最新记录
            Map<String, Object> result = pwdSetService.queryTradPwdLastApply(companyBase);
            hre = new HttpRespEnvelope(result);
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
