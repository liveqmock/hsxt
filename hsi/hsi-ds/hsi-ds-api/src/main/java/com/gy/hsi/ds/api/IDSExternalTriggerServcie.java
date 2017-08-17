/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.api;

import java.util.Map;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-api
 * 
 *  Package Name    : com.gy.hsi.ds.api
 * 
 *  File Name       : IDSExternalTriggerServcie.java
 * 
 *  Creation Date   : 2016年1月29日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : 提供给其他子系统触发调度的接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IDSExternalTriggerServcie {

	/**
	 * 递归执行业务 (由其他子系统触发, 目前只有支付网关用)
	 * 
	 * @param name
	 * @param group
	 * @param externalArgs
	 */
	public void triggerByExternal(String name, String group,
			Map<String, String> externalArgs);
}
