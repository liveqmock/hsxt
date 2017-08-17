package com.gy.hsxt.tm.bean;

import com.alibaba.fastjson.JSON;

public class WorkType {
    private Integer workType;

    private String workTypeName;

    private String workOnTime;

    private String workOffTime;

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType == null ? null : workType;
    }

    public String getWorkOnTime() {
        return workOnTime;
    }

    public void setWorkOnTime(String workOnTime) {
        this.workOnTime = workOnTime;
    }

    public String getWorkOffTime() {
        return workOffTime;
    }

    public void setWorkOffTime(String workOffTime) {
        this.workOffTime = workOffTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
