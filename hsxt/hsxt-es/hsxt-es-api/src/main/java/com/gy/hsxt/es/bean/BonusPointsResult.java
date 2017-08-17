/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @Package :com.gy.hsxt.es.bean
 * @ClassName : BonusPointsResult
 * @Description :通用接口(支付、结单、撤单，退货)返回实体
 * @Author : Martin.Cubbon
 * @Date : 3/2 0002 16:43
 * @Version V3.0.0.0
 */
public class BonusPointsResult extends Result implements Serializable {

    private static final long serialVersionUID = 1655918748891072838L;

    /**交易类型 0 是消费积分，1是撤单，2是退货，3是预留，4是预留结单*/
    private Integer  tradeType;

    /**消费积分返回流水号 */
    private String transNo;

    /**原电商流水号*/
    private String sourceTransNo;

    /**订单号*/
    private String orderNo;

    /** 实退或实付金额 **/
    private String transAmount;

    /** 实退或实付积分 */
    private String transPoint;

    /** 会计时间*/
    private String accountantDate;

    /** 响应码 */
    private int retCode;

    /** 返回信息 */
    private String retMsg;

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getSourceTransNo() {
        return sourceTransNo;
    }

    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransPoint() {
        return transPoint;
    }

    public void setTransPoint(String transPoint) {
        this.transPoint = transPoint;
    }

    public String getAccountantDate() {
        return accountantDate;
    }

    public void setAccountantDate(String accountantDate) {
        this.accountantDate = accountantDate;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
