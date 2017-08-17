/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

/**
 * 用户组查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : GroupQuery
 * @Description : 用户组查询实体
 * @Author : chenm
 * @Date : 2016/2/4 11:31
 * @Version V3.0.0.0
 */
public class GroupQuery extends Query {

    private static final long serialVersionUID = 5243638127549629165L;
    /**
     * 用户组ID
     */
    private String groupId;

    /**
     * 用户组名称
     */
    private String groupName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 构建查询实体
     *
     * @param group 用户组
     * @return {@code GroupQuery}
     */
    public static GroupQuery bulid(Group group) {
        GroupQuery query = new GroupQuery();
        query.setGroupName(group.getGroupName());
        return query;
    }
}
