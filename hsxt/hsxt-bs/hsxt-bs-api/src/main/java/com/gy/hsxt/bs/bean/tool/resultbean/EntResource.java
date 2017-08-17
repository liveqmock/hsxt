/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.tool.ToolProduct;

/**
 * 企业资源段
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: EntResource
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月14日 下午7:32:01
 * @company: gyist
 * @version V3.0.0
 */
public class EntResource implements Serializable {

	private static final long serialVersionUID = -8449242254996800124L;

	/** 卡工具 **/
	private ToolProduct product;

	/** 段数 **/
	private List<ResourceSegment> segment;

	/** 开始购买段数 **/
	private int startBuyRes;

	public ToolProduct getProduct()
	{
		return product;
	}

	public void setProduct(ToolProduct product)
	{
		this.product = product;
	}

	public List<ResourceSegment> getSegment()
	{
		return segment;
	}

	public void setSegment(List<ResourceSegment> segment)
	{
		this.segment = segment;
	}

	public int getStartBuyRes()
	{
		return startBuyRes;
	}

	public void setStartBuyRes(int startBuyRes)
	{
		this.startBuyRes = startBuyRes;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
