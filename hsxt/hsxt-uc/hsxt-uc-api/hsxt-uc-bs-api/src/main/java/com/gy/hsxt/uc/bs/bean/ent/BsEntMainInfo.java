/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.bean.ent;

import java.io.Serializable;

/**
 * 企业重要信息
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.ent
 * @ClassName: BsEntMainInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午12:19:04
 * @version V1.0
 */
public class BsEntMainInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业客户号 */
	private String entCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 企业客户类型 */
	private Integer entCustType;
	/** 企业注册名 */
	private String entName;
	/** 企业英文名称 */
	private String entNameEn;
	/** 企业注册地址 */
	private String entRegAddr;
	/** 企业营业执照号码 */
	private String busiLicenseNo;
	/** 营业执照照片 */
	private String busiLicenseImg;
	/** 组织机构代码证号 */
	private String orgCodeNo;
	/** 组织机构代码证图片 */
	private String orgCodeImg;
	/** 纳税人识别号 */
	private String taxNo;
	/** 税务登记证正面扫描图片 */
	private String taxRegImg;
	/** 法人代表 */
	private String creName;
	/** 法人证件类型 1：身份证 2：护照:3：营业执照 */
	private Integer creType;
	/** 法人证件号码 */
	private String creNo;
	/** 法人身份证正面图片 */
	private String creFaceImg;
	/** 法人身份证反面图片 */
	private String creBackImg;
	/** 联系人 */
	private String contactPerson;
	/** 联系人电话 */
	private String contactPhone;
	/** 企业注册编号 港澳特有 */
	private String entRegCode;
	/** 企业注册证书 港澳特有 */
	private String entRegImg;
	/** 工商登记编号 港澳特有 */
	private String busiRegCode;
	/** 工商登记证书 港澳特有 */
	private String busiRegImg;
	/** 企业版本号 */
	private String entVersion;
	/** 联系人授权委托书 */
	private String authProxyFile;

	/**
	 * @return the 企业版本号
	 */
	public String getEntVersion() {
		return entVersion;
	}

	/**
	 * @param 企业版本号
	 *            the entVersion to set
	 */
	public void setEntVersion(String entVersion) {
		this.entVersion = entVersion;
	}

	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 企业注册名
	 */
	public String getEntName() {
		return entName;
	}

	/**
	 * @param 企业注册名
	 *            the entName to set
	 */
	public void setEntName(String entName) {
		this.entName = entName;
	}

	/**
	 * @return the 企业英文名称
	 */
	public String getEntNameEn() {
		return entNameEn;
	}

	/**
	 * @param 企业英文名称
	 *            the entNameEn to set
	 */
	public void setEntNameEn(String entNameEn) {
		this.entNameEn = entNameEn;
	}

	/**
	 * @return the 企业注册地址
	 */
	public String getEntRegAddr() {
		return entRegAddr;
	}

	/**
	 * @param 企业注册地址
	 *            the entRegAddr to set
	 */
	public void setEntRegAddr(String entRegAddr) {
		this.entRegAddr = entRegAddr;
	}

	/**
	 * @return the 企业营业执照号码
	 */
	public String getBusiLicenseNo() {
		return busiLicenseNo;
	}

	/**
	 * @param 企业营业执照号码
	 *            the busiLicenseNo to set
	 */
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}

	/**
	 * @return the 营业执照照片
	 */
	public String getBusiLicenseImg() {
		return busiLicenseImg;
	}

	/**
	 * @param 营业执照照片
	 *            the busiLicenseImg to set
	 */
	public void setBusiLicenseImg(String busiLicenseImg) {
		this.busiLicenseImg = busiLicenseImg;
	}

	/**
	 * @return the 组织机构代码证号
	 */
	public String getOrgCodeNo() {
		return orgCodeNo;
	}

	/**
	 * @param 组织机构代码证号
	 *            the orgCodeNo to set
	 */
	public void setOrgCodeNo(String orgCodeNo) {
		this.orgCodeNo = orgCodeNo;
	}

	/**
	 * @return the 组织机构代码证图片
	 */
	public String getOrgCodeImg() {
		return orgCodeImg;
	}

	/**
	 * @param 组织机构代码证图片
	 *            the orgCodeImg to set
	 */
	public void setOrgCodeImg(String orgCodeImg) {
		this.orgCodeImg = orgCodeImg;
	}

	/**
	 * @return the 纳税人识别号
	 */
	public String getTaxNo() {
		return taxNo;
	}

	/**
	 * @param 纳税人识别号
	 *            the taxNo to set
	 */
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	/**
	 * @return the 税务登记证正面扫描图片
	 */
	public String getTaxRegImg() {
		return taxRegImg;
	}

	/**
	 * @param 税务登记证正面扫描图片
	 *            the taxRegImg to set
	 */
	public void setTaxRegImg(String taxRegImg) {
		this.taxRegImg = taxRegImg;
	}

	/**
	 * @return the 法人代表
	 */
	public String getCreName() {
		return creName;
	}

	/**
	 * @param 法人代表
	 *            the creName to set
	 */
	public void setCreName(String creName) {
		this.creName = creName;
	}

	/**
	 * @return the 法人证件类型1：身份证2：护照:3：营业执照
	 */
	public Integer getCreType() {
		return creType;
	}

	/**
	 * @param 法人证件类型1
	 *            ：身份证2：护照:3：营业执照 the creType to set
	 */
	public void setCreType(Integer creType) {
		this.creType = creType;
	}

	/**
	 * @return the 法人证件号码
	 */
	public String getCreNo() {
		return creNo;
	}

	/**
	 * @param 法人证件号码
	 *            the creNo to set
	 */
	public void setCreNo(String creNo) {
		this.creNo = creNo;
	}

	/**
	 * @return the 法人身份证正面图片
	 */
	public String getCreFaceImg() {
		return creFaceImg;
	}

	/**
	 * @param 法人身份证正面图片
	 *            the creFaceImg to set
	 */
	public void setCreFaceImg(String creFaceImg) {
		this.creFaceImg = creFaceImg;
	}

	/**
	 * @return the 法人身份证反面图片
	 */
	public String getCreBackImg() {
		return creBackImg;
	}

	/**
	 * @param 法人身份证反面图片
	 *            the creBackImg to set
	 */
	public void setCreBackImg(String creBackImg) {
		this.creBackImg = creBackImg;
	}

	/**
	 * @return the 联系人
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param 联系人
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the 联系人电话
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param 联系人电话
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the 企业注册编号港澳特有
	 */
	public String getEntRegCode() {
		return entRegCode;
	}

	/**
	 * @param 企业注册编号港澳特有
	 *            the entRegCode to set
	 */
	public void setEntRegCode(String entRegCode) {
		this.entRegCode = entRegCode;
	}

	/**
	 * @return the 企业注册证书港澳特有
	 */
	public String getEntRegImg() {
		return entRegImg;
	}

	/**
	 * @param 企业注册证书港澳特有
	 *            the entRegImg to set
	 */
	public void setEntRegImg(String entRegImg) {
		this.entRegImg = entRegImg;
	}

	/**
	 * @return the 工商登记编号港澳特有
	 */
	public String getBusiRegCode() {
		return busiRegCode;
	}

	/**
	 * @param 工商登记编号港澳特有
	 *            the busiRegCode to set
	 */
	public void setBusiRegCode(String busiRegCode) {
		this.busiRegCode = busiRegCode;
	}

	/**
	 * @return the 工商登记证书港澳特有
	 */
	public String getBusiRegImg() {
		return busiRegImg;
	}

	/**
	 * @param 工商登记证书港澳特有
	 *            the busiRegImg to set
	 */
	public void setBusiRegImg(String busiRegImg) {
		this.busiRegImg = busiRegImg;
	}

	/**
	 * @return the 企业客户类型
	 */
	public Integer getEntCustType() {
		return entCustType;
	}

	/**
	 * @param 企业客户类型
	 *            the entCustType to set
	 */
	public void setEntCustType(Integer entCustType) {
		this.entCustType = entCustType;
	}

	/**
	 * @return the 联系人授权委托书
	 */
	public String getAuthProxyFile() {
		return authProxyFile;
	}

	/**
	 * @param 联系人授权委托书
	 *            the authProxyFile to set
	 */
	public void setAuthProxyFile(String authProxyFile) {
		this.authProxyFile = authProxyFile;
	}

}
