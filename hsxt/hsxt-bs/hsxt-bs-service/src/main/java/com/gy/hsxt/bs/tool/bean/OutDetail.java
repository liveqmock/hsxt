/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具出库清单
 * 
 * @Package: com.hsxt.bs.btool.bean
 * @ClassName: OutDetail
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月8日 下午6:09:41
 * @company: gyist
 * @version V3.0.0
 */
public class OutDetail implements Serializable {

	private static final long serialVersionUID = -446319149452636570L;

	/** 出库批次号 **/
	private String outBatchNo;
	/** 设备客户号 **/
	private String deviceCustId;

	public OutDetail()
	{
		super();
	}

	public OutDetail(String outBatchNo, String deviceCustId)
	{
		super();
		this.outBatchNo = outBatchNo;
		this.deviceCustId = deviceCustId;
	}

	public String getOutBatchNo()
	{
		return outBatchNo;
	}

	public void setOutBatchNo(String outBatchNo)
	{
		this.outBatchNo = outBatchNo;
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
