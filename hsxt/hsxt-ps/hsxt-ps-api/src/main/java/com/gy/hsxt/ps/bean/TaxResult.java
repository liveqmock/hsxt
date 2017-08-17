/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.bean;

import java.io.Serializable;

/**
 * @Package: com.gy.hsxt.es.api
 * @ClassName: TaxResult
 * @Description: 消费积分纳税详单
 * 
 * @author: guopengfei
 * @date: 2016-1-21下午午18:06:19
 * @version V3.0
 */
public class TaxResult implements Serializable
{

	private static final long serialVersionUID = 117193481263216697L;
	/** 互生号 **/
	private String hsResNo;
	/** 客户号 **/
	private String custId;
	/** 批次号 **/
	private String batchNo;
	/** 交易流水号 **/
	private String transNo;
	/** 交易时间 **/
	private String accountantDate;
	/** 纳税扣除积分数 **/
	private String pointTax;
	/** 消费积分收入 **/
	private String pointIncome;
	/** 纳税扣除互生币数 **/
	private String cscTax;
	/** 互生币收入 **/
	private String cscIncome;
	/** 税率 **/
	private String taxRate;


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

	public String getPointTax() {
		return pointTax;
	}

	public void setPointTax(String pointTax) {
		this.pointTax = pointTax;
	}

	public String getPointIncome() {
		return pointIncome;
	}

	public void setPointIncome(String pointIncome) {
		this.pointIncome = pointIncome;
	}

	public String getCscTax() {
		return cscTax;
	}

	public void setCscTax(String cscTax) {
		this.cscTax = cscTax;
	}

	public String getCscIncome() {
		return cscIncome;
	}

	public void setCscIncome(String cscIncome) {
		this.cscIncome = cscIncome;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
}
