/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.bean;

import java.io.Serializable;

/**
 * @Package: com.gy.hsxt.ps.distribution.bean
 * @ClassName: PointSumAllot
 * @Description: 分录汇总实体类
 * 
 * @author: chenhz
 * @date: 2016-2-24 下午2:05:55
 * @version V3.0
 */
public class PointSum implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7013123840925179746L;
	/** 汇总流水号 **/
	private String sumNo;
	/** 客户号 **/
	private String custId;
	/** 互生号 **/
	private String hsResNo;
	/** 账户类型 **/
	private String accType;
	/** 增向金额 **/
	private String addAmount;
	/** 减向金额 **/
	private String subAmount;
	/** 交易类型 **/
	private String transType;
	/** 批次号 **/
	private String batchNo;
	/** 客户类型 **/
	private String custType;
	/** 汇总笔数 **/
	private String sumCount;
	/** 销售金额 **/
	private String saleAmount;
	/** 交易金额 **/
	private String transAmount;
	/** 会计时间 **/
	private String accountantDate;
	/** 现金交易金额 **/
	private String cashTransAmount;
	/** 互生币交易金额 **/
	private String hsbTransAmount;
	/** 退货金额 **/
	private String backAmount;
	/** 商业服务费 **/
	private String cscAmount;
	/** 是否结算 **/
	private String isSettle;
	/** 标记此记录是否有效 **/
	private String isActive;
	/** 创建时间 **/
	private String createdDate;
	/** 创建者 **/
	private String createdBy;
	/** 更新时间 **/
	private String updatedDate;
	/** 更新者 **/
	private String updatedBy;

	/**
	 * 获取汇总流水号
	 * 
	 * @return sumNo 汇总流水号
	 */
	public String getSumNo()
	{
		return sumNo;
	}

	/**
	 * 设置汇总流水号
	 * 
	 * @param sumNo
	 *            汇总流水号
	 */
	public void setSumNo(String sumNo)
	{
		this.sumNo = sumNo;
	}

	/**
	 * 获取客户号
	 * 
	 * @return custId 客户号
	 */
	public String getCustId()
	{
		return custId;
	}

	/**
	 * 设置客户号
	 * 
	 * @param custId
	 *            客户号
	 */
	public void setCustId(String custId)
	{
		this.custId = custId;
	}

	/**
	 * 获取互生号
	 * 
	 * @return hsResNo 互生号
	 */
	public String getHsResNo()
	{
		return hsResNo;
	}

	/**
	 * 设置互生号
	 * 
	 * @param hsResNo
	 *            互生号
	 */
	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	/**
	 * 获取账户类型
	 * 
	 * @return accType 账户类型
	 */
	public String getAccType()
	{
		return accType;
	}

	/**
	 * 设置账户类型
	 * 
	 * @param accType
	 *            账户类型
	 */
	public void setAccType(String accType)
	{
		this.accType = accType;
	}

	/**
	 * 获取增向金额
	 * 
	 * @return addAmount 增向金额
	 */
	public String getAddAmount()
	{
		return addAmount;
	}

	/**
	 * 设置增向金额
	 * 
	 * @param addAmount
	 *            增向金额
	 */
	public void setAddAmount(String addAmount)
	{
		this.addAmount = addAmount;
	}

	/**
	 * 获取减向金额
	 * 
	 * @return subAmount 减向金额
	 */
	public String getSubAmount()
	{
		return subAmount;
	}

	/**
	 * 设置减向金额
	 * 
	 * @param subAmount
	 *            减向金额
	 */
	public void setSubAmount(String subAmount)
	{
		this.subAmount = subAmount;
	}

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
	 * 获取批次号
	 * 
	 * @return batchNo 批次号
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
	 * 获取客户类型
	 * 
	 * @return custType 客户类型
	 */
	public String getCustType()
	{
		return custType;
	}

	/**
	 * 设置客户类型
	 * 
	 * @param custType
	 *            客户类型
	 */
	public void setCustType(String custType)
	{
		this.custType = custType;
	}

	/**
	 * 获取汇总笔数
	 * 
	 * @return sumCount 汇总笔数
	 */
	public String getSumCount()
	{
		return sumCount;
	}

	/**
	 * 设置汇总笔数
	 * 
	 * @param sumCount
	 *            汇总笔数
	 */
	public void setSumCount(String sumCount)
	{
		this.sumCount = sumCount;
	}

	/**
	 * 获取销售金额
	 * 
	 * @return saleAmount 销售金额
	 */
	public String getSaleAmount()
	{
		return saleAmount;
	}

	/**
	 * 设置销售金额
	 * 
	 * @param saleAmount
	 *            销售金额
	 */
	public void setSaleAmount(String saleAmount)
	{
		this.saleAmount = saleAmount;
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
	 * 获取会计时间
	 * 
	 * @return accountantDate 会计时间
	 */
	public String getAccountantDate()
	{
		return accountantDate;
	}

	/**
	 * 设置会计时间
	 * 
	 * @param accountantDate
	 *            会计时间
	 */
	public void setAccountantDate(String accountantDate)
	{
		this.accountantDate = accountantDate;
	}

	/**
	 * 获取现金交易金额
	 * 
	 * @return cashTransAmount 现金交易金额
	 */
	public String getCashTransAmount()
	{
		return cashTransAmount;
	}

	/**
	 * 设置现金交易金额
	 * 
	 * @param cashTransAmount
	 *            现金交易金额
	 */
	public void setCashTransAmount(String cashTransAmount)
	{
		this.cashTransAmount = cashTransAmount;
	}

	/**
	 * 获取互生币交易金额
	 * 
	 * @return hsbTransAmount 互生币交易金额
	 */
	public String getHsbTransAmount()
	{
		return hsbTransAmount;
	}

	/**
	 * 设置互生币交易金额
	 * 
	 * @param hsbTransAmount
	 *            互生币交易金额
	 */
	public void setHsbTransAmount(String hsbTransAmount)
	{
		this.hsbTransAmount = hsbTransAmount;
	}

	/**
	 * 获取退货金额
	 * 
	 * @return backAmount 退货金额
	 */
	public String getBackAmount()
	{
		return backAmount;
	}

	/**
	 * 设置退货金额
	 * 
	 * @param backAmount
	 *            退货金额
	 */
	public void setBackAmount(String backAmount)
	{
		this.backAmount = backAmount;
	}

	/**
	 * 获取商业服务费
	 * 
	 * @return cscAmount 商业服务费
	 */
	public String getCscAmount()
	{
		return cscAmount;
	}

	/**
	 * 设置商业服务费
	 * 
	 * @param cscAmount
	 *            商业服务费
	 */
	public void setCscAmount(String cscAmount)
	{
		this.cscAmount = cscAmount;
	}

	/**
	 * 获取是否结算
	 * 
	 * @return isSettle 是否结算
	 */
	public String getIsSettle()
	{
		return isSettle;
	}

	/**
	 * 设置是否结算
	 * 
	 * @param isSettle
	 *            是否结算
	 */
	public void setIsSettle(String isSettle)
	{
		this.isSettle = isSettle;
	}

	/**
	 * 获取标记此记录是否有效
	 * 
	 * @return isActive 标记此记录是否有效
	 */
	public String getIsActive()
	{
		return isActive;
	}

	/**
	 * 设置标记此记录是否有效
	 * 
	 * @param isActive
	 *            标记此记录是否有效
	 */
	public void setIsActive(String isActive)
	{
		this.isActive = isActive;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return createdDate 创建时间
	 */
	public String getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createdDate
	 *            创建时间
	 */
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * 获取创建者
	 * 
	 * @return createdBy 创建者
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
	 * 获取更新时间
	 * 
	 * @return updatedDate 更新时间
	 */
	public String getUpdatedDate()
	{
		return updatedDate;
	}

	/**
	 * 设置更新时间
	 * 
	 * @param updatedDate
	 *            更新时间
	 */
	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	/**
	 * 获取更新者
	 * 
	 * @return updatedBy 更新者
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}

	/**
	 * 设置更新者
	 * 
	 * @param updatedBy
	 *            更新者
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

}
