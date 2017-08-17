/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: PerRealnameAuth
 * @Description: 消费者实名认证信息
 * 
 * @author: xiaofl
 * @date: 2015-9-6 下午12:14:23
 * @version V1.0
 */
public class PerRealnameAuth extends OptInfo implements Serializable {

    private static final long serialVersionUID = -349009608301695556L;

    /** 申请编号 **/
    private String applyId;

    /** 个人互生号 **/
    private String perResNo;

    /** 个人客户号 **/
    private String perCustId;

    /** 姓名 **/
    private String name;

    /** 性别 **/
    private Integer sex;

    /** 手机 **/
    private String mobile;

    /** 国籍 **/
    private String countryNo;

    /** 国籍text **/
    private String countryName;

    /** 民族 **/
    private String nation;

    /** 户籍地址 **/
    private String birthAddr;

    /** 发证机关 **/
    private String licenceIssuing;

    /** 职业 **/
    private String profession;

    /** 证件类型 **/
    private Integer certype;

    /** 证件号码 **/
    private String credentialsNo;

    /** 证件正面照 **/
    private String cerpica;

    /** 证件背面照 **/
    private String cerpicb;

    /** 手持证件照 **/
    private String cerpich;

    /** 证件有效期 **/
    private String validDate;

    /** 申诉类型 **/
    private String appealType;

    /** 申诉理由 **/
    private String appealReason;

    /** 认证附言 **/
    private String postScript;

    /** 签发地点 **/
    private String issuePlace;

    /** 企业名称 **/
    private String entName;

    /** 企业注册地址 **/
    private String entRegAddr;

    /** 企业类型 **/
    private String entType;

    /** 企业成立日期 **/
    private String entBuildDate;

    /** 申请时间 **/
    private String applyDate;

    /** 状态 **/
    private Integer status;

    /** 审批内容 **/
    private String apprContent;

    /** 审批时间 **/
    private String apprDate;

    /** 创建日期 */
    private String createdDate;

    /** 修改日期 */
    private String updateDate;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getPerResNo() {
        return perResNo;
    }

    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    public String getPerCustId() {
        return perCustId;
    }

    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthAddr() {
        return birthAddr;
    }

    public void setBirthAddr(String birthAddr) {
        this.birthAddr = birthAddr;
    }

    public String getLicenceIssuing() {
        return licenceIssuing;
    }

    public void setLicenceIssuing(String licenceIssuing) {
        this.licenceIssuing = licenceIssuing;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getCertype() {
        return certype;
    }

    public void setCertype(Integer certype) {
        this.certype = certype;
    }

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getCerpica() {
        return cerpica;
    }

    public void setCerpica(String cerpica) {
        this.cerpica = cerpica;
    }

    public String getCerpicb() {
        return cerpicb;
    }

    public void setCerpicb(String cerpicb) {
        this.cerpicb = cerpicb;
    }

    public String getCerpich() {
        return cerpich;
    }

    public void setCerpich(String cerpich) {
        this.cerpich = cerpich;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getAppealType() {
        return appealType;
    }

    public void setAppealType(String appealType) {
        this.appealType = appealType;
    }

    public String getAppealReason() {
        return appealReason;
    }

    public void setAppealReason(String appealReason) {
        this.appealReason = appealReason;
    }

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntRegAddr() {
        return entRegAddr;
    }

    public void setEntRegAddr(String entRegAddr) {
        this.entRegAddr = entRegAddr;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getEntBuildDate() {
        return entBuildDate;
    }

    public void setEntBuildDate(String entBuildDate) {
        this.entBuildDate = entBuildDate;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprContent() {
        return apprContent;
    }

    public void setApprContent(String apprContent) {
        this.apprContent = apprContent;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
