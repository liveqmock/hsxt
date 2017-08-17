/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 设备使用查询返回Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: DeviceDetail
 * @Description:
 * @author: likui
 * @date: 2015年10月28日 上午11:28:48
 * @company: gyist
 * @version V3.0.0
 */
public class DeviceDetail implements Serializable {

	private static final long serialVersionUID = -1507053513929725351L;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 批次号 **/
	private String batchNo;

	/** 工具名称 **/
	private String productName;

	/** 工具类别 **/
	private String categoryCode;

	/** 仓库名称 **/
	private String whName;

	public DeviceDetail()
	{
		super();
	}

	public DeviceDetail(String deviceSeqNo, String batchNo, String productName)
	{
		super();
		this.deviceSeqNo = deviceSeqNo;
		this.batchNo = batchNo;
		this.productName = productName;
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

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getWhName()
	{
		return whName;
	}

	public void setWhName(String whName)
	{
		this.whName = whName;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
