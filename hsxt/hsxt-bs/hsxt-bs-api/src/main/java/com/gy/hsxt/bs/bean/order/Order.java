/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkDebit;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: Order
 * @Description: 业务订单实体类
 * 
 * @author: kongsl
 * @date: 2015-9-1 下午6:36:35
 * @version V1.0
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 7306561333120661455L;

    /** 订单编号 **/
    private String orderNo;

    /** 客户号 **/
    private String custId;

    /** 互生号 **/
    private String hsResNo;

    /** 客户名称 **/
    private String custName;

    /** 客户类型 **/
    private Integer custType;

    /** 原业务编号 **/
    private String bizNo;

    /** 订单类型 **/
    private String orderType;

    /** 是否平台代理 **/
    private Boolean isProxy;

    /** 订单数量 **/
    private Integer quantity;

    /** 订单单位 **/
    private String orderUnit;

    /** 订单原始金额 **/
    private String orderOriginalAmount;

    /** 订单减免金额 **/
    private String orderDerateAmount;

    /** 订单货币金额 **/
    private String orderCashAmount;

    /** 订单货币币种 **/
    private String currencyCode;

    /** 订单互生币金额 **/
    private String orderHsbAmount;

    /** 货币转换比率 **/
    private String exchangeRate;

    /** 订单备注 **/
    private String orderRemark;

    /** 订单时间 **/
    private String orderTime;

    /** 收货信息编号 **/
    private String deliverId;

    /** 订单渠道来源(受理方式) **/
    private Integer orderChannel;

    /** 订单操作员 **/
    private String orderOperator;

    /** 支付超时时间 **/
    private String payOvertime;

    /** 订单状态 **/
    private Integer orderStatus;

    /** 支付状态 **/
    private Integer payStatus;

    /** 支付方式 **/
    private Integer payChannel;

    /** 支付时间 **/
    private String payTime;

    /** 是否需要发票 **/
    private Integer isNeedInvoice;

    /** 是否已开发票 **/
    private Integer isInvoiced;

    /** 订单完成时间 **/
    private String finishedDate;

    /** 银行流水号 **/
    private String bankTransNo;

    /**
     * 订单成功关联的临账记录
     */
    private List<TempAcctLinkDebit> linkDebits;

    /**
     * 付款中时间
     */
    private String payingTime;

    public String getPayingTime() {
        return payingTime;
    }

    public void setPayingTime(String payingTime) {
        this.payingTime = payingTime;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getBankTransNo() {
        return bankTransNo;
    }

    public void setBankTransNo(String bankTransNo) {
        this.bankTransNo = bankTransNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getHsResNo() {
        return hsResNo;
    }

    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Boolean getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Boolean isProxy) {
        this.isProxy = isProxy;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getOrderOriginalAmount() {
        return orderOriginalAmount;
    }

    public void setOrderOriginalAmount(String orderOriginalAmount) {
        this.orderOriginalAmount = orderOriginalAmount;
    }

    public String getOrderDerateAmount() {
        return orderDerateAmount;
    }

    public void setOrderDerateAmount(String orderDerateAmount) {
        this.orderDerateAmount = orderDerateAmount;
    }

    public String getOrderCashAmount() {
        return orderCashAmount;
    }

    public void setOrderCashAmount(String orderCashAmount) {
        this.orderCashAmount = orderCashAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOrderHsbAmount() {
        return orderHsbAmount;
    }

    public void setOrderHsbAmount(String orderHsbAmount) {
        this.orderHsbAmount = orderHsbAmount;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public String getOrderOperator() {
        return orderOperator;
    }

    public void setOrderOperator(String orderOperator) {
        this.orderOperator = orderOperator;
    }

    public String getPayOvertime() {
        return payOvertime;
    }

    public void setPayOvertime(String payOvertime) {
        this.payOvertime = payOvertime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public Integer getIsInvoiced() {
        return isInvoiced;
    }

    public void setIsInvoiced(Integer isInvoiced) {
        this.isInvoiced = isInvoiced;
    }

    public List<TempAcctLinkDebit> getLinkDebits() {
        return linkDebits;
    }

    public void setLinkDebits(List<TempAcctLinkDebit> linkDebits) {
        this.linkDebits = linkDebits;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
