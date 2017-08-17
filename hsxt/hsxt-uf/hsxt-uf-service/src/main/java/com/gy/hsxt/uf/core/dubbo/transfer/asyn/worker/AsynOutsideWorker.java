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
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.core.dubbo.transfer.abstracts.OutsideWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.asyn.worker
 * 
 *  File Name       : AsynOutsideWorker.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 负责对来自外部子系统的的报文进行处理的工人类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class AsynOutsideWorker extends OutsideWorker {
	/**
	 * 处理来自外部地区平台的异步报文
	 */
	public PacketHandleResult handlePacket(SecureRegionPacket secureRegionPacket) {
		return null;
	}
}