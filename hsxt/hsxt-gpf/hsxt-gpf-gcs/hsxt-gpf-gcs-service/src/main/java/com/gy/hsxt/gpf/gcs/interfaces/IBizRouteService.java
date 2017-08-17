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

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.BizRoute;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
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
	 * 添加业务路由
	 * 
	 * @param bizRoute
	 * @return
	 */
	@Transactional
	public int addBizRoute(BizRoute bizRoute);

	/**
	 * 删除业务路由，delFlag = 1
	 * 
	 * @param bizCode
	 * @return
	 */
	@Transactional
	public boolean deleteBizRoute(String bizCode);

	/**
	 * 修改业务路由
	 * 
	 * @param bizRoute
	 * @return
	 */
	@Transactional
	public boolean updateBizRoute(BizRoute bizRoute);

	/**
	 * 查询业务路由
	 * 
	 * @param bizRoute
	 * @return
	 */
	public List<BizRoute> queryBizRoute(BizRoute bizRoute);
	
	/**
	 * 判断是否存一样的业务路由代码
	 * 
	 * @param bizCode
	 * @return
	 */
	public boolean existBizRoute(String bizCode);
	
	/**
	 * 用主键查询业务路由
	 * @param bizCode
	 * @return
	 */
	public BizRoute queryBizRouteWithPK(String bizCode);
	
	/**
	 * 查询出版本号大于子平台的所有业务路由
	 * @param version
	 * @return
	 */
	public List<BizRoute> queryBizRoute4SyncData(long version);
	
}
