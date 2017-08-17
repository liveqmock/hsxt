/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 积分刷卡器Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: PointKSN
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:31:44
 * @company: gyist
 * @version V3.0.0
 */
public class PointKSN implements Serializable {

	private static final long serialVersionUID = -5210667366054214757L;

	/** 设备客户号 **/
	private String deviceCustId;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 批次号 **/
	private String batchNo;

	/** KSN码 **/
	private String ksnCode;

	public PointKSN()
	{
		super();
	}

	public PointKSN(String deviceCustId, String deviceSeqNo, String batchNo,
			String ksnCode)
	{
		super();
		this.deviceCustId = deviceCustId;
		this.deviceSeqNo = deviceSeqNo;
		this.batchNo = batchNo;
		this.ksnCode = ksnCode;
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

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public String getKsnCode()
	{
		return ksnCode;
	}

	public void setKsnCode(String ksnCode)
	{
		this.ksnCode = ksnCode;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
