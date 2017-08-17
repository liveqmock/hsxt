/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 卡制作配置(制作单)
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: CardMarkConfig
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月20日 上午10:32:12
 * @company: gyist
 * @version V3.0.0
 */
public class CardMarkConfig implements Serializable {

	private static final long serialVersionUID = -5282991999175941679L;

	/** 订单号 **/
	@NotEmpty(message = "订单号不能为空")
	private String orderNo;

	/** 配置单号 **/
	@NotEmpty(message = "配置单号 不能为空")
	private String confNo;

	/** 供应商id **/
	@NotEmpty(message = "供应商id不能为空")
	private String supplierId;

	/** 卡样id **/
	@NotEmpty(message = "卡样id不能为空")
	private String cardStyleId;

	/** 操作员 **/
	@NotEmpty(message = "操作员不能为空")
	private String operNo;

	/** 配置说明 **/
	private String description;

	public CardMarkConfig()
	{
		super();
	}

	public CardMarkConfig(String orderNo, String confNo, String supplierId, String cardStyleId, String operNo,
			String description)
	{
		super();
		this.orderNo = orderNo;
		this.confNo = confNo;
		this.supplierId = supplierId;
		this.cardStyleId = cardStyleId;
		this.operNo = operNo;
		this.description = description;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getCardStyleId()
	{
		return cardStyleId;
	}

	public void setCardStyleId(String cardStyleId)
	{
		this.cardStyleId = cardStyleId;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
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
