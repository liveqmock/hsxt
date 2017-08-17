package com.gy.hsxt.pg.mapper.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TPgBankTransOrder {
	private String orderSeqId;

	private String bankOrderNo;

	private String bankOrderSeqId;

	private String bankBatchNo;

	private Integer merType;

	private BigDecimal transAmount;

	private Integer transStatus;

	private Date transDate;

	private String currencyCode;

	private String outAccName;

	private String outAccNo;

	private String inAccNo;

	private String inAccName;

	private String inAccBankName;

	private String inAccBankNode;

	private String inAccProvinceCode;

	private String inAccCityName;

	private BigDecimal bankFee;

	private Date bankSubmitDate;

	private Date bankAccountDate;

	private String notifyMobile;

	private Integer unionFlag;

	private String sysFlag;

	private Integer addrFlag;

	private String transDesc;

	private String failReason;

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
		this.bankOrderSeqId = bankOrderSeqId == null ? null : bankOrderSeqId
				.trim();
	}

	public String getBankBatchNo() {
		return bankBatchNo;
	}

	public void setBankBatchNo(String bankBatchNo) {
		this.bankBatchNo = bankBatchNo == null ? null : bankBatchNo.trim();
	}

	public Integer getMerType() {
		return merType;
	}

	public void setMerType(Integer merType) {
		this.merType = merType;
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

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public String getOutAccName() {
		return outAccName;
	}

	public void setOutAccName(String outAccName) {
		this.outAccName = outAccName == null ? null : outAccName.trim();
	}

	public String getOutAccNo() {
		return outAccNo;
	}

	public void setOutAccNo(String outAccNo) {
		this.outAccNo = outAccNo == null ? null : outAccNo.trim();
	}

	public String getInAccNo() {
		return inAccNo;
	}

	public void setInAccNo(String inAccNo) {
		this.inAccNo = inAccNo == null ? null : inAccNo.trim();
	}

	public String getInAccName() {
		return inAccName;
	}

	public void setInAccName(String inAccName) {
		this.inAccName = inAccName == null ? null : inAccName.trim();
	}

	public String getInAccBankName() {
		return inAccBankName;
	}

	public void setInAccBankName(String inAccBankName) {
		this.inAccBankName = inAccBankName == null ? null : inAccBankName
				.trim();
	}

	public String getInAccBankNode() {
		return inAccBankNode;
	}

	public void setInAccBankNode(String inAccBankNode) {
		this.inAccBankNode = inAccBankNode == null ? null : inAccBankNode
				.trim();
	}

	public String getInAccProvinceCode() {
		return inAccProvinceCode;
	}

	public void setInAccProvinceCode(String inAccProvinceCode) {
		this.inAccProvinceCode = inAccProvinceCode == null ? null
				: inAccProvinceCode.trim();
	}

	public String getInAccCityName() {
		return inAccCityName;
	}

	public void setInAccCityName(String inAccCityName) {
		this.inAccCityName = inAccCityName == null ? null : inAccCityName
				.trim();
	}

	public BigDecimal getBankFee() {
		return bankFee;
	}

	public void setBankFee(BigDecimal bankFee) {
		this.bankFee = bankFee;
	}

	public Date getBankSubmitDate() {
		return bankSubmitDate;
	}

	public void setBankSubmitDate(Date bankSubmitDate) {
		this.bankSubmitDate = bankSubmitDate;
	}

	public Date getBankAccountDate() {
		return bankAccountDate;
	}

	public void setBankAccountDate(Date bankAccountDate) {
		this.bankAccountDate = bankAccountDate;
	}

	public String getNotifyMobile() {
		return notifyMobile;
	}

	public void setNotifyMobile(String notifyMobile) {
		this.notifyMobile = notifyMobile == null ? null : notifyMobile.trim();
	}

	public Integer getUnionFlag() {
		return unionFlag;
	}

	public void setUnionFlag(Integer unionFlag) {
		this.unionFlag = unionFlag;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag == null ? null : sysFlag.trim();
	}

	public Integer getAddrFlag() {
		return addrFlag;
	}

	public void setAddrFlag(Integer addrFlag) {
		this.addrFlag = addrFlag;
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc == null ? null : transDesc.trim();
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason == null ? null : failReason.trim();
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
		return "TPgPinganOrder [orderSeqId=" + orderSeqId + ", bankOrderNo="
				+ bankOrderNo + ", bankOrderSeqId=" + bankOrderSeqId
				+ ", bankBatchNo=" + bankBatchNo + ", merType=" + merType
				+ ", transAmount=" + transAmount + ", transStatus="
				+ transStatus + ", transDate=" + transDate + ", currencyCode="
				+ currencyCode + ", outAccName=" + outAccName + ", outAccNo="
				+ outAccNo + ", inAccNo=" + inAccNo + ", inAccName="
				+ inAccName + ", inAccBankName=" + inAccBankName
				+ ", inAccBankNode=" + inAccBankNode + ", inAccProvinceCode="
				+ inAccProvinceCode + ", inAccCityName=" + inAccCityName
				+ ", bankFee=" + bankFee + ", bankSubmitDate=" + bankSubmitDate
				+ ", bankAccountDate=" + bankAccountDate + ", notifyMobile="
				+ notifyMobile + ", unionFlag=" + unionFlag + ", sysFlag="
				+ sysFlag + ", addrFlag=" + addrFlag + ", transDesc="
				+ transDesc + ", failReason=" + failReason + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + "]";
	}
}