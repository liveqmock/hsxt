/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.gy.hsxt.bs.common.annotation.PaymentInfo;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyHandler;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 支付结果通知处理工厂
 *
 * @Package :com.gy.hsxt.bs.common.utils
 * @ClassName : PaymentNotifyFactory
 * @Description : 支付结果通知处理工厂
 * @Author : chenm
 * @Date : 2015/11/16 18:27
 * @Version V3.0.0.0
 */
@Service
public class PaymentNotifyFactory {

    /**
     * 支付信息存储
     */
    private Map<String, PaymentInfo> paymentInfoMap = new HashMap<String, PaymentInfo>();

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 工厂初始化
     */
    @PostConstruct
    public final void init() {
        Map<String, IPaymentNotifyHandler> handlerMap = applicationContext.getBeansOfType(IPaymentNotifyHandler.class);
        for (Map.Entry<String, IPaymentNotifyHandler> handlerEntry : handlerMap.entrySet()) {
            PaymentInfo paymentInfo = handlerEntry.getValue().getClass().getAnnotation(PaymentInfo.class);
            paymentInfoMap.put(handlerEntry.getKey(), paymentInfo);
        }
    }

    /**
     * 获取处理器
     *
     * @param payChannel 支付渠道
     * @param orderType  订单类型
     * @return handler 处理器
     * @throws HsException 异常
     * @see com.gy.hsxt.gp.constant.GPConstant.PayChannel
     * @see OrderType
     */
    public IPaymentNotifyHandler getHandler(PayChannel payChannel, String orderType) throws HsException {
        // 检查参数
        HsAssert.isTrue(OrderType.checkType(orderType), RespCode.PARAM_ERROR, "支付结果-获取处理器参数[orderType]错误:" + orderType);
        OrderType type = OrderType.getOrderType(orderType);
        // 筛选beanName
        IPaymentNotifyHandler handler = null;
        for (Map.Entry<String, PaymentInfo> infoEntry : paymentInfoMap.entrySet()) {
            PaymentInfo paymentInfo = infoEntry.getValue();
            List<OrderType> typeList = Arrays.asList(paymentInfo.orderType());
            List<PayChannel> channelList = Arrays.asList(paymentInfo.payChannel());
            if (channelList.contains(payChannel) && typeList.contains(type)) {
                // 获取bean对象
                handler = applicationContext.getBean(infoEntry.getKey(), IPaymentNotifyHandler.class);
                break;
            }
        }
        return handler;
    }
}
