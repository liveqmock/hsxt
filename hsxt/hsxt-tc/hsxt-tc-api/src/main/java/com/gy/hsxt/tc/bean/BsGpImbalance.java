/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 业务系统BS 与支付系统GP 对账不平衡记录
 * 
 * @Package: com.gy.hsxt.tc.bean
 * @ClassName: BsGpImbalance
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午8:13:16
 * @version V1.0
 */
public class BsGpImbalance implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键，自增长 */
	private Long imbalanceId;

	/** 对账日期，格式YYYY-MM-DD */
	private String tcDate;

	/** 对账结果 0：长款 1：短款 2：要素不一致 */
	private Integer imbalanceType;

	/** 业务订单号 */
	private String orderNo;

	/** 业务服务支付交易金额 */
	private BigDecimal bsTransAmount;

	/** 业务服务支付交易状态 */
	private Integer bsTransStatus;

	/** 业务服务支付交易时间 */
	private Timestamp bsTransDate;

	/** 支付系统交易流水号 */
	private String gpSeqId;

	/** 支付系统交易类型 10 - 支付交易 20 - 退款交易 */
	private Integer gpTransType;

	/** 支付系统交易金额 */
	private BigDecimal gpTransAmount;

	/** 支付系统支付单状态 具体枚举值定义请参见附录《支付单状态枚举值定义》 */
	private Integer gpTransStatus;

	/** 支付系统交易时间 */
	private Timestamp gpTransDate;

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

	/**
	 * @return the 业务订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param 业务订单号
	 *            the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the 业务服务支付交易金额
	 */
	public BigDecimal getBsTransAmount() {
		return bsTransAmount;
	}

	/**
	 * @param 业务服务支付交易金额
	 *            the bsTransAmount to set
	 */
	public void setBsTransAmount(BigDecimal bsTransAmount) {
		this.bsTransAmount = bsTransAmount;
	}

	/**
	 * @return the 业务服务支付交易状态
	 */
	public Integer getBsTransStatus() {
		return bsTransStatus;
	}

	/**
	 * @param 业务服务支付交易状态
	 *            the bsTransStatus to set
	 */
	public void setBsTransStatus(Integer bsTransStatus) {
		this.bsTransStatus = bsTransStatus;
	}

	/**
	 * @return the 业务服务支付交易时间
	 */
	public Timestamp getBsTransDate() {
		return bsTransDate;
	}

	/**
	 * @param 业务服务支付交易时间
	 *            the bsTransDate to set
	 */
	public void setBsTransDate(Timestamp bsTransDate) {
		this.bsTransDate = bsTransDate;
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
	public Timestamp getGpTransDate() {
		return gpTransDate;
	}

	/**
	 * @param 支付系统交易时间
	 *            the gpTransDate to set
	 */
	public void setGpTransDate(Timestamp gpTransDate) {
		this.gpTransDate = gpTransDate;
	}

}