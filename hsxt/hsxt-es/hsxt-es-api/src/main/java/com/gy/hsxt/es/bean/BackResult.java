package com.gy.hsxt.es.bean;

import java.io.Serializable;

/**
 * @description 退货返回结果实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v3.0
 */
public class BackResult extends Result implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5586498936477682694L;

	/** 积分 */
	private String perPoint;
	/** 交易流水号 */
	private String transNo;
	/** 会计时间*/
	private String accountantDate;
	/** 原交易币种 **/
	private String currency;
	/** 企业互生号 **/
	private String entNo;
	/** 原订单金额 **/
	private String orderAmount;
	/** 积分比例 **/
	private String pointRate;
	/** 积分应付款金额 **/
	private String assureOutValue;
	/** 交易金额 **/
	private String transAmount;
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
	 * 获取原交易币种
	 * @return currency 原交易币种
	 */
	public String getCurrency()
	{
		return currency;
	}
	/**
	 * 设置原交易币种
	 * @param currency 原交易币种
	 */
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	/**
	 * 获取企业互生号
	 * @return entNo 企业互生号
	 */
	public String getEntNo()
	{
		return entNo;
	}
	/**
	 * 设置企业互生号
	 * @param entNo 企业互生号
	 */
	public void setEntNo(String entNo)
	{
		this.entNo = entNo;
	}
	/**
	 * 获取原订单金额
	 * @return orderAmount 原订单金额
	 */
	public String getOrderAmount()
	{
		return orderAmount;
	}
	/**
	 * 设置原订单金额
	 * @param orderAmount 原订单金额
	 */
	public void setOrderAmount(String orderAmount)
	{
		this.orderAmount = orderAmount;
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
	 * 获取交易金额
	 * @return transAmount 交易金额
	 */
	public String getTransAmount()
	{
		return transAmount;
	}
	/**
	 * 设置交易金额
	 * @param transAmount 交易金额
	 */
	public void setTransAmount(String transAmount)
	{
		this.transAmount = transAmount;
	}

}
