package com.gy.hsxt.ps.points.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author chenhz
 * @version v0.0.1
 * @description 积分明细实体类
 * @createDate 2015-7-29 上午9:18:53
 * @Company 深圳市归一科技研发有限公司
 */
public class PointDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8833751423298641409L;
    /**
     * 交易类型
     */
    private String transType;
    /**
     * 企业互生号
     */
    private String entResNo;
    /**
     * 消费者互生号
     */
    private String perResNo;
    /**
     * 设备编号
     */
    private String equipmentNo;
    /**
     * 渠道类型
     */
    private Integer channelType;
    /**
     * 设备类型
     */
    private Integer equipmentType;
    /**
     * 原始交易号
     */
    private String sourceTransNo;
    /**
     * 原批次号
     */
    private String sourceBatchNo;
    /**
     * 原始交易时间
     */
    private Timestamp sourceTransDate;
    /**
     * 原始交易币种代号
     */
    private String sourceCurrencyCode;
    /**
     * 原始币种金额
     */
    private BigDecimal sourceTransAmount;
    /**
     * 原始币种金额转换后金额
     */
    private BigDecimal targetTransAmount;
    /***
     * 消费金额
     */
    private BigDecimal orderAmount;

    /***
     * 抵扣券数量
     */
    private int deductionVoucher;
    /**
     * 交易金额
     */
    private BigDecimal transAmount;
    /**
     * 积分比例
     */
    private BigDecimal pointRate;
    /**
     * 消费积分
     */
    private BigDecimal pointSum;

    /**
     * 企业客户号
     */
    private String entCustId;
    /**
     * 消费者客户号
     */
    private String perCustId;
    /**
     * 操作员
     */
    private String operNo;
    /**
     * 交易流水号
     */
    private String transNo;
    /**
     * 货币转换率
     */
    private BigDecimal currencyRate;
    /**
     * 消费者积分
     */
    private BigDecimal perPoint;
    /**
     * 企业应付积分款
     */
    private BigDecimal entPoint;
    /**
     * 业务状态
     */
    private Integer status;
    /**
     * 订单状态
     */
    private Integer transStatus;
    /**
     * 是否结算
     */
    private Integer isSettle;
    /**
     * 此记录状态
     */
    private String isActive;
    /**
     * 创建时间
     */
    private Timestamp createdDate;
    /**
     * 创建者
     */
    private String createdBy;
    /**
     * 修改时间
     */
    private Timestamp updatedDate;
    /**
     * 修改者
     */
    private String updatedBy;
    /**
     * 批次号
     */
    private String batchNo;

    /***
     * 终端流水号
     */
    private String termRunCode;
    /***
     * 终端类型码
     */
    private String termTypeCode;
    /***
     * 终端交易码
     */
    private String termTradeCode;
    /**
     * 是否扣商业服务费  0 是(收费) 1 否(不收费)
     **/
    private Integer isDeduction;
    /**
     * 是否网上积分登记  0 是 1 否
     **/
    private Integer isOnlineRegister;
    /**
     * 业务类型
     **/
    private Integer businessType;
    /***
     * pos调用ps次数
     */
    private Integer posToPsCount;
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 店铺名称
     */
    private String mallName;
    /***
     * 单据码生成时间
     */
    private Timestamp sourcePosDate;
    /**
     * 支付状态  0未支付 1已支付 2非支付类业务
     */
    private Integer payStatus;

    /*备注 */
    private String remark;

    /***
     * 获取交易类型
     *
     * @return transType 交易类型
     */
    public String getTransType() {
        return transType;
    }

    /**
     * 设置交易类型
     *
     * @param transType 交易类型
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * 获取企业互生号
     *
     * @return entResNo 企业互生号
     */
    public String getEntResNo() {
        return entResNo;
    }

    /**
     * 设置企业互生号
     *
     * @param entResNo 企业互生号
     */
    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    /**
     * 获取消费者互生号
     *
     * @return perResNo 消费者互生号
     */
    public String getPerResNo() {
        return perResNo;
    }

    /**
     * 设置消费者互生号
     *
     * @param perResNo 消费者互生号
     */
    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    /**
     * 获取设备编号
     *
     * @return equipmentNo 设备编号
     */
    public String getEquipmentNo() {
        return equipmentNo;
    }

    /**
     * 设置设备编号
     *
     * @param equipmentNo 设备编号
     */
    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    /**
     * 获取渠道类型
     *
     * @return channelType 渠道类型
     */
    public Integer getChannelType() {
        return channelType;
    }

    /**
     * 设置渠道类型
     *
     * @param channelType 渠道类型
     */
    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    /**
     * 获取设备类型
     *
     * @return equipmentType 设备类型
     */
    public Integer getEquipmentType() {
        return equipmentType;
    }

    /**
     * 设置设备类型
     *
     * @param equipmentType 设备类型
     */
    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    /**
     * 获取原始交易号
     *
     * @return sourceTransNo 原始交易号
     */
    public String getSourceTransNo() {
        return sourceTransNo;
    }

    /**
     * 设置原始交易号
     *
     * @param sourceTransNo 原始交易号
     */
    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

    /**
     * 获取原批次号
     *
     * @return String sourceBatchNo
     */
    public String getSourceBatchNo() {
        return sourceBatchNo;
    }

    /**
     * 设置原批次号
     *
     * @param sourceBatchNo 原批次号
     */
    public void setSourceBatchNo(String sourceBatchNo) {
        this.sourceBatchNo = sourceBatchNo;
    }

    /**
     * 获取原始交易时间
     *
     * @return sourceTransDate 原始交易时间
     */
    public Timestamp getSourceTransDate() {
        return sourceTransDate == null ? null : (Timestamp) sourceTransDate.clone();
    }

    /**
     * 设置原始交易时间
     *
     * @param sourceTransDate 原始交易时间
     */
    public void setSourceTransDate(Timestamp sourceTransDate) {
        this.sourceTransDate = sourceTransDate == null ? null : (Timestamp) sourceTransDate.clone();
    }

    /**
     * 获取原始交易币种代号
     *
     * @return sourceCurrencyCode 原始交易币种代号
     */
    public String getSourceCurrencyCode() {
        return sourceCurrencyCode;
    }

    /**
     * 设置原始交易币种代号
     *
     * @param sourceCurrencyCode 原始交易币种代号
     */
    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    /**
     * 获取原始币种金额
     *
     * @return sourceTransAmount 原始币种金额
     */
    public BigDecimal getSourceTransAmount() {
        return sourceTransAmount;
    }

    /**
     * 设置原始币种金额
     *
     * @param sourceTransAmount 原始币种金额
     */
    public void setSourceTransAmount(BigDecimal sourceTransAmount) {
        this.sourceTransAmount = sourceTransAmount;
    }

    public BigDecimal getTargetTransAmount() {
        return targetTransAmount;
    }

    public void setTargetTransAmount(BigDecimal targetTransAmount) {
        this.targetTransAmount = targetTransAmount;
    }


    /**
     * 获取抵扣券金额
     *
     * @return rebateAmount 抵扣券金额
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置抵扣券金额
     *
     * @param orderAmount 抵扣券金额
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * 获取抵扣券张数
     *
     * @return deductionVoucher 抵扣券张数
     */
    public int getDeductionVoucher() {
        return deductionVoucher;
    }

    /**
     * 设置抵扣券张数
     *
     * @param deductionVoucher 抵扣券张数
     */
    public void setDeductionVoucher(int deductionVoucher) {
        this.deductionVoucher = deductionVoucher;
    }


    /**
     * 获取交易金额
     *
     * @return transAmount 交易金额
     */
    public BigDecimal getTransAmount() {
        return transAmount;
    }

    /**
     * 设置交易金额
     *
     * @param transAmount 交易金额
     */
    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * 获取积分比例
     *
     * @return pointRate 积分比例
     */
    public BigDecimal getPointRate() {
        return pointRate;
    }

    /**
     * 设置积分比例
     *
     * @param pointRate 积分比例
     */
    public void setPointRate(BigDecimal pointRate) {
        this.pointRate = pointRate;
    }

    /**
     * 获取企业客户号
     *
     * @return entCustId 企业客户号
     */
    public String getEntCustId() {
        return entCustId;
    }

    /**
     * 设置企业客户号
     *
     * @param entCustId 企业客户号
     */
    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    /**
     * 获取消费者客户号
     *
     * @return perCustId 消费者客户号
     */
    public String getPerCustId() {
        return perCustId;
    }

    /**
     * 设置消费者客户号
     *
     * @param perCustId 消费者客户号
     */
    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId;
    }

    /**
     * 获取操作员
     *
     * @return operNo 操作员
     */
    public String getOperNo() {
        return operNo;
    }

    /**
     * 设置操作员
     *
     * @param operNo 操作员
     */
    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    /**
     * 获取积分
     *
     * @return BigDecimal pointSum
     */
    public BigDecimal getPointSum() {
        return pointSum;
    }

    /**
     * 设置积分
     *
     * @param pointSum 积分
     */
    public void setPointSum(BigDecimal pointSum) {
        this.pointSum = pointSum;
    }

    /**
     * 获取交易流水号
     *
     * @return String transNo
     */
    public String getTransNo() {
        return transNo;
    }

    /**
     * 设置交易流水号
     *
     * @param transNo 交易流水号
     */
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    /**
     * 获取货币转换率
     *
     * @return BigDecimal currencyRate
     */
    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }

    /**
     * 设置货币转换率
     *
     * @param currencyRate 货币转换率
     */
    public void setCurrencyRate(BigDecimal currencyRate) {
        this.currencyRate = currencyRate;
    }

    /**
     * 获取消费者积分
     *
     * @return BigDecimal perPoint
     */
    public BigDecimal getPerPoint() {
        return perPoint;
    }

    /**
     * 设置消费者积分
     *
     * @param perPoint 消费者积分
     */
    public void setPerPoint(BigDecimal perPoint) {
        this.perPoint = perPoint;
    }

    /**
     * 获取业务状态
     *
     * @return Integer status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置业务状态
     *
     * @param status 业务状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取订单状态
     *
     * @return Integer transStatus
     */
    public Integer getTransStatus() {
        return transStatus;
    }

    /**
     * 设置订单状态
     *
     * @param transStatus 订单状态
     */
    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }

    /**
     * 获取是否结算
     *
     * @return Integer isSettle
     */
    public Integer getIsSettle() {
        return isSettle;
    }

    /**
     * 设置是否结算
     *
     * @param isSettle 是否结算
     */
    public void setIsSettle(Integer isSettle) {
        this.isSettle = isSettle;
    }

    /**
     * 获取此记录状态
     *
     * @return String isActive
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * 设置此记录状态
     *
     * @param isActive 此记录状态
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * 获取创建时间
     *
     * @return Date createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * 设置创建时间
     *
     * @param createdDate 创建时间
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * 获取创建者
     *
     * @return String createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建者
     *
     * @param createdBy 创建者
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取修改时间
     *
     * @return Date updatedDate
     */
    public Timestamp getUpdatedDate() {
        return updatedDate == null ? null : (Timestamp) updatedDate.clone();
    }

    /**
     * 设置修改时间
     *
     * @param updatedDate 修改时间
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate == null ? null : (Timestamp) updatedDate.clone();
    }

    /**
     * 获取修改者
     *
     * @return String updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设置修改者
     *
     * @param updatedBy 修改者
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获取批次号
     *
     * @return String batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 设置批次号
     *
     * @param batchNo 批次号
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the 企业应付积分款
     */
    public BigDecimal getEntPoint() {
        return entPoint;
    }

    /**
     * @param 企业应付积分款 the entPoint to set
     */
    public void setEntPoint(BigDecimal entPoint) {
        this.entPoint = entPoint;
    }

    /**
     * 获取终端交易码
     *
     * @return termTradeCode 终端交易码
     */
    public String getTermTradeCode() {
        return termTradeCode;
    }

    /**
     * 设置终端交易码
     *
     * @param termTradeCode 终端交易码
     */
    public void setTermTradeCode(String termTradeCode) {
        this.termTradeCode = termTradeCode;
    }

    /**
     * 获取终端流水号
     *
     * @return termRunCode 终端流水号
     */
    public String getTermRunCode() {
        return termRunCode;
    }

    /**
     * 设置终端流水号
     *
     * @param termRunCode 终端流水号
     */
    public void setTermRunCode(String termRunCode) {
        this.termRunCode = termRunCode;
    }

    /**
     * 获取终端类型码
     *
     * @return termTypeCode 终端类型码
     */
    public String getTermTypeCode() {
        return termTypeCode;
    }

    /**
     * 设置终端类型码
     *
     * @param termTypeCode 终端类型码
     */
    public void setTermTypeCode(String termTypeCode) {
        this.termTypeCode = termTypeCode;
    }

    /**
     * 获取是否扣商业服务费0是(收费)1否(不收费)
     *
     * @return isDeduction 是否扣商业服务费0是(收费)1否(不收费)
     */
    public int getIsDeduction() {
        return isDeduction;
    }

    /**
     * 设置是否扣商业服务费0是(收费)1否(不收费)
     *
     * @param isDeduction 是否扣商业服务费0是(收费)1否(不收费)
     */
    public void setIsDeduction(Integer isDeduction) {
        this.isDeduction = isDeduction;
    }

    /**
     * 获取是否网上积分登记0是1否
     *
     * @return isOnlineRegister 是否网上积分登记0是1否
     */
    public int getIsOnlineRegister() {
        return isOnlineRegister;
    }

    /**
     * 设置是否网上积分登记0是1否
     *
     * @param isOnlineRegister 是否网上积分登记0是1否
     */
    public void setIsOnlineRegister(Integer isOnlineRegister) {
        this.isOnlineRegister = isOnlineRegister;
    }

    /**
     * 获取pos调用ps次数
     *
     * @return posToPsCount pos调用ps次数
     */
    public Integer getPosToPsCount() {
        return posToPsCount;
    }

    /**
     * 设置pos调用ps次数
     *
     * @param posToPsCount pos调用ps次数
     */
    public void setPosToPsCount(Integer posToPsCount) {
        this.posToPsCount = posToPsCount;
    }

    /**
     * 获取企业名称
     *
     * @return entName 企业名称
     */
    public String getEntName() {
        return entName;
    }

    /**
     * 设置企业名称
     *
     * @param entName 企业名称
     */
    public void setEntName(String entName) {
        this.entName = entName;
    }

    /**
     * 获取店铺名称
     *
     * @return mallName 店铺名称
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 设置店铺名称
     *
     * @param mallName 店铺名称
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 获取remark
     *
     * @return remark remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置remark
     *
     * @param remark remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取业务类型
     *
     * @return businessType 业务类型
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 设置业务类型
     *
     * @param businessType 业务类型
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Timestamp getSourcePosDate() {
        return sourcePosDate == null ? null : (Timestamp) sourcePosDate.clone();
    }

    public void setSourcePosDate(Timestamp sourcePosDate) {
        this.sourcePosDate = sourcePosDate == null ? null : (Timestamp) sourcePosDate.clone();
    }

    /**
     * @return the 支付状态  0未支付 1已支付 2非支付类业务
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * @param 支付状态 0未支付 1已支付 2非支付类业务 the payStatus to set
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}
