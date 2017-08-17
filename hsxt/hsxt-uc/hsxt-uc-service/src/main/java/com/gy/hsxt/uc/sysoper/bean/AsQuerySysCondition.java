package com.gy.hsxt.uc.sysoper.bean;

import java.io.Serializable;

public class AsQuerySysCondition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3651058739864945551L;
	/** 用户账号 */
	private String userName;
	/** 用户姓名 */
	private String realName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
}