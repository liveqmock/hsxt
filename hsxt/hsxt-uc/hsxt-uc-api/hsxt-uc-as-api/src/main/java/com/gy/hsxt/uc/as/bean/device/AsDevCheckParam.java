/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.device;

import java.io.Serializable;

import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 设备校验批处理参数
 * 
 * @Package: com.gy.hsxt.uc.as.bean.device
 * @ClassName: AsDevCheckVo
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-19 下午5:04:27
 * @version V1.0
 */
public class AsDevCheckParam implements Serializable {
	private static final long serialVersionUID = 7982775130738618743L;

	/** 消費者互生卡号 */
	private String resNo;
	/** 互生卡暗码 */
	private String code;

	/** 企业互生号 */
	private String entResNo;
	/** 设备序号（终端编号） 平台给企业定义的4位编码 */
	private String deviceNO;
	/** 设备类型 */
	private int deviceType;
	/** 操作员 */
	private String operator;

	/** 待验数校据 */
	private byte[] data;
	/** mac数据 */
	private byte[] mac;
	/** 待解密密码，（登陆密码or交易密码） */
	private byte[] posPwd;
	/** 待解密企业交易密码 */
	private byte[] entTradePwd;

	/** 进行参数校验 */
	private boolean isCheckMac = false;
	/** 校验消费者卡号及卡暗码 */
	private boolean isCheckCardCode = false;
	/** 校验消费者卡号及登陆密码 */
	private boolean isCheckCardPwd = false;
	/** 查询pos机信息 */
	private boolean isGetPosInfo = false;
	
	private boolean isPosSignIn = false;

	/**
	 * @return the 待解密企业交易密码
	 */
	public byte[] getEntTradePwd() {
		return entTradePwd;
	}

	/**
	 * @param 待解密企业交易密码 the entTradePwd to set
	 */
	public void setEntTradePwd(byte[] entTradePwd) {
		this.entTradePwd = entTradePwd;
	}

	/**
	 * @return the 消費者互生卡号
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * @param 消費者互生卡号
	 *            the resNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * @return the 互生卡暗码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param 互生卡暗码
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 设备序号（终端编号）平台给企业定义的4位编码
	 */
	public String getDeviceNO() {
		return deviceNO;
	}

	/**
	 * @param 设备序号
	 *            （终端编号）平台给企业定义的4位编码 the deviceNO to set
	 */
	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
	}

	/**
	 * @return the 设备类型
	 */
	public int getDeviceType() {
		return deviceType;
	}

	/**
	 * @param 设备类型
	 *            the deviceType to set
	 */
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the 操作员
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param 操作员
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the 待验数校据
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param 待验数校据
	 *            the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the mac数据
	 */
	public byte[] getMac() {
		return mac;
	}

	/**
	 * @param mac数据
	 *            the mac数据 to set
	 */
	public void setMac(byte[] mac) {
		this.mac = mac;
	}

	/**
	 * @return the 进行参数校验
	 */
	public boolean isCheckMac() {
		return isCheckMac;
	}

	/**
	 * @param 进行参数校验
	 *            the isCheckMac to set
	 */
	public void setCheckMac(boolean isCheckMac) {
		this.isCheckMac = isCheckMac;
	}

	/**
	 * @return the 校验消费者卡号及卡暗码
	 */
	public boolean isCheckCardCode() {
		return isCheckCardCode;
	}

	/**
	 * @param 校验消费者卡号及卡暗码
	 *            the isCheckCardCode to set
	 */
	public void setCheckCardCode(boolean isCheckCardCode) {
		this.isCheckCardCode = isCheckCardCode;
	}

	/**
	 * @return the 校验消费者卡号及登陆密码
	 */
	public boolean isCheckCardPwd() {
		return isCheckCardPwd;
	}

	/**
	 * @param 校验消费者卡号及登陆密码
	 *            the isCheckCardPwd to set
	 */
	public void setCheckCardPwd(boolean isCheckCardPwd) {
		this.isCheckCardPwd = isCheckCardPwd;
	}

	/**
	 * @return the 待解密密码，（登陆密码or交易密码）
	 */
	public byte[] getPosPwd() {
		return posPwd;
	}

	/**
	 * @param 待解密密码，（登陆密码or交易密码） the posPwd to set
	 */
	public void setPosPwd(byte[] posPwd) {
		this.posPwd = posPwd;
	}

	/**
	 * @return the 查询pos机信息
	 */
	public boolean isGetPosInfo() {
		return isGetPosInfo;
	}

	/**
	 * @param 查询pos机信息 the isGetPosInfo to set
	 */
	public void setGetPosInfo(boolean isGetPosInfo) {
		this.isGetPosInfo = isGetPosInfo;
	}

	/**
	 * @return the isSignIn
	 */
	public boolean isPosSignIn() {
		return isPosSignIn;
	}

	/**
	 * @param isSignIn the isSignIn to set
	 */
	public void setPosSignIn(boolean isPosSignIn) {
		this.isPosSignIn = isPosSignIn;
	}

}
