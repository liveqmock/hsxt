package com.gy.hsxt.tc.batch.bean;

import java.math.BigDecimal;

public class TcBsacSummary {
    private Long sumId;

    private String accDate;

    private Long acTranNum;

    private BigDecimal acTranAmount;

    private Long bsTranNum;

    private BigDecimal bsTranAmount;

    private Long flatNum;

    private BigDecimal flatAmount;

    private Long bsHaveNum;

    private BigDecimal bsHaveAmount;

    private Long acHaveNum;

    private BigDecimal acHaveAmount;

    private Object updatedDate;

    private Short tcResult = (short) 0;

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

    public Long getAcTranNum() {
        return acTranNum;
    }

    public void setAcTranNum(Long acTranNum) {
        this.acTranNum = acTranNum;
    }

    public BigDecimal getAcTranAmount() {
        return acTranAmount;
    }

    public void setAcTranAmount(BigDecimal acTranAmount) {
        this.acTranAmount = acTranAmount;
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

    public Long getAcHaveNum() {
        return acHaveNum;
    }

    public void setAcHaveNum(Long acHaveNum) {
        this.acHaveNum = acHaveNum;
    }

    public BigDecimal getAcHaveAmount() {
        return acHaveAmount;
    }

    public void setAcHaveAmount(BigDecimal acHaveAmount) {
        this.acHaveAmount = acHaveAmount;
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