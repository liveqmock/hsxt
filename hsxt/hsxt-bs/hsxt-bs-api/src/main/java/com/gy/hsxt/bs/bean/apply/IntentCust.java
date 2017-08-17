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
 * @ClassName: IntentCust
 * @Description: 意向客户信息
 *
 * @author: xiaofl
 * @date: 2015-8-31 下午4:56:56
 * @version V1.0
 */
public class IntentCust implements Serializable {

    private static final long serialVersionUID = 70202294475885876L;

    /** 申请编号 */
    private String applyId;

    /** 受理编号 */
    private String acceptNo;

    /** 服务公司互生号 */
    private String serEntResNo;

    /** 服务公司客户号 */
    private String serEntCustId;

    /** 服务公司名称 */
    private String serEntCustName;

    /** 企业执照号码 */
    private String licenseNo;

    /** 申请企业名称(意向客户名称) */
    private String entCustName;

    /** 申请类别:2.成员企业 3.托管企业 */
    private Integer appType;

    /** 企业性质:1.私有企业 2.国有企业 3.合资企业 */
    private Integer entType;

    /** 企业所在国家代码 */
    private String countryNo;

    /** 企业所在省代码 */
    private String provinceNo;

    /** 企业所在城市代码 */
    private String cityNo;

    /** 企业详细地址 */
    private String entAddress;

    /** 联系人 */
    private String linkman;

    /** 手机号码 */
    private String mobile;

    /** 办公电话 */
    private String officeTel;

    /** 电子邮箱 */
    private String email;

    /** 经营面积 */
    private String area;

    /** 员工数量 */
    private Integer staffs;

    /** 企业经营范围 */
    private String bizScope;

    /** 填写日期 */
    private String createdDate;

    /** 状态 */
    private Integer status;

    /** 审批操作员客户号 */
    private String apprOperator;

    /** 审批时间 */
    private String apprTime;

    /** 审批意见 */
    private String apprRemark;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getAcceptNo() {
        return acceptNo;
    }

    public void setAcceptNo(String acceptNo) {
        this.acceptNo = acceptNo;
    }

    public String getSerEntResNo() {
        return serEntResNo;
    }

    public void setSerEntResNo(String serEntResNo) {
        this.serEntResNo = serEntResNo;
    }

    public String getSerEntCustId() {
        return serEntCustId;
    }

    public void setSerEntCustId(String serEntCustId) {
        this.serEntCustId = serEntCustId;
    }

    public String getSerEntCustName() {
        return serEntCustName;
    }

    public void setSerEntCustName(String serEntCustName) {
        this.serEntCustName = serEntCustName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getEntType() {
        return entType;
    }

    public void setEntType(Integer entType) {
        this.entType = entType;
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

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStaffs() {
        return staffs;
    }

    public void setStaffs(Integer staffs) {
        this.staffs = staffs;
    }

    public String getBizScope() {
        return bizScope;
    }

    public void setBizScope(String bizScope) {
        this.bizScope = bizScope;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprOperator() {
        return apprOperator;
    }

    public void setApprOperator(String apprOperator) {
        this.apprOperator = apprOperator;
    }

    public String getApprTime() {
        return apprTime;
    }

    public void setApprTime(String apprTime) {
        this.apprTime = apprTime;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
