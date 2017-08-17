/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.bean.packet;

import java.io.Serializable;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.bean.packet
 * 
 *  File Name       : SecureRegionPacket.java
 * 
 *  Creation Date   : 2015-10-24
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 跨地区平台报文
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SecureRegionPacket implements Serializable {
	private static final long serialVersionUID = 3838912988434406852L;

	/** 报文头对象 **/
	private SecureRegionPacketHeader header;

	/** 报文体对象 **/
	private SecureRegionPacketBody body;

	private SecureRegionPacket() {
		this.header = SecureRegionPacketHeader.build();
		this.body = SecureRegionPacketBody.build();
	}

	public SecureRegionPacketHeader getHeader() {
		return header;
	}

	public SecureRegionPacket setHeader(SecureRegionPacketHeader header) {
		this.header = header;

		return this;
	}

	public static SecureRegionPacket build() {
		return new SecureRegionPacket();
	}

	public SecureRegionPacketBody getBody() {
		return body;
	}

	public SecureRegionPacket setBody(SecureRegionPacketBody body) {
		this.body = body;

		return this;
	}
}
