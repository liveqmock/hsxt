/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.bean.ent;

import java.io.Serializable;

/**
 * 企业所有信息
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.ent
 * @ClassName: BsEntAllInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午12:20:56
 * @version V1.0
 */
public class BsEntAllInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业基本信息 */
	private BsEntBaseInfo baseInfo;
	/** 企业重要信息 */
	private BsEntMainInfo mainInfo;
	/** 企业状态信息 */
	private BsEntStatusInfo statusInfo;

	/**
	 * @return the 企业基本信息
	 */
	public BsEntBaseInfo getBaseInfo() {
		return baseInfo;
	}

	/**
	 * @param 企业基本信息
	 *            the baseInfo to set
	 */
	public void setBaseInfo(BsEntBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	/**
	 * @return the 企业重要信息
	 */
	public BsEntMainInfo getMainInfo() {
		return mainInfo;
	}

	/**
	 * @param 企业重要信息
	 *            the mainInfo to set
	 */
	public void setMainInfo(BsEntMainInfo mainInfo) {
		this.mainInfo = mainInfo;
	}

	/**
	 * @return the 企业状态信息
	 */
	public BsEntStatusInfo getStatusInfo() {
		return statusInfo;
	}

	/**
	 * @param 企业状态信息
	 *            the statusInfo to set
	 */
	public void setStatusInfo(BsEntStatusInfo statusInfo) {
		this.statusInfo = statusInfo;
	}

}
