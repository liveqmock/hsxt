/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import com.gy.hsxt.gpf.um.bean.Role;
import com.gy.hsxt.gpf.um.bean.RoleQuery;

/**
 * 角色业务层接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IRoleService
 * @Description : 角色业务层接口
 * @Author : chenm
 * @Date : 2016/1/27 15:53
 * @Version V3.0.0.0
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 分页查询角色列表
     *
     * @param gridPage  分页实体
     * @param roleQuery 查询实体
     * @return {@link GridData}
     * @throws HsException
     */
    GridData<Role> queryBeanListForPage(GridPage gridPage, RoleQuery roleQuery) throws HsException;
}
