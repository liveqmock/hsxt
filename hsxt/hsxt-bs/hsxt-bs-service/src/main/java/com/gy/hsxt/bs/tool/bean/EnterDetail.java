/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具入库清单Bean
 * 
 * @Package: com.hsxt.bs.btool.bean
 * @ClassName: EnterDetail
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午6:56:44
 * @company: gyist
 * @version V3.0.0
 */
public class EnterDetail implements Serializable {

	private static final long serialVersionUID = -7054120016759197543L;

	/** 批次号 **/
	private String batchNo;

	/** 设备客户号 **/
	private String deviceCustId;

	public EnterDetail()
	{
		super();
	}

	public EnterDetail(String batchNo, String deviceCustId)
	{
		super();
		this.batchNo = batchNo;
		this.deviceCustId = deviceCustId;
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

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
