package com.gy.hsxt.gp.mapper.vo;

import java.util.Date;

public class TGpSequence {
	private Integer seqId;

	private Integer seqType;

	private String sysInstanceNo;

	private String seqNumber;

	private Date createdDate;

	private Date updatedDate;

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	public Integer getSeqType() {
		return seqType;
	}

	public void setSeqType(Integer seqType) {
		this.seqType = seqType;
	}

	public String getSysInstanceNo() {
		return sysInstanceNo;
	}

	public void setSysInstanceNo(String sysInstanceNo) {
		this.sysInstanceNo = sysInstanceNo;
	}

	public String getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(String seqNumber) {
		this.seqNumber = seqNumber;
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