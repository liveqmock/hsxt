package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * 设备登录信息
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-as-api
 * @package com.gy.hsxt.uc.as.bean.common.AsSiginInfo.java
 * @className AsSiginInfo
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-19 上午11:13:21
 * @updateUser lixuan
 * @updateDate 2015-10-19 上午11:13:21
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class AsSignInInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6613957457422798789L;
	private String entResNO;
	private String userName;
	private String deviceNo;
	private String pik;
	private String pwdVer;
	private String entVer;
	private String pointVer;
	private ChannelTypeEnum channelType;

	/**
	 * 企业互生号
	 * 
	 * @return
	 */
	public String getEntResNO() {
		return entResNO;
	}

	/**
	 * 企业互生号
	 * 
	 * @param entResNO
	 */
	public void setEntResNO(String entResNO) {
		this.entResNO = entResNO;
	}

	/**
	 * 用户名（操作员账号）
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 用户名（操作员账号）
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 终端编号
	 * 
	 * @return
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * 终端编号
	 * 
	 * @param deviceNo
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * @return the pik
	 */
	public String getPik() {
		return pik;
	}

	/**
	 * @param pik the pik to set
	 */
	public void setPik(String pik) {
		this.pik = pik;
	}

	/**
	 * 密码版本号
	 * 
	 * @return
	 */
	public String getPwdVer() {
		return pwdVer;
	}

	/**
	 * 密码版本号
	 * 
	 * @param pwdVer
	 */
	public void setPwdVer(String pwdVer) {
		this.pwdVer = pwdVer;
	}

	/**
	 * 企业版本号
	 * 
	 * @return
	 */
	public String getEntVer() {
		return entVer;
	}

	/**
	 * 企业版本号
	 * 
	 * @param entVer
	 */
	public void setEntVer(String entVer) {
		this.entVer = entVer;
	}

	/**
	 * 积分版本号
	 * 
	 * @return
	 */
	public String getPointVer() {
		return pointVer;
	}

	/**
	 * 积分版本号
	 * 
	 * @param pointVer
	 */
	public void setPointVer(String pointVer) {
		this.pointVer = pointVer;
	}

	/**
	 * 渠道类型
	 * 
	 * @return
	 */
	public ChannelTypeEnum getChannelType() {
		return channelType;
	}

	/**
	 * 渠道类型
	 * 
	 * @param channelType
	 */
	public void setChannelType(ChannelTypeEnum channelType) {
		this.channelType = channelType;
	}
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
