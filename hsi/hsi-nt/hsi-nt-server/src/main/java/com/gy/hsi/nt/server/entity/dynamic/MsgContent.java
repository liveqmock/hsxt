/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.server.entity.dynamic;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 消息内容
 * 
 * @Package: com.gy.hsi.nt.server.entity.dynamic
 * @ClassName: MsgContent
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月24日 下午4:30:52
 * @company: gyist
 * @version V3.0.0
 */
public class MsgContent implements Serializable {

	private static final long serialVersionUID = 7375663859415761814L;

	/**
	 * 消息ID
	 */
	private String msgid;
	/**
	 * 消息样式 101为纯文本消息,102为新闻样式风格带图片的公告
	 */
	private String msgStyle;
	/**
	 * 缩略图url
	 */
	private String smallPicUrl;
	/**
	 * 原图url
	 */
	private String realPicUrl;
	/**
	 * 详细内容
	 */
	private String pageUrl;
	/**
	 * 内容简述
	 */
	private String summary;

	public String getMsgid()
	{
		return msgid;
	}

	public void setMsgid(String msgid)
	{
		this.msgid = msgid;
	}

	public String getMsgStyle()
	{
		return msgStyle;
	}

	public void setMsgStyle(String msgStyle)
	{
		this.msgStyle = msgStyle;
	}

	public String getSmallPicUrl()
	{
		return smallPicUrl;
	}

	public void setSmallPicUrl(String smallPicUrl)
	{
		this.smallPicUrl = smallPicUrl;
	}

	public String getRealPicUrl()
	{
		return realPicUrl;
	}

	public void setRealPicUrl(String realPicUrl)
	{
		this.realPicUrl = realPicUrl;
	}

	public String getPageUrl()
	{
		return pageUrl;
	}

	public void setPageUrl(String pageUrl)
	{
		this.pageUrl = pageUrl;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
