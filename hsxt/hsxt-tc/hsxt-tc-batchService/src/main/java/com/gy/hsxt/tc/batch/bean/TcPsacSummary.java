package com.gy.hsxt.tc.batch.bean;

import java.math.BigDecimal;

public class TcPsacSummary {
    private Long sumId;

    private String accDate;

    private Long acTranNum;

    private BigDecimal acTranAmount;

    private Long psTranNum;

    private BigDecimal psTranAmount;

    private Long flatNum;

    private BigDecimal flatAmount;

    private Long psHaveNum;

    private BigDecimal psHaveAmount;

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

    public Long getPsTranNum() {
        return psTranNum;
    }

    public void setPsTranNum(Long psTranNum) {
        this.psTranNum = psTranNum;
    }

    public BigDecimal getPsTranAmount() {
        return psTranAmount;
    }

    public void setPsTranAmount(BigDecimal psTranAmount) {
        this.psTranAmount = psTranAmount;
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

    public Long getPsHaveNum() {
        return psHaveNum;
    }

    public void setPsHaveNum(Long psHaveNum) {
        this.psHaveNum = psHaveNum;
    }

    public BigDecimal getPsHaveAmount() {
        return psHaveAmount;
    }

    public void setPsHaveAmount(BigDecimal psHaveAmount) {
        this.psHaveAmount = psHaveAmount;
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