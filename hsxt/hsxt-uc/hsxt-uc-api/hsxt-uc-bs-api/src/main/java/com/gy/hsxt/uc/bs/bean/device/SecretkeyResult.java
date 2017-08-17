/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.bean.device;

import java.io.Serializable;

/**
 * 创建Pos机秘钥返回的结果
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.device
 * @ClassName: SecretkeyResult
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-4 上午10:47:58
 * @version V1.0
 */
public class SecretkeyResult implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业名称 */
	private String entName;
	/** 企业类型 */
	private Integer entType;
	/** 结算公司代码 */
	private String settleCode;
	/** 企业客户号 */
	private String entCustId;
	/** 企业状态 */
	private Integer enyStatus;
	/** 企业版本号 */
	private String version;
	/** 设备积分比例 */
	private String[] pointRate;
	/** 设备秘钥 */
	private String pmk;

	/**
	 * @return the 企业名称
	 */
	public String getEntName() {
		return entName;
	}

	/**
	 * @param 企业名称
	 *            the entName to set
	 */
	public void setEntName(String entName) {
		this.entName = entName;
	}

	/**
	 * @return the 结算公司代码
	 */
	public String getSettleCode() {
		return settleCode;
	}

	/**
	 * @param 结算公司代码
	 *            the settleCode to set
	 */
	public void setSettleCode(String settleCode) {
		this.settleCode = settleCode;
	}

	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 企业版本号
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param 企业版本号
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the 设备积分比例
	 */
	public String[] getPointRate() {
		return pointRate;
	}

	/**
	 * @param 设备积分比例
	 *            the pointRate to set
	 */
	public void setPointRate(String[] pointRate) {
		this.pointRate = pointRate;
	}

	/**
	 * @return the 设备秘钥
	 */
	public String getPmk() {
		return pmk;
	}

	/**
	 * @param 设备秘钥
	 *            the pmk to set
	 */
	public void setPmk(String pmk) {
		this.pmk = pmk;
	}

	/**
	 * @return the 企业类型
	 */
	public Integer getEntType() {
		return entType;
	}

	/**
	 * @param 企业类型
	 *            the entType to set
	 */
	public void setEntType(Integer entType) {
		this.entType = entType;
	}

	/**
	 * @return the 企业状态
	 */
	public Integer getEnyStatus() {
		return enyStatus;
	}

	/**
	 * @param 企业状态
	 *            the enyStatus to set
	 */
	public void setEnyStatus(Integer enyStatus) {
		this.enyStatus = enyStatus;
	}

}
