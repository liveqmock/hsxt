/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 值班员列表实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: Operator
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:28:29
 * @version V3.0.0
 */
public class CustomOperator extends Operator {
    private static final long serialVersionUID = 7944941363519481468L;

    /** 值班组名称 **/
    private String groupName;

    /** 值班组类型 **/
    private Integer groupType;

    /** 值班状态 **/
    private Integer workType;

    /** 值班状态名称 **/
    private String workTypeName;

    /** 业务类型 **/
    List<BizType> bizTypes;

    /** 值班计划 **/
    List<ScheduleOpt> scheduleOpts;

    public List<ScheduleOpt> getScheduleOpts() {
        return scheduleOpts;
    }

    public void setScheduleOpts(List<ScheduleOpt> scheduleOpts) {
        this.scheduleOpts = scheduleOpts;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public List<BizType> getBizTypes() {
        return bizTypes;
    }

    public void setBizTypes(List<BizType> bizTypes) {
        this.bizTypes = bizTypes;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
