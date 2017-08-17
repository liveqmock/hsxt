/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 设备信息分页返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: DeviceInfoPage
 * @Description: TODO
 * @author: likui
 * @date: 2016年2月22日 上午11:21:57
 * @company: gyist
 * @version V3.0.0
 */
public class DeviceInfoPage implements Serializable {

	private static final long serialVersionUID = 1032900791026096991L;

	/** 工具类别 **/
	private String categoryCode;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 批次号 **/
	private String batchNo;

	/** 仓库名称 **/
	private String whName;

	/** 入库时间 **/
	private String enterDate;

	/** 出库时间 **/
	private String outDate;

	/** 设备使用状态 0 ：未使用1 ：已使用2 ：已报损3 ：已领用4 ：已返修5:已报废 **/
	private Integer useStatus;

	/** 企业互生号 **/
	private String entResNo;

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(String deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public String getWhName()
	{
		return whName;
	}

	public void setWhName(String whName)
	{
		this.whName = whName;
	}

	public String getEnterDate()
	{
		return enterDate;
	}

	public void setEnterDate(String enterDate)
	{
		this.enterDate = enterDate;
	}

	public String getOutDate()
	{
		return outDate;
	}

	public void setOutDate(String outDate)
	{
		this.outDate = outDate;
	}

	public Integer getUseStatus()
	{
		return useStatus;
	}

	public void setUseStatus(Integer useStatus)
	{
		this.useStatus = useStatus;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
