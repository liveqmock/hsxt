/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

public class AsUpdateTradePwd implements Serializable{

	private static final long serialVersionUID = -2373310102661599936L;
	/**
	 * 客户号
	 */
	private String custId;
	/**
	 * 旧交易密码
	 */
	private String oldTradePwd;
	/**
	 * 新交易密码
	 */
	private String newTradePwd;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 秘钥(随机token)
	 */
	private String secretKey;
	/**
	 * 操作员客户号
	 */
	private String operCustId;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getOldTradePwd() {
		return oldTradePwd;
	}
	public void setOldTradePwd(String oldTradePwd) {
		this.oldTradePwd = oldTradePwd;
	}
	public String getNewTradePwd() {
		return newTradePwd;
	}
	public void setNewTradePwd(String newTradePwd) {
		this.newTradePwd = newTradePwd;
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
	public String getOperCustId() {
		return operCustId;
	}
	public void setOperCustId(String operCustId) {
		this.operCustId = operCustId;
	}
	
}
