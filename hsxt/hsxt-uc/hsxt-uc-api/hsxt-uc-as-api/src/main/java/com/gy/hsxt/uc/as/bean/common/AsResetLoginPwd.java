/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

public class AsResetLoginPwd implements Serializable {

	private static final long serialVersionUID = -8862536664804359136L;
	/**
	 * 客户号
	 */
	private String custId;
	/**
	 * 新登录密码
	 */
	private String newLoginPwd;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 秘钥（随机token）
	 */
	private String secretKey;
	/**
	 * 随机数
	 */
	private String random;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 邮箱信息
	 */
	private String email;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getNewLoginPwd() {
		return newLoginPwd;
	}
	public void setNewLoginPwd(String newLoginPwd) {
		this.newLoginPwd = newLoginPwd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
