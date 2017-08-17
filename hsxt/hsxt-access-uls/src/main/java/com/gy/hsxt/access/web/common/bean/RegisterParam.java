/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.common.bean;

import java.io.Serializable;

/**
 * 非持卡人注册传入参数
 * 
 * @Package: com.gy.hsxt.access.web.common.bean  
 * @ClassName: RegisterParam 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-10-18 上午11:25:40 
 * @version V1.0
 */
public class RegisterParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8708509559075711482L;
	/**
	 * 手机号
	 */
	String mobile;
	/**
	 * 登录密码
	 */
	String loginPwd;
	/**
	 * 短信验证码
	 */
	String smsCode;
	/**
	 * 昵称
	 */
	String nickname;
	/**
	 * email
	 */
	String email;
	/**
	 * 随机token
	 */
	String randomToken;
	
	String custId;
	
	Integer custType;
	/**
	 * @return the 手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param 手机号 the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the 登录密码
	 */
	public String getLoginPwd() {
		return loginPwd;
	}
	/**
	 * @param 登录密码 the loginPwd to set
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	/**
	 * @return the 短信验证码
	 */
	public String getSmsCode() {
		return smsCode;
	}
	/**
	 * @param 短信验证码 the smsCode to set
	 */
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	/**
	 * @return the 昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param 昵称 the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the 随机token
	 */
	public String getRandomToken() {
		return randomToken;
	}
	/**
	 * @param 随机token the randomToken to set
	 */
	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Integer getCustType() {
		return custType;
	}
	public void setCustType(Integer custType) {
		this.custType = custType;
	}
	
	

}
