/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.nt.api.common;

/**
 * 消息样式
 * 
 * @Package: com.gy.hsi.nt.api.common
 * @ClassName: MsgStyle
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月24日 下午4:45:55
 * @company: gyist
 * @version V3.0.0
 */
public enum MsgStyle
{

	/**
	 * 101为纯文本消息
	 */
	TEXT("101"),

	/**
	 * 102为新闻样式风格带图片的公告
	 */
	TEXT_IMG("102");

	private String style;

	MsgStyle(String style)
	{
		this.style = style;
	}

	public String getStyle()
	{
		return style;
	}
}
