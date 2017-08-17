/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

/**
 * 操作员角色关系查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : OperatorRoleQuery
 * @Description : 操作员角色关系查询实体
 * @Author : chenm
 * @Date : 2016/2/2 9:26
 * @Version V3.0.0.0
 */
public class OperatorRoleQuery extends Query {

    private static final long serialVersionUID = -8983453506365547740L;
    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 角色ID
     */
    private String roleId;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 构建查询实体
     *
     * @param operator 操作员
     * @return {@code OperatorRoleQuery}
     */
    public static OperatorRoleQuery bulid(Operator operator) {
        OperatorRoleQuery query = new OperatorRoleQuery();
        query.setOperatorId(operator.getOperatorId());
        return query;
    }

    /**
     * 构建查询实体
     *
     * @param loginInfo 登录信息
     * @return {@code OperatorRoleQuery}
     */
    public static OperatorRoleQuery bulid(LoginInfo loginInfo) {
        OperatorRoleQuery query = new OperatorRoleQuery();
        query.setOperatorId(loginInfo.getOperatorId());
        return query;
    }

}
