package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @description 撤单返回结果实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class CancelResult extends Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7276847655115789263L;

	/** 积分*/
	private String perPoint;
	/** 交易流水号*/
	private String transNo;
	/** 会计时间*/
	private String accountantDate;
	/** 撤单金额*/
	private String transAmount;
	/** 原交易币种 */
	private  String sourceCurrencyCode;
	/** 企业互生号 */
	private  String entResNo;
	/** 原订单金额 */
	private  String sourceTransAmount;
	/** 积分比例 */
	private  String pointRate;
	/** 积分应付款金额*/
	private  String assureOutValue;
	/** 现金金额 */
	private  String cashAmount;
	/** 积分 */
	private  String pointsValue ;
	/**
	 * 获取积分
	 * @return perPoint 积分
	 */
	public String getPerPoint()
	{
		return perPoint;
	}
	/**
	 * 设置积分
	 * @param perPoint 积分
	 */
	public void setPerPoint(String perPoint)
	{
		this.perPoint = perPoint;
	}
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
	 * 获取会计时间
	 * @return accountantDate 会计时间
	 */
	public String getAccountantDate()
	{
		return accountantDate;
	}
	/**
	 * 设置会计时间
	 * @param accountantDate 会计时间
	 */
	public void setAccountantDate(String accountantDate)
	{
		this.accountantDate = accountantDate;
	}
	/**
	 * 获取撤单金额
	 * @return transAmount 撤单金额
	 */
	public String getTransAmount()
	{
		return transAmount;
	}
	/**
	 * 设置撤单金额
	 * @param transAmount 撤单金额
	 */
	public void setTransAmount(String transAmount)
	{
		this.transAmount = transAmount;
	}
	/**
	 * 获取原交易币种
	 * @return sourceCurrencyCode 原交易币种
	 */
	public String getSourceCurrencyCode()
	{
		return sourceCurrencyCode;
	}
	/**
	 * 设置原交易币种
	 * @param sourceCurrencyCode 原交易币种
	 */
	public void setSourceCurrencyCode(String sourceCurrencyCode)
	{
		this.sourceCurrencyCode = sourceCurrencyCode;
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
	 * 获取原订单金额
	 * @return sourceTransAmount 原订单金额
	 */
	public String getSourceTransAmount()
	{
		return sourceTransAmount;
	}
	/**
	 * 设置原订单金额
	 * @param sourceTransAmount 原订单金额
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
	 * 获取积分应付款金额
	 * @return assureOutValue 积分应付款金额
	 */
	public String getAssureOutValue()
	{
		return assureOutValue;
	}
	/**
	 * 设置积分应付款金额
	 * @param assureOutValue 积分应付款金额
	 */
	public void setAssureOutValue(String assureOutValue)
	{
		this.assureOutValue = assureOutValue;
	}
	/**
	 * 获取现金金额
	 * @return cashAmount 现金金额
	 */
	public String getCashAmount()
	{
		return cashAmount;
	}
	/**
	 * 设置现金金额
	 * @param cashAmount 现金金额
	 */
	public void setCashAmount(String cashAmount)
	{
		this.cashAmount = cashAmount;
	}
	/**
	 * 获取积分
	 * @return pointsValue 积分
	 */
	public String getPointsValue()
	{
		return pointsValue;
	}
	/**
	 * 设置积分
	 * @param pointsValue 积分
	 */
	public void setPointsValue(String pointsValue)
	{
		this.pointsValue = pointsValue;
	}
	
}
