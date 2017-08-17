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
 * @ClassName: EntRealnameAuth
 * @Description: 企业实名认证信息
 * 
 * @author: xiaofl
 * @date: 2015-9-6 上午11:23:42
 * @version V1.0
 */
public class EntRealnameAuth extends OptInfo implements Serializable {

    private static final long serialVersionUID = 5651782699634669397L;

    /** 申请单编号 **/
    private String applyId;

    /** 企业资源号 **/
    private String entResNo;

    /** 企业客户号 **/
    private String entCustId;

    /** 企业客户类型 **/
    private Integer custType;

    /** 企业名称 **/
    private String entCustName;

    /** 企业英文名称 **/
    @Deprecated
    private String entCustNameEn;

    /** 企业所在国家代码 **/
    private String countryNo;

    /** 企业所在省代码 **/
    private String provinceNo;

    /** 企业所在城市代码 **/
    private String cityNo;

    /** 企业地址 **/
    private String entAddr;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String mobile;

    /** 法人代表姓名 **/
    private String legalName;

    /** 法人代表证件类型 1：身份证:IDC、2：护照:PSP **/
    @Deprecated
    private Integer legalCreType;

    /** 法人代表证件号码 **/
    @Deprecated
    private String legalCreNo;

    /** 营业执照号 **/
    private String licenseNo;

    /** 组织机构代码证号 **/
    @Deprecated
    private String orgNo;

    /** 纳税人识别号 **/
    @Deprecated
    private String taxNo;

    /** 法人代表证件正面图路径 **/
    @Deprecated
    private String lrcFacePic;

    /** 法人代表证件反面图路径 **/
    @Deprecated
    private String lrcBackPic;

    /** 营业执照扫描件 **/
    private String licensePic;

    /** 组织机构代码证扫描件 **/
    @Deprecated
    private String orgPic;

    /** 税务登记证扫描件 **/
    @Deprecated
    private String taxPic;

    /** 状态 **/
    private Integer status;

    /** 认证附言 **/
    private String postScript;

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

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    @Deprecated
    public String getEntCustNameEn() {
        return entCustNameEn;
    }

    @Deprecated
    public void setEntCustNameEn(String entCustNameEn) {
        this.entCustNameEn = entCustNameEn;
    }

    public String getEntAddr() {
        return entAddr;
    }

    public void setEntAddr(String entAddr) {
        this.entAddr = entAddr;
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

    @Deprecated
    public String getLrcFacePic() {
        return lrcFacePic;
    }

    @Deprecated
    public void setLrcFacePic(String lrcFacePic) {
        this.lrcFacePic = lrcFacePic;
    }

    @Deprecated
    public String getLrcBackPic() {
        return lrcBackPic;
    }

    public void setLrcBackPic(String lrcBackPic) {
        this.lrcBackPic = lrcBackPic;
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    @Deprecated
    public String getOrgPic() {
        return orgPic;
    }

    @Deprecated
    public void setOrgPic(String orgPic) {
        this.orgPic = orgPic;
    }

    @Deprecated
    public String getTaxPic() {
        return taxPic;
    }

    @Deprecated
    public void setTaxPic(String taxPic) {
        this.taxPic = taxPic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
