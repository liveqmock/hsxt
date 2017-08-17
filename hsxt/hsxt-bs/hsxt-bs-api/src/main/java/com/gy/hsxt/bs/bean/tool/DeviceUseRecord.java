/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.Base;

/**
 * 设备使用记录Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ProductUse
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:32:14
 * @company: gyist
 * @version V3.0.0
 */
public class DeviceUseRecord extends Base implements Serializable {

	private static final long serialVersionUID = -6463046233450311668L;

	/** 领用业务编号 **/
	private String recordId;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 使用类型 1:领用 2:报损 **/
	private Integer useType;

	/** 批次号 **/
	private String batchNo;

	/** 使用人 **/
	private String useName;

	/** 使用说明 **/
	private String description;

	public DeviceUseRecord()
	{
		super();
	}

	public DeviceUseRecord(String recordId, String deviceSeqNo,
			Integer useType, String batchNo, String useName, String description)
	{
		super();
		this.recordId = recordId;
		this.deviceSeqNo = deviceSeqNo;
		this.useType = useType;
		this.batchNo = batchNo;
		this.useName = useName;
		this.description = description;
	}

	public String getRecordId()
	{
		return recordId;
	}

	public void setRecordId(String recordId)
	{
		this.recordId = recordId;
	}

	public String getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(String deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	public Integer getUseType()
	{
		return useType;
	}

	public void setUseType(Integer useType)
	{
		this.useType = useType;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public String getUseName()
	{
		return useName;
	}

	public void setUseName(String useName)
	{
		this.useName = useName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
