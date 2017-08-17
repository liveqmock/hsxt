/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付结果实体
 * 
 * @Package: com.gy.hsxt.ao.bean
 * @ClassName: PaymentResult
 * @Description: 参考支付系统com.gy.hsxt.gp.bean.PaymentOrderState
 * 
 * @author: kongsl
 * @date: 2016-3-2 下午6:00:04
 * @version V3.0.0
 */
public class PaymentResult implements Serializable {

    private static final long serialVersionUID = -8966382096218086793L;

    private String bankOrderSeqId;

    private String orderNo;

    private Date orderDate;

    private String transAmount;

    private String currencyCode;

    private int payChannel;

    private int transStatus;

    private String failReason;

    private String srcSubsysId;

    public PaymentResult() {
    }

    public PaymentResult(String orderNo, Date orderDate, String transAmount, String currencyCode, int payChannel,
            int transStatus, String failReason) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.transAmount = transAmount;
        this.currencyCode = currencyCode;
        this.payChannel = payChannel;
        this.transStatus = transStatus;
        this.failReason = failReason;
    }

    public String getSrcSubsysId() {
        return this.srcSubsysId;
    }

    public void setSrcSubsysId(String srcSubsysId) {
        this.srcSubsysId = srcSubsysId;
    }

    public String getBankOrderSeqId() {
        return this.bankOrderSeqId;
    }

    public void setBankOrderSeqId(String bankOrderSeqId) {
        this.bankOrderSeqId = bankOrderSeqId;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getTransAmount() {
        return this.transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getPayChannel() {
        return this.payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getTransStatus() {
        return this.transStatus;
    }

    public void setTransStatus(int transStatus) {
        this.transStatus = transStatus;
    }

    public String getFailReason() {
        return this.failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String toString() {
        return "PaymentResult [orderNo=" + this.orderNo + ", orderDate=" + this.orderDate + ", transAmount="
                + this.transAmount + ", currencyCode=" + this.currencyCode + ", payChannel=" + this.payChannel
                + ", transStatus=" + this.transStatus + ", failReason=" + this.failReason + ", srcSubsysId="
                + this.srcSubsysId + "]";
    }
}
