package com.gy.hsxt.uc.as.bean.result;

import java.io.Serializable;

import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-as-api
 * @package com.gy.hsxt.uc.as.bean.common.AsPadSignInfoResult.java
 * @className AsPadSignInfoResult
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-19 上午11:34:14
 * @updateUser lixuan
 * @updateDate 2015-10-19 上午11:34:14
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class AsPadSignInResult implements Serializable {

	private static final long serialVersionUID = -684707841900896456L;

	String entResNo;
	String entVer;
	String pointVer;
	String pointRate;
	ChannelTypeEnum channelType;
	AsEntMainInfo entMainInfo;
	byte[] mak;
	byte[] pik;
	byte[] pikmak;
	
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
	 * 企业互生号
	 * 
	 * @return
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * 企业互生号
	 * 
	 * @param entResNo
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
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
	 * 积分比例信息
	 * 
	 * @return
	 */
	public String getPointRate() {
		return pointRate;
	}

	/**
	 * 积分比例信息
	 * 
	 * @param pointRate
	 */
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
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
