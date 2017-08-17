/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.ResNoRoute;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
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
@Repository
public interface ResNoRouteMapper {
	
	public int addResNoRoute(ResNoRoute resNoRoute);

	public boolean deleteResNoRoute(ResNoRoute resNoRoute);

	public boolean updateResNoRoute(ResNoRoute resNoRoute);

	public String existResNoRoute(@Param("resNo")String resNo);

	public ResNoRoute queryResNoRouteWithPK(@Param("resNo")String resNo);
	
	public List<ResNoRoute> queryResNoRouteListPage(ResNoRoute resNoRoute);
	
	public List<ResNoRoute> queryResNoRoute4SyncData(@Param("version")long version);
	
    public List<ResNoRoute> getResNoRouteList();
    
    /**
     * 查询资源号列表中已经存在资源号
     * @param resNoList
     * @return
     */
    public List<String> findExistResNo(List<String> resNoList);
    
    /**
     * 批量删除资源路由关系
     * @param resNoRouteList
     */
    public void batchInsert(List<ResNoRoute> resNoRouteList);

    /**
     * 批量删除资源路由关系
     * @param resNoRouteList
     */
    public void batchDelete(List<ResNoRoute> resNoRouteList);
    
    /**
     * 批量更新资源路由关系
     * @param resNoRouteList
     */
    public void batchUpdate(List<ResNoRoute> resNoRouteList);
}
