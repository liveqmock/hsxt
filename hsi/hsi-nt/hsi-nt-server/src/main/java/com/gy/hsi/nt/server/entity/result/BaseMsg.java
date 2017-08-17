package com.gy.hsi.nt.server.entity.result;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 基础的消息Bean
 * 
 * @Package: com.gy.hsi.nt.server.entity.result
 * @ClassName: BaseMsg
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午3:12:34
 * @company: gyist
 * @version V3.0.0
 */
public class BaseMsg implements Serializable {

	private static final long serialVersionUID = -5843574833819808621L;

	/**
	 * 消息id
	 */
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
	 * 客户类型
	 */
	private Integer custType;

	/**
	 * 业务类别
	 */
	private String bizType;

	/**
	 * 启用资源类别 1:首段资源 2:创业资源 3:全部资源 4:正常成员企业 5:免费成员企业 备注：1-3适用于托管企业 4-5适用于成员企业
	 */
	private int buyResType;

	/**
	 * 消息渠道 短信(1) 互动业务消息(2) 邮件(3) 互动系统消息(4)
	 */
	private int msgChannel;
	/**
	 * 消息类型
	 */
	private int msgType;

	/**
	 * 消息大类(参照互信消息大类定义)
	 */
	private String msgCode;

	/**
	 * 消息子类(参照互信消息大类定义)
	 */
	private String subMsgCode;

	/**
	 * 接收者
	 */
	private String msgReceiver;
	/**
	 * 消息主题
	 */
	private String msgTitle;
	/**
	 * 消息内容
	 */
	private String msgContent;
	/**
	 * 消息摘要
	 */
	private String msgSummary;
	/**
	 * 消息摘要图片URL
	 */
	private String msgSummaryPicUrl;
	/**
	 * 优先级
	 */
	private int priority;
	/**
	 * 发送者
	 */
	private String sender;
	/**
	 * 附件的文件id，如果为多个用逗号隔开'
	 */
	private String attachFileIds;
	/**
	 * 最后发送时间
	 */
	private Date lastSendDate;
	/**
	 * 创建时间
	 */
	private Date createdDate;
	/**
	 * 创建人
	 */
	private String createdby;

	public BaseMsg()
	{
		super();
	}

	public BaseMsg(String msgId, String hsResNo, String custName, String tempName, Integer custType, String bizType,
			int buyResType, int msgChannel, int msgType, String msgReceiver, String msgTitle, String msgContent,
			String msgSummary, String msgSummaryPicUrl, int priority, String sender, String attachFileIds)
	{
		super();
		this.msgId = msgId;
		this.hsResNo = hsResNo;
		this.custName = custName;
		this.tempName = tempName;
		this.custType = custType;
		this.bizType = bizType;
		this.buyResType = buyResType;
		this.msgChannel = msgChannel;
		this.msgType = msgType;
		this.msgReceiver = msgReceiver;
		this.msgTitle = msgTitle;
		this.msgContent = msgContent;
		this.msgSummary = msgSummary;
		this.msgSummaryPicUrl = msgSummaryPicUrl;
		this.priority = priority;
		this.sender = sender;
		this.attachFileIds = attachFileIds;
	}

	public BaseMsg(String msgId, String hsResNo, String custName, String tempName, Integer custType, String bizType,
			int buyResType, int msgChannel, int msgType,String msgCode,String subMsgCode, String msgReceiver, String msgTitle, String msgContent,
			String msgSummary, String msgSummaryPicUrl, int priority, String sender, String attachFileIds)
	{
		super();
		this.msgId = msgId;
		this.hsResNo = hsResNo;
		this.custName = custName;
		this.tempName = tempName;
		this.custType = custType;
		this.bizType = bizType;
		this.buyResType = buyResType;
		this.msgChannel = msgChannel;
		this.msgType = msgType;
		this.msgCode = msgCode;
		this.subMsgCode = subMsgCode;
		this.msgReceiver = msgReceiver;
		this.msgTitle = msgTitle;
		this.msgContent = msgContent;
		this.msgSummary = msgSummary;
		this.msgSummaryPicUrl = msgSummaryPicUrl;
		this.priority = priority;
		this.sender = sender;
		this.attachFileIds = attachFileIds;
	}

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

	public int getMsgType()
	{
		return msgType;
	}

	public void setMsgType(int msgType)
	{
		this.msgType = msgType;
	}

	public String getSubMsgCode()
	{
		return subMsgCode;
	}

	public void setSubMsgCode(String subMsgCode)
	{
		this.subMsgCode = subMsgCode;
	}

	public String getMsgCode()
	{
		return msgCode;
	}

	public void setMsgCode(String msgCode)
	{
		this.msgCode = msgCode;
	}

	public String getMsgReceiver()
	{
		return msgReceiver;
	}

	public void setMsgReceiver(String msgReceiver)
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

	public String getMsgSummaryPicUrl()
	{
		return msgSummaryPicUrl;
	}

	public void setMsgSummaryPicUrl(String msgSummaryPicUrl)
	{
		this.msgSummaryPicUrl = msgSummaryPicUrl;
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

	public String getAttachFileIds()
	{
		return attachFileIds;
	}

	public void setAttachFileIds(String attachFileIds)
	{
		this.attachFileIds = attachFileIds;
	}

	public Date getLastSendDate()
	{
		return lastSendDate;
	}

	public void setLastSendDate(Date lastSendDate)
	{
		this.lastSendDate = lastSendDate;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedby()
	{
		return createdby;
	}

	public void setCreatedby(String createdby)
	{
		this.createdby = createdby;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
