/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 分页查询工具发货单Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.pageresult
 * @ClassName: ToolShippingPage
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:27:58
 * @company: gyist
 * @version V3.0.0
 */
public class ToolShippingPage implements Serializable {

	private static final long serialVersionUID = -3762330212476748145L;

	/** 发货单号 */
	private String shippingId;

	/** 企业互生号 **/
	private String entResNo;

	/** 客户号 **/
	private String entCustId;

	/** 客户名称 **/
	private String custName;

	/** 发货单类型 **/
	private Integer shippingType;

	/** 发货时间 **/
	private String deliveryTime;

	/** 发货人 **/
	private String consignor;

	/** 发货单状态 **/
	private Integer deliveryStatus;

	private String receiver;

	private String mobile;

	public ToolShippingPage()
	{
		super();
	}

	public ToolShippingPage(String shippingId, String entResNo,
			String entCustId, String custName, Integer shippingType,
			String deliveryTime, String consignor)
	{
		super();
		this.shippingId = shippingId;
		this.entResNo = entResNo;
		this.entCustId = entCustId;
		this.custName = custName;
		this.shippingType = shippingType;
		this.deliveryTime = deliveryTime;
		this.consignor = consignor;
	}

	public String getShippingId()
	{
		return shippingId;
	}

	public void setShippingId(String shippingId)
	{
		this.shippingId = shippingId;
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

	public Integer getShippingType()
	{
		return shippingType;
	}

	public void setShippingType(Integer shippingType)
	{
		this.shippingType = shippingType;
	}

	public String getDeliveryTime()
	{
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime)
	{
		this.deliveryTime = deliveryTime;
	}

	public String getConsignor()
	{
		return consignor;
	}

	public void setConsignor(String consignor)
	{
		this.consignor = consignor;
	}

	public Integer getDeliveryStatus()
	{
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus)
	{
		this.deliveryStatus = deliveryStatus;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
