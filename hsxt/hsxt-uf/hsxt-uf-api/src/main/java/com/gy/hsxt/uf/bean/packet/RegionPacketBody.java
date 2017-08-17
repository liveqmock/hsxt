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
 *  File Name       : RegionPacketBody.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 跨地区平台消息数据报文体
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RegionPacketBody implements Serializable {
	private static final long serialVersionUID = -2733639068087287256L;
	
	/** 报文体内容 **/
	private Object bodyContent;

	private RegionPacketBody(Object bodyContent) {
		this.bodyContent = bodyContent;
	}

	public Object getBodyContent() {
		return bodyContent;
	}

	public static RegionPacketBody build(Object bodyContent) {
		return new RegionPacketBody(bodyContent);
	}
}
