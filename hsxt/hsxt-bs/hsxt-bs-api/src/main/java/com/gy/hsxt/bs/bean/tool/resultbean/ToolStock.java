/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 分页查询工具库存返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: ToolStockStatistics
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月26日 下午4:40:00
 * @company: gyist
 * @version V3.0.0
 */
public class ToolStock implements Serializable {

	private static final long serialVersionUID = 3564369254416411401L;

	/** 工具类别代码 **/
	private String categoryCode;

	/** 工具名称 **/
	private String productName;

	/** 批次号 **/
	private String batchNo;

	/** 仓库名称 **/
	private String whName;

	/** 入库数量 **/
	private Integer enterNum;

	/** 出库数量 **/
	private Integer outNum;

	/** 应存数量 **/
	private Integer shouldNum;

	/** 实存数量 **/
	private Integer actualNum;

	public ToolStock()
	{
		super();
	}

	public ToolStock(String categoryCode, String productName,
			String batchNo, String whName, Integer enterNum, Integer outNum,
			Integer shouldNum, Integer actualNum)
	{
		super();
		this.categoryCode = categoryCode;
		this.productName = productName;
		this.batchNo = batchNo;
		this.whName = whName;
		this.enterNum = enterNum;
		this.outNum = outNum;
		this.shouldNum = shouldNum;
		this.actualNum = actualNum;
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

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public String getWhName()
	{
		return whName;
	}

	public void setWhName(String whName)
	{
		this.whName = whName;
	}

	public Integer getEnterNum()
	{
		return enterNum;
	}

	public void setEnterNum(Integer enterNum)
	{
		this.enterNum = enterNum;
	}

	public Integer getOutNum()
	{
		return outNum;
	}

	public void setOutNum(Integer outNum)
	{
		this.outNum = outNum;
	}

	public Integer getShouldNum()
	{
		return shouldNum;
	}

	public void setShouldNum(Integer shouldNum)
	{
		this.shouldNum = shouldNum;
	}

	public Integer getActualNum()
	{
		return actualNum;
	}

	public void setActualNum(Integer actualNum)
	{
		this.actualNum = actualNum;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
