/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.ent;

import java.io.Serializable;

/**
 * 企业所有信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.ent
 * @ClassName: AsEntAllInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午12:20:56
 * @version V1.0
 */
public class AsEntAllInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业基本信息 */
	private AsEntBaseInfo baseInfo;
	/** 企业重要信息 */
	private AsEntMainInfo mainInfo;
	/** 企业状态信息 */
	private AsEntStatusInfo statusInfo;
	/** 企业的扩展信息*/
	private AsEntExtendInfo extendInfo;

	
	public AsEntExtendInfo getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(AsEntExtendInfo extendInfo) {
		this.extendInfo = extendInfo;
	}

	/**
	 * @return the 企业基本信息
	 */
	public AsEntBaseInfo getBaseInfo() {
		return baseInfo;
	}

	/**
	 * @param 企业基本信息
	 *            the baseInfo to set
	 */
	public void setBaseInfo(AsEntBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	/**
	 * @return the 企业重要信息
	 */
	public AsEntMainInfo getMainInfo() {
		return mainInfo;
	}

	/**
	 * @param 企业重要信息
	 *            the mainInfo to set
	 */
	public void setMainInfo(AsEntMainInfo mainInfo) {
		this.mainInfo = mainInfo;
	}

	/**
	 * @return the 企业状态信息
	 */
	public AsEntStatusInfo getStatusInfo() {
		return statusInfo;
	}

	/**
	 * @param 企业状态信息
	 *            the statusInfo to set
	 */
	public void setStatusInfo(AsEntStatusInfo statusInfo) {
		this.statusInfo = statusInfo;
	}

}
