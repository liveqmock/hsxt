/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.order.DeliverInfo;

/**
 * 查询发货单数据(页面显示)
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ShippingData
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:33:06
 * @company: gyist
 * @version V3.0.0
 */
public class ShippingData implements Serializable {

	private static final long serialVersionUID = 5119381108717973945L;

	/** 仓库id **/
	private String whId;

	/** 赠送工具 **/
	private List<ToolProduct> giftProduct;

	/** 配套工具 **/
	private List<ToolProduct> supportProduct;

	/** 主产品 **/
	private List<ToolConfig> configs;

	/** 所有启用配送方式 **/
	private List<ShippingMethod> mothods;

	/** 收货信息 **/
	private List<DeliverInfo> delivers;

	/** 售后服务清单 **/
	private List<AfterServiceDetail> details;

	public ShippingData()
	{
		super();
	}

	public ShippingData(List<ToolProduct> giftProduct, List<ToolProduct> supportProduct, List<ToolConfig> configs,
			List<ShippingMethod> mothods, List<DeliverInfo> delivers, List<AfterServiceDetail> details)
	{
		super();
		this.giftProduct = giftProduct;
		this.supportProduct = supportProduct;
		this.configs = configs;
		this.mothods = mothods;
		this.delivers = delivers;
		this.details = details;
	}

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
	}

	public List<ToolProduct> getGiftProduct()
	{
		return giftProduct;
	}

	public void setGiftProduct(List<ToolProduct> giftProduct)
	{
		this.giftProduct = giftProduct;
	}

	public List<ToolProduct> getSupportProduct()
	{
		return supportProduct;
	}

	public void setSupportProduct(List<ToolProduct> supportProduct)
	{
		this.supportProduct = supportProduct;
	}

	public List<ToolConfig> getConfigs()
	{
		return configs;
	}

	public void setConfigs(List<ToolConfig> configs)
	{
		this.configs = configs;
	}

	public List<ShippingMethod> getMothods()
	{
		return mothods;
	}

	public void setMothods(List<ShippingMethod> mothods)
	{
		this.mothods = mothods;
	}

	public List<DeliverInfo> getDelivers()
	{
		return delivers;
	}

	public void setDelivers(List<DeliverInfo> delivers)
	{
		this.delivers = delivers;
	}

	public List<AfterServiceDetail> getDetails()
	{
		return details;
	}

	public void setDetails(List<AfterServiceDetail> details)
	{
		this.details = details;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
