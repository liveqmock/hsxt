/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;

import com.gy.hsxt.pg.mapper.vo.TPgOperationLock;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : IOperationLockService.java
 * 
 *  Creation Date   : 2015年12月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 锁操作
 * 
 * 
 *  History         : None
 * 
 * </PRE>
 ***************************************************************************/
public interface IOperationLockService {

	/**
	 * 查询锁
	 * 
	 * @param businessId
	 * @param businessType
	 * @return
	 */
	public TPgOperationLock selectLock(String businessId, Integer businessType);

	/**
	 * 加锁
	 * 
	 * @param businessId
	 * @param businessType
	 * @return
	 */
	public int addLock(String businessId, Integer businessType);

	/**
	 * 解锁
	 * 
	 * @param businessId
	 * @param businessType
	 * @return
	 */
	public int deleteLock(String businessId, Integer businessType);
}
