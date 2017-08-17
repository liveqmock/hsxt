/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.pg.mapper.TPgOperationLockMapper;
import com.gy.hsxt.pg.mapper.vo.TPgOperationLock;
import com.gy.hsxt.pg.service.IOperationLockService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : OperationLockService.java
 * 
 *  Creation Date   : 2016年3月3日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 锁操作
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class OperationLockService implements IOperationLockService {

	@Autowired
	private TPgOperationLockMapper optLockMapper;

	@Override
	public TPgOperationLock selectLock(String businessId, Integer businessType) {
		return optLockMapper.selectByBusinessId(businessId, businessType);
	}

	@Override
	public int addLock(String businessId, Integer businessType) {
		TPgOperationLock record = new TPgOperationLock();
		record.setBusinessId(businessId);
		record.setBusinessType(businessType);
		record.setCreatedDate(new Date());

		return optLockMapper.insert(record);
	}

	@Override
	public int deleteLock(String businessId, Integer businessType) {
		
		optLockMapper.deleteByBusinessIdAndType(businessId, businessType);

		return 1;
	}

}
