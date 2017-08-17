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
 * 操作员角色关系
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : OperatorRole
 * @Description : 操作员角色关系
 * @Author : chenm
 * @Date : 2016/1/26 20:02
 * @Version V3.0.0.0
 */
public class OperatorRole implements Serializable {

    private static final long serialVersionUID = 2883678941797534044L;
    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 待添加角色
     */
    private List<String> addRoleIds;

    /**
     * 待删除角色
     */
    private List<String> delRoleIds;

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

    public List<String> getAddRoleIds() {
        return addRoleIds;
    }

    public void setAddRoleIds(List<String> addRoleIds) {
        this.addRoleIds = addRoleIds;
    }

    public List<String> getDelRoleIds() {
        return delRoleIds;
    }

    public void setDelRoleIds(List<String> delRoleIds) {
        this.delRoleIds = delRoleIds;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
