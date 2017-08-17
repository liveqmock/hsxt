/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service;

import java.util.List;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsPermissionRule;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service
 * 
 *  File Name       : IPermissionRuleService.java
 * 
 *  Creation Date   : 2015年7月10日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 权限规则矩阵接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IPermissionRuleService {

	/**
	 * 查询所有规则
	 * 
	 * @return
	 */
	public List<TFsPermissionRule> queryAllRule();
}
