/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

/**
 * 角色菜单关系查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : RoleMenuQuery
 * @Description : 角色菜单关系查询实体
 * @Author : chenm
 * @Date : 2016/2/2 10:18
 * @Version V3.0.0.0
 */
public class RoleMenuQuery extends Query {

    private static final long serialVersionUID = 844565378773093997L;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单编号
     */
    private String menuNo;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    /**
     * 构建查询实体
     *
     * @param roleId 角色ID
     * @return {@code RoleMenuQuery}
     */
    public static RoleMenuQuery bulid(String roleId) {
        RoleMenuQuery query = new RoleMenuQuery();
        query.setRoleId(roleId);
        return query;
    }

    /**
     * 构建查询实体
     *
     * @param operatorRole 操作员角色关系
     * @return {@code RoleMenuQuery}
     */
    public static RoleMenuQuery bulid(OperatorRole operatorRole) {
        RoleMenuQuery query = new RoleMenuQuery();
        query.setRoleId(operatorRole.getRoleId());
        return query;
    }
}
