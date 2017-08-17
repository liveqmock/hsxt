/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;

import com.gy.hsxt.gpf.um.bean.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色菜单关系数据库层接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IRoleMenuMapper
 * @Description : 角色菜单关系数据库层接口
 * @Author : chenm
 * @Date : 2016/1/27 16:06
 * @Version V3.0.0.0
 */
@Repository("roleMenuMapper")
public interface IRoleMenuMapper extends IBaseMapper<RoleMenu> {

    /**
     * 添加角色菜单关系列表
     *
     * @param roleMenu 角色菜单关系
     */
    void batchInsertForRole(RoleMenu roleMenu);

    /**
     * 删除角色菜单关系列表
     *
     * @param roleMenu 角色菜单关系
     */
    void batchDeleteForRole(RoleMenu roleMenu);

    /**
     * 根据角色Id删除角色菜单关系
     *
     * @param roleId 角色Id
     */
    void batchDeleteByRoleId(@Param("roleId") String roleId);
}
