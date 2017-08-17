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
import com.gy.hsxt.lcs.bean.BizRoute;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
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
public interface BizRouteMapper {

    /**
     * 判断业务路由映射数据是否存在
     * @param bizCode
     * @return
     */
	public String existBizRoute(@Param("bizCode")String bizCode);

	/**
	 * 根据业务代码查询业务路由映射对象
	 * @param bizCode
	 * @return
	 */
	public BizRoute queryBizRouteWithPK(@Param("bizCode")String bizCode);

	/**
	 * 批量更新业务路由映射规则
	 * @param bizRouteListUpdate
	 */
	public void batchUpdate(List<BizRoute> bizRouteListUpdate);

	/**
	 * 批量插入业务路由映射规则
	 * @param bizRouteListAdd
	 */
	public void batchInsert(List<BizRoute> bizRouteListAdd);
}
