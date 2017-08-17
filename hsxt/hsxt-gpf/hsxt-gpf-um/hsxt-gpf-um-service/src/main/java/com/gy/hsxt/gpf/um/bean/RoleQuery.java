/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

/**
 * 角色查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : RoleQuery
 * @Description : 角色查询实体
 * @Author : chenm
 * @Date : 2016/2/1 20:56
 * @Version V3.0.0.0
 */
public class RoleQuery extends Query {

    private static final long serialVersionUID = 3559095373715361687L;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 构建角色查询实体
     *
     * @param role 角色
     * @return {@code RoleQuery}
     */
    public static RoleQuery bulid(Role role) {
        RoleQuery query = new RoleQuery();
        query.setRoleName(role.getRoleName());
        return query;
    }

    /**
     * 构建角色查询实体
     *
     * @param roleMenu 角色菜单关系
     * @return {@code RoleQuery}
     */
    public static RoleQuery bulid(RoleMenu roleMenu) {
        RoleQuery query = new RoleQuery();
        query.setRoleId(roleMenu.getRoleId());
        return query;
    }

    /**
     * 构建角色查询实体
     *
     * @param operatorRole 操作员角色关系
     * @return {@code RoleQuery}
     */
    public static RoleQuery bulid(OperatorRole operatorRole) {
        RoleQuery query = new RoleQuery();
        query.setRoleId(operatorRole.getRoleId());
        return query;
    }
}
