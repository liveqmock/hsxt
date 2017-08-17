package com.gy.hsxt.tc.batch.bean;

import java.math.BigDecimal;

public class TcBsgpPaySummary {
    private Long sumId;

    private String accDate;

    private Long gpTranNum;

    private BigDecimal gpTranAmount;

    private Long bsTranNum;

    private BigDecimal bsTranAmount;

    private Long flatNum;

    private BigDecimal flatAmount;

    private Long bsHaveNum=0l;

    private BigDecimal bsHaveAmount;

    private Long gpHaveNum=0l;

    private BigDecimal gpHaveAmount;

    private Object updatedDate;

    private Short tcResult=(short)0;

    public Long getSumId() {
        return sumId;
    }

    public void setSumId(Long sumId) {
        this.sumId = sumId;
    }

    public String getAccDate() {
        return accDate;
    }

    public void setAccDate(String accDate) {
        this.accDate = accDate;
    }

    public Long getGpTranNum() {
        return gpTranNum;
    }

    public void setGpTranNum(Long gpTranNum) {
        this.gpTranNum = gpTranNum;
    }

    public BigDecimal getGpTranAmount() {
        return gpTranAmount;
    }

    public void setGpTranAmount(BigDecimal gpTranAmount) {
        this.gpTranAmount = gpTranAmount;
    }

    public Long getBsTranNum() {
        return bsTranNum;
    }

    public void setBsTranNum(Long bsTranNum) {
        this.bsTranNum = bsTranNum;
    }

    public BigDecimal getBsTranAmount() {
        return bsTranAmount;
    }

    public void setBsTranAmount(BigDecimal bsTranAmount) {
        this.bsTranAmount = bsTranAmount;
    }

    public Long getFlatNum() {
        return flatNum;
    }

    public void setFlatNum(Long flatNum) {
        this.flatNum = flatNum;
    }

    public BigDecimal getFlatAmount() {
        return flatAmount;
    }

    public void setFlatAmount(BigDecimal flatAmount) {
        this.flatAmount = flatAmount;
    }

    public Long getBsHaveNum() {
        return bsHaveNum;
    }

    public void setBsHaveNum(Long bsHaveNum) {
        this.bsHaveNum = bsHaveNum;
    }

    public BigDecimal getBsHaveAmount() {
        return bsHaveAmount;
    }

    public void setBsHaveAmount(BigDecimal bsHaveAmount) {
        this.bsHaveAmount = bsHaveAmount;
    }

    public Long getGpHaveNum() {
        return gpHaveNum;
    }

    public void setGpHaveNum(Long gpHaveNum) {
        this.gpHaveNum = gpHaveNum;
    }

    public BigDecimal getGpHaveAmount() {
        return gpHaveAmount;
    }

    public void setGpHaveAmount(BigDecimal gpHaveAmount) {
        this.gpHaveAmount = gpHaveAmount;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Short getTcResult() {
        return tcResult;
    }

    public void setTcResult(Short tcResult) {
        this.tcResult = tcResult;
    }
}