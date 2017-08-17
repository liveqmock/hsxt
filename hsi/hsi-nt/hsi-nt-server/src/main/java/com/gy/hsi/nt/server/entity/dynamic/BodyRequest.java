package com.gy.hsi.nt.server.entity.dynamic;

import java.io.Serializable;
/**
 * 
 * @className:BodyRequest
 * @author:likui
 * @date:2015年7月29日
 * @desc:互动消息请求实体
 * @company:gyist
 */
public class BodyRequest implements Serializable {
	
	private static final long serialVersionUID = -1250441984525078524L;
	
	/**
	 * 消息内容
	 */
	private Content content;

	/**
	 * JSON字符串消息内容
	 */
	private String strContent;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getStrContent() {
		return strContent;
	}

	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}
}
