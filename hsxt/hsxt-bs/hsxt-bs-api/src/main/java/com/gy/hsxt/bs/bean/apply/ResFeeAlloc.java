/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: ResFeeAlloc
 * @Description: 资源费分配记录
 * 
 * @author: xiaofl
 * @date: 2016-1-14 下午3:28:53
 * @version V1.0
 */
public class ResFeeAlloc implements Serializable {

    private static final long serialVersionUID = 1688124957806081264L;

    /** 记录编号 **/
    private String detailId;

    /** 申报单编号 **/
    private String totalId;

    /** 企业客户号 **/
    private String custId;

    /** 客户类型 **/
    private Integer custType;

    /** 企业互生号 **/
    private String resNo;

    /** 企业名称 **/
    private String custName;

    /** 分配金额 **/
    private String allocAmt;

    /** 币种代码 **/
    private String currencyCode;

    /** 是否扣税 **/
    private Boolean needTax;

    /** 扣税比例 **/
    private String taxRate;

    /** 扣税金额 **/
    private String taxAmount;

    /** 货币转换比率 **/
    private String exChangeRate;

    /** 分配日期 **/
    private String allocDate;

    /** 分配类型 **/
    private int allocType;

    /** 是否已分配 **/
    private boolean allocated;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getTotalId() {
        return totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAllocAmt() {
        return allocAmt;
    }

    public void setAllocAmt(String allocAmt) {
        this.allocAmt = allocAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean getNeedTax() {
        return needTax;
    }

    public void setNeedTax(Boolean needTax) {
        this.needTax = needTax;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getExChangeRate() {
        return exChangeRate;
    }

    public void setExChangeRate(String exChangeRate) {
        this.exChangeRate = exChangeRate;
    }

    public String getAllocDate() {
        return allocDate;
    }

    public void setAllocDate(String allocDate) {
        this.allocDate = allocDate;
    }

    public int getAllocType() {
        return allocType;
    }

    public void setAllocType(int allocType) {
        this.allocType = allocType;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
