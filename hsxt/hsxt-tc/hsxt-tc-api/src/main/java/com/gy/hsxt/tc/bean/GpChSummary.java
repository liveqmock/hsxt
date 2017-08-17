/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 支付系统GP 与银联CH 对账汇总记录
 * 
 * @Package: com.gy.hsxt.tc.bean
 * @ClassName: GpChSummary
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午8:23:12
 * @version V1.0
 */
public class GpChSummary implements Serializable {
	private static final long serialVersionUID = 8789144556303655839L;

	/** 主键，自增长 */
	private Long sumId;

	/** 对账日期，格式YYYY-MM-DD */
	private String tcDate;

	/** 支付系统交易笔数 */
	private Long gpTranNum;

	/** 支付系统交易金额 */
	private BigDecimal gpTranAmount;

	/** 银联笔数 */
	private Long chTranNum;

	/** 银联金额 */
	private BigDecimal chTranAmount;

	/** 帐平笔数 */
	private Long flatNum;

	/** 帐平金额 */
	private BigDecimal flatAmount;

	/** 支付无银联有笔数 */
	private Long chHaveNum;

	/** 支付无银联有金额 */
	private BigDecimal chHaveAmount;

	/** 支付有银联无笔数 */
	private Long gpHaveNum;

	/** 支付有银联无金额 */
	private BigDecimal gpHaveAmount;

	/** 更新时间，取记录更新时的系统时间 */
	private Timestamp updatedDate;

	/** 对账结果：0平衡，1不平衡 */
	private Integer tcResult;

	/**
	 * @return the 主键，自增长
	 */
	public Long getSumId() {
		return sumId;
	}

	/**
	 * @param 主键
	 *            ，自增长 the sumId to set
	 */
	public void setSumId(Long sumId) {
		this.sumId = sumId;
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
	 * @return the 支付系统交易笔数
	 */
	public Long getGpTranNum() {
		return gpTranNum;
	}

	/**
	 * @param 支付系统交易笔数
	 *            the gpTranNum to set
	 */
	public void setGpTranNum(Long gpTranNum) {
		this.gpTranNum = gpTranNum;
	}

	/**
	 * @return the 支付系统交易金额
	 */
	public BigDecimal getGpTranAmount() {
		return gpTranAmount;
	}

	/**
	 * @param 支付系统交易金额
	 *            the gpTranAmount to set
	 */
	public void setGpTranAmount(BigDecimal gpTranAmount) {
		this.gpTranAmount = gpTranAmount;
	}

	/**
	 * @return the 银联笔数
	 */
	public Long getChTranNum() {
		return chTranNum;
	}

	/**
	 * @param 银联笔数
	 *            the chTranNum to set
	 */
	public void setChTranNum(Long chTranNum) {
		this.chTranNum = chTranNum;
	}

	/**
	 * @return the 银联金额
	 */
	public BigDecimal getChTranAmount() {
		return chTranAmount;
	}

	/**
	 * @param 银联金额
	 *            the chTranAmount to set
	 */
	public void setChTranAmount(BigDecimal chTranAmount) {
		this.chTranAmount = chTranAmount;
	}

	/**
	 * @return the 帐平笔数
	 */
	public Long getFlatNum() {
		return flatNum;
	}

	/**
	 * @param 帐平笔数
	 *            the flatNum to set
	 */
	public void setFlatNum(Long flatNum) {
		this.flatNum = flatNum;
	}

	/**
	 * @return the 帐平金额
	 */
	public BigDecimal getFlatAmount() {
		return flatAmount;
	}

	/**
	 * @param 帐平金额
	 *            the flatAmount to set
	 */
	public void setFlatAmount(BigDecimal flatAmount) {
		this.flatAmount = flatAmount;
	}

	/**
	 * @return the 支付无银联有笔数
	 */
	public Long getChHaveNum() {
		return chHaveNum;
	}

	/**
	 * @param 支付无银联有笔数
	 *            the chHaveNum to set
	 */
	public void setChHaveNum(Long chHaveNum) {
		this.chHaveNum = chHaveNum;
	}

	/**
	 * @return the 支付无银联有金额
	 */
	public BigDecimal getChHaveAmount() {
		return chHaveAmount;
	}

	/**
	 * @param 支付无银联有金额
	 *            the chHaveAmount to set
	 */
	public void setChHaveAmount(BigDecimal chHaveAmount) {
		this.chHaveAmount = chHaveAmount;
	}

	/**
	 * @return the 支付有银联无笔数
	 */
	public Long getGpHaveNum() {
		return gpHaveNum;
	}

	/**
	 * @param 支付有银联无笔数
	 *            the gpHaveNum to set
	 */
	public void setGpHaveNum(Long gpHaveNum) {
		this.gpHaveNum = gpHaveNum;
	}

	/**
	 * @return the 支付有银联无金额
	 */
	public BigDecimal getGpHaveAmount() {
		return gpHaveAmount;
	}

	/**
	 * @param 支付有银联无金额
	 *            the gpHaveAmount to set
	 */
	public void setGpHaveAmount(BigDecimal gpHaveAmount) {
		this.gpHaveAmount = gpHaveAmount;
	}

	/**
	 * @return the 更新时间，取记录更新时的系统时间
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param 更新时间
	 *            ，取记录更新时的系统时间 the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the 对账结果：0平衡，1不平衡
	 */
	public Integer getTcResult() {
		return tcResult;
	}

	/**
	 * @param 对账结果
	 *            ：0平衡，1不平衡 the tcResult to set
	 */
	public void setTcResult(Integer tcResult) {
		this.tcResult = tcResult;
	}

}