/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.safeSet;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.bean.VerificationCodeType;
import com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean;
import com.gy.hsxt.access.web.bean.safeSet.RequestResetTradPwdBean;
import com.gy.hsxt.access.web.bean.safeSet.TradePwdBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.scs.services.common.SCSConfigService;
import com.gy.hsxt.access.web.scs.services.safeSet.IPwdSetService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.scs.controllers.safeSet
 * @ClassName: PwdSetController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-2 下午8:19:37
 * @version V1.0
 */
@Controller
@RequestMapping("pwdSet")
public class PwdSetController extends BaseController {

    @Autowired
    private SCSConfigService scsConfigService;

    @Autowired
    private IPwdSetService pwdSetService; // 系统安全设置
    
    @Resource
    private RedisUtil<String> changeRedisUtil;

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
            //检验token
            super.verifySecureToken(request);
            // 登录密码长度限制
            tempMap.put("loginPassLength", scsConfigService.getLoginPasswordLength());
            // 交易密码长度限制
            tempMap.put("tradingPasswordLength", scsConfigService.getTradingPasswordLength());
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
//    	String errorMsg = e.getMessage();
//    	String funName = "handleModifyPasswordException";
//    	StringBuffer msg = new StringBuffer();
//    	msg.append("处理修改登录密码Exception时报错,传入参数信息  e["+JSON.toJSONString(e)+"] "+NEWLINE);
    	if(160359 == errorCode || 160108 == errorCode){
    		/**保留
     		try {
        			if(!StringUtils.isEmptyTrim(errorMsg) && errorMsg.contains(",")){
            			String[] msgInfo = errorMsg.split(",");
            			if(msgInfo.length > 1){
            				int loginFailTimes = Integer.parseInt(msgInfo[0]);
            				int maxFailTimes = Integer.parseInt(msgInfo[1]);
            			}
            		}
            		
    			} catch (Exception e2) {
    				SystemLog.error(MOUDLENAME, funName, msg.toString(), e2);
    			}
        		*/
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
            // 3、修改交易密码
            pwdSetService.updateTradingPassword(tpb);
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
     * 查找企业信息
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_ent_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getEntInfo(HttpServletRequest request, SCSBase scsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、获取信息并返回
            hre = new HttpRespEnvelope(pwdSetService.getEntInfo(scsBase));
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
            // 3、重置交易密码
            pwdSetService.resetTradingPassword(tpb);
            hre = new HttpRespEnvelope();
        }
        catch (Exception e)
        {
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
            // 3、验证码验证
            this.VerificationCode(rrtpb);
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
     * 验证码验证
     */
    public void VerificationCode(RequestResetTradPwdBean rrtpb) throws HsException {
    	// 操作员编号
        String key = rrtpb.getCustId()+"_"+VerificationCodeType.tradePwdResetApply;
        // 获取验证码
        Object vCode = changeRedisUtil.get(key, Object.class);
        
        // 判断为空
        if (null == vCode)
        {
            throw new HsException(RespCode.SW_VERIFICATION_CODE_NULL);
        }
        // 判断相等
        if (!rrtpb.getValidateCode().toUpperCase().equals(vCode.toString().toUpperCase()))
        {
            throw new HsException(RespCode.SW_VERIFICATION_CODE_ERROR);
        }
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
            hre = new HttpRespEnvelope(scsConfigService.getTradPwdRequestFile());
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
    public HttpRespEnvelope getIsSafeSet(HttpServletRequest request, SCSBase scsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            // 2、交易密码、预留信息设置 是否设置 true:已设置 false:未设置
            Map<String, Object> retMap = pwdSetService.getIsSafeSet(scsBase);
            hre = new HttpRespEnvelope(retMap);
        }
        catch (HsException e)
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
    public HttpRespEnvelope queryTradPwdLastApply(HttpServletRequest request, SCSBase scsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.verifySecureToken(request);
            // 获取交易密码重置申请最新记录
            Map<String, Object> result = pwdSetService.queryTradPwdLastApply(scsBase);
            hre = new HttpRespEnvelope(result);
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
