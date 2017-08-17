package com.gy.hsxt.uc.as.bean.result;

import java.io.Serializable;
		
/**   
 * Simple to Introduction
 * @category          Simple to Introduction
 * @projectName   hsxt-uc-as-api
 * @package           com.gy.hsxt.uc.as.bean.common.LoginResult.java
 * @className       LoginResult
 * @description      一句话描述该类的功能 
 * @author              lixuan
 * @createDate       2015-10-19 上午10:08:24  
 * @updateUser      lixuan
 * @updateDate      2015-10-19 上午10:08:24
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
public class LoginResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1173054104697765570L;
	private String token;
	private String lastLoginDate;
	private String lastLoginIp;
	private String isLocal;
	private String netWorkVer;
	/**
	 * 已登录的token
	 * @return
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * 已登录的token
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * 最后一次登录的日期：yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	
	/**
	 * 最后一次登录的日期：yyyy-MM-dd hh:mm:ss
	 * @param lastLoginDate
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	/**
	 * 最后一次登录的IP
	 * @return
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	
	/**
	 * 最后一次登录的IP
	 * @param lastLoginIp
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	/**
	 * 是否本地登录，1：本地登录，0：异地登录
	 * @return
	 */
	public String getIsLocal() {
		return isLocal;
	}
	
	/**
	 * 是否本地登录，1：本地登录，0：异地登录
	 * @param isLocal
	 */
	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}
	
	/**
	 * 网络信息版本号
	 * @return
	 */
	public String getNetWorkVer() {
		return netWorkVer;
	}
	
	/**
	 * 网络信息版本号
	 * @param netWorkVer
	 */
	public void setNetWorkVer(String netWorkVer) {
		this.netWorkVer = netWorkVer;
	}

}

	