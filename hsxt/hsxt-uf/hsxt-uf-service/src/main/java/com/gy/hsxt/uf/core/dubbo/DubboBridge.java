/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo;

import org.apache.log4j.Logger;

import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.spring.SpringContextLoader;
import com.gy.hsxt.uf.core.dubbo.transfer.sync.SyncPacketTransfer;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo
 * 
 *  File Name       : DubboBridge.java
 * 
 *  Creation Date   : 2015-9-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : dubbo桥接类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class DubboBridge {
	/** 记录日志对象 **/
	private final Logger logger = Logger.getLogger(this.getClass());

	/** 单实例对象 **/
	private static final DubboBridge instance = new DubboBridge();

	/**
	 * 私有构造函数
	 */
	private DubboBridge() {
	}

	/**
	 * 获得单实例
	 * 
	 * @return
	 */
	public static DubboBridge getInstance() {
		return instance;
	}

	/**
	 * 向dubbo发起同步和异步消息订阅
	 * 
	 * @throws
	 */
	public void registerDubboService() throws Exception {
		// 向dubbo发起同步消息订阅
		SpringContextLoader.getAppContext().start();
	}

	/**
	 * 处理来自地区平台内部的请求
	 * 
	 * @param regionPacket
	 * @return
	 */
	public PacketHandleResult handlePacketsFromInside(RegionPacket regionPacket) {
		PacketHandleResult handleResult = SyncPacketTransfer.getInstance()
				.handlePacketFromInside(regionPacket);

		boolean success = handleResult.isSuccess();

		if (!success) {
			logger.error(handleResult.getResultDesc(), handleResult.getCause());
		}

		return handleResult;
	}

	/**
	 * 处理来自外部地区平台的报文
	 * 
	 * @param secureRegionPacket
	 * @return
	 */
	public PacketHandleResult handlePacketsFromOutside(
			SecureRegionPacket secureRegionPacket) {
		// 同步消息处理
		return SyncPacketTransfer.getInstance().handlePacketFromOutside(
				secureRegionPacket);
	}
}
