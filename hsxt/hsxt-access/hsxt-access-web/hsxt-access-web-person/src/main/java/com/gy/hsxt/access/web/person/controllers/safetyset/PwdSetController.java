/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.safetyset;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdReset;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdResetCheck;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService;
import com.gy.hsxt.common.exception.HsException;

/***
 * 系统安全设置-密码设置控制器
 * 
 * @Package: com.gy.hsxt.access.web.person.controllers.safetyset
 * @ClassName: PwdSetController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-10 下午3:21:41
 * @version V1.0
 */
@Controller
@RequestMapping("pwdSet")
public class PwdSetController extends BaseController {
    @Resource
    private PersonConfigService personConfigSevice;
    @Autowired
    private IPwdSetService pwdSetService; // 系统安全设置

    /**
     * 重置交易密码===用户信息验证
     * 
     * @param dprc
     * @param request
     * @return
     */
    @RequestMapping(value = { "/check_userinfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope checkUserinfo(DealPwdResetCheck dprc, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.verifySecureToken(request);
            // 参数非空验证
//            dprc.checkEmptyData();
            // 重置密码验证
            String result = pwdSetService.checkUserinfo(dprc);
            hre = new HttpRespEnvelope(result);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 重置交易密码===提交重置密码
     * 
     * @param dpr
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/reset_tradePwd" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope resetTradePwd(DealPwdReset dpr, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.verifySecureToken(request);
            // 参数验证
            dpr.checkData();
            // 重置交易密码
            pwdSetService.resetTradePwd(dpr);
            hre = new HttpRespEnvelope();
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
