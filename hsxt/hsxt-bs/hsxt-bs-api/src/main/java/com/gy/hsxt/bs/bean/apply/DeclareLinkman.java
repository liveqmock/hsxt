/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DeclareLinkman
 * @Description: 申报企业联系人信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 上午11:30:29
 * @version V1.0
 */
public class DeclareLinkman extends OptInfo implements Serializable {

    private static final long serialVersionUID = -3224170296039187346L;

    /** 申请编号 */
    private String applyId;

    /** 联系人名称 */
    private String linkman;

    /** 办公电话 */
    private String phone;

    /** 手机号码 */
    private String mobile;

    /** 邮箱地址 */
    private String email;

    /** 邮政编码 */
    private String zipCode;

    /** 联系地址 */
    private String address;

    /** 职位 */
    private String job;

    /** 授权书路径 */
    private String certificateFile;

    /** 联系人工作QQ */
    private String qq;

    /** 企业网址 */
    private String webSite;

    /** 传真号码 */
    private String fax;

    /** 帮扶协议附件 **/
    private DeclareAptitude protocolAptitude;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCertificateFile() {
        return certificateFile;
    }

    public void setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public DeclareAptitude getProtocolAptitude() {
        return protocolAptitude;
    }

    public void setProtocolAptitude(DeclareAptitude protocolAptitude) {
        this.protocolAptitude = protocolAptitude;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
