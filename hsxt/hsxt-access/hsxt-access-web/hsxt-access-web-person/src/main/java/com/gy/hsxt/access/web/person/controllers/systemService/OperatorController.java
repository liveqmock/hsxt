/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.person.controllers.systemService;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.services.systemService.OperatorService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 操作员控制器
 * @Package: com.gy.hsxt.access.web.mcs.controllers.systemmanage  
 * @ClassName: OperatorController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-9 下午12:10:20 
 * @version V1.0
 */
@Controller
@RequestMapping("operator")
public class OperatorController extends BaseController {

    @Resource
    private OperatorService operatorService;
    
    /**
     * 操作员信息
     * @param request
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("get_operator_login_detail")
    public HttpRespEnvelope operatorDetail(HttpServletRequest request, PersonBase personBase,String hs_isCard) throws IOException {
        try
        {
            //操作员登录信息
            Map<String, Object> retMap = operatorService.getOperatorDetail(personBase,hs_isCard);
            return new HttpRespEnvelope(retMap);
        }
        catch (HsException e)
        {
            return new HttpRespEnvelope(e);
        }
    }
    
    @Override
    protected IBaseService getEntityService() {
        return operatorService;
    }

}
