/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

public class AsResetTradePwdConsumer implements Serializable {

	private static final long serialVersionUID = -4666997667852909486L;
	/**
	 * 客户号
	 */
	private String custId;
	/**
	 * 随机数
	 */
	private String random;
	/**
	 * 新交易密码
	 */
	private String newTraderPwd;
	/**
	 * 秘钥（随机token）
	 */
	private String secretKey;
	/**
	 * 证件类型
	 */
	private String cerType;
	/**
	 * 证件号码
	 */
	private String cerNo;
	/**
	 * 用户类型
	 */
	private String userType;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	public String getNewTraderPwd() {
		return newTraderPwd;
	}
	public void setNewTraderPwd(String newTraderPwd) {
		this.newTraderPwd = newTraderPwd;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
