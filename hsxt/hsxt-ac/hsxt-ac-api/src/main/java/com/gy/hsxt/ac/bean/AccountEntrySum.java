/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.ac.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 分录汇总信息对象
 * @author 作者 : weixz
 * @ClassName: 类名:AccountEntrySum
 * @Description: TODO
 * @createDate 创建时间: 2015-8-31 下午12:01:36
 * @version 1.0 
 */
public class AccountEntrySum implements Serializable{

	
	private static final long serialVersionUID = 5515396153946456298L;

	/**	客户全局编号 */
	private   String       custID;
	
	/**	账户类型编码 */
	private   String       accType;
	
	/**	互生号 */
	private   String       hsResNo;
	
	/**	红冲标识 */
	private   Integer      writeBack;
	
	/**	交易类型 */
	private   String       transType;
	
	/**
     * 原始交易流水号
     */
    private   String       sourceTransNo;
	
	/**	交易流水号 */
	private   String       transNo;
	
	/**	关联交易类型 */
	private   String       relTransType;
	
	/**	关联交易流水号 */
	private   String       relTransNo;
	
	/**	起始时间 */
	private   String       beginDate;
	
	/**	结束时间 */
	private   String       endDate;
	
	/** 起始时间 */
    private   Timestamp    beginDateTim;
    
    /** 结束时间 */
    private   Timestamp    endDateTim;
	
	/**	增向金额总额 */
	private   String       addAmountSum;
	
	/**	减向金额总额 */
	private   String       subAmountSum;
	
	/** 总金额 */
	private   String       sumAmount; 
	
	

	/**
	 * @return the 客户全局编号
	 */
	public String getCustID() {
		return custID;
	}

	/**
	 * @param 客户全局编号 the custID to set
	 */
	public void setCustID(String custID) {
		this.custID = custID;
	}

	/**
	 * @return the 账户类型编码
	 */
	public String getAccType() {
		return accType;
	}

	/**
	 * @param 账户类型编码 the accType to set
	 */
	public void setAccType(String accType) {
		this.accType = accType;
	}

	/**
	 * @return the 互生号
	 */
	public String getHsResNo() {
		return hsResNo;
	}

	/**
	 * @param 互生号 the hsResNo to set
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	/**
	 * @return the 红冲标识
	 */
	public Integer getWriteBack() {
		return writeBack;
	}

	/**
	 * @param 红冲标识 the writeBack to set
	 */
	public void setWriteBack(Integer writeBack) {
		this.writeBack = writeBack;
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
	 * @return the 交易流水号
	 */
	public String getTransNo() {
		return transNo;
	}

	/**
	 * @param 交易流水号 the transNo to set
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	/**
	 * @return the 关联交易类型
	 */
	public String getRelTransType() {
		return relTransType;
	}

	/**
	 * @param 关联交易类型 the relTransType to set
	 */
	public void setRelTransType(String relTransType) {
		this.relTransType = relTransType;
	}

	/**
	 * @return the 关联交易流水号
	 */
	public String getRelTransNo() {
		return relTransNo;
	}

	/**
	 * @param 关联交易流水号 the relTransNo to set
	 */
	public void setRelTransNo(String relTransNo) {
		this.relTransNo = relTransNo;
	}

	/**
	 * @return the 起始时间
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param 起始时间 the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the 结束时间
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 结束时间 the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 增向金额总额
	 */
	public String getAddAmountSum() {
		if(addAmountSum != null)
		{
			String a = "";
			if(addAmountSum.length()>1)
			{
				a = addAmountSum.substring(0, 1);
			}
			String b = "";
			if(addAmountSum.length()>2)
			{
				b = addAmountSum.substring(0, 2);
			}
			if(".".equals(a)){
				addAmountSum = 0 + addAmountSum;
			}
			if("-.".equals(b))
			{
				addAmountSum = a + 0 + addAmountSum.substring(1);
			}
		}
		return addAmountSum;
	}

	/**
	 * @param 增向金额总额 the addAmountSum to set
	 */
	public void setAddAmountSum(String addAmountSum) {
		this.addAmountSum = addAmountSum;
	}

	/**
	 * @return the 减向金额总额
	 */
	public String getSubAmountSum() {
		if(subAmountSum != null)
		{
			String a = "";
			if(subAmountSum.length()>1)
			{
				a = subAmountSum.substring(0, 1);
			}
			String b = "";
			if(subAmountSum.length()>2)
			{
				b = subAmountSum.substring(0, 2);
			}
			if(".".equals(a)){
				subAmountSum = 0 + subAmountSum;
			}
			if("-.".equals(b))
			{
				subAmountSum = a + 0 + subAmountSum.substring(1);
			}
		}
		return subAmountSum;
	}

	/**
	 * @param 减向金额总额 the subAmountSum to set
	 */
	public void setSubAmountSum(String subAmountSum) {
		this.subAmountSum = subAmountSum;
	}

    /**
     * @return the 起始时间
     */
    public Timestamp getBeginDateTim() {
        return beginDateTim;
    }

    /**
     * @param 起始时间 the beginDateTim to set
     */
    public void setBeginDateTim(Timestamp beginDateTim) {
        this.beginDateTim = beginDateTim;
    }

    /**
     * @return the 结束时间
     */
    public Timestamp getEndDateTim() {
        return endDateTim;
    }

    /**
     * @param 结束时间 the endDateTim to set
     */
    public void setEndDateTim(Timestamp endDateTim) {
        this.endDateTim = endDateTim;
    }

    /**
     * @return the 总金额
     */
    public String getSumAmount() {
    	if(sumAmount != null)
		{
			String a = "";
			if(sumAmount.length()>1)
			{
				a = sumAmount.substring(0, 1);
			}
			String b = "";
			if(sumAmount.length()>2)
			{
				b = sumAmount.substring(0, 2);
			}
			if(".".equals(a)){
				sumAmount = 0 + sumAmount;
			}
			if("-.".equals(b))
			{
				sumAmount = a + 0 + sumAmount.substring(1);
			}
		}
        return sumAmount;
    }

    /**
     * @param 总金额 the sumAmount to set
     */
    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount;
    }

	/**
	 * @return the 原始交易流水号
	 */
	public String getSourceTransNo() {
		return sourceTransNo;
	}

	/**
	 * @param 原始交易流水号 the sourceTransNo to set
	 */
	public void setSourceTransNo(String sourceTransNo) {
		this.sourceTransNo = sourceTransNo;
	}
	
}
