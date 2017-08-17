package com.gy.hsxt.access.web.aps.controllers.receivingManage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.aps.services.receivingManage.IPaymentOrderService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.receivingManage
 * @className     : PaymentOrderController.java
 * @description   : 网银收款对账
 * @author        : maocy
 * @createDate    : 2016-03-03
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("paymentOrderController")
public class PaymentOrderController extends BaseController {

    @Resource
    private IPaymentOrderService paymentOrderService;
    
    /**
     * 
     * 方法名称：收款管理数据对帐
     * 方法描述：网银收款对账-收款管理数据对帐
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/dataReconciliation")
    public HttpRespEnvelope dataReconciliation(HttpServletRequest request, HttpServletResponse response, String orderNos) {
        try
        {
            // 校验互生卡号
            super.verifyPointNo(request);

            // 转换为订单集合
            List<String> temp = JSON.parseArray(URLDecoder.decode(orderNos, "UTF-8"), String.class);
            if (temp == null || temp.size() == 0) {
                throw new HsException(ASRespCode.APS_ORDERNO_NOT_NULL);
            }

            List<PaymentManagementOrder> list = this.paymentOrderService.dataReconciliation(temp);
            return new HttpRespEnvelope(list);
        }
        catch (HsException e)
        {
            return new HttpRespEnvelope(e);
        }
        catch (UnsupportedEncodingException e)
        {
            return new HttpRespEnvelope(ASRespCode.APS_ORDERNO_NOT_NULL);
        }
    }
	
	/**
     * 
     * 方法名称：收款管理订单详情
     * 方法描述：网银收款对账-收款管理订单详情
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findPaymentOrderInfo")
    public HttpRespEnvelope findPaymentOrderInfo(HttpServletRequest request, String orderNo) {
        try
        {
            // 校验互生卡号
            super.verifyPointNo(request);
            // 验证空数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo, ASRespCode.APS_ORDERNO_NOT_NULL });
            // 获取订单详情
            PaymentManagementOrder obj = this.paymentOrderService.findPaymentOrderInfo(orderNo);

            return new HttpRespEnvelope(obj);
        }
        catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
	@Override
	protected IBaseService getEntityService() {
		return this.paymentOrderService;
	}

}
