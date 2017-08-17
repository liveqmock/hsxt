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
 * @ClassName: DeclareEntInfo
 * @Description: 申报企业工商登记信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 下午4:52:36
 * @version V1.0
 */
public class DeclareBizRegInfo extends OptInfo{

    private static final long serialVersionUID = -2849564428064067499L;

    /** 申请编号 */
    private String applyId;

    /** 申报企业中文名称 */
    private String entName;

    /** 企业地址 */
    private String entAddress;

    /** 法人代表名称 */
    private String legalName;

    /**
     * 业务变更 http://docs.gyist.com/pages/viewpage.action?pageId=6783233
     * 企业工商登记信息界面，删除联系电话、法人代表证件类型、法人代表证件号码、组织机构代码证号、纳税人识别号字段，新增营业执照扫描件上传功能，原‘营业执照号’改名为‘营业执照注册号’；
     */

    /** 法人代表证件类型 */
    @Deprecated
    private Integer legalCreType;

    /** 法人代表证件号码 */
    @Deprecated
    private String legalCreNo;

    /** 联系人手机 */
    @Deprecated
    private String linkmanMobile;

    /** 企业类型 */
    private String entType;

    /** 营业执照号 */
    private String licenseNo;

    /** 组织机构代码 */
    @Deprecated
    private String orgNo;

    /** 纳税人识别号 */
    @Deprecated
    private String taxNo;

    /** 企业成立日期 */
    private String establishingDate;

    /** 营业期限 DATE OR long_term((长期) */
    private String limitDate;

    /** 经营范围 */
    private String dealArea;

    /** 营业执照扫描件 **/
    private DeclareAptitude licenseAptitude;

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

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    @Deprecated
    public Integer getLegalCreType() {
        return legalCreType;
    }

    @Deprecated
    public void setLegalCreType(Integer legalCreType) {
        this.legalCreType = legalCreType;
    }

    @Deprecated
    public String getLegalCreNo() {
        return legalCreNo;
    }

    @Deprecated
    public void setLegalCreNo(String legalCreNo) {
        this.legalCreNo = legalCreNo;
    }

    @Deprecated
    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    @Deprecated
    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    @Deprecated
    public String getOrgNo() {
        return orgNo;
    }

    @Deprecated
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    @Deprecated
    public String getTaxNo() {
        return taxNo;
    }

    @Deprecated
    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getEstablishingDate() {
        return establishingDate;
    }

    public void setEstablishingDate(String establishingDate) {
        this.establishingDate = establishingDate;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public String getDealArea() {
        return dealArea;
    }

    public void setDealArea(String dealArea) {
        this.dealArea = dealArea;
    }


    public DeclareAptitude getLicenseAptitude() {
        return licenseAptitude;
    }

    public void setLicenseAptitude(DeclareAptitude licenseAptitude) {
        this.licenseAptitude = licenseAptitude;
    }
}
