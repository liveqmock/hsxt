/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DaclareEnt
 * @Description: 申报企业信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 上午11:29:45
 * @version V1.0
 */
public class DeclareEnt implements Serializable {

    private static final long serialVersionUID = -5154588560208939540L;

    /** 申请编号 */
    private String applyId;

    /** 企业名称 */
    private String entName;

    /** 法人代表名称 */
    private String legalName;

    /** 法人代表证件类型 */
    private Integer legalCreType;

    /** 法人代表证件号码 */
    private String legalCreNo;

    /** 所属国家 */
    private String countryNo;

    /** 所属省份 */
    private String provinceNo;

    /** 所属城市 */
    private String cityNo;

    /** 营业期限 DATE OR long_term((长期) */
    private String limitDate;

    /** 企业工商登记地址 */
    private String entAddr;

    /** 企业工商登记电话 */
    private String phone;

    /** 营业执照号 */
    private String licenseNo;

    /** 组织机构代码 */
    private String orgNo;

    /** 纳税人识别号 */
    private String taxNo;

    /** 经营范围 */
    private String dealArea;

    /** 注册资金 */
    private String registerFee;

    /** 企业成立日期 */
    private String establishingDate;

    /** 最近年检 */
    private String yearCheck;

    /** 操作员客户号 */
    private String operator;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Integer getLegalCreType() {
        return legalCreType;
    }

    public void setLegalCreType(Integer legalCreType) {
        this.legalCreType = legalCreType;
    }

    public String getLegalCreNo() {
        return legalCreNo;
    }

    public void setLegalCreNo(String legalCreNo) {
        this.legalCreNo = legalCreNo;
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

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public String getEntAddr() {
        return entAddr;
    }

    public void setEntAddr(String entAddr) {
        this.entAddr = entAddr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getDealArea() {
        return dealArea;
    }

    public void setDealArea(String dealArea) {
        this.dealArea = dealArea;
    }

    public String getRegisterFee() {
        return registerFee;
    }

    public void setRegisterFee(String registerFee) {
        this.registerFee = registerFee;
    }

    public String getEstablishingDate() {
        return establishingDate;
    }

    public void setEstablishingDate(String establishingDate) {
        this.establishingDate = establishingDate;
    }

    public String getYearCheck() {
        return yearCheck;
    }

    public void setYearCheck(String yearCheck) {
        this.yearCheck = yearCheck;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
