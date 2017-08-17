/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.bean
 * @ClassName: DeclareEntStatus
 * @Description: 申报状态标识
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:46:55
 * @version V1.0
 */
public class DeclareEntStatus implements Serializable {

    private static final long serialVersionUID = -3507584014964020744L;

    /** 申请编号 **/
    private String applyId;

    /** 被申报企业客户号 **/
    private String toEntCustId;

    /** 申报状态 **/
    private Integer appStatus;

    /** 被申报办理期限(截止办理日期) **/
    private Date expiryDate;

    /** 资源费订单号 **/
    private String orderNo;

    /** 资源费方案编号 **/
    private String resFeeId;

    /** 资源费分配方式 **/
    private Integer resFeeAllocMode;

    /** 资源费收费方式 **/
    private Integer resFeeChargeMode;

    /** 是否已收费 **/
    private Boolean chargeFlag;

    /** 企业开户标识 **/
    private Boolean openAccFlag;

    /** 增值开启标识 **/
    private Boolean openVasFlag;

    /** 操作员客户号 **/
    private String operator;

    /** 开启系统日期 **/
    private String openAccDate;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getToEntCustId() {
        return toEntCustId;
    }

    public void setToEntCustId(String toEntCustId) {
        this.toEntCustId = toEntCustId;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getResFeeId() {
        return resFeeId;
    }

    public void setResFeeId(String resFeeId) {
        this.resFeeId = resFeeId;
    }

    public Integer getResFeeAllocMode() {
        return resFeeAllocMode;
    }

    public void setResFeeAllocMode(Integer resFeeAllocMode) {
        this.resFeeAllocMode = resFeeAllocMode;
    }

    public Integer getResFeeChargeMode() {
        return resFeeChargeMode;
    }

    public void setResFeeChargeMode(Integer resFeeChargeMode) {
        this.resFeeChargeMode = resFeeChargeMode;
    }

    public Boolean getChargeFlag() {
        return chargeFlag;
    }

    public void setChargeFlag(Boolean chargeFlag) {
        this.chargeFlag = chargeFlag;
    }

    public Boolean getOpenAccFlag() {
        return openAccFlag;
    }

    public void setOpenAccFlag(Boolean openAccFlag) {
        this.openAccFlag = openAccFlag;
    }

    public Boolean getOpenVasFlag() {
        return openVasFlag;
    }

    public void setOpenVasFlag(Boolean openVasFlag) {
        this.openVasFlag = openVasFlag;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOpenAccDate() {
        return openAccDate;
    }

    public void setOpenAccDate(String openAccDate) {
        this.openAccDate = openAccDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
