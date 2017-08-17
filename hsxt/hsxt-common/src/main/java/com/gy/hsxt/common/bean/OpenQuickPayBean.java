/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.bean;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 开通快捷支付实体类
 * 
 * @Package: com.gy.hsxt.ao.bean
 * @ClassName: OpenQuickPayBean
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-8 上午9:08:31
 * @version V3.0.0
 */
public class OpenQuickPayBean implements Serializable {

    private static final long serialVersionUID = 771318323182323091L;

    /** 商户号，1100000001：互商 1100000007：互生 第三方接入的酌情分配 */
    private String merId;

    /** 业务订单号，最大32位字符 */
    private String orderNo;

    /** 业务订单日期，格式：yyyyMMdd */
    private Date orderDate;

    /** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
    private String transAmount;

    /** 交易描述 ,最大长度30 */
    private String transDesc;

    /** 币种 CNY 人民币（默认） */
    private String currencyCode;

    /** 支付成功后，银行将会通过浏览器跳转到这个页面，,最大长度80 */
    private String jumpUrl;

    /** 传递商户私有数据,用于回调时带回,最大长度60 */
    private String privObligate;

    /** 客户号，用于向用户中心同步签约号,最大长度21 */
    private String custId;

    /** 银行账户，最大长度19 */
    private String bankCardNo;

    /**
     * 借贷记标识，即：借记卡 or 信用卡 1 - 借记卡 2 - 贷记卡
     */
    private int bankCardType;

    /** 银行ID,参照《支付系统设计文档》附录银行名称-简码对照表,最大长度4 */
    private String bankId;

    /**
     * 构造函数
     */
    public OpenQuickPayBean() {
        super();
    }

    /**
     * 构造函数
     */
    public OpenQuickPayBean(String merId, String orderNo, Date orderDate, String transAmount, String jumpUrl,
            String custId, String bankCardNo, int bankCardType, String bankId) {
        super();
        this.merId = merId;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.transAmount = transAmount;
        this.jumpUrl = jumpUrl;
        this.custId = custId;
        this.bankCardNo = bankCardNo;
        this.bankCardType = bankCardType;
        this.bankId = bankId;
    }

    /**
     * @return the 商户号，1100000001：互商1100000007：互生第三方接入的酌情分配
     */
    public String getMerId() {
        return merId;
    }

    /**
     * @param 商户号
     *            ，1100000001：互商1100000007：互生第三方接入的酌情分配 the merId to set
     */
    public void setMerId(String merId) {
        this.merId = merId;
    }

    /**
     * @return the 业务订单号，最大32位字符
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param 业务订单号
     *            ，最大32位字符 the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the 业务订单日期，格式：yyyyMMdd
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param 业务订单日期
     *            ，格式：yyyyMMdd the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止；
     */
    public String getTransAmount() {
        return transAmount;
    }

    /**
     * @param 交易金额
     *            ，精度为6，传递值的精度小于6的自动补足，大于6的禁止； the transAmount to set
     */
    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * @return the 交易描述最大长度30
     */
    public String getTransDesc() {
        return transDesc;
    }

    /**
     * @param 交易描述最大长度30
     *            the transDesc to set
     */
    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    /**
     * @return the 币种CNY人民币（默认）
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param 币种CNY人民币
     *            （默认） the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the 支付成功后，银行将会通过浏览器跳转到这个页面，最大长度80
     */
    public String getJumpUrl() {
        return jumpUrl;
    }

    /**
     * @param 支付成功后
     *            ，银行将会通过浏览器跳转到这个页面，最大长度80 the jumpUrl to set
     */
    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    /**
     * @return the 传递商户私有数据用于回调时带回最大长度60
     */
    public String getPrivObligate() {
        return privObligate;
    }

    /**
     * @param 传递商户私有数据用于回调时带回最大长度60
     *            the privObligate to set
     */
    public void setPrivObligate(String privObligate) {
        this.privObligate = privObligate;
    }

    /**
     * @return the 客户号，用于向用户中心同步签约号最大长度21
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param 客户号
     *            ，用于向用户中心同步签约号最大长度21 the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * @return the 银行账户，最大长度19
     */
    public String getBankCardNo() {
        return bankCardNo;
    }

    /**
     * @param 银行账户
     *            ，最大长度19 the bankCardNo to set
     */
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    /**
     * @return the 借贷记标识，即：借记卡or信用卡1-借记卡2-贷记卡
     */
    public int getBankCardType() {
        return bankCardType;
    }

    /**
     * @param 借贷记标识
     *            ，即：借记卡or信用卡1-借记卡2-贷记卡 the bankCardType to set
     */
    public void setBankCardType(int bankCardType) {
        this.bankCardType = bankCardType;
    }

    /**
     * @return the 银行ID参照《支付系统设计文档》附录银行名称-简码对照表最大长度4
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * @param 银行ID参照
     *            《支付系统设计文档》附录银行名称-简码对照表最大长度4 the bankId to set
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
