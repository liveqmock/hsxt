package com.gy.hsxt.pg.mapper.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TPgBankcardBinding {

	private String bankCardNo;

	private Integer bankCardType;

	private String bankId;

	private String bankName;

	private String bindingNo;

	private BigDecimal transLimit;

	private BigDecimal sumLimit;

	private Date expireDate;

	private Integer bindingStatus;

	private Date createdDate;

	private Date updatedDate;

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public Integer getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(Integer bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	public BigDecimal getTransLimit() {
		return transLimit;
	}

	public void setTransLimit(BigDecimal transLimit) {
		this.transLimit = transLimit;
	}

	public BigDecimal getSumLimit() {
		return sumLimit;
	}

	public void setSumLimit(BigDecimal sumLimit) {
		this.sumLimit = sumLimit;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getBindingStatus() {
		return bindingStatus;
	}

	public void setBindingStatus(Integer bindingStatus) {
		this.bindingStatus = bindingStatus;
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
}