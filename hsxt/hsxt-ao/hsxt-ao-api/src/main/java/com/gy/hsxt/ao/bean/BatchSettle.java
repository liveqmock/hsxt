/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 终端批结算表
 * 
 * @Package: com.gy.hsxt.ao.bean
 * @ClassName: BatchSettle
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-8 上午10:01:09
 * @version V1.0
 */
public class BatchSettle implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 批总账编号 **/
	private String batchCheckId;

	/** 企业客户号 **/
	private String entCustId;

	/** 企业互生号 **/
	private String entResNo;

	/** 终端渠道 **/
	private Integer channel;

	/** 终端编号 **/
	private String termNo;

	/** 批次编号 **/
	private String batchNo;

	/** 兑换总金额 **/
	private String buyHsbSum;

	/** 兑换总笔数 **/
	private Integer buyHsbCnt;

	/** 代兑总金额 **/
	private String proxyHsbSum;

	/** 代兑总笔数 **/
	private Integer proxyHsbCnt;

	/** 批结算时间 **/
	private String checkTime;

	/** 批结算结果 1：通过 0：不通过 **/
	private Integer isSame;

	/** 批上传时间 批结算不通过才需要批上传 **/
	private String uploadData;

	/** 批上传标识 1：已上传 0：未上传 **/
	private Integer uploadFlag;

	/** 处理结果 2：待处理 3：已处理 **/
	private Integer processStatus;

	/** 处理说明 **/
	private String remark;

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
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 终端渠道
	 */
	public Integer getChannel() {
		return channel;
	}

	/**
	 * @param 终端渠道
	 *            the channel to set
	 */
	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	/**
	 * @return the 终端编号
	 */
	public String getTermNo() {
		return termNo;
	}

	/**
	 * @param 终端编号
	 *            the termNo to set
	 */
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	/**
	 * @return the 批次编号
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param 批次编号
	 *            the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the 兑换总金额
	 */
	public String getBuyHsbSum() {
		return buyHsbSum;
	}

	/**
	 * @param 兑换总金额
	 *            the buyHsbSum to set
	 */
	public void setBuyHsbSum(String buyHsbSum) {
		this.buyHsbSum = buyHsbSum;
	}

	/**
	 * @return the 兑换总笔数
	 */
	public Integer getBuyHsbCnt() {
		return buyHsbCnt;
	}

	/**
	 * @param 兑换总笔数
	 *            the buyHsbCnt to set
	 */
	public void setBuyHsbCnt(Integer buyHsbCnt) {
		this.buyHsbCnt = buyHsbCnt;
	}

	/**
	 * @return the 代兑总金额
	 */
	public String getProxyHsbSum() {
		return proxyHsbSum;
	}

	/**
	 * @param 代兑总金额
	 *            the proxyHsbSum to set
	 */
	public void setProxyHsbSum(String proxyHsbSum) {
		this.proxyHsbSum = proxyHsbSum;
	}

	/**
	 * @return the 代兑总笔数
	 */
	public Integer getProxyHsbCnt() {
		return proxyHsbCnt;
	}

	/**
	 * @param 代兑总笔数
	 *            the proxyHsbCnt to set
	 */
	public void setProxyHsbCnt(Integer proxyHsbCnt) {
		this.proxyHsbCnt = proxyHsbCnt;
	}

	/**
	 * @return the 批结算时间
	 */
	public String getCheckTime() {
		return checkTime;
	}

	/**
	 * @param 批结算时间
	 *            the checkTime to set
	 */
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * @return the 批结算结果1：通过0：不通过
	 */
	public Integer getIsSame() {
		return isSame;
	}

	/**
	 * @param 批结算结果1
	 *            ：通过0：不通过 the isSame to set
	 */
	public void setIsSame(Integer isSame) {
		this.isSame = isSame;
	}

	/**
	 * @return the 批上传时间批结算不通过才需要批上传
	 */
	public String getUploadData() {
		return uploadData;
	}

	/**
	 * @param 批上传时间批结算不通过才需要批上传
	 *            the uploadData to set
	 */
	public void setUploadData(String uploadData) {
		this.uploadData = uploadData;
	}

	/**
	 * @return the 批上传标识1：已上传0：未上传
	 */
	public Integer getUploadFlag() {
		return uploadFlag;
	}

	/**
	 * @param 批上传标识1
	 *            ：已上传0：未上传 the uploadFlag to set
	 */
	public void setUploadFlag(Integer uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

	/**
	 * @return the 处理结果2：待处理3：已处理
	 */
	public Integer getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param 处理结果2
	 *            ：待处理3：已处理 the processStatus to set
	 */
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return the 处理说明
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param 处理说明
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

}
