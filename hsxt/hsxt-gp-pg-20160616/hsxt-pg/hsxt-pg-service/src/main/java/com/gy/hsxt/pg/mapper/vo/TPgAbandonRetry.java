package com.gy.hsxt.pg.mapper.vo;

import java.util.Date;

public class TPgAbandonRetry {
    private Integer retryId;

    private String businessId;

    private Integer retryBusinessType;

    private Integer retryStatus;

    private Integer retryCounts;

    private Date lastRetryDate;

    private Date nextRetryDate;

    private Date createdDate;

    private Date updatedDate;

    public Integer getRetryId() {
        return retryId;
    }

    public void setRetryId(Integer retryId) {
        this.retryId = retryId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public Integer getRetryBusinessType() {
        return retryBusinessType;
    }

    public void setRetryBusinessType(Integer retryBusinessType) {
        this.retryBusinessType = retryBusinessType;
    }

    public Integer getRetryStatus() {
        return retryStatus;
    }

    public void setRetryStatus(Integer retryStatus) {
        this.retryStatus = retryStatus;
    }

    public Integer getRetryCounts() {
        return retryCounts;
    }

    public void setRetryCounts(Integer retryCounts) {
        this.retryCounts = retryCounts;
    }

    public Date getLastRetryDate() {
        return lastRetryDate;
    }

    public void setLastRetryDate(Date lastRetryDate) {
        this.lastRetryDate = lastRetryDate;
    }

    public Date getNextRetryDate() {
        return nextRetryDate;
    }

    public void setNextRetryDate(Date nextRetryDate) {
        this.nextRetryDate = nextRetryDate;
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