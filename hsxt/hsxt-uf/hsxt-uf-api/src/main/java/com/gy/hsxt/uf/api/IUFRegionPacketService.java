/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.api;

import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.api
 * 
 *  File Name       : IUFRegionPacketService.java
 * 
 *  Creation Date   : 2015年10月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置跨地区报文服务接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IUFRegionPacketService {
	/**
	 * 发起跨地区报文
	 * 
	 * @param packetHeader
	 * @param packetBody
	 * @return
	 */
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody);

	/**
	 * 发起跨地区报文
	 * 
	 * @param packetHeader
	 * @param packetBody
	 * @param timeoutMills
	 * @return
	 */
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody, Integer timeoutMills);
}
