/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具下单返回结果
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ToolOrderResult
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月15日 下午7:32:05
 * @company: gyist
 * @version V3.0.0
 */
public class ToolOrderResult implements Serializable {

	private static final long serialVersionUID = 6166653089347308968L;

	/** 订单编号 **/
	private String orderNo;

	/** 订单支付超时 **/
	private String payOverTime;

	/** 订单货币金额 **/
	private String cashAmount;

	/** 订单互生币金额 **/
	private String hsbAmount;

	/** 货币转换比率 **/
	private String exchangeRate;

	/** 本地结算币种 **/
	private String currencyNameCn;

	/** 是否支付 **/
	private boolean isPay;

	public ToolOrderResult()
	{
		super();
	}

	public ToolOrderResult(String orderNo, String payOverTime, String cashAmount, String hsbAmount, String exchangeRate,
			String currencyNameCn)
	{
		super();
		this.orderNo = orderNo;
		this.payOverTime = payOverTime;
		this.cashAmount = cashAmount;
		this.hsbAmount = hsbAmount;
		this.exchangeRate = exchangeRate;
		this.currencyNameCn = currencyNameCn;
	}

	public ToolOrderResult(String orderNo, String payOverTime, String cashAmount, String hsbAmount, String exchangeRate,
			String currencyNameCn, boolean isPay)
	{
		super();
		this.orderNo = orderNo;
		this.payOverTime = payOverTime;
		this.cashAmount = cashAmount;
		this.hsbAmount = hsbAmount;
		this.exchangeRate = exchangeRate;
		this.currencyNameCn = currencyNameCn;
		this.isPay = isPay;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getPayOverTime()
	{
		return payOverTime;
	}

	public void setPayOverTime(String payOverTime)
	{
		this.payOverTime = payOverTime;
	}

	public String getCashAmount()
	{
		return cashAmount;
	}

	public void setCashAmount(String cashAmount)
	{
		this.cashAmount = cashAmount;
	}

	public String getHsbAmount()
	{
		return hsbAmount;
	}

	public void setHsbAmount(String hsbAmount)
	{
		this.hsbAmount = hsbAmount;
	}

	public String getExchangeRate()
	{
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}

	public String getCurrencyNameCn()
	{
		return currencyNameCn;
	}

	public void setCurrencyNameCn(String currencyNameCn)
	{
		this.currencyNameCn = currencyNameCn;
	}

	public boolean isPay()
	{
		return isPay;
	}

	public void setPay(boolean isPay)
	{
		this.isPay = isPay;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
