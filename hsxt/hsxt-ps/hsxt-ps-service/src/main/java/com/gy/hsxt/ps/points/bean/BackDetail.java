package com.gy.hsxt.ps.points.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @description 退货明细实体类
 * @author chenhz
 * @createDate 2015-7-29 上午9:18:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class BackDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5634086535482266257L;
	
    /** 原交易流水号 */
	private String oldTransNo;
	/** 交易类型 */
	private String transType;
	/** 设备编号 */
	private String equipmentNo;
	/** 原始交易号 */
	private String sourceTransNo;
	/** 原批次号  */
	private String sourceBatchNo;
	/** 原始交易时间 */
	private Timestamp sourceTransDate;
	/** 原始币种金额 */
	private BigDecimal sourceTransAmount;
	/** 交易金额 */
	private BigDecimal transAmount;
	/** 退货积分 */
	private BigDecimal backPoint;
	/** 操作员 */
	private String operNo;
	/** 原原始交易号 */
	private String oldSourceTransNo;
	/** 订单交易流水号 */
	private String transNo;
	/** 企业互生号 */
	private String entResNo;
	/** 消费者互生号 */
	private String perResNo;
	/** 渠道类型 */
	private Integer channelType;
	/** 设备类型 */
	private Integer equipmentType;
	/** 原始交易币种代号 */
	private String sourceCurrencyCode;
	/** 货币转换率 */
	private BigDecimal currencyRate;
	/** 积分比例 */
	private BigDecimal pointRate;
	/** 原交易金额 */
	private BigDecimal oldTransAmount;
	/** 原消费者的积分 */
	private BigDecimal oldPerPoint;
	/** 原积分预付款 */
	private BigDecimal oldEntPoint;
	/** 退货积分 */
	private BigDecimal perPoint;
	/** 退货积分预付款 */
	private BigDecimal entPoint;
	/** 订单状态 */
	private Integer transStatus;
	/** 业务状态 */
	private Integer status;
	/** 是否结算 */
	private Integer isSettle;
	/** 企业客户号 */
	private String entCustId;
	/** 消费者客户号 */
	private String perCustId;
	/** 此记录的状态 */
	private String isActive;
	/** 创建时间 */
	private Timestamp createdDate;
	/** 创建者 */
	private String createdBy;
	/** 批次号  */
	private String batchNo;
	
	/** 终端流水号(POS) */
	private String termRunCode;
	/** 终端类型码(POS) */
	private String termTypeCode;
	/** 终端交易码(POS) */
	private String termTradeCode;

	/**企业名称 */
	private  String entName;

	/**店铺名称 */
	private  String mallName;

	/*备注 */
	private  String remark;
	/**
	 * 获取创建时间
	 * @return Timestamp createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate==null?null:(Timestamp) createdDate.clone();
	}
	/**
	 * 设置创建时间
	 * @param createdDate 创建时间
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate==null?null:(Timestamp) createdDate.clone();
	}
	/**
	 * 获取修改时间
	 * @return Timestamp updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate==null?null:(Timestamp)updatedDate.clone();
	}
	/**
	 * 设置修改时间
	 * @param updatedDate 修改时间
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate==null?null:(Timestamp) updatedDate.clone();
	}
	/** 修改时间 */
	private Timestamp updatedDate;
	/** 修改者 */
	private String updatedBy;
	/**
	 * 获取订单交易流水号
	 * @return String transNo
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * 设置订单交易流水号
	 * @param transNo 订单交易流水号
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	/**
	 * 获取企业互生号
	 * @return String entResNo
	 */
	public String getEntResNo() {
		return entResNo;
	}
	/**
	 * 设置企业互生号
	 * @param entResNo 企业互生号
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}
	/**
	 * 获取消费者互生号
	 * @return String perResNo
	 */
	public String getPerResNo() {
		return perResNo;
	}
	/**
	 * 设置消费者互生号
	 * @param perResNo 消费者互生号
	 */
	public void setPerResNo(String perResNo) {
		this.perResNo = perResNo;
	}
	/**
	 * 获取渠道类型
	 * @return Integer channelType
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
	 * 获取设备类型
	 * @return Integer equipmentType
	 */
	public Integer getEquipmentType() {
		return equipmentType;
	}
	/**
	 * 设置设备类型
	 * @param equipmentType 设备类型
	 */
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	/**
	 * 获取原始交易币种代号
	 * @return String sourceCurrencyCode
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
	 * 获取积分比例
	 * @return BigDecimal pointRate
	 */
	public BigDecimal getPointRate() {
		return pointRate;
	}
	/**
	 * 设置积分比例
	 * @param pointRate 积分比例
	 */
	public void setPointRate(BigDecimal pointRate) {
		this.pointRate = pointRate;
	}
	/**
	 * 获取原交易金额
	 * @return BigDecimal oldTransAmount
	 */
	public BigDecimal getOldTransAmount() {
		return oldTransAmount;
	}
	/**
	 * 设置原交易金额
	 * @param oldTransAmount 原交易金额
	 */
	public void setOldTransAmount(BigDecimal oldTransAmount) {
		this.oldTransAmount = oldTransAmount;
	}
	/**
	 * 获取原消费者的积分
	 * @return BigDecimal oldPerPoint
	 */
	public BigDecimal getOldPerPoint() {
		return oldPerPoint;
	}
	/**
	 * 设置原消费者的积分
	 * @param oldPerPoint 原消费者的积分
	 */
	public void setOldPerPoint(BigDecimal oldPerPoint) {
		this.oldPerPoint = oldPerPoint;
	}
	/**
	 * 获取原积分预付款
	 * @return BigDecimal oldEntPoint
	 */
	public BigDecimal getOldEntPoint() {
		return oldEntPoint;
	}
	/**
	 * 设置原积分预付款
	 * @param oldEntPoint 原积分预付款
	 */
	public void setOldEntPoint(BigDecimal oldEntPoint) {
		this.oldEntPoint = oldEntPoint;
	}
	/**
	 * 获取退货积分
	 * @return BigDecimal perPoint
	 */
	public BigDecimal getPerPoint() {
		return perPoint;
	}
	/**
	 * 设置退货积分
	 * @param perPoint 退货积分
	 */
	public void setPerPoint(BigDecimal perPoint) {
		this.perPoint = perPoint;
	}
	/**
	 * 获取退货积分预付款
	 * @return BigDecimal entPoint
	 */
	public BigDecimal getEntPoint() {
		return entPoint;
	}
	/**
	 * 设置退货积分预付款
	 * @param entPoint 退货积分预付款
	 */
	public void setEntPoint(BigDecimal entPoint) {
		this.entPoint = entPoint;
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
	/**
	 * 获取业务状态
	 * @return Integer status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置业务状态
	 * @param status 业务状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	/**
	 * 获取企业客户号
	 * @return String entCustId
	 */
	public String getEntCustId() {
		return entCustId;
	}
	/**
	 * 设置企业客户号
	 * @param entCustId 企业客户号
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}
	/**
	 * 获取消费者客户号
	 * @return String perCustId
	 */
	public String getPerCustId() {
		return perCustId;
	}
	/**
	 * 设置消费者客户号
	 * @param perCustId 消费者客户号
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}
	/**
	 * 获取此记录的状态
	 * @return String isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	/**
	 * 设置此记录的状态
	 * @param isActive 此记录的状态
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * 获取创建者
	 * @return String createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * 设置创建者
	 * @param createdBy 创建者
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 获取修改者
	 * @return String updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * 设置修改者
	 * @param updatedBy 修改者
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * 获取批次号
	 * @return String batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * 设置批次号
	 * @param batchNo 批次号
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTermTypeCode() {
		return termTypeCode;
	}

	public void setTermTypeCode(String termTypeCode) {
		this.termTypeCode = termTypeCode;
	}

	public String getTermTradeCode() {
		return termTradeCode;
	}

	public void setTermTradeCode(String termTradeCode) {
		this.termTradeCode = termTradeCode;
	}
	/**
	 * 获取原交易流水号
	 * @return oldTransNo 原交易流水号
	 */
	public String getOldTransNo()
	{
		return oldTransNo;
	}
	/**
	 * 设置原交易流水号
	 * @param oldTransNo 原交易流水号
	 */
	public void setOldTransNo(String oldTransNo)
	{
		this.oldTransNo = oldTransNo;
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
	public Timestamp getSourceTransDate()
	{
		return sourceTransDate==null?null:(Timestamp) sourceTransDate.clone();
	}
	/**
	 * 设置原始交易时间
	 * @param sourceTransDate 原始交易时间
	 */
	public void setSourceTransDate(Timestamp sourceTransDate)
	{
		this.sourceTransDate = sourceTransDate==null?null:(Timestamp) sourceTransDate.clone();
	}
	/**
	 * 获取原始币种金额
	 * @return sourceTransAmount 原始币种金额
	 */
	public BigDecimal getSourceTransAmount()
	{
		return sourceTransAmount;
	}
	/**
	 * 设置原始币种金额
	 * @param sourceTransAmount 原始币种金额
	 */
	public void setSourceTransAmount(BigDecimal sourceTransAmount)
	{
		this.sourceTransAmount = sourceTransAmount;
	}
	/**
	 * 获取交易金额
	 * @return transAmount 交易金额
	 */
	public BigDecimal getTransAmount()
	{
		return transAmount;
	}
	/**
	 * 设置交易金额
	 * @param transAmount 交易金额
	 */
	public void setTransAmount(BigDecimal transAmount)
	{
		this.transAmount = transAmount;
	}
	/**
	 * 获取退货积分
	 * @return backPoint 退货积分
	 */
	public BigDecimal getBackPoint()
	{
		return backPoint;
	}
	/**
	 * 设置退货积分
	 * @param backPoint 退货积分
	 */
	public void setBackPoint(BigDecimal backPoint)
	{
		this.backPoint = backPoint;
	}
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

	/**
	 * 获取原始交易号
	 * @return oldSourceTransNo 原始交易号
	 */
	public String getOldSourceTransNo() {
		return oldSourceTransNo;
	}
	/**
	 * 设置原始交易号
	 * @param oldSourceTransNo 原始交易号
	 */
	public void setOldSourceTransNo(String oldSourceTransNo) {
		this.oldSourceTransNo = oldSourceTransNo;
	}
}
