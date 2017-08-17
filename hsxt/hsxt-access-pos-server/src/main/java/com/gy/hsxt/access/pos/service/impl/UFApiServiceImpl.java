package com.gy.hsxt.access.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.service.UFApiService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;


@Service("uFApiService")
public class UFApiServiceImpl implements UFApiService{
	
	@Autowired
	@Qualifier("ufRegionPacketService")
	private IUFRegionPacketService ufRegionPacketService;
	
	@Override
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody)  throws HsException {
		SystemLog.debug(this.getClass().getName(), "sendSyncRegionPacket()","req: packetHeader: " + 
				packetHeader.getDestResNo() + "  packetBody:" + packetBody.getBodyContent());
		return ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
	}
}