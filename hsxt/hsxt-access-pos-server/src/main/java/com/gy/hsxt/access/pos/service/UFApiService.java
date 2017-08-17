package com.gy.hsxt.access.pos.service;

import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;


/**
 * 综合前置服务类
 * @author kend
 *
 */
public interface UFApiService {
	
	/**
	 * 通过综合前置向目标平台发送业务请求
	 * @param packetHeader 目标平台定向及业务类型
	 * @param packetBody   业务参数
	 * @return 
	 * @throws PosException
	 */
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody)  throws HsException;
	
}