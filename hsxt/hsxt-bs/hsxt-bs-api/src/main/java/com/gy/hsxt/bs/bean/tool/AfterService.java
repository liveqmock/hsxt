/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.ApprBase;
import com.gy.hsxt.bs.bean.order.DeliverInfo;

/**
 * 工具售后申请单Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: AfterService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:29:22
 * @company: gyist
 * @version V3.0.0
 */
public class AfterService extends ApprBase implements Serializable {

	private static final long serialVersionUID = 4155295422983340278L;

	/** 售后订单编号 **/
	private String afterOrderNo;

	/** 客户号 **/
	@NotEmpty(message = "客户号不能为空")
	private String entCustId;

	/** 互生号 **/
	@NotEmpty(message = "互生号不能为空")
	private String entResNo;

	/** 企业名称 **/
	@NotEmpty(message = "企业名称不能为空")
	private String entCustName;

	/** 客户类型 2 成员企业 3 托管企业 **/
	private Integer custType;

	/** 处理费用 **/
	private String disposeAmount;

	/** 收货信息编号 **/
	private String deliverId;

	/** 收货信息实体 **/
	private DeliverInfo deliver;

	/** 审批状态 0：待复核1：复核通过 **/
	private Integer status;

	/** 售后服务清单 **/
	@NotNull(message = "售后服务清单不能为空")
	private List<AfterServiceDetail> detail;

	/** 支付状态 **/
	private Integer payStatus;

	public AfterService()
	{
		super();
	}

	public AfterService(String afterOrderNo, String entCustId, String entResNo, String entCustName, Integer custType,
			Integer status)
	{
		super();
		this.afterOrderNo = afterOrderNo;
		this.entCustId = entCustId;
		this.entResNo = entResNo;
		this.entCustName = entCustName;
		this.custType = custType;
		this.status = status;
	}

	public AfterService(String afterOrderNo, String entCustId, String entResNo, String entCustName, Integer custType,
			String disposeAmount, String deliverId, Integer status, List<AfterServiceDetail> detail)
	{
		super();
		this.afterOrderNo = afterOrderNo;
		this.entCustId = entCustId;
		this.entResNo = entResNo;
		this.entCustName = entCustName;
		this.custType = custType;
		this.disposeAmount = disposeAmount;
		this.deliverId = deliverId;
		this.status = status;
		this.detail = detail;
	}

	public String getAfterOrderNo()
	{
		return afterOrderNo;
	}

	public void setAfterOrderNo(String afterOrderNo)
	{
		this.afterOrderNo = afterOrderNo;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
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

	public String getDisposeAmount()
	{
		return disposeAmount;
	}

	public void setDisposeAmount(String disposeAmount)
	{
		this.disposeAmount = disposeAmount;
	}

	public String getDeliverId()
	{
		return deliverId;
	}

	public void setDeliverId(String deliverId)
	{
		this.deliverId = deliverId;
	}

	public DeliverInfo getDeliver()
	{
		return deliver;
	}

	public void setDeliver(DeliverInfo deliver)
	{
		this.deliver = deliver;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public List<AfterServiceDetail> getDetail()
	{
		return detail;
	}

	public void setDetail(List<AfterServiceDetail> detail)
	{
		this.detail = detail;
	}

	public Integer getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(Integer payStatus)
	{
		this.payStatus = payStatus;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
