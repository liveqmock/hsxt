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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-param
 * 
 *  Package Name    : com.gy.hsi.ds.job.service
 * 
 *  File Name       : IBusExecuteLogicServcie.java
 * 
 *  Creation Date   : 2016年1月29日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IBusExecuteLogicServcie {

	/**
	 * 递归执行业务，如果有后置任务，则所有的后置任务执行完成为止
	 * 
	 * @param name
	 * @param group
	 */
	public void recursiveServiceByQuartz(String name, String group);

	/**
	 * 递归执行业务 (由其他子系统触发, 目前只有支付网关用)
	 * 
	 * @param name
	 * @param group
	 * @param externalArgs
	 */
	public void recursiveServiceByExternal(String name, String group,
			Map<String, String> externalArgs);

	/**
	 * 手工执行
	 * 
	 * @param name
	 * @param group
	 * @param tempArgs
	 */
	public void recursiveServiceByManual(String name, String group,
			String tempArgs);

	/**
	 * 静默形式执行
	 * 
	 * @param name
	 * @param group
	 * @param newExecuteId
	 */
	public void recursiveServiceByFront(String name, String group,
			String newExecuteId);
}
