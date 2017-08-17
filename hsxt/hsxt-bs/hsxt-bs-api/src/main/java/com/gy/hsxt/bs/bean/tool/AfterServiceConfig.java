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
 * 售后服务配置Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: AfterServiceConfig
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月6日 下午5:03:18
 * @company: gyist
 * @version V3.0.0
 */
public class AfterServiceConfig implements Serializable {

	private static final long serialVersionUID = -633238071798977847L;

	/** 客户号 **/
	@NotEmpty(message = "客户号不能为空")
	private String entCustId;

	/** 售后订单号 **/
	@NotEmpty(message = "售后订单号不能为空")
	private String afterOrderNo;

	/** 配置单号 **/
	@NotEmpty(message = "配置单号不能为空")
	private String confNo;

	/** 原设备序列号 **/
	@NotEmpty(message = "原设备序列号不能为空")
	private String deviceSeqNo;

	/** 终端编号 **/
	@NotEmpty(message = "终端编号不能为空")
	private String terminalNo;

	/** 新设备序列号 **/
	@NotEmpty(message = "新设备序列号不能为空")
	private String newDeviceSeqNo;

	/** 工具类别 **/
	private String categoryCode;

	/** 操作员编号 **/
	@NotEmpty(message = "操作员编号不能为空")
	private String operNo;

	public AfterServiceConfig()
	{
		super();
	}

	public AfterServiceConfig(String entCustId, String afterOrderNo,
			String confNo, String deviceSeqNo, String terminalNo,
			String newDeviceSeqNo, String categoryCode, String operNo)
	{
		super();
		this.entCustId = entCustId;
		this.afterOrderNo = afterOrderNo;
		this.confNo = confNo;
		this.deviceSeqNo = deviceSeqNo;
		this.terminalNo = terminalNo;
		this.newDeviceSeqNo = newDeviceSeqNo;
		this.categoryCode = categoryCode;
		this.operNo = operNo;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getAfterOrderNo()
	{
		return afterOrderNo;
	}

	public void setAfterOrderNo(String afterOrderNo)
	{
		this.afterOrderNo = afterOrderNo;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(String deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	public String getTerminalNo()
	{
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo)
	{
		this.terminalNo = terminalNo;
	}

	public String getNewDeviceSeqNo()
	{
		return newDeviceSeqNo;
	}

	public void setNewDeviceSeqNo(String newDeviceSeqNo)
	{
		this.newDeviceSeqNo = newDeviceSeqNo;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
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
