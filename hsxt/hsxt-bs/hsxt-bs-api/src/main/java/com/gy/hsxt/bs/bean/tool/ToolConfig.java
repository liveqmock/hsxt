/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 工具配置单Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ToolConfig
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:33:52
 * @company: gyist
 * @version V3.0.0
 */
public class ToolConfig implements Serializable {

	private static final long serialVersionUID = -6278378070435473526L;
	/** 配置单号 **/
	private String confNo;

	/** 客户号 **/
	private String hsCustId;

	/** 互生号 **/
	private String hsResNo;

	/** 发货单号 **/
	private String shippingId;

	/** 工具类别代码 **/
	private String categoryCode;

	/** 工具编号 **/
	private String productId;

	/** 工具名称 **/
	private String productName;

	/** 单位 **/
	private String unit;

	/** 价格 **/
	private String price;

	/** 数量 **/
	private Integer quantity;

	/** 总金额 **/
	private String totalAmount;

	/** 互生卡样编号 **/
	private String cardStyleId;

	/** 互生卡制作卡样确认书 **/
	private String confirmFile;

	/** 订单号 **/
	private String orderNo;

	/** 配置状态 0：待付款 1：待确认 2：待配置 3：待制作 4：待入库 5：待发货 5：已发货 7：已签收 8：已取消 **/
	private Integer confStatus;

	/** 出库编号 **/
	private String storeOutNo;

	/** 配置说明 **/
	private String description;

	/** 配置人 **/
	private String confUser;

	/** 配置时间 **/
	private String confDate;

	/** 配置类型 1:申报配置 2:新增配置 3:售后配置 **/
	private Integer confType;

	/** 根据客户收货地址及仓库配送区域决定哪个仓库配货 **/
	private String whId;

	/** 资源段id **/
	private List<String> segmentIds;

	/** 资源段 **/
	private List<ResSegment> segments;

	public ToolConfig()
	{
		super();
	}

	public ToolConfig(String confNo, String confirmFile)
	{
		super();
		this.confNo = confNo;
		this.confirmFile = confirmFile;
	}

	public ToolConfig(String confNo, String cardStyleId, Integer confStatus, String storeOutNo, String description)
	{
		super();
		this.confNo = confNo;
		this.cardStyleId = cardStyleId;
		this.confStatus = confStatus;
		this.storeOutNo = storeOutNo;
		this.description = description;
	}

	public ToolConfig(String confNo, String hsCustId, String hsResNo, String shippingId, String categoryCode,
			String productId, String productName, String unit, String price, Integer quantity, String totalAmount,
			String cardStyleId, String orderNo, Integer confStatus, String storeOutNo, String description,
			String confUser, String confDate, Integer confType, String whId)
	{
		super();
		this.confNo = confNo;
		this.hsCustId = hsCustId;
		this.hsResNo = hsResNo;
		this.shippingId = shippingId;
		this.categoryCode = categoryCode;
		this.productId = productId;
		this.productName = productName;
		this.unit = unit;
		this.price = price;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.cardStyleId = cardStyleId;
		this.orderNo = orderNo;
		this.confStatus = confStatus;
		this.storeOutNo = storeOutNo;
		this.description = description;
		this.confUser = confUser;
		this.confDate = confDate;
		this.confType = confType;
		this.whId = whId;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getHsCustId()
	{
		return hsCustId;
	}

	public void setHsCustId(String hsCustId)
	{
		this.hsCustId = hsCustId;
	}

	public String getHsResNo()
	{
		return hsResNo;
	}

	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	public String getShippingId()
	{
		return shippingId;
	}

	public void setShippingId(String shippingId)
	{
		this.shippingId = shippingId;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
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

	public String getConfirmFile()
	{
		return confirmFile;
	}

	public void setConfirmFile(String confirmFile)
	{
		this.confirmFile = confirmFile;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public Integer getConfStatus()
	{
		return confStatus;
	}

	public void setConfStatus(Integer confStatus)
	{
		this.confStatus = confStatus;
	}

	public String getStoreOutNo()
	{
		return storeOutNo;
	}

	public void setStoreOutNo(String storeOutNo)
	{
		this.storeOutNo = storeOutNo;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getConfUser()
	{
		return confUser;
	}

	public void setConfUser(String confUser)
	{
		this.confUser = confUser;
	}

	public String getConfDate()
	{
		return confDate;
	}

	public void setConfDate(String confDate)
	{
		this.confDate = confDate;
	}

	public Integer getConfType()
	{
		return confType;
	}

	public void setConfType(Integer confType)
	{
		this.confType = confType;
	}

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
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
