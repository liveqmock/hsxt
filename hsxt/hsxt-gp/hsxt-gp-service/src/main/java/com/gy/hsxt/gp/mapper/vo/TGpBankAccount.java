package com.gy.hsxt.gp.mapper.vo;

import java.util.Date;

public class TGpBankAccount {
	private Integer bankAccId;

	private String bankAccNo;

	private String bankAccName;

	private Integer bankAccType;

	private String bankAccCityName;

	private String bankAccCityCode;

	private String bankCode;

	private String currencyCode;

	private Integer isactive;

	private Date createdDate;

	private Date updatedDate;

	public Integer getBankAccId() {
		return bankAccId;
	}

	public void setBankAccId(Integer bankAccId) {
		this.bankAccId = bankAccId;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo == null ? null : bankAccNo.trim();
	}

	public String getBankAccName() {
		return bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName == null ? null : bankAccName.trim();
	}

	public Integer getBankAccType() {
		return bankAccType;
	}

	public void setBankAccType(Integer bankAccType) {
		this.bankAccType = bankAccType;
	}

	public String getBankAccCityName() {
		return bankAccCityName;
	}

	public void setBankAccCityName(String bankAccCityName) {
		this.bankAccCityName = bankAccCityName == null ? null : bankAccCityName
				.trim();
	}

	public String getBankAccCityCode() {
		return bankAccCityCode;
	}

	public void setBankAccCityCode(String bankAccCityCode) {
		this.bankAccCityCode = bankAccCityCode == null ? null : bankAccCityCode
				.trim();
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public Integer getIsactive() {
		return isactive;
	}

	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
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
		return "TGpBankAccount [bankAccId=" + bankAccId + ", bankAccNo="
				+ bankAccNo + ", bankAccName=" + bankAccName + ", bankAccType="
				+ bankAccType + ", bankAccCityName=" + bankAccCityName
				+ ", bankAccCityCode=" + bankAccCityCode + ", bankCode="
				+ bankCode + ", currencyCode=" + currencyCode + ", isactive="
				+ isactive + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}

}