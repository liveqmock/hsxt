/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.companyInfo.ICompanyInfoService;
import com.gy.hsxt.access.web.aps.services.toolorder.IToolListPrintService;
import com.gy.hsxt.access.web.bean.PrintDetailInfo;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.toolorder
 * @className     : ToolListPrintController.java
 * @description   : 售后工具配送清单打印
 * @author        : maocy
 * @createDate    : 2016-03-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("toolListPrintController")
public class ToolListPrintController extends BaseController {

    @Resource
    private IToolListPrintService toolListPrintService;
    
    @Resource
    private ICompanyInfoService companyService;//企业信息服务类
    
    /**
     * 
     * 方法名称：查询发货清单
     * 方法描述：售后订单管理-消查询发货清单
     * @param request HttpServletRequest对象
     * @param shippingId 发货单编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findShippingAfterById")
    public HttpRespEnvelope findShippingAfterById(HttpServletRequest request, String shippingId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {shippingId, ASRespCode.APS_TOOL_SHIPPING_ID_INVALID}
            );
            Shipping obj = this.toolListPrintService.findShippingAfterById(shippingId);
            if(obj == null){
            	throw new HsException(ASRespCode.APS_TOOL_SHIPPING_NOT_FOUND);
            }
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：企业信息查询
     * 方法描述：售后订单管理-企业信息查询
     * @param request HttpServletRequest对象
     * @param entCustId  企业客户号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findCompanyInfo")
    public HttpRespEnvelope findCompanyInfo(HttpServletRequest request, String companyCustId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {companyCustId, ASRespCode.APS_SMRZSP_ENTCUSTID_NOT_NULL}
            );
            AsEntExtendInfo obj = this.companyService.findEntAllInfo(companyCustId);
            if(obj == null){
            	throw new HsException(ASRespCode.APS_TOOL_ENTCUSTID_NOT_FOUND);
            }
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 
     * 方法名称：查询售后发货清单列表
     * 方法描述：售后订单管理-售后工具配送清单打印
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findPrintListResult")
    public HttpRespEnvelope findPrintListResult(HttpServletRequest request,HttpServletResponse response) throws HsException{
    	
    	try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", toolListPrintService);
			request.setAttribute("methodName", "findPrintListResult");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
    }
    /**
     * 
     * 方法名称：查询售后发货清单打印信息
     * 方法描述：售后订单管理-售后工具配送清单打印-打印配送单
     * @param shippingId 发货单编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findPrintDetailAfterById")
    public HttpRespEnvelope findPrintDetailAfterById(HttpServletRequest request,String shippingId) throws HsException{
    	try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {shippingId, ASRespCode.APS_TOOL_SHIPPING_ID_INVALID}
            );
            PrintDetailInfo info = this.toolListPrintService.findPrintDetailAfterById(shippingId);
            if(info == null){
            	throw new HsException(ASRespCode.APS_TOOL_SHIPPING_NOT_FOUND);
            }
            return new HttpRespEnvelope(info);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    @Override
    protected IBaseService getEntityService() {
        return this.toolListPrintService;
    }

}
