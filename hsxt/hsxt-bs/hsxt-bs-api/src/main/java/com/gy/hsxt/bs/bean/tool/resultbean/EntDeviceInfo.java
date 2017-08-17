/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 企业设备信息
 * 
 * @Package: com.gy.hsxt.bs.tool.bean
 * @ClassName: EntDeviceInfo
 * @Description:
 * @author: likui
 * @date: 2016年2月27日 上午10:51:02
 * @company: gyist
 * @version V3.0.0
 */
public class EntDeviceInfo implements Serializable {

	private static final long serialVersionUID = -1860880421056794927L;

	/** 企业互生号 **/
	private String entResNo;

	/** 企业客户号 **/
	private String entCustId;

	/** 企业名称 **/
	private String entCustName;

	/** 订单编号 **/
	private String orderNo;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 工具类别 **/
	private String categoryCode;

	/** 终端编号 **/
	private String terminalNo;

	/** 设备使用状态 0 ：未使用1 ：已使用2 ：已报损3 ：已领用4 ：已返修 5:已报废 **/
	private Integer useStatus;

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

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(String deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getTerminalNo()
	{
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo)
	{
		this.terminalNo = terminalNo;
	}

	public Integer getUseStatus()
	{
		return useStatus;
	}

	public void setUseStatus(Integer useStatus)
	{
		this.useStatus = useStatus;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
