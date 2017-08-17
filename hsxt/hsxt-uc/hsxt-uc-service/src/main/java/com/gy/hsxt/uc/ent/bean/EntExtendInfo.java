package com.gy.hsxt.uc.ent.bean;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntRegInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsHkEntRegInfo;

/**
 * 企业扩展信息
 * 
 * @Package: com.gy.hsxt.uc.ent.bean
 * @ClassName: EntExtendInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-11 上午9:03:38
 * @version V1.0
 */
public class EntExtendInfo implements Serializable {

	private static final long serialVersionUID = 1L;
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
	/** 经营类型 0：普通 1：连锁企业*/
	private Integer businessType;

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
	 * @return the
	 *         "企业客户类型1持卡人;2成员企业;3托管企业;4服务公司;5管理公司;6地区平台;7总平台;8其它地区平台11：操作员21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台51非持卡人;52非互生格式化企业"
	 */
	public Integer getCustType() {
		return custType;
	}

	/**
	 * @param 
	 *        "企业客户类型1持卡人;2成员企业;3托管企业;4服务公司;5管理公司;6地区平台;7总平台;8其它地区平台11：操作员21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台51非持卡人;52非互生格式化企业"
	 *        the custType to set
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
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
	 * @return the 企业名称
	 */
	public String getEntCustName() {
		return entCustName;
	}

	/**
	 * @param 企业名称
	 *            the entCustName to set
	 */
	public void setEntCustName(String entCustName) {
		this.entCustName = entCustName;
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
	 * @return the 企业营业营业执照照片ID
	 */
	public String getBusiLicenseImg() {
		return busiLicenseImg;
	}

	/**
	 * @param 企业营业营业执照照片ID
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
	 * @return the 组织机构代码证图片ID
	 */
	public String getOrgCodeImg() {
		return orgCodeImg;
	}

	/**
	 * @param 组织机构代码证图片ID
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
	 * @return the 税务登记证正面扫描图片ID
	 */
	public String getTaxRegImg() {
		return taxRegImg;
	}

	/**
	 * @param 税务登记证正面扫描图片ID
	 *            the taxRegImg to set
	 */
	public void setTaxRegImg(String taxRegImg) {
		this.taxRegImg = taxRegImg;
	}

	/**
	 * @return the 成立日期营业执照上的成立日期
	 */
	public Date getBuildDate() {
		return buildDate;
	}

	/**
	 * @param 成立日期营业执照上的成立日期
	 *            the buildDate to set
	 */
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * @return the 营业执照到期年限企业永久经营：LONG_TERM
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 营业执照到期年限企业永久经营
	 *            ：LONG_TERM the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the
	 *         "企业性质1：国有企业、2：集体企业、3：有限责任公司、4：股份有限公司、5：私营企业、6：中外合资企业、7：外商投资企业"
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * @param "企业性质1：国有企业、2：集体企业、3：有限责任公司、4：股份有限公司、5：私营企业、6：中外合资企业、7：外商投资企业" the
	 *        nature to set
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}

	/**
	 * @return the 企业法人代表
	 */
	public String getLegalPerson() {
		return legalPerson;
	}

	/**
	 * @param 企业法人代表
	 *            the legalPerson to set
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * @return the 法人证件号码
	 */
	public String getLegalPersonId() {
		return legalPersonId;
	}

	/**
	 * @param 法人证件号码
	 *            the legalPersonId to set
	 */
	public void setLegalPersonId(String legalPersonId) {
		this.legalPersonId = legalPersonId;
	}

	/**
	 * @return the 法人证件类型1：身份证2：护照:
	 */
	public Integer getCredentialType() {
		return credentialType;
	}

	/**
	 * @param 法人证件类型1
	 *            ：身份证2：护照: the credentialType to set
	 */
	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}

	/**
	 * @return the 法人身份证正面图片ID
	 */
	public String getLegalPersonPicFront() {
		return legalPersonPicFront;
	}

	/**
	 * @param 法人身份证正面图片ID
	 *            the legalPersonPicFront to set
	 */
	public void setLegalPersonPicFront(String legalPersonPicFront) {
		this.legalPersonPicFront = legalPersonPicFront;
	}

	/**
	 * @return the 法人身份证反面图片ID
	 */
	public String getLegalPersonPicBack() {
		return legalPersonPicBack;
	}

	/**
	 * @param 法人身份证反面图片ID
	 *            the legalPersonPicBack to set
	 */
	public void setLegalPersonPicBack(String legalPersonPicBack) {
		this.legalPersonPicBack = legalPersonPicBack;
	}

	/**
	 * @return the 所在国家
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 所在国家
	 *            the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 所在省份
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 所在省份
	 *            the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 所在城市
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 所在城市
	 *            the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
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
	 * @return the 注册资本
	 */
	public String getCertificateDeposit() {
		return certificateDeposit;
	}

	/**
	 * @param 注册资本
	 *            the certificateDeposit to set
	 */
	public void setCertificateDeposit(String certificateDeposit) {
		this.certificateDeposit = certificateDeposit;
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
	public String getEntCustNameEn() {
		return entCustNameEn;
	}

	/**
	 * @param 企业英文名称
	 *            the entCustNameEn to set
	 */
	public void setEntCustNameEn(String entCustNameEn) {
		this.entCustNameEn = entCustNameEn;
	}

	/**
	 * @return the 企业LOGO图片ID
	 */
	public String getLogoImg() {
		return logoImg;
	}

	/**
	 * @param 企业LOGO图片ID
	 *            the logoImg to set
	 */
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
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
	 * @return the 税率
	 */
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	/**
	 * @param 税率
	 *            the taxRate to set
	 */
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * @return the 联系人授权书附件
	 */
	public String getContactProxy() {
		return contactProxy;
	}

	/**
	 * @param 联系人授权书附件
	 *            the contactProxy to set
	 */
	public void setContactProxy(String contactProxy) {
		this.contactProxy = contactProxy;
	}

	/**
	 * @return the 帮扶协议文件附件
	 */
	public String getHelpAgreement() {
		return helpAgreement;
	}

	/**
	 * @param 帮扶协议文件附件
	 *            the helpAgreement to set
	 */
	public void setHelpAgreement(String helpAgreement) {
		this.helpAgreement = helpAgreement;
	}

	/**
	 * @return the "托管企业启用资源类型托管企业启用资源类型1：首段资源2：创业资源3：全部资源"
	 */
	public Integer getStartResType() {
		return startResType;
	}

	/**
	 * @param "托管企业启用资源类型托管企业启用资源类型1：首段资源2：创业资源3：全部资源" the startResType to set
	 */
	public void setStartResType(Integer startResType) {
		this.startResType = startResType;
	}

	/**
	 * @return the
	 *         "企业重要信息修改计数企业重要信息修改计数，也作为企业重要信息版本号用设备、手机登陆需要是需要传入修改计数作为对比如果不一样，需要把修改的企业信息传回给手机、设备，已便他们更新同步企业数据"
	 */
	public Integer getModifyAcount() {
		return modifyAcount;
	}

	/**
	 * @param 
	 *        "企业重要信息修改计数企业重要信息修改计数，也作为企业重要信息版本号用设备、手机登陆需要是需要传入修改计数作为对比如果不一样，需要把修改的企业信息传回给手机、设备，已便他们更新同步企业数据"
	 *        the modifyAcount to set
	 */
	public void setModifyAcount(Integer modifyAcount) {
		this.modifyAcount = modifyAcount;
	}

	/**
	 * @return the 企业注册编号港澳企业专有
	 */
	public String getEntRegCode() {
		return entRegCode;
	}

	/**
	 * @param 企业注册编号港澳企业专有
	 *            the entRegCode to set
	 */
	public void setEntRegCode(String entRegCode) {
		this.entRegCode = entRegCode;
	}

	/**
	 * @return the 企业注册证书港澳企业专有
	 */
	public String getEntRegImg() {
		return entRegImg;
	}

	/**
	 * @param 企业注册证书港澳企业专有
	 *            the entRegImg to set
	 */
	public void setEntRegImg(String entRegImg) {
		this.entRegImg = entRegImg;
	}

	/**
	 * @return the 工商登记编号港澳企业专有
	 */
	public String getBusiRegCode() {
		return busiRegCode;
	}

	/**
	 * @param 工商登记编号港澳企业专有
	 *            the busiRegCode to set
	 */
	public void setBusiRegCode(String busiRegCode) {
		this.busiRegCode = busiRegCode;
	}

	/**
	 * @return the 工商登记证书港澳企业专有
	 */
	public String getBusiRegImg() {
		return busiRegImg;
	}

	/**
	 * @param 工商登记证书港澳企业专有
	 *            the busiRegImg to set
	 */
	public void setBusiRegImg(String busiRegImg) {
		this.busiRegImg = busiRegImg;
	}

	/**
	 * @return the 标记此条记录的状态Y:可用N:不可用
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * @param 标记此条记录的状态Y
	 *            :可用N:不可用 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	/**
	 * @return the 创建时间创建时间，取记录创建时的系统时间
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param 创建时间创建时间
	 *            ，取记录创建时的系统时间 the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the 创建人由谁创建，值为用户的伪键ID
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param 创建人由谁创建
	 *            ，值为用户的伪键ID the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the 更新时间更新时间，取记录更新时的系统时间
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param 更新时间更新时间
	 *            ，取记录更新时的系统时间 the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the 更新人由谁更新，值为用户的伪键ID
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param 更新人由谁更新
	 *            ，值为用户的伪键ID the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	/**
	 * 设置注册参数
	 * 
	 * @param regInfo
	 *            企业注册信息
	 */
	public void fillRegParams(BsEntRegInfo regInfo) {
		String buildDate=regInfo.getBuildDate();
		if (StringUtils.isNotBlank(buildDate)) {
			if(buildDate.length()==10){ // yyyy-mm-dd
				buildDate=buildDate+"  00:00:00";
			}
			//"Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]"
			try {
				this.buildDate = Timestamp.valueOf(buildDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		this.startResType = regInfo.getStartResType();
		this.busiArea = regInfo.getBusiArea();
		this.busiLicenseImg = regInfo.getBusiLicenseImg();
		this.busiLicenseNo = regInfo.getBusiLicenseNo();
		this.businessScope = regInfo.getBusinessScope();
		this.certificateDeposit = regInfo.getCerDeposit();
		this.cityNo = regInfo.getCityCode();
		this.contactAddr = regInfo.getContactAddr();
		this.contactDuty = regInfo.getContactDuty();
		this.contactEmail = regInfo.getContactEmail();
		this.contactPerson = regInfo.getContactPerson();
		this.contactPhone = regInfo.getContactPhone();
		this.countryNo = regInfo.getCountryCode();
		this.createdby = regInfo.getOperator();
		this.credentialType = regInfo.getCreType();
		this.endDate = regInfo.getEndDate();
		this.entCustName = regInfo.getEntName();
		this.entCustNameEn = regInfo.getEntNameEn();
		this.entRegAddr = regInfo.getEntRegAddr();
		this.entResNo = regInfo.getEntResNo();
		this.applyEntResNo = regInfo.getApplyEntResNo();
		this.superEntResNo = regInfo.getSuperEntResNo();
		this.custType = regInfo.getCustType();
		this.entEmpNum = regInfo.getEntEmpNum();
		this.industry = regInfo.getIndustry();
		this.introduction = regInfo.getIntroduction();
		this.latitude = regInfo.getLatitude();
		this.legalPerson = regInfo.getCreName();
		this.legalPersonId = regInfo.getCreNo();
		this.legalPersonPicBack = regInfo.getCreBackImg();
		this.legalPersonPicFront = regInfo.getCreFaceImg();
		this.logoImg = regInfo.getLogo();
		this.longitude = regInfo.getLongitude();
		this.modifyAcount = 0;
		this.nature = regInfo.getNature();
		this.officeFax = regInfo.getOfficeFax();
		this.officeQq = regInfo.getOfficeQq();
		this.officeTel = regInfo.getOfficeTel();
		this.orgCodeNo = regInfo.getOrgCodeNo();
		this.orgCodeImg = regInfo.getOrgCodeImg();
		this.postCode = regInfo.getPostCode();
		this.provinceNo = regInfo.getProvinceCode();
		this.taxNo = regInfo.getTaxNo();
		this.taxRegImg = regInfo.getTaxRegImg();
		this.updatedby = regInfo.getOperator();
		this.webSite = regInfo.getWebSite();
		this.contactProxy = regInfo.getAuthProxyFile();
		this.businessType = regInfo.getBusinessType();
		this.helpAgreement = regInfo.getHelpAgreement();
		if (regInfo instanceof BsHkEntRegInfo) {
			BsHkEntRegInfo hkRegInfo = (BsHkEntRegInfo) regInfo;
			this.entRegCode = hkRegInfo.getEntRegCode();
			this.entRegImg = hkRegInfo.getEntRegImg();
			this.busiRegCode = hkRegInfo.getBusiRegCode();
			this.busiRegImg = hkRegInfo.getBusiRegImg();
		}
	}

	/**
	 * 构建AsEntMainInfo 对象
	 * 
	 * @return
	 */
	public AsEntMainInfo buildAsEntMainInfo() {
		AsEntMainInfo mainInfo = new AsEntMainInfo();
		mainInfo.setBusiLicenseImg(this.busiLicenseImg);
		mainInfo.setBusiLicenseNo(this.busiLicenseNo);
		mainInfo.setBusiRegCode(this.busiRegCode);
		mainInfo.setBusiRegImg(this.busiRegImg);
		mainInfo.setContactPerson(this.contactPerson);
		mainInfo.setContactPhone(this.contactPhone);
		mainInfo.setCreBackImg(this.legalPersonPicBack);
		mainInfo.setCreFaceImg(this.legalPersonPicFront);
		mainInfo.setCreName(this.legalPerson);
		mainInfo.setCreNo(this.legalPersonId);
		mainInfo.setCreType(this.credentialType);
		mainInfo.setEntCustId(this.entCustId);
		mainInfo.setEntName(this.entCustName);
		mainInfo.setEntNameEn(this.entCustNameEn);
		mainInfo.setEntResNo(this.entResNo);
		mainInfo.setEntCustType(this.custType);
		mainInfo.setEntRegAddr(this.entRegAddr);
		mainInfo.setEntRegCode(this.entRegCode);
		mainInfo.setEntRegImg(this.entRegImg);
		mainInfo.setOrgCodeImg(this.orgCodeImg);
		mainInfo.setOrgCodeNo(this.orgCodeNo);
		mainInfo.setTaxNo(this.taxNo);
		mainInfo.setTaxRegImg(this.taxRegImg);
		mainInfo.setEntVersion(String.valueOf(this.modifyAcount));
		mainInfo.setAuthProxyFile(this.contactProxy);
		return mainInfo;
	}

	/**
	 * 构建BsEntMainInfo 对象
	 * 
	 * @return
	 */
	public BsEntMainInfo buildBsEntMainInfo() {
		BsEntMainInfo bsEntMainInfo = new BsEntMainInfo();
		AsEntMainInfo asEntMainInfo = buildAsEntMainInfo();
		BeanUtils.copyProperties(asEntMainInfo, bsEntMainInfo);
		return bsEntMainInfo;
	}

	/**
	 * 把BsEntMainInfo 对象转换成 EntExtendInfo对象
	 * 
	 * @param mainInfo
	 *            企业重要信息
	 * @return
	 */
	public static EntExtendInfo generateExtendInfo(BsEntMainInfo mainInfo) {
		EntExtendInfo extendInfo = new EntExtendInfo();
		extendInfo.setBusiLicenseImg(mainInfo.getBusiLicenseImg());
		extendInfo.setBusiLicenseNo(mainInfo.getBusiLicenseNo());
		extendInfo.setBusiRegCode(mainInfo.getBusiRegCode());
		extendInfo.setBusiRegImg(mainInfo.getBusiRegImg());
		extendInfo.setContactPerson(mainInfo.getContactPerson());
		extendInfo.setContactPhone(mainInfo.getContactPhone());
		extendInfo.setLegalPersonPicBack(mainInfo.getCreBackImg());
		extendInfo.setLegalPersonPicFront(mainInfo.getCreFaceImg());
		extendInfo.setLegalPerson(mainInfo.getCreName());
		extendInfo.setLegalPersonId(mainInfo.getCreNo());
		extendInfo.setCredentialType(mainInfo.getCreType());
		extendInfo.setEntCustId(mainInfo.getEntCustId());
		extendInfo.setEntCustName(mainInfo.getEntName());
		extendInfo.setEntCustNameEn(mainInfo.getEntNameEn());
		extendInfo.setEntRegAddr(mainInfo.getEntRegAddr());
		extendInfo.setEntRegCode(mainInfo.getEntRegCode());
		extendInfo.setEntRegImg(mainInfo.getEntRegImg());
		extendInfo.setOrgCodeImg(mainInfo.getOrgCodeImg());
		extendInfo.setOrgCodeNo(mainInfo.getOrgCodeNo());
		extendInfo.setTaxNo(mainInfo.getTaxNo());
		extendInfo.setContactProxy(mainInfo.getAuthProxyFile());
		extendInfo.setTaxRegImg(mainInfo.getTaxRegImg());
		return extendInfo;
	}

	/**
	 * 生成企业基本信息 AsEntBaseInfo
	 * 
	 * @return
	 */
	public AsEntBaseInfo bulidAsEntBaseInfo() {
		AsEntBaseInfo baseInfo = new AsEntBaseInfo();
		if (null != buildDate) {
			baseInfo.setBuildDate(DateUtil.DateToString(this.buildDate));
		}
		baseInfo.setBusinessArea(this.busiArea);
		baseInfo.setBusinessScope(this.businessScope);
		baseInfo.setCityCode(this.cityNo);
		baseInfo.setContactAddr(this.contactAddr);
		baseInfo.setContactDuty(this.contactDuty);
		baseInfo.setContactEmail(this.contactEmail);
		baseInfo.setCountryCode(this.countryNo);
		baseInfo.setEndDate(this.endDate);
		baseInfo.setEntCustId(this.entCustId);
		baseInfo.setEntEmpNum(this.entEmpNum);
		baseInfo.setEntName(this.entCustName);
		baseInfo.setEntNameEn(this.entCustNameEn);
		baseInfo.setEntResNo(this.entResNo);
		baseInfo.setEnsureInfo(this.ensureInfo);
		baseInfo.setEntCustType(this.custType);
		baseInfo.setIndustry(this.industry);
		baseInfo.setIntroduction(this.introduction);
		baseInfo.setLogo(this.logoImg);
		baseInfo.setNature(this.nature);
		baseInfo.setOfficeFax(this.officeFax);
		baseInfo.setOfficeQq(this.officeQq);
		baseInfo.setOfficeTel(this.officeTel);
		baseInfo.setPostCode(this.postCode);
		baseInfo.setProvinceCode(this.provinceNo);
		baseInfo.setWebSite(this.webSite);
		baseInfo.setStartResType(this.startResType);
		// baseInfo.setSuperEntResNo(this.superEntResNo);
		return baseInfo;
	}

	/**
	 * 把 AsEntBaseInfo 对象转换成 EntExtendInfo对象
	 * 
	 * @param baseInfo
	 *            企业一般信息
	 * @return
	 */
	public static EntExtendInfo generateEntendInfo(AsEntBaseInfo baseInfo) {
		EntExtendInfo extendInfo = new EntExtendInfo();
		if (isNotBlank(baseInfo.getBuildDate())) {
			extendInfo.setBuildDate(DateUtil.StringToDate(baseInfo
					.getBuildDate()));
		}
		extendInfo.setBusinessScope(baseInfo.getBusinessScope());
		extendInfo.setBusiArea(baseInfo.getBusinessArea());
		extendInfo.setCityNo(baseInfo.getCityCode());
		extendInfo.setContactAddr(baseInfo.getContactAddr());
		extendInfo.setContactDuty(baseInfo.getContactDuty());
		extendInfo.setContactEmail(baseInfo.getContactEmail());
		extendInfo.setCountryNo(baseInfo.getCountryCode());
		extendInfo.setEndDate(baseInfo.getEndDate());
		extendInfo.setEntCustId(baseInfo.getEntCustId());
		extendInfo.setEntEmpNum(baseInfo.getEntEmpNum());
		extendInfo.setEntCustName(baseInfo.getEntName());
		extendInfo.setEntCustNameEn(baseInfo.getEntNameEn());
		extendInfo.setIndustry(baseInfo.getIndustry());
		extendInfo.setIntroduction(baseInfo.getIntroduction());
		extendInfo.setLogoImg(baseInfo.getLogo());
		extendInfo.setNature(baseInfo.getNature());
		extendInfo.setOfficeFax(baseInfo.getOfficeFax());
		extendInfo.setOfficeQq(baseInfo.getOfficeQq());
		extendInfo.setOfficeTel(baseInfo.getOfficeTel());
		extendInfo.setPostCode(baseInfo.getPostCode());
		extendInfo.setWebSite(baseInfo.getWebSite());
		extendInfo.setStartResType(baseInfo.getStartResType());
		extendInfo.setEnsureInfo(baseInfo.getEnsureInfo());
		return extendInfo;
	}
	
	public static EntExtendInfo generateEntExtInfo(AsEntExtendInfo vo) {
		EntExtendInfo bean = new EntExtendInfo();
		BeanUtils.copyProperties(vo, bean, new String[]{"buildDate"});
		if (isNotBlank(vo.getBuildDate())) {
			bean.setBuildDate(DateUtil.StringToDate(vo
					.getBuildDate()));
		}
		return bean;
	}

	public AsEntExtendInfo buildAsEntExtInfo( ) {
		AsEntExtendInfo vo = new AsEntExtendInfo();
		BeanUtils.copyProperties(this, vo, new String[]{"buildDate"});
		if (null != buildDate) {
			vo.setBuildDate(DateUtil.DateToString(this.buildDate));
		}
		if(createDate!=null){
			vo.setCreateDate(DateUtil.DateToString(this.createDate));
		}
		return vo;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}