/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.api;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.gcs.bean.RouteTarget;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-api
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.api
 * 
 *  File Name       : IGCSRouteRuleService.java
 * 
 *  Creation Date   : 2015-7-2
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 路由规则查询服务接口
 * 
 * 
 *  History         :  
 * 
 * </PRE>
 ***************************************************************************/
public interface IGCSRouteRuleService {
	
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
	RouteTarget getRouteTarget(String platNo, String bizCode)
			throws HsException;

	/**
	 * 根据互生号及业务代码找到目标平台地址及目标子系统
	 * 
	 * @param resNo
	 *            互生号
	 * @param bizCode
	 *            业务代码
	 * @return
	 * @throws HsException
	 */
	RouteTarget getRouteTargetByResNo(String resNo, String bizCode)
			throws HsException;
	
	 /**
     * 增加资源号关联平台的路由规则，一级区域资源分配同步
     * @param resNoList 服务资源号列表
     * @param platNo 平台代码
     * @throws HsException
     */
    void syncAddRouteRule(List<String> resNoList, String platNo) throws HsException;
    
    /**
     * 删除资源号关联平台的路由规则，一级区域资源释放同步
     * @param resNoList 服务资源号列表
     * @throws HsException
     */
    void syncDelRouteRule(List<String> resNoList) throws HsException;
    
    /**
     * 获取全部资源号与平台的路由映射关系
     * @return 返回 资源号前5位为key,平台代码为value的Map结果
     */
    Map<String,String> getResNoRouteMap();

    /**
     * 根据互生号获取路由目标平台代码
     * @param resNo 互生号
     * @return
     * @throws HsException
     */
    String getPlatByResNo(String resNo) throws HsException;
    
    /**
     *  获取全部有效地区平台代码
     * @return
     */
    List<Plat> findRegionPlats();

}
