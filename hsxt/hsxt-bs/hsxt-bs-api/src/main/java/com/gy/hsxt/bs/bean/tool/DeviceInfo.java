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
 * 设备信息Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: DeviceInfo
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:30:59
 * @company: gyist
 * @version V3.0.0
 */
public class DeviceInfo extends Base implements Serializable {

	private static final long serialVersionUID = 9111903965340457583L;

	/** 设备客户号 **/
	private String deviceCustId;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 工具类别 **/
	private String categoryCode;

	/** 工具编号 **/
	private String productId;

	/** 设备库存状态 1：已入库 2：已出库 **/
	private Integer storeStatus;

	/** 设备使用状态 0 ：未使用1 ：已使用2 ：已报损3 ：已领用4 ：已返修 5:已报废 **/
	private Integer useStatus;

	/** 仓库id **/
	private String whId;

	/** 批次号 **/
	private String batchNo;

	public DeviceInfo()
	{
		super();
	}

	public DeviceInfo(String deviceCustId, String deviceSeqNo, String categoryCode, String productId,
			Integer storeStatus, String whId)
	{
		super();
		this.deviceCustId = deviceCustId;
		this.deviceSeqNo = deviceSeqNo;
		this.categoryCode = categoryCode;
		this.productId = productId;
		this.storeStatus = storeStatus;
		this.whId = whId;
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

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public Integer getStoreStatus()
	{
		return storeStatus;
	}

	public void setStoreStatus(Integer storeStatus)
	{
		this.storeStatus = storeStatus;
	}

	public Integer getUseStatus()
	{
		return useStatus;
	}

	public void setUseStatus(Integer useStatus)
	{
		this.useStatus = useStatus;
	}

	public String getWhId()
	{
		return whId;
	}

	public void setWhId(String whId)
	{
		this.whId = whId;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
