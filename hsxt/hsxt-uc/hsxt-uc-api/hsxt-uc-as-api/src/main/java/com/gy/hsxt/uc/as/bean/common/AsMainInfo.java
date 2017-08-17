/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

public class AsMainInfo implements Serializable {

	private static final long serialVersionUID = 3223479309783987739L;
	/**
	 * 持卡人客户号
	 */
	private String perCustId;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 证件号码
	 */
	private String cerNo;
	/**
	 * 证件类型
	 */
	private String cerType;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getCerType() {
		return cerType;
	}

	public void setCerType(String cerType) {
		this.cerType = cerType;
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
	
	
	
}
