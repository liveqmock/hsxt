/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具盘库Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: WhInventory
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月26日 下午7:38:21
 * @company: gyist
 * @version V3.0.0
 */
public class WhInventory implements Serializable {

	private static final long serialVersionUID = 8055140781063566903L;

	/** 盘库id **/
	private String inventoryId;

	/** 入库批次号 **/
	private String enterNo;

	/** 应存数量 **/
	private Integer shouldQuantity;

	/** 盘库数量 **/
	private Integer quantity;

	/** 盘库操作员 **/
	private String operNo;

	/** 盘库日期 **/
	private String inventoryDate;

	public WhInventory()
	{
		super();
	}

	public WhInventory(String inventoryId, String enterNo,
			Integer shouldQuantity, Integer quantity, String operNo,
			String inventoryDate)
	{
		super();
		this.inventoryId = inventoryId;
		this.enterNo = enterNo;
		this.shouldQuantity = shouldQuantity;
		this.quantity = quantity;
		this.operNo = operNo;
		this.inventoryDate = inventoryDate;
	}

	public String getInventoryId()
	{
		return inventoryId;
	}

	public void setInventoryId(String inventoryId)
	{
		this.inventoryId = inventoryId;
	}

	public String getEnterNo()
	{
		return enterNo;
	}

	public void setEnterNo(String enterNo)
	{
		this.enterNo = enterNo;
	}

	public Integer getShouldQuantity()
	{
		return shouldQuantity;
	}

	public void setShouldQuantity(Integer shouldQuantity)
	{
		this.shouldQuantity = shouldQuantity;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	public String getInventoryDate()
	{
		return inventoryDate;
	}

	public void setInventoryDate(String inventoryDate)
	{
		this.inventoryDate = inventoryDate;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
