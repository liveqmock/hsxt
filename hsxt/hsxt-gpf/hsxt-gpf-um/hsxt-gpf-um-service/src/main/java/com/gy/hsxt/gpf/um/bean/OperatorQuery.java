/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

/**
 * 操作员查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : OperatorQuery
 * @Description : 操作员查询实体
 * @Author : chenm
 * @Date : 2016/1/28 11:36
 * @Version V3.0.0.0
 */
public class OperatorQuery extends Query {

    private static final long serialVersionUID = 8956319662981379335L;

    /**
     * 操作员ID
     */
    private String operatorId;
    /**
     * 登录用户名
     */
    private String loginUser;

    /**
     * 姓名
     */
    private String name;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 构建查询实体
     *
     * @param operator 操作者
     * @return bean
     */
    public static OperatorQuery bulid(Operator operator) {
        OperatorQuery query = new OperatorQuery();
        query.setLoginUser(operator.getLoginUser());
        return query;
    }

    /**
     * 构建查询实体
     *
     * @param info 登录信息
     * @return bean
     */
    public static OperatorQuery bulid(LoginInfo info) {
        OperatorQuery query = new OperatorQuery();
        query.setOperatorId(info.getOperatorId());
        query.setLoginUser(info.getLoginUser());
        query.setName(info.getName());
        return query;
    }
}
