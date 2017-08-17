/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @Package: com.gy.hsxt.es.api
 * @ClassName: Proceeds
 * @Description: 企业收入详单
 * 
 * @author: chenhz
 * @date: 2016-1-16 上午11:31:19
 * @version V3.0
 */
public class ProceedsResult  extends Result implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3943376626521914291L;
	/** 互生号 **/
	private String hsResNo;
	/** 客户号 **/
	private String custId;
	/** 批次号 **/
	private String batchNo;
	/** 交易流水号 **/
	private String transNo;
	/** 交易类型 **/
	private String transType;
	/** 会计时间 **/
	private String accountantDate;
	/** 当日销售额 **/
	private String daySale;
	/** 当日交易额 **/
	private String dayTurnover;
	/** 互生币交易额 **/
	private String dayHsbTurnover;
	/** 线下现金交易额 **/
	private String dayCashTurnover;
	/** 当日退货退款额 **/
	private String dayBackTurnover;
	/** 日结商业服务费扣除金额 **/
	private String cscTurnover;
	/** 日结互生币金额 **/
	private String dayHsbAmount;
	/** 总笔数 **/
	private int addCount;

	/**
	 * 获取交易流水号
	 *
	 * @return transNo 交易流水号
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * 设置交易流水号
	 *
	 * @param transNo
	 *            交易流水号
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}

	/**
	 * 获取交易类型
	 *
	 * @return transType 交易类型
	 */
	public String getTransType()
	{
		return transType;
	}

	/**
	 * 设置交易类型
	 *
	 * @param transType
	 *            交易类型
	 */
	public void setTransType(String transType)
	{
		this.transType = transType;
	}

	/**
	 * 获取会计时间
	 * @return accountantDate 会计时间
	 */
	public String getAccountantDate()
	{
		return accountantDate;
	}

	/**
	 * 设置会计时间
	 * @param accountantDate 会计时间
	 */
	public void setAccountantDate(String accountantDate)
	{
		this.accountantDate = accountantDate;
	}

	/**
	 * 获取当日销售额
	 *
	 * @return daySale 当日销售额
	 */
	public String getDaySale()
	{
		return daySale;
	}

	/**
	 * 设置当日销售额
	 *
	 * @param daySale
	 *            当日销售额
	 */
	public void setDaySale(String daySale)
	{
		this.daySale = daySale;
	}

	/**
	 * 获取当日交易额
	 *
	 * @return dayTurnover 当日交易额
	 */
	public String getDayTurnover()
	{
		return dayTurnover;
	}

	/**
	 * 设置当日交易额
	 *
	 * @param dayTurnover
	 *            当日交易额
	 */
	public void setDayTurnover(String dayTurnover)
	{
		this.dayTurnover = dayTurnover;
	}

	/**
	 * 获取互生币交易额
	 *
	 * @return dayHsbTurnover 互生币交易额
	 */
	public String getDayHsbTurnover()
	{
		return dayHsbTurnover;
	}

	/**
	 * 设置互生币交易额
	 *
	 * @param dayHsbTurnover
	 *            互生币交易额
	 */
	public void setDayHsbTurnover(String dayHsbTurnover)
	{
		this.dayHsbTurnover = dayHsbTurnover;
	}

	/**
	 * 获取线下现金交易额
	 *
	 * @return dayCashTurnover 线下现金交易额
	 */
	public String getDayCashTurnover()
	{
		return dayCashTurnover;
	}

	/**
	 * 设置线下现金交易额
	 *
	 * @param dayCashTurnover
	 *            线下现金交易额
	 */
	public void setDayCashTurnover(String dayCashTurnover)
	{
		this.dayCashTurnover = dayCashTurnover;
	}

	/**
	 * 获取当日退货退款额
	 *
	 * @return dayBackTurnover 当日退货退款额
	 */
	public String getDayBackTurnover()
	{
		return dayBackTurnover;
	}

	/**
	 * 设置当日退货退款额
	 *
	 * @param dayBackTurnover
	 *            当日退货退款额
	 */
	public void setDayBackTurnover(String dayBackTurnover)
	{
		this.dayBackTurnover = dayBackTurnover;
	}

	/**
	 * 获取日结商业服务费扣除金额
	 *
	 * @return cscTurnover 日结商业服务费扣除金额
	 */
	public String getCscTurnover()
	{
		return cscTurnover;
	}

	/**
	 * 设置日结商业服务费扣除金额
	 *
	 * @param cscTurnover
	 *            日结商业服务费扣除金额
	 */
	public void setCscTurnover(String cscTurnover)
	{
		this.cscTurnover = cscTurnover;
	}

	/**
	 * 获取日结互生币金额
	 *
	 * @return dayHsbAmount 日结互生币金额
	 */
	public String getDayHsbAmount()
	{
		return dayHsbAmount;
	}

	/**
	 * 设置日结互生币金额
	 *
	 * @param dayHsbAmount
	 *            日结互生币金额
	 */
	public void setDayHsbAmount(String dayHsbAmount)
	{
		this.dayHsbAmount = dayHsbAmount;
	}

	/**
	 * 获取互生号
	 * @return hsResNo 互生号
	 */
	public String getHsResNo()
	{
		return hsResNo;
	}

	/**
	 * 设置互生号
	 * @param hsResNo 互生号
	 */
	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	/**
	 * 获取客户号
	 * @return custId 客户号
	 */
	public String getCustId()
	{
		return custId;
	}

	/**
	 * 设置客户号
	 * @param custId 客户号
	 */
	public void setCustId(String custId)
	{
		this.custId = custId;
	}

	/**
	 * 获取批次号
	 * @return batchNo 批次号
	 */
	public String getBatchNo()
	{
		return batchNo;
	}

	/**
	 * 设置批次号
	 * @param batchNo 批次号
	 */
	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	/**
	 * 获取总笔数
	 * @return addCount 总笔数
	 */
	public int getAddCount() {
		return addCount;
	}
	/**
	 * 设置总笔数
	 * @param addCount 总笔数
	 */
	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}
}
