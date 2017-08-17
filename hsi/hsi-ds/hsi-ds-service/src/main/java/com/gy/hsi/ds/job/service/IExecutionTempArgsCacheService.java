/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.service;

import java.util.Map;

import com.gy.hsi.ds.job.beans.bo.JobTempArgs;

public interface IExecutionTempArgsCacheService {

	/**
	 * 清理过期的bean
	 * 
	 * @param executeId
	 * @param serviceName
	 * @param tempArgs
	 */
	public void saveTempArgsCache(String executeId, String serviceName,
			Map<String, String> tempArgs);

	/**
	 * 清理过期的bean
	 */
	public void cleanOldTempArgsCache();
	
	/**
	 * 按执行id清理临时参数
	 * 
	 * @param executeId
	 */
	public void cleanTempArgsCacheByExecuteId(String executeId);

	/**
	 * 获取同一批次运行的业务缓存
	 * 
	 * @param executeId
	 * @return
	 */
	public JobTempArgs getTempArgs(String executeId);
	
	/**
	 * 临时传递的参数
	 * 
	 * @param tempArgs
	 * @return
	 */
	public String parseExecuteIdPrefix(Map<String, String> tempArgs);

	/**
	 * 判断是否为手工
	 * 
	 * @param executeId
	 * @return
	 */
	public boolean isManual(String executeId);

	/**
	 * 判断是否忽略前置业务
	 * 
	 * @param executeId
	 * @return
	 */
	public boolean isIgnoreFront(String executeId);

	/**
	 * 判断是否触发后置业务
	 * 
	 * @param executeId
	 * @return
	 */
	public boolean isRecurPost(String executeId);
}
