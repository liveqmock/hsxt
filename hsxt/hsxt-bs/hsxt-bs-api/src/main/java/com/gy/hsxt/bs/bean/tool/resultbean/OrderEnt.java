/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 分页查询企业订单返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: ToolOrderEnt
 * @Description:
 * @author: likui
 * @date: 2015年10月28日 上午11:58:41
 * @company: gyist
 * @version V3.0.0
 */
public class OrderEnt implements Serializable {

	private static final long serialVersionUID = -6071753076745559124L;

	/** 订单号 **/
	private String orderNo;

	/** 订单时间 **/
	private String orderDate;

	/** 订单金额 **/
	private String orderAmount;

	/** 订单类型 **/
	private String orderType;

	/** 支付方式 **/
	private Integer payChannel;

	/** 订单状态 **/
	private Integer orderStatus;

	public OrderEnt()
	{
		super();
	}

	public OrderEnt(String orderNo, String orderDate, String orderAmount, String orderType,
			Integer payChannel, Integer orderStatus)
	{
		super();
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.orderAmount = orderAmount;
		this.orderType = orderType;
		this.payChannel = payChannel;
		this.orderStatus = orderStatus;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	public String getOrderAmount()
	{
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount)
	{
		this.orderAmount = orderAmount;
	}

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public Integer getPayChannel()
	{
		return payChannel;
	}

	public void setPayChannel(Integer payChannel)
	{
		this.payChannel = payChannel;
	}

	public Integer getOrderStatus()
	{
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
