/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具验证Bean
 * 
 * @Package: com.gy.hsxt.bs.tool.bean
 * @ClassName: ToolVaild
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月7日 下午3:43:39
 * @company: gyist
 * @version V3.0.0
 */
public class ToolVaild implements Serializable {

	private static final long serialVersionUID = 6899785408636347725L;

	/** 工具类型 **/
	private String categoryCode;

	/** 数量 **/
	private int quantity;

	public ToolVaild()
	{
		super();
	}

	public ToolVaild(String categoryCode, int quantity)
	{
		super();
		this.categoryCode = categoryCode;
		this.quantity = quantity;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
