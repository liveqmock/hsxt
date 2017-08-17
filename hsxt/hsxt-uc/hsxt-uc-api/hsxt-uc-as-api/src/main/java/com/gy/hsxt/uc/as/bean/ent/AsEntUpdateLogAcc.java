package com.gy.hsxt.uc.as.bean.ent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AsEntUpdateLogAcc  implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1419754212614430608L;

	private String logId;

    private BigDecimal accId;

    private String entCustId;

    private String entResNo;

    private String bankName;

    private String bankCode;

    private String bankAccName;

    private String bankAccNo;

    private BigDecimal bankCardType;

    private String countryNo;

    private String provinceNo;

    private String cityNo;

    private String bankBranch;

    private BigDecimal isDefaultAccount;

    private BigDecimal isValidAccount;

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

    public BigDecimal getAccId() {
        return accId;
    }

    public void setAccId(BigDecimal accId) {
        this.accId = accId;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public BigDecimal getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(BigDecimal bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public BigDecimal getIsDefaultAccount() {
        return isDefaultAccount;
    }

    public void setIsDefaultAccount(BigDecimal isDefaultAccount) {
        this.isDefaultAccount = isDefaultAccount;
    }

    public BigDecimal getIsValidAccount() {
        return isValidAccount;
    }

    public void setIsValidAccount(BigDecimal isValidAccount) {
        this.isValidAccount = isValidAccount;
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