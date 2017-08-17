/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.beans;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.nt.api.common.DateUtil;

/**
 * 自定义消息的实体Bean
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: SelfMsgBase
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月6日 下午3:48:52
 * @company: gyist
 * @version V3.0.0
 */
public class SelfMsgBase implements Serializable {

	private static final long serialVersionUID = 7682421656990834496L;

	/**
	 * 消息id
	 */
	@NotEmpty(message = "消息ID不能为空")
	@Length(max = 36, message = "消息ID最大长度36")
	private String msgId;

	/**
	 * 互生号
	 */
	private String hsResNo;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 发送内容
	 */
	@NotEmpty(message = "发送内容为空")
	private String msgContent;
	/**
	 * 接收人列表
	 */
	@NotNull(message = "接收人列表不能为空")
	private String[] msgReceiver;

	/**
	 * 消息渠道 1:手机短信 2:互动消息(业务推送,替换模板) 3:电子邮件 4:互动消息(自定义,无模板)
	 */
	private int msgChannel;

	/**
	 * 优先级 0(高) 1(中) 2(低)
	 */
	@Range(min = 0, max = 2, message = "优先级取值 0 - 2")
	private int priority = 2;
	/**
	 * 发送人
	 */
	@NotEmpty(message = "发送人不能为空")
	private String sender;
	/**
	 * 发送时间
	 */
	private Date sendDate = DateUtil.now();

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

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public String getMsgContent()
	{
		return msgContent;
	}

	public void setMsgContent(String msgContent)
	{
		this.msgContent = msgContent;
	}

	public String[] getMsgReceiver()
	{
		return msgReceiver;
	}

	public void setMsgReceiver(String[] msgReceiver)
	{
		this.msgReceiver = msgReceiver;
	}

	public int getMsgChannel()
	{
		return msgChannel;
	}

	public void setMsgChannel(int msgChannel)
	{
		this.msgChannel = msgChannel;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public String getSender()
	{
		return sender;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public Date getSendDate()
	{
		return sendDate;
	}

	public void setSendDate(Date sendDate)
	{
		this.sendDate = sendDate;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
