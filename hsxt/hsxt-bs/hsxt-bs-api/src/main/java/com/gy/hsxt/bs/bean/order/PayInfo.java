/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.order;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.PayChannel;

/**
 * 支付信息
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: PayInfo
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-7-1 下午6:08:39
 * @version V1.0
 */
public class PayInfo implements Serializable {

    private static final long serialVersionUID = -8395391352314692976L;

    /**
     * 订单实体
     */
    private Order order;

    /**
     * 交易流水号
     */
    private String transNo;

    /**
     * 支付方式
     * 
     * @see PayChannel
     */
    private Integer payChannel;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PayInfo() {
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

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

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
