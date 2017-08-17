/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.annotation.PaymentInfo;
import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyHandler;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: ResFeeNotifyHandler
 * @Description: 企业资源费网银支付结果业务处理
 * 
 * @author: xiaofl
 * @date: 2015-12-23 上午9:47:01
 * @version V1.0
 */
@PaymentInfo(payChannel = { PayChannel.E_BANK_PAY, PayChannel.MOBILE_PAY, PayChannel.QUICK_PAY,
        PayChannel.TRANSFER_REMITTANCE, PayChannel.CARD_PAY}, orderType = OrderType.RES_FEE_ALLOT)
public class ResFeeNotifyHandler implements IPaymentNotifyHandler {

    @Autowired
    private IOrderService orderService;

    @Resource
    private BsConfig bsConfig;

    @Autowired
    IDeclareService declareService;

    /**
     * 处理支付结果方法
     * 
     * @param paymentNotify
     *            支付结果实体
     * @return true or false
     * @throws HsException
     *             异常
     */
    @Override
    public boolean handle(PaymentNotify paymentNotify) throws HsException {

        Order order = paymentNotify.getOrder();
        // 1.支付成功的情况
        if (PayStatus.PAY_FINISH == paymentNotify.getPayStatus())
        {
            // 付款成功后更新订单和申报相关信息
            declareService.updateDeclareAfterPay(order.getBizNo(), order);
        }
        else
        {// 其他情况 暂不处理

            order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());

            // 更改资源费订单状态为"待付款",支付状态为"付款失败"
            orderService.updateOrderAllStatus(order);

        }
        // 写入系统日志 101为失败
        SystemLog.debug(bsConfig.getSysName(), "ResFeeNotifyHandler:handle", "处理支付结果:" + paymentNotify.getPayStatus());
        return true;
    }
}
