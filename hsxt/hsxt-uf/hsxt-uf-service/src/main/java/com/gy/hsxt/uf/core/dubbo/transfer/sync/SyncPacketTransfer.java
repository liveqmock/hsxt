/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo.transfer.sync;

import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.core.dubbo.transfer.abstracts.PacketTransfer;
import com.gy.hsxt.uf.core.dubbo.transfer.sync.worker.SyncInsideWorker;
import com.gy.hsxt.uf.core.dubbo.transfer.sync.worker.SyncOutsideWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.sync
 * 
 *  File Name       : SyncPacketTransfer.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 同步报文中转
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SyncPacketTransfer extends PacketTransfer {
	/** 单实例对象 **/
	private static final SyncPacketTransfer instance = new SyncPacketTransfer();

	/** 内部工作者对象 **/
	private final SyncInsideWorker syncInsideWorker = new SyncInsideWorker();

	/** 外部工作者对象 **/
	private final SyncOutsideWorker syncOutsideWorker = new SyncOutsideWorker();

	/**
	 * 私有构造函数
	 */
	private SyncPacketTransfer() {
	}

	/**
	 * 获得单实例
	 * 
	 * @return
	 */
	public static SyncPacketTransfer getInstance() {
		return instance;
	}

	/**
	 * 处理来自外部地区平台的同步报文
	 */
	@Override
	public PacketHandleResult handlePacketFromOutside(
			SecureRegionPacket regionPacket) {
		// 将来自外部地区平台 的同步报文转发给内部子系统, 并返回处理结果
		return syncOutsideWorker.handlePacket(regionPacket);
	}

	/**
	 * 处理来自内部子系统的同步报文
	 */
	@Override
	public PacketHandleResult handlePacketFromInside(RegionPacket regionPacket) {
		// 发送跨地区平台的同步消息,并返回处理结果
		return syncInsideWorker.handlePacket(regionPacket);
	}
}
