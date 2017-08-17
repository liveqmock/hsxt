/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.consumer.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameReg;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.consumer.bean
 * @ClassName: RealNameAuth
 * @Description: 实名认证信息
 * 
 * @author: tianxh
 * @date: 2015-11-9 下午9:04:40
 * @version V1.0
 */

public class RealNameAuth implements Serializable {
	private static final long serialVersionUID = 4791652807412429770L;

	/** 职业 */
	private String job;

	/** 出生地 */
	private String birthPlace;

	/** 签发地点 */
	private String issuePlace;

	/** 企业名称 */
	private String entName;

	/** 企业注册地址 */
	private String entRegAddr;

	/** 企业类型 */
	private String entType;

	/** 企业成立日期 */
	private String entBuildDate;

	/** 认证附言 */
	private String authRemark;

	/** 客户号 */
	private String perCustId;

	/** 国家代码 */
	private String countryName;

	/** 证件类型 证件类型1：身份证 2：护照 3：营业执照 */
	private Integer idType;

	/** 证件号码 */
	private String idNo;

	/** 性别1：男 0：女 */
	private Integer sex;

	/** 姓名 */
	private String perName;

	/** 证件失效日期 */
	private String idValidDate;

	/** 发证机关 */
	private String idIssueOrg;

	/** 户籍地址 */
	private String residentAddr;

	/** 证件正面图ID */
	private String certificateFront;

	/** 证件反面图ID */
	private String certificateBack;

	/** 手持证件图ID */
	private String certificateHandhold;

	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;

	/** 创建时间创建时间，取记录创建时的系统时间 */
	private Timestamp createDate;

	/** 实名认证的时间 */
	private Timestamp realNameAuthDate;

	/** 实名注册的时间 */
	private Timestamp realNameRegDate;

	/** 创建人由谁创建，值为用户的伪键ID */
	private String createdby;

	/** 更新时间更新时间，取记录更新时的系统时间 */
	private Timestamp updateDate;

	/** 更新人由谁更新，值为用户的伪键ID */
	private String updatedby;

	/**
	 * @return the 职业
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param 职业
	 *            the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * @return the 出生地
	 */
	public String getBirthPlace() {
		return birthPlace;
	}

	/**
	 * @param 出生地
	 *            the birthPlace to set
	 */
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	/**
	 * @return the 签发地点
	 */
	public String getIssuePlace() {
		return issuePlace;
	}

	/**
	 * @param 签发地点
	 *            the issuePlace to set
	 */
	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}

	/**
	 * @return the 企业名称
	 */
	public String getEntName() {
		return entName;
	}

	/**
	 * @param 企业名称
	 *            the entName to set
	 */
	public void setEntName(String entName) {
		this.entName = entName;
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
	 * @return the 企业类型
	 */
	public String getEntType() {
		return entType;
	}

	/**
	 * @param 客户号
	 *            the entType to set
	 */
	public void setEntType(String entType) {
		this.entType = entType;
	}

	/**
	 * @return the 客户号
	 */
	public String getEntBuildDate() {
		return entBuildDate;
	}

	/**
	 * @param 客户号
	 *            the entBuildDate to set
	 */
	public void setEntBuildDate(String entBuildDate) {
		this.entBuildDate = entBuildDate;
	}

	/**
	 * @return the 认证附言
	 */
	public String getAuthRemark() {
		return authRemark;
	}

	/**
	 * @param 客户号
	 *            the authRemark to set
	 */
	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	/**
	 * @return the 客户号
	 */
	public String getPerCustId() {
		return perCustId;
	}

	/**
	 * @param 客户号
	 *            the perCustId to set
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId == null ? null : perCustId.trim();
	}

	/**
	 * @return the 国籍
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param 国籍
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName == null ? null : countryName.trim();
	}

	/**
	 * @return the 证件类型1：身份证 2：护照:
	 */
	public Integer getIdType() {
		return idType;
	}

	/**
	 * @param 证件类型1
	 *            ：身份证 2：护照: the idType to set
	 */
	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	/**
	 * @return the 证件号码
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param 证件号码
	 *            the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo == null ? null : idNo.trim();
	}

	/**
	 * @return the 性别1：男 2：女
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param 性别1
	 *            ：男 2：女 the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return the 姓名
	 */
	public String getPerName() {
		return perName;
	}

	/**
	 * @param 姓名
	 *            the perName to set
	 */
	public void setPerName(String perName) {
		this.perName = perName == null ? null : perName.trim();
	}

	/**
	 * @return the 证件失效日期
	 */
	public String getIdValidDate() {
		return idValidDate;
	}

	/**
	 * @param 证件失效日期
	 *            the idValidDate to set
	 */
	public void setIdValidDate(String idValidDate) {
		this.idValidDate = idValidDate == null ? null : idValidDate.trim();
	}

	/**
	 * @return the 发证机关
	 */
	public String getIdIssueOrg() {
		return idIssueOrg;
	}

	/**
	 * @param 发证机关
	 *            the idIssueOrg to set
	 */
	public void setIdIssueOrg(String idIssueOrg) {
		this.idIssueOrg = idIssueOrg == null ? null : idIssueOrg.trim();
	}

	/**
	 * @return the 户籍地址
	 */
	public String getResidentAddr() {
		return residentAddr;
	}

	/**
	 * @param 户籍地址
	 *            the residentAddr to set
	 */
	public void setResidentAddr(String residentAddr) {
		this.residentAddr = residentAddr == null ? null : residentAddr.trim();
	}

	/**
	 * @return the 证件正面图ID
	 */
	public String getCertificateFront() {
		return certificateFront;
	}

	/**
	 * @param 证件正面图ID
	 *            the certificateFront to set
	 */
	public void setCertificateFront(String certificateFront) {
		this.certificateFront = certificateFront == null ? null
				: certificateFront.trim();
	}

	/**
	 * @return the 证件反面图ID
	 */
	public String getCertificateBack() {
		return certificateBack;
	}

	/**
	 * @param 证件反面图ID
	 *            the certificateBack to set
	 */
	public void setCertificateBack(String certificateBack) {
		this.certificateBack = certificateBack == null ? null : certificateBack
				.trim();
	}

	/**
	 * @return the 手持证件图ID
	 */
	public String getCertificateHandhold() {
		return certificateHandhold;
	}

	/**
	 * @param 手持证件图ID
	 *            the certificateHandhold to set
	 */
	public void setCertificateHandhold(String certificateHandhold) {
		this.certificateHandhold = certificateHandhold == null ? null
				: certificateHandhold.trim();
	}

	/**
	 * @return the 标记此条记录的状态Y:可用 N:不可用
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * @param 标记此条记录的状态Y
	 *            :可用 N:不可用 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive == null ? null : isactive.trim();
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
		this.createdby = createdby == null ? null : createdby.trim();
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
		this.updatedby = updatedby == null ? null : updatedby.trim();
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
	 * 填充实名注册信息
	 * 
	 * @param bsRealNameRegInfo
	 *            实名注册信息
	 */
	public void fillBsRealNameRegInfo(BsRealNameReg bsRealNameReg) {
		bsRealNameReg.setCerNo(StringUtils.nullToEmpty(this.idNo));
		if (null != this.idType) {
			bsRealNameReg.setCertype(String.valueOf(this.idType));
		}
		bsRealNameReg.setCountryCode(StringUtils.nullToEmpty(this.countryName));
		bsRealNameReg.setCustId(StringUtils.nullToEmpty(this.countryName));
		bsRealNameReg.setRealName(StringUtils.nullToEmpty(this.perName));
	}

	/**
	 * 填充实名注册信息
	 * 
	 * @param asRealNameRegInfo
	 *            实名注册信息
	 */
	public void fillBsRealNameRegInfo(AsRealNameReg asRealNameReg) {
		asRealNameReg.setCerNo(StringUtils.nullToEmpty(this.idNo));
		if (null != this.idType) {
			asRealNameReg.setCertype(String.valueOf(this.idType));
		}
		asRealNameReg.setCountryCode(StringUtils.nullToEmpty(this.countryName));
		asRealNameReg.setCountryName(StringUtils.nullToEmpty(this.countryName));
		asRealNameReg.setCustId(StringUtils.nullToEmpty(this.perCustId));
		asRealNameReg.setRealName(StringUtils.nullToEmpty(this.perName));
		asRealNameReg.setEntName(StringUtils.nullToEmpty(this.entName));
		asRealNameReg.setEntRegAddr(StringUtils.nullToEmpty(this.entRegAddr));
		if (this.sex == null) {
			asRealNameReg.setSex(-1);
		} else {
			asRealNameReg.setSex(this.sex);
		}
	}

	/**
	 * 填充 实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息()
	 */
	public void fillBsRealNameAuthInfo(BsRealNameAuth bsRealNameAuth) {
		bsRealNameAuth.setBirthAddress(StringUtils
				.nullToEmpty(this.residentAddr));
		bsRealNameAuth.setCerNo(StringUtils.nullToEmpty(this.idNo));
		bsRealNameAuth.setCerPica(StringUtils
				.nullToEmpty(this.certificateFront));
		bsRealNameAuth
				.setCerPicb(StringUtils.nullToEmpty(this.certificateBack));
		bsRealNameAuth.setCerPich(StringUtils
				.nullToEmpty(this.certificateHandhold));
		if (null != this.idType) {
			bsRealNameAuth.setCerType(this.idType);
		}
		bsRealNameAuth
				.setCountryCode(StringUtils.nullToEmpty(this.countryName));
		bsRealNameAuth.setCustId(StringUtils.nullToEmpty(this.perCustId));
		bsRealNameAuth.setIssuingOrg(StringUtils.nullToEmpty(this.idIssueOrg));
		if (null != this.sex) {
			bsRealNameAuth.setSex(this.sex);
		}
		bsRealNameAuth.setUserName(StringUtils.nullToEmpty(this.perName));
		bsRealNameAuth.setValidDate(StringUtils.nullToEmpty(this.idValidDate));

		// add by lvyan
		bsRealNameAuth.setAuthRemark(authRemark);
		// bsRealNameAuth.setBirthAddress(residentAddr);//
		bsRealNameAuth.setBirthPlace(birthPlace);
		// bsRealNameAuth.setCerNo(idNo);//
		// bsRealNameAuth.setCerPica(certificateFront);//
		// bsRealNameAuth.setCerPicb(certificateBack);//
		// bsRealNameAuth.setCerPich(certificateHandhold);//
		// bsRealNameAuth.setCerType(idType.toString());//
		// bsRealNameAuth.setCountryCode(countryName);//
		// bsRealNameAuth.setCustId(perCustId);//
		bsRealNameAuth.setEntBuildDate(entBuildDate);
		bsRealNameAuth.setEntName(entName);
		bsRealNameAuth.setEntRegAddr(entRegAddr);
		bsRealNameAuth.setEntType(entType);
		bsRealNameAuth.setIssuePlace(issuePlace);
		// bsRealNameAuth.setIssuingOrg(idIssueOrg);//
		bsRealNameAuth.setJob(job);
		// bsRealNameAuth.setRealNameStatus(realNameStatus);//实名验证状态在持卡人信息表
		// bsRealNameAuth.setSex(String.valueOf(sex));//
		// bsRealNameAuth.setUserName(perName);//
		// bsRealNameAuth.setValidDate(idValidDate);
	}

	/**
	 * 填充 实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息()
	 */
	public void fillAsRealNameAuthInfo(AsRealNameAuth asRealNameAuth) {
		asRealNameAuth.setBirthAddress(StringUtils
				.nullToEmpty(this.residentAddr));
		asRealNameAuth.setCerNo(StringUtils.nullToEmpty(this.idNo));
		asRealNameAuth.setCerPica(StringUtils
				.nullToEmpty(this.certificateFront));
		asRealNameAuth
				.setCerPicb(StringUtils.nullToEmpty(this.certificateBack));
		asRealNameAuth.setCerPich(StringUtils
				.nullToEmpty(this.certificateHandhold));
		if (null != this.idType) {
			asRealNameAuth.setCerType(String.valueOf(this.idType));
		}
		asRealNameAuth
				.setCountryCode(StringUtils.nullToEmpty(this.countryName));
		asRealNameAuth.setCustId(StringUtils.nullToEmpty(this.perCustId));
		asRealNameAuth.setIssuingOrg(StringUtils.nullToEmpty(this.idIssueOrg));
		if (null != this.sex) {
			asRealNameAuth.setSex(String.valueOf(this.sex));
		}
		asRealNameAuth.setUserName(StringUtils.nullToEmpty(this.perName));
		asRealNameAuth.setValidDate(StringUtils.nullToEmpty(this.idValidDate));

		// add by lvyan
		asRealNameAuth.setAuthRemark(authRemark);
		// asRealNameAuth.setBirthAddress(residentAddr);//
		asRealNameAuth.setBirthPlace(birthPlace);
		// asRealNameAuth.setCerNo(idNo);//
		// asRealNameAuth.setCerPica(certificateFront);//
		// asRealNameAuth.setCerPicb(certificateBack);//
		// asRealNameAuth.setCerPich(certificateHandhold);//
		// asRealNameAuth.setCerType(idType.toString());//
		// asRealNameAuth.setCountryCode(countryName);//
		// asRealNameAuth.setCustId(perCustId);//
		asRealNameAuth.setEntBuildDate(entBuildDate);
		asRealNameAuth.setEntName(entName);
		asRealNameAuth.setEntRegAddr(entRegAddr);
		asRealNameAuth.setEntType(entType);
		asRealNameAuth.setIssuePlace(issuePlace);
		// asRealNameAuth.setIssuingOrg(idIssueOrg);//
		asRealNameAuth.setJob(job);
		// asRealNameAuth.setRealNameStatus(realNameStatus);//实名验证状态在持卡人信息表
		// asRealNameAuth.setSex(String.valueOf(sex));//
		// asRealNameAuth.setUserName(perName);//
		// asRealNameAuth.setValidDate(idValidDate);

	}

	/**
	 * 入库实名认证信息
	 * 
	 * @param bsRealNameAuthInfo
	 *            实名认证信息
	 */
	public void setBsRealNameAuthInfo(BsRealNameAuth bsRealNameAuthInfo) {
		String birthAddress = bsRealNameAuthInfo.getBirthAddress();
		if (!StringUtils.isBlank(birthAddress)) {
			this.setResidentAddr(birthAddress.trim());
		}
		String cerNo = bsRealNameAuthInfo.getCerNo();
		if (!StringUtils.isBlank(cerNo)) {
			this.setIdNo(cerNo.trim());
		}
		String cerPica = bsRealNameAuthInfo.getCerPica();
		if (!StringUtils.isBlank(cerPica)) {
			this.setCertificateFront(cerPica.trim());
		}
		String cerPicb = bsRealNameAuthInfo.getCerPicb();
		if (!StringUtils.isBlank(cerPicb)) {
			this.setCertificateBack(cerPicb.trim());
		}
		String cerPich = bsRealNameAuthInfo.getCerPich();
		if (!StringUtils.isBlank(cerPich)) {
			this.setCertificateHandhold(cerPich.trim());
		}
		Integer cerType = bsRealNameAuthInfo.getCerType();
		if (null != cerType) {
			this.setIdType(cerType);
		}
		String countryCode = bsRealNameAuthInfo.getCountryCode();
		if (!StringUtils.isBlank(countryCode)) {
			this.setCountryName(countryCode.trim());
		}
		String custId = bsRealNameAuthInfo.getCustId();
		if (!StringUtils.isBlank(custId)) {
			this.setPerCustId(custId.trim());
		}
		String issuingOrg = bsRealNameAuthInfo.getIssuingOrg();
		if (!StringUtils.isBlank(issuingOrg)) {
			this.setIdIssueOrg(issuingOrg.trim());
		}
		Integer sex = bsRealNameAuthInfo.getSex();
		if (null != sex) {
			this.setSex(sex);
		}
		String userName = bsRealNameAuthInfo.getUserName();
		if (!StringUtils.isBlank(userName)) {
			this.setPerName(userName.trim());
		}
		String validDate = bsRealNameAuthInfo.getValidDate();
		if (!StringUtils.isBlank(validDate)) {
			this.setIdValidDate(validDate.trim());
		}
		String job = bsRealNameAuthInfo.getJob();
		if (!StringUtils.isBlank(job)) {
			this.setJob(job.trim());
		}
		String birthPlace = bsRealNameAuthInfo.getBirthPlace();
		if (!StringUtils.isBlank(birthPlace)) {
			this.setBirthPlace(birthPlace.trim());
		}
		String issuePlace = bsRealNameAuthInfo.getIssuePlace();
		if (!StringUtils.isBlank(issuePlace)) {
			this.setIssuePlace(issuePlace.trim());
		}
		String entName = bsRealNameAuthInfo.getEntName();
		if (!StringUtils.isBlank(entName)) {
			this.setEntName(entName.trim());
		}
		String entRegAddr = bsRealNameAuthInfo.getEntRegAddr();
		if (!StringUtils.isBlank(entRegAddr)) {
			this.setEntRegAddr(entRegAddr.trim());
		}
		String entType = bsRealNameAuthInfo.getEntType();
		if (!StringUtils.isBlank(entType)) {
			this.setEntType(entType.trim());
		}
		String entBuildDate = bsRealNameAuthInfo.getEntBuildDate();
		if (!StringUtils.isBlank(entBuildDate)) {
			this.setEntBuildDate(entBuildDate.trim());
		}
		String authRemark = bsRealNameAuthInfo.getAuthRemark();
		if (!StringUtils.isBlank(authRemark)) {
			this.setAuthRemark(authRemark.trim());
		}
	}

	/**
	 * 入库实名认证信息
	 * 
	 * @param asRealNameAuth
	 *            实名认证信息
	 */
	public void setAsRealNameAuthInfo(AsRealNameAuth asRealNameAuth) {
		String birthAddress = asRealNameAuth.getBirthAddress();
		if (!StringUtils.isBlank(birthAddress)) {
			this.setResidentAddr(birthAddress.trim());
		}
		String cerNo = asRealNameAuth.getCerNo();
		if (!StringUtils.isBlank(cerNo)) {
			this.setIdNo(cerNo.trim());
		}
		String cerPica = asRealNameAuth.getCerPica();
		if (!StringUtils.isBlank(cerPica)) {
			this.setCertificateFront(cerPica.trim());
		}
		String cerPicb = asRealNameAuth.getCerPicb();
		if (!StringUtils.isBlank(cerPicb)) {
			this.setCertificateBack(cerPicb.trim());
		}
		String cerPich = asRealNameAuth.getCerPich();
		if (!StringUtils.isBlank(cerPich)) {
			this.setCertificateHandhold(cerPich.trim());
		}
		String cerType = asRealNameAuth.getCerType();
		if (!StringUtils.isBlank(cerType)) {
			this.setIdType(Integer.valueOf(cerType.trim()));
		}
		String countryCode = asRealNameAuth.getCountryCode();
		if (!StringUtils.isBlank(countryCode)) {
			this.setCountryName(countryCode.trim());
		}
		String custId = asRealNameAuth.getCustId();
		if (!StringUtils.isBlank(custId)) {
			this.setPerCustId(custId.trim());
		}
		String issuingOrg = asRealNameAuth.getIssuingOrg();
		if (!StringUtils.isBlank(issuingOrg)) {
			this.setIdIssueOrg(issuingOrg.trim());
		}
		String sex = asRealNameAuth.getSex();
		if (!StringUtils.isBlank(sex)) {
			this.setSex(Integer.valueOf(sex.trim()));
		}
		String userName = asRealNameAuth.getUserName();
		if (!StringUtils.isBlank(userName)) {
			this.setPerName(userName.trim());
		}
		String validDate = asRealNameAuth.getValidDate();
		if (!StringUtils.isBlank(validDate)) {
			this.setIdValidDate(validDate.trim());
		}
		String job = asRealNameAuth.getJob();
		if (!StringUtils.isBlank(job)) {
			this.setJob(job.trim());
		}
		String birthPlace = asRealNameAuth.getBirthPlace();
		if (!StringUtils.isBlank(birthPlace)) {
			this.setBirthPlace(birthPlace.trim());
		}
		String issuePlace = asRealNameAuth.getIssuePlace();
		if (!StringUtils.isBlank(issuePlace)) {
			this.setIssuePlace(issuePlace.trim());
		}
		String entName = asRealNameAuth.getEntName();
		if (!StringUtils.isBlank(entName)) {
			this.setEntName(entName.trim());
		}
		String entRegAddr = asRealNameAuth.getEntRegAddr();
		if (!StringUtils.isBlank(entRegAddr)) {
			this.setEntRegAddr(entRegAddr.trim());
		}
		String entType = asRealNameAuth.getEntType();
		if (!StringUtils.isBlank(entType)) {
			this.setEntType(entType.trim());
		}
		String entBuildDate = asRealNameAuth.getEntBuildDate();
		if (!StringUtils.isBlank(entBuildDate)) {
			this.setEntBuildDate(entBuildDate.trim());
		}
		String authRemark = asRealNameAuth.getAuthRemark();
		if (!StringUtils.isBlank(authRemark)) {
			this.setAuthRemark(authRemark.trim());
		}
	}

	/**
	 * 入库实名注册信息
	 * 
	 * @param asRealNameRegInfo
	 *            实名注册信息
	 */
	public void setAsRealNameRegInfoParams(AsRealNameReg regInfo) {
		this.setAuthRemark(regInfo.getComment());
		this.setBirthPlace(regInfo.getBirthPlace());
		this.setCertificateBack(regInfo.getLicenceBackPic());
		this.setCertificateFront(regInfo.getLicenceFrontPic());
		this.setCertificateHandhold(regInfo.getLicenceHandHoldPic());
		String country = regInfo.getCountryCode();
		if (StringUtils.isBlank(country)) {
			country = regInfo.getCountryName();
		}
		this.setCountryName(country);
		this.setCreatedby(regInfo.getCreator());
		this.setEntBuildDate(regInfo.getEntCreateDate());
		this.setEntName(regInfo.getEntName());
		this.setEntRegAddr(regInfo.getEntRegAddr());
		this.setEntType(regInfo.getEntType());
		this.setIdIssueOrg(regInfo.getLicenceIssuingAuth());
		this.setIdNo(regInfo.getCerNo());
		this.setIdType(Integer.parseInt(regInfo.getCertype()));
		this.setIdValidDate(regInfo.getExpiredDate());
		this.setIssuePlace(regInfo.getIssuingPlace());
		this.setJob(regInfo.getJob());
		this.setPerName(regInfo.getRealName());
		this.setResidentAddr(regInfo.getResidentAddr());
		// this.setSex(regInfo.getSex());
		this.setPerCustId(regInfo.getCustId());
		this.setRealNameRegDate(StringUtils.getNowTimestamp());
	}

	public void setParams(RealNameAuth realNameAuth) {
		if (!StringUtils.isBlank(realNameAuth.getPerCustId())) {
			this.perCustId = realNameAuth.getPerCustId().trim();
		}
		if (!StringUtils.isBlank(realNameAuth.getCountryName())) {
			this.countryName = realNameAuth.getCountryName().trim();
		}
		if (null != realNameAuth.getIdType()) {
			this.idType = realNameAuth.getIdType();
		}
		if (!StringUtils.isBlank(realNameAuth.getIdNo())) {
			this.idNo = realNameAuth.getIdNo().trim();
		}
		if (null != realNameAuth.getSex()) {
			this.sex = realNameAuth.getSex();
		}
		if (!StringUtils.isBlank(realNameAuth.getPerName())) {
			this.perName = realNameAuth.getPerName().trim();
		}
		if (!StringUtils.isBlank(realNameAuth.getIdValidDate())) {
			this.idValidDate = realNameAuth.getIdValidDate().trim();
		}

		if (!StringUtils.isBlank(realNameAuth.getIdIssueOrg())) {
			this.idIssueOrg = realNameAuth.getIdIssueOrg().trim();
		}
		if (!StringUtils.isBlank(realNameAuth.getResidentAddr())) {
			this.residentAddr = realNameAuth.getResidentAddr().trim();
		}
		if (!StringUtils.isBlank(realNameAuth.getCertificateFront())) {
			this.certificateFront = realNameAuth.getCertificateFront().trim();
		}
		if (!StringUtils.isBlank(realNameAuth.getCertificateBack())) {
			this.certificateBack = realNameAuth.getCertificateBack().trim();
		}
		if (!StringUtils.isBlank(realNameAuth.getCertificateHandhold())) {
			this.certificateHandhold = realNameAuth.getCertificateHandhold()
					.trim();
		}

	}

	public Timestamp getRealNameAuthDate() {
		return realNameAuthDate;
	}

	public void setRealNameAuthDate(Timestamp realNameAuthDate) {
		this.realNameAuthDate = realNameAuthDate;
	}

	public Timestamp getRealNameRegDate() {
		return realNameRegDate;
	}

	public void setRealNameRegDate(Timestamp realNameRegDate) {
		this.realNameRegDate = realNameRegDate;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}