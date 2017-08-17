/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.gks.bean;

import java.io.Serializable;

/**
 * 登录参数
 * 
 * @Package: com.gy.hsxt.access.web.common.bean  
 * @ClassName: LoginParam 
 * @Description: TODO
 * 
 * @author: lixuan 
 * @date: 2015-11-13 下午6:05:25 
 * @version V1.0
 */
public class LoginParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7201564792087769208L;
	
	/**
	 * 企业用户登录时，输入的企业用户名
	 */
	String userName;
	/**
	 * 客户号
	 */
	String custId;
	/**
	 * 手机号
	 */
	String mobile;
	/**
	 * 登录密码
	 */
	String loginPwd;
	/**
	 * 渠道类型
	 */
	String channelType;
	/**
	 * 版本号
	 */
	String versionNumber;
	/**
	 * 随机token
	 */
	String randomToken;
	/**
	 * 登录IP
	 */
	String loginIp;
	
	/**
	 * 资源号
	 */
	String resNo;

	/**
	 * 用户类型
	 */
	String userType;

	/**
	 * @return the 用户类型
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param 用户类型 the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the 企业用户登录时，输入的企业用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param 企业用户登录时，输入的企业用户名 the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the 客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

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
	 * @return the 渠道类型
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param 渠道类型 the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * @return the 版本号
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param 版本号 the versionNumber to set
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
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

	/**
	 * @return the 登录IP
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * @param 登录IP the loginIp to set
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * @return the 资源号
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * @param 资源号 the resNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	
	
	
}
