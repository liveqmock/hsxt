/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具入库出库流水分页返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.pageresult
 * @ClassName: ToolEnterOutPage
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:26:31
 * @company: gyist
 * @version V3.0.0
 */
public class ToolEnterOutPage implements Serializable {

	private static final long serialVersionUID = 3312564799655485210L;

	/** 工具名称 **/
	private String productName;

	/** 工具类别代码 **/
	private String categoryCode;

	/** 批次号 **/
	private String batchNo;

	/** 数量 **/
	private Integer quantity;

	/** 操作日期(入库/出库日期) **/
	private String operDate;

	/** 仓库名称 **/
	private String whName;

	/** 操作员 **/
	private String operNo;

	public ToolEnterOutPage()
	{
		super();
	}

	public ToolEnterOutPage(String productName, String categoryCode, String batchNo, Integer quantity, String operDate,
			String operNo)
	{
		super();
		this.productName = productName;
		this.categoryCode = categoryCode;
		this.batchNo = batchNo;
		this.quantity = quantity;
		this.operDate = operDate;
		this.operNo = operNo;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public String getOperDate()
	{
		return operDate;
	}

	public void setOperDate(String operDate)
	{
		this.operDate = operDate;
	}

	public String getWhName()
	{
		return whName;
	}

	public void setWhName(String whName)
	{
		this.whName = whName;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
