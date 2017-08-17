/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.api;

import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.api
 * 
 *  File Name       : IUFRegionPacketDataService.java
 * 
 *  Creation Date   : 2015年10月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 业务系统接收并处理UF接收到的跨地区平台数据
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IUFRegionPacketDataService {

	/**
	 * 业务系统接收并处理UF接收到的跨地区平台报文数据
	 * 
	 * @param regionPacketData
	 *            接收到的跨地区平台数据对象
	 * @return 响应结果对象
	 */
	public Object handleReceived(RegionPacketData regionPacketData);
}
