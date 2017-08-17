package com.gy.hsxt.ps.bean;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author chenhz
 * @version v3.0
 * @description 查询单笔交易返回结果实体类
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 */
public class QueryEntry implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4005537682483642817L;
    /**
     * 交易流水号
     */
    private String transNo;
    /**
     * 交易类型
     */
    private String transType;
    /**
     * 企业互生号
     */
    private String entResNo;
    /**
     * 消费者互生号
     */
    private String perResNo;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 交易时间
     */
    private String sourceTransDate;
    /**
     * 结算时间
     */
    private String settleDate;
    /**
     * 结算金额
     */
    private String settleAmount;
    /**
     * 积分比例
     */
    private String pointRate;
    /**
     * 支付金额
     */
    private String transAmount;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 扣除积分数
     */
    private String deductPoint;

    private String orderNoOrorderType;

    /***
     * 商业服务费
     */
    private String serviceFee;

    /**
     * 获取交易流水号
     *
     * @return transNo 交易流水号
     */
    public String getTransNo() {
        return transNo;
    }

    /**
     * 设置交易流水号
     *
     * @param transNo 交易流水号
     */
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    /**
     * 获取交易类型
     *
     * @return transType 交易类型
     */
    public String getTransType() {
        return transType;
    }

    /**
     * 设置交易类型
     *
     * @param transType 交易类型
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * 获取企业互生号
     *
     * @return entResNo 企业互生号
     */
    public String getEntResNo() {
        return entResNo;
    }

    /**
     * 设置企业互生号
     *
     * @param entResNo 企业互生号
     */
    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    /**
     * 获取消费者互生号
     *
     * @return perResNo 消费者互生号
     */
    public String getPerResNo() {
        return perResNo;
    }

    /**
     * 设置消费者互生号
     *
     * @param perResNo 消费者互生号
     */
    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    /**
     * 获取批次号
     *
     * @return batchNo 批次号
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 设置批次号
     *
     * @param batchNo 批次号
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * 获取原始交易时间
     *
     * @return sourceTransDate 原始交易时间
     */
    public String getSourceTransDate() {
        return sourceTransDate;
    }

    /**
     * 设置原始交易时间
     *
     * @param sourceTransDate 原始交易时间
     */
    public void setSourceTransDate(String sourceTransDate) {
        this.sourceTransDate = sourceTransDate;
    }

    /**
     * 获取积分比例
     *
     * @return pointRate 积分比例
     */
    public String getPointRate() {
        return pointRate;
    }

    /**
     * 设置积分比例
     *
     * @param pointRate 积分比例
     */
    public void setPointRate(String pointRate) {
        this.pointRate = pointRate;
    }

    /**
     * 获取交易金额
     *
     * @return transAmount 交易金额
     */
    public String getTransAmount() {
        return transAmount;
    }

    /**
     * 设置交易金额
     *
     * @param transAmount 交易金额
     */
    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * * 获取结算时间
     *
     * @return settleDate 结算时间
     */
    public String getSettleDate() {
        return settleDate;
    }

    /**
     * 设置交易金额
     *
     * @param settleDate 结算时间
     */
    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    /**
     * 获取结算金额
     *
     * @return settleAmount 结算金额
     */
    public String getSettleAmount() {
        return settleAmount;
    }

    /**
     * 设置交易金额
     *
     * @param settleAmount 结算金额
     */
    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getOrderType() {
        if (this.getOrderNoOrorderType() != null) {
            return this.getOrderNoOrorderType().getOrderType();
        }
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        if (this.getOrderNoOrorderType() != null) {
            return this.getOrderNoOrorderType().getOrderNo();
        }
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDeductPoint() {
        return deductPoint;
    }

    public void setDeductPoint(String deductPoint) {
        this.deductPoint = deductPoint;
    }

    private QueryEntry getOrderNoOrorderType() {
        if (StringUtils.isNotEmpty(orderNoOrorderType)) {
            return JSONObject.parseObject(orderNoOrorderType, QueryEntry.class);
        }
        return null;
    }

    private void setOrderNoOrorderType(String orderNoOrorderType) {
        this.orderNoOrorderType = orderNoOrorderType;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
}
