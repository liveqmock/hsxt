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
 *  Package Name    : com.gy.hsxt.uf.bean.packet
 * 
 *  File Name       : RegionPacketDataBody.java
 * 
 *  Creation Date   : 2015年10月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收到的跨地区平台数据
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RegionPacketDataBody implements Serializable {
	private static final long serialVersionUID = -7758130134201567451L;

	/** 报文内容 **/
	private Object bodyContent;

	public RegionPacketDataBody(Object bodyContent) {
		this.bodyContent = bodyContent;
	}

	public Object getBodyContent() {
		return bodyContent;
	}

	public static RegionPacketDataBody build(Object bodyContent) {
		return new RegionPacketDataBody(bodyContent);
	}
}
