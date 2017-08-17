/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.bean;

import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.es.distribution.bean
 * @ClassName: TollAlloc
 * @Description: 积分分配、积分汇总、税收、互生币汇总、商业服务费
 * @author: chenhz
 * @date: 2016-1-6 下午2:05:55
 */
public class Alloc implements Cloneable, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4274588230357655728L;

    /**
     * 消费者互生号
     **/
    private String perResNo;
    /**
     * 原始交易时间
     */
    private Timestamp sourceTransDate;
    /**
     * 会计时间
     */
    private Timestamp accountantDate;
    /**
     * 交易类型
     **/
    private String transType;
    /**
     * 获得积分值
     **/
    private BigDecimal pointVal;

    /**
     * 积分分配流水号
     **/
    private String allocNo;
    /**
     * 客户全局编号
     **/
    private String custId;
    /**
     * 企业互生号
     **/
    private String hsResNo;
    /**
     * 账户类型编码
     **/
    private String accType;
    /**
     * 红冲标识
     **/
    private String writeBack;
    /**
     * 渠道类型
     **/
    private Integer channelType;
    /**
     * 设备类型
     **/
    private Integer equipmentType;
    /**
     * 交易流水号
     **/
    private String transNo;
    /**
     * 关联交易类型
     **/
    private String relTransType;
    /**
     * 关联交易流水号
     **/
    private String relTransNo;
    /**
     * 原交易流水号
     **/
    private String sourceTransNo;

    /**
     * 关联扣费汇总流水号
     **/
    private String relTaxSumNo;
    /**
     * 增向金额
     **/
    private BigDecimal addAmount;
    /**
     * 减向金额
     **/
    private BigDecimal subAmount;
    /**
     * 批次号
     **/
    private String batchNo;
    /**
     * 原批次号
     **/
    private String sourceBatchNo;
    /**
     * 客户类型
     **/
    private Integer custType;
    /**
     * 实时分录表 - 分录流水号
     **/
    private String entryNo;
    /**
     * 实时分录表 - 关联分录流水号
     **/
    private String relEntryNo;

    /**
     * 积分汇总流水号
     **/
    private String pvNo;
    /**
     * 汇总笔数
     **/
    private Integer pvAddCount;

    /**
     * 税收流水号
     */
    private String taxNo;
    /**
     * 税收增向金额
     */
    private BigDecimal taxAddAmount;
    /**
     * 税收减向金额
     */
    private BigDecimal taxSubAmount;
    /**
     * 税收账户类型
     */
    private String taxAccType;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 关联积分汇总
     */
    private String relPvNo;
    /**
     * 关联商业服务费税收
     */
    private String relCscNo;
    /**
     * 税收类别
     */
    private Integer taxType;

    /**
     * 互生币汇总表 - 流水号
     */
    private String hsbNo;
    /**
     * 笔数
     **/
    private Integer hsbAddCount;

    /**
     * 商业服务费表流水号
     */
    private String tollNo;
    /**
     * 增向金额
     */
    private BigDecimal tollAddAmount;
    /**
     * 减向金额
     */
    private BigDecimal tollSubAmount;
    /**
     * 汇总账户类型
     */
    private String tollAccType;
    /**
     * 关联互生币汇总
     */
    private String relHsbNo;
    /**
     * 服务费类型 1、商业服务费 2、其他
     */
    private Integer cscType;
    /**
     * 结算类型 1、日结 2、月结
     */
    private Integer settleType;
    /**
     * 费率
     */
    private BigDecimal amountRate;

    /**
     * 是否结算
     */
    private Integer isSettle;

    /**
     * 当日销售额
     **/
    private BigDecimal daySale;
    /**
     * 当日交易额
     **/
    private BigDecimal dayTurnover;
    /**
     * 互生币交易额
     **/
    private BigDecimal dayHsbTurnover;
    /**
     * 线下现金交易额
     **/
    private BigDecimal dayCashTurnover;
    /**
     * 当日退货退款额(HSB)
     **/
    private BigDecimal dayBackHsbTurnover;

    /**
     * 当日结单退积分数
     **/
    private BigDecimal dayBackPointTurnover;

    /**
     * 当日退货退款额(現金)
     **/
    private BigDecimal dayBackCyberTurnover;
    /**
     * 日结商业服务费扣除金额
     **/
    private BigDecimal cscTurnover;
    /**
     * 日结互生币金额
     **/
    private BigDecimal dayHsbAmount;

    /**
     * 扣除积分金额(扣企业互生币)
     **/
    private BigDecimal deductPointAmount;

    /*备注 */
    private String remark;

    /**
     * 获取是否结算
     *
     * @return isSettle 是否结算
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
     * 获取关联商业服务费税收
     *
     * @return relCscNo 关联商业服务费税收
     */
    public String getRelCscNo() {
        return relCscNo;
    }

    /**
     * 设置关联商业服务费税收
     *
     * @param relCscNo 关联商业服务费税收
     */
    public void setRelCscNo(String relCscNo) {
        this.relCscNo = relCscNo;
    }

    /**
     * 获取积分分配流水号
     *
     * @return allocNo 积分分配流水号
     */
    public String getAllocNo() {
        return allocNo;
    }

    /**
     * 设置积分分配流水号
     *
     * @param allocNo 积分分配流水号
     */
    public void setAllocNo(String allocNo) {
        this.allocNo = allocNo;
    }

    /**
     * 获取客户全局编号
     *
     * @return custId 客户全局编号
     */
    public String getCustId() {
        return custId;
    }

    /**
     * 设置客户全局编号
     *
     * @param custId 客户全局编号
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * 获取企业互生号
     *
     * @return hsResNo 企业互生号
     */
    public String getHsResNo() {
        return hsResNo;
    }

    /**
     * 设置企业互生号
     *
     * @param hsResNo 企业互生号
     */
    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    /**
     * 获取账户类型编码
     *
     * @return accType 账户类型编码
     */
    public String getAccType() {
        return accType;
    }

    /**
     * 设置账户类型编码
     *
     * @param accType 账户类型编码
     */
    public void setAccType(String accType) {
        this.accType = accType;
    }

    /**
     * 获取红冲标识
     *
     * @return writeBack 红冲标识
     */
    public String getWriteBack() {
        return writeBack;
    }

    /**
     * 设置红冲标识
     *
     * @param writeBack 红冲标识
     */
    public void setWriteBack(String writeBack) {
        this.writeBack = writeBack;
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
     * 获取交易流水号
     *
     * @return transNo 交易流水号
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
     * 获取关联交易类型
     *
     * @return relTransType 关联交易类型
     */
    public String getRelTransType() {
        return relTransType;
    }

    /**
     * 设置关联交易类型
     *
     * @param relTransType 关联交易类型
     */
    public void setRelTransType(String relTransType) {
        this.relTransType = relTransType;
    }

    /**
     * 获取关联交易流水号
     *
     * @return relTransNo 关联交易流水号
     */
    public String getRelTransNo() {
        return relTransNo;
    }

    /**
     * 设置关联交易流水号
     *
     * @param relTransNo 关联交易流水号
     */
    public void setRelTransNo(String relTransNo) {
        this.relTransNo = relTransNo;
    }

    /**
     * 获取原交易流水号
     *
     * @return sourceTransNo 原交易流水号
     */
    public String getSourceTransNo() {
        return sourceTransNo;
    }

    /**
     * 设置原交易流水号
     *
     * @param sourceTransNo 原交易流水号
     */
    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

    /**
     * 获取关联扣费汇总流水号
     *
     * @return relTaxSumNo 关联扣费汇总流水号
     */
    public String getRelTaxSumNo() {
        return relTaxSumNo;
    }

    /**
     * 设置关联扣费汇总流水号
     *
     * @param relTaxSumNo 关联扣费汇总流水号
     */
    public void setRelTaxSumNo(String relTaxSumNo) {
        this.relTaxSumNo = relTaxSumNo;
    }

    /**
     * 获取增向金额
     *
     * @return addAmount 增向金额
     */
    public BigDecimal getAddAmount() {
        return addAmount;
    }

    /**
     * 设置增向金额
     *
     * @param addAmount 增向金额
     */
    public void setAddAmount(BigDecimal addAmount) {
        this.addAmount = addAmount;
    }

    /**
     * 获取减向金额
     *
     * @return subAmount 减向金额
     */
    public BigDecimal getSubAmount() {
        return subAmount;
    }

    /**
     * 设置减向金额
     *
     * @param subAmount 减向金额
     */
    public void setSubAmount(BigDecimal subAmount) {
        this.subAmount = subAmount;
    }

    /**
     * 获取批次号
     *
     * @return batchNo 批次号
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
     * 获取原批次号
     *
     * @return sourceBatchNo 原批次号
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
     * 获取客户类型
     *
     * @return custType 客户类型
     */
    public Integer getCustType() {
        return custType;
    }

    /**
     * 设置客户类型
     *
     * @param custType 客户类型
     */
    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    /**
     * 获取实时分录表-分录流水号
     *
     * @return entryNo 实时分录表-分录流水号
     */
    public String getEntryNo() {
        return entryNo;
    }

    /**
     * 设置实时分录表-分录流水号
     *
     * @param entryNo 实时分录表-分录流水号
     */
    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    /**
     * 获取实时分录表-关联分录流水号
     *
     * @return relEntryNo 实时分录表-关联分录流水号
     */
    public String getRelEntryNo() {
        return relEntryNo;
    }

    /**
     * 设置实时分录表-关联分录流水号
     *
     * @param relEntryNo 实时分录表-关联分录流水号
     */
    public void setRelEntryNo(String relEntryNo) {
        this.relEntryNo = relEntryNo;
    }

    /**
     * 获取积分汇总流水号
     *
     * @return pvNo 积分汇总流水号
     */
    public String getPvNo() {
        return pvNo;
    }

    /**
     * 设置积分汇总流水号
     *
     * @param pvNo 积分汇总流水号
     */
    public void setPvNo(String pvNo) {
        this.pvNo = pvNo;
    }

    /**
     * 获取汇总笔数
     *
     * @return pvAddCount 汇总笔数
     */
    public Integer getPvAddCount() {
        return pvAddCount;
    }

    /**
     * 设置汇总笔数
     *
     * @param pvAddCount 汇总笔数
     */
    public void setPvAddCount(Integer pvAddCount) {
        this.pvAddCount = pvAddCount;
    }

    /**
     * 获取税收流水号
     *
     * @return taxNo 税收流水号
     */
    public String getTaxNo() {
        return taxNo;
    }

    /**
     * 设置税收流水号
     *
     * @param taxNo 税收流水号
     */
    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    /**
     * 获取税收增向金额
     *
     * @return taxAddAmount 税收增向金额
     */
    public BigDecimal getTaxAddAmount() {
        return taxAddAmount;
    }

    /**
     * 设置税收增向金额
     *
     * @param taxAddAmount 税收增向金额
     */
    public void setTaxAddAmount(BigDecimal taxAddAmount) {
        this.taxAddAmount = taxAddAmount;
    }

    /**
     * 获取税收减向金额
     *
     * @return taxSubAmount 税收减向金额
     */
    public BigDecimal getTaxSubAmount() {
        return taxSubAmount;
    }

    /**
     * 设置税收减向金额
     *
     * @param taxSubAmount 税收减向金额
     */
    public void setTaxSubAmount(BigDecimal taxSubAmount) {
        this.taxSubAmount = taxSubAmount;
    }

    /**
     * 获取税收账户类型
     *
     * @return taxAccType 税收账户类型
     */
    public String getTaxAccType() {
        return taxAccType;
    }

    /**
     * 设置税收账户类型
     *
     * @param taxAccType 税收账户类型
     */
    public void setTaxAccType(String taxAccType) {
        this.taxAccType = taxAccType;
    }

    /**
     * 获取税率
     *
     * @return taxRate 税率
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 设置税率
     *
     * @param taxRate 税率
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 获取关联积分汇总
     *
     * @return relPvNo 关联积分汇总
     */
    public String getRelPvNo() {
        return relPvNo;
    }

    /**
     * 设置关联积分汇总
     *
     * @param relPvNo 关联积分汇总
     */
    public void setRelPvNo(String relPvNo) {
        this.relPvNo = relPvNo;
    }

    /**
     * 获取税收类别
     *
     * @return taxType 税收类别
     */
    public Integer getTaxType() {
        return taxType;
    }

    /**
     * 设置税收类别
     *
     * @param taxType 税收类别
     */
    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }

    /**
     * 获取互生币汇总表-流水号
     *
     * @return hsbNo 互生币汇总表-流水号
     */
    public String getHsbNo() {
        return hsbNo;
    }

    /**
     * 设置互生币汇总表-流水号
     *
     * @param hsbNo 互生币汇总表-流水号
     */
    public void setHsbNo(String hsbNo) {
        this.hsbNo = hsbNo;
    }

    /**
     * 获取笔数
     *
     * @return hsbAddCount 笔数
     */
    public Integer getHsbAddCount() {
        return hsbAddCount;
    }

    /**
     * 设置笔数
     *
     * @param hsbAddCount 笔数
     */
    public void setHsbAddCount(Integer hsbAddCount) {
        this.hsbAddCount = hsbAddCount;
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

    /**
     * 获取增向金额
     *
     * @return tollAddAmount 增向金额
     */
    public BigDecimal getTollAddAmount() {
        return tollAddAmount;
    }

    /**
     * 设置增向金额
     *
     * @param tollAddAmount 增向金额
     */
    public void setTollAddAmount(BigDecimal tollAddAmount) {
        this.tollAddAmount = tollAddAmount;
    }

    /**
     * 获取减向金额
     *
     * @return tollSubAmount 减向金额
     */
    public BigDecimal getTollSubAmount() {
        return tollSubAmount;
    }

    /**
     * 设置减向金额
     *
     * @param tollSubAmount 减向金额
     */
    public void setTollSubAmount(BigDecimal tollSubAmount) {
        this.tollSubAmount = tollSubAmount;
    }

    /**
     * 获取汇总账户类型
     *
     * @return tollAccType 汇总账户类型
     */
    public String getTollAccType() {
        return tollAccType;
    }

    /**
     * 设置汇总账户类型
     *
     * @param tollAccType 汇总账户类型
     */
    public void setTollAccType(String tollAccType) {
        this.tollAccType = tollAccType;
    }

    /**
     * 获取关联互生币汇总
     *
     * @return relHsbNo 关联互生币汇总
     */
    public String getRelHsbNo() {
        return relHsbNo;
    }

    /**
     * 设置关联互生币汇总
     *
     * @param relHsbNo 关联互生币汇总
     */
    public void setRelHsbNo(String relHsbNo) {
        this.relHsbNo = relHsbNo;
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
     * 获取获得积分值
     *
     * @return pointVal 获得积分值
     */
    public BigDecimal getPointVal() {
        return pointVal;
    }

    /**
     * 设置获得积分值
     *
     * @param pointVal 获得积分值
     */
    public void setPointVal(BigDecimal pointVal) {
        this.pointVal = pointVal;
    }

    /**
     * 获取会计时间
     *
     * @return accountantDate 会计时间
     */
    public Timestamp getAccountantDate() {
        return accountantDate == null ? null : (Timestamp) accountantDate.clone();
    }

    /**
     * 设置会计时间
     *
     * @param accountantDate 会计时间
     */
    public void setAccountantDate(Timestamp accountantDate) {
        this.accountantDate = accountantDate == null ? null : (Timestamp) accountantDate.clone();
    }

    /**
     * 获取服务费类型1、商业服务费2、其他
     *
     * @return cscType 服务费类型1、商业服务费2、其他
     */
    public Integer getCscType() {
        return cscType;
    }

    /**
     * 设置服务费类型1、商业服务费2、其他
     *
     * @param cscType 服务费类型1、商业服务费2、其他
     */
    public void setCscType(Integer cscType) {
        this.cscType = cscType;
    }

    /**
     * 获取结算类型1、日结2、月结
     *
     * @return settleType 结算类型1、日结2、月结
     */
    public Integer getSettleType() {
        return settleType;
    }

    /**
     * 设置结算类型1、日结2、月结
     *
     * @param settleType 结算类型1、日结2、月结
     */
    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    /**
     * 获取费率
     *
     * @return amountRate 费率
     */
    public BigDecimal getAmountRate() {
        return amountRate;
    }

    /**
     * 设置费率
     *
     * @param amountRate 费率
     */
    public void setAmountRate(BigDecimal amountRate) {
        this.amountRate = amountRate;
    }


    /**
     * 获取当日销售额
     *
     * @return daySale 当日销售额
     */
    public BigDecimal getDaySale() {

        if (dayBackHsbTurnover == null) {
            dayBackHsbTurnover = BigDecimal.ZERO;
        }
        if (dayHsbTurnover == null) {
            dayHsbTurnover = BigDecimal.ZERO;
        }
        if (dayCashTurnover == null) {
            dayCashTurnover = BigDecimal.ZERO;
        }
        return Compute.add(dayHsbTurnover, dayCashTurnover);

    }

    /**
     * 设置当日销售额
     *
     * @param daySale 当日销售额
     */
    public void setDaySale(BigDecimal daySale) {
        this.daySale = daySale;
    }

    /**
     * 获取当日交易额
     *
     * @return dayTurnover 当日交易额
     */
    public BigDecimal getDayTurnover() {
        if (dayHsbTurnover == null) {
            dayHsbTurnover = BigDecimal.ZERO;
        }
        if (dayCashTurnover == null) {
            dayCashTurnover = BigDecimal.ZERO;
        }
        return Compute.add(dayHsbTurnover, dayCashTurnover);

    }

    /**
     * 设置当日交易额
     *
     * @param dayTurnover 当日交易额
     */
    public void setDayTurnover(BigDecimal dayTurnover) {
        this.dayTurnover = dayTurnover;
    }

    /**
     * 获取互生币交易额
     *
     * @return dayHsbTurnover 互生币交易额
     */
    public BigDecimal getDayHsbTurnover() {
        return dayHsbTurnover;
    }

    /**
     * 设置互生币交易额
     *
     * @param dayHsbTurnover 互生币交易额
     */
    public void setDayHsbTurnover(BigDecimal dayHsbTurnover) {
        this.dayHsbTurnover = dayHsbTurnover;
    }

    /**
     * 获取线下现金交易额
     *
     * @return dayCashTurnover 线下现金交易额
     */
    public BigDecimal getDayCashTurnover() {
        return dayCashTurnover;
    }

    /**
     * 设置线下现金交易额
     *
     * @param dayCashTurnover 线下现金交易额
     */
    public void setDayCashTurnover(BigDecimal dayCashTurnover) {
        this.dayCashTurnover = dayCashTurnover;
    }

    /**
     * 获取当日退货退款额
     *
     * @return dayBackHsbTurnover 当日退货退款额
     */
    public BigDecimal getDayBackHsbTurnover() {
        return dayBackHsbTurnover;
    }

    /**
     * 设置当日退货退款额
     *
     * @param dayBackHsbTurnover 当日退货退款额
     */

    public void setDayBackHsbTurnover(BigDecimal dayBackHsbTurnover) {
        this.dayBackHsbTurnover = dayBackHsbTurnover;
    }

    public BigDecimal getDayBackPointTurnover() {
        return dayBackPointTurnover;
    }

    public void setDayBackPointTurnover(BigDecimal dayBackPointTurnover) {
        this.dayBackPointTurnover = dayBackPointTurnover;
    }

    /**
     * 获取当日退货退款额
     *
     * @return dayBackCyberTurnover 当日退货退款额
     */
    public BigDecimal getDayBackCyberTurnover() {
        return dayBackCyberTurnover;
    }

    /**
     * 设置当日退货退款额
     *
     * @param dayBackCyberTurnover 当日退货退款额
     */

    public void setDayBackCyberTurnover(BigDecimal dayBackCyberTurnover) {
        this.dayBackCyberTurnover = dayBackCyberTurnover;
    }

    /**
     * 获取日结商业服务费扣除金额
     *
     * @return cscTurnover 日结商业服务费扣除金额
     */
    public BigDecimal getCscTurnover() {
        return cscTurnover;
    }

    /**
     * 设置日结商业服务费扣除金额
     *
     * @param cscTurnover 日结商业服务费扣除金额
     */
    public void setCscTurnover(BigDecimal cscTurnover) {
        this.cscTurnover = cscTurnover;
    }

    /**
     * 获取日结互生币金额
     *
     * @return dayHsbAmount 日结互生币金额
     */
    public BigDecimal getDayHsbAmount() {
        if (dayHsbTurnover == null) {
            dayHsbTurnover = BigDecimal.ZERO;
        }
        if (cscTurnover == null) {
            cscTurnover = BigDecimal.ZERO;
        }
        if (dayBackPointTurnover == null) {
            dayBackPointTurnover = BigDecimal.ZERO;
        }
        dayHsbAmount = Compute.sub(Compute.add(dayBackPointTurnover, dayHsbTurnover), deductPointAmount);
        return dayHsbAmount;

    }

    /**
     * 设置日结互生币金额
     *
     * @param dayHsbAmount 日结互生币金额
     */
    public void setDayHsbAmount(BigDecimal dayHsbAmount) {
        this.dayHsbAmount = dayHsbAmount;
    }

    public BigDecimal getDeductPointAmount() {
        return deductPointAmount;
    }

    public void setDeductPointAmount(BigDecimal deductPointAmount) {
        this.deductPointAmount = deductPointAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Alloc clone() throws CloneNotSupportedException {
        Alloc alloc;
        try {
            alloc = (Alloc) super.clone();//Object 中的clone()识别出你要复制的是哪一个对象。
        } catch (CloneNotSupportedException e) {
            throw e;
        }
        return alloc;
    }
}
