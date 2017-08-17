/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.RouteTarget;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-api
 * 
 *  Package Name    : com.gy.hsxt.lcs.api
 * 
 *  File Name       : ILCSRouteRuleService.java
 * 
 *  Creation Date   : 2015-7-2
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 路由接口，给综合前置用
 * 
 * 
 *  History         :  
 * 
 * </PRE>
 ***************************************************************************/
public interface ILCSRouteRuleService {

	/**
	 * 根据平台代码及业务代码找到目标平台地址及目标子系统
	 * 
	 * @param platNo
	 *            据平台代码
	 * @param bizCode
	 *            业务代码
	 * @return
	 * @throws HsException
	 */
	RouteTarget getRouteTarget(String platNo, String bizCode)
			throws HsException;

	/**
	 * 根据平台代码及业务代码找到目标平台地址及目标子系统
	 * 
	 * @param platNo
	 *            平台代码
	 * @param bizCode
	 *            业务代码
	 * @return
	 * @throws HsException
	 */
	RouteTarget getRouteTargetByResNo(String resNo, String bizCode)
			throws HsException;

	
}
