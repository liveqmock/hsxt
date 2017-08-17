/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 操作员用户组关系
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : OperatorGroup
 * @Description : 操作员用户组关系
 * @Author : chenm
 * @Date : 2016/1/26 20:05
 * @Version V3.0.0.0
 */
public class OperatorGroup implements Serializable {

    private static final long serialVersionUID = 1332871999991565969L;
    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 用户组ID
     */
    private String groupId;

    /**
     * 待添加用户组ID
     */
    private List<String> addGroupIds;

    /**
     * 待删除用户组ID
     */
    private List<String> delGroupIds;

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

    public List<String> getAddGroupIds() {
        return addGroupIds;
    }

    public void setAddGroupIds(List<String> addGroupIds) {
        this.addGroupIds = addGroupIds;
    }

    public List<String> getDelGroupIds() {
        return delGroupIds;
    }

    public void setDelGroupIds(List<String> delGroupIds) {
        this.delGroupIds = delGroupIds;
    }

    /**
     * 构建关系对象
     *
     * @param operatorId 操作者ID
     * @param groupId    用户组ID
     * @return {@code OperatorGroup}
     */
    public static OperatorGroup bulid(String operatorId, String groupId) {
        OperatorGroup operatorGroup = new OperatorGroup();
        operatorGroup.setGroupId(groupId);
        operatorGroup.setOperatorId(operatorId);
        return operatorGroup;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
