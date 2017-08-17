package com.gy.hsxt.access.pos.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.service.UidApiService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uid.uid.service.IUidService;


@Service("uidApiService")
public class UidApiServiceImpl implements UidApiService{

	
	@Autowired
	@Qualifier("uidService")
	private IUidService uidService;

	@Override
	public String getUid(String entResNo) throws HsException {
//		SystemLog.debug("UidApiServiceImpl", "getUid()","获取11位流水号，entResNo: " + entResNo);
		return String.format("%011d", uidService.getUid(entResNo));
		
	}
}