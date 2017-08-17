package com.gy.hsxt.pg.mapper.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TPgChinapayOrder {
    private String orderSeqId;

    private String bankOrderNo;

    private String bankOrderSeqId;

    private String origBankOrderNo;

    private Integer merType;

    private Integer transType;

    private BigDecimal transAmount;

    private Integer transStatus;

    private String bankOrderStatus;

    private Date transDate;

    private Integer payChannel;

    private String currencyCode;

    private String failReason;

    private String privObligate;
    
    private String privObligate2;

    private String jumpUrl;

    private Date createdDate;

    private Date updatedDate;

    public String getOrderSeqId() {
        return orderSeqId;
    }

    public void setOrderSeqId(String orderSeqId) {
        this.orderSeqId = orderSeqId == null ? null : orderSeqId.trim();
    }

    public String getBankOrderNo() {
        return bankOrderNo;
    }

    public void setBankOrderNo(String bankOrderNo) {
        this.bankOrderNo = bankOrderNo == null ? null : bankOrderNo.trim();
    }

    public String getBankOrderSeqId() {
        return bankOrderSeqId;
    }

    public void setBankOrderSeqId(String bankOrderSeqId) {
        this.bankOrderSeqId = bankOrderSeqId == null ? null : bankOrderSeqId.trim();
    }

    public String getOrigBankOrderNo() {
        return origBankOrderNo;
    }

    public void setOrigBankOrderNo(String origBankOrderNo) {
        this.origBankOrderNo = origBankOrderNo == null ? null : origBankOrderNo.trim();
    }

    public Integer getMerType() {
        return merType;
    }

    public void setMerType(Integer merType) {
        this.merType = merType;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public Integer getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }

    public String getBankOrderStatus() {
        return bankOrderStatus;
    }

    public void setBankOrderStatus(String bankOrderStatus) {
        this.bankOrderStatus = bankOrderStatus == null ? null : bankOrderStatus.trim();
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getPrivObligate() {
        return privObligate;
    }

    public void setPrivObligate(String privObligate) {
        this.privObligate = privObligate == null ? null : privObligate.trim();
    }
    
    public String getPrivObligate2() {
        return privObligate2;
    }

    public void setPrivObligate2(String privObligate2) {
        this.privObligate2 = privObligate2 == null ? null : privObligate2.trim();
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl == null ? null : jumpUrl.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

	@Override
	public String toString() {
		return "TPgChinapayOrder [orderSeqId=" + orderSeqId + ", bankOrderNo="
				+ bankOrderNo + ", bankOrderSeqId=" + bankOrderSeqId
				+ ", origBankOrderNo=" + origBankOrderNo + ", merType="
				+ merType + ", transType=" + transType + ", transAmount="
				+ transAmount + ", transStatus=" + transStatus
				+ ", bankOrderStatus=" + bankOrderStatus + ", transDate="
				+ transDate + ", payChannel=" + payChannel + ", currencyCode="
				+ currencyCode + ", failReason=" + failReason
				+ ", privObligate=" + privObligate + ", jumpUrl=" + jumpUrl
				+ ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}
    
}