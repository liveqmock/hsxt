/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具出库Bean(除互生卡)
 * 
 * @Package: com.hsxt.bs.btool.bean
 * @ClassName: ProductOut
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:43:11
 * @company: gyist
 * @version V3.0.0
 */
public class Out implements Serializable {

	private static final long serialVersionUID = 1177932875647732285L;

	/** 出库批次号 **/
	private String batchNo;

	/** 工具类别代码 **/
	private String categoryCode;

	/** 工具编号 **/
	private String productId;

	/** 仓库编号 **/
	private String whId;

	/** 出库类型 0:购买1:领用 **/
	private Integer outType;

	/** 出库业务编号 因为某个订单领用单出库 **/
	private String bizNo;

	/** 数量 **/
	private Integer quantity;

	/** 出库日期 **/
	private String outDate;

	/** 操作员 **/
	private String operNo;

	/** 备注 **/
	private String remark;

	public Out()
	{
		super();
	}

	public Out(String batchNo, String categoryCode, String productId,
			String whId, Integer outType, String bizNo, Integer quantity,
			String outDate, String operNo, String remark)
	{
		super();
		this.batchNo = batchNo;
		this.categoryCode = categoryCode;
		this.productId = productId;
		this.whId = whId;
		this.outType = outType;
		this.bizNo = bizNo;
		this.quantity = quantity;
		this.outDate = outDate;
		this.operNo = operNo;
		this.remark = remark;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
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

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
	}

	public Integer getOutType()
	{
		return outType;
	}

	public void setOutType(Integer outType)
	{
		this.outType = outType;
	}

	public String getBizNo()
	{
		return bizNo;
	}

	public void setBizNo(String bizNo)
	{
		this.bizNo = bizNo;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public String getOutDate()
	{
		return outDate;
	}

	public void setOutDate(String outDate)
	{
		this.outDate = outDate;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
