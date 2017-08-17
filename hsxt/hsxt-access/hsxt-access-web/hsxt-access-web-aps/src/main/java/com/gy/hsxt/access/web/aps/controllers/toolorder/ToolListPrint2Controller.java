/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.ToolListPrintService;
import com.gy.hsxt.access.web.bean.PrintDetailInfo;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具清单打印
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder  
 * @ClassName: ToolListPrintController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-26 上午11:03:06 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("toollistprint")
public class ToolListPrint2Controller extends BaseController {

    @Resource
    private ToolListPrintService toolListPrintService;
    
    @Override
    protected IBaseService getEntityService() {
        return toolListPrintService;
    }
    
    @ResponseBody
    @RequestMapping(value = "/findPrintDetailById")
    public HttpRespEnvelope findPrintDetailById(HttpServletRequest request, String shippingType,String shippingId) {
    	try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {shippingId, ASRespCode.APS_TOOL_SHIPPING_ID_INVALID}
            );
            PrintDetailInfo info = this.toolListPrintService.findPrintDetailById(shippingType,shippingId);
            if(info == null){
            	throw new HsException(ASRespCode.APS_TOOL_SHIPPING_NOT_FOUND);
            }
            return new HttpRespEnvelope(info);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
}
