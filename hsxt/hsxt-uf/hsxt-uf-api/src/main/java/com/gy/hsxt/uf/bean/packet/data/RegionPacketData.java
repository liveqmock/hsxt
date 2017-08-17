/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.bean.packet.data;

import java.io.Serializable;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.bean.packet.data
 * 
 *  File Name       : RegionPacketData.java
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
public class RegionPacketData implements Serializable {

	private static final long serialVersionUID = -4865328675174159759L;

	/** 报文头 **/
	private RegionPacketDataHeader header;

	/** 报文体 **/
	private RegionPacketDataBody body;

	public RegionPacketData(RegionPacketDataHeader header,
			RegionPacketDataBody body) {
		this.header = header;
		this.body = body;
	}

	public RegionPacketDataHeader getHeader() {
		return header;
	}

	public RegionPacketDataBody getBody() {
		return body;
	}

	public static RegionPacketData build(RegionPacketDataHeader header,
			RegionPacketDataBody body) {
		return new RegionPacketData(header, body);
	}

	public static RegionPacketData build(Object bodyContent) {
		return new RegionPacketData(RegionPacketDataHeader.build(),
				RegionPacketDataBody.build(bodyContent));
	}
}
