package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.Date;

public class TBsFilingAptitude {
    private String filingAptId;

    private String applyId;

    private Short aptType;

    private String aptName;

    private String fileId;

    private Date createdDate;

    private String createdby;

    private Date updatedDate;

    private String updatedby;

    public String getFilingAptId() {
        return filingAptId;
    }

    public void setFilingAptId(String filingAptId) {
        this.filingAptId = filingAptId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Short getAptType() {
        return aptType;
    }

    public void setAptType(Short aptType) {
        this.aptType = aptType;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }
}