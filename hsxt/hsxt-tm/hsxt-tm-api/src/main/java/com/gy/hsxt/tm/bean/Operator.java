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
public class Operator implements Serializable {

    private static final long serialVersionUID = 5022030345279412272L;

    /** 值班员编号 **/
    private String optCustId;

    /** 值班组编号 **/
    private String groupId;

    /** 值班员工号 **/
    private String optWorkNo;

    /** 值班员名称 **/
    private String operatorName;

    /** 是否值班主任 **/
    private Boolean chief;

    /** 值班员排班 **/
    private List<ScheduleOpt> scheduleOpts;

    public List<ScheduleOpt> getScheduleOpts() {
        return scheduleOpts;
    }

    public void setScheduleOpts(List<ScheduleOpt> scheduleOpts) {
        this.scheduleOpts = scheduleOpts;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId == null ? null : optCustId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getOptWorkNo() {
        return optWorkNo;
    }

    public void setOptWorkNo(String optWorkNo) {
        this.optWorkNo = optWorkNo == null ? null : optWorkNo.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Boolean getChief() {
        return chief;
    }

    public void setChief(Boolean chief) {
        this.chief = chief;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
