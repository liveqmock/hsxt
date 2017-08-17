package com.gy.hsxt.es.distribution.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * @description 积分分配实体类
 * @author chenhz
 * @createDate 2015-7-27 下午3:30:10
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class Alloc implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4274588230357655728L;
	
	/** 消费者互生号 **/
	private String perResNo;
	/** 原始交易时间 */
	private Timestamp sourceTransDate;
	/** 交易类型 **/
	private String transType;
	/** 获得积分值 **/
	private BigDecimal pointVal;
	
	/** 积分分配流水号 **/
	private String allocNo;
	/** 客户全局编号 **/
	private String custId;
	/** 企业互生号 **/
	private String hsResNo;
	/** 账户类型编码 **/
	private String accType;
	/** 红冲标识 **/
	private String writeBack;
	/** 渠道类型 **/
	private Integer channelType;
	/** 设备类型 **/
	private Integer equipmentType;
	/** 交易流水号 **/
	private String transNo;
	/** 关联交易类型 **/
	private String relTransType;
	/** 关联交易流水号 **/
	private String relTransNo;
	/** 原交易流水号 **/
	private String sourceTransNo;
	
	/** 是否扣商业服务费 **/
	private Integer isDeduction;

	/** 关联扣费汇总流水号 **/
	private String relTaxSumNo;
	/** 增向金额 **/
	private BigDecimal addAmount;
	/** 批次号 **/
	private String batchNo;
	/** 原批次号 **/
	private String sourceBatchNo;
	/** 客户类型 **/
	private Integer custType;
	/** 扣费汇总(税收、商业服务费、汇总) */
	private String deductionSumNo;
	/** 实时分录表 - 分录流水号 **/
	private String entryNo;
	/** 实时分录表 - 关联分录流水号 **/
	private String relEntryNo;
	/** 实时分录表 - 减向金额 **/
	private BigDecimal subAmount;

	/** 积分-汇总表 - 流水号 **/
	private String sumNo;
	/** 积分-汇总表 - 笔数 **/
	private Integer addCount;
	/** 积分-汇总表 - 关联流水号 **/
	private String relAllocNo;

	/** 积分-税收表 - 流水号 */
	private String taxNo;
	/** 积分-税收表 - 税收金额 */
	private BigDecimal taxAmount;
	/** 积分-税收表 - 账户类型 */
	private String taxAccType;

	/** 互生币汇总表 - 流水号 */
	private String hsbSumNo;
	/** 互生币-商业服务费表 - 流水号 */
	private String cscNo;
	/** 互生币-商业服务费表 - 金额 */
	private BigDecimal cscAmount;
	
	/** 账户余额正负（1：正, 2：负）*/
	private String  positiveNegative;

	/** 是否结算 */
	private Integer isSettle;

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
	 * 获取客户全局编号
	 * 
	 * @return String custId
	 */
	public String getCustId()
	{
		return custId;
	}

	/**
	 * 设置客户全局编号
	 * 
	 * @param custId
	 *            客户全局编号
	 */
	public void setCustId(String custId)
	{
		this.custId = custId;
	}

	/**
	 * 获取企业互生号
	 * 
	 * @return String hsResNo
	 */
	public String getHsResNo()
	{
		return hsResNo;
	}

	/**
	 * 设置企业互生号
	 * 
	 * @param hsResNo
	 *            企业互生号
	 */
	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	/**
	 * 获取账户类型编码
	 * 
	 * @return String accType
	 */
	public String getAccType()
	{
		return accType;
	}

	/**
	 * 设置账户类型编码
	 * 
	 * @param accType
	 *            账户类型编码
	 */
	public void setAccType(String accType)
	{
		this.accType = accType;
	}

	/**
	 * 获取增向金额
	 * 
	 * @return BigDecimal addAmount
	 */
	public BigDecimal getAddAmount()
	{
		return addAmount;
	}

	/**
	 * 设置增向金额
	 * 
	 * @param addAmount
	 *            增向金额
	 */
	public void setAddAmount(BigDecimal addAmount)
	{
		this.addAmount = addAmount;
	}

	/**
	 * 获取红冲标识
	 * 
	 * @return String writeBack
	 */
	public String getWriteBack()
	{
		return writeBack;
	}

	/**
	 * 设置红冲标识
	 * 
	 * @param writeBack
	 *            红冲标识
	 */
	public void setWriteBack(String writeBack)
	{
		this.writeBack = writeBack;
	}

	/**
	 * 获取关联交易类型
	 * 
	 * @return String relTransType
	 */
	public String getRelTransType()
	{
		return relTransType;
	}

	/**
	 * 设置关联交易类型
	 * 
	 * @param relTransType
	 *            关联交易类型
	 */
	public void setRelTransType(String relTransType)
	{
		this.relTransType = relTransType;
	}

	/**
	 * 获取关联交易流水号
	 * 
	 * @return String relTransNo
	 */
	public String getRelTransNo()
	{
		return relTransNo;
	}

	/**
	 * 设置关联交易流水号
	 * 
	 * @param relTransNo
	 *            关联交易流水号
	 */
	public void setRelTransNo(String relTransNo)
	{
		this.relTransNo = relTransNo;
	}

	/**
	 * 获取分录表-序号
	 * 
	 * @return String entryNo
	 */
	public String getEntryNo()
	{
		return entryNo;
	}

	/**
	 * 设置分录表-序号
	 * 
	 * @param entryNo
	 *            分录表-序号
	 */
	public void setEntryNo(String entryNo)
	{
		this.entryNo = entryNo;
	}

	/**
	 * 获取分录表-减向金额
	 * 
	 * @return BigDecimal subAmount
	 */
	public BigDecimal getSubAmount()
	{
		return subAmount;
	}

	/**
	 * 设置分录表-减向金额
	 * 
	 * @param subAmount
	 *            分录表-减向金额
	 */
	public void setSubAmount(BigDecimal subAmount)
	{
		this.subAmount = subAmount;
	}

	/**
	 * 获取积分分配流水号
	 * @return allocNo 积分分配流水号
	 */
	public String getAllocNo()
	{
		return allocNo;
	}

	/**
	 * 设置积分分配流水号
	 * @param allocNo 积分分配流水号
	 */
	public void setAllocNo(String allocNo)
	{
		this.allocNo = allocNo;
	}

	/**
	 * 获取交易流水号
	 * 
	 * @return String transNo
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
	 * 获取汇总表-
	 * 
	 * @return String sumNo
	 */
	public String getSumNo()
	{
		return sumNo;
	}

	/**
	 * 设置汇总表-
	 * 
	 * @param sumNo
	 *            汇总表-
	 */
	public void setSumNo(String sumNo)
	{
		this.sumNo = sumNo;
	}

	/**
	 * 获取addCount
	 * 
	 * @return Integer addCount
	 */
	public Integer getAddCount()
	{
		return addCount;
	}

	/**
	 * 设置addCount
	 * 
	 * @param addCount
	 *            addCount
	 */
	public void setAddCount(Integer addCount)
	{
		this.addCount = addCount;
	}

	/**
	 * 获取批次号
	 * 
	 * @return String batchNo
	 */
	public String getBatchNo()
	{
		return batchNo;
	}

	/**
	 * 设置批次号
	 * 
	 * @param batchNo
	 *            批次号
	 */
	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	/**
	 * 获取原批次号
	 * 
	 * @return String sourceBatchNo
	 */
	public String getSourceBatchNo()
	{
		return sourceBatchNo;
	}

	/**
	 * 设置原批次号
	 * 
	 * @param sourceBatchNo
	 *            原批次号
	 */
	public void setSourceBatchNo(String sourceBatchNo)
	{
		this.sourceBatchNo = sourceBatchNo;
	}

	/**
	 * 获取relAllocNo
	 * 
	 * @return String relAllocNo
	 */
	public String getRelAllocNo()
	{
		return relAllocNo;
	}

	/**
	 * 设置relAllocNo
	 * 
	 * @param relAllocNo
	 *            relAllocNo
	 */
	public void setRelAllocNo(String relAllocNo)
	{
		this.relAllocNo = relAllocNo;
	}

	/**
	 * 获取关联扣费汇总流水号
	 * 
	 * @return String relTaxSumNo
	 */
	public String getRelTaxSumNo()
	{
		return relTaxSumNo;
	}

	/**
	 * 设置关联扣费汇总流水号
	 * 
	 * @param relTaxSumNo
	 *            关联扣费汇总流水号
	 */
	public void setRelTaxSumNo(String relTaxSumNo)
	{
		this.relTaxSumNo = relTaxSumNo;
	}

	/**
	 * 获取扣费表
	 * 
	 * @return BigDecimal taxAmount
	 */
	public BigDecimal getTaxAmount()
	{
		return taxAmount;
	}

	/**
	 * 设置扣费表
	 * 
	 * @param taxAmount
	 *            扣费表
	 */
	public void setTaxAmount(BigDecimal taxAmount)
	{
		this.taxAmount = taxAmount;
	}

	/**
	 * 获取taxAccType
	 * 
	 * @return String taxAccType
	 */
	public String getTaxAccType()
	{
		return taxAccType;
	}

	/**
	 * 设置taxAccType
	 * 
	 * @param taxAccType
	 *            taxAccType
	 */
	public void setTaxAccType(String taxAccType)
	{
		this.taxAccType = taxAccType;
	}

	/**
	 * 获取扣费表
	 * 
	 * @return String taxNo
	 */
	public String getTaxNo()
	{
		return taxNo;
	}

	/**
	 * 设置扣费表
	 * 
	 * @param taxNo
	 *            扣费表
	 */
	public void setTaxNo(String taxNo)
	{
		this.taxNo = taxNo;
	}

	/**
	 * 获取原交易流水号
	 * 
	 * @return String sourceTransNo
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}

	/**
	 * 设置原交易流水号
	 * 
	 * @param sourceTransNo
	 *            原交易流水号
	 */
	public void setSourceTransNo(String sourceTransNo)
	{
		this.sourceTransNo = sourceTransNo;
	}

	/**
	 * 获取商业服务费表
	 * 
	 * @return String cscNo
	 */
	public String getCscNo()
	{
		return cscNo;
	}

	/**
	 * 设置商业服务费表
	 * 
	 * @param cscNo
	 *            商业服务费表
	 */
	public void setCscNo(String cscNo)
	{
		this.cscNo = cscNo;
	}

	/**
	 * 获取扣费汇总(税收、商业服务费、汇总)
	 * 
	 * @return String deductionSumNo
	 */
	public String getDeductionSumNo()
	{
		return deductionSumNo;
	}

	/**
	 * 设置扣费汇总(税收、商业服务费、汇总)
	 * 
	 * @param deductionSumNo
	 *            扣费汇总(税收、商业服务费、汇总)
	 */
	public void setDeductionSumNo(String deductionSumNo)
	{
		this.deductionSumNo = deductionSumNo;
	}

	/**
	 * 获取cscAmount
	 * 
	 * @return BigDecimal cscAmount
	 */
	public BigDecimal getCscAmount()
	{
		return cscAmount;
	}

	/**
	 * 设置cscAmount
	 * 
	 * @param cscAmount
	 *            cscAmount
	 */
	public void setCscAmount(BigDecimal cscAmount)
	{
		this.cscAmount = cscAmount;
	}

	/**
	 * 获取客户类型
	 * 
	 * @return Integer custType
	 */
	public Integer getCustType()
	{
		return custType;
	}

	/**
	 * 设置客户类型
	 * 
	 * @param custType
	 *            客户类型
	 */
	public void setCustType(Integer custType)
	{
		this.custType = custType;
	}

	/**
	 * 获取渠道类型
	 * 
	 * @return Integer channelType
	 */
	public Integer getChannelType()
	{
		return channelType;
	}

	/**
	 * 设置渠道类型
	 * 
	 * @param channelType
	 *            渠道类型
	 */
	public void setChannelType(Integer channelType)
	{
		this.channelType = channelType;
	}

	/**
	 * 获取互生币汇总表-流水号
	 * 
	 * @return String hsbSumNo
	 */
	public String getHsbSumNo()
	{
		return hsbSumNo;
	}

	/**
	 * 设置互生币汇总表-流水号
	 * 
	 * @param hsbSumNo
	 *            互生币汇总表-流水号
	 */
	public void setHsbSumNo(String hsbSumNo)
	{
		this.hsbSumNo = hsbSumNo;
	}

	/**
	 * @return the 设备类型
	 */
	public Integer getEquipmentType()
	{
		return equipmentType;
	}

	/**
	 * @param 设备类型
	 *            the equipmentType to set
	 */
	public void setEquipmentType(Integer equipmentType)
	{
		this.equipmentType = equipmentType;
	}

	/**
	 * 获取实时分录表-关联分录流水号
	 * @return relEntryNo 实时分录表-关联分录流水号
	 */
	public String getRelEntryNo()
	{
		return relEntryNo;
	}

	/**
	 * 设置实时分录表-关联分录流水号
	 * @param relEntryNo 实时分录表-关联分录流水号
	 */
	public void setRelEntryNo(String relEntryNo)
	{
		this.relEntryNo = relEntryNo;
	}

	/**
	 * 获取账户余额正负（1：正2：负）
	 * @return positiveNegative 账户余额正负（1：正2：负）
	 */
	public String getPositiveNegative()
	{
		return positiveNegative;
	}

	/**
	 * 设置账户余额正负（1：正2：负）
	 * @param positiveNegative 账户余额正负（1：正2：负）
	 */
	public void setPositiveNegative(String positiveNegative)
	{
		this.positiveNegative = positiveNegative;
	}

	/**
	 * 获取消费者互生号
	 * @return perResNo 消费者互生号
	 */
	public String getPerResNo()
	{
		return perResNo;
	}

	/**
	 * 设置消费者互生号
	 * @param perResNo 消费者互生号
	 */
	public void setPerResNo(String perResNo)
	{
		this.perResNo = perResNo;
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
	 * 获取获得积分值
	 * @return pointVal 获得积分值
	 */
	public BigDecimal getPointVal()
	{
		return pointVal;
	}

	/**
	 * 设置获得积分值
	 * @param pointVal 获得积分值
	 */
	public void setPointVal(BigDecimal pointVal)
	{
		this.pointVal = pointVal;
	}

	/**
	 * 获取是否结算
	 * @return Integer isSettle
	 */
	public Integer getIsSettle() {
		return isSettle;
	}
	/**
	 * 设置是否结算
	 * @param isSettle 是否结算
	 */
	public void setIsSettle(Integer isSettle) {
		this.isSettle = isSettle;
	}

}
