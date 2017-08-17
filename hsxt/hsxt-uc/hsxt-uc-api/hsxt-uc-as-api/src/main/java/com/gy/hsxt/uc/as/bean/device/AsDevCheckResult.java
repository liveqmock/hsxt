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
public class AsDevCheckResult implements Serializable {
	private static final long serialVersionUID = 7982775130738618744L;
	
	/** 消费者客户号 */
	private String custId;
	/** 企业状态信息 */
	private AsEntStatusInfo asEntStatusInfo;
	/** 解密后密码 */
	private byte[] pwdDecrypt;	
	/** pos机信息 */
	private AsPos asPos;
	/** 待解密企业交易密码 */
	private byte[] entTradePwd;
	/** 企业客户号 */
	private String entCustId;
	/** 签到pikmak */
	private byte[] pikmak;
	
	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号 the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

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
	 * 企业资源类型 1：首段资源  2：创业资源  3：全部资源 4：正常成员企业 5：免费成员企业
	 */
	private Integer startResType;

	/**
	 * @return the 消费者客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 消费者客户号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 企业状态信息
	 */
	public AsEntStatusInfo getAsEntStatusInfo() {
		return asEntStatusInfo;
	}

	/**
	 * @param 企业状态信息 the asEntStatusInfo to set
	 */
	public void setAsEntStatusInfo(AsEntStatusInfo asEntStatusInfo) {
		this.asEntStatusInfo = asEntStatusInfo;
	}

	/**
	 * @return the 解密后密码
	 */
	public byte[] getPwdDecrypt() {
		return pwdDecrypt;
	}

	/**
	 * @param 解密后密码 the pwdDecrypt to set
	 */
	public void setPwdDecrypt(byte[] pwdDecrypt) {
		this.pwdDecrypt = pwdDecrypt;
	}

	/**
	 * @return the pos机信息
	 */
	public AsPos getAsPos() {
		return asPos;
	}

	/**
	 * @param pos机信息 the asPos to set
	 */
	public void setAsPos(AsPos asPos) {
		this.asPos = asPos;
	}

	/**
	 * @return the 企业资源类型1：首段资源2：创业资源3：全部资源4：正常成员企业5：免费成员企业
	 */
	public Integer getStartResType() {
		return startResType;
	}

	/**
	 * @param 企业资源类型1：首段资源2：创业资源3：全部资源4：正常成员企业5：免费成员企业 the startResType to set
	 */
	public void setStartResType(Integer startResType) {
		this.startResType = startResType;
	}

	/**
	 * @return the 签到pikmak
	 */
	public byte[] getPikmak() {
		return pikmak;
	}

	/**
	 * @param 签到pikmak the 签到pikmak to set
	 */
	public void setPikmak(byte[] pikmak) {
		this.pikmak = pikmak;
	}
	

}
