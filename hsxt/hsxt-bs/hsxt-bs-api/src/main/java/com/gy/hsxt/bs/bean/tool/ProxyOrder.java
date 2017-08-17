/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.ApprBase;
import com.gy.hsxt.bs.bean.order.DeliverInfo;

/**
 * 平台代购订单Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ProxyOrder
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:32:27
 * @company: gyist
 * @version V3.0.0
 */
public class ProxyOrder extends ApprBase implements Serializable {

	private static final long serialVersionUID = 4676595859513416386L;

	/** 代购订单编号 **/
	private String proxyOrderNo;

	/** 企业互生号 **/
	@NotEmpty(message = "企业互生号不能为空")
	@Pattern(regexp = "^[0-9]{11}$", message = "企业互生号必须为11位数字")
	private String entResNo;

	/** 企业客户号 **/
	@NotEmpty(message = "企业客户号不能为空")
	private String entCustId;

	/** 企业名称 **/
	@NotEmpty(message = "企业名称不能为空")
	private String entCustName;

	/** 客户类型 **/
	@NotNull(message = "客户类型不能为空")
	private Integer custType;

	/** 代购订单类型 **/
	@NotEmpty(message = "代购订单类型不能为空")
	private String orderType;

	/** 代购订单金额 **/
	@NotEmpty(message = "代购订单金额不能为空")
	private String orderAmount;

	/** 代购订单币种 **/
	@NotEmpty(message = "代购订单币种不能为空")
	private String currencyCode;

	/** 代购订单备注 **/
	private String orderRemark;

	/** 收货信息编号 **/
	private String deliverId;

	/** 审批状态 0：待复核1：复核通过2：复核驳回 **/
	private Integer status;

	/** 代购清单 **/
	private List<ProxyOrderDetail> detail;

	/** 收货信息 **/
	private DeliverInfo deliverInfo;

	public ProxyOrder()
	{
		super();
	}

	public ProxyOrder(String proxyOrderNo, String entResNo, String entCustId, String entCustName, Integer custType,
			String orderAmount, String currencyCode, String orderRemark, String deliverId, Integer status,
			List<ProxyOrderDetail> detail, DeliverInfo deliverInfo)
	{
		super();
		this.proxyOrderNo = proxyOrderNo;
		this.entResNo = entResNo;
		this.entCustId = entCustId;
		this.entCustName = entCustName;
		this.custType = custType;
		this.orderAmount = orderAmount;
		this.currencyCode = currencyCode;
		this.orderRemark = orderRemark;
		this.deliverId = deliverId;
		this.status = status;
		this.detail = detail;
		this.deliverInfo = deliverInfo;
	}

	public String getProxyOrderNo()
	{
		return proxyOrderNo;
	}

	public void setProxyOrderNo(String proxyOrderNo)
	{
		this.proxyOrderNo = proxyOrderNo;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getEntCustName()
	{
		return entCustName;
	}

	public void setEntCustName(String entCustName)
	{
		this.entCustName = entCustName;
	}

	public Integer getCustType()
	{
		return custType;
	}

	public void setCustType(Integer custType)
	{
		this.custType = custType;
	}

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public String getOrderAmount()
	{
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount)
	{
		this.orderAmount = orderAmount;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public String getOrderRemark()
	{
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark)
	{
		this.orderRemark = orderRemark;
	}

	public String getDeliverId()
	{
		return deliverId;
	}

	public void setDeliverId(String deliverId)
	{
		this.deliverId = deliverId;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public List<ProxyOrderDetail> getDetail()
	{
		return detail;
	}

	public void setDetail(List<ProxyOrderDetail> detail)
	{
		this.detail = detail;
	}

	public DeliverInfo getDeliverInfo()
	{
		return deliverInfo;
	}

	public void setDeliverInfo(DeliverInfo deliverInfo)
	{
		this.deliverInfo = deliverInfo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
