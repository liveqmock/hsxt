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
 * 业务系统BS 与支付系统GP 对账汇总记录
 * 
 * @Package: com.gy.hsxt.tc.bean
 * @ClassName: BsGpSummary
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午8:14:35
 * @version V1.0
 */
public class BsGpSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键，自增长 */
	private Long sumId;

	/** 对账日期，格式YYYY-MM-DD */
	private String tcDate;

	/** 支付系统交易笔数 */
	private Long gpTranNum;

	/** 支付系统交易金额 */
	private BigDecimal gpTranAmount;

	/** 业务服务支付笔数 */
	private Long bsTranNum;

	/** 业务服务支付金额 */
	private BigDecimal bsTranAmount;

	/** 帐平笔数 */
	private Long flatNum;

	/** 帐平金额 */
	private BigDecimal flatAmount;

	/** 业务服务支付长款笔数 */
	private Long bsHaveNum;

	/** 业务服务支付长款金额 */
	private BigDecimal bsHaveAmount;

	/** 业务服务支付短款笔数 */
	private Long gpHaveNum;

	/** 业务服务支付短款金额 */
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
	 * @return the 业务服务支付笔数
	 */
	public Long getBsTranNum() {
		return bsTranNum;
	}

	/**
	 * @param 业务服务支付笔数
	 *            the bsTranNum to set
	 */
	public void setBsTranNum(Long bsTranNum) {
		this.bsTranNum = bsTranNum;
	}

	/**
	 * @return the 业务服务支付金额
	 */
	public BigDecimal getBsTranAmount() {
		return bsTranAmount;
	}

	/**
	 * @param 业务服务支付金额
	 *            the bsTranAmount to set
	 */
	public void setBsTranAmount(BigDecimal bsTranAmount) {
		this.bsTranAmount = bsTranAmount;
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
	 * @return the 业务服务支付长款笔数
	 */
	public Long getBsHaveNum() {
		return bsHaveNum;
	}

	/**
	 * @param 业务服务支付长款笔数
	 *            the bsHaveNum to set
	 */
	public void setBsHaveNum(Long bsHaveNum) {
		this.bsHaveNum = bsHaveNum;
	}

	/**
	 * @return the 业务服务支付长款金额
	 */
	public BigDecimal getBsHaveAmount() {
		return bsHaveAmount;
	}

	/**
	 * @param 业务服务支付长款金额
	 *            the bsHaveAmount to set
	 */
	public void setBsHaveAmount(BigDecimal bsHaveAmount) {
		this.bsHaveAmount = bsHaveAmount;
	}

	/**
	 * @return the 业务服务支付短款笔数
	 */
	public Long getGpHaveNum() {
		return gpHaveNum;
	}

	/**
	 * @param 业务服务支付短款笔数
	 *            the gpHaveNum to set
	 */
	public void setGpHaveNum(Long gpHaveNum) {
		this.gpHaveNum = gpHaveNum;
	}

	/**
	 * @return the 业务服务支付短款金额
	 */
	public BigDecimal getGpHaveAmount() {
		return gpHaveAmount;
	}

	/**
	 * @param 业务服务支付短款金额
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