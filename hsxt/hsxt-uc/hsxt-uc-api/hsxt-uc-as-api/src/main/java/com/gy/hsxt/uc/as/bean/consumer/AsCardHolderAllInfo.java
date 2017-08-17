/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;

import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;

/**
 * 持卡人消费者所有信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.consumer
 * @ClassName: AsCardHolderAllInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-13 下午7:45:31
 * @version V1.0
 */
public class AsCardHolderAllInfo implements Serializable {
	private static final long serialVersionUID = -8427069844039192091L;
	/** 持卡人基本信息 */
	private AsCardHolder baseInfo;
	/** 持卡人互生卡信息 */
	private AsHsCard cardInfo;
	/** 持卡人实名认证信息 */
	private AsRealNameAuth authInfo;
	/** 持卡人网络信息 */
	private AsNetworkInfo networkInfo;

	/**
	 * @return the 持卡人基本信息
	 */
	public AsCardHolder getBaseInfo() {
		return baseInfo;
	}

	/**
	 * @param 持卡人基本信息
	 *            the baseInfo to set
	 */
	public void setBaseInfo(AsCardHolder baseInfo) {
		this.baseInfo = baseInfo;
	}

	/**
	 * @return the 持卡人互生卡信息
	 */
	public AsHsCard getCardInfo() {
		return cardInfo;
	}

	/**
	 * @param 持卡人互生卡信息
	 *            the cardInfo to set
	 */
	public void setCardInfo(AsHsCard cardInfo) {
		this.cardInfo = cardInfo;
	}

	/**
	 * @return the 持卡人实名认证信息
	 */
	public AsRealNameAuth getAuthInfo() {
		return authInfo;
	}

	/**
	 * @param 持卡人实名认证信息
	 *            the authInfo to set
	 */
	public void setAuthInfo(AsRealNameAuth authInfo) {
		this.authInfo = authInfo;
	}

	/**
	 * @return the 持卡人网络信息
	 */
	public AsNetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	/**
	 * @param 持卡人网络信息
	 *            the networkInfo to set
	 */
	public void setNetworkInfo(AsNetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}

}
