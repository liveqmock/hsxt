/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.password.bean;

import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/**
 * 密码
 * @author lixuan
 *
 */
public class PasswordBean {
	/** 用户名 */
	private String username;
	/** 密码 */
	private String pwd;
	/** 盐值 */
	private String saltValue;
	/** 随机token */
	private String randomToken;
	/**
	 * 密码版本号
	 */
	private String version = "3";
	/** 原始加密后的密码 */
	private String originalPwd;
	/** 是否企业用户类型 */
	private boolean isEnt;

	public PasswordBean() {
	}

	/**
	 * @return the 是否企业用户类型
	 */
	public boolean isEnt() {
		return isEnt;
	}

	/**
	 * @param 是否企业用户类型 the isEnt to set
	 */
	public void setEnt(boolean isEnt) {
		this.isEnt = isEnt;
	}

	/**
	 * @return the 原始加密后的密码
	 */
	public String getOriginalPwd() {
		return originalPwd;
	}

	/**
	 * @param 原始加密后的密码
	 *            the originalPwd to set
	 */
	public void setOriginalPwd(String originalPwd) {
		this.originalPwd = originalPwd;
	}

	/**
	 * @return the 密码版本号
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param 密码版本号
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param 用户名
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the 密码
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param 密码
	 *            the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the 盐值
	 */
	public String getSaltValue() {
		return saltValue;
	}

	/**
	 * @param 盐值
	 *            the saltValue to set
	 */
	public void setSaltValue(String saltValue) {
		this.saltValue = saltValue;
	}

	/**
	 * @return the 随机token
	 */
	public String getRandomToken() {
		return randomToken;
	}

	/**
	 * @param 随机token
	 *            the randomToken to set
	 */
	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}

}
