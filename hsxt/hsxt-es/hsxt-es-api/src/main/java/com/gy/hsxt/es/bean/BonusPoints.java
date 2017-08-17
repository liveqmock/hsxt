/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @Package :com.gy.hsxt.es.bean
 * @ClassName : BonusPoints
 * @Description : 混合通用接口实体类
 * @Author : Martin.Cubbon
 * @Date : 2/24 0024 15:16
 * @Version V3.0.0.0
 */
public class BonusPoints implements Serializable {

    private static final long serialVersionUID = -4155369692575534347L;
    /** 交易流水号 **/
    private String transNo;
    /** 交易类型 **/
    private String transType;
    /** 消费者互生号 **/
    private String perResNo;
    /**消费者客户号 */
    private String perCustId;
    /** 设备编号 */
    private String equipmentNo;
    /** 渠道类型 */
    private Integer channelType;
    /** 设备类型 */
    private Integer equipmentType;
    /** 原始电商交易号 */
    private String sourceTransNo;
    /** 批次号 */
    private String batchNo;
    /** 原批次号  */
    private String sourceBatchNo;
    /** 原始交易时间 */
    private String sourceTransDate;
    /** 原始交易币种代号 */
    private String sourceCurrencyCode;
    /** 原始币种金额 */
    private String sourceTransAmount;
    /** 交易金额 */
    private String transAmount;
    /** 积分比例 */
    private String pointRate;
    /** 消费者积分 */
    private String perPoint;
    /** 积分预付款 */
    private String entPoint;
    /** 原始交易流水号 */
    private String oldTransNo;
    /** 企业互生号 **/
    private String entResNo;
    /** 企业客户号 **/
    private String entCustId;
    /*** 操作员编号*/
    private String operNo;
    /*** pos中心流水号编码*/
    private String termRunCode;
    /** pos机类型编码 */
    private String termTypeCode;
    /** pos机交易编码 */
    private String termTradeCode;
    /** 交易类型 :1是预留, 2是预留结单,3是撤单，4是退货，5是消费积分，货到付款一次性支付完成 */
    private Integer  tradeType;
    /** 退货积分 */
    private String backPoint;
    /** 是否扣商业服务费  0 是(收费) 1 否(不收费)**/
    private Integer isDeduction;

    /*货币转换率 */
    private String  currencyRate;

    /**企业名称 */
    private  String entName;

    /**店铺名称 */
    private  String mallName;

    /*备注 */
    private  String remark;

    /**订单号*/
    private  String orderNo;
    
    /**订单类型*/
    private String orderType;
    
    /** 订单完成时间 */
    private String orderFinishDate;
    
    /** 订单金额 */
    private String orderAmount;

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPerResNo() {
        return perResNo;
    }

    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    public String getPerCustId() {
        return perCustId;
    }

    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getSourceTransNo() {
        return sourceTransNo;
    }

    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSourceBatchNo() {
        return sourceBatchNo;
    }

    public void setSourceBatchNo(String sourceBatchNo) {
        this.sourceBatchNo = sourceBatchNo;
    }

    public String getSourceTransDate() {
        return sourceTransDate;
    }

    public void setSourceTransDate(String sourceTransDate) {
        this.sourceTransDate = sourceTransDate;
    }

    public String getSourceCurrencyCode() {
        return sourceCurrencyCode;
    }

    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    public String getSourceTransAmount() {
        return sourceTransAmount;
    }

    public void setSourceTransAmount(String sourceTransAmount) {
        this.sourceTransAmount = sourceTransAmount;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getPointRate() {
        return pointRate;
    }

    public void setPointRate(String pointRate) {
        this.pointRate = pointRate;
    }

    public String getPerPoint() {
        return perPoint;
    }

    public void setPerPoint(String perPoint) {
        this.perPoint = perPoint;
    }

    public String getEntPoint() {
        return entPoint;
    }

    public void setEntPoint(String entPoint) {
        this.entPoint = entPoint;
    }

    public String getOldTransNo() {
        return oldTransNo;
    }

    public void setOldTransNo(String oldTransNo) {
        this.oldTransNo = oldTransNo;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getTermRunCode() {
        return termRunCode;
    }

    public void setTermRunCode(String termRunCode) {
        this.termRunCode = termRunCode;
    }

    public String getTermTypeCode() {
        return termTypeCode;
    }

    public void setTermTypeCode(String termTypeCode) {
        this.termTypeCode = termTypeCode;
    }

    public String getTermTradeCode() {
        return termTradeCode;
    }

    public void setTermTradeCode(String termTradeCode) {
        this.termTradeCode = termTradeCode;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getIsDeduction() {
        return isDeduction;
    }

    public void setIsDeduction(Integer isDeduction) {
        this.isDeduction = isDeduction;
    }

    public String getBackPoint() {
        return backPoint;
    }

    public void setBackPoint(String backPoint) {
        this.backPoint = backPoint;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(String currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the 订单类型
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param 订单类型 the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the 订单完成时间
     */
    public String getOrderFinishDate() {
        return orderFinishDate;
    }

    /**
     * @param 订单完成时间 the orderFinishDate to set
     */
    public void setOrderFinishDate(String orderFinishDate) {
        this.orderFinishDate = orderFinishDate;
    }

    /**
     * @return the 订单金额
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     * @param 订单金额 the orderAmount to set
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }
}
