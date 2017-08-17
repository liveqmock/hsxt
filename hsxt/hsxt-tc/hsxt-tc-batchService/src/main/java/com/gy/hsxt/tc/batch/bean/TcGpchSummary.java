package com.gy.hsxt.tc.batch.bean;

import java.math.BigDecimal;

public class TcGpchSummary {
    private Long sumId;

    private String accDate;

    private Long gpTranNum;

    private BigDecimal gpTranAmount;

    private Long chTranNum;

    private BigDecimal chTranAmount;

    private Long flatNum;

    private BigDecimal flatAmount;

    private Long chHaveNum = 0l;;

    private BigDecimal chHaveAmount;

    private Long gpHaveNum = 0l;;

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

    public Long getChTranNum() {
        return chTranNum;
    }

    public void setChTranNum(Long chTranNum) {
        this.chTranNum = chTranNum;
    }

    public BigDecimal getChTranAmount() {
        return chTranAmount;
    }

    public void setChTranAmount(BigDecimal chTranAmount) {
        this.chTranAmount = chTranAmount;
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

    public Long getChHaveNum() {
        return chHaveNum;
    }

    public void setChHaveNum(Long chHaveNum) {
        this.chHaveNum = chHaveNum;
    }

    public BigDecimal getChHaveAmount() {
        return chHaveAmount;
    }

    public void setChHaveAmount(BigDecimal chHaveAmount) {
        this.chHaveAmount = chHaveAmount;
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