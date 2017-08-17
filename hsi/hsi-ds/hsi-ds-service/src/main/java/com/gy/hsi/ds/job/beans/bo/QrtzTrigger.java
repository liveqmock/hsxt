/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.beans.bo;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
/**
 * 
 * Trigger数据库操作类
 * @Package: com.gy.hsi.ds.job.web.service.trigger.bo  
 * @ClassName: QrtzTrigger 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月14日 上午9:07:57 
 * @version V3.0
 */
@Table(db = "", name = "QRTZ_TRIGGERS" ,keyColumn = Columns.TRIGGER_NAME)
public class QrtzTrigger extends BaseObject<String>{
    /**
     * 自动生成的序列号
     */
    private static final long serialVersionUID = 221981614892998519L;

    /**
     * schedule名称
     */
    @Column(value = Columns.SCHED_NAME)
    private String schedName;
    
    
    /**
     * trigger所属组的名字
     */
    @Column(value = Columns.TRIGGER_GROUP)
    private String triggerGroup;
    
    /**
     * job的名字
     */
    @Column(value = Columns.JOB_NAME)
    private String jobName;
    
    /**
     * job所属组的名字
     */
    @Column(value = Columns.JOB_GROUP)
    private String jobGroup ;
    
    /**
     * 描述
     */
    @Column(value = Columns.DESCRIPTION)
    private String desc;
    
    /**
     * 下次执行时间
     */
    @Column(value = Columns.NEXT_FIRE_TIME)
    private Long nextFireTime;
    
    /**
     * 上次执行时间
     */
    @Column(value = Columns.PREV_FIRE_TIME)
    private Long prevFireTime;
    
    /**
     * 优先级
     */
    @Column(value = Columns.PRIORITY)
    private Integer priority;
    
    /**
     * 当前trigger状态
     */
    @Column(value = Columns.TRIGGER_STATE)
    private String triggerState ;
    
    /**
     * 触发器类型
     */
    @Column(value = Columns.TRIGGER_TYPE)
    private String triggerType;
    
    /**
     * 开始时间
     */
    @Column(value = Columns.START_TIME)
    private Long startTime;
    
    /**
     * 结束时间
     */
    @Column(value = Columns.END_TIME)
    private Long endTime;
    
    @Column(value = Columns.CALENDAR_NAME)
    private String calendarName;
    
    @Column(value = Columns.MISFIRE_INSTR)
    private Integer misfireInstr ;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }


    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Long getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public Integer getMisfireInstr() {
        return misfireInstr;
    }

    public void setMisfireInstr(Integer misfireInstr) {
        this.misfireInstr = misfireInstr;
    }

    @Override
    public String toString() {
        return "QrtzTrigger [schedName=" + schedName + ", triggerGroup=" + triggerGroup + ", jobName=" + jobName
                + ", jobGroup=" + jobGroup + ", desc=" + desc + ", nextFireTime=" + nextFireTime + ", prevFireTime="
                + prevFireTime + ", priority=" + priority + ", triggerState=" + triggerState + ", triggerType="
                + triggerType + ", startTime=" + startTime + ", endTime=" + endTime + ", calendarName=" + calendarName
                + ", misfireInstr=" + misfireInstr + "]";
    }


        
}
