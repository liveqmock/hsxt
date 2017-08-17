package com.gy.hsxt.gp.mapper.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TGpTransOrder {
	
	private String transSeqId;

	private String orderNo;

	private Date orderDate;

	private String batchNo;

	private String bankOrderNo;

	private String bankOrderSeqId;

	private String bankBatchNo;

	private String merId;

	private BigDecimal transAmount;

	private Integer transStatus;

	private Date transDate;

	private String currencyCode;

	private Integer outBankAccType;

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

	private String srcSubsysId;
	
	private Date orderDateS;
	
	private Date transDateS;

	public String getSrcSubsysId() {
		return srcSubsysId;
	}

	public void setSrcSubsysId(String srcSubsysId) {
		this.srcSubsysId = srcSubsysId;
	}

	public String getTransSeqId() {
		return transSeqId;
	}

	public void setTransSeqId(String transSeqId) {
		this.transSeqId = transSeqId == null ? null : transSeqId.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
		
		// 仅用于对账文件生成
		this.setOrderDateS(orderDate);
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
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

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId == null ? null : merId.trim();
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
		
		// 仅用于对账文件生成
		this.setTransDateS(transDate);
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public Integer getOutBankAccType() {
		return outBankAccType;
	}

	public void setOutBankAccType(Integer outBankAccType) {
		this.outBankAccType = outBankAccType;
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

	public Date getOrderDateS() {
		return orderDateS;
	}

	public void setOrderDateS(Date orderDateS) {
		this.orderDateS = orderDateS;
	}

	public Date getTransDateS() {
		return transDateS;
	}

	public void setTransDateS(Date transDateS) {
		this.transDateS = transDateS;
	}

	@Override
	public String toString() {
		return "TGpTransOrder [transSeqId=" + transSeqId + ", orderNo="
				+ orderNo + ", orderDate=" + orderDate + ", batchNo=" + batchNo
				+ ", bankOrderNo=" + bankOrderNo + ", bankOrderSeqId="
				+ bankOrderSeqId + ", bankBatchNo=" + bankBatchNo + ", merId="
				+ merId + ", transAmount=" + transAmount + ", transStatus="
				+ transStatus + ", transDate=" + transDate + ", currencyCode="
				+ currencyCode + ", outBankAccType=" + outBankAccType
				+ ", outAccNo=" + outAccNo + ", inAccNo=" + inAccNo
				+ ", inAccName=" + inAccName + ", inAccBankName="
				+ inAccBankName + ", inAccBankNode=" + inAccBankNode
				+ ", inAccProvinceCode=" + inAccProvinceCode
				+ ", inAccCityName=" + inAccCityName + ", bankFee=" + bankFee
				+ ", bankSubmitDate=" + bankSubmitDate + ", bankAccountDate="
				+ bankAccountDate + ", notifyMobile=" + notifyMobile
				+ ", unionFlag=" + unionFlag + ", sysFlag=" + sysFlag
				+ ", addrFlag=" + addrFlag + ", transDesc=" + transDesc
				+ ", failReason=" + failReason + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", srcSubsysId="
				+ srcSubsysId + "]";
	}

}