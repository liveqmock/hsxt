/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.beans;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 消息查询Bean
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: MsgQueryBean
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 上午11:50:32
 * @company: gyist
 * @version V3.0.0
 */
public class MsgQueryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息ID
	 */
	private String msgId;

	/**
	 * 互生号
	 */
	private String hsResNo;

	/**
	 * 模板名称
	 */
	private String tempName;

	/**
	 * 客户类型
	 */
	private int custType;

	/**
	 * 业务类型
	 */
	private String bizType;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 接受者
	 */
	private String msgReceiver;

	/**
	 * 发送时间
	 */
	private String sendDate;

	/**
	 * 发送状态
	 */
	private String status;

	/**
	 * 消息内容
	 */
	private String msgContent;

	public String getMsgId()
	{
		return msgId;
	}

	public void setMsgId(String msgId)
	{
		this.msgId = msgId;
	}

	public String getHsResNo()
	{
		return hsResNo;
	}

	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	public String getTempName()
	{
		return tempName;
	}

	public void setTempName(String tempName)
	{
		this.tempName = tempName;
	}

	public int getCustType()
	{
		return custType;
	}

	public void setCustType(int custType)
	{
		this.custType = custType;
	}

	public String getBizType()
	{
		return bizType;
	}

	public void setBizType(String bizType)
	{
		this.bizType = bizType;
	}

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public String getMsgReceiver()
	{
		return msgReceiver;
	}

	public void setMsgReceiver(String msgReceiver)
	{
		this.msgReceiver = msgReceiver;
	}

	public String getSendDate()
	{
		return sendDate;
	}

	public void setSendDate(String sendDate)
	{
		this.sendDate = sendDate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getMsgContent()
	{
		return msgContent;
	}

	public void setMsgContent(String msgContent)
	{
		this.msgContent = msgContent;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
