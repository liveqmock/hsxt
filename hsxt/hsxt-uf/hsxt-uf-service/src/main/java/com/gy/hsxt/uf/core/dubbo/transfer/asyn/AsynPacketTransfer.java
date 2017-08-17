/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo.transfer.asyn;

import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.core.dubbo.transfer.abstracts.PacketTransfer;
import com.gy.hsxt.uf.core.dubbo.transfer.asyn.worker.AsynInsideWorker;
import com.gy.hsxt.uf.core.dubbo.transfer.asyn.worker.AsynOutsideWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.asyn
 * 
 *  File Name       : AsynPacketTransfer.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 异步报文中转
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class AsynPacketTransfer extends PacketTransfer {
	/** 单实例对象 **/
	private static final AsynPacketTransfer instance = new AsynPacketTransfer();
	
	/** 内部工作者对象 **/
	private final AsynInsideWorker asynInsideWorker = new AsynInsideWorker();

	/** 外部工作者对象 **/
	private final AsynOutsideWorker asynOutsideWorker = new AsynOutsideWorker();

	/**
	 * 私有构造函数
	 */
	private AsynPacketTransfer() {
	}

	/**
	 * 获得单实例
	 * 
	 * @return
	 */
	public static AsynPacketTransfer getInstance() {
		return instance;
	}

	/**
	 * 处理来自外部地区平台的异步报文
	 */
	@Override
	public PacketHandleResult handlePacketFromOutside(SecureRegionPacket regionPacket) {
		// 将来自外部地区平台 的异步报文转发给内部子系统, 并返回处理结果
		return asynOutsideWorker.handlePacket(regionPacket);
	}

	/**
	 * 处理来自内部子系统的异步报文
	 */
	@Override
	public PacketHandleResult handlePacketFromInside(RegionPacket RegionPacket) {
		// 发送跨地区平台的异步消息,并返回处理结果
		return asynInsideWorker.handlePacket(RegionPacket);
	}
}
