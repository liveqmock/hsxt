/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.constant.GPConstant;

/**
 * 支付通知实体
 *
 * @Package : com.gy.hsxt.bs.common.bean
 * @ClassName : PaymentNotify
 * @Description : 支付通知实体
 * @Author : chenm
 * @Date : 2015/12/28 11:01
 * @Version V3.0.0.0
 */
public class PaymentNotify {

    /**
     * 业务流水号/业务订单编号
     */
    private String orderNo;

    /**
     * 支付状态
     *
     * @see PayStatus
     */
    private PayStatus payStatus;

    /**
     * 支付渠道
     *
     * @see PayChannel
     */
    private PayChannel payChannel;

    /**
     * 支付金额
     */
    private String payAmount;

    /**
     * 支付币种
     */
    private String currencyCode;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 业务订单
     */
    private Order order;

    /**
     * 银行流水号
     */
    private String bankTransNo;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public PayChannel getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(PayChannel payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        order.setPayStatus(this.getPayStatus().getCode());
        order.setOrderChannel(this.getPayChannel().getCode());
        order.setBankTransNo(this.getBankTransNo());
        this.order = order;
    }

    public String getBankTransNo() {
        return bankTransNo;
    }

    public void setBankTransNo(String bankTransNo) {
        this.bankTransNo = bankTransNo;
    }

    public static PaymentNotify bulid(PaymentOrderState paymentOrderState) {
        PaymentNotify notify = new PaymentNotify();
        notify.setCurrencyCode(paymentOrderState.getCurrencyCode());//币种
        notify.setOrderNo(paymentOrderState.getOrderNo());//业务流水号
        notify.setPayAmount(paymentOrderState.getTransAmount());//支付金额
        notify.setPayTime(DateUtil.DateTimeToString(paymentOrderState.getOrderDate()));//支付时间
        //校验参数
        HsAssert.isTrue(PayChannel.checkChannel(paymentOrderState.getPayChannel()), RespCode.PARAM_ERROR, "支付系统结果中的参数[payChannel]类型错误:" + paymentOrderState.getPayChannel());
        notify.setPayChannel(PayChannel.getPayChannel(paymentOrderState.getPayChannel()));//支付方式
        // 支付状态
        if (GPConstant.PaymentStateCode.PAY_SUCCESS == paymentOrderState.getTransStatus()) {
            notify.setPayStatus(PayStatus.PAY_FINISH);
        }  else {
            notify.setPayStatus(PayStatus.WAIT_PAY);
        }
        notify.setBankTransNo(paymentOrderState.getBankOrderNo());//设置银行流水号
        return notify;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
