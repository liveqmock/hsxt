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
 *  File Name       : SecureRegionPacketBody.java
 * 
 *  Creation Date   : 2015-10-24
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 跨地区平台报文体
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SecureRegionPacketBody implements Serializable {
	private static final long serialVersionUID = 7210435545346489890L;

	/** 报文体内容 **/
	private Object bodyContent;

	private SecureRegionPacketBody() {
	}

	public Object getBodyContent() {
		return bodyContent;
	}

	public SecureRegionPacketBody setBodyContent(Object bodyContent) {
		this.bodyContent = bodyContent;

		return this;
	}

	public static SecureRegionPacketBody build() {
		return new SecureRegionPacketBody();
	}
}
