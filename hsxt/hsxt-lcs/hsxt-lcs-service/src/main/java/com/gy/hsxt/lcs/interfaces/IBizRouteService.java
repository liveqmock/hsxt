/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.BizRoute;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IBizRouteService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 业务路由接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IBizRouteService {

	/**
	 * 用主键查询业务路由
	 * @param bizCode
	 * @return
	 */
	public BizRoute queryBizRouteWithPK(String bizCode);
	
	/**
	 * 插入或更新业务路由
	 * @param list
	 * @param version
	 * @return
	 */
	public int addOrUpdateBizRoute(List<BizRoute> list,Long version);
}
