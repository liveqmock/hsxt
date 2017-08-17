/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

/**
 * 操作者用户组关系查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : OperatorGroupQuery
 * @Description : 操作者用户组关系查询实体
 * @Author : chenm
 * @Date : 2016/2/4 16:12
 * @Version V3.0.0.0
 */
public class OperatorGroupQuery extends Query {

    private static final long serialVersionUID = 7991314850807713515L;
    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 用户组ID
     */
    private String groupId;

    /**
     * 是否查询已关联  是-true
     */
    private boolean related;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isRelated() {
        return related;
    }

    public void setRelated(boolean related) {
        this.related = related;
    }

    /**
     * 构建查询实体
     *
     * @param operator 操作者
     * @return 实体
     */
    public static OperatorGroupQuery bulid(Operator operator) {
        OperatorGroupQuery query = new OperatorGroupQuery();
        query.setOperatorId(operator.getOperatorId());
        return query;
    }
}
