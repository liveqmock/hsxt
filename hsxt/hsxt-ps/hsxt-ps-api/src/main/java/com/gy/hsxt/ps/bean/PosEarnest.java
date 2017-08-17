package com.gy.hsxt.ps.bean;

import java.io.Serializable;

/**
 * @description
 * @author maocan
 * @createDate 2016-3-31 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v3.0
 */
public class PosEarnest implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4005537682483642817L;

    /** 原始交易号 */
    private String sourceTransNo;

    /** 原始交易时间 */
    private String sourceTransDate;
    
    /** 消费金额*/
    private String orderAmount;
    
    /** 实付金额 */
    private String transAmount;
    
    /** 交易类型 */
    private String transType;
    
    /*** 原始交易币种代号 */
    private String sourceCurrencyCode;
    
    /** 抵扣券数量 */
    private int deductionVoucher;

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
     * @param sourceTransNo
     *            原始交易号
     */
    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
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
     * @param sourceTransDate
     *            原始交易时间
     */
    public void setSourceTransDate(String sourceTransDate) {
        this.sourceTransDate = sourceTransDate;
    }

    /**
     * @return the 实付金额
     */
    public String getTransAmount() {
        return transAmount;
    }

    /**
     * @param 实付金额 the transAmount to set
     */
    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * @return the 交易类型
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param 交易类型 the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * @return 原始交易币种代号
     */
    public String getSourceCurrencyCode() {
        return sourceCurrencyCode;
    }

    /**
     * @param 原始交易币种代号
     */
    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

	/**
	 * @return the 消费金额
	 */
	public String getOrderAmount() {
		return orderAmount;
	}

	/**
	 * @param 消费金额 the orderAmount to set
	 */
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * @return the 抵扣券数量
	 */
	public int getDeductionVoucher() {
		return deductionVoucher;
	}

	/**
	 * @param 抵扣券数量 the deductionVoucher to set
	 */
	public void setDeductionVoucher(int deductionVoucher) {
		this.deductionVoucher = deductionVoucher;
	}

    
}
