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
import com.gy.hsxt.bs.bean.order.Order;

/**
 * 订单Bean包括(订单|工具配置单|收货信息)
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: OrderBean
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:34:04
 * @company: gyist
 * @version V3.0.0
 */
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 7979928659058519978L;

	/** 订单信息实体 **/
	private Order order;

	/** 配置清单列表 **/
	private List<ToolConfig> confs;

	/** 收货信息 **/
	private DeliverInfo deliverInfo;

	/** 消费者互生号列表 **/
	private List<String> perResNos;

	/** 售后清单 **/
	private List<AfterServiceDetail> afterDetail;

	public OrderBean()
	{
		super();
	}

	public OrderBean(Order order, List<ToolConfig> confs, DeliverInfo deliverInfo, List<String> perResNos)
	{
		super();
		this.order = order;
		this.confs = confs;
		this.deliverInfo = deliverInfo;
		this.perResNos = perResNos;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	public List<ToolConfig> getConfs()
	{
		return confs;
	}

	public void setConfs(List<ToolConfig> confs)
	{
		this.confs = confs;
	}

	public DeliverInfo getDeliverInfo()
	{
		return deliverInfo;
	}

	public void setDeliverInfo(DeliverInfo deliverInfo)
	{
		this.deliverInfo = deliverInfo;
	}

	public List<String> getPerResNos()
	{
		return perResNos;
	}

	public void setPerResNos(List<String> perResNos)
	{
		this.perResNos = perResNos;
	}

	public List<AfterServiceDetail> getAfterDetail()
	{
		return afterDetail;
	}

	public void setAfterDetail(List<AfterServiceDetail> afterDetail)
	{
		this.afterDetail = afterDetail;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
