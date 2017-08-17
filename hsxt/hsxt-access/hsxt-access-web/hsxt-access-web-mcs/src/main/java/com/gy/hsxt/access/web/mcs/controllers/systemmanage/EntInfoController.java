/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.controllers.systemmanage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.mcs.services.systemmanage.EntInfoService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("entinfo")
public class EntInfoController extends BaseController {

	@Resource
    private EntInfoService entInfoService;
    
    /**
     * 查询地区平台信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/platform" ,method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope update(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            AsEntInfo result = entInfoService.searchRegionalPlatform();
            return new HttpRespEnvelope(result);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    @Override
    protected IBaseService getEntityService() {
        return entInfoService;
    }

}
