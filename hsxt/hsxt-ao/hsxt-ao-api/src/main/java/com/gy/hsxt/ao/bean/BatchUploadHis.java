/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.bean;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 终端批上传表
 * 
 * @Package: com.gy.hsxt.ao.bean
 * @ClassName: BatchUpload
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-8 上午9:37:13
 * @version V1.0
 */
public class BatchUploadHis implements java.io.Serializable {

	private static final long serialVersionUID = -1265096295164541584L;

	// field
	/** 明细账编号 **/
	private String detailCheckId;

	/** 批总账编号 **/
	private String batchCheckId;

	/** 交易类型码 71：货币兑换互生币 70：代兑互生币 **/
	private String bizType;

	/** 交易金额 **/
	private String transAmt;

	/** 持卡人互生号 **/
	private String perResNo;

	/** 交易时间 **/
	private String transTime;

	/** 原POS中心流水号 **/
	private String originNo;

	/** 原始终端流水号 **/
	private String termRuncode;

	/**
	 * @return the field
	 */
	public String getDetailCheckId() {
		return detailCheckId;
	}

	/**
	 * @param field
	 *            the detailCheckId to set
	 */
	public void setDetailCheckId(String detailCheckId) {
		this.detailCheckId = detailCheckId;
	}

	/**
	 * @return the 批总账编号
	 */
	public String getBatchCheckId() {
		return batchCheckId;
	}

	/**
	 * @param 批总账编号
	 *            the batchCheckId to set
	 */
	public void setBatchCheckId(String batchCheckId) {
		this.batchCheckId = batchCheckId;
	}

	/**
	 * @return the 交易类型码71：货币兑换互生币70：代兑互生币
	 */
	public String getBizType() {
		return bizType;
	}

	/**
	 * @param 交易类型码71
	 *            ：货币兑换互生币70：代兑互生币 the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * @return the 交易金额
	 */
	public String getTransAmt() {
		return transAmt;
	}

	/**
	 * @param 交易金额
	 *            the transAmt to set
	 */
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	/**
	 * @return the 持卡人互生号
	 */
	public String getPerResNo() {
		return perResNo;
	}

	/**
	 * @param 持卡人互生号
	 *            the perResNo to set
	 */
	public void setPerResNo(String perResNo) {
		this.perResNo = perResNo;
	}

	/**
	 * @return the 交易时间
	 */
	public String getTransTime() {
		return transTime;
	}

	/**
	 * @param 交易时间
	 *            the transTime to set
	 */
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	/**
	 * @return the 原POS中心流水号
	 */
	public String getOriginNo() {
		return originNo;
	}

	/**
	 * @param 原POS中心流水号
	 *            the originNo to set
	 */
	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	/**
	 * @return the 原始终端流水号
	 */
	public String getTermRuncode() {
		return termRuncode;
	}

	/**
	 * @param 原始终端流水号
	 *            the termRuncode to set
	 */
	public void setTermRuncode(String termRuncode) {
		this.termRuncode = termRuncode;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

}
