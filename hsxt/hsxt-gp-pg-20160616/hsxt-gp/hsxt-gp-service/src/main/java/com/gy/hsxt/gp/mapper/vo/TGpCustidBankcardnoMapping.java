package com.gy.hsxt.gp.mapper.vo;

import java.util.Date;

import com.gy.hsxt.gp.common.utils.StringUtils;

public class TGpCustidBankcardnoMapping {
	private Long mappingId;

	private String custId;

	private String bankOrderNo;

	private String bankCardNo;

	private Integer bankCardType;

	private String bankId;

	private Boolean bindingOk;

	private Date createdDate;

	private Date updatedDate;

	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = StringUtils.avoidNullTrim(custId);
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = StringUtils.avoidNullTrim(bankOrderNo);
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = StringUtils.avoidNullTrim(bankCardNo);
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
		this.bankId = StringUtils.avoidNullTrim(bankId);
	}

	public Boolean getBindingOk() {
		return bindingOk;
	}

	public void setBindingOk(Boolean bindingOk) {
		this.bindingOk = bindingOk;
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