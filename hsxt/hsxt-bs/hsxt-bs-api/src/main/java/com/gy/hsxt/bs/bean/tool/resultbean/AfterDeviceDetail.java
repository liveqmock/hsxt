/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 售后设备详情Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: AfterDeviceDetail
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月7日 下午12:21:31
 * @company: gyist
 * @version V3.0.0
 */
public class AfterDeviceDetail implements Serializable {

	private static final long serialVersionUID = 821550203229474308L;

	/** 售后订单号 **/
	private String afterOrderNo;

	/** 配置单号 **/
	private String confNo;

	/** 原设备序列号 **/
	private String deviceSeqNo;

	/** 终端编号 **/
	private String terminalNo;

	/** 是否配置 **/
	private Boolean isConfig;

	public AfterDeviceDetail()
	{
		super();
	}

	public AfterDeviceDetail(String afterOrderNo, String confNo,
			String deviceSeqNo, String terminalNo, Boolean isConfig)
	{
		super();
		this.afterOrderNo = afterOrderNo;
		this.confNo = confNo;
		this.deviceSeqNo = deviceSeqNo;
		this.terminalNo = terminalNo;
		this.isConfig = isConfig;
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

	public Boolean getIsConfig()
	{
		return isConfig;
	}

	public void setIsConfig(Boolean isConfig)
	{
		this.isConfig = isConfig;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
