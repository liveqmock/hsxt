/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.toolmanage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolmanage.ILogisticsService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.ShippingMethod;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.toolmanage
 * @className     : LogisticsController.java
 * @description   : 物流配送信息
 * @author        : maocy
 * @createDate    : 2016-01-18
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("logisticsController")
public class LogisticsController extends BaseController {
	
	@Resource
    private ILogisticsService logisticsService;//物流配送信息服务类
    
    /**
     * 
     * 方法名称：添加配送方式
     * 方法描述：物流配送信息-添加配送方式
     * @param request HttpServletRequest对象
     * @param bean 配送方式信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addShipping")
    public HttpRespEnvelope addShipping(HttpServletRequest request, @ModelAttribute ShippingMethod bean) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bean.getSmName(), RespCode.APS_LOG_SMNAME_INVALID},
                new Object[] {bean.getSort(), RespCode.APS_LOG_SORT_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getSmName(), 2, 64, RespCode.APS_LOG_SMNAME_LENGTH_INVALID},
                new Object[] {bean.getSmDesc(), 0, 200, RespCode.APS_LOG_SMDESC_LENGTH_INVALID}
            );
            bean.setCreatedBy(custId);
            this.logisticsService.addShipping(bean);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：修改配送方式
     * 方法描述：物流配送信息-修改配送方式
     * @param request HttpServletRequest对象
     * @param bean 配送方式信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyShipping")
    public HttpRespEnvelope modifyShipping(HttpServletRequest request, @ModelAttribute ShippingMethod bean) {
    	try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bean.getSmId(), RespCode.APS_LOG_SMID_INVALID},
                new Object[] {bean.getSmName(), RespCode.APS_LOG_SMNAME_INVALID},
                new Object[] {bean.getSort(), RespCode.APS_LOG_SORT_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getSmName(), 2, 64, RespCode.APS_LOG_SMNAME_LENGTH_INVALID},
                new Object[] {bean.getSmDesc(), 0, 200, RespCode.APS_LOG_SMDESC_LENGTH_INVALID}
            );
            bean.setUpdatedBy(custId);
            this.logisticsService.modifyShipping(bean);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：删除配送方式
     * 方法描述：物流配送信息-删除配送方式
     * @param request HttpServletRequest对象
     * @param smId 配送方式编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removeShipping")
    public HttpRespEnvelope removeShipping(HttpServletRequest request, String smId) {
    	try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {smId, RespCode.APS_LOG_SMID_INVALID}
            );
            this.logisticsService.removeShipping(smId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
	
	@Override
	protected IBaseService getEntityService() {
		return this.logisticsService;
	}

}
