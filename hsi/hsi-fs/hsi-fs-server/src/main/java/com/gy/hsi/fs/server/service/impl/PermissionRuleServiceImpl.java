/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.fs.server.common.framework.mybatis.MapperSupporter;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsPermissionRule;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsPermissionRuleExample;
import com.gy.hsi.fs.server.service.IPermissionRuleService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service.impl
 * 
 *  File Name       : PermissionRuleServiceImpl.java
 * 
 *  Creation Date   : 2015年7月10日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 权限规则service接口实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service(value = "permissionRuleService")
public class PermissionRuleServiceImpl extends MapperSupporter implements
		IPermissionRuleService {

	public PermissionRuleServiceImpl() {
	}

	@Override
	public List<TFsPermissionRule> queryAllRule() {
		TFsPermissionRuleExample example = new TFsPermissionRuleExample();
		example.createCriteria().andIdIsNotNull();

		return getTFsPermissionRuleMapper().selectByExample(example);
	}

}
