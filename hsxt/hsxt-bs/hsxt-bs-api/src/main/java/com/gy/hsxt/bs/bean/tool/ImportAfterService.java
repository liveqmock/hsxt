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
 * 批量导入售后Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ImportAfterService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:31:28
 * @company: gyist
 * @version V3.0.0
 */
public class ImportAfterService implements Serializable {

	private static final long serialVersionUID = -4132209514403756593L;

	/** 企业互生号 **/
	@NotEmpty(message = "企业互生号不能为空")
	private String entResNo;

	/** 设备序列号 **/
	@NotEmpty(message = "设备序列号不能为空")
	private String deviceSeqNo;

	/** 终端编号 **/
	@NotEmpty(message = "终端编号不能为空")
	private String terminalNo;

	/** 设备标示 1:存在 2:不存在 3:设备不是已使用状态 **/
	private Integer deviceFalg;

	public ImportAfterService()
	{
		super();
	}

	public ImportAfterService(String entResNo, String deviceSeqNo, String terminalNo)
	{
		super();
		this.entResNo = entResNo;
		this.deviceSeqNo = deviceSeqNo;
		this.terminalNo = terminalNo;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
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

	public Integer getDeviceFalg()
	{
		return deviceFalg;
	}

	public void setDeviceFalg(Integer deviceFalg)
	{
		this.deviceFalg = deviceFalg;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
