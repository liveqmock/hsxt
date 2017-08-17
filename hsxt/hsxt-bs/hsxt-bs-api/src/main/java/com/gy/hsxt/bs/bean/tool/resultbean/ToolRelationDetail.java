/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具关联详情Bean
 * 
 * @Package: com.gy.hsxt.bs.tool.bean
 * @ClassName: ToolRelationDetail
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月15日 下午5:26:19
 * @company: gyist
 * @version V3.0.0
 */
public class ToolRelationDetail implements Serializable {

	private static final long serialVersionUID = -8488807575956515320L;

	/** 终端编号 **/
	private String terminalNo;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 批次号 **/
	private String batchNo;

	/** 设备客户号 **/
	private String deviceCustId;

	/** 供应商名称 **/
	private String supplierName;

	/** 联系人 **/
	private String linkMan;

	/** 手机 **/
	private String mobile;

	/** 仓库名称 **/
	private String whName;

	public ToolRelationDetail()
	{
		super();
	}

	public ToolRelationDetail(String terminalNo, String deviceSeqNo,
			String batchNo, String deviceCustId, String supplierName,
			String linkMan, String mobile, String whName)
	{
		super();
		this.terminalNo = terminalNo;
		this.deviceSeqNo = deviceSeqNo;
		this.batchNo = batchNo;
		this.deviceCustId = deviceCustId;
		this.supplierName = supplierName;
		this.linkMan = linkMan;
		this.mobile = mobile;
		this.whName = whName;
	}

	public String getTerminalNo()
	{
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo)
	{
		this.terminalNo = terminalNo;
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

	public String getDeviceCustId()
	{
		return deviceCustId;
	}

	public void setDeviceCustId(String deviceCustId)
	{
		this.deviceCustId = deviceCustId;
	}

	public String getSupplierName()
	{
		return supplierName;
	}

	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
	}

	public String getLinkMan()
	{
		return linkMan;
	}

	public void setLinkMan(String linkMan)
	{
		this.linkMan = linkMan;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
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
