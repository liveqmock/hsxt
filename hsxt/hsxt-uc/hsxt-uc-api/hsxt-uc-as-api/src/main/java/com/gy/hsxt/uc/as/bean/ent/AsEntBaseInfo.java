/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.ent;

import java.io.Serializable;

/**
 * 
 * 企业一般信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.ent
 * @ClassName: AsEntBaseInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午5:33:45
 * @version V1.0
 */
public class AsEntBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业客户号 */
	private String entCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 企业名称 */
	private String entName;
	/** 企业英文名称 */
	private String entNameEn;
	/** 企业客户类型 */
	private Integer entCustType;
	/** 成立日期 */
	private String buildDate;
	/** 营业期限 */
	private String endDate;
	/** 企业性质 */
	private String nature;
	/** 所在国家 */
	private String countryCode;
	/** 所在省份 */
	private String provinceCode;
	/** 所在城市 */
	private String cityCode;
	/** 经营面积 */
	private Long businessArea;
	/** 员工数量 */
	private Long entEmpNum;
	/** 经营范围 */
	private String businessScope;
	/** 联系人职务 */
	private String contactDuty;
	/** 企业联系地址 */
	private String contactAddr;
	/** 办公电话 */
	private String officeTel;
	/** 传真号码 */
	private String officeFax;
	/** 企业QQ */
	private String officeQq;
	/** 企业邮箱 */
	private String contactEmail;
	/** 企业logo */
	private String logo;
	/** 所属行业 */
	private Integer industry;
	/** 邮政编码 */
	private String postCode;
	/** 企业网址 */
	private String webSite;
	/** 简介 */
	private String introduction;
	/** 预留信息 */
	private String ensureInfo;
	/** 托管企业启用资源类型 1：首段资源 2：创业资源 3：全部资源 */
	private Integer startResType;
	/** 上级企业（管理公司、平台） */
	private String superEntResNo;
	
	/**
	 * @return the 上级企业（管理公司、平台）
	 */
	public String getSuperEntResNo() {
		return superEntResNo;
	}

	/**
	 * @param 上级企业（管理公司、平台） the superEntResNo to set
	 */
	public void setSuperEntResNo(String superEntResNo) {
		this.superEntResNo = superEntResNo;
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
	 * @return the 成立日期
	 */
	public String getBuildDate() {
		return buildDate;
	}

	/**
	 * @param 成立日期
	 *            the buildDate to set
	 */
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * @return the 营业期限
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 营业期限
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 企业性质
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * @param 企业性质
	 *            the nature to set
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}

	/**
	 * @return the 所在国家
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param 所在国家
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the 所在省份
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param 所在省份
	 *            the provinceCode to set
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return the 所在城市
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param 所在城市
	 *            the cityCode to set
	 */
	public void setCityCode(String cityCopde) {
		this.cityCode = cityCopde;
	}

	/**
	 * @return the 经营面积
	 */
	public Long getBusinessArea() {
		return businessArea;
	}

	/**
	 * @param 经营面积
	 *            the businessArea to set
	 */
	public void setBusinessArea(Long businessArea) {
		this.businessArea = businessArea;
	}

	/**
	 * @return the 员工数量
	 */
	public Long getEntEmpNum() {
		return entEmpNum;
	}

	/**
	 * @param 员工数量
	 *            the entEmpNum to set
	 */
	public void setEntEmpNum(Long entEmpNum) {
		this.entEmpNum = entEmpNum;
	}

	/**
	 * @return the 经营范围
	 */
	public String getBusinessScope() {
		return businessScope;
	}

	/**
	 * @param 经营范围
	 *            the businessScope to set
	 */
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	/**
	 * @return the 联系人职务
	 */
	public String getContactDuty() {
		return contactDuty;
	}

	/**
	 * @param 联系人职务
	 *            the contactDuty to set
	 */
	public void setContactDuty(String contactDuty) {
		this.contactDuty = contactDuty;
	}

	/**
	 * @return the 企业联系地址
	 */
	public String getContactAddr() {
		return contactAddr;
	}

	/**
	 * @param 企业联系地址
	 *            the contactAddr to set
	 */
	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	/**
	 * @return the 办公电话
	 */
	public String getOfficeTel() {
		return officeTel;
	}

	/**
	 * @param 办公电话
	 *            the officeTel to set
	 */
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	/**
	 * @return the 传真号码
	 */
	public String getOfficeFax() {
		return officeFax;
	}

	/**
	 * @param 传真号码
	 *            the officeFax to set
	 */
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}

	/**
	 * @return the 企业QQ
	 */
	public String getOfficeQq() {
		return officeQq;
	}

	/**
	 * @param 企业QQ
	 *            the officeQq to set
	 */
	public void setOfficeQq(String officeQq) {
		this.officeQq = officeQq;
	}

	/**
	 * @return the 企业邮箱
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param 企业邮箱
	 *            the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the 企业logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param 企业logo
	 *            the 企业logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the 所属行业
	 */
	public Integer getIndustry() {
		return industry;
	}

	/**
	 * @param 所属行业
	 *            the industry to set
	 */
	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	/**
	 * @return the 邮政编码
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param 邮政编码
	 *            the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the 企业网址
	 */
	public String getWebSite() {
		return webSite;
	}

	/**
	 * @param 企业网址
	 *            the webSite to set
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * @return the 简介
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param 简介
	 *            the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
	 * @return the 预留信息
	 */
	public String getEnsureInfo() {
		return ensureInfo;
	}

	/**
	 * @param 预留信息
	 *            the ensureInfo to set
	 */
	public void setEnsureInfo(String ensureInfo) {
		this.ensureInfo = ensureInfo;
	}

	/**
	 * @return the 托管企业启用资源类型1：首段资源2：创业资源3：全部资源
	 */
	public Integer getStartResType() {
		return startResType;
	}

	/**
	 * @param 托管企业启用资源类型1
	 *            ：首段资源2：创业资源3：全部资源 the startResType to set
	 */
	public void setStartResType(Integer startResType) {
		this.startResType = startResType;
	}

}
