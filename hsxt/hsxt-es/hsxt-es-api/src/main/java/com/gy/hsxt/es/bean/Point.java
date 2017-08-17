package com.gy.hsxt.es.bean;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Package: com.gy.hsxt.es.bean
 * @ClassName: Point
 * @Description: 消费积分接口参数对象
 * 
 * @author: chenhongzhi
 * @date: 2015-10-13 上午9:48:38
 * @version V3.0
 */
public class Point implements Serializable
{

	/**
     *
     */
	private static final long serialVersionUID = 74906902096582029L;

	/*** 交易类型 */
	@NotBlank(message = "交易类型不能为空")
	@Size(min = 6, max = 6, message = "交易类型最大长度是6位！")
	@Pattern(regexp = "^[A-Z][1-2][1-3][0-9][0-9]0$", message = "交易类型错误")
	private String transType;

	/*** 企业互生号 */
	@NotBlank(message = "企业互生号不能为空")
	@Size(min = 11, max = 11, message = "企业互生号必须11位！")
	private String entResNo;

	/*** 消费者互生号 */
//	@Size(min = 11, max = 11, message = "消费者互生号必须11位！")
	private String perResNo;

	/*** 设备编号 */
	private String equipmentNo;

	/*** 渠道类型 */
	@NotNull(message = "渠道类型不能为空")
	private Integer channelType;

	/*** 设备类型 */
	private Integer equipmentType;

	/*** 原始交易号 */
	@NotBlank(message = "交易流水号不能为空")
	private String sourceTransNo;
	
	/** 消费积分原始交易流水号*/
	private String oldTransNo;

	/*** 原批次号 */
	private String sourceBatchNo;

	/*** 原始交易时间 */
	@NotBlank(message = "交易时间不能为空")
	private String sourceTransDate;

	/*** 原始交易币种代号 */
	@NotBlank(message = "交易币种代号不能为空")
	@Size(min = 3, max = 3, message = "交易币种代号3位！")
	private String sourceCurrencyCode;

	/*** 原始币种金额 */
	@NotBlank(message = "原始币种金额不能为空")
//	@Min(value = 1, message = "最少值为1")
	private String sourceTransAmount;

	/*** 交易金额 */
	@NotBlank(message = "交易金额不能为空")
//	@Min(value = 1, message = "最少值为1")
	private String transAmount;

	/*** 积分比例 */
	private String pointRate;

	/*** 企业积分款 */
	@NotBlank(message = "积分款不能为空")
	private String entPoint;

	/*** 企业客户号 */
	@NotBlank(message = "企业客户号不能为空")
	private String entCustId;

	/*** 消费者客户号 */
	@NotBlank(message = "消费者客户号不能为空")
	private String perCustId;

	/*** 操作员 */
	private String operNo;

	/** 是否扣商业服务费  0 是(收费) 1 否(不收费)**/
	@NotNull(message = "是否扣费不能为空")
	private Integer isDeduction;

	/**订单号*/
	@NotBlank(message = "订单号不能为空")
	private  String orderNo;
	
	/**订单类型*/
//    @NotBlank(message = "订单类型不能为空")
	private String orderType;
	
	/** 订单完成时间 */
	private String orderFinishDate;
	
	/** 订单金额 */
	private String orderAmount;

	/*货币转换率 */
	private String  currencyRate;

	/**企业名称 */
	@NotBlank(message = "企业名称不能为空")
	private  String entName;

	/**店铺名称 */
	private  String mallName;

	/*备注 */
	private  String remark;

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
	 * 获取设备编号
	 * 
	 * @return equipmentNo 设备编号
	 */
	public String getEquipmentNo()
	{
		return equipmentNo;
	}

	/**
	 * 设置设备编号
	 * 
	 * @param equipmentNo
	 *            设备编号
	 */
	public void setEquipmentNo(String equipmentNo)
	{
		this.equipmentNo = equipmentNo;
	}

	/**
	 * 获取渠道类型
	 * 
	 * @return channelType 渠道类型
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
	 * 获取设备类型
	 * 
	 * @return equipmentType 设备类型
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
	 * 获取原始交易号
	 * 
	 * @return sourceTransNo 原始交易号
	 */
	public String getSourceTransNo()
	{
		return sourceTransNo;
	}

	/**
	 * 设置原始交易号
	 * 
	 * @param sourceTransNo
	 *            原始交易号
	 */
	public void setSourceTransNo(String sourceTransNo)
	{
		this.sourceTransNo = sourceTransNo;
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
	 * 获取原始交易时间
	 * 
	 * @return sourceTransDate 原始交易时间
	 */
	public String getSourceTransDate()
	{
		return sourceTransDate;
	}

	/**
	 * 设置原始交易时间
	 * 
	 * @param sourceTransDate
	 *            原始交易时间
	 */
	public void setSourceTransDate(String sourceTransDate)
	{
		this.sourceTransDate = sourceTransDate;
	}

	/**
	 * 获取原始交易币种代号
	 * 
	 * @return sourceCurrencyCode 原始交易币种代号
	 */
	public String getSourceCurrencyCode()
	{
		return sourceCurrencyCode;
	}

	/**
	 * 设置原始交易币种代号
	 * 
	 * @param sourceCurrencyCode
	 *            原始交易币种代号
	 */
	public void setSourceCurrencyCode(String sourceCurrencyCode)
	{
		this.sourceCurrencyCode = sourceCurrencyCode;
	}

	/**
	 * 获取原始币种金额
	 * 
	 * @return sourceTransAmount 原始币种金额
	 */
	public String getSourceTransAmount()
	{
		return sourceTransAmount;
	}

	/**
	 * 设置原始币种金额
	 * 
	 * @param sourceTransAmount
	 *            原始币种金额
	 */
	public void setSourceTransAmount(String sourceTransAmount)
	{
		this.sourceTransAmount = sourceTransAmount;
	}

	/**
	 * 获取交易金额
	 * 
	 * @return transAmount 交易金额
	 */
	public String getTransAmount()
	{
		return transAmount;
	}

	/**
	 * 设置交易金额
	 * 
	 * @param transAmount
	 *            交易金额
	 */
	public void setTransAmount(String transAmount)
	{
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
	 * 获取操作员
	 * 
	 * @return operNo 操作员
	 */
	public String getOperNo()
	{
		return operNo;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operNo
	 *            操作员
	 */
	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	/**
	 * 获取企业积分款
	 * @return entPoint 企业积分款
	 */
	public String getEntPoint()
	{
		return entPoint;
	}

	/**
	 * 设置企业积分款
	 * @param entPoint 企业积分款
	 */
	public void setEntPoint(String entPoint)
	{
		this.entPoint = entPoint;
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
	 * 获取消费积分原始交易流水号
	 * @return oldTransNo 消费积分原始交易流水号
	 */
	public String getOldTransNo()
	{
		return oldTransNo;
	}

	/**
	 * 设置消费积分原始交易流水号
	 * @param oldTransNo 消费积分原始交易流水号
	 */
	public void setOldTransNo(String oldTransNo)
	{
		this.oldTransNo = oldTransNo;
	}

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

    /**
     * @return the 订单类型
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType 订单类型 the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
	/**
     * @return the 订单完成时间
     */
    public String getOrderFinishDate() {
        return orderFinishDate;
    }

    /**
     * @param 订单完成时间 the orderFinishDate to set
     */
    public void setOrderFinishDate(String orderFinishDate) {
        this.orderFinishDate = orderFinishDate;
    }

    /**
     * @return the 订单金额
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     * @param 订单金额 the orderAmount to set
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
	public String toString() {
		return "Point{" +
				"transType='" + transType + '\'' +
				", sourceTransNo='" + sourceTransNo + '\'' +
				", channelType=" + channelType +
				", sourceCurrencyCode='" + sourceCurrencyCode + '\'' +
				", sourceTransAmount='" + sourceTransAmount + '\'' +
				", transAmount='" + transAmount + '\'' +
				", entPoint='" + entPoint + '\'' +
				", perCustId='" + perCustId + '\'' +
				", entCustId='" + entCustId + '\'' +
				", orderNo='" + orderNo + '\'' +
				'}';
	}
}
