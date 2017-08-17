/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 申报订单配置Bean
 * 
 * @Package: com.gy.hsxt.bs.tool.bean
 * @ClassName: ApplyOrderConfig
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月12日 下午3:30:11
 * @company: gyist
 * @version V3.0.0
 */
public class ApplyOrderConfig implements Serializable {

	private static final long serialVersionUID = -1522676244981547349L;

	/** 订单号 **/
	@NotEmpty(message = "订单号 不能为空")
	private String orderNo;

	/** 客户号 **/
	@NotEmpty(message = "客户号不能为空")
	private String entCustId;

	/** 资源费方案编号 **/
	@NotNull(message = "资源费方案编号不能为空")
	private String resFeeId;

	/** 省代码 **/
	@NotEmpty(message = "省代码不能为空")
	private String provinceNo;

	/** 操作员 **/
	@NotEmpty(message = "操作员不能为空")
	private String operNo;

	public ApplyOrderConfig()
	{
		super();
	}

	public ApplyOrderConfig(String orderNo, String entCustId, String resFeeId, String provinceNo, String operNo)
	{
		super();
		this.orderNo = orderNo;
		this.entCustId = entCustId;
		this.resFeeId = resFeeId;
		this.provinceNo = provinceNo;
		this.operNo = operNo;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getResFeeId()
	{
		return resFeeId;
	}

	public void setResFeeId(String resFeeId)
	{
		this.resFeeId = resFeeId;
	}

	public String getProvinceNo()
	{
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo)
	{
		this.provinceNo = provinceNo;
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
