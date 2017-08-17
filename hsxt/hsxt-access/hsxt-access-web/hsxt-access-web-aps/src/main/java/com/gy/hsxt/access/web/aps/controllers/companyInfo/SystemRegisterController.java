/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.companyInfo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.companyInfo.ISystemRegisterService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;

/***
 * 实名认证
 * 
 * @Package: com.gy.hsxt.access.web.business.companySystemInfo.controller
 * @ClassName: RealNameAuthController
 * @Description: TODO
 * 
 * @author: liuxy
 * @date: 2015-9-29 上午11:44:01
 * @version V3.0.0
 */
@Controller
@RequestMapping("systemRegister")
public class SystemRegisterController extends BaseController {
    @Resource
    private ISystemRegisterService service;


    /***
     * 查实登记信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/findSysRegisterInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findSysRegisterInfo(HttpServletRequest request) {

        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            Map<String, String> map = new HashMap<String, String>();
            AsEntBaseInfo seb = service.findSysRegisterInfo(request.getParameter("custId"));
//            LocalInfo localInfo = bsService.findSystemInfo();

            map.put("buildDate", seb.getBuildDate());
            map.put("entName", seb.getEntName());
            map.put("entNameEn", seb.getEntNameEn());
            map.put("contactAddr", seb.getContactAddr());
            map.put("entCustId", seb.getEntCustId());
//            map.put("currency", localInfo.getCurrencyCode());

            hre = new HttpRespEnvelope(map);
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
