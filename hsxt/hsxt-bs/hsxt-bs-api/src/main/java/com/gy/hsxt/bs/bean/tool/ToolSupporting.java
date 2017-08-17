/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具配套关系Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ToolSupporting
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:34:41
 * @company: gyist
 * @version V3.0.0
 */
public class ToolSupporting implements Serializable {

	private static final long serialVersionUID = 4644674905876826822L;
	/** 主产品 **/
	private String mainProductId;

	/** 配套产品 **/
	private String supportingProductId;

	/** 配套比例 **/
	private Double supportRate;

	/** 配套说明 **/
	private String description;

	public String getMainProductId()
	{
		return mainProductId;
	}

	public void setMainProductId(String mainProductId)
	{
		this.mainProductId = mainProductId;
	}

	public String getSupportingProductId()
	{
		return supportingProductId;
	}

	public void setSupportingProductId(String supportingProductId)
	{
		this.supportingProductId = supportingProductId;
	}

	public Double getSupportRate()
	{
		return supportRate;
	}

	public void setSupportRate(Double supportRate)
	{
		this.supportRate = supportRate;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
