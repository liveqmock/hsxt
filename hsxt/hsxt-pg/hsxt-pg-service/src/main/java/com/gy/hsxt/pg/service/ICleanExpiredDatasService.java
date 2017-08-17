/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : ICleanExpiredDatasService.java
 * 
 *  Creation Date   : 2015年12月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 垃圾数据清理接口
 * 
 * 
 *  History         : None
 * 
 * </PRE>
 ***************************************************************************/
public interface ICleanExpiredDatasService {

	/**
	 * 清理无效的数据
	 */
	public void cleanExpiredDatas();
}
