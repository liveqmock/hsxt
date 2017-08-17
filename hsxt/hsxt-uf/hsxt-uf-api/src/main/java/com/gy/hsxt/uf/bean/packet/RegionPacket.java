/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.bean.packet;

import java.io.Serializable;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.bean.packet
 * 
 *  File Name       : RegionPacket.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 跨地区平台消息数据对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RegionPacket implements Serializable {

	private static final long serialVersionUID = -5856171983638612117L;

	/** 报文头 **/
	private RegionPacketHeader header;

	/** 报文体 **/
	private RegionPacketBody body;

	public RegionPacket(RegionPacketHeader header, RegionPacketBody body) {
		this.header = header;
		this.body = body;
	}

	public RegionPacketHeader getHeader() {
		return header;
	}

	public RegionPacketBody getBody() {
		return body;
	}

	public static RegionPacket build(RegionPacketHeader header,
			RegionPacketBody body) {
		return new RegionPacket(header, body);
	}
}
