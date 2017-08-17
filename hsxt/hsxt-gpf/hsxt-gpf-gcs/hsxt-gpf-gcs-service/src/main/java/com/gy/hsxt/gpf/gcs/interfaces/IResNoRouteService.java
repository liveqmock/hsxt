/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.interfaces;

import java.util.List;

import com.gy.hsxt.gpf.gcs.bean.ResNoRoute;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : ResNoRouteService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 资源号平台路由规则服务接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IResNoRouteService {

	/**
	 * 添加个人号平台路由
	 * 
	 * @param resNoRoute
	 * @return
	 */
	public int addResNoRoute(ResNoRoute resNoRoute);

	/**
	 * 删除个人号平台路由，delFlag = 1
	 * 
	 * @param resNoRoute
	 * @return
	 */
	public boolean deleteResNoRoute(String resNo);
	
	/**
     * 批量删除资源号平台路由规则，delFlag = 1
     * 
     * @param resNoList 资源号前5位
     * @return
     */
    public void batchDelResNoRoute(List<String> resNoList);

	/**
	 * 修改个人号平台路由
	 * 
	 * @param resNoRoute
	 * @return
	 */
	public boolean updateResNoRoute(ResNoRoute resNoRoute);


	/**
	 * 查询个人号平台路由
	 * 
	 * @param resNoRoute
	 * @return
	 */
	public List<ResNoRoute> queryResNoRoute(ResNoRoute resNoRoute);

	/**
	 * 判断是否存一样的个人号平台路由代码
	 * 
	 * @param resNo
	 * @return
	 */
	public boolean existResNoRoute(String resNo);
	
	/**
	 * 主键查询个人号平台路由
	 * @param resNo
	 * @return
	 */
	public ResNoRoute queryResNoRouteWithPK(String resNo);

	/**
	 * 查询出版本号大于子平台的所有个人平台路由
	 * @param version
	 * @return
	 */
	public List<ResNoRoute> queryResNoRoute4SyncData(Long version);
	
	/**
	 * 批量保存资源号路由关系列表
	 * @param resNoRouteList
	 */
	public void batchInsert(List<ResNoRoute> resNoRouteList);
	
	/**
	 * 批量保存或更新资源号路由关系列表
	 * @param resNoList
	 * @param platNo
	 */
    public void syncAddRouteRule(List<String> resNoList, String platNo);

	/**
	 * 获取资源号路由关系列表
	 * @return
	 */
    public List<ResNoRoute> getResNoRouteList();
}
