/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

public class AsNoCarderMainInfo implements Serializable {

	private static final long serialVersionUID = 3223479309783987739L;
	/**
	 * 非持卡人客户号
	 */
	private String perCustId;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 短信验证码
	 */
	private String validCode;
	/**
	 * 登录密码
	 */
	private String loginPwd;
	/**
	 * 秘钥
	 */
	private String secretKey;
	/**
	 * 登录密码版本号
	 */
	private String loginPwdVer;

	public String getPerCustId() {
		return perCustId;
	}

	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getLoginPwdVer() {
		return loginPwdVer;
	}

	public void setLoginPwdVer(String loginPwdVer) {
		this.loginPwdVer = loginPwdVer;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
	
	
}
