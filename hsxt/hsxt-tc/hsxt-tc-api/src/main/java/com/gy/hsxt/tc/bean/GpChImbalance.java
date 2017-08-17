/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付系统GP 与银联CH 对账不平衡记录
 * 
 * @Package: com.gy.hsxt.tc.bean
 * @ClassName: GpChImbalance
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午8:20:10
 * @version V1.0
 */
public class GpChImbalance implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键，自增长 */
	private Long imbalanceId;

	/** 对账日期，格式YYYY-MM-DD */
	private String tcDate;

	/** 交易单号，对应银联订单号，支付系统银行订单号 */
	private String tranNum;

	/** 支付系统交易流水号 */
	private String gpSeqId;

	/**
	 * 支付系统交易类型 10 - 支付交易 20 - 退款交易
	 */
	private Integer gpTransType;

	/** 支付系统交易金额 */
	private BigDecimal gpTransAmount;

	/**
	 * 支付系统支付单状态 具体枚举值定义请参见附录《支付单状态枚举值定义》
	 */
	private Integer gpTransStatus;

	/** 支付系统交易时间 */
	private Date gpTransDate;

	/** 银联流水号 */
	private String chSeqId;

	/**
	 * 银联交易类型 10 - 支付交易 20 - 退款交易
	 */
	private Integer chTransType;

	/** 银联交易金额 */
	private BigDecimal chTransAmount;

	/** 银联交易状态 */
	private Integer chTransStatus;

	/** 银联交易时间 */
	private Date chTransDate;

	/** 银联商户日期 */
	private Date chMerchantDate;

	/**
	 * 对账结果 0：长款 1：短款 2：要素不一致
	 */
	private Integer imbalanceType;

	/**
	 * @return the 主键，自增长
	 */
	public Long getImbalanceId() {
		return imbalanceId;
	}

	/**
	 * @param 主键
	 *            ，自增长 the imbalanceId to set
	 */
	public void setImbalanceId(Long imbalanceId) {
		this.imbalanceId = imbalanceId;
	}

	/**
	 * @return the 对账日期，格式YYYY-MM-DD
	 */
	public String getTcDate() {
		return tcDate;
	}

	/**
	 * @param 对账日期
	 *            ，格式YYYY-MM-DD the tcDate to set
	 */
	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}

	/**
	 * @return the 交易单号，对应银联订单号，支付系统银行订单号
	 */
	public String getTranNum() {
		return tranNum;
	}

	/**
	 * @param 交易单号
	 *            ，对应银联订单号，支付系统银行订单号 the tranNum to set
	 */
	public void setTranNum(String tranNum) {
		this.tranNum = tranNum;
	}

	/**
	 * @return the 支付系统交易流水号
	 */
	public String getGpSeqId() {
		return gpSeqId;
	}

	/**
	 * @param 支付系统交易流水号
	 *            the gpSeqId to set
	 */
	public void setGpSeqId(String gpSeqId) {
		this.gpSeqId = gpSeqId;
	}

	/**
	 * @return the 支付系统交易类型10-支付交易20-退款交易
	 */
	public Integer getGpTransType() {
		return gpTransType;
	}

	/**
	 * @param 支付系统交易类型10
	 *            -支付交易20-退款交易 the gpTransType to set
	 */
	public void setGpTransType(Integer gpTransType) {
		this.gpTransType = gpTransType;
	}

	/**
	 * @return the 支付系统交易金额
	 */
	public BigDecimal getGpTransAmount() {
		return gpTransAmount;
	}

	/**
	 * @param 支付系统交易金额
	 *            the gpTransAmount to set
	 */
	public void setGpTransAmount(BigDecimal gpTransAmount) {
		this.gpTransAmount = gpTransAmount;
	}

	/**
	 * @return the 支付系统支付单状态具体枚举值定义请参见附录《支付单状态枚举值定义》
	 */
	public Integer getGpTransStatus() {
		return gpTransStatus;
	}

	/**
	 * @param 支付系统支付单状态具体枚举值定义请参见附录
	 *            《支付单状态枚举值定义》 the gpTransStatus to set
	 */
	public void setGpTransStatus(Integer gpTransStatus) {
		this.gpTransStatus = gpTransStatus;
	}

	/**
	 * @return the 支付系统交易时间
	 */
	public Date getGpTransDate() {
		return gpTransDate;
	}

	/**
	 * @param 支付系统交易时间
	 *            the gpTransDate to set
	 */
	public void setGpTransDate(Date gpTransDate) {
		this.gpTransDate = gpTransDate;
	}

	/**
	 * @return the 银联流水号
	 */
	public String getChSeqId() {
		return chSeqId;
	}

	/**
	 * @param 银联流水号
	 *            the chSeqId to set
	 */
	public void setChSeqId(String chSeqId) {
		this.chSeqId = chSeqId;
	}

	/**
	 * @return the 银联交易类型10-支付交易20-退款交易
	 */
	public Integer getChTransType() {
		return chTransType;
	}

	/**
	 * @param 银联交易类型10
	 *            -支付交易20-退款交易 the chTransType to set
	 */
	public void setChTransType(Integer chTransType) {
		this.chTransType = chTransType;
	}

	/**
	 * @return the 银联交易金额
	 */
	public BigDecimal getChTransAmount() {
		return chTransAmount;
	}

	/**
	 * @param 银联交易金额
	 *            the chTransAmount to set
	 */
	public void setChTransAmount(BigDecimal chTransAmount) {
		this.chTransAmount = chTransAmount;
	}

	/**
	 * @return the 银联交易状态
	 */
	public Integer getChTransStatus() {
		return chTransStatus;
	}

	/**
	 * @param 银联交易状态
	 *            the chTransStatus to set
	 */
	public void setChTransStatus(Integer chTransStatus) {
		this.chTransStatus = chTransStatus;
	}

	/**
	 * @return the 银联交易时间
	 */
	public Date getChTransDate() {
		return chTransDate;
	}

	/**
	 * @param 银联交易时间
	 *            the chTransDate to set
	 */
	public void setChTransDate(Date chTransDate) {
		this.chTransDate = chTransDate;
	}

	/**
	 * @return the 银联商户日期
	 */
	public Date getChMerchantDate() {
		return chMerchantDate;
	}

	/**
	 * @param 银联商户日期
	 *            the chMerchantDate to set
	 */
	public void setChMerchantDate(Date chMerchantDate) {
		this.chMerchantDate = chMerchantDate;
	}

	/**
	 * @return the 对账结果0：长款1：短款2：要素不一致
	 */
	public Integer getImbalanceType() {
		return imbalanceType;
	}

	/**
	 * @param 对账结果0
	 *            ：长款1：短款2：要素不一致 the imbalanceType to set
	 */
	public void setImbalanceType(Integer imbalanceType) {
		this.imbalanceType = imbalanceType;
	}

}