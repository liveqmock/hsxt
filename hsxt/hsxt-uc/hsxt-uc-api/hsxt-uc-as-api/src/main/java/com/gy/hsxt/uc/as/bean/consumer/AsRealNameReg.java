/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Package: com.gy.hsxt.uc.bs.bean
 * @ClassName: BsRealNameRegInfo
 * @Description: 实名注册信息
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午3:50:29
 * @version V1.0
 */
public class AsRealNameReg implements Serializable {

	private static final long serialVersionUID = 26830670574929878L;

	/**	客户号 */
	private String custId;
	/**	姓名 */
	private String realName;
	/**	证件类型  1：身份证 2：护照:3：营业执照*/
	private String certype;
	/**	证件号码 */
	private String cerNo;
	/**	国家代码 */
	private String countryCode;
	/**	国家名称 */
	private String countryName;
	/**	  实名状态 1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 */
	private String realNameStatus;
	
	/**	企业名称   */
	private String entName;
	
	/**	企业注册地址  （证件类型为营业执照时必填 */
	private String entRegAddr;
	/** 户籍地址 */
	private String residentAddr;
	/** 性别 */
	private int sex;
	/** 证件有效期 */
	private String expiredDate;
	/** 发证机关 */
	private String licenceIssuingAuth;
	/** 职业  */
	private String job;
	/** 附言  */
	private String comment;
	/**
	 * 证件正面照
	 */
	private String licenceFrontPic;
	/** 证件反面照 */
	private String licenceBackPic;
	/** 手持证件照 */
	private String licenceHandHoldPic;
	/** 出生地  */
	private String birthPlace;
	/** 签发地点  */
	private String issuingPlace;
	/** 企业类型 */
	private String entType;
	/** 企业创建日期 */
	private String entCreateDate;
	/** 创建人 */
	private String creator;
	
	/** 实名注册时间 */
	private String realnameRegDate;
	
	
	public String getRealnameRegDate() {
		return realnameRegDate;
	}
	public void setRealnameRegDate(String realnameRegDate) {
		this.realnameRegDate = realnameRegDate;
	}
	/**
	 * @return the 创建人
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param 创建人 the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return the 户籍地址
	 */
	public String getResidentAddr() {
		return residentAddr;
	}
	/**
	 * @param 户籍地址 the residentAddr to set
	 */
	public void setResidentAddr(String residentAddr) {
		this.residentAddr = residentAddr;
	}
	/**
	 * @return the 性别
	 */
	public int getSex() {
		return sex;
	}
	/**
	 * @param 性别 the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	/**
	 * @return the 证件有效期
	 */
	public String getExpiredDate() {
		return expiredDate;
	}
	/**
	 * @param 证件有效期 the expiredDate to set
	 */
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	/**
	 * @return the 发证机关
	 */
	public String getLicenceIssuingAuth() {
		return licenceIssuingAuth;
	}
	/**
	 * @param 发证机关 the licenceIssuingAuth to set
	 */
	public void setLicenceIssuingAuth(String licenceIssuingAuth) {
		this.licenceIssuingAuth = licenceIssuingAuth;
	}
	/**
	 * @return the 职业
	 */
	public String getJob() {
		return job;
	}
	/**
	 * @param 职业 the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * @return the 附言
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param 附言 the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the 证件正面照
	 */
	public String getLicenceFrontPic() {
		return licenceFrontPic;
	}
	/**
	 * @param 证件正面照 the licenceFrontPic to set
	 */
	public void setLicenceFrontPic(String licenceFrontPic) {
		this.licenceFrontPic = licenceFrontPic;
	}
	/**
	 * @return the 证件反面照
	 */
	public String getLicenceBackPic() {
		return licenceBackPic;
	}
	/**
	 * @param 证件反面照 the licenceBackPic to set
	 */
	public void setLicenceBackPic(String licenceBackPic) {
		this.licenceBackPic = licenceBackPic;
	}

	/**
	 * @return the 手持证件照
	 */
	public String getLicenceHandHoldPic() {
		return licenceHandHoldPic;
	}
	/**
	 * @param 手持证件照 the licenceHandHoldPic to set
	 */
	public void setLicenceHandHoldPic(String licenceHandHoldPic) {
		this.licenceHandHoldPic = licenceHandHoldPic;
	}
	/**
	 * @return the 出生地
	 */
	public String getBirthPlace() {
		return birthPlace;
	}
	/**
	 * @param 出生地 the bornPlace to set
	 */
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	/**
	 * @return the 签发地点
	 */
	public String getIssuingPlace() {
		return issuingPlace;
	}
	/**
	 * @param 签发地点 the issuingPlace to set
	 */
	public void setIssuingPlace(String issuingPlace) {
		this.issuingPlace = issuingPlace;
	}
	/**
	 * @return the 企业类型
	 */
	public String getEntType() {
		return entType;
	}
	/**
	 * @param 企业类型 the entType to set
	 */
	public void setEntType(String entType) {
		this.entType = entType;
	}
	/**
	 * @return the 企业创建日期
	 */
	public String getEntCreateDate() {
		return entCreateDate;
	}
	/**
	 * @param 企业创建日期 the entCreateDate to set
	 */
	public void setEntCreateDate(String entCreateDate) {
		this.entCreateDate = entCreateDate;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertype() {
		return certype;
	}
	public void setCertype(String certype) {
		this.certype = certype;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getRealNameStatus() {
		return realNameStatus;
	}
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}
	public String toString(){
        return JSONObject.toJSONString(this);
    }
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public String getEntRegAddr() {
		return entRegAddr;
	}
	public void setEntRegAddr(String entRegAddr) {
		this.entRegAddr = entRegAddr;
	}
	
	
}
