/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.bean.ent;

import java.io.Serializable;

/**
 * 
 * 企业注册信息
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.ent
 * @ClassName: BsEntRegInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午5:33:26
 * @version V1.0
 */
public class BsEntRegInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业名称(注册名) */
	private String entName;
	/** 企业互生号 */
	private String entResNo;
	/** 客户类型 */
	private Integer custType;
	/** 推荐企业（平台、服务公司）申报企业的服务公司资源号 */
	private String applyEntResNo;
	/** 上级企业（管理公司、平台） */
	private String superEntResNo;
	/** 企业地址 */
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
	/** 成立日期 */
	private String buildDate;
	/** 营业期限（现在单位是年） */
	private String endDate;
	/** 企业性质（以前是企业类型） */
	private String nature;
	/** 法人代表 */
	private String creName;
	/** 法人证件类型 */
	private Integer creType;
	/** 法人证件号码 */
	private String creNo;
	/** 法人身份证正面图片 */
	private String creFaceImg;
	/** 法人身份证反面图片 */
	private String creBackImg;
	/** 所在国家 */
	private String countryCode;
	/** 所在省份 */
	private String provinceCode;
	/** 所在城市 */
	private String cityCode;
	/** 国家名称 */
	private String countryName;
	/** 省份名称 */
	private String provinceName;
	/** 城市名称 */
	private String cityName;
	/** 经营面积 */
	private Long busiArea;
	/** 员工数量 */
	private Long entEmpNum;
	/** 经营范围 */
	private String businessScope;
	/** 注册资本 */
	private String cerDeposit;
	/** 企业简介 */
	private String introduction;
	/** 联系人 */
	private String contactPerson;
	/** 联系人职务 */
	private String contactDuty;
	/** 联系人电话 */
	private String contactPhone;
	/** 联系人地址 */
	private String contactAddr;
	/** 企业邮箱 */
	private String contactEmail;
	/** 企业QQ */
	private String officeQq;
	/** 办公电话 */
	private String officeTel;
	/** 传真号码 */
	private String officeFax;
	/** 企业英文名称 */
	private String entNameEn;
	/** 企业LOGO图片ID */
	private String logo;
	/** 所属行业 */
	private Integer industry;
	/** 邮政编码 */
	private String postCode;
	/** 企业网址 */
	private String webSite;
	/** 预留信息 */
	private String reserveInfo;
	/** 企业经度 */
	private String longitude;
	/** 企业纬度 */
	private String latitude;
	/** 操作者客户号 */
	private String operator;
	/** 联系人授权委托书 */
	private String authProxyFile;
	/** 系统开启时间  格式：yyyy-MM-dd HH:mm:ss*/
	private String openDate;
	
	/** 帮扶协议文件附件 */
	private String helpAgreement;

	/** 托管企业启用资源类型 1：首段资源 2：创业资源 3：全部资源 */
	private Integer startResType;

	/** 经营类型 0：普通 1：连锁企业*/
	private Integer businessType;
	
	/** 企业缴年费截止日期  格式：yyyy-MM-dd HH:mm:ss */
	private String expireDate;
	
	/** 企业客户号 */
	private String entCustId;
	
	/**
	 * @return the 企业名称(注册名)
	 */
	public String getEntName() {
		return entName;
	}

	/**
	 * @param 企业名称
	 *            (注册名) the entName to set
	 */
	public void setEntName(String entName) {
		this.entName = entName == null ? null : entName.trim();
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
		this.entResNo = entResNo == null ? null : entResNo.trim();
	}

	/**
	 * @return the 企业地址
	 */
	public String getEntRegAddr() {
		return entRegAddr;
	}

	/**
	 * @param 企业地址
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
	 * @return the 营业期限（现在单位是年）
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 营业期限
	 *            （现在单位是年） the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 企业性质（以前是企业类型）
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * @param 企业性质
	 *            （以前是企业类型） the nature to set
	 */
	public void setNature(String nature) {
		this.nature = nature;
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
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the 国家名称
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param 国家名称
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the 省份名称
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param 省份名称
	 *            the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the 城市名称
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param 城市名称
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the 推荐企业（平台、服务公司）申报企业的服务公司资源号
	 */
	public String getApplyEntResNo() {
		return applyEntResNo;
	}

	/**
	 * @param 推荐企业
	 *            （平台、服务公司）申报企业的服务公司资源号 the applyEntResNo to set
	 */
	public void setApplyEntResNo(String applyEntResNo) {
		this.applyEntResNo = applyEntResNo;
	}

	/**
	 * @return the 上级企业（管理公司、平台）
	 */
	public String getSuperEntResNo() {
		return superEntResNo;
	}

	/**
	 * @param 上级企业
	 *            （管理公司、平台） the superEntResNo to set
	 */
	public void setSuperEntResNo(String superEntResNo) {
		this.superEntResNo = superEntResNo;
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
	 * @return the 注册资本
	 */
	public String getCerDeposit() {
		return cerDeposit;
	}

	/**
	 * @param 注册资本
	 *            the cerDeposit to set
	 */
	public void setCerDeposit(String cerDeposit) {
		this.cerDeposit = cerDeposit;
	}

	/**
	 * @return the 企业简介
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param 企业简介
	 *            the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
	 * @return the 联系人地址
	 */
	public String getContactAddr() {
		return contactAddr;
	}

	/**
	 * @param 联系人地址
	 *            the contactAddr to set
	 */
	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
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
	 * @return the 企业LOGO图片ID
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param 企业LOGO图片ID
	 *            the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
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
	 * @return the 预留信息
	 */
	public String getReserveInfo() {
		return reserveInfo;
	}

	/**
	 * @param 预留信息
	 *            the reserveInfo to set
	 */
	public void setReserveInfo(String reserveInfo) {
		this.reserveInfo = reserveInfo;
	}

	/**
	 * @return the 企业经度
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param 企业经度
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the 企业纬度
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param 企业纬度
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the 操作者客户号
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param 操作者客户号
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
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

	/**
	 * @return the 客户类型
	 */
	public Integer getCustType() {
		return custType;
	}

	/**
	 * @param 客户类型
	 *            the custType to set
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	/**
	 * @return the 法人证件类型
	 */
	public Integer getCreType() {
		return creType;
	}

	/**
	 * @param 法人证件类型
	 *            the creType to set
	 */
	public void setCreType(Integer creType) {
		this.creType = creType;
	}

	/**
	 * @return the 经营面积
	 */
	public Long getBusiArea() {
		return busiArea;
	}

	/**
	 * @param 经营面积
	 *            the busiArea to set
	 */
	public void setBusiArea(Long busiArea) {
		this.busiArea = busiArea;
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

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getHelpAgreement() {
		return helpAgreement;
	}

	public void setHelpAgreement(String helpAgreement) {
		this.helpAgreement = helpAgreement;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号 the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}
	
}
