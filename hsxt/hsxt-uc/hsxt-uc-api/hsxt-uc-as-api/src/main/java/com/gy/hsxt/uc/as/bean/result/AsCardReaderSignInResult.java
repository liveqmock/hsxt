package com.gy.hsxt.uc.as.bean.result;

import java.io.Serializable;

import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * 
 * 刷卡器签入返回对象
 * 
 * @Package: com.gy.hsxt.uc.as.bean.result
 * @ClassName: AsCardReaderSignInResult
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 上午11:59:34
 * @version V1.0
 */
public class AsCardReaderSignInResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3025257855256343705L;
	String token;
	String entCustId;
	byte[] mak;
	byte[] pik;
	byte[] pikmak;
	String entVer;
	String pointVer;
	String point;
	ChannelTypeEnum channelType;
	AsEntMainInfo entMainInfo;
	String deviceType;
	String entResNo;
	
	
	/**
	 * @return the entResNo
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param entResNo the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * 登录token
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 登录token
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 企业客户号
	 * 
	 * @return
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * 企业客户号
	 * 
	 * @param entCustId
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * 积分信息
	 * 
	 * @return
	 */
	public String getPoint() {
		return point;
	}

	/**
	 * 积分信息
	 * 
	 * @param point
	 */
	public void setPoint(String point) {
		this.point = point;
	}

	/**
	 * 设备类型
	 * 
	 * @return
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * 设备类型
	 * 
	 * @param deviceType
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the mak
	 */
	public byte[] getMak() {
		return mak;
	}

	/**
	 * @param mak the mak to set
	 */
	public void setMak(byte[] mak) {
		this.mak = mak;
	}

	/**
	 * @return the pik
	 */
	public byte[] getPik() {
		return pik;
	}

	/**
	 * @param pik the pik to set
	 */
	public void setPik(byte[] pik) {
		this.pik = pik;
	}

	/**
	 * @return the pikmak
	 */
	public byte[] getPikmak() {
		return pikmak;
	}

	/**
	 * @param pikmak the pikmak to set
	 */
	public void setPikmak(byte[] pikmak) {
		this.pikmak = pikmak;
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

	/**
	 * 企业的重要信息
	 * 
	 * @return
	 */
	public AsEntMainInfo getEntMainInfo() {
		return entMainInfo;
	}

	/**
	 * 企业的重要信息
	 * 
	 * @param entMainInfo
	 */
	public void setEntMainInfo(AsEntMainInfo entMainInfo) {
		this.entMainInfo = entMainInfo;
	}

}
