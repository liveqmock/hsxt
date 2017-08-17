/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 刷卡器入库信息
 * 
 * @Package: com.hsxt.bs.btool.bean
 * @ClassName: KsnEnterInfo
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午12:00:29
 * @company: gyist
 * @version V3.0.0
 */
public class KsnEnterInfo implements Serializable {

	private static final long serialVersionUID = 7973166936581215454L;

	/** 批次号 **/
	private String batchNo;

	/** 设备客户号 **/
	private String deviceCustId;

	/** 设备序列号 **/
	private String deviceSeqNo;

	public KsnEnterInfo()
	{
		super();
	}

	public KsnEnterInfo(String batchNo, String deviceCustId, String deviceSeqNo)
	{
		super();
		this.batchNo = batchNo;
		this.deviceCustId = deviceCustId;
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

	public String getDeviceCustId()
	{
		return deviceCustId;
	}

	public void setDeviceCustId(String deviceCustId)
	{
		this.deviceCustId = deviceCustId;
	}

	public String getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(String deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
