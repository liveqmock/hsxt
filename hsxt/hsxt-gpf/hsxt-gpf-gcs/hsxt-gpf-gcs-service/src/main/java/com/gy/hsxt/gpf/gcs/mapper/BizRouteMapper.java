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

import com.gy.hsxt.gpf.gcs.bean.BizRoute;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : BizRouteMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 业务路由mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("BizRouteMapper")
public interface BizRouteMapper {
	
	public int addBizRoute(BizRoute bizRoute);

	public boolean deleteBizRoute(BizRoute bizRoute);

	public boolean updateBizRoute(BizRoute bizRoute);

	public String existBizRoute(@Param("bizCode")String bizCode);

	public BizRoute queryBizRouteWithPK(@Param("bizCode")String bizCode);
	
	public List<BizRoute> queryBizRouteListPage(BizRoute bizRoute);
	
	public List<BizRoute> queryBizRoute4SyncData(@Param("version")long version);

}
