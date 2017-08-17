package com.gy.hsxt.es.points.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @description 积分明细实体类
 * @author chenhz
 * @createDate 2015-7-29 上午9:18:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class PointDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8833751423298641409L;
    /** 交易类型*/
    private String transType;
    /** 企业互生号*/
    private String entResNo;
    /** 消费者互生号*/
    private String perResNo;
    /** 设备编号*/
    private String equipmentNo;
    /** 渠道类型*/
    private Integer channelType;
    /** 设备类型*/
    private Integer equipmentType;
    /** 原始交易号*/
    private String sourceTransNo;
	/** 退货交易号*/
	private String backTransNo;
    /** 原批次号  */
    private String sourceBatchNo;
    /** 原始交易时间*/
    private Timestamp sourceTransDate;
    /** 原始交易币种代号*/
    private String sourceCurrencyCode;
    /** 原始币种金额*/
    private BigDecimal sourceTransAmount;
    /** 原始币种金额转换后金额*/
    private BigDecimal targetTransAmount;
    /** 交易金额*/
    private BigDecimal transAmount;
    /** 积分比例*/
    private BigDecimal pointRate;
    /** 消费积分 */
    private BigDecimal entPoint;
    /** 企业客户号*/
    private String entCustId;
    /** 消费者客户号*/
    private String perCustId;
    /** 操作员*/
    private String operNo;
	/** 交易流水号 */
	private String transNo;
	/** 货币转换率 */
	private BigDecimal currencyRate;
	/** 消费者积分 */
	private BigDecimal perPoint;
	/** 业务状态 */
	private Integer status;
	/** 订单状态 */
	private Integer transStatus;
	/** 是否结算 */
	private Integer isSettle;
	/** 此记录状态 */
	private String isActive;
	/** 创建时间 */
	private Timestamp createdDate;
	/** 创建者 */
	private String createdBy;
	/** 修改时间 */
	private Timestamp updatedDate;
	/** 修改者 */
	private String updatedBy;
	/** 批次号  */
	private String batchNo;
	
	/** 是否扣商业服务费  0 是(收费) 1 否(不收费)**/
	private Integer isDeduction;
	
	/** 是否网上积分登记  0 是 1 否**/
	private Integer isOnlineRegister;

	/**企业名称 */
	private  String entName;

	/**店铺名称 */
	private  String mallName;

	/*备注 */
	private  String remark;

	/** 业务类型**/
	private Integer businessType;

	/**订单号*/
	private  String orderNo;
	
	/** 订单类型 */
	private String orderType;
	
	/** 订单完成时间 */
    private Timestamp orderFinishDate;
    
    /** 订单金额 */
    private String orderAmount;
	
    /*** 获取交易类型
     * @return transType 交易类型
     */
    public String getTransType() {
        return transType;
    }
    /**
     * 设置交易类型
     * @param transType 交易类型
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }
    /**
     * 获取企业互生号
     * @return entResNo 企业互生号
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
     * @return perResNo 消费者互生号
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
     * 获取设备编号
     * @return equipmentNo 设备编号
     */
    public String getEquipmentNo() {
        return equipmentNo;
    }
    /**
     * 设置设备编号
     * @param equipmentNo 设备编号
     */
    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
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
     * 获取设备类型
     * @return equipmentType 设备类型
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
     * 获取原始交易号
     * @return sourceTransNo 原始交易号
     */
    public String getSourceTransNo() {
        return sourceTransNo;
    }
    /**
     * 设置原始交易号
     * @param sourceTransNo 原始交易号
     */
    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

	public String getBackTransNo() {
		return backTransNo;
	}

	public void setBackTransNo(String backTransNo) {
		this.backTransNo = backTransNo;
	}

	/**
     * 获取原批次号
     * @return String sourceBatchNo
     */
    public String getSourceBatchNo() {
        return sourceBatchNo;
    }
    /**
     * 设置原批次号
     * @param sourceBatchNo 原批次号
     */
    public void setSourceBatchNo(String sourceBatchNo) {
        this.sourceBatchNo = sourceBatchNo;
    }
    /**
     * 获取原始交易时间
     * @return sourceTransDate 原始交易时间
     */
    public Timestamp getSourceTransDate() {
        return sourceTransDate;
    }
    /**
     * 设置原始交易时间
     * @param sourceTransDate 原始交易时间
     */
    public void setSourceTransDate(Timestamp sourceTransDate) {
        this.sourceTransDate = sourceTransDate;
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
     * @return the 原始币种金额转换后金额
     */
    public BigDecimal getTargetTransAmount() {
        return targetTransAmount;
    }
    /**
     * @param 原始币种金额转换后金额 the targetTransAmount to set
     */
    public void setTargetTransAmount(BigDecimal targetTransAmount) {
        this.targetTransAmount = targetTransAmount;
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
     * 获取积分比例
     * @return pointRate 积分比例
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
     * 获取企业客户号
     * @return entCustId 企业客户号
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
     * @return perCustId 消费者客户号
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
     * 获取操作员
     * @return operNo 操作员
     */
    public String getOperNo() {
        return operNo;
    }
    /**
     * 设置操作员
     * @param operNo 操作员
     */
    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }
  
	/**
	 * 获取交易流水号
	 * @return String transNo
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * 设置交易流水号
	 * @param transNo 交易流水号
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
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
	 * 获取此记录状态
	 * @return String isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	/**
	 * 设置此记录状态
	 * @param isActive 此记录状态
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	/**
	 * 获取创建时间
	 * @return Date createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	/**
	 * 设置创建时间
	 * @param createdDate 创建时间
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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
	 * 获取修改时间
	 * @return Date updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * 设置修改时间
	 * @param updatedDate 修改时间
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
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
	/**
	 * @return the 企业应付积分款
	 */
	public BigDecimal getEntPoint()
	{
		return entPoint;
	}
	/**
	 * @param 企业应付积分款 the entPoint to set
	 */
	public void setEntPoint(BigDecimal entPoint)
	{
		this.entPoint = entPoint;
	}
	/**
	 * 获取是否扣商业服务费0是(收费)1否(不收费)
	 * @return isDeduction 是否扣商业服务费0是(收费)1否(不收费)
	 */
	public Integer getIsDeduction()
	{
		return isDeduction;
	}
	/**
	 * 设置是否扣商业服务费0是(收费)1否(不收费)
	 * @param isDeduction 是否扣商业服务费0是(收费)1否(不收费)
	 */
	public void setIsDeduction(Integer isDeduction)
	{
		this.isDeduction = isDeduction;
	}
	/**
	 * 获取是否网上积分登记0是1否
	 * @return isOnlineRegister 是否网上积分登记0是1否
	 */
	public Integer getIsOnlineRegister()
	{
		return isOnlineRegister;
	}
	/**
	 * 设置是否网上积分登记0是1否
	 * @param isOnlineRegister 是否网上积分登记0是1否
	 */
	public void setIsOnlineRegister(Integer isOnlineRegister)
	{
		this.isOnlineRegister = isOnlineRegister;
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
     * @return the 订单类型
     */
    public String getOrderType() {
        return orderType;
    }
    /**
     * @param 订单类型 the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    /**
     * @return the 订单完成时间
     */
    public Timestamp getOrderFinishDate() {
        return orderFinishDate;
    }
    /**
     * @param 订单完成时间 the orderFinishDate to set
     */
    public void setOrderFinishDate(Timestamp orderFinishDate) {
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

}
