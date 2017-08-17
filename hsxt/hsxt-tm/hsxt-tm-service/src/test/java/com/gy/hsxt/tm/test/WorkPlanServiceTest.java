/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.tm.api.ITMWorkPlanService;
import com.gy.hsxt.tm.bean.Schedule;
import com.gy.hsxt.tm.bean.ScheduleOpt;
import com.gy.hsxt.tm.enumtype.ScheduleStauts;
import com.gy.hsxt.tm.mapper.ScheduleOptMapper;

public class WorkPlanServiceTest extends BaseTest {

    @Autowired
    ITMWorkPlanService workPlanService;

    @Autowired
    ScheduleOptMapper scheduleOptMapper;

    /**
     * 保存值班计划
     */
    @Test
    public void testSaveSchedule() {
        Schedule schedule = new Schedule();
        schedule.setGroupId("120120160104093545000000");
        schedule.setPlanYear("2016");
        schedule.setPlanMonth("1");
        schedule.setStatus(ScheduleStauts.DRAFT.getCode());

        List<ScheduleOpt> listSo = new ArrayList<ScheduleOpt>();
        ScheduleOpt opt1 = new ScheduleOpt();
        opt1.setOptCustId("00000000001");
        opt1.setPlanDate(com.gy.hsxt.common.utils.DateUtil.getCurrentDateNoSign());
        opt1.setWorkType(1);
        opt1.setWorkTypeTemp(2);
        listSo.add(opt1);

        ScheduleOpt opt2 = new ScheduleOpt();
        opt2.setOptCustId("00000000002");
        opt2.setPlanDate(com.gy.hsxt.common.utils.DateUtil.getCurrentDateNoSign());
        opt2.setWorkType(1);
        opt2.setWorkTypeTemp(2);
        listSo.add(opt2);

        schedule.setScheduleOptList(listSo);

        workPlanService.saveSchedule(schedule);
    }

    /**
     * 给值班员排班
     */
    @Test
    public void testSaveScheduleOpt() {
        List<String> optCustIdList = new ArrayList<String>();
        optCustIdList.add("00000000156000220160105");
        optCustIdList.add("00000000156000320160105");
        ScheduleOpt scheduleOpt = null;
        for (int j = 1; j <= optCustIdList.size(); j++)
        {
            for (int i = 1; i <= 31; i++)
            {
                scheduleOpt = new ScheduleOpt();
                scheduleOpt.setScheduleId("120120160109122834000001");
                scheduleOpt.setOptCustId(optCustIdList.get(j));
                String pd = i < 10 ? "0" + i : i + "";
                scheduleOpt.setPlanDate("201601" + pd);
                scheduleOpt.setWorkType(1);// 手工插入不记录该数据，需要到数据库更新临时状态到正式状态
                scheduleOpt.setWorkTypeTemp(j);
                workPlanService.saveScheduleOpt(scheduleOpt);
            }
            scheduleOptMapper.updateWorkType(optCustIdList.get(j - 1), j);
        }
    }
}
