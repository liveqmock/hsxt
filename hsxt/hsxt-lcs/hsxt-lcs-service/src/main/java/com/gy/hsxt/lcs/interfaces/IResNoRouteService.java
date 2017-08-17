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

import com.gy.hsxt.lcs.bean.ResNoRoute;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IResNoRouteService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 个人路由接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IResNoRouteService {
	
	/**
	 * 主键查询个人号平台路由
	 * @param resNo
	 * @return
	 */
	public ResNoRoute queryResNoRouteWithPK(String resNo);
	
	/**
	 * 插入或更新个人平台路由
	 * @param list
	 * @param version
	 * @return
	 */
	public int addOrUpdateResNoRoute(List<ResNoRoute> list,Long version);
	
	/**
     * 获取资源号路由关系列表
     * @return
     */
    public List<ResNoRoute> getResNoRouteList();

}
