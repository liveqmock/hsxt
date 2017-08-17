package com.gy.hsxt.uc.as.bean.result;

import java.io.Serializable;

import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * Pos签到返回结果
 * 
 * @category Simple to Introduction
 * @projectName hsxt-uc-as-api
 * @package com.gy.hsxt.uc.as.bean.common.AsPosSignInResult.java
 * @className AsPosSignInResult
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-10-19 上午11:28:50
 * @updateUser lixuan
 * @updateDate 2015-10-19 上午11:28:50
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class AsPosSignInResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2788948439510249927L;

	byte[] mak;
	byte[] pik;
	byte[] pikmak;
	String entResNo;
	String entVer;
	String pointVer;
	String[] pointRate;
	ChannelTypeEnum channelType;
	AsEntMainInfo entMainInfo;

	
	
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
	 * 加密秘钥
	 * 
	 * @return
	 */
	public byte[] getMak() {
		return mak;
	}

	/**
	 * 加密秘钥
	 * 
	 * @param mak
	 */
	public void setMak(byte[] mak) {
		this.mak = mak;
	}

	/**
	 * 加密秘钥
	 * 
	 * @return
	 */
	public byte[] getPik() {
		return pik;
	}

	/**
	 * 加密秘钥
	 * 
	 * @param pik
	 */
	public void setPik(byte[] pik) {
		this.pik = pik;
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
	public String[] getPointRate() {
		return pointRate;
	}

	/**
	 * 积分比例信息
	 * 
	 * @param pointRate
	 */
	public void setPointRate(String[] pointRate) {
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
