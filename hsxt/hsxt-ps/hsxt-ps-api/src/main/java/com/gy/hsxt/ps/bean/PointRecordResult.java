/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.bean;

import java.io.Serializable;

/**
 * @Package: com.gy.hsxt.ps.bean
 * @ClassName: PointRecordResult
 * @Description: 积分记录返回值(刷卡器)
 * 
 * @author: chenhz
 * @date: 2016-1-13 下午3:56:40
 * @version V3.0
 */
public class PointRecordResult implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6160302928145586284L;
	/** 交易流水号 */
	private String transNo;
	/** 企业互生号 */
	private String entResNo;
	/** 消费者互生号 */
	private String perResNo;
	/** 企业客户号 */
	private String entCustId;
	/** 消费者客户号 */
	private String perCustId;
	/** 交易类型 */
	private String transType;
	/** 原交易币种 */
	private String sourceCurrencyCode;
	/** 原订单金额 */
	private String sourceTransAmount;
	/** 消费金额*/
    private String orderAmount;
	/** 实付金额 */
	private String transAmount;
	/** 积分比例 */
	private String pointRate;
	/** 消费者积分 */
	private String perPoint;
	/** 企业应付积分款 */
	private String entPoint;
	/** 原交易流水号 */
	private String sourceTransNo;
	/** 批次号 */
	private String batchNo;
	/** 原交易时间 */
	private String sourceTransDate;
	/*** 操作员 */
	private String operNo;

	/**企业名称 */
	private  String entName;

	/**店铺名称 */
	private  String mallName;

	/*备注 */
	private  String remark;

    /***
     * 抵扣券数量
     */
    private int deductionVoucher;
	
	/**
	 * 获取操作员
	 * @return operNo 操作员
	 */
	public String getOperNo()
	{
		return operNo;
	}

	/**
	 * 设置操作员
	 * @param operNo 操作员
	 */
	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

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
	 * 获取企业互生号
	 * 
	 * @return entResNo 企业互生号
	 */
	public String getEntResNo()
	{
		return entResNo;
	}

	/**
	 * 设置企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号
	 */
	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	/**
	 * 获取消费者互生号
	 * 
	 * @return perResNo 消费者互生号
	 */
	public String getPerResNo()
	{
		return perResNo;
	}

	/**
	 * 设置消费者互生号
	 * 
	 * @param perResNo
	 *            消费者互生号
	 */
	public void setPerResNo(String perResNo)
	{
		this.perResNo = perResNo;
	}

	/**
	 * 获取企业客户号
	 * 
	 * @return entCustId 企业客户号
	 */
	public String getEntCustId()
	{
		return entCustId;
	}

	/**
	 * 设置企业客户号
	 * 
	 * @param entCustId
	 *            企业客户号
	 */
	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	/**
	 * 获取消费者客户号
	 * 
	 * @return perCustId 消费者客户号
	 */
	public String getPerCustId()
	{
		return perCustId;
	}

	/**
	 * 设置消费者客户号
	 * 
	 * @param perCustId
	 *            消费者客户号
	 */
	public void setPerCustId(String perCustId)
	{
		this.perCustId = perCustId;
	}

	/**
	 * 获取原交易币种
	 * 
	 * @return sourceCurrencyCode 原交易币种
	 */
	public String getSourceCurrencyCode()
	{
		return sourceCurrencyCode;
	}

	/**
	 * 设置原交易币种
	 * 
	 * @param sourceCurrencyCode
	 *            原交易币种
	 */
	public void setSourceCurrencyCode(String sourceCurrencyCode)
	{
		this.sourceCurrencyCode = sourceCurrencyCode;
	}

	/**
	 * 获取原订单金额
	 * 
	 * @return sourceTransAmount 原订单金额
	 */
	public String getSourceTransAmount()
	{
		return sourceTransAmount;
	}

	/**
	 * 设置原订单金额
	 * 
	 * @param sourceTransAmount
	 *            原订单金额
	 */
	public void setSourceTransAmount(String sourceTransAmount)
	{
		this.sourceTransAmount = sourceTransAmount;
	}

	/**
     * @return the 消费金额
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     * @param 消费金额 the orderAmount to set
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * @return the 实付金额
     */
    public String getTransAmount() {
        return transAmount;
    }

    /**
     * @param 实付金额 the transAmount to set
     */
    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    /**
	 * 获取积分比例
	 * 
	 * @return pointRate 积分比例
	 */
	public String getPointRate()
	{
		return pointRate;
	}

	/**
	 * 设置积分比例
	 * 
	 * @param pointRate
	 *            积分比例
	 */
	public void setPointRate(String pointRate)
	{
		this.pointRate = pointRate;
	}

	/**
	 * 获取消费者积分
	 * 
	 * @return perPoint 消费者积分
	 */
	public String getPerPoint()
	{
		return perPoint;
	}

	/**
	 * 设置消费者积分
	 * 
	 * @param perPoint
	 *            消费者积分
	 */
	public void setPerPoint(String perPoint)
	{
		this.perPoint = perPoint;
	}

	/**
	 * 获取企业应付积分款
	 * @return entPoint 企业应付积分款
	 */
	public String getEntPoint()
	{
		return entPoint;
	}

	/**
	 * 设置企业应付积分款
	 * @param entPoint 企业应付积分款
	 */
	public void setEntPoint(String entPoint)
	{
		this.entPoint = entPoint;
	}

	/**
	 * 获取交易类型
	 * @return transType 交易类型
	 */
	public String getTransType()
	{
		return transType;
	}

	/**
	 * 设置交易类型
	 * @param transType 交易类型
	 */
	public void setTransType(String transType)
	{
		this.transType = transType;
	}

	/**
	 * 获取原交易流水号
	 * @return sourceTransNo 原交易流水号
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}

	/**
	 * 设置原交易流水号
	 * @param sourceTransNo 原交易流水号
	 */
	public void setSourceTransNo(String sourceTransNo)
	{
		this.sourceTransNo = sourceTransNo;
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
	 * 获取原交易时间
	 * @return sourceTransDate 原交易时间
	 */
	public String getSourceTransDate()
	{
		return sourceTransDate;
	}

	/**
	 * 设置原交易时间
	 * @param sourceTransDate 原交易时间
	 */
	public void setSourceTransDate(String sourceTransDate)
	{
		this.sourceTransDate = sourceTransDate;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDeductionVoucher() {
		return deductionVoucher;
	}

	public void setDeductionVoucher(int deductionVoucher) {
		this.deductionVoucher = deductionVoucher;
	}
	
	
	
}
