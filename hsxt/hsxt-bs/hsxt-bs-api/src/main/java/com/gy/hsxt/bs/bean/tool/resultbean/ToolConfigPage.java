/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 分页查询工具配置单返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.pageresult
 * @ClassName: ToolConfigPage
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:26:05
 * @company: gyist
 * @version V3.0.0
 */
public class ToolConfigPage implements Serializable {

	private static final long serialVersionUID = -4000572248016472045L;

	/** 订单号 **/
	private String orderNo;

	/** 配置单号 **/
	private String confNo;

	/** 互生号 **/
	private String entResNo;

	/** 客户号 **/
	private String entCustId;

	/** 客户名称 **/
	private String custName;

	/** 订单类型 **/
	private String orderType;

	/** 数量 **/
	private Integer quantity;

	/** 制作状态 **/
	private Integer confStatus;

	/** 配置时间 **/
	private String confDate;

	/** 工具类别 **/
	private String categoryCode;

	/** 完整收货地址 **/
	private String fullAddr;

	/** 售后编号 **/
	private String afterOrderNo;

	/** 订单时间 **/
	private String orderDate;

	public ToolConfigPage()
	{
		super();
	}

	public ToolConfigPage(String confNo, String entResNo, String entCustId, String custName, Integer quantity,
			Integer confStatus)
	{
		super();
		this.confNo = confNo;
		this.entResNo = entResNo;
		this.entCustId = entCustId;
		this.custName = custName;
		this.quantity = quantity;
		this.confStatus = confStatus;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
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

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public Integer getConfStatus()
	{
		return confStatus;
	}

	public void setConfStatus(Integer confStatus)
	{
		this.confStatus = confStatus;
	}

	public String getConfDate()
	{
		return confDate;
	}

	public void setConfDate(String confDate)
	{
		this.confDate = confDate;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getFullAddr()
	{
		return fullAddr;
	}

	public void setFullAddr(String fullAddr)
	{
		this.fullAddr = fullAddr;
	}

	public String getAfterOrderNo()
	{
		return afterOrderNo;
	}

	public void setAfterOrderNo(String afterOrderNo)
	{
		this.afterOrderNo = afterOrderNo;
	}

	public String getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
