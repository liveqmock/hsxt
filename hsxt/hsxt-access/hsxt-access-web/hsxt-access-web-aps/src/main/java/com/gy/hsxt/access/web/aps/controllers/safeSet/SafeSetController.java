/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.safeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.companyInfo.ISafeSetService;
import com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

@Controller
@RequestMapping("safeSetController")
public class SafeSetController extends BaseController {
    
    @Resource
    private ISafeSetService isService;

    @RequestMapping(value = { "/updatePwd" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope updatePwd(HttpServletRequest request,LoginPasswordBean lpb) {
        HttpRespEnvelope hre = null;
        
        try
        {
        	// 1、Token验证
            super.verifySecureToken(request);
            // 2、空数据验证
            lpb.validateEmptyData(request);
            // 3、修改登录密码
            isService.updateLoginPassword(lpb);
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
    
    
    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return isService;
    }

}
