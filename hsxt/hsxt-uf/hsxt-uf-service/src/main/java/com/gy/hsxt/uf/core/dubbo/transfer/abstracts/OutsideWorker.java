/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo.transfer.abstracts;

import org.apache.log4j.Logger;

import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.spring.SpringContextLoader;
import com.gy.hsxt.uf.factory.BeansFactory;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.abstracts
 * 
 *  File Name       : OutsideWorker.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自外部地区平台的报文
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class OutsideWorker {
	/** 记录日志对象 **/
	protected final Logger logger = Logger.getLogger(this.getClass());

	/** bean工厂 **/
	protected final BeansFactory beansFactory = SpringContextLoader
			.getBeansFactory();

	/**
	 * 构造函数
	 */
	public OutsideWorker() {
	}

	/**
	 * 处理来自外部地区平台的报文
	 * 
	 * @param regionPacket
	 * @return
	 */
	public abstract PacketHandleResult handlePacket(
			SecureRegionPacket regionPacket);
}
