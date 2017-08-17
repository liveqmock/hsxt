package com.gy.hsi.nt.server.entity.dynamic;

import java.io.Serializable;
/**
 * 
 * @className:DynamicMsgRequest
 * @author:likui
 * @date:2015年7月29日
 * @desc:互动消息请求对象
 * @company:gyist
 */
public class DynamicMsgRequest implements Serializable {

	private static final long serialVersionUID = 8834294972512684006L;

	/**
	 * 消息头
	 */
	private Header header;
	/**
	 * 消息体
	 */
	private BodyRequest body ;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public BodyRequest getBody() {
		return body;
	}

	public void setBody(BodyRequest body) {
		this.body = body;
	}	
}
