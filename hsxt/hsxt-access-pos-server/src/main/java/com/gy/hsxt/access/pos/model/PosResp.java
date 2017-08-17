/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.io.Serializable;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: PosResp 
 * @Description: 响应pos机请求的应答报文
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:09:56 
 * @version V1.0
 */
public class PosResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Cmd cmd;
	/**
	 * 应答消息类型ID
	 */
	private byte[] respId;
	/**
	 * 应答body
	 */
	private byte[] body;

	public PosResp(Cmd cmd, byte[] respId) {
		this.cmd = cmd;
		this.respId = respId;
	}

	public Cmd getCmd() {
		return cmd;
	}

	public void setCmd(Cmd cmd) {
		this.cmd = cmd;
	}

	public byte[] getRespId() {
		return respId;
	}

	public void setRespId(byte[] respId) {
		this.respId = respId;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

}
