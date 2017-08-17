/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.consumer.bean;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.common.bean.UserInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.consumer.bean
 * @ClassName: NoCardHolder
 * @Description: 非持卡人信息
 * 
 * @author: tianxh
 * @date: 2015-11-9 下午8:49:18
 * @version V1.0
 */
public class NoCardHolder extends UserInfo {
	private static final long serialVersionUID = -3612037939521426531L;
	/** 客户号 */
	private String perCustId;

	/** 手机号码 */
	private String mobile;

	/** 邮箱 */
	private String email;

	/** 指纹 */
	private String fingerprint;

	/** 是否已验证邮箱1:已验证 0: 未验证 */
	private Integer isAuthEmail;

	/** 预留信息 */
	private String ensureInfo;

	/** 最近登录时间 */
	private Timestamp lastLoginDate;

	/** 最近登录IP */
	private String lastLoginIp;

	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;

	private String pwdTrans;

	private String pwdTransSaltValue;

	private String pwdTransVer;

	private String entResNo;

	private Integer isRealnameAuth;

	private Integer isKeyinfoChanged;

	private Timestamp realnameRegDate;

	private Timestamp realnameAuthDate;
	
	private Integer isBindBank;

	/**
	 * @return the pwdTransSaltValue
	 */
	public String getPwdTransSaltValue() {
		return pwdTransSaltValue;
	}

	/**
	 * @param pwdTransSaltValue
	 *            the pwdTransSaltValue to set
	 */
	public void setPwdTransSaltValue(String pwdTransSaltValue) {
		this.pwdTransSaltValue = pwdTransSaltValue;
	}

	/**
	 * @return the pwdTransVer
	 */
	public String getPwdTransVer() {
		return pwdTransVer;
	}

	/**
	 * @param pwdTransVer
	 *            the pwdTransVer to set
	 */
	public void setPwdTransVer(String pwdTransVer) {
		this.pwdTransVer = pwdTransVer;
	}

	/**
	 * @return the pwdTrans
	 */
	public String getPwdTrans() {
		return pwdTrans;
	}

	/**
	 * @param pwdTrans
	 *            the pwdTrans to set
	 */
	public void setPwdTrans(String pwdTrans) {
		this.pwdTrans = pwdTrans;
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
	 * @return the 手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param 手机号码
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	/**
	 * @return the 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param 邮箱
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	/**
	 * @return the 指纹
	 */
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * @param 指纹
	 *            the fingerprint to set
	 */
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint == null ? null : fingerprint.trim();
	}

	/**
	 * @return the 是否已验证邮箱1:已验证 0: 未验证
	 */
	public Integer getIsAuthEmail() {
		return isAuthEmail;
	}

	/**
	 * @param 是否已验证邮箱1
	 *            :已验证 0: 未验证 the isAuthEmail to set
	 */
	public void setIsAuthEmail(Integer isAuthEmail) {
		this.isAuthEmail = isAuthEmail;
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
		this.ensureInfo = ensureInfo == null ? null : ensureInfo.trim();
	}

	/**
	 * @return the 最近登录IP
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param 最近登录IP
	 *            the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
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
	 * @return the 最近登录时间
	 */
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param 最近登录时间
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getEntResNo() {
		return entResNo;
	}

	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	public Integer getIsRealnameAuth() {
		return isRealnameAuth;
	}

	public void setIsRealnameAuth(Integer isRealnameAuth) {
		this.isRealnameAuth = isRealnameAuth;
	}

	public Integer getIsKeyinfoChanged() {
		return isKeyinfoChanged;
	}

	public void setIsKeyinfoChanged(Integer isKeyinfoChanged) {
		this.isKeyinfoChanged = isKeyinfoChanged;
	}

	public Timestamp getRealnameRegDate() {
		return realnameRegDate;
	}

	public void setRealnameRegDate(Timestamp realnameRegDate) {
		this.realnameRegDate = realnameRegDate;
	}

	public Timestamp getRealnameAuthDate() {
		return realnameAuthDate;
	}

	public void setRealnameAuthDate(Timestamp realnameAuthDate) {
		this.realnameAuthDate = realnameAuthDate;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public Integer getIsBindBank() {
		return isBindBank;
	}

	public void setIsBindBank(Integer isBindBank) {
		this.isBindBank = isBindBank;
	}
	
	
}