/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.order.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.annotation.PaymentInfo;
import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyHandler;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.order.mapper.OrderMapper;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;

/**
 * 兑换互生币订单临帐支付方式业务处理
 * 
 * @Package: com.gy.hsxt.bs.order.service
 * @ClassName: BuyHsbPayByTempService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-25 下午4:20:33
 * @version V3.0.0
 */
@PaymentInfo(payChannel = { PayChannel.TRANSFER_REMITTANCE }, orderType = OrderType.BUY_HSB)
public class BuyHsbPayByTempService implements IPaymentNotifyHandler {
    @Autowired
    private IOrderService orderService;

    @Resource
    private BsConfig bsConfig;

    @Autowired
    IAccountDetailService accountDetailService;

    @Autowired
    OrderMapper orderMapper;

    @Override
    public boolean handle(PaymentNotify paymentNotify) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "兑换互生币订单临帐支付方式业务处理,params[" + paymentNotify + "]");
        // 查询业务订单
        Order order = paymentNotify.getOrder();
        // 支付成功的情况
        if (PayStatus.PAY_FINISH == paymentNotify.getPayStatus())
        {
            // 订单未付款
            order.setOrderStatus(OrderStatus.IS_COMPLETE.getCode());
            // 更新订单状态和支付状态为已完成、已付款
            orderService.updateOrderAllStatus(order);
            // 兑换互生币临帐支付方式记帐分解
            accountDetailService.saveTempActDetail(order.getOrderNo());
        }
        else
        {
            order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
            // 更改兑换互生币订单状态为"待付款",支付状态为"付款失败"
            orderService.updateOrderAllStatus(order);
        }
        return true;
    }
}
