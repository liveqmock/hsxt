/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 分页查询工具订单输出参数Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.pageresult
 * @ClassName: ToolOrderPage
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:26:52
 * @company: gyist
 * @version V3.0.0
 */
public class ToolOrderPage implements Serializable {

	private static final long serialVersionUID = -2021229470809975227L;

	/** 订单号 **/
	private String orderNo;

	/** 互生号 **/
	private String entResNo;

	/** 客户号 **/
	private String entCustId;

	/** 客户名称 **/
	private String custName;

	/** 订单状态 **/
	private Integer orderStatus;

	/** 订单总金额 **/
	private String orderTotal;

	/** 支付方式 **/
	private Integer payChannel;

	/** 订单类型 **/
	private String orderType;

	/** 订单时间 **/
	private String orderDate;

	/** 订单备注 **/
	private String remark;

	public ToolOrderPage()
	{
		super();
	}

	public ToolOrderPage(String orderNo, String entResNo, String entCustId, String custName, Integer orderStatus,
			String orderTotal, Integer payChannel, String orderType)
	{
		super();
		this.orderNo = orderNo;
		this.entResNo = entResNo;
		this.entCustId = entCustId;
		this.custName = custName;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
		this.payChannel = payChannel;
		this.orderType = orderType;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public Integer getOrderStatus()
	{
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public String getOrderTotal()
	{
		return orderTotal;
	}

	public void setOrderTotal(String orderTotal)
	{
		this.orderTotal = orderTotal;
	}

	public Integer getPayChannel()
	{
		return payChannel;
	}

	public void setPayChannel(Integer payChannel)
	{
		this.payChannel = payChannel;
	}

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public String getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
