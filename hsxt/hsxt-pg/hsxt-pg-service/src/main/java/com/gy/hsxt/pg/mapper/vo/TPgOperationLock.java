package com.gy.hsxt.pg.mapper.vo;

import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;

public class TPgOperationLock {

	private Integer lockId;

	private String businessId;

	private Integer businessType;

	private Date createdDate;

	private Date updatedDate;

	public Integer getLockId() {
		return lockId;
	}

	public void setLockId(Integer lockId) {
		this.lockId = lockId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId == null ? null : businessId.trim();
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
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
	
	/**
	 * 判断锁释放已经过期(仅5秒有效期)
	 * 
	 * @return
	 */
	public boolean isExpired() {
		boolean isExpired = DateUtils.addSeconds(createdDate, 5).getTime() <= DateUtils
				.getCurrentDate().getTime();
		
		return isExpired;
	}

	@Override
	public String toString() {
		return "TPgOperationLock [lockId=" + lockId + ", businessId="
				+ businessId + ", businessType=" + businessType
				+ ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}
	
}