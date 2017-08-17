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
 * 消费积分系统PS 与账务系统AC 对账不平衡记录
 * 
 * @Package: com.gy.hsxt.tc.bean
 * @ClassName: PsAcImbalance
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午8:24:00
 * @version V1.0
 */
public class PsAcImbalance implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键，自增长 */
	private Long imbalanceId;

	/** 对账结果 0：长款 1：短款 2：要素不一致 */
	private Integer imbalanceType;

	/** 对账日期，格式YYYY-MM-DD */
	private String tcDate;

	/** 交易流水号 */
	private String transNo;

	/** 业务记账分录ID */
	private String accountSubId;

	/** 消费积分客户号 */
	private String psCustId;

	/** 消费积分账户类型 */
	private String psAccType;

	/** 消费积分增向金额 */
	private BigDecimal psAddAmount;

	/** 消费积分减向金额 */
	private BigDecimal psDecAmount;

	/** 消费积分记账状态 */
	private Integer psStatus;
	/** 消费积分交易类型 */
	private String psTransType;
	/** 消费积分交易单号 */
	private String psSourceTransNo;

	/** 账务系统分录编号 */
	private String acEntryNo;

	/** 账务系统客户号 */
	private String acCustId;

	/** 账务系统账户类型 */
	private String acAccType;

	/** 账务系统增向金额 */
	private BigDecimal acAddAmount;

	/** 账务系统减向金额 */
	private BigDecimal acDecAmount;

	/** 账务系统交易时间 */
	private Timestamp acTransDate;

	/** 账务系统交易类型 */
	private String acTransType;
	/** 账务系统原交易单号 */
	private String acSourceTransNo;
	/**
	 * @return the 主键，自增长
	 */
	public Long getImbalanceId() {
		return imbalanceId;
	}
	/**
	 * @param 主键，自增长 the imbalanceId to set
	 */
	public void setImbalanceId(Long imbalanceId) {
		this.imbalanceId = imbalanceId;
	}
	/**
	 * @return the 对账结果0：长款1：短款2：要素不一致
	 */
	public Integer getImbalanceType() {
		return imbalanceType;
	}
	/**
	 * @param 对账结果0：长款1：短款2：要素不一致 the imbalanceType to set
	 */
	public void setImbalanceType(Integer imbalanceType) {
		this.imbalanceType = imbalanceType;
	}
	/**
	 * @return the 对账日期，格式YYYY-MM-DD
	 */
	public String getTcDate() {
		return tcDate;
	}
	/**
	 * @param 对账日期，格式YYYY-MM-DD the tcDate to set
	 */
	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}
	/**
	 * @return the 交易流水号
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * @param 交易流水号 the transNo to set
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	/**
	 * @return the 业务记账分录ID
	 */
	public String getAccountSubId() {
		return accountSubId;
	}
	/**
	 * @param 业务记账分录ID the accountSubId to set
	 */
	public void setAccountSubId(String accountSubId) {
		this.accountSubId = accountSubId;
	}
	/**
	 * @return the 消费积分客户号
	 */
	public String getPsCustId() {
		return psCustId;
	}
	/**
	 * @param 消费积分客户号 the psCustId to set
	 */
	public void setPsCustId(String psCustId) {
		this.psCustId = psCustId;
	}
	/**
	 * @return the 消费积分账户类型
	 */
	public String getPsAccType() {
		return psAccType;
	}
	/**
	 * @param 消费积分账户类型 the psAccType to set
	 */
	public void setPsAccType(String psAccType) {
		this.psAccType = psAccType;
	}
	/**
	 * @return the 消费积分增向金额
	 */
	public BigDecimal getPsAddAmount() {
		return psAddAmount;
	}
	/**
	 * @param 消费积分增向金额 the psAddAmount to set
	 */
	public void setPsAddAmount(BigDecimal psAddAmount) {
		this.psAddAmount = psAddAmount;
	}
	/**
	 * @return the 消费积分减向金额
	 */
	public BigDecimal getPsDecAmount() {
		return psDecAmount;
	}
	/**
	 * @param 消费积分减向金额 the psDecAmount to set
	 */
	public void setPsDecAmount(BigDecimal psDecAmount) {
		this.psDecAmount = psDecAmount;
	}
	/**
	 * @return the 消费积分记账状态
	 */
	public Integer getPsStatus() {
		return psStatus;
	}
	/**
	 * @param 消费积分记账状态 the psStatus to set
	 */
	public void setPsStatus(Integer psStatus) {
		this.psStatus = psStatus;
	}
	/**
	 * @return the 消费积分交易类型
	 */
	public String getPsTransType() {
		return psTransType;
	}
	/**
	 * @param 消费积分交易类型 the psTransType to set
	 */
	public void setPsTransType(String psTransType) {
		this.psTransType = psTransType;
	}
	/**
	 * @return the 消费积分交易单号
	 */
	public String getPsSourceTransNo() {
		return psSourceTransNo;
	}
	/**
	 * @param 消费积分交易单号 the psSourceTransNo to set
	 */
	public void setPsSourceTransNo(String psSourceTransNo) {
		this.psSourceTransNo = psSourceTransNo;
	}
	/**
	 * @return the 账务系统分录编号
	 */
	public String getAcEntryNo() {
		return acEntryNo;
	}
	/**
	 * @param 账务系统分录编号 the acEntryNo to set
	 */
	public void setAcEntryNo(String acEntryNo) {
		this.acEntryNo = acEntryNo;
	}
	/**
	 * @return the 账务系统客户号
	 */
	public String getAcCustId() {
		return acCustId;
	}
	/**
	 * @param 账务系统客户号 the acCustId to set
	 */
	public void setAcCustId(String acCustId) {
		this.acCustId = acCustId;
	}
	/**
	 * @return the 账务系统账户类型
	 */
	public String getAcAccType() {
		return acAccType;
	}
	/**
	 * @param 账务系统账户类型 the acAccType to set
	 */
	public void setAcAccType(String acAccType) {
		this.acAccType = acAccType;
	}
	/**
	 * @return the 账务系统增向金额
	 */
	public BigDecimal getAcAddAmount() {
		return acAddAmount;
	}
	/**
	 * @param 账务系统增向金额 the acAddAmount to set
	 */
	public void setAcAddAmount(BigDecimal acAddAmount) {
		this.acAddAmount = acAddAmount;
	}
	/**
	 * @return the 账务系统减向金额
	 */
	public BigDecimal getAcDecAmount() {
		return acDecAmount;
	}
	/**
	 * @param 账务系统减向金额 the acDecAmount to set
	 */
	public void setAcDecAmount(BigDecimal acDecAmount) {
		this.acDecAmount = acDecAmount;
	}
	/**
	 * @return the 账务系统交易时间
	 */
	public Timestamp getAcTransDate() {
		return acTransDate;
	}
	/**
	 * @param 账务系统交易时间 the acTransDate to set
	 */
	public void setAcTransDate(Timestamp acTransDate) {
		this.acTransDate = acTransDate;
	}
	/**
	 * @return the 账务系统交易类型
	 */
	public String getAcTransType() {
		return acTransType;
	}
	/**
	 * @param 账务系统交易类型 the acTransType to set
	 */
	public void setAcTransType(String acTransType) {
		this.acTransType = acTransType;
	}
	/**
	 * @return the 账务系统原交易单号
	 */
	public String getAcSourceTransNo() {
		return acSourceTransNo;
	}
	/**
	 * @param 账务系统原交易单号 the acSourceTransNo to set
	 */
	public void setAcSourceTransNo(String acSourceTransNo) {
		this.acSourceTransNo = acSourceTransNo;
	}



}