/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLog;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service
 * 
 *  File Name       : IOperationLogService.java
 * 
 *  Creation Date   : 2015-05-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : IOperationLogService
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IOperationLogService {
	/**
	 * 插入操作日志
	 * 
	 * @param log
	 * @return
	 */
	public boolean insertOperationLog(TFsFileOperationLog log);

	/**
	 * 根据userId查询操作日志
	 * 
	 * @param userId
	 * @return
	 */
	public TFsFileOperationLog queryOperationLogByOptuserId(String userId);

	/**
	 * 根据fileId查询操作日志
	 * 
	 * @param fileId
	 * @return
	 */
	public TFsFileOperationLog queryOperationLogByFileId(String fileId);

	/**
	 * 根据functionId查询操作日志
	 * 
	 * @param functionId
	 * @return
	 */
	public TFsFileOperationLog queryOperationLogByFuncId(String functionId);

	/**
	 * 清理过期数据[定时任务调用]
	 */
	public void cleanGarbageDatas();
}
