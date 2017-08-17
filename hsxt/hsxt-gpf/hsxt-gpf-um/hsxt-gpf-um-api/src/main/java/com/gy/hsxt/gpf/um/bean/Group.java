/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import java.util.List;

/**
 * 用户组
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : Group
 * @Description : 用户组
 * @Author : chenm
 * @Date : 2016/1/26 19:47
 * @Version V3.0.0.0
 */
public class Group extends Base {

    private static final long serialVersionUID = 8874461871278580497L;
    /**
     * 用户组ID
     */
    private String groupId;

    /**
     * 用户组名称
     */
    private String groupName;

    /**
     * 查询时，是操作者名称
     * 保存/修改时，操作员ID
     *
     * 格式 a,b,c,d
     */
    private List<String> operators;

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

    public List<String> getOperators() {
        return operators;
    }

    public void setOperators(List<String> operators) {
        this.operators = operators;
    }
}
