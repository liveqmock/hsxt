/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.bean.ent;

import java.io.Serializable;

/**
 * 企业注册信息（香港版）
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.ent
 * @ClassName: AsHkEntRegInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午5:33:02
 * @version V1.0
 */
public class BsHkEntRegInfo extends BsEntRegInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业注册编号 */
	private String entRegCode;
	/** 企业注册证书 */
	private String entRegImg;
	/** 工商登记编号 */
	private String busiRegCode;
	/** 工商登记证书 */
	private String busiRegImg;

	/**
	 * @return the 企业注册编号
	 */
	public String getEntRegCode() {
		return entRegCode;
	}

	/**
	 * @param 企业注册编号
	 *            the entRegCode to set
	 */
	public void setEntRegCode(String entRegCode) {
		this.entRegCode = entRegCode;
	}

	/**
	 * @return the 企业注册证书
	 */
	public String getEntRegImg() {
		return entRegImg;
	}

	/**
	 * @param 企业注册证书
	 *            the entRegImg to set
	 */
	public void setEntRegImg(String entRegImg) {
		this.entRegImg = entRegImg;
	}

	/**
	 * @return the 工商登记编号
	 */
	public String getBusiRegCode() {
		return busiRegCode;
	}

	/**
	 * @param 工商登记编号
	 *            the busiRegCode to set
	 */
	public void setBusiRegCode(String busiRegCode) {
		this.busiRegCode = busiRegCode;
	}

	/**
	 * @return the 工商登记证书
	 */
	public String getBusiRegImg() {
		return busiRegImg;
	}

	/**
	 * @param 工商登记证书
	 *            the busiRegImg to set
	 */
	public void setBusiRegImg(String busiRegImg) {
		this.busiRegImg = busiRegImg;
	}

}
