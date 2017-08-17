/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** 
 * 
 * @Package: com.gy.hsxt.bs.apply.bean  
 * @ClassName: SameItemParam 
 * @Description: 相同项参数
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:48:33 
 * @version V1.0 
 

 */ 
public class SameItemParam implements Serializable {

    private static final long serialVersionUID = -3207577782700205469L;

    /** 申请编号 */
    private String applyId;

    /** 提交报备企业管理号 */
    private String opResNo;

    /** 企业名称 */
    private String entCustName;

    /** 营业执照号 */
    private String licenseNo;

    /** 企业法人代表 */
    private String legalName;

    /** 法人代表证件类型 */
    private Integer legalType;

    /** 法人代表证件号码 */
    private String legalNo;

    /** 联系人 */
    private String linkman;

    /** 联系人电话 */
    private String phone;

    /** 股东名称 */
    private String shName;

    /** 证件类型 */
    private Integer idType;

    /** 证件号码 */
    private String idNo;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getOpResNo() {
        return opResNo;
    }

    public void setOpResNo(String opResNo) {
        this.opResNo = opResNo;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Integer getLegalType() {
        return legalType;
    }

    public void setLegalType(Integer legalType) {
        this.legalType = legalType;
    }

    public String getLegalNo() {
        return legalNo;
    }

    public void setLegalNo(String legalNo) {
        this.legalNo = legalNo;
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

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
