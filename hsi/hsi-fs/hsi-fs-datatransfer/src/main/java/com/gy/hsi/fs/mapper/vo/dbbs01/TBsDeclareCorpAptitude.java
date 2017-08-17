package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.Date;

public class TBsDeclareCorpAptitude {
    private String aptitudeId;

    private String applyId;

    private Short aptitudeType;

    private String aptitudeName;

    private String fileId;

    private Date createdDate;

    private String createdby;

    private Date updatedDate;

    private String updatedby;

    public String getAptitudeId() {
        return aptitudeId;
    }

    public void setAptitudeId(String aptitudeId) {
        this.aptitudeId = aptitudeId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Short getAptitudeType() {
        return aptitudeType;
    }

    public void setAptitudeType(Short aptitudeType) {
        this.aptitudeType = aptitudeType;
    }

    public String getAptitudeName() {
        return aptitudeName;
    }

    public void setAptitudeName(String aptitudeName) {
        this.aptitudeName = aptitudeName;
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