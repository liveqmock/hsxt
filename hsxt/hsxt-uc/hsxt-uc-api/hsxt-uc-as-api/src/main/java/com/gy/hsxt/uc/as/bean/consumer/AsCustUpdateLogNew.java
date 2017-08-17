package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AsCustUpdateLogNew  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7955163905260459138L;

	private String logId;

    private String perCustId;

    private String countryName;

    private BigDecimal idType;

    private String idNo;

    private BigDecimal sex;

    private String perName;

    private String idValidDate;

    private String idIssueOrg;

    private String residentAddr;

    private String certificateFront;

    private String certificateBack;

    private String certificateHandhold;

    private String job;

    private String birthPlace;

    private String issuePlace;

    private String entName;

    private String entRegAddr;

    private String entType;

    private String entBuildDate;

    private String authRemark;

    private String isactive;

    private Timestamp createDate;

    private String createdby;

    private Timestamp updateDate;

    private String updatedby;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getPerCustId() {
        return perCustId;
    }

    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public BigDecimal getIdType() {
        return idType;
    }

    public void setIdType(BigDecimal idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public BigDecimal getSex() {
        return sex;
    }

    public void setSex(BigDecimal sex) {
        this.sex = sex;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getIdValidDate() {
        return idValidDate;
    }

    public void setIdValidDate(String idValidDate) {
        this.idValidDate = idValidDate;
    }

    public String getIdIssueOrg() {
        return idIssueOrg;
    }

    public void setIdIssueOrg(String idIssueOrg) {
        this.idIssueOrg = idIssueOrg;
    }

    public String getResidentAddr() {
        return residentAddr;
    }

    public void setResidentAddr(String residentAddr) {
        this.residentAddr = residentAddr;
    }

    public String getCertificateFront() {
        return certificateFront;
    }

    public void setCertificateFront(String certificateFront) {
        this.certificateFront = certificateFront;
    }

    public String getCertificateBack() {
        return certificateBack;
    }

    public void setCertificateBack(String certificateBack) {
        this.certificateBack = certificateBack;
    }

    public String getCertificateHandhold() {
        return certificateHandhold;
    }

    public void setCertificateHandhold(String certificateHandhold) {
        this.certificateHandhold = certificateHandhold;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntRegAddr() {
        return entRegAddr;
    }

    public void setEntRegAddr(String entRegAddr) {
        this.entRegAddr = entRegAddr;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getEntBuildDate() {
        return entBuildDate;
    }

    public void setEntBuildDate(String entBuildDate) {
        this.entBuildDate = entBuildDate;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }
}