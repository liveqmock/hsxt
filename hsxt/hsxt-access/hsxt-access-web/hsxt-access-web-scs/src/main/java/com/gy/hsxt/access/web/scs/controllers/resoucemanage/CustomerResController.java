/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.resoucemanage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.scs.services.resoucemanage.ICustomerResService;

/***
 * 消费者资源统计控制器
 * 
 * @Package: com.gy.hsxt.access.web.scs.controllers.resoucemanage
 * @ClassName: CustomerResController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-5 下午2:39:29
 * @version V1.0
 */
@Controller
@RequestMapping("customer_sys_res")
public class CustomerResController extends BaseController {

    /**
     * 分页数据验证
     * 
     * @param request
     * @param filterMap
     * @param sortMap
     * @return
     * @see com.gy.hsxt.access.web.common.controller.BaseController#beforeList(javax.servlet.http.HttpServletRequest,
     *      java.util.Map, java.util.Map)
     */
    @Override
    protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            hre = super.checkSecureToken(request);
            if (hre != null)
            {
                return hre;
            }
        }
        catch (Exception e)
        {
            // 异常信息封装
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    // 消费者资源服务接口
    @Autowired
    private ICustomerResService customerResService;

    /**
     * 消费者关联资源统计
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_correlat_statistics", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getCorrelatStatistics(HttpServletRequest request, HttpServletResponse response, String resNo) {

        HttpRespEnvelope hre = null;
        try
        {

            // Token验证
            hre = super.checkSecureToken(request);
            if (hre != null)
            {
                return hre;
            }
            // 返回结果
            Map<String, Object> retMap = customerResService.getCorrelatStatistics(resNo);
            hre = new HttpRespEnvelope(retMap);
        }
        catch (Exception e)
        {
            // 异常信息封装
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        return customerResService;
    }

}
