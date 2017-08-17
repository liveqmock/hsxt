/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.nt.api.common.DateUtil;

/**
 * 消息基础Bean
 * 
 * @Package: com.gy.hsi.nt.api.beans
 * @ClassName: MsgBase
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月25日 下午4:45:56
 * @company: gyist
 * @version V3.0.0
 */
public class MsgBase implements Serializable {

	private static final long serialVersionUID = 5354547595900054712L;

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
	 * 模板名称
	 */
	private String tempName;

	/**
	 * 发送内容
	 */
	private String msgContent;
	/**
	 * 接收人列表
	 */
	@NotNull(message = "接收人列表不能为空")
	private String[] msgReceiver;
	/**
	 * 消息内容集合<key,value>
	 */
	@NotNull(message = "消息内容不能为空")
	private Map<String, String> content;

	/**
	 * 客户类型
	 */
	@NotNull(message = "客户类型不能为空")
	private Integer custType;

	/**
	 * 业务类别
	 */
	@NotEmpty(message = "业务类别不能为空")
	private String bizType;

	/**
	 * 启用资源类别 1:首段资源 2:创业资源 3:全部资源 4:正常成员企业 5:免费成员企业 备注：1-3适用于托管企业 4-5适用于成员企业
	 */
	private int buyResType;

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

	public String getTempName()
	{
		return tempName;
	}

	public void setTempName(String tempName)
	{
		this.tempName = tempName;
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

	public Map<String, String> getContent()
	{
		return content;
	}

	public void setContent(Map<String, String> content)
	{
		this.content = content;
	}

	public Integer getCustType()
	{
		return custType;
	}

	public void setCustType(Integer custType)
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

	public int getBuyResType()
	{
		return buyResType;
	}

	public void setBuyResType(int buyResType)
	{
		this.buyResType = buyResType;
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
