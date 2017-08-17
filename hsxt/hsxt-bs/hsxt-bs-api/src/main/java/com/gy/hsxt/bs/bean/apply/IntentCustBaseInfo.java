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
 * @ClassName: IntentCustBaseInfo
 * @Description: 意向客户基本信息
 * 
 * @author: xiaofl
 * @date: 2015-8-31 下午4:57:15
 * @version V1.0
 */
public class IntentCustBaseInfo implements Serializable {

    private static final long serialVersionUID = 6178564194649677024L;

    /** 申请编号 */
    private String applyId;

    /** 意向客户名称 */
    private String entCustName;

    /** 企业详细地址 */
    private String entAddress;

    /** 联系人 */
    private String linkman;

    /** 手机号码 */
    private String mobile;

    /** 企业所在国家代码 */
    private String countryNo;

    /** 企业所在省代码 */
    private String provinceNo;

    /** 企业所在城市代码 */
    private String cityNo;

    /** 申请类别 */
    private Integer appType;

    /** 状态 */
    private Integer status;

    /** 服务公司互生号 */
    private String serEntResNo;

    /** 申请日期 */
    private String createdDate;

    /** 状态日期 */
    private String apprTime;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
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

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSerEntResNo() {
        return serEntResNo;
    }

    public void setSerEntResNo(String serEntResNo) {
        this.serEntResNo = serEntResNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprTime() {
        return apprTime;
    }

    public void setApprTime(String apprTime) {
        this.apprTime = apprTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
