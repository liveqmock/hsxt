/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.controllers.resoucemanage;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService;
import com.gy.hsxt.common.exception.HsException;

/***
 * 消费者资源统计
 * @Package: com.gy.hsxt.access.web.mcs.controllers.resoucemanage  
 * @ClassName: PersonResStatisticsController 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-1-21 下午12:22:28 
 * @version V1.0
 */
@Controller
@RequestMapping("person_res")
public class PersonResStatisticsController extends BaseController {
    /** 资源配额管理 */
    @Resource
    private IResouceQuotaService iResouceQuotaService;
    
    /**
     * 消费者资源详情
     * @param request
     * @param mcsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/person_res_detail" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope personResDetail(HttpServletRequest request,MCSBase mcsBase) {
        HttpRespEnvelope hre=null;
        
        try
        {
            //验证token
            super.verifySecureToken(request);
            //查询消费者统计详情
            Map<String, Object> rrs= iResouceQuotaService.personResDetail(mcsBase);
            hre=new HttpRespEnvelope(rrs);
        }
        catch (HsException e)
        {
            hre=new HttpRespEnvelope(e);
        }
        
        return hre;
    };
    
    /**
     * 企业下的消费者统计
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/ent_next_person_statistics_page" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope entNextPersonStatisticsPage(HttpServletRequest request,HttpServletResponse response){
        HttpRespEnvelope hre=null;
        
        try
        {
            //验证token
            super.verifySecureToken(request);
            //分页查询
            request.setAttribute("serviceName", iResouceQuotaService);
            request.setAttribute("methodName", "entNextPersonStatisticsPage");
            hre=super.doList(request, response);
        }
        catch (HsException e)
        {
          hre=new HttpRespEnvelope(e);
        }
        
        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        return iResouceQuotaService;
    }

}
