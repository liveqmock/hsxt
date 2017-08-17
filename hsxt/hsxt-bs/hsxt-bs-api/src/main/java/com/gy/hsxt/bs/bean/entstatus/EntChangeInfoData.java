/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.gy.hsxt.bs.bean.entstatus;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: EntChangeInfo
 * @Description: 企业重要信息变更项信息
 * 
 * @author: xiaofl
 * @date: 2015-9-6 上午10:39:34
 * @version V1.0
 */
public class EntChangeInfoData implements Serializable {

    private static final long serialVersionUID = 1846861058645566150L;

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

    public String getCustNameOld() {
        return custNameOld;
    }

    public void setCustNameOld(String custNameOld) {
        this.custNameOld = custNameOld;
    }

    public String getCustNameEnOld() {
        return custNameEnOld;
    }

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

    public Integer getLegalRepCreTypeOld() {
        return legalRepCreTypeOld;
    }

    public void setLegalRepCreTypeOld(Integer legalRepCreTypeOld) {
        this.legalRepCreTypeOld = legalRepCreTypeOld;
    }

    public String getLegalRepCreNoOld() {
        return legalRepCreNoOld;
    }

    public void setLegalRepCreNoOld(String legalRepCreNoOld) {
        this.legalRepCreNoOld = legalRepCreNoOld;
    }

    public String getBizLicenseNoOld() {
        return bizLicenseNoOld;
    }

    public void setBizLicenseNoOld(String bizLicenseNoOld) {
        this.bizLicenseNoOld = bizLicenseNoOld;
    }

    public String getOrganizerNoOld() {
        return organizerNoOld;
    }

    public void setOrganizerNoOld(String organizerNoOld) {
        this.organizerNoOld = organizerNoOld;
    }

    public String getTaxpayerNoOld() {
        return taxpayerNoOld;
    }

    public void setTaxpayerNoOld(String taxpayerNoOld) {
        this.taxpayerNoOld = taxpayerNoOld;
    }

    public String getLegalRepCreFacePicOld() {
        return legalRepCreFacePicOld;
    }

    public void setLegalRepCreFacePicOld(String legalRepCreFacePicOld) {
        this.legalRepCreFacePicOld = legalRepCreFacePicOld;
    }

    public String getLegalRepCreBackPicOld() {
        return legalRepCreBackPicOld;
    }

    public void setLegalRepCreBackPicOld(String legalRepCreBackPicOld) {
        this.legalRepCreBackPicOld = legalRepCreBackPicOld;
    }

    public String getBizLicenseCrePicOld() {
        return bizLicenseCrePicOld;
    }

    public void setBizLicenseCrePicOld(String bizLicenseCrePicOld) {
        this.bizLicenseCrePicOld = bizLicenseCrePicOld;
    }

    public String getOrganizerCrePicOld() {
        return organizerCrePicOld;
    }

    public void setOrganizerCrePicOld(String organizerCrePicOld) {
        this.organizerCrePicOld = organizerCrePicOld;
    }

    public String getTaxpayerCrePicOld() {
        return taxpayerCrePicOld;
    }

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

    public String getCustNameEnNew() {
        return custNameEnNew;
    }

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

    public Integer getLegalRepCreTypeNew() {
        return legalRepCreTypeNew;
    }

    public void setLegalRepCreTypeNew(Integer legalRepCreTypeNew) {
        this.legalRepCreTypeNew = legalRepCreTypeNew;
    }

    public String getLegalRepCreNoNew() {
        return legalRepCreNoNew;
    }

    public void setLegalRepCreNoNew(String legalRepCreNoNew) {
        this.legalRepCreNoNew = legalRepCreNoNew;
    }

    public String getBizLicenseNoNew() {
        return bizLicenseNoNew;
    }

    public void setBizLicenseNoNew(String bizLicenseNoNew) {
        this.bizLicenseNoNew = bizLicenseNoNew;
    }

    public String getOrganizerNoNew() {
        return organizerNoNew;
    }

    public void setOrganizerNoNew(String organizerNoNew) {
        this.organizerNoNew = organizerNoNew;
    }

    public String getTaxpayerNoNew() {
        return taxpayerNoNew;
    }

    public void setTaxpayerNoNew(String taxpayerNoNew) {
        this.taxpayerNoNew = taxpayerNoNew;
    }

    public String getLegalRepCreFacePicNew() {
        return legalRepCreFacePicNew;
    }

    public void setLegalRepCreFacePicNew(String legalRepCreFacePicNew) {
        this.legalRepCreFacePicNew = legalRepCreFacePicNew;
    }

    public String getLegalRepCreBackPicNew() {
        return legalRepCreBackPicNew;
    }

    public void setLegalRepCreBackPicNew(String legalRepCreBackPicNew) {
        this.legalRepCreBackPicNew = legalRepCreBackPicNew;
    }

    public String getBizLicenseCrePicNew() {
        return bizLicenseCrePicNew;
    }

    public void setBizLicenseCrePicNew(String bizLicenseCrePicNew) {
        this.bizLicenseCrePicNew = bizLicenseCrePicNew;
    }

    public String getOrganizerCrePicNew() {
        return organizerCrePicNew;
    }

    public void setOrganizerCrePicNew(String organizerCrePicNew) {
        this.organizerCrePicNew = organizerCrePicNew;
    }

    public String getTaxpayerCrePicNew() {
        return taxpayerCrePicNew;
    }

    public void setTaxpayerCrePicNew(String taxpayerCrePicNew) {
        this.taxpayerCrePicNew = taxpayerCrePicNew;
    }

    public String getLinkAuthorizePicNew() {
        return linkAuthorizePicNew;
    }

    public void setLinkAuthorizePicNew(String linkAuthorizePicNew) {
        this.linkAuthorizePicNew = linkAuthorizePicNew;
    }

    @Override
    public String toString() {
        JSONObject changeContent = new JSONObject();

        putChangeItem(changeContent, "custNameNew", custNameOld, custNameNew);

        putChangeItem(changeContent, "custNameEnNew", custNameEnOld, custNameEnNew);

        putChangeItem(changeContent, "custAddressNew", custAddressOld, custAddressNew);

        putChangeItem(changeContent, "linkmanNew", linkmanOld, linkmanNew);

        putChangeItem(changeContent, "linkmanMobileNew", linkmanMobileOld, linkmanMobileNew);

        putChangeItem(changeContent, "legalRepNew", legalRepOld, legalRepNew);

        putChangeItem(changeContent, "legalRepCreNoNew", legalRepCreNoOld, legalRepCreNoNew);

        putChangeItem(changeContent, "bizLicenseNoNew", bizLicenseNoOld, bizLicenseNoNew);

        putChangeItem(changeContent, "organizerNoNew", organizerNoOld, organizerNoNew);

        putChangeItem(changeContent, "taxpayerNoNew", taxpayerNoOld, taxpayerNoNew);

        putChangeItem(changeContent, "legalRepCreFacePicNew", legalRepCreFacePicOld, legalRepCreFacePicNew);

        putChangeItem(changeContent, "legalRepCreBackPicNew", legalRepCreBackPicOld, legalRepCreBackPicNew);

        putChangeItem(changeContent, "bizLicenseCrePicNew", bizLicenseCrePicOld, bizLicenseCrePicNew);

        putChangeItem(changeContent, "organizerCrePicNew", organizerCrePicOld, organizerCrePicNew);

        putChangeItem(changeContent, "taxpayerCrePicNew", taxpayerCrePicOld, taxpayerCrePicNew);

        putChangeItem(changeContent, "linkAuthorizePicNew", linkAuthorizePicOld, linkAuthorizePicNew);

        putChangeItem(changeContent, "legalRepCreTypeNew", legalRepCreTypeOld, legalRepCreTypeNew);

        return changeContent.toString();
    }

    public void putChangeItem(JSONObject changeContent, String propertyName, String oldValue, String newValue) {
        if (StringUtils.isNotBlank(newValue)&& !StringUtils.trimToEmpty(newValue).equals(StringUtils.trimToEmpty(oldValue)))
        {
            JSONObject changeVal = new JSONObject();
            changeVal.put("old", oldValue);
            changeVal.put("new", newValue);
            changeContent.put(propertyName, changeVal);
        }
    }

    public void putChangeItem(JSONObject changeContent, String propertyName, Integer oldValue, Integer newValue) {
        if (newValue != null && (oldValue==null||newValue.intValue() != oldValue))
        {
            JSONObject changeVal = new JSONObject();
            changeVal.put("old", oldValue);
            changeVal.put("new", newValue);
            changeContent.put(propertyName, changeVal);
        }
    }
}
