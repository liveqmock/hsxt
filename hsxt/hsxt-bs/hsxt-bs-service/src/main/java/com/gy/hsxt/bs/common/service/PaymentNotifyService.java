/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyHandler;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyService;
import com.gy.hsxt.bs.common.utils.PaymentNotifyFactory;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付回调业务层实现
 *
 * @Package :com.gy.hsxt.bs.common.service
 * @ClassName : PaymentNotifyService
 * @Description : 支付回调业务层实现 支付方式包括：网银支付，手机支付，快捷支付，临账支付 等
 * @Author : chenm
 * @Date : 2015/11/16 17:28
 * @Version V3.0.0.0
 */
@Service
public class PaymentNotifyService implements IPaymentNotifyService {

    @Resource
    private BsConfig bsConfig;

    @Resource
    private IOrderService orderService;

    @Resource
    private PaymentNotifyFactory paymentNotifyFactory;

    /**
     * 　GP-BS支付结果异步通知　 “支付系统-->业务系统”异步通知接口，该接口由支付系统定义，然后由BS业务系统实现。 临账支付也由此回调
     *
     * @param paymentOrderState 支付结果
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean notifyPaymentOrderState(PaymentOrderState paymentOrderState) throws HsException {
        // 第一步，校验参数
        // 判断参数是否为空
        if (paymentOrderState == null) {
            SystemLog.error(bsConfig.getSysName(), "function:notifyPaymentOrderState", "支付系统异步通知结果为空", null);
            return false;
        }
        // 返回处理结果
        return this.paymentNotifyHandle(PaymentNotify.bulid(paymentOrderState));
    }

    /**
     * 支付通知处理
     *
     * @param paymentNotify 支付通知实体
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean paymentNotifyHandle(PaymentNotify paymentNotify) throws HsException {
        try {
            // 1.3 校验业务订单号
            // 1.3.2判断业务订单号是否为空
            HsAssert.hasText(paymentNotify.getOrderNo(), RespCode.PARAM_ERROR, "支付通知结果中的业务订单号[orderNo]不能为空");
            // 1.4 校验订单是否存在
            // 1.4.1查询业务订单
            Order order = orderService.getOrderByNo(paymentNotify.getOrderNo());
            // 1.4.2 校验业务订单实体

            HsAssert.notNull(order, BSRespCode.BS_ORDER_NOT_EXIST, "业务订单[orderNo:" + paymentNotify.getOrderNo() + "]不存在");

            if (paymentNotify.getPayStatus() == PayStatus.PAY_FINISH) {//如果是支付成功的状态进行判断
                HsAssert.isTrue(PayStatus.PAY_FINISH.getCode() != order.getPayStatus(), BSRespCode.BS_ORDER_IS_PAY, "业务订单[orderNo:" + paymentNotify.getOrderNo() + "]已经完成支付，不能再次支付");
            }

            order.setBankTransNo(paymentNotify.getBankTransNo());

            // 将查询的订单实体设置到通知实体中
            paymentNotify.setOrder(order);
            // 第二步，业务逻辑处理
            // 订单类型
            String orderType = order.getOrderType();
            // 2.1 获取支付结果通知处理器
            IPaymentNotifyHandler notifyHandler = paymentNotifyFactory.getHandler(paymentNotify.getPayChannel(), orderType);
            // 校验处理器是否存在
            HsAssert.notNull(notifyHandler, RespCode.PARAM_ERROR, "没有对应支付方式或业务的支付结果处理器[IPaymentNotifyHandler]存在");
            return notifyHandler.handle(paymentNotify);
        } catch (Exception e) {
            // 支付系统不要业务系统抛异常，所以异常不抛，只记下日志。
            SystemLog.error("BS", "function:paymentNotifyHandle(param:" + paymentNotify + ")", e.getMessage(), e);
        }
        return true;
    }

    /**
     * GP-BS转账结果异步通知,　　“支付系统-->业务系统”通知接口，该接口由支付系统定义，然后由BS业务系统实现。
     *
     * @param transCashOrderState 转账结果
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean notifyTransCashOrderState(TransCashOrderState transCashOrderState) throws HsException {
        return false;
    }
}
