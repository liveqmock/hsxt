package com.gy.hsxt.es.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 查询单笔交易返回结果实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v3.0
 */
public class QueryResult extends Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005537682483642817L;
	/** 交易流水号 */
	private String transNo;
	/** 交易类型 */
	private String transType;
	/** 企业互生号*/
	private String entResNo;
	/** 消费者互生号 */
	private String perResNo;
	/** 企业客户号*/
	private String entCustId;
	/** 原始交易号 */
	private String sourceTransNo;
	/** 批次号 */
	private String batchNo;
	/** 原批次号  */
	private String sourceBatchNo;
	/** 原始交易时间 */
	private String sourceTransDate;
	/** 原始币种金额 */
	private String sourceTransAmount;
	/** 积分比例 */
	private String pointRate;
	/** 积分预付款 */
	private String entPoint;
	/** 交易金额 */
	private String transAmount;
	/** 消费者积分 */
	private String perPoint;
	/** 原分配积分数 */
	private String sourcePoint;
	/** 原始交易流水号 */
	private String oldTransNo;
	/*** 终端流水号*/
	private String termRunCode;
	/*** 终端类型码 */
	private String termTypeCode;
	/*** 终端交易码 */
	private String termTradeCode;
	/*** 冲正原因码 */
	private String returnReason;
	/*** 设备编号 */
	private String equipmentNo;
	/*** 操作员编号*/
	private String operNo;

	/**企业名称 */
	private  String entName;

	/**店铺名称 */
	private  String mallName;

	/*备注 */
	private  String remark;

	/*货币转换率 */
	private String  currencyRate;

	/**
	 * 获取交易流水号
	 * @return transNo 交易流水号
	 */
	public String getTransNo()
	{
		return transNo;
	}
	/**
	 * 设置交易流水号
	 * @param transNo 交易流水号
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
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
	 * 获取原始交易号
	 * @return sourceTransNo 原始交易号
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}
	/**
	 * 设置原始交易号
	 * @param sourceTransNo 原始交易号
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
	 * 获取原始交易时间
	 * @return sourceTransDate 原始交易时间
	 */
	public String getSourceTransDate()
	{
		return sourceTransDate;
	}
	/**
	 * 设置原始交易时间
	 * @param sourceTransDate 原始交易时间
	 */
	public void setSourceTransDate(String sourceTransDate)
	{
		this.sourceTransDate = sourceTransDate;
	}
	/**
	 * 获取原始币种金额
	 * @return sourceTransAmount 原始币种金额
	 */
	public String getSourceTransAmount()
	{
		return toBigString(sourceTransAmount);
	}
	/**
	 * 设置原始币种金额
	 * @param sourceTransAmount 原始币种金额
	 */
	public void setSourceTransAmount(String sourceTransAmount)
	{
		this.sourceTransAmount = sourceTransAmount;
	}
	/**
	 * 获取积分比例
	 * @return pointRate 积分比例
	 */
	public String getPointRate()
	{
		return pointRate;
	}
	/**
	 * 设置积分比例
	 * @param pointRate 积分比例
	 */
	public void setPointRate(String pointRate)
	{
		this.pointRate = pointRate;
	}
	/**
	 * 获取积分预付款
	 * @return entPoint 积分预付款
	 */

	public String getEntPoint()
	{
		return toBigString(entPoint);
	}
	/**
	 * 设置积分预付款
	 * @param entPoint 积分预付款
	 */
	public void setEntPoint(String entPoint)
	{
		this.entPoint = entPoint;
	}
	/**
	 * 获取交易金额
	 * @return transAmount 交易金额
	 */
	public String getTransAmount()
	{
		return toBigString(transAmount);
	}
	/**
	 * 设置交易金额
	 * @param transAmount 交易金额
	 */
	public void setTransAmount(String transAmount)
	{
		this.transAmount = transAmount;
	}
	/**
	 * 获取消费者积分
	 * @return perPoint 消费者积分
	 */
	public String getPerPoint()
	{
		return toBigString(perPoint);
	}
	/**
	 * 设置消费者积分
	 * @param perPoint 消费者积分
	 */
	public void setPerPoint(String perPoint)
	{
		this.perPoint = perPoint;
	}
	/**
	 * 获取原分配积分数
	 * @return sourcePoint 原分配积分数
	 */
	public String getSourcePoint()
	{
		return toBigString(sourcePoint);
	}
	/**
	 * 设置原分配积分数
	 * @param sourcePoint 原分配积分数
	 */
	public void setSourcePoint(String sourcePoint)
	{
		this.sourcePoint = sourcePoint;
	}
	/**
	 * 获取原始交易流水号
	 * @return oldTransNo 原始交易流水号
	 */
	public String getOldTransNo()
	{
		return oldTransNo;
	}
	/**
	 * 设置原始交易流水号
	 * @param oldTransNo 原始交易流水号
	 */
	public void setOldTransNo(String oldTransNo)
	{
		this.oldTransNo = oldTransNo;
	}
	/**
	 * 获取终端流水号
	 * @return termRunCode 终端流水号
	 */
	public String getTermRunCode()
	{
		return termRunCode;
	}
	/**
	 * 设置终端流水号
	 * @param termRunCode 终端流水号
	 */
	public void setTermRunCode(String termRunCode)
	{
		this.termRunCode = termRunCode;
	}
	/**
	 * 获取终端类型码
	 * @return termTypeCode 终端类型码
	 */
	public String getTermTypeCode()
	{
		return termTypeCode;
	}
	/**
	 * 设置终端类型码
	 * @param termTypeCode 终端类型码
	 */
	public void setTermTypeCode(String termTypeCode)
	{
		this.termTypeCode = termTypeCode;
	}
	/**
	 * 获取终端交易码
	 * @return termTradeCode 终端交易码
	 */
	public String getTermTradeCode()
	{
		return termTradeCode;
	}
	/**
	 * 设置终端交易码
	 * @param termTradeCode 终端交易码
	 */
	public void setTermTradeCode(String termTradeCode)
	{
		this.termTradeCode = termTradeCode;
	}
	/**
	 * 获取冲正原因码
	 * @return returnReason 冲正原因码
	 */
	public String getReturnReason()
	{
		return returnReason;
	}
	/**
	 * 设置冲正原因码
	 * @param returnReason 冲正原因码
	 */
	public void setReturnReason(String returnReason)
	{
		this.returnReason = returnReason;
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
	 * 获取操作员编号
	 * @return operNo 操作员编号
	 */
	public String getOperNo()
	{
		return operNo;
	}
	/**
	 * 设置操作员编号
	 * @param operNo 操作员编号
	 */
	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}


	private     String  toBigString(String value){
		if (StringUtils.isNotEmpty(value)){
		return (new BigDecimal(value)).toEngineeringString();
		}
		return value;
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

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}
}
