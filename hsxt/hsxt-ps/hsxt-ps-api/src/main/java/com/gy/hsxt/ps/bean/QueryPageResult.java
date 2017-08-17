/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.bean;

import java.io.Serializable;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.bean
 * @ClassName: QueryPageResult
 * @Description: 积分记录返回值
 * @author: Martin.Cubbon
 * @date: 2016-6-21 下午3:56:40
 */
public class QueryPageResult implements Serializable {

    private static final long serialVersionUID = 1405780231283353404L;
    /**
     * 交易流水号
     */
    private String transNo;
    /**
     * 企业互生号
     */
    private String entResNo;
    /**
     * 消费者互生号
     */
    private String perResNo;

    /**
     * 原交易金额
     */
    private String sourceTransAmount;

    /**
     * 交易金额
     */
    private String transAmount;
    /**
     * 积分比例
     */
    private String pointRate;
    /**
     * 消费者积分
     */
    private String perPoint;

    /**
     * 原消费者积分
     */
    private String sourcePerPoint;
    /**
     * 原企业应付积分款
     */
    private String sourceEntPoint;

    /**
     * 企业应付积分款
     */
    private String entPoint;
    /**
     * 原交易时间
     */
    private String sourceTransDate;


    private Integer channelType;


    private Integer equipmentType;

    /*备注 */
    private String remark;

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
     * 获取消费者积分
     *
     * @return perPoint 消费者积分
     */
    public String getPerPoint() {
        return perPoint;
    }

    /**
     * 设置消费者积分
     *
     * @param perPoint 消费者积分
     */
    public void setPerPoint(String perPoint) {
        this.perPoint = perPoint;
    }

    /**
     * 获取企业应付积分款
     *
     * @return entPoint 企业应付积分款
     */
    public String getEntPoint() {
        return entPoint;
    }

    /**
     * 设置企业应付积分款
     *
     * @param entPoint 企业应付积分款
     */
    public void setEntPoint(String entPoint) {
        this.entPoint = entPoint;
    }


    /**
     * 获取原交易时间
     *
     * @return sourceTransDate 原交易时间
     */
    public String getSourceTransDate() {
        return sourceTransDate;
    }

    /**
     * 设置原交易时间
     *
     * @param sourceTransDate 原交易时间
     */
    public void setSourceTransDate(String sourceTransDate) {
        this.sourceTransDate = sourceTransDate;
    }

    /**
     * 获取渠道类型
     *
     * @return channelType 渠道类型
     */
    public Integer getChannelType()
    {
        return channelType;
    }

    /**
     * 设置渠道类型
     *
     * @param channelType
     *            渠道类型
     */
    public void setChannelType(Integer channelType)
    {
        this.channelType = channelType;
    }

    /**
     * 获取设备类型
     *
     * @return equipmentType 设备类型
     */
    public Integer getEquipmentType()
    {
        return equipmentType;
    }

    /**
     * 设置设备类型
     *
     * @param equipmentType
     *            设备类型
     */
    public void setEquipmentType(Integer equipmentType)
    {
        this.equipmentType = equipmentType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSourceTransAmount() {
        return sourceTransAmount;
    }

    public void setSourceTransAmount(String sourceTransAmount) {
        this.sourceTransAmount = sourceTransAmount;
    }

    public String getSourceEntPoint() {
        return sourceEntPoint;
    }

    public void setSourceEntPoint(String sourceEntPoint) {
        this.sourceEntPoint = sourceEntPoint;
    }

    public String getSourcePerPoint() {
        return sourcePerPoint;
    }

    public void setSourcePerPoint(String sourcePerPoint) {
        this.sourcePerPoint = sourcePerPoint;
    }
}
