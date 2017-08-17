/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.api;

import java.util.List;

import com.gy.hsxt.uc.search.bean.SearchUserRole;

/**
 *  用户角色操作接口
 * 
 * @Package: com.gy.hsxt.uc.index.service
 * @ClassName: IUCUserRoleSearchService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-4-9 下午3:51:23
 * @version V1.0
 */
public interface IUCUserRoleService {
	/**
	 * 添加角色
	 * @param roles
	 */
	public void add(List<SearchUserRole> roles);
	
	/**
	 * 修改角色
	 * @param custId 客户ID
	 * @param roles 角色列表
	 */
	public void update(String custId, List<SearchUserRole> roles);
	/**
	 * 根据客户号删除角色
	 * @param custId
	 */
	public void deleteByCustId(String custId);
	
}
