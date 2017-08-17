/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 代购工具清单
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ProxyOrderDetail
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:32:38
 * @company: gyist
 * @version V3.0.0
 */
public class ProxyOrderDetail implements Serializable {

	private static final long serialVersionUID = -8431837108485745528L;

	/** 代购订单编号 **/
	private String proxyOrderNo;

	/** 产品编号 **/
	@NotEmpty(message = "产品编号不能为空")
	private String productId;

	/** 类别代码 **/
	@NotEmpty(message = "类别代码不能为空")
	private String categoryCode;

	/** 工具名称 **/
	@NotEmpty(message = "产品名称不能为空")
	private String productName;

	/** 工具单位 **/
	private String unit;

	/** 产品单价 **/
	@NotEmpty(message = "产品单价不能为空")
	private String price;

	/** 购买数量 **/
	@NotNull(message = "购买数量不能为空")
	private Integer quantity;

	/** 合计金额 **/
	private String totalAmount;

	/** 互生卡样编号 **/
	private String cardStyleId;

	/** 资源段id **/
	private List<String> segmentIds;

	/** 资源段 **/
	private List<ResSegment> segments;

	public ProxyOrderDetail()
	{
		super();
	}

	public ProxyOrderDetail(String proxyOrderNo, String productId,
			String categoryCode, String productName, String unit, String price,
			Integer quantity, String totalAmount, String cardStyleId)
	{
		super();
		this.proxyOrderNo = proxyOrderNo;
		this.productId = productId;
		this.categoryCode = categoryCode;
		this.productName = productName;
		this.unit = unit;
		this.price = price;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.cardStyleId = cardStyleId;
	}

	public String getProxyOrderNo()
	{
		return proxyOrderNo;
	}

	public void setProxyOrderNo(String proxyOrderNo)
	{
		this.proxyOrderNo = proxyOrderNo;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public String getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public String getCardStyleId()
	{
		return cardStyleId;
	}

	public void setCardStyleId(String cardStyleId)
	{
		this.cardStyleId = cardStyleId;
	}

	public List<String> getSegmentIds() {
		return segmentIds;
	}

	public void setSegmentIds(List<String> segmentIds) {
		this.segmentIds = segmentIds;
	}

	public List<ResSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<ResSegment> segments) {
		this.segments = segments;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
