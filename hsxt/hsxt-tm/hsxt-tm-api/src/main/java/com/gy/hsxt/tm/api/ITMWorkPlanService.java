/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.CustomScheduleOpt;
import com.gy.hsxt.tm.bean.Schedule;
import com.gy.hsxt.tm.bean.ScheduleOpt;

/**
 * 值班计划接口
 * 
 * @Package: com.gy.hsxt.tm.api
 * @ClassName: ITMWorkPlayService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-13 上午9:26:30
 * @version V3.0.0
 */
public interface ITMWorkPlanService {

    /**
     * 保存值班计划
     * 
     * @param schedule
     *            值班计划信息
     * @return 值班计划编号
     * @throws HsException
     */
    public String saveSchedule(Schedule schedule) throws HsException;

    /**
     * @param customScheduleOpt
     *            值班信息
     * @return 值班计划ID
     * @throws HsException
     */
    public String executeSchedule(CustomScheduleOpt customScheduleOpt) throws HsException;

    /**
     * 暂停值班计划
     * 
     * @param groupId
     *            值班组编号
     * @param entCustId
     *            企业客户号
     * @param entCustName
     *            企业名称
     * @param operatorId
     *            操作员客户号
     * @param operatorName
     *            操作员名称
     * @throws HsException
     */
    public void pauseSchedule(String groupId, String entCustId, String entCustName, String operatorId,
            String operatorName) throws HsException;

    /**
     * 保存值班员排班
     * 
     * @param scheduleOpt
     *            值班员排班信息
     */
    public void saveScheduleOpt(ScheduleOpt scheduleOpt) throws HsException;

    /**
     * 修改值班员值班状态
     * 
     * @param scheduleOpt
     *            排班信息
     */
    public void updateWorkType(ScheduleOpt scheduleOpt) throws HsException;

    /**
     * 调班
     * 
     * @param scheduleOpt
     *            排班信息
     * @param planDate1
     *            排班日期1
     * @param planDate2
     *            排班日期2
     * @throws HsException
     */
    public void changeTheShift(ScheduleOpt scheduleOpt, String planDate1, String planDate2) throws HsException;

    /**
     * 换班
     * 
     * @param scheduleOpt
     *            排班信息
     * @param optCustId1
     *            值班员编号1
     * @param optCustId2
     *            值班员编号2
     * @throws HsException
     */
    public void changeOfShift(ScheduleOpt scheduleOpt, String optCustId1, String optCustId2) throws HsException;

}
