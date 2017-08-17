package com.gy.hsxt.ps.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author chenhz
 * @version v0.0.1
 * @description 积分参数实体类
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 */
public class Point implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 74906902096582029L;

    /***
     * 交易类型
     */
    @NotBlank(message = "交易类型不能为空")
    @Size(min = 6, max = 6, message = "交易类型最大长度是6位！")
    @Pattern(regexp = "^[A-Z][1-2][1-3][0-9][0-9]0$", message = "交易类型错误")
    private String transType;

    /***
     * 企业互生号
     */
    @NotBlank(message = "企业互生号不能为空")
    @Size(min = 11, max = 11, message = "企业互生号必须11位！")
    private String entResNo;

    /***
     * 消费者互生号
     */
    @NotBlank(message = "消费者互生号不能为空")
    @Size(min = 11, max = 11, message = "消费者互生号必须11位！")
    private String perResNo;

    /***
     * 设备编号
     */
    //@NotBlank(message = "设备编号不能为空")
    @Size(max = 20, message = "设备编号最大值为20位！")
    private String equipmentNo;

    /***
     * 渠道类型
     */
    @NotNull(message = "渠道类型不能为空")
    private Integer channelType;

    /***
     * 设备类型
     */
    //@NotNull(message = "设备类型不能为空")
    private Integer equipmentType;

    /***
     * 原始交易号
     */
    @NotBlank(message = "原始交易号不能为空")
    private String sourceTransNo;

    /***
     * 原批次号
     */
    //@NotBlank(message = "原批次号不能为空")
    private String sourceBatchNo;

    /***
     * 原始交易时间
     */
    @NotBlank(message = "原始交易时间不能为空")
    private String sourceTransDate;

    /***
     * 原始交易币种代号
     */
    @NotBlank(message = "原始交易币种代号不能为空")
    @Size(min = 3, max = 3, message = "原始交易币种代号3位！")
    private String sourceCurrencyCode;

    /***
     * 原始币种金额
     */
    @NotBlank(message = "原始币种金额不能为空")
    //@Min(value = 1, message = "最少值为1")
    private String sourceTransAmount;

    /***
     * 消费金额
     */
    private String orderAmount;

    /***
     * 抵扣券数量
     */
    private int deductionVoucher;

    /***
     * 交易金额
     */
    //@NotBlank(message = "交易金额不能为空")
    //@Min(value = 1, message = "最少值为1")
    private String transAmount;

    /***
     * 积分比例
     */
    //@NotBlank(message = "积分比例不能为空")
    private String pointRate;

    /***
     * 消费积分
     */
    private String pointSum;

    /***
     * 企业客户号
     */
    @NotBlank(message = "企业客户号不能为空")
    private String entCustId;

    /***
     * 消费者客户号
     */
    @NotBlank(message = "消费者客户号不能为空")
    private String perCustId;

    /***
     * 操作员
     */
    private String operNo;

    /***
     * 终端流水号(POS)
     */
    //@NotBlank(message = " 终端流水号不能为空")
    private String termRunCode;

    /***
     * 终端类型码(POS)
     */
    private String termTypeCode;

    /***
     * 终端交易码(POS)
     */
    private String termTradeCode;

    /***
     * pos调用ps次数
     */
    private Integer posToPsCount;

    /***
     * 货币转换率
     */
    private String currencyRate;

    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空")
    private String entName;

    /**
     * 店铺名称
     */
    private String mallName;

    /***
     * 单据码生成时间
     */
    private String sourcePosDate;

    /*备注 */
    private String remark;

    /**
     * 原原始交易号
     */
    private String oldSourceTransNo;

    /**
     * 支付方式
     */
    private Integer payChannel;

    /**
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
    public String getSourceTransDate() {
        return sourceTransDate;
    }

    /**
     * 设置原始交易时间
     *
     * @param sourceTransDate 原始交易时间
     */
    public void setSourceTransDate(String sourceTransDate) {
        this.sourceTransDate = sourceTransDate;
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
    public String getSourceTransAmount() {
        return sourceTransAmount;
    }

    /**
     * 设置原始币种金额
     *
     * @param sourceTransAmount 原始币种金额
     */
    public void setSourceTransAmount(String sourceTransAmount) {
        this.sourceTransAmount = sourceTransAmount;
    }

    /**
     * 获取抵扣券金额
     *
     * @return rebateAmount 抵扣券金额
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置抵扣券金额
     *
     * @param orderAmount 抵扣券金额
     */
    public void setOrderAmount(String orderAmount) {
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
    public String getTransAmount() {
        return transAmount;
    }

    /**
     * 设置交易金额
     *
     * @param transAmount 交易金额
     */
    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * 获取积分比例
     *
     * @return pointRate 积分比例
     */
    public String getPointRate() {
        return pointRate;
    }

    /**
     * 设置积分比例
     *
     * @param pointRate 积分比例
     */
    public void setPointRate(String pointRate) {
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
     * @return String pointSum
     */
    public String getPointSum() {
        return pointSum;
    }

    /**
     * 设置积分
     *
     * @param pointSum 积分
     */
    public void setPointSum(String pointSum) {
        this.pointSum = pointSum;
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
     * 设置终端类型码(POS)
     *
     * @param termTypeCode 终端类型码
     */
    public void setTermTypeCode(String termTypeCode) {
        this.termTypeCode = termTypeCode;
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

    public String getSourcePosDate() {
        return sourcePosDate;
    }

    public void setSourcePosDate(String sourcePosDate) {
        this.sourcePosDate = sourcePosDate;
    }

    /**
     * 获取原始交易号
     *
     * @return oldSourceTransNo 原始交易号
     */
    public String getOldSourceTransNo() {
        return oldSourceTransNo;
    }

    /**
     * 设置原始交易号
     *
     * @param oldSourceTransNo 原始交易号
     */
    public void setOldSourceTransNo(String oldSourceTransNo) {
        this.oldSourceTransNo = oldSourceTransNo;
    }

    /**
     * @return the 支付方式
     */
    public Integer getPayChannel() {
        return payChannel;
    }

    /**
     * @param 支付方式 the payChannel to set
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

}
