/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tm.bean;

import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 值班计划扩展实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: CustomScheduleOpt
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-4 下午8:11:33
 * @version V1.0
 */
public class CustomScheduleOpt extends BaseBean {

    private static final long serialVersionUID = 3687840884543094889L;

    // 值班计划编号
    private String scheduleId;

    // 值班组编号
    private String groupId;

    // 计划年份
    private String planYear;

    // 计划月份
    private String planMonth;

    // 值班计划列表
    private List<ScheduleOpt> scheduleOptList;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<ScheduleOpt> getScheduleOptList() {
        return scheduleOptList;
    }

    public void setScheduleOptList(List<ScheduleOpt> scheduleOptList) {
        this.scheduleOptList = scheduleOptList;
    }

    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
