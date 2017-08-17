/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 设备配置Bean
 * 
 * @Package: com.hsxt.bs.btool.bean
 * @ClassName: DeviceConfig
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午4:43:37
 * @company: gyist
 * @version V3.0.0
 */
public class DeviceConfig implements Serializable {

	private static final long serialVersionUID = -6496496527695120868L;

	/** 设备客户号 **/
	private String deviceCustId;

	/** 配置单号 **/
	private String confNo;

	/** 终端编号 **/
	private String terminalNo;

	/** 是否启用 0：否 1：是 **/
	private Boolean isUse;

	public DeviceConfig()
	{
		super();
	}

	public DeviceConfig(String deviceCustId, String confNo, String terminalNo,
			Boolean isUse)
	{
		super();
		this.deviceCustId = deviceCustId;
		this.confNo = confNo;
		this.terminalNo = terminalNo;
		this.isUse = isUse;
	}



	public String getDeviceCustId()
	{
		return deviceCustId;
	}

	public void setDeviceCustId(String deviceCustId)
	{
		this.deviceCustId = deviceCustId;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getTerminalNo()
	{
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo)
	{
		this.terminalNo = terminalNo;
	}

	public Boolean getIsUse()
	{
		return isUse;
	}

	public void setIsUse(Boolean isUse)
	{
		this.isUse = isUse;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
