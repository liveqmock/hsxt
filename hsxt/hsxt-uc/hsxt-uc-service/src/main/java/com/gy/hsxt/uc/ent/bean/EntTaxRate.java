package com.gy.hsxt.uc.ent.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class EntTaxRate {
    private String entCustId;

    private BigDecimal entTaxRate;

    private String isactive;

    private Timestamp createDate;

    private String createdby;

    private Timestamp updateDate;

    private String updatedby;

    private Timestamp activeDate;

    private String entResNo;

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId == null ? null : entCustId.trim();
    }

    public BigDecimal getEntTaxRate() {
        return entTaxRate;
    }

    public void setEntTaxRate(BigDecimal entTaxRate) {
        this.entTaxRate = entTaxRate;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive == null ? null : isactive.trim();
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
        this.createdby = createdby == null ? null : createdby.trim();
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
        this.updatedby = updatedby == null ? null : updatedby.trim();
    }

    public Timestamp getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Timestamp activeDate) {
        this.activeDate = activeDate;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo == null ? null : entResNo.trim();
    }
}