/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具售后申请单清单
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: AfterServiceDetail
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:29:35
 * @company: gyist
 * @version V3.0.0
 */
public class AfterServiceDetail implements Serializable {

	private static final long serialVersionUID = 7235484532955227165L;

	/** 售后订单编号 **/
	private String afterOrderNo;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 终端编号 **/
	private String terminalNo;

	/** 配置单号 **/
	private String confNo;

	/** 工具类别代码 **/
	private String categoryCode;

	/** 工具编号 **/
	private String productId;

	/** 工具名称(查询) **/
	private String productName;

	/** 处理方式 0:待处理1:无故障2:重新配置3:换货 4:维修 **/
	private Integer disposeType;

	/** 新设备序列号 **/
	private String newDeviceSeqNo;

	/** 处理费用 **/
	private String disposeAmount;

	/** 处理状态false:待处理 true:已处理 **/
	private Boolean disposeStatus;

	/** 是否配置false:未配置 true:已配置 **/
	private Boolean isConfig;

	/** 备注 **/
	private String remark;

	public AfterServiceDetail()
	{
		super();
	}

	public AfterServiceDetail(String afterOrderNo, String newDeviceSeqNo, Boolean isConfig)
	{
		super();
		this.afterOrderNo = afterOrderNo;
		this.newDeviceSeqNo = newDeviceSeqNo;
		this.isConfig = isConfig;
	}

	public AfterServiceDetail(String afterOrderNo, String deviceSeqNo, String terminalNo, String categoryCode,
			String productId, Integer disposeType, Boolean disposeStatus, Boolean isConfig, String remark)
	{
		super();
		this.afterOrderNo = afterOrderNo;
		this.deviceSeqNo = deviceSeqNo;
		this.terminalNo = terminalNo;
		this.categoryCode = categoryCode;
		this.productId = productId;
		this.disposeType = disposeType;
		this.disposeStatus = disposeStatus;
		this.isConfig = isConfig;
		this.remark = remark;
	}

	public AfterServiceDetail(String afterOrderNo, String deviceSeqNo, String terminalNo, String confNo,
			String categoryCode, String productId, Integer disposeType, String newDeviceSeqNo, String disposeAmount,
			Boolean disposeStatus, Boolean isConfig, String remark)
	{
		super();
		this.afterOrderNo = afterOrderNo;
		this.deviceSeqNo = deviceSeqNo;
		this.terminalNo = terminalNo;
		this.confNo = confNo;
		this.categoryCode = categoryCode;
		this.productId = productId;
		this.disposeType = disposeType;
		this.newDeviceSeqNo = newDeviceSeqNo;
		this.disposeAmount = disposeAmount;
		this.disposeStatus = disposeStatus;
		this.isConfig = isConfig;
		this.remark = remark;
	}

	public String getAfterOrderNo()
	{
		return afterOrderNo;
	}

	public void setAfterOrderNo(String afterOrderNo)
	{
		this.afterOrderNo = afterOrderNo;
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

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
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

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Integer getDisposeType()
	{
		return disposeType;
	}

	public void setDisposeType(Integer disposeType)
	{
		this.disposeType = disposeType;
	}

	public String getNewDeviceSeqNo()
	{
		return newDeviceSeqNo;
	}

	public void setNewDeviceSeqNo(String newDeviceSeqNo)
	{
		this.newDeviceSeqNo = newDeviceSeqNo;
	}

	public String getDisposeAmount()
	{
		return disposeAmount;
	}

	public void setDisposeAmount(String disposeAmount)
	{
		this.disposeAmount = disposeAmount;
	}

	public Boolean getDisposeStatus()
	{
		return disposeStatus;
	}

	public void setDisposeStatus(Boolean disposeStatus)
	{
		this.disposeStatus = disposeStatus;
	}

	public Boolean getIsConfig()
	{
		return isConfig;
	}

	public void setIsConfig(Boolean isConfig)
	{
		this.isConfig = isConfig;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
