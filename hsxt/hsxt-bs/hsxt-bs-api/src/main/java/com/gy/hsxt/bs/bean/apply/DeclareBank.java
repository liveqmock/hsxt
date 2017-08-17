/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DeclareBank
 * @Description: 申报企业银行账号信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 上午11:30:08
 * @version V1.0
 */
public class DeclareBank extends OptInfo{

    private static final long serialVersionUID = 7789823171673428751L;

    /** 账号ID **/
    private String accountId;

    /** 申请编号 **/
    private String applyId;

    /** 开户银行代码 **/
    private String bankCode;

    /** 支行名称代码 **/
    private String branchCode;

    /** 银行户名 **/
    private String accountName;

    /** 银行账号 **/
    private String accountNo;

    /** 所属国家代码 **/
    private String countryNo;

    /** 所属省份代码 **/
    private String provinceNo;

    /** 所属城市代码 **/
    private String cityNo;

    /** 币种代码 **/
    private String currencyCode;

    /** 是否默认账户 **/
    private Boolean isDefault;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

}
