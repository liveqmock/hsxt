/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.constant.PayChannel;

/**
 * @Package : com.gy.hsxt.bs.common.bean
 * @ClassName : PayUrl
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/28 17:47
 * @Version V3.0.0.0
 */
public class PayUrl {

    /**
     * 业务订单
     */
    private Order order;

    /**
     * 支付方式
     * 
     * @see PayChannel
     */
    private int payChannel;

    /**
     * 网银回调地址
     */
    private String callbackUrl;

    /**
     * 签约号
     */
    private String bindingNo;

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 交易购买商品名称
     */
    private String goodsName;

    /**
     * 支付页面显示类型, 0:通用(默认)； 1:单独显示B2B； 2:单独显示B2C；
     */
    private Integer paymentUIType;

    /**
     * 交易描述
     */
    private String transDesc;

    /**
     * 传递商户私有数据
     */
    private String privObligate;

    public String getPrivObligate() {
        return privObligate;
    }

    public void setPrivObligate(String privObligate) {
        this.privObligate = privObligate;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getPaymentUIType() {
        return paymentUIType;
    }

    public void setPaymentUIType(Integer paymentUIType) {
        this.paymentUIType = paymentUIType;
    }

    private PayUrl(Order order, int payChannel, String callbackUrl, String bindingNo, String smsCode) {
        this.order = order;
        this.payChannel = payChannel;
        this.callbackUrl = callbackUrl;
        this.bindingNo = bindingNo;
        this.smsCode = smsCode;
    }

    private PayUrl(Order order, int payChannel, String callbackUrl, String bindingNo, String smsCode, String goodsName) {
        this.order = order;
        this.payChannel = payChannel;
        this.callbackUrl = callbackUrl;
        this.bindingNo = bindingNo;
        this.smsCode = smsCode;
        this.goodsName = goodsName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getBindingNo() {
        return bindingNo;
    }

    public void setBindingNo(String bindingNo) {
        this.bindingNo = bindingNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public static PayUrl bulid(Order order, int payChannel, String callbackUrl, String bindingNo, String smsCode,
            String goodsName) {
        return new PayUrl(order, payChannel, callbackUrl, bindingNo, smsCode, goodsName);
    }

    public static PayUrl bulid(Order order, int payChannel, String callbackUrl, String bindingNo, String smsCode) {
        return new PayUrl(order, payChannel, callbackUrl, bindingNo, smsCode);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
