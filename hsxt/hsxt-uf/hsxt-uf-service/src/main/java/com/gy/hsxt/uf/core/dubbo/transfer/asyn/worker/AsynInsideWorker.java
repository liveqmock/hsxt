/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo.transfer.asyn.worker;

import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacket;
import com.gy.hsxt.uf.core.dubbo.transfer.abstracts.InsideWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.asyn.worker
 * 
 *  File Name       : AsynInsideWorker.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 负责对来自内部子系统的的报文进行处理的工人类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class AsynInsideWorker extends InsideWorker {

	public AsynInsideWorker() {
	}

	public PacketHandleResult handlePacket(RegionPacket RegionPacket) {
		// 返回处理结果
		return null;
	}
}