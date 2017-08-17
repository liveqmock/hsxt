/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 入库Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: Enter
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:31:13
 * @company: gyist
 * @version V3.0.0
 */
public class Enter implements Serializable {

	private static final long serialVersionUID = 1060714680882859288L;

	/** 入库批次号 **/
	private String enterBatchNo;

	/** 工具类别 **/
	@NotEmpty(message = "工具类别不能为空")
	private String categoryCode;

	/** 工具编号 **/
	@NotEmpty(message = "工具编号不能为空")
	private String productId;

	/** 仓库编号 **/
	@NotEmpty(message = "仓库编号不能为空")
	private String whId;

	/** 供应商编号 **/
	@NotEmpty(message = "供应商编号不能为空")
	private String supplierId;

	/** 入库数量 **/
	private Integer quantity;

	/** 入库日期 **/
	private String enterDate;

	/** 进货价 **/
	private String purchasePrice;

	/** 市场价 **/
	private String marketPrice;

	/** 入库总价 **/
	private String totalAmount;

	/** 入库操作员 **/
	@NotEmpty(message = "入库操作员不能为空")
	private String operNo;

	/** 备注 **/
	private String remark;

	/** POS机或平板的序列号 **/
	private List<String> deviceSeqNo;

	public Enter()
	{
		super();
	}

	public Enter(String enterBatchNo, String categoryCode, String productId, String whId, String supplierId,
			Integer quantity, String enterDate, String purchasePrice, String marketPrice, String totalAmount,
			String operNo, String remark, List<String> deviceSeqNo)
	{
		super();
		this.enterBatchNo = enterBatchNo;
		this.categoryCode = categoryCode;
		this.productId = productId;
		this.whId = whId;
		this.supplierId = supplierId;
		this.quantity = quantity;
		this.enterDate = enterDate;
		this.purchasePrice = purchasePrice;
		this.marketPrice = marketPrice;
		this.totalAmount = totalAmount;
		this.operNo = operNo;
		this.remark = remark;
		this.deviceSeqNo = deviceSeqNo;
	}

	public String getEnterBatchNo()
	{
		return enterBatchNo;
	}

	public void setEnterBatchNo(String enterBatchNo)
	{
		this.enterBatchNo = enterBatchNo;
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

	public String getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public String getEnterDate()
	{
		return enterDate;
	}

	public void setEnterDate(String enterDate)
	{
		this.enterDate = enterDate;
	}

	public String getPurchasePrice()
	{
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice)
	{
		this.purchasePrice = purchasePrice;
	}

	public String getMarketPrice()
	{
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice)
	{
		this.marketPrice = marketPrice;
	}

	public String getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount)
	{
		this.totalAmount = totalAmount;
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

	public List<String> getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(List<String> deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
