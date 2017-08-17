package com.gy.hsxt.ps.distribution.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.ps.distribution.bean.PointAlloc.java
 * @className PointItems
 * @description 一句话描述该类的功能
 * @author liuchao
 * @createDate 2015-8-7 上午9:57:51
 * @updateUser liuchao
 * @updateDate 2015-8-7 上午9:57:51
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class PointItem extends OrganizeItems
{

	/** * 平台20%待分配总额度 */
	protected BigDecimal paasSumPoint = new BigDecimal("0");

	/** * 交易币种类型 流动、定向互生币、网银 */
	protected String transWay;
	/** * 交易金额 */
	protected BigDecimal transAmount;
	/** * 积分总额 */
	protected BigDecimal pointSum;
	/** * 交易流水号 */
	protected String transNo;
	/** * 交易类型 */
	protected String transType;
	/** 原始交易时间 */
	protected Timestamp sourceTransDate;
	/** * 结余积分 */
	protected BigDecimal residuePoint;
	/** * 待分配积分 */
	protected BigDecimal waitPoint;
	/** * 红冲标识 */
	protected String writeBack = "0";
	/** * 关联交易号 */
	protected String relTransNo;
	/** * 关联交易类型 */
	protected String relTransType;

	/** * 原交易流水号 */
	protected String sourceTransNo;

	/** * 消费者积分 */
	protected BigDecimal perPoint;
	/** * 消费者互生号 */
	protected String perResNo;
	/** * 消费者客户号 */
	protected String perCustId;

	/** * 企业积分预付款 */
	protected BigDecimal entPoint;


	/** * 渠道类型 */
	protected Integer channelType;
	
	/** * 设备类型 */
	protected Integer equipmentType;

	/** * 批次号 */
	protected String batchNo;
	
	/** * 原始批次号 */
	protected String sourceBatchNo;
	/** * pos机调用次数 */
	protected Integer posToPsCount;
	
	/** 增向金额 */
	protected BigDecimal addAmount;
	/** 服务公司分配增向金额 */
	protected BigDecimal serviceAddAmount;
	/** 服务公司分配增向金额减税 */
	protected BigDecimal serviceSubTaxAfterAmount;
	/** 平台分配增向金额 */
	protected BigDecimal paasAddAmount;
	/** 税金 */
	protected BigDecimal taxAddAmount;
	/** 互生号 */
	protected String hsResNo;
	/** 客户号 */
	protected String custId;
	/** 业务类型 */
	protected Integer businessType;
	/**
	 * 商业服务费表流水号
	 */
	private String tollNo;

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
	 * 获取服务公司分配增向金额减税
	 * @return serviceSubTaxAfterAmount 服务公司分配增向金额减税
	 */
	public BigDecimal getServiceSubTaxAfterAmount()
	{
		return serviceSubTaxAfterAmount;
	}

	/**
	 * 设置服务公司分配增向金额减税
	 * @param serviceSubTaxAfterAmount 服务公司分配增向金额减税
	 */
	public void setServiceSubTaxAfterAmount(BigDecimal serviceSubTaxAfterAmount)
	{
		this.serviceSubTaxAfterAmount = serviceSubTaxAfterAmount;
	}

	/**
	 * 获取服务公司分配增向金额
	 * @return serviceAddAmount 服务公司分配增向金额
	 */
	public BigDecimal getServiceAddAmount()
	{
		return serviceAddAmount;
	}

	/**
	 * 设置服务公司分配增向金额
	 * @param serviceAddAmount 服务公司分配增向金额
	 */
	public void setServiceAddAmount(BigDecimal serviceAddAmount)
	{
		this.serviceAddAmount = serviceAddAmount;
	}

	/**
	 * 获取平台分配增向金额
	 * @return paasAddAmount 平台分配增向金额
	 */
	public BigDecimal getPaasAddAmount()
	{
		return paasAddAmount;
	}

	/**
	 * 设置平台分配增向金额
	 * @param paasAddAmount 平台分配增向金额
	 */
	public void setPaasAddAmount(BigDecimal paasAddAmount)
	{
		this.paasAddAmount = paasAddAmount;
	}

	/**
	 * 获取税金
	 * @return taxAddAmount 税金
	 */
	public BigDecimal getTaxAddAmount()
	{
		return taxAddAmount;
	}

	/**
	 * 设置税金
	 * @param taxAddAmount 税金
	 */
	public void setTaxAddAmount(BigDecimal taxAddAmount)
	{
		this.taxAddAmount = taxAddAmount;
	}

	/**
	 * 获取交易金额
	 * 
	 * @return transAmount 交易金额
	 */
	public BigDecimal getTransAmount()
	{
		return transAmount;
	}

	/**
	 * 设置交易金额
	 * 
	 * @param transAmount
	 *            交易金额
	 */
	public void setTransAmount(BigDecimal transAmount)
	{
		this.transAmount = transAmount;
	}

	/**
	 * 获取积分总额
	 * 
	 * @return pointSum 积分总额
	 */
	public BigDecimal getPointSum()
	{
		return pointSum;
	}

	/**
	 * 设置积分总额
	 * 
	 * @param pointSum
	 *            积分总额
	 */
	public void setPointSum(BigDecimal pointSum)
	{
		this.pointSum = pointSum;
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
	 * 获取结余积分
	 * 
	 * @return residuePoint 结余积分
	 */
	public BigDecimal getResiduePoint()
	{
		return residuePoint;
	}

	/**
	 * 设置结余积分
	 * 
	 * @param residuePoint
	 *            结余积分
	 */
	public void setResiduePoint(BigDecimal residuePoint)
	{
		this.residuePoint = residuePoint;
	}

	/**
	 * 获取待分配积分
	 * 
	 * @return waitPoint 待分配积分
	 */
	public BigDecimal getWaitPoint()
	{
		return waitPoint;
	}

	/**
	 * 设置待分配积分
	 * 
	 * @param waitPoint
	 *            待分配积分
	 */
	public void setWaitPoint(BigDecimal waitPoint)
	{
		this.waitPoint = waitPoint;
	}

	/**
	 * 获取红冲标识
	 * 
	 * @return writeBack 红冲标识
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
	 * 获取关联交易号
	 * 
	 * @return relTransNo 关联交易号
	 */
	public String getRelTransNo()
	{
		return relTransNo;
	}

	/**
	 * 设置关联交易号
	 * 
	 * @param relTransNo
	 *            关联交易号
	 */
	public void setRelTransNo(String relTransNo)
	{
		this.relTransNo = relTransNo;
	}

	/**
	 * 获取关联交易类型
	 * 
	 * @return relTransType 关联交易类型
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
	 * 获取消费者积分
	 * 
	 * @return perPoint 消费者积分
	 */
	public BigDecimal getPerPoint()
	{
		return perPoint;
	}

	/**
	 * 设置消费者积分
	 * 
	 * @param perPoint
	 *            消费者积分
	 */
	public void setPerPoint(BigDecimal perPoint)
	{
		this.perPoint = perPoint;
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
	 * 获取企业积分预付款
	 * 
	 * @return entPoint 企业积分预付款
	 */
	public BigDecimal getEntPoint()
	{
		return entPoint;
	}

	/**
	 * 设置企业积分预付款
	 * 
	 * @param entPoint
	 *            企业积分预付款
	 */
	public void setEntPoint(BigDecimal entPoint)
	{
		this.entPoint = entPoint;
	}
	/**
	 * 获取平台20%待分配总额度
	 * 
	 * @return BigDecimal paasSumPoint
	 */
	public BigDecimal getPaasSumPoint()
	{
		return paasSumPoint;
	}

	/**
	 * 设置平台20%待分配总额度
	 * 
	 * @param paasSumPoint
	 *            平台20%待分配总额度
	 */
	public void setPaasSumPoint(BigDecimal paasSumPoint)
	{
		this.paasSumPoint = paasSumPoint;
	}

	/**
	 * 获取交易币种类型流动、定向互生币、网银
	 * 
	 * @return String transWay
	 */
	public String getTransWay()
	{
		return transWay;
	}

	/**
	 * 设置交易币种类型流动、定向互生币、网银
	 * 
	 * @param transWay
	 *            交易币种类型流动、定向互生币、网银
	 */
	public void setTransWay(String transWay)
	{
		this.transWay = transWay;
	}

	/**
	 * 获取原始交易时间
	 * 
	 * @return Timestamp sourceTransDate
	 */
	public Timestamp getSourceTransDate()
	{
		return sourceTransDate==null?null:(Timestamp) sourceTransDate.clone();
	}

	/**
	 * 设置原始交易时间
	 * 
	 * @param sourceTransDate
	 *            原始交易时间
	 */
	public void setSourceTransDate(Timestamp sourceTransDate)
	{
		this.sourceTransDate = sourceTransDate==null?null:(Timestamp)sourceTransDate.clone();
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
	 * @return the 原始批次号
	 */
	public String getSourceBatchNo()
	{
		return sourceBatchNo;
	}

	/**
	 * @param sourceBatchNo 原始批次号 the sourceBatchNo to set
	 */
	public void setSourceBatchNo(String sourceBatchNo)
	{
		this.sourceBatchNo = sourceBatchNo;
	}

	/**
	 * @return the 设备类型
	 */
	public Integer getEquipmentType()
	{
		return equipmentType;
	}

	/**
	 * @param equipmentType 设备类型 the equipmentType to set
	 */
	public void setEquipmentType(Integer equipmentType)
	{
		this.equipmentType = equipmentType;
	}

	/**
	 * 获取posToPsCount
	 * @return posToPsCount posToPsCount
	 */
	public Integer getPosToPsCount()
	{
		return posToPsCount;
	}

	/**
	 * 设置posToPsCount
	 * @param posToPsCount posToPsCount
	 */
	public void setPosToPsCount(Integer posToPsCount)
	{
		this.posToPsCount = posToPsCount;
	}

	/**
	 * 获取原始批次号
	 * @return addAmount 原始批次号
	 */
	public BigDecimal getAddAmount()
	{
		return addAmount;
	}

	/**
	 * 设置原始批次号
	 * @param addAmount 原始批次号
	 */
	public void setAddAmount(BigDecimal addAmount)
	{
		this.addAmount = addAmount;
	}

	/**
	 * 获取业务类型
	 * @return businessType 业务类型
	 */
	public Integer getBusinessType()
	{
		return businessType;
	}

	/**
	 * 设置业务类型
	 * @param businessType 业务类型
	 */
	public void setBusinessType(Integer businessType)
	{
		this.businessType = businessType;
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
	 * 获取商业服务费表流水号
	 *
	 * @return tollNo 商业服务费表流水号
	 */
	public String getTollNo() {
		return tollNo;
	}

	/**
	 * 设置商业服务费表流水号
	 *
	 * @param tollNo 商业服务费表流水号
	 */
	public void setTollNo(String tollNo) {
		this.tollNo = tollNo;
	}

}
