/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import com.alibaba.fastjson.JSON;

/**
 * 记帐分解实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: AccountDetail
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-1 下午6:35:18
 * @version V1.0
 */
public class AccountDetail implements java.io.Serializable {
    private static final long serialVersionUID = 2733828028111645592L;

    /** 记账编号 **/
    private String accountNo;

    /** 原业务编号 **/
    private String bizNo;

    /** 原业务类型 **/
    private String transType;

    /** 客户号 **/
    private String custId;

    /** 互生号 **/
    private String hsResNo;

    /** 客户名称 **/
    private String custName;

    /** 客户类型 **/
    private Integer custType;

    /** 账户类型 **/
    private String accType;

    /** 增加金额 **/
    private String addAmount;

    /** 减少金额 **/
    private String decAmount;

    /** 货币币种 **/
    private String currencyCode;

    /** 会计日期 **/
    private String fiscalDate;

    /** 备注 **/
    private String remark;

    /** 记账状态 false：未记账(非实时记账) true：已记账 **/
    private Boolean status;

    /** 创建时间 **/
    private String createdDate;

    /** 更新时间，非实时记账时需要 **/
    private String updatedDate;

    /** 记帐交易流水号 **/
    private String accountTransNo;

    /** 保底值 **/
    private String guaranteedValue;

    public String getAccountTransNo() {
        return accountTransNo;
    }

    public void setAccountTransNo(String accountTransNo) {
        this.accountTransNo = accountTransNo;
    }

    public AccountDetail() {
        super();
    }

    /**
     * 实时记帐：多一个fiscalDate参数
     */
    public AccountDetail(String accountNo, String bizNo, String transType, String custId, String hsResNo,
            String custName, Integer custType, String accType, String addAmount, String decAmount, String currencyCode,
            String fiscalDate, String remark, Boolean status) {
        super();
        this.accountNo = accountNo;
        this.bizNo = bizNo;
        this.transType = transType;
        this.custId = custId;
        this.hsResNo = hsResNo;
        this.custName = custName;
        this.custType = custType;
        this.accType = accType;
        this.addAmount = addAmount;
        this.decAmount = decAmount;
        this.currencyCode = currencyCode;
        this.fiscalDate = fiscalDate;
        this.remark = remark;
        this.status = status;
    }

    public String getGuaranteedValue() {
        return guaranteedValue;
    }

    public void setGuaranteedValue(String guaranteedValue) {
        this.guaranteedValue = guaranteedValue;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
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

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(String addAmount) {
        this.addAmount = addAmount;
    }

    public String getDecAmount() {
        return decAmount;
    }

    public void setDecAmount(String decAmount) {
        this.decAmount = decAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFiscalDate() {
        return fiscalDate;
    }

    public void setFiscalDate(String fiscalDate) {
        this.fiscalDate = fiscalDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
