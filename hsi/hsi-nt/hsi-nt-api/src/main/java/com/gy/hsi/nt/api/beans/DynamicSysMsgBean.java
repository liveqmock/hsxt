package com.gy.hsi.nt.api.beans;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.nt.api.common.DateUtil;
import com.gy.hsi.nt.api.common.MsgChannel;

/**
 * 互动消息接收bean(系统消息)
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: DynamicMsgBean
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 上午9:24:59
 * @company: gyist
 * @version V3.0.0
 */
public final class DynamicSysMsgBean implements Serializable {

	private static final long serialVersionUID = -8625233137759909194L;

	/**
	 * 消息ID
	 */
	@NotEmpty(message = "消息ID不能为空")
	@Length(max = 36, message = "消息ID最大长度36")
	private String msgId;
	/**
	 * 接收人列表
	 */
	@NotNull(message = "接收人列表不能为空")
	private String[] msgReceiver;
	/**
	 * 消息主题
	 */
	@NotEmpty(message = "消息主题不能为空")
	@Length(max = 150, message = "互动消息主题最大长度150")
	private String msgTitle;
	/**
	 * 消息内容
	 */
	@NotEmpty(message = "消息内容不能为空")
	private String msgContent;
	/**
	 * 消息摘要
	 */
	@Length(max = 800, message = "互动消息摘要最大长度800")
	private String msgSummary;
	/**
	 * 消息摘要图片URL地址
	 */
	private String[] msgSummaryPicUrl;
	/**
	 * 消息发送方式 公开信12(公开信) 20(私信)
	 */
	@NotNull(message = "消息发送方式 不能为空")
	private Integer msgType;
	/**
	 * 优先级 0(高) 1(中) 2(低)
	 */
	@Range(min = 0, max = 2, message = "优先级取值 0 - 2")
	private int priority = 2;
	/**
	 * 发送人(客户号)
	 */
	@NotEmpty(message = "发送人不能为空")
	@Length(min = 11, message = "发送人最小长度11")
	private String sender;
	/**
	 * 发送时间
	 */
	private Date sendDate = DateUtil.now();
	/**
	 * 通知系统内部使用字段(消息渠道)互生消息(系统消息)
	 */
	private int msgChannel = MsgChannel.DYNAMIC_SYS.getChannel();

	public DynamicSysMsgBean()
	{
		super();
	}

	public String getMsgId()
	{
		return msgId;
	}

	public void setMsgId(String msgId)
	{
		this.msgId = msgId;
	}

	public String[] getMsgReceiver()
	{
		return msgReceiver;
	}

	public void setMsgReceiver(String[] msgReceiver)
	{
		this.msgReceiver = msgReceiver;
	}

	public String getMsgTitle()
	{
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle)
	{
		this.msgTitle = msgTitle;
	}

	public String getMsgContent()
	{
		return msgContent;
	}

	public void setMsgContent(String msgContent)
	{
		this.msgContent = msgContent;
	}

	public String getMsgSummary()
	{
		return msgSummary;
	}

	public void setMsgSummary(String msgSummary)
	{
		this.msgSummary = msgSummary;
	}

	public String[] getMsgSummaryPicUrl()
	{
		return msgSummaryPicUrl;
	}

	public void setMsgSummaryPicUrl(String[] msgSummaryPicUrl)
	{
		this.msgSummaryPicUrl = msgSummaryPicUrl;
	}

	public Integer getMsgType()
	{
		return msgType;
	}

	public void setMsgType(Integer msgType)
	{
		this.msgType = msgType;
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

	public int getMsgChannel()
	{
		return msgChannel;
	}

	public void setMsgChannel(int msgChannel)
	{
		this.msgChannel = msgChannel;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
