package com.gy.hsxt.uc.common.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3232552782928938734L;

	/** 登录密码 */
	private String pwdLogin;

	/** 登录密码版本 */
	private String pwdLoginVer;
	
	/** 登录密码盐值 */
	private String pwdLoginSaltValue;
	
	/** 创建时间创建时间，取记录创建时的系统时间 */
	private Timestamp createDate;

	/** 创建人由谁创建，值为用户的伪键ID */
	private String createdby;

	/** 更新时间更新时间，取记录更新时的系统时间 */
	private Timestamp updateDate;

	/** 更新人由谁更新，值为用户的伪键ID */
	private String updatedby;
	
	/**
	 * @return the 登录密码
	 */
	public String getPwdLogin() {
		return pwdLogin;
	}

	/**
	 * @param 登录密码
	 *            the pwdLogin to set
	 */
	public void setPwdLogin(String pwdLogin) {
		this.pwdLogin = pwdLogin == null ? null : pwdLogin.trim();
	}

	/**
	 * @return the 登录密码版本
	 */
	public String getPwdLoginVer() {
		return pwdLoginVer;
	}

	/**
	 * @param 登录密码版本
	 *            the pwdLoginVer to set
	 */
	public void setPwdLoginVer(String pwdLoginVer) {
		this.pwdLoginVer = pwdLoginVer == null ? null : pwdLoginVer.trim();
	}

	/**
	 * @return the 登录密码盐值
	 */
	public String getPwdLoginSaltValue() {
		return pwdLoginSaltValue;
	}

	/**
	 * @param 登录密码盐值
	 *            the pwdLoginSaltValue to set
	 */
	public void setPwdLoginSaltValue(String pwdLoginSaltValue) {
		this.pwdLoginSaltValue = pwdLoginSaltValue == null ? null : pwdLoginSaltValue.trim();
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
}
