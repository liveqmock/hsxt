/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.service;

import com.gy.hsxt.uf.mapper.vo.TUfPacketLog;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLogDetail;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.service
 * 
 *  File Name       : IRegionPacketLogService.java
 * 
 *  Creation Date   : 2015-9-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 报文日志Service接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IRegionPacketLogService {
	/**
	 * 记录来往报文日志
	 * 
	 * @param log
	 * @param logDetail
	 * @return
	 */
	public boolean insertLogData(TUfPacketLog log,
			TUfPacketLogDetail logDetail);
	
	/**
	 * 清理过期数据[定时任务调用]
	 */
	public void cleanGarbageDatas();
}
