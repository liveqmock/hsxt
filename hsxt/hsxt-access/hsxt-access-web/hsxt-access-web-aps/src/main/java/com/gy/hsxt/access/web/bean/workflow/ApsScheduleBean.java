/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.workflow;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.Schedule;
import com.gy.hsxt.tm.bean.ScheduleOpt;
import com.gy.hsxt.tm.enumtype.WorkTypeStatus;

/***
 * 地区平台值班计划实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.workflow
 * @ClassName: ApsScheduleBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-30 下午4:27:18
 * @version V1.0
 */
public class ApsScheduleBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 值班员计划排班JSON
     */
    private String scheduleJson;

    /**
     * 值班员计划排班
     */
    private Schedule schedule;

    /**
     * 验证有效数据
     */
    public void checkData() {
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.scheduleJson, ASRespCode.APS_SCHEDULE_NOT_NULL });

        // 值班计划不能为空
        if (schedule == null || schedule.getScheduleOptList() == null || schedule.getScheduleOptList().size() == 0) {
            throw new HsException(ASRespCode.APS_SCHEDULE_NOT_NULL);
        }
    }

    /**
     * 值班类型转换
     */
    void convertType() {
        if (schedule != null && schedule.getScheduleOptList() != null) {
            for (ScheduleOpt so : this.schedule.getScheduleOptList())
            {
                so.setWorkTypeTemp(WorkTypeStatus.getCode(so.getInputWorkName()));
            }
        }
    }

    /**
     * @return the scheduleJson
     */
    public String getScheduleJson() {
        return scheduleJson;
    }

    /**
     * @param scheduleJson
     *            the scheduleJson to set
     */
    public void setScheduleJson(String scheduleJson) {
        this.scheduleJson = scheduleJson;
        if (!StringUtils.isEmptyTrim(this.scheduleJson))
        {
            try
            {
                List<Schedule> sList = JSON.parseArray(URLDecoder.decode(this.scheduleJson, "UTF-8"), Schedule.class);
                if (sList != null) {
                    this.setSchedule(sList.get(0));
                }

            } catch (Exception e) {
                SystemLog.error("workflow", "setScheduleJson", "值班员计划转换异常", e);
            }
        }
    }

    /**
     * @return the schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * @param schedule
     *            the schedule to set
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;

        // 值班类型转换
        this.convertType();
    }

}
