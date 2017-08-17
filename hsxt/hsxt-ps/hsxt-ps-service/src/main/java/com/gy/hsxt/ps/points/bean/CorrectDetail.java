package com.gy.hsxt.ps.points.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @description 冲正明细实体类
 * @author chenhz
 * @createDate 2015-7-29 上午9:18:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class CorrectDetail implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5634086535482266257L;

	/** 交易类型 */
	private String transType;
	/** 企业互生号 */
	private String entResNo;
	/** 设备编号 */
	private String equipmentNo;
	/** 渠道类型 */
	private Integer channelType;
	/** 原始交易流水号 */
	private String sourceTransNo;
	/** 原批次号 */
	private String sourceBatchNo;
	/** 冲正原因 */
	private String returnReason;
	/*** 终端流水号(POS) */
	private String termRunCode;

	/** 冲正流水号 */
	private String returnNo;
	/** 原订单流水号 */
	private String transNo;
	/** 消费者互生号 */
	private String perResNo;
	/** 设备类型 */
	private Integer equipmentType;
	/** 原始交易时间 */
	private Timestamp sourceTransDate;
	/** 冲正交易金额 */
	private BigDecimal transAmount;
	/** 冲正积分 */
	private BigDecimal perPoint;
	/** 积分预付款 */
	private BigDecimal entPoint;
	/** 业务状态 */
	private Integer status;
	/** 冲正状态 */
	private Integer transStatus;
	/** 冲正发起方 */
	private String initiate;
	/** 此记录的状态 */
	private String isActive;
	/** 创建时间 */
	private Timestamp createdDate;
	/** 创建者 */
	private String createdBy;
	/** 修改时间 */
	private Timestamp updatedDate;
	/** 修改者 */
	private String updatedBy;
	/** 批次号 */
	private String batchNo;

	/**企业名称 */
	private  String entName;

	/**店铺名称 */
	private  String mallName;

	/*备注 */
	private  String remark;
	/**
	 * 获取冲正流水号
	 * 
	 * @return String returnNo
	 */
	public String getReturnNo()
	{
		return returnNo;
	}

	/**
	 * 设置冲正流水号
	 * 
	 * @param returnNo
	 *            冲正流水号
	 */
	public void setReturnNo(String returnNo)
	{
		this.returnNo = returnNo;
	}

	/**
	 * 获取原订单流水号
	 * 
	 * @return String transNo
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * 设置原订单流水号
	 * 
	 * @param transNo
	 *            原订单流水号
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}

	/**
	 * 获取消费者互生号
	 * 
	 * @return String perResNo
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
	 * 获取设备类型
	 * 
	 * @return Integer equipmentType
	 */
	public Integer getEquipmentType()
	{
		return equipmentType;
	}

	/**
	 * 设置设备类型
	 * 
	 * @param equipmentType
	 *            设备类型
	 */
	public void setEquipmentType(Integer equipmentType)
	{
		this.equipmentType = equipmentType;
	}

	/**
	 * 获取冲正交易金额
	 * 
	 * @return BigDecimal transAmount
	 */
	public BigDecimal getTransAmount()
	{
		return transAmount;
	}

	/**
	 * 设置冲正交易金额
	 * 
	 * @param transAmount
	 *            冲正交易金额
	 */
	public void setTransAmount(BigDecimal transAmount)
	{
		this.transAmount = transAmount;
	}

	/**
	 * 获取冲正积分
	 * 
	 * @return BigDecimal perPoint
	 */
	public BigDecimal getPerPoint()
	{
		return perPoint;
	}

	/**
	 * 设置冲正积分
	 * 
	 * @param perPoint
	 *            冲正积分
	 */
	public void setPerPoint(BigDecimal perPoint)
	{
		this.perPoint = perPoint;
	}

	/**
	 * 获取积分预付款
	 * 
	 * @return BigDecimal entPoint
	 */
	public BigDecimal getEntPoint()
	{
		return entPoint;
	}

	/**
	 * 设置积分预付款
	 * 
	 * @param entPoint
	 *            积分预付款
	 */
	public void setEntPoint(BigDecimal entPoint)
	{
		this.entPoint = entPoint;
	}

	/**
	 * 获取业务状态
	 * 
	 * @return Integer status
	 */
	public Integer getStatus()
	{
		return status;
	}

	/**
	 * 设置业务状态
	 * 
	 * @param status
	 *            业务状态
	 */
	public void setStatus(Integer status)
	{
		this.status = status;
	}

	/**
	 * 获取冲正状态
	 * 
	 * @return Integer transStatus
	 */
	public Integer getTransStatus()
	{
		return transStatus;
	}

	/**
	 * 设置冲正状态
	 * 
	 * @param transStatus
	 *            冲正状态
	 */
	public void setTransStatus(Integer transStatus)
	{
		this.transStatus = transStatus;
	}

	/**
	 * 获取冲正发起方
	 * 
	 * @return String initiate
	 */
	public String getInitiate()
	{
		return initiate;
	}

	/**
	 * 设置冲正发起方
	 * 
	 * @param initiate
	 *            冲正发起方
	 */
	public void setInitiate(String initiate)
	{
		this.initiate = initiate;
	}

	/**
	 * 获取此记录的状态
	 * 
	 * @return String isActive
	 */
	public String getIsActive()
	{
		return isActive;
	}

	/**
	 * 设置此记录的状态
	 * 
	 * @param isActive
	 *            此记录的状态
	 */
	public void setIsActive(String isActive)
	{
		this.isActive = isActive;
	}

	/**
	 * 获取创建者
	 * 
	 * @return String createdBy
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * 设置创建者
	 * 
	 * @param createdBy
	 *            创建者
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * 获取修改者
	 * 
	 * @return String updatedBy
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}

	/**
	 * 设置修改者
	 * 
	 * @param updatedBy
	 *            修改者
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
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
		this.sourceTransDate = sourceTransDate==null?null:(Timestamp) sourceTransDate.clone();
	}

	/**
	 * 获取创建时间
	 * 
	 * @return Timestamp createdDate
	 */
	public Timestamp getCreatedDate()
	{
		return createdDate==null?null:(Timestamp) createdDate.clone();
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createdDate
	 *            创建时间
	 */
	public void setCreatedDate(Timestamp createdDate)
	{
		this.createdDate = createdDate==null?null:(Timestamp) createdDate.clone();
	}

	/**
	 * 获取修改时间
	 * 
	 * @return Timestamp updatedDate
	 */
	public Timestamp getUpdatedDate()
	{
		return updatedDate==null?null:(Timestamp)updatedDate.clone();
	}

	/**
	 * 设置修改时间
	 * 
	 * @param updatedDate
	 *            修改时间
	 */
	public void setUpdatedDate(Timestamp updatedDate)
	{
		this.updatedDate = updatedDate==null?null:(Timestamp) updatedDate.clone();
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
	 * 获取企业互生号
	 * @return entResNo 企业互生号
	 */
	public String getEntResNo()
	{
		return entResNo;
	}

	/**
	 * 设置企业互生号
	 * @param entResNo 企业互生号
	 */
	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	/**
	 * 获取设备编号
	 * @return equipmentNo 设备编号
	 */
	public String getEquipmentNo()
	{
		return equipmentNo;
	}

	/**
	 * 设置设备编号
	 * @param equipmentNo 设备编号
	 */
	public void setEquipmentNo(String equipmentNo)
	{
		this.equipmentNo = equipmentNo;
	}

	/**
	 * 获取渠道类型
	 * @return channelType 渠道类型
	 */
	public Integer getChannelType()
	{
		return channelType;
	}

	/**
	 * 设置渠道类型
	 * @param channelType 渠道类型
	 */
	public void setChannelType(Integer channelType)
	{
		this.channelType = channelType;
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
	 * 获取原批次号
	 * @return sourceBatchNo 原批次号
	 */
	public String getSourceBatchNo()
	{
		return sourceBatchNo;
	}

	/**
	 * 设置原批次号
	 * @param sourceBatchNo 原批次号
	 */
	public void setSourceBatchNo(String sourceBatchNo)
	{
		this.sourceBatchNo = sourceBatchNo;
	}

	/**
	 * 获取冲正原因
	 * @return returnReason 冲正原因
	 */
	public String getReturnReason()
	{
		return returnReason;
	}

	/**
	 * 设置冲正原因
	 * @param returnReason 冲正原因
	 */
	public void setReturnReason(String returnReason)
	{
		this.returnReason = returnReason;
	}

	/**
	 * 获取终端流水号(POS)
	 * @return termRunCode 终端流水号(POS)
	 */
	public String getTermRunCode()
	{
		return termRunCode;
	}

	/**
	 * 设置终端流水号(POS)
	 * @param termRunCode 终端流水号(POS)
	 */
	public void setTermRunCode(String termRunCode)
	{
		this.termRunCode = termRunCode;
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
}
