/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 值班员排班实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: ScheduleOpt
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:33:49
 * @version V3.0.0
 */
public class ScheduleOpt implements Serializable {

    private static final long serialVersionUID = 614417363252400495L;

    /** 计划编号 **/
    private String scheduleId;

    /** 值班号编号 **/
    private String optCustId;

    /** 排班日期 **/
    private String planDate;

    /** 值班状态 **/
    private Integer workType;

    /** 值班状态（临时） **/
    private Integer workTypeTemp;

    /** 输入值班名称（临时） **/
    private String inputWorkName;

    public Integer getWorkTypeTemp() {
        return workTypeTemp;
    }

    public void setWorkTypeTemp(Integer workTypeTemp) {
        this.workTypeTemp = workTypeTemp;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * @return the 输入值班名称（临时）
     */
    public String getInputWorkName() {
        return inputWorkName;
    }

    /**
     * @param 输入值班名称
     *            （临时） the inputWorkName to set
     */
    public void setInputWorkName(String inputWorkName) {
        this.inputWorkName = inputWorkName;
    }

}
