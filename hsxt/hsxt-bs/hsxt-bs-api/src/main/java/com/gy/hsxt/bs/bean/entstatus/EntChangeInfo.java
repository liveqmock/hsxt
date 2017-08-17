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
 * @ClassName: EntChangeInfo
 * @Description: 企业重要信息变更信息
 * 
 * @author: xiaofl
 * @date: 2015-9-6 上午10:39:34
 * @version V1.0
 */
public class EntChangeInfo extends OptInfo implements Serializable {

    private static final long serialVersionUID = 6767732206192489900L;

    /** 申请编号 **/
    private String applyId;

    /** 企业客户号 **/
    private String entCustId;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业名称 **/
    private String entCustName;

    /** 客户类型 **/
    private Integer custType;

    /** 企业联系人姓名 **/
    private String linkman;

    /** 企业联系人手机 **/
    private String mobile;

    /** 企业所在国家代码 **/
    private String countryNo;

    /** 企业所在省代码 **/
    private String provinceNo;

    /** 企业所在城市代码 **/
    private String cityNo;

    // private List<ChangeItem> changeItems;
    /**删除企业英文名称、法人代表证件类型、法人代表证件号码、组织机构代码证号、纳税人识别号字段**/

    /** 变更前的企业名称 **/
    private String custNameOld;

    /** 变更前的企业英文名称 **/
    @Deprecated
    private String custNameEnOld;

    /** 变更前的企业地址 **/
    private String custAddressOld;

    /** 变更前的联系人姓名 **/
    private String linkmanOld;

    /** 变更前的联系人手机 **/
    private String linkmanMobileOld;

    /** 变更前的法人代表 **/
    private String legalRepOld;

    /** 变更前的法人代表证件类型 **/
    @Deprecated
    private Integer legalRepCreTypeOld;

    /** 变更前的法人代表证件号码 **/
    @Deprecated
    private String legalRepCreNoOld;

    /** 变更前的营业执照号 **/
    private String bizLicenseNoOld;

    /** 变更前的组织代码证号 **/
    @Deprecated
    private String organizerNoOld;

    /** 变更前的纳税人识别号 **/
    @Deprecated
    private String taxpayerNoOld;

    /** 变更前的法人代表证件正面 **/
    @Deprecated
    private String legalRepCreFacePicOld;

    /** 变更前的法人代表证件反面 **/
    @Deprecated
    private String legalRepCreBackPicOld;

    /** 变更前的营业执照正本扫描件 **/
    private String bizLicenseCrePicOld;

    /** 变更前的组织结构代码证正本扫描件 **/
    @Deprecated
    private String organizerCrePicOld;

    /** 变更前的税务登记证正本扫描件 **/
    @Deprecated
    private String taxpayerCrePicOld;

    /** 变更前的联系人授权委托书 **/
    private String linkAuthorizePicOld;

    /** 变更后的企业名称 **/
    private String custNameNew;

    /** 变更后的企业英文名称 **/
    @Deprecated
    private String custNameEnNew;

    /** 变更后的企业地址 **/
    private String custAddressNew;

    /** 变更后的联系人姓名 **/
    private String linkmanNew;

    /** 变更后的联系人手机 **/
    private String linkmanMobileNew;

    /** 变更后的法人代表 **/
    private String legalRepNew;

    /** 变更后的法人代表证件类型 **/
    @Deprecated
    private Integer legalRepCreTypeNew;

    /** 变更后的法人代表证件号码 **/
    @Deprecated
    private String legalRepCreNoNew;

    /** 变更后的营业执照号 **/
    private String bizLicenseNoNew;

    /** 变更后的组织代码证号 **/
    @Deprecated
    private String organizerNoNew;

    /** 变更后的纳税人识别号 **/
    @Deprecated
    private String taxpayerNoNew;

    /** 变更后的法人代表证件正面 **/
    @Deprecated
    private String legalRepCreFacePicNew;

    /** 变更后的法人代表证件反面 **/
    @Deprecated
    private String legalRepCreBackPicNew;

    /** 变更后的营业执照正本扫描件 **/
    private String bizLicenseCrePicNew;

    /** 变更后的组织结构代码证正本扫描件 **/
    @Deprecated
    private String organizerCrePicNew;

    /** 变更后的税务登记证正本扫描件 **/
    @Deprecated
    private String taxpayerCrePicNew;

    /** 变更后的联系人授权委托书 **/
    private String linkAuthorizePicNew;

    /** 重要信息变更业务办理申请书扫描件 **/
    private String imptappPic;

    /** 申请变更原因 **/
    private String applyReason;

    /** 申请状态 **/
    private Integer status;

    /** 审批意见 **/
    private String apprRemark;

    /** 申请变更时间 **/
    private String applyDate;

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

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    // public List<ChangeItem> getChangeItems() {
    // return changeItems;
    // }
    //
    // public void setChangeItems(List<ChangeItem> changeItems) {
    // this.changeItems = changeItems;
    // }

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

    public String getCustNameOld() {
        return custNameOld;
    }

    public void setCustNameOld(String custNameOld) {
        this.custNameOld = custNameOld;
    }

    @Deprecated
    public String getCustNameEnOld() {
        return custNameEnOld;
    }

    @Deprecated
    public void setCustNameEnOld(String custNameEnOld) {
        this.custNameEnOld = custNameEnOld;
    }

    public String getCustAddressOld() {
        return custAddressOld;
    }

    public void setCustAddressOld(String custAddressOld) {
        this.custAddressOld = custAddressOld;
    }

    public String getLinkmanOld() {
        return linkmanOld;
    }

    public void setLinkmanOld(String linkmanOld) {
        this.linkmanOld = linkmanOld;
    }

    public String getLinkmanMobileOld() {
        return linkmanMobileOld;
    }

    public void setLinkmanMobileOld(String linkmanMobileOld) {
        this.linkmanMobileOld = linkmanMobileOld;
    }

    public String getLegalRepOld() {
        return legalRepOld;
    }

    public void setLegalRepOld(String legalRepOld) {
        this.legalRepOld = legalRepOld;
    }

    @Deprecated
    public Integer getLegalRepCreTypeOld() {
        return legalRepCreTypeOld;
    }

    @Deprecated
    public void setLegalRepCreTypeOld(Integer legalRepCreTypeOld) {
        this.legalRepCreTypeOld = legalRepCreTypeOld;
    }

    @Deprecated
    public String getLegalRepCreNoOld() {
        return legalRepCreNoOld;
    }

    @Deprecated
    public void setLegalRepCreNoOld(String legalRepCreNoOld) {
        this.legalRepCreNoOld = legalRepCreNoOld;
    }

    public String getBizLicenseNoOld() {
        return bizLicenseNoOld;
    }

    public void setBizLicenseNoOld(String bizLicenseNoOld) {
        this.bizLicenseNoOld = bizLicenseNoOld;
    }

    @Deprecated
    public String getOrganizerNoOld() {
        return organizerNoOld;
    }

    @Deprecated
    public void setOrganizerNoOld(String organizerNoOld) {
        this.organizerNoOld = organizerNoOld;
    }

    @Deprecated
    public String getTaxpayerNoOld() {
        return taxpayerNoOld;
    }

    @Deprecated
    public void setTaxpayerNoOld(String taxpayerNoOld) {
        this.taxpayerNoOld = taxpayerNoOld;
    }

    @Deprecated
    public String getLegalRepCreFacePicOld() {
        return legalRepCreFacePicOld;
    }

    @Deprecated
    public void setLegalRepCreFacePicOld(String legalRepCreFacePicOld) {
        this.legalRepCreFacePicOld = legalRepCreFacePicOld;
    }

    @Deprecated
    public String getLegalRepCreBackPicOld() {
        return legalRepCreBackPicOld;
    }

    @Deprecated
    public void setLegalRepCreBackPicOld(String legalRepCreBackPicOld) {
        this.legalRepCreBackPicOld = legalRepCreBackPicOld;
    }

    public String getBizLicenseCrePicOld() {
        return bizLicenseCrePicOld;
    }

    public void setBizLicenseCrePicOld(String bizLicenseCrePicOld) {
        this.bizLicenseCrePicOld = bizLicenseCrePicOld;
    }

    @Deprecated
    public String getOrganizerCrePicOld() {
        return organizerCrePicOld;
    }

    @Deprecated
    public void setOrganizerCrePicOld(String organizerCrePicOld) {
        this.organizerCrePicOld = organizerCrePicOld;
    }

    @Deprecated
    public String getTaxpayerCrePicOld() {
        return taxpayerCrePicOld;
    }

    @Deprecated
    public void setTaxpayerCrePicOld(String taxpayerCrePicOld) {
        this.taxpayerCrePicOld = taxpayerCrePicOld;
    }

    public String getLinkAuthorizePicOld() {
        return linkAuthorizePicOld;
    }

    public void setLinkAuthorizePicOld(String linkAuthorizePicOld) {
        this.linkAuthorizePicOld = linkAuthorizePicOld;
    }

    public String getCustNameNew() {
        return custNameNew;
    }

    public void setCustNameNew(String custNameNew) {
        this.custNameNew = custNameNew;
    }

    @Deprecated
    public String getCustNameEnNew() {
        return custNameEnNew;
    }

    @Deprecated
    public void setCustNameEnNew(String custNameEnNew) {
        this.custNameEnNew = custNameEnNew;
    }

    public String getCustAddressNew() {
        return custAddressNew;
    }

    public void setCustAddressNew(String custAddressNew) {
        this.custAddressNew = custAddressNew;
    }

    public String getLinkmanNew() {
        return linkmanNew;
    }

    public void setLinkmanNew(String linkmanNew) {
        this.linkmanNew = linkmanNew;
    }

    public String getLinkmanMobileNew() {
        return linkmanMobileNew;
    }

    public void setLinkmanMobileNew(String linkmanMobileNew) {
        this.linkmanMobileNew = linkmanMobileNew;
    }

    public String getLegalRepNew() {
        return legalRepNew;
    }

    public void setLegalRepNew(String legalRepNew) {
        this.legalRepNew = legalRepNew;
    }

    @Deprecated
    public Integer getLegalRepCreTypeNew() {
        return legalRepCreTypeNew;
    }

    @Deprecated
    public void setLegalRepCreTypeNew(Integer legalRepCreTypeNew) {
        this.legalRepCreTypeNew = legalRepCreTypeNew;
    }

    @Deprecated
    public String getLegalRepCreNoNew() {
        return legalRepCreNoNew;
    }

    @Deprecated
    public void setLegalRepCreNoNew(String legalRepCreNoNew) {
        this.legalRepCreNoNew = legalRepCreNoNew;
    }

    public String getBizLicenseNoNew() {
        return bizLicenseNoNew;
    }

    public void setBizLicenseNoNew(String bizLicenseNoNew) {
        this.bizLicenseNoNew = bizLicenseNoNew;
    }

    @Deprecated
    public String getOrganizerNoNew() {
        return organizerNoNew;
    }

    @Deprecated
    public void setOrganizerNoNew(String organizerNoNew) {
        this.organizerNoNew = organizerNoNew;
    }

    @Deprecated
    public String getTaxpayerNoNew() {
        return taxpayerNoNew;
    }

    @Deprecated
    public void setTaxpayerNoNew(String taxpayerNoNew) {
        this.taxpayerNoNew = taxpayerNoNew;
    }

    @Deprecated
    public String getLegalRepCreFacePicNew() {
        return legalRepCreFacePicNew;
    }

    @Deprecated
    public void setLegalRepCreFacePicNew(String legalRepCreFacePicNew) {
        this.legalRepCreFacePicNew = legalRepCreFacePicNew;
    }

    @Deprecated
    public String getLegalRepCreBackPicNew() {
        return legalRepCreBackPicNew;
    }

    @Deprecated
    public void setLegalRepCreBackPicNew(String legalRepCreBackPicNew) {
        this.legalRepCreBackPicNew = legalRepCreBackPicNew;
    }

    public String getBizLicenseCrePicNew() {
        return bizLicenseCrePicNew;
    }

    public void setBizLicenseCrePicNew(String bizLicenseCrePicNew) {
        this.bizLicenseCrePicNew = bizLicenseCrePicNew;
    }

    @Deprecated
    public String getOrganizerCrePicNew() {
        return organizerCrePicNew;
    }

    @Deprecated
    public void setOrganizerCrePicNew(String organizerCrePicNew) {
        this.organizerCrePicNew = organizerCrePicNew;
    }

    @Deprecated
    public String getTaxpayerCrePicNew() {
        return taxpayerCrePicNew;
    }

    @Deprecated
    public void setTaxpayerCrePicNew(String taxpayerCrePicNew) {
        this.taxpayerCrePicNew = taxpayerCrePicNew;
    }

    public String getLinkAuthorizePicNew() {
        return linkAuthorizePicNew;
    }

    public void setLinkAuthorizePicNew(String linkAuthorizePicNew) {
        this.linkAuthorizePicNew = linkAuthorizePicNew;
    }

    public String getImptappPic() {
        return imptappPic;
    }

    public void setImptappPic(String imptappPic) {
        this.imptappPic = imptappPic;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
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
