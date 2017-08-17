package com.gy.hsxt.uc.as.bean.result;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.gy.hsxt.uc.as.bean.auth.AsRole;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-as-api
 * @package com.gy.hsxt.uc.as.bean.common.AsOperatorLoginResult.java
 * @className AsOperatorLoginResult
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-19 上午10:59:41
 * @updateUser lixuan
 * @updateDate 2015-10-19 上午10:59:41
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class AsOperatorLoginResult extends LoginResult implements Serializable {
	private static final long serialVersionUID = 1977720270370917016L;

	/********************** 企业的扩展信息   begin***********************/
	/**
	 * 保留信息
	 */
	private String reserveInfo;
	/**
	 * 企业资源号
	 */
	private String entResNo;
	/**
	 * 企业客户号
	 */
	private String entCustId;
	/**
	 * 企业类型
	 */
	private String entResType;
	/**
	 * LOGO
	 */
	private String logo;
	/**
	 * 企业名称
	 */
	private String entCustName;
	
	/**
	 * 国家代码
	 */
	private String countryCode;
	/**
	 * 省份代码
	 */
	private String provinceCode;
	/**
	 * 城市代码
	 */
	private String cityCode;
	
	/** 推荐企业（平台、服务公司）申报企业的服务公司资源号 */
	private String applyEntResNo;
	/** 上级企业（管理公司、平台） */
	private String superEntResNo;
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
	private String buildDate;
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
	/** 经营面积 */
	private Long busiArea;
	private Long entEmpNum;
	/** 员工数量 */
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
	/** 所属行业 */
	private Integer industry;
	/** 邮政编码 */
	private String postCode;
	/** 企业网址 */
	private String webSite;

	/** 企业经度 */
	private String longitude;
	/** 企业纬度 */
	private String latitude;
	/** 税率 */
	private String taxRate;
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
	
	/** 经营类型 0：普通 1：连锁企业*/
	private Integer businessType;
	/********************** 企业的扩展信息   end***********************/
	
	/********************** 企业的状态信息   begin***********************/
	/** 是否已注册 1:是 0：否 */
	private Integer isReg;
	/** 是否欠年费 1:是 0：否 */
	private Integer isOweFee;
	/** 是否老企业 1:是 0：否 */
	private Integer isOldEnt;
	/** 企业是否关闭（冻结） 1:是 0：否 */
	private Integer isClosedEnt;
	/** 最后一次积分时间 */
	private String lastPointDate;
	/** 系统开启时间 */
	private String openDate;
	/** 企业缴年费截止日期 */
	private String expireDate;
	/** 企业购买积分卡数量 */
	private Long countBuyCards;
	/** 是否设置交易密码 */
	private boolean isHaveTradePwd;
	
	/**
	 * 是否认证email
	 */
	private String isAuthEmail;
	/**
	 * 是否认证手机号
	 */
	private String isAuthMobile;
	/**
	 * 是否实名认证
	 */
	private String isRealnameAuth;

	/**
	 * 本地平台企业门户url
	 */
	private String entWebUrl;
	
	/**
	 * 基本状态
	 */
	private String baseStatus;
	/**
	 * 重要信息状态
	 */
	private String mainInfoStatus;
	
	/********************** 企业的状态信息   end***********************/
	
	/********************** 操作员信息   begin***********************/
	/**
	 * 操作员客户号
	 */
	private String custId;
	
	/**
	 * 操作员互生号
	 */
	private String resNo;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 是否管理员
	 */
	private String isAdmin;
	
	/**
	 * 是否绑定互生卡
	 */
	private String isBindResNo;
	
	/**
	 * 操作员手机号
	 */
	private String mobile;
	/**
	 * 操作员email
	 */
	private String email;
	
	/**
	 * 操作员名称
	 */
	private String operName;
	/**
	 * 职责
	 */
	private String operDuty;
	/**
	 * 是否是管理员
	 */
	
	/**
	 * 角色列表
	 */
	private List<AsRole> roles;
	
	/** 所属企业资源类型?3：托管企业，2 ：成员企业 */
	private String enterpriseResourceType;

	/** 操作员状态 0：启用，1：禁用 2:已删除 */
	private Integer accountStatus;

	/** 积分卡申请绑定时间 */
	private Timestamp applyBindDate;

	/** 备注信息 */
	private String remark;
	
	/********************** 操作员信息 end***********************/
	
	/********************** 网络信息   begin***********************/
	/**
	 * 头像
	 */
	private String headPic;
	/**
	 * 昵称
	 */
	private String nickName;
	
	/********************** 网络信息   end***********************/
	
	/**
	 * 是否绑定银行卡
	 */
	private String isBindBank;
	
	
	
	/**
	 * @return the 基本状态
	 */
	public String getBaseStatus() {
		return baseStatus;
	}

	/**
	 * @param 基本状态 the baseStatus to set
	 */
	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the 重要信息状态
	 */
	public String getMainInfoStatus() {
		return mainInfoStatus;
	}

	/**
	 * @param 重要信息状态 the mainInfoStatus to set
	 */
	public void setMainInfoStatus(String mainInfoStatus) {
		this.mainInfoStatus = mainInfoStatus;
	}

	/**
	 * @return the 本地平台企业门户url
	 */
	public String getEntWebUrl() {
		return entWebUrl;
	}

	/**
	 * @param 本地平台企业门户url the entWebUrl to set
	 */
	public void setEntWebUrl(String entWebUrl) {
		this.entWebUrl = entWebUrl;
	}

	/**
	 * @return the 是否绑定银行卡
	 */
	public String getIsBindBank() {
		return isBindBank;
	}

	/**
	 * @param 是否绑定银行卡 the isBindBank to set
	 */
	public void setIsBindBank(String isBindBank) {
		this.isBindBank = isBindBank;
	}

	/**
	 * @return the 操作员客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 操作员客户号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 角色列表
	 */
	public List<AsRole> getRoles() {
		return roles;
	}

	/**
	 * @param 角色列表 the roles to set
	 */
	public void setRoles(List<AsRole> roles) {
		this.roles = roles;
	}

	/**
	 * @return 省份代码
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param 省份代码
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return 国家代码
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param 国家代码
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return 城市代码
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param 城市代码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 消费者互生号
	 * 
	 * @return
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * 消费者互生号
	 * 
	 * @param resNo
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * 是否绑定互生号,2：绑定 0：未绑定, 1待确定
	 * 
	 * @return
	 */
	public String getIsBindResNo() {
		return isBindResNo;
	}

	/**
	 * 是否绑定互生号,2：绑定 0：未绑定, 1待确定
	 * 
	 * @param isBindResNo
	 */
	public void setIsBindResNo(String isBindResNo) {
		this.isBindResNo = isBindResNo;
	}

	/**
	 * 操作员账号
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 操作员账号
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 手机号
	 * 
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 邮箱
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 是否验证邮箱,1:已验证 0: 未验证
	 * 
	 * @return
	 */
	public String getIsAuthEmail() {
		return isAuthEmail;
	}

	/**
	 * 是否验证邮箱,1:已验证 0: 未验证
	 * 
	 * @param isAuthEmail
	 */
	public void setIsAuthEmail(String isAuthEmail) {
		this.isAuthEmail = isAuthEmail;
	}

	/**
	 * 是否验证手机,1:已验证 0: 未验证
	 * 
	 * @return
	 */
	public String getIsAuthMobile() {
		return isAuthMobile;
	}

	/**
	 * 是否验证手机,1:已验证 0: 未验证
	 * 
	 * @param isAuthMobile
	 */
	public void setIsAuthMobile(String isAuthMobile) {
		this.isAuthMobile = isAuthMobile;
	}

	/**
	 * 实名状态,1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 * 
	 * @return
	 */
	public String getIsRealnameAuth() {
		return isRealnameAuth;
	}

	/**
	 * 实名状态,1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 * 
	 * @param isRealnameAuth
	 */
	public void setIsRealnameAuth(String isRealnameAuth) {
		this.isRealnameAuth = isRealnameAuth;
	}

	/**
	 * 预留信息
	 * 
	 * @return
	 */
	public String getReserveInfo() {
		return reserveInfo;
	}

	/**
	 * 预留信息
	 * 
	 * @param reserveInfo
	 */
	public void setReserveInfo(String reserveInfo) {
		this.reserveInfo = reserveInfo;
	}

	/**
	 * 隶属托管企业资源号
	 * 
	 * @return
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * 隶属托管企业资源号
	 * 
	 * @param entResNo
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * 隶属托管企业客户号
	 * 
	 * @return
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * 隶属托管企业客户号
	 * 
	 * @param entCustId
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * 隶属企业资源类型,2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台
	 * 
	 * @return
	 */
	public String getEntResType() {
		return entResType;
	}

	/**
	 * 隶属企业资源类型,2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台
	 * 
	 * @param entResType
	 */
	public void setEntResType(String entResType) {
		this.entResType = entResType;
	}

	/**
	 * 企业LOGO图片地址
	 * 
	 * @return
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * 企业LOGO图片地址
	 * 
	 * @param logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 企业名称
	 * 
	 * @return
	 */
	public String getEntCustName() {
		return entCustName;
	}

	/**
	 * 企业名称
	 * 
	 * @param entCustName
	 */
	public void setEntCustName(String entCustName) {
		this.entCustName = entCustName;
	}

	/**
	 * 头像
	 * 
	 * @return
	 */
	public String getHeadPic() {
		return headPic;
	}

	/**
	 * 头像
	 * 
	 * @param headPic
	 */
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	/**
	 * 昵称
	 * 
	 * @return
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 昵称
	 * 
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 操作员姓名
	 * 
	 * @return
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * 操作员姓名
	 * 
	 * @param operName
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * 职务
	 * 
	 * @return
	 */
	public String getOperDuty() {
		return operDuty;
	}

	/**
	 * 职务
	 * 
	 * @param operDuty
	 */
	public void setOperDuty(String operDuty) {
		this.operDuty = operDuty;
	}

	/**
	 * 是否管理员,1:是 0：否
	 * 
	 * @return
	 */
	public String getIsAdmin() {
		return isAdmin;
	}

	/**
	 * 是否管理员,1:是 0：否
	 * 
	 * @param isAdmin
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
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

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
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

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
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

	public Integer getIsReg() {
		return isReg;
	}

	public void setIsReg(Integer isReg) {
		this.isReg = isReg;
	}

	public Integer getIsOweFee() {
		return isOweFee;
	}

	public void setIsOweFee(Integer isOweFee) {
		this.isOweFee = isOweFee;
	}

	public Integer getIsOldEnt() {
		return isOldEnt;
	}

	public void setIsOldEnt(Integer isOldEnt) {
		this.isOldEnt = isOldEnt;
	}

	public Integer getIsClosedEnt() {
		return isClosedEnt;
	}

	public void setIsClosedEnt(Integer isClosedEnt) {
		this.isClosedEnt = isClosedEnt;
	}

	public String getLastPointDate() {
		return lastPointDate;
	}

	public void setLastPointDate(String lastPointDate) {
		this.lastPointDate = lastPointDate;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public Long getCountBuyCards() {
		return countBuyCards;
	}

	public void setCountBuyCards(Long countBuyCards) {
		this.countBuyCards = countBuyCards;
	}

	public boolean isHaveTradePwd() {
		return isHaveTradePwd;
	}

	public void setHaveTradePwd(boolean isHaveTradePwd) {
		this.isHaveTradePwd = isHaveTradePwd;
	}

	public String getEnterpriseResourceType() {
		return enterpriseResourceType;
	}

	public void setEnterpriseResourceType(String enterpriseResourceType) {
		this.enterpriseResourceType = enterpriseResourceType;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Timestamp getApplyBindDate() {
		return applyBindDate;
	}

	public void setApplyBindDate(Timestamp applyBindDate) {
		this.applyBindDate = applyBindDate;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the 经营类型0：普通1：连锁企业
	 */
	public Integer getBusinessType() {
		return businessType;
	}

	/**
	 * @param 经营类型0：普通1：连锁企业 the businessType to set
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

}
