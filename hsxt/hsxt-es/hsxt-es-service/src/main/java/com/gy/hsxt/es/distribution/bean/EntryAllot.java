/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.distribution.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Package: com.gy.hsxt.ps.distribution.bean
 * @ClassName: EntryAllot
 * @Description: 线上分录实体、线下分录实体
 * 
 * @author: chenhz
 * @date: 2016-2-24 下午2:05:55
 * @version V3.0
 */

public class EntryAllot implements Serializable
{


	private static final long serialVersionUID = -654884504824849872L;
	/** 分录流水号 **/
	private String entryNo;
	/** 批次号 **/
	private String batchNo;
	/** 企业互生号 **/
	private String entHsNo;
	/** 消费者互生号 **/
	private String perHsNo;
	/** 企业客户号 **/
	private String entCustId;
	/** 消费者客户号 **/
	private String perCustId;
	/** 企业增向交易金额 **/
	private BigDecimal entAddAmount;
	/** 企业减向交易金额 **/
	private BigDecimal entSubAmount;
	/** 消费者增向交易金额 **/
	private BigDecimal perAddAmount;
	/** 消费者减向交易金额 **/
	private BigDecimal perSubAmount;
	/** 企业增向积分额 **/
	private BigDecimal entAddPointAmount;
	/** 企业减向积分额 **/
	private BigDecimal entSubPointAmount;
	/** 消费者增向积分额 **/
	private BigDecimal perAddPointAmount;
	/** 消费者减向积分额 **/
	private BigDecimal perSubPointAmount;
	/** 企业增向商业服务费 **/
	private BigDecimal entAddServiceFee;
	/** 企业减向商业服务费 **/
	private BigDecimal entSubServiceFee;
	/** 是否扣商业服务费 **/
	private Integer isDeduction;
	/** 企业结算金额 **/
	private BigDecimal settleAmount;
	/** 原始交易时间 **/
	private Timestamp sourceTransDate;
	/** 交易类型 **/
	private String transType;
	/** 原始交易流水号 **/
	private String sourceTransNo;
	/** 关联原消费积分流水号 **/
	private String relTransNo;
	/** 关联原分录流水号 **/
	private String relEntryNo;
	/** 冲正标识 **/
	private String writeBack;
	/** 是否结算 **/
	private Integer isSettle;
	/** 备注 **/
	private String remark;
	/** 标记此条记录的状态 **/
	private String isActive;
	/** 创建时间，取记录创建时的系统时间 **/
	private Timestamp createdDate;
	/** 由谁创建，值为用户的伪键ID **/
	private String createdBy;
	/** 更新时间，取记录更新时的系统时间 **/
	private Timestamp updatedDate;
	/** 由谁更新，值为用户的伪键ID **/
	private String updatedBy;
	/** 订单状态 */
	private Integer transStatus;

	/** 是否结算 */
	private Integer isOldSettle;

	/** 消费者积分 */
	private BigDecimal perPoint;

	/** 消费积分 */
	private BigDecimal entPoint;

	/** 交易金额*/
	private BigDecimal transAmount;
	/** 原始交易币种代号*/
	private String sourceCurrencyCode;

	/** 渠道类型*/
	private Integer channelType;

	/** 原始币种金额*/
	private BigDecimal sourceTransAmount;

	/** 货币转换率 */
	private BigDecimal currencyRate;
	
	/**企业名称 */
    private  String entName;

    /**店铺名称 */
    private  String mallName;
	
	/**
	 * 获取分录流水号
	 * @return entryNo 分录流水号
	 */
	public String getEntryNo()
	{
		return entryNo;
	}
	/**
	 * 设置分录流水号
	 * @param entryNo 分录流水号
	 */
	public void setEntryNo(String entryNo)
	{
		this.entryNo = entryNo;
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
	 * 获取企业互生号
	 * @return entHsNo 企业互生号
	 */
	public String getEntHsNo()
	{
		return entHsNo;
	}
	/**
	 * 设置企业互生号
	 * @param entHsNo 企业互生号
	 */
	public void setEntHsNo(String entHsNo)
	{
		this.entHsNo = entHsNo;
	}
	/**
	 * 获取消费者互生号
	 * @return perHsNo 消费者互生号
	 */
	public String getPerHsNo()
	{
		return perHsNo;
	}
	/**
	 * 设置消费者互生号
	 * @param perHsNo 消费者互生号
	 */
	public void setPerHsNo(String perHsNo)
	{
		this.perHsNo = perHsNo;
	}
	/**
	 * 获取企业客户号
	 * @return entCustId 企业客户号
	 */
	public String getEntCustId()
	{
		return entCustId;
	}
	/**
	 * 设置企业客户号
	 * @param entCustId 企业客户号
	 */
	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}
	/**
	 * 获取消费者客户号
	 * @return perCustId 消费者客户号
	 */
	public String getPerCustId()
	{
		return perCustId;
	}
	/**
	 * 设置消费者客户号
	 * @param perCustId 消费者客户号
	 */
	public void setPerCustId(String perCustId)
	{
		this.perCustId = perCustId;
	}
	/**
	 * 获取企业增向交易金额
	 * @return entAddAmount 企业增向交易金额
	 */
	public BigDecimal getEntAddAmount()
	{
		return entAddAmount;
	}
	/**
	 * 设置企业增向交易金额
	 * @param entAddAmount 企业增向交易金额
	 */
	public void setEntAddAmount(BigDecimal entAddAmount)
	{
		this.entAddAmount = entAddAmount;
	}
	/**
	 * 获取企业减向交易金额
	 * @return entSubAmount 企业减向交易金额
	 */
	public BigDecimal getEntSubAmount()
	{
		return entSubAmount;
	}
	/**
	 * 设置企业减向交易金额
	 * @param entSubAmount 企业减向交易金额
	 */
	public void setEntSubAmount(BigDecimal entSubAmount)
	{
		this.entSubAmount = entSubAmount;
	}
	/**
	 * 获取消费者增向交易金额
	 * @return perAddAmount 消费者增向交易金额
	 */
	public BigDecimal getPerAddAmount()
	{
		return perAddAmount;
	}
	/**
	 * 设置消费者增向交易金额
	 * @param perAddAmount 消费者增向交易金额
	 */
	public void setPerAddAmount(BigDecimal perAddAmount)
	{
		this.perAddAmount = perAddAmount;
	}
	/**
	 * 获取消费者减向交易金额
	 * @return perSubAmount 消费者减向交易金额
	 */
	public BigDecimal getPerSubAmount()
	{
		return perSubAmount;
	}
	/**
	 * 设置消费者减向交易金额
	 * @param perSubAmount 消费者减向交易金额
	 */
	public void setPerSubAmount(BigDecimal perSubAmount)
	{
		this.perSubAmount = perSubAmount;
	}
	/**
	 * 获取企业增向积分额
	 * @return entAddPointAmount 企业增向积分额
	 */
	public BigDecimal getEntAddPointAmount()
	{
		return entAddPointAmount;
	}
	/**
	 * 设置企业增向积分额
	 * @param entAddPointAmount 企业增向积分额
	 */
	public void setEntAddPointAmount(BigDecimal entAddPointAmount)
	{
		this.entAddPointAmount = entAddPointAmount;
	}
	/**
	 * 获取企业减向积分额
	 * @return entSubPointAmount 企业减向积分额
	 */
	public BigDecimal getEntSubPointAmount()
	{
		return entSubPointAmount;
	}
	/**
	 * 设置企业减向积分额
	 * @param entSubPointAmount 企业减向积分额
	 */
	public void setEntSubPointAmount(BigDecimal entSubPointAmount)
	{
		this.entSubPointAmount = entSubPointAmount;
	}
	/**
	 * 获取消费者增向积分额
	 * @return perAddPointAmount 消费者增向积分额
	 */
	public BigDecimal getPerAddPointAmount()
	{
		return perAddPointAmount;
	}
	/**
	 * 设置消费者增向积分额
	 * @param perAddPointAmount 消费者增向积分额
	 */
	public void setPerAddPointAmount(BigDecimal perAddPointAmount)
	{
		this.perAddPointAmount = perAddPointAmount;
	}
	/**
	 * 获取消费者减向积分额
	 * @return perSubPointAmount 消费者减向积分额
	 */
	public BigDecimal getPerSubPointAmount()
	{
		return perSubPointAmount;
	}
	/**
	 * 设置消费者减向积分额
	 * @param perSubPointAmount 消费者减向积分额
	 */
	public void setPerSubPointAmount(BigDecimal perSubPointAmount)
	{
		this.perSubPointAmount = perSubPointAmount;
	}
	/**
	 * 获取企业增向商业服务费
	 * @return entAddServiceFee 企业增向商业服务费
	 */
	public BigDecimal getEntAddServiceFee()
	{
		return entAddServiceFee;
	}
	/**
	 * 设置企业增向商业服务费
	 * @param entAddServiceFee 企业增向商业服务费
	 */
	public void setEntAddServiceFee(BigDecimal entAddServiceFee)
	{
		this.entAddServiceFee = entAddServiceFee;
	}
	/**
	 * 获取企业减向商业服务费
	 * @return entSubServiceFee 企业减向商业服务费
	 */
	public BigDecimal getEntSubServiceFee()
	{
		return entSubServiceFee;
	}
	/**
	 * 设置企业减向商业服务费
	 * @param entSubServiceFee 企业减向商业服务费
	 */
	public void setEntSubServiceFee(BigDecimal entSubServiceFee)
	{
		this.entSubServiceFee = entSubServiceFee;
	}
	/**
	 * 获取是否扣商业服务费
	 * @return isDeduction 是否扣商业服务费
	 */
	public Integer getIsDeduction()
	{
		return isDeduction;
	}
	/**
	 * 设置是否扣商业服务费
	 * @param isDeduction 是否扣商业服务费
	 */
	public void setIsDeduction(Integer isDeduction)
	{
		this.isDeduction = isDeduction;
	}
	/**
	 * 获取企业结算金额
	 * @return settleAmount 企业结算金额
	 */
	public BigDecimal getSettleAmount()
	{
		return settleAmount;
	}
	/**
	 * 设置企业结算金额
	 * @param settleAmount 企业结算金额
	 */
	public void setSettleAmount(BigDecimal settleAmount)
	{
		this.settleAmount = settleAmount;
	}
	/**
	 * 获取原始交易时间
	 * @return sourceTransDate 原始交易时间
	 */
	public Timestamp getSourceTransDate()
	{
		return sourceTransDate;
	}
	/**
	 * 设置原始交易时间
	 * @param sourceTransDate 原始交易时间
	 */
	public void setSourceTransDate(Timestamp sourceTransDate)
	{
		this.sourceTransDate = sourceTransDate;
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
	 * 获取原始交易流水号
	 * @return sourceTransNo 原始交易流水号
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}
	/**
	 * 设置原始交易流水号
	 * @param sourceTransNo 原始交易流水号
	 */
	public void setSourceTransNo(String sourceTransNo)
	{
		this.sourceTransNo = sourceTransNo;
	}
	/**
	 * 获取关联原消费积分流水号
	 * @return relTransNo 关联原消费积分流水号
	 */
	public String getRelTransNo()
	{
		return relTransNo;
	}
	/**
	 * 设置关联原消费积分流水号
	 * @param relTransNo 关联原消费积分流水号
	 */
	public void setRelTransNo(String relTransNo)
	{
		this.relTransNo = relTransNo;
	}
	/**
	 * 获取关联原分录流水号
	 * @return relEntryNo 关联原分录流水号
	 */
	public String getRelEntryNo()
	{
		return relEntryNo;
	}
	/**
	 * 设置关联原分录流水号
	 * @param relEntryNo 关联原分录流水号
	 */
	public void setRelEntryNo(String relEntryNo)
	{
		this.relEntryNo = relEntryNo;
	}
	/**
	 * 获取冲正标识
	 * @return writeBack 冲正标识
	 */
	public String getWriteBack()
	{
		return writeBack;
	}
	/**
	 * 设置冲正标识
	 * @param writeBack 冲正标识
	 */
	public void setWriteBack(String writeBack)
	{
		this.writeBack = writeBack;
	}
	/**
	 * 获取备注
	 * @return remark 备注
	 */
	public String getRemark()
	{
		return remark;
	}
	/**
	 * 设置备注
	 * @param remark 备注
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	/**
	 * 获取标记此条记录的状态
	 * @return isActive 标记此条记录的状态
	 */
	public String getIsActive()
	{
		return isActive;
	}
	/**
	 * 设置标记此条记录的状态
	 * @param isActive 标记此条记录的状态
	 */
	public void setIsActive(String isActive)
	{
		this.isActive = isActive;
	}
	/**
	 * 获取创建时间，取记录创建时的系统时间
	 * @return createdDate 创建时间，取记录创建时的系统时间
	 */
	public Timestamp getCreatedDate()
	{
		return createdDate;
	}
	/**
	 * 设置创建时间，取记录创建时的系统时间
	 * @param createdDate 创建时间，取记录创建时的系统时间
	 */
	public void setCreatedDate(Timestamp createdDate)
	{
		this.createdDate = createdDate;
	}
	/**
	 * 获取由谁创建，值为用户的伪键ID
	 * @return createdBy 由谁创建，值为用户的伪键ID
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}
	/**
	 * 设置由谁创建，值为用户的伪键ID
	 * @param createdBy 由谁创建，值为用户的伪键ID
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	/**
	 * 获取更新时间，取记录更新时的系统时间
	 * @return updatedDate 更新时间，取记录更新时的系统时间
	 */
	public Timestamp getUpdatedDate()
	{
		return updatedDate;
	}
	/**
	 * 设置更新时间，取记录更新时的系统时间
	 * @param updatedDate 更新时间，取记录更新时的系统时间
	 */
	public void setUpdatedDate(Timestamp updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	/**
	 * 获取由谁更新，值为用户的伪键ID
	 * @return updatedBy 由谁更新，值为用户的伪键ID
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	/**
	 * 设置由谁更新，值为用户的伪键ID
	 * @param updatedBy 由谁更新，值为用户的伪键ID
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	/**
	 * 获取是否结算
	 * @return isSettle 是否结算
	 */
	public Integer getIsSettle()
	{
		return isSettle;
	}
	/**
	 * 设置是否结算
	 * @param isSettle 是否结算
	 */
	public void setIsSettle(Integer isSettle)
	{
		this.isSettle = isSettle;
	}

	/**
	 * 获取订单状态
	 * @return Integer transStatus
	 */
	public Integer getTransStatus() {
		return transStatus;
	}
	/**
	 * 设置订单状态
	 * @param transStatus 订单状态
	 */
	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}

	public Integer getIsOldSettle() {
		return isOldSettle;
	}

	public void setIsOldSettle(Integer isOldSettle) {
		this.isOldSettle = isOldSettle;
	}
	/**
	 * 获取消费者积分
	 * @return BigDecimal perPoint
	 */
	public BigDecimal getPerPoint() {
		return perPoint;
	}
	/**
	 * 设置消费者积分
	 * @param perPoint 消费者积分
	 */
	public void setPerPoint(BigDecimal perPoint) {
		this.perPoint = perPoint;
	}

	/**
	 * @return the 企业应付积分款
	 */
	public BigDecimal getEntPoint()
	{
		return entPoint;
	}
	/**
	 * @param entPoint 企业应付积分款
	 */
	public void setEntPoint(BigDecimal entPoint)
	{
		this.entPoint = entPoint;
	}
	/**
	 * 获取交易金额
	 * @return transAmount 交易金额
	 */
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	/**
	 * 设置交易金额
	 * @param transAmount 交易金额
	 */
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	/**
	 * 获取原始交易币种代号
	 * @return sourceCurrencyCode 原始交易币种代号
	 */
	public String getSourceCurrencyCode() {
		return sourceCurrencyCode;
	}
	/**
	 * 设置原始交易币种代号
	 * @param sourceCurrencyCode 原始交易币种代号
	 */
	public void setSourceCurrencyCode(String sourceCurrencyCode) {
		this.sourceCurrencyCode = sourceCurrencyCode;
	}
	/**
	 * 获取渠道类型
	 * @return channelType 渠道类型
	 */
	public Integer getChannelType() {
		return channelType;
	}
	/**
	 * 设置渠道类型
	 * @param channelType 渠道类型
	 */
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	/**
	 * 获取原始币种金额
	 * @return sourceTransAmount 原始币种金额
	 */
	public BigDecimal getSourceTransAmount() {
		return sourceTransAmount;
	}
	/**
	 * 设置原始币种金额
	 * @param sourceTransAmount 原始币种金额
	 */
	public void setSourceTransAmount(BigDecimal sourceTransAmount) {
		this.sourceTransAmount = sourceTransAmount;
	}

	/**
	 * 获取货币转换率
	 * @return BigDecimal currencyRate
	 */
	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}
	/**
	 * 设置货币转换率
	 * @param currencyRate 货币转换率
	 */
	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}
    /**
     * @return the 企业名称
     */
    public String getEntName() {
        return entName;
    }
    /**
     * @param 企业名称 the entName to set
     */
    public void setEntName(String entName) {
        this.entName = entName;
    }
    /**
     * @return the 店铺名称
     */
    public String getMallName() {
        return mallName;
    }
    /**
     * @param 店铺名称 the mallName to set
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }
	
}
