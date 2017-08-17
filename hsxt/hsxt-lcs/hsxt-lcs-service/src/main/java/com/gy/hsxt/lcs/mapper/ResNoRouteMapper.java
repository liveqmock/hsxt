/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.lcs.bean.ResNoRoute;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
 * 
 *  File Name       : ResNoRouteMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 个人平台路由mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface ResNoRouteMapper {
	
	public String existResNoRoute(@Param("resNo")String resNo);

	public ResNoRoute queryResNoRouteWithPK(@Param("resNo")String resNo);
	
	public void batchUpdate(List<ResNoRoute> resNoRouteListUpdate);

	public void batchInsert(List<ResNoRoute> resNoRouteListAdd);

    public List<ResNoRoute> getResNoRouteList();
}
