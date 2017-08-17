/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 积分卡出入库Bean
 * 
 * @Package: com.hsxt.bs.btool.bean
 * @ClassName: CardInOut
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:42:57
 * @company: gyist
 * @version V3.0.0
 */
public class CardInOut implements Serializable {

	private static final long serialVersionUID = 3811643808041517870L;

	/** 出入库编号 **/
	private String inOutId;

	/** * 出入库状态 1,入库,2 出库,3 盘库,4 积分卡待入库 **/
	private Integer inOutFalg;

	/** 批次号 **/
	private String batchNo;

	/** 工具编号 **/
	private String productId;

	/** 工具名称 **/
	private String productName;

	/** 数量 **/
	private Integer inOutAmount;

	/** 仓库id **/
	private String whId;

	/** 订单号 **/
	private String relatedOrderNo;

	/** 备注 **/
	private String remark;

	/** 操作员编号 **/
	private String operNo;

	/** 供应商id **/
	private String supplierId;

	/** 出入库日期 **/
	private String inOutDate;

	public CardInOut()
	{
		super();
	}

	public CardInOut(String relatedOrderNo, String operNo)
	{
		super();
		this.relatedOrderNo = relatedOrderNo;
		this.operNo = operNo;
	}

	public CardInOut(Integer inOutFalg, String relatedOrderNo, String remark,
			String operNo, String supplierId)
	{
		super();
		this.inOutFalg = inOutFalg;
		this.relatedOrderNo = relatedOrderNo;
		this.remark = remark;
		this.operNo = operNo;
		this.supplierId = supplierId;
	}

	public CardInOut(String inOutId, Integer inOutFalg, String batchNo,
			String productId, String productName, Integer inOutAmount,
			String whId, String relatedOrderNo, String remark, String operNo,
			String supplierId)
	{
		super();
		this.inOutId = inOutId;
		this.inOutFalg = inOutFalg;
		this.batchNo = batchNo;
		this.productId = productId;
		this.productName = productName;
		this.inOutAmount = inOutAmount;
		this.whId = whId;
		this.relatedOrderNo = relatedOrderNo;
		this.remark = remark;
		this.operNo = operNo;
		this.supplierId = supplierId;
	}

	public String getInOutId()
	{
		return inOutId;
	}

	public void setInOutId(String inOutId)
	{
		this.inOutId = inOutId;
	}

	public Integer getInOutFalg()
	{
		return inOutFalg;
	}

	public void setInOutFalg(Integer inOutFalg)
	{
		this.inOutFalg = inOutFalg;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Integer getInOutAmount()
	{
		return inOutAmount;
	}

	public void setInOutAmount(Integer inOutAmount)
	{
		this.inOutAmount = inOutAmount;
	}

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
	}

	public String getRelatedOrderNo()
	{
		return relatedOrderNo;
	}

	public void setRelatedOrderNo(String relatedOrderNo)
	{
		this.relatedOrderNo = relatedOrderNo;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	public String getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getInOutDate()
	{
		return inOutDate;
	}

	public void setInOutDate(String inOutDate)
	{
		this.inOutDate = inOutDate;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
