/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.safeset;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.safeset.LoginPasswordBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.mcs.services.common.MCSConfigService;
import com.gy.hsxt.access.web.mcs.services.safeset.IPwdSetService;
import com.gy.hsxt.common.exception.HsException;

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
@Controller
@RequestMapping("pwdSet")
public class PwdSetController extends BaseController {

    @Autowired
    private MCSConfigService mcsConfigService; // 全局配置文件

    @Resource
    private IPwdSetService pwdSetService; // 系统安全设置

    /**
     * 获取密码配置文件
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
            tempMap.put("loginPassLength", mcsConfigService.getLoginPasswordLength());
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
            lpb.validateEmptyData();
            
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
 //   	String errorMsg = e.getMessage();
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
     * 获取交易密码和密保问题已设置
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_is_safe_set", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getIsSafeSet(HttpServletRequest request, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、Token验证
            super.verifySecureToken(request);
            
            // 2、交易密码、预留信息设置 是否设置 true:已设置 false:未设置
            Map<String, Object> tmpMap = pwdSetService.getIsSafeSet(mcsBase);
            hre = new HttpRespEnvelope(tmpMap);
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
