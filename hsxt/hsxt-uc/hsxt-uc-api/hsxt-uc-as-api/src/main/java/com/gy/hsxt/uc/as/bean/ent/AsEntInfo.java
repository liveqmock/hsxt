/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.ent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class AsEntInfo implements Serializable {

	private static final long serialVersionUID = 5144860523238290616L;
	private String entName;
	private String entNameEn;
	/** 企业客户号 */
	private String entCustId;
	/** 企业互生号 */
	private String entResNo;
	/**
	 * "企业客户类型1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台; 8 其它地区平台 11：操作员
	 * 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台 51 非持卡人; 52 非互生格式化企业"
	 */
	private Integer custType;
	/** 推荐企业（平台、服务公司）申报企业的服务公司资源号 */
	private String applyEntResNo;
	/** 上级企业（管理公司、平台） */
	private String superEntResNo;
	/** 企业名称 */
	private String entCustName;
	/** 企业注册地址 */
	private String entRegAddr;
	/** 企业营业执照号码 */
	private String busiLicenseNo;
	/** 企业营业营业执照照片ID */
	private String busiLicenseImg;
	/** 组织机构代码证号 */
	private String orgCodeNo;
	/** 组织机构代码证图片ID */
	private String orgCodeImg;
	/** 纳税人识别号 */
	private String taxNo;
	/** 税务登记证正面扫描图片ID */
	private String taxRegImg;
	/** 成立日期营业执照上的成立日期 */
	private Date buildDate;
	/** 营业执照到期年限企业永久经营：LONG_TERM */
	private String endDate;
	/**
	 * "企业性质1：国有企业、 2：集体企业、 3：有限责任公司、 4：股份有限公司、 5：私营企业、 6：中外合资企业、 7：外商投资企业"
	 */
	private String nature;
	/** 企业法人代表 */
	private String legalPerson;
	/** 法人证件号码 */
	private String legalPersonId;
	/** 法人证件类型1：身份证2：护照: */
	private Integer credentialType;
	/** 法人身份证正面图片ID */
	private String legalPersonPicFront;
	/** 法人身份证反面图片ID */
	private String legalPersonPicBack;
	/** 所在国家 */
	private String countryNo;
	/** 所在省份 */
	private String provinceNo;
	/** 所在城市 */
	private String cityNo;
	/** 经营面积 */
	private Long busiArea;
	/** 员工数量 */
	private Long entEmpNum;
	/** 注册资本 */
	private String certificateDeposit;
	/** 经营范围 */
	private String businessScope;
	/** 企业简介 */
	private String introduction;
	/** 联系人 */
	private String contactPerson;
	/** 联系人职务 */
	private String contactDuty;
	/** 联系人电话 */
	private String contactPhone;
	/** 企业联系地址 */
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
	private String entCustNameEn;
	/** 企业LOGO图片ID */
	private String logoImg;
	/** 所属行业 */
	private Integer industry;
	/** 邮政编码 */
	private String postCode;
	/** 企业网址 */
	private String webSite;
	/** 预留信息 */
	private String ensureInfo;
	/** 企业经度 */
	private String longitude;
	/** 企业纬度 */
	private String latitude;
	/** 税率 */
	private BigDecimal taxRate;
	/** 联系人授权书附件 */
	private String contactProxy;
	/** 帮扶协议文件附件 */
	private String helpAgreement;
	/**
	 * "托管企业启用资源类型托管企业启用资源类型 1：首段资源 2：创业资源 3：全部资源"
	 */
	private Integer startResType;
	/**
	 * "企业重要信息修改计数企业重要信息修改计数，也作为企业重要信息版本号用 设备、手机登陆需要是需要传入修改计数作为对比,如果不一样，
	 * 需要把修改的企业信息传回给手机、设备，已便他们更新同步企业数据"
	 */
	private Integer modifyAcount;
	/** 企业注册编号 港澳企业专有 */
	private String entRegCode;
	/** 企业注册证书 港澳企业专有 */
	private String entRegImg;
	/** 工商登记编号 港澳企业专有 */
	private String busiRegCode;
	/** 工商登记证书 港澳企业专有 */
	private String busiRegImg;
	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;
	/** 创建时间创建时间，取记录创建时的系统时间 */
	private Timestamp createDate;
	/** 创建人由谁创建，值为用户的伪键ID */
	private String createdby;
	/** 更新时间更新时间，取记录更新时的系统时间 */
	private Timestamp updateDate;
	/** 更新人由谁更新，值为用户的伪键ID */
	private String updatedby;
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public String getEntNameEn() {
		return entNameEn;
	}
	public void setEntNameEn(String entNameEn) {
		this.entNameEn = entNameEn;
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
	public Integer getCustType() {
		return custType;
	}
	public void setCustType(Integer custType) {
		this.custType = custType;
	}
	public String getApplyEntResNo() {
		return applyEntResNo;
	}
	public void setApplyEntResNo(String applyEntResNo) {
		this.applyEntResNo = applyEntResNo;
	}
	public String getSuperEntResNo() {
		return superEntResNo;
	}
	public void setSuperEntResNo(String superEntResNo) {
		this.superEntResNo = superEntResNo;
	}
	public String getEntCustName() {
		return entCustName;
	}
	public void setEntCustName(String entCustName) {
		this.entCustName = entCustName;
	}
	public String getEntRegAddr() {
		return entRegAddr;
	}
	public void setEntRegAddr(String entRegAddr) {
		this.entRegAddr = entRegAddr;
	}
	public String getBusiLicenseNo() {
		return busiLicenseNo;
	}
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}
	public String getBusiLicenseImg() {
		return busiLicenseImg;
	}
	public void setBusiLicenseImg(String busiLicenseImg) {
		this.busiLicenseImg = busiLicenseImg;
	}
	public String getOrgCodeNo() {
		return orgCodeNo;
	}
	public void setOrgCodeNo(String orgCodeNo) {
		this.orgCodeNo = orgCodeNo;
	}
	public String getOrgCodeImg() {
		return orgCodeImg;
	}
	public void setOrgCodeImg(String orgCodeImg) {
		this.orgCodeImg = orgCodeImg;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public String getTaxRegImg() {
		return taxRegImg;
	}
	public void setTaxRegImg(String taxRegImg) {
		this.taxRegImg = taxRegImg;
	}
	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getLegalPersonId() {
		return legalPersonId;
	}
	public void setLegalPersonId(String legalPersonId) {
		this.legalPersonId = legalPersonId;
	}
	public Integer getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}
	public String getLegalPersonPicFront() {
		return legalPersonPicFront;
	}
	public void setLegalPersonPicFront(String legalPersonPicFront) {
		this.legalPersonPicFront = legalPersonPicFront;
	}
	public String getLegalPersonPicBack() {
		return legalPersonPicBack;
	}
	public void setLegalPersonPicBack(String legalPersonPicBack) {
		this.legalPersonPicBack = legalPersonPicBack;
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
	public Long getBusiArea() {
		return busiArea;
	}
	public void setBusiArea(Long busiArea) {
		this.busiArea = busiArea;
	}
	public Long getEntEmpNum() {
		return entEmpNum;
	}
	public void setEntEmpNum(Long entEmpNum) {
		this.entEmpNum = entEmpNum;
	}
	public String getCertificateDeposit() {
		return certificateDeposit;
	}
	public void setCertificateDeposit(String certificateDeposit) {
		this.certificateDeposit = certificateDeposit;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactDuty() {
		return contactDuty;
	}
	public void setContactDuty(String contactDuty) {
		this.contactDuty = contactDuty;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactAddr() {
		return contactAddr;
	}
	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getOfficeQq() {
		return officeQq;
	}
	public void setOfficeQq(String officeQq) {
		this.officeQq = officeQq;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	public String getOfficeFax() {
		return officeFax;
	}
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}
	public String getEntCustNameEn() {
		return entCustNameEn;
	}
	public void setEntCustNameEn(String entCustNameEn) {
		this.entCustNameEn = entCustNameEn;
	}
	public String getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	public Integer getIndustry() {
		return industry;
	}
	public void setIndustry(Integer industry) {
		this.industry = industry;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getEnsureInfo() {
		return ensureInfo;
	}
	public void setEnsureInfo(String ensureInfo) {
		this.ensureInfo = ensureInfo;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public String getContactProxy() {
		return contactProxy;
	}
	public void setContactProxy(String contactProxy) {
		this.contactProxy = contactProxy;
	}
	public String getHelpAgreement() {
		return helpAgreement;
	}
	public void setHelpAgreement(String helpAgreement) {
		this.helpAgreement = helpAgreement;
	}
	public Integer getStartResType() {
		return startResType;
	}
	public void setStartResType(Integer startResType) {
		this.startResType = startResType;
	}
	public Integer getModifyAcount() {
		return modifyAcount;
	}
	public void setModifyAcount(Integer modifyAcount) {
		this.modifyAcount = modifyAcount;
	}
	public String getEntRegCode() {
		return entRegCode;
	}
	public void setEntRegCode(String entRegCode) {
		this.entRegCode = entRegCode;
	}
	public String getEntRegImg() {
		return entRegImg;
	}
	public void setEntRegImg(String entRegImg) {
		this.entRegImg = entRegImg;
	}
	public String getBusiRegCode() {
		return busiRegCode;
	}
	public void setBusiRegCode(String busiRegCode) {
		this.busiRegCode = busiRegCode;
	}
	public String getBusiRegImg() {
		return busiRegImg;
	}
	public void setBusiRegImg(String busiRegImg) {
		this.busiRegImg = busiRegImg;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	
	
}
