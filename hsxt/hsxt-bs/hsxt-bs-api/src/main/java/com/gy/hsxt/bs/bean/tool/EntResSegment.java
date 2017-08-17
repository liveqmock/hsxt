
/**
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 * <p>
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 企业资源段(新)
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: EntResSegment
 * @Description:
 * @author: likui
 * @date: 2016/6/14 18:23
 * @company: gyist
 * @version V3.0.0
 */
public class EntResSegment implements Serializable {

	private static final long serialVersionUID = 8727037646306155560L;

	/** 卡工具 **/
	private ToolProduct product;

	/** 资源段 **/
	private List<ResSegment> segment;

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

	public List<ResSegment> getSegment()
	{
		return segment;
	}

	public void setSegment(List<ResSegment> segment)
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
		return JSONObject.toJSONString(this);
	}
}
