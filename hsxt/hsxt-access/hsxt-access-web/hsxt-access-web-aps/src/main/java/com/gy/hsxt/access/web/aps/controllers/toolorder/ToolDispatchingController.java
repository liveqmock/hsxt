/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.aps.services.toolorder.IToolDispatchingService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.toolorder
 * @className     : ToolDispatchingController.java
 * @description   : 售后工具配送
 * @author        : maocy
 * @createDate    : 2016-03-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("toolDispatchingController")
public class ToolDispatchingController extends BaseController {

    @Resource
    private IToolDispatchingService toolDispatchingService;
    
    /**
     * 
     * 方法名称：添加售后服务发货单
     * 方法描述：售后订单管理-添加售后服务发货单
     * @param request HttpServletRequest对象
     * @param bean 发货单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addToolShippingAfter")
    public HttpRespEnvelope addToolShippingAfter(HttpServletRequest request, @ModelAttribute Shipping bean) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//发货人
            String c_hsResNo = request.getParameter("c_hsResNo");//互生号
            String c_custId = request.getParameter("c_custId");//企业客户ID
            String c_custName = request.getParameter("c_custName");//企业客户名称
            String c_custType = request.getParameter("c_custType");//企业客户类型
            String c_mobile = request.getParameter("c_mobile");//手机
            String confNos = request.getParameter("confNos");//配置单编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {c_hsResNo, ASRespCode.APS_GJPSGL_HSRESNO},
                new Object[] {c_custId, ASRespCode.AS_CUSTID_INVALID},
                new Object[] {c_custName, ASRespCode.AS_CUSTNAME_INVALID},
                new Object[] {c_custType, ASRespCode.AS_CUSTTYPE_INVALID},
                new Object[] {bean.getReceiver(), ASRespCode.APS_GJPSGL_RECEIVER},
                new Object[] {bean.getReceiverAddr(), ASRespCode.APS_GJPSGL_RECEIVERADDR},
                new Object[] {c_mobile, ASRespCode.APS_GJPSGL_RECEIVERMOBILE},
                new Object[] {bean.getSmName(), ASRespCode.APS_TOOL_SM_NAME_INVALID},
                new Object[] {bean.getTrackingNo(), ASRespCode.APS_GJPSGL_TRACKINGNO},
                new Object[] {bean.getShippingFee(), ASRespCode.APS_GJPSGL_SHIPPINGFEE},
                new Object[] {custId, ASRespCode.APS_GJPSGL_CONSIGNOR}
            );
            String[] array = null;
            try {
				List<String> temp = JSON.parseArray(URLDecoder.decode(confNos, "UTF-8"), String.class);
				array = temp.toArray(new String[0]);
			} catch (UnsupportedEncodingException e) {
				throw new HsException(ASRespCode.APS_TOOL_CONFNO_NOT_FOUND);
			}
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getTrackingNo(), 1, 20, ASRespCode.APS_TOOL_SHIPPING_ID_LENGTH_INVALID}
            );
            bean.setHsResNo(c_hsResNo);
            bean.setConfNos(array);
            bean.setCustId(c_custId);
            bean.setCustName(c_custName);
            bean.setCustType(CommonUtils.toInteger(c_custType));
            bean.setMobile(c_mobile);
            bean.setConsignor(custId);
            this.toolDispatchingService.addToolShippingAfter(bean);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询发货单的数据
     * 方法描述：售后订单管理-查询发货单的数据
     * @param request HttpServletRequest对象
     * @param confNo 配置单编号列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findAfterShipingData")
    public HttpRespEnvelope findAfterShipingData(HttpServletRequest request, String confNo) {
        try {
            super.verifySecureToken(request);
            String[] array = null;
            try {
				List<String> temp = JSON.parseArray(URLDecoder.decode(confNo, "UTF-8"), String.class);
				array = temp.toArray(new String[0]);
			} catch (UnsupportedEncodingException e) {
				throw new HsException(ASRespCode.APS_TOOL_CONFNO_NOT_FOUND);
			}
            ShippingData obj = this.toolDispatchingService.findAfterShipingData(array);
            if(obj == null){
            	throw new HsException(ASRespCode.APS_TOOL_SHIPPING_NOT_FOUND);
            }
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    @Override
    protected IBaseService getEntityService() {
        return this.toolDispatchingService;
    }

}
