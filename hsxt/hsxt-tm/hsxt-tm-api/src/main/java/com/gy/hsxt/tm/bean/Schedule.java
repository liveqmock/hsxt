/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 值班计划实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: Schedule
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:30:16
 * @version V3.0.0
 */
public class Schedule extends BaseBean implements Serializable {

    private static final long serialVersionUID = 6996711905367370283L;

    /** 计划编号 **/
    private String scheduleId;

    /** 值班组编号 **/
    private String groupId;

    /** 计划年度 **/
    private String planYear;

    /** 计划月份 **/
    private String planMonth;

    /** 计划状态 **/
    private Integer status;

    /** 操作员编号 **/
    private String optCustId;

    /** 值班员排班表 **/
    private List<ScheduleOpt> scheduleOptList;

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId == null ? null : scheduleId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear == null ? null : planYear.trim();
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth == null ? null : planMonth.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ScheduleOpt> getScheduleOptList() {
        return scheduleOptList;
    }

    public void setScheduleOptList(List<ScheduleOpt> scheduleOptList) {
        this.scheduleOptList = scheduleOptList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
