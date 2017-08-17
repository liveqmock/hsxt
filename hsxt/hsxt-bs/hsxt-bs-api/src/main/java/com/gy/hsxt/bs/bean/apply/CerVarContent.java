/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: CerVarContent
 * @Description: 证书占位符替换内容
 * 
 * @author: xiaofl
 * @date: 2016-1-7 下午5:01:04
 * @version V1.0
 */
public class CerVarContent implements Serializable {

    private static final long serialVersionUID = -3758378493357547702L;

    /** 平台名称(发证单位) **/
    private String platName;

    /** 服务公司互生号(所属服务机构互生号) **/
    private String serResNo;

    /** 服务公司名称 (所属服务机构) **/
    private String serEntName;

    /** 企业互生号(证书编号) **/
    private String entResNo;

    /** 企业名称(销售商) **/
    private String entCustName;

    /** 营业执照编号(证件号码) **/
    private String licenseNo;

    /** 唯一识别码 **/
    private String uniqueCode;

    /** 验证地址 **/
    private String checkAddress;

    /** 发证日期 **/
    private String sendDate;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String mobile;

    public String getSerResNo() {
        return serResNo;
    }

    public void setSerResNo(String serResNo) {
        this.serResNo = serResNo;
    }

    public String getSerEntName() {
        return serEntName;
    }

    public void setSerEntName(String serEntName) {
        this.serEntName = serEntName;
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

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
    }

}
