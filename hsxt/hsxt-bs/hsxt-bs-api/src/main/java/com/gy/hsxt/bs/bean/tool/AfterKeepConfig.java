/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 售后保持关联Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: AfterKeepConfig
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月22日 上午10:35:45
 * @company: gyist
 * @version V3.0.0
 */
public class AfterKeepConfig implements Serializable {

	private static final long serialVersionUID = -1707913886833877603L;

	/** 客户号 **/
	@NotEmpty(message = "客户号不能为空")
	private String entCustId;

	/** 售后订单号 **/
	@NotEmpty(message = "售后订单号不能为空")
	private String afterOrderNo;

	/** 配置单号 **/
	@NotEmpty(message = "配置单号不能为空")
	private String confNo;

	/** 原设备序列号 **/
	@NotEmpty(message = "原设备序列号不能为空")
	private String deviceSeqNo;

	/** 操作员编号 **/
	@NotEmpty(message = "操作员编号不能为空")
	private String operNo;

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getAfterOrderNo()
	{
		return afterOrderNo;
	}

	public void setAfterOrderNo(String afterOrderNo)
	{
		this.afterOrderNo = afterOrderNo;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(String deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	public String getOperNo()
	{
		return operNo;
	}

	public void setOperNo(String operNo)
	{
		this.operNo = operNo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
