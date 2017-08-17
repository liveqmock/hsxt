/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

public class AsResetTradePwdAuthCode implements Serializable {

	private static final long serialVersionUID = 1155369449717709098L;
	/**
	 * 客户号
	 */
	private String custId;
	/**
	 * 新交易密码
	 */
	private String newTraderPwd;
	/**
	 * 授权码
	 */
	private String authCode;
	/**
	 * 秘钥（随机token）
	 */
	private String secretKey;
	/**
	 * 操作员客户号
	 */
	private String operCustId;
	/**
	 * 手机号码
	 */
	private String mobile;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getNewTraderPwd() {
		return newTraderPwd;
	}
	public void setNewTraderPwd(String newTraderPwd) {
		this.newTraderPwd = newTraderPwd;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getOperCustId() {
		return operCustId;
	}
	public void setOperCustId(String operCustId) {
		this.operCustId = operCustId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
