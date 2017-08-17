/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.tm.TMErrorCode;
import com.gy.hsxt.tm.api.ITMWorkPlanService;
import com.gy.hsxt.tm.bean.CustomScheduleOpt;
import com.gy.hsxt.tm.bean.Schedule;
import com.gy.hsxt.tm.bean.ScheduleOpt;
import com.gy.hsxt.tm.common.StringUtil;
import com.gy.hsxt.tm.disconf.TmConfig;
import com.gy.hsxt.tm.enumtype.ScheduleStauts;
import com.gy.hsxt.tm.interfaces.IOperatorService;
import com.gy.hsxt.tm.mapper.ScheduleMapper;
import com.gy.hsxt.tm.mapper.ScheduleOptMapper;

/**
 * 值班计划dubbo service实现类
 * 
 * @Package: com.gy.hsxt.tm.service
 * @ClassName: WorkPlanService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-13 上午9:41:49
 * @version V3.0.0
 */
@Service
public class WorkPlanService implements ITMWorkPlanService {
    /** 业务服务私有配置参数 **/
    @Autowired
    private TmConfig tmConfig;

    /**
     * 注入值班计划mapper
     */
    @Autowired
    ScheduleMapper scheduleMapper;

    /**
     * 注入排班mapper
     */
    @Autowired
    ScheduleOptMapper scheduleOptMapper;

    /**
     * 注入值班员service
     */
    @Autowired
    IOperatorService operatorService;

    /**
     * 保存值班计划
     * 
     * @param schedule
     *            值班计划信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMWorkPlanService#saveSchedule(com.gy.hsxt.tm.bean.Schedule)
     */
    @Override
    public String saveSchedule(Schedule schedule) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存值班计划,params[" + schedule + "]");
        // 值班计划信息为空
        HsAssert.notNull(schedule, TMErrorCode.TM_PARAMS_NULL, "保存值班计划：值班计划信息参数为空");
        try
        {
            // 查询重复值班计划
            Schedule isSchedule = scheduleMapper.findExistsSchedule(schedule.getPlanYear(), schedule.getPlanMonth(),
                    schedule.getGroupId());
            // 值班计划不存在
            if (isSchedule == null)
            {
                // 设置计划编号
                schedule.setScheduleId(StringUtil.getTmGuid(tmConfig.getAppNode()));
                // 计划状态
                schedule.setStatus(ScheduleStauts.DRAFT.getCode());
                // 新增值班计划
                scheduleMapper.insertSchedule(schedule);

                // 判断排班列表不为空
                if (schedule.getScheduleOptList() != null && schedule.getScheduleOptList().size() > 0)
                {
                    // 获取排班列表
                    List<ScheduleOpt> scheduleOptList = schedule.getScheduleOptList();
                    // 迭代排班列表
                    for (Iterator<ScheduleOpt> iterator = scheduleOptList.iterator(); iterator.hasNext();)
                    {
                        ScheduleOpt scheduleOpt = (ScheduleOpt) iterator.next();
                        // 设置值班计划ID
                        scheduleOpt.setScheduleId(schedule.getScheduleId());
                        // 插入值班计划
                        scheduleOptMapper.insertScheduleOpt(scheduleOpt);
                    }
                }
            }
            else
            {
                // 设置值班计划编号
                schedule.setScheduleId(isSchedule.getScheduleId());
                // 判断排班列表不为空
                if (schedule.getScheduleOptList() != null && schedule.getScheduleOptList().size() > 0)
                {
                    // 获取排班列表
                    List<ScheduleOpt> scheduleOptList = schedule.getScheduleOptList();
                    // 迭代排班列表
                    for (Iterator<ScheduleOpt> iterator = scheduleOptList.iterator(); iterator.hasNext();)
                    {
                        ScheduleOpt scheduleOpt = (ScheduleOpt) iterator.next();
                        // 查询是否已排班
                        ScheduleOpt isOpt = scheduleOptMapper.findScheduleOpt(schedule.getScheduleId(), scheduleOpt
                                .getOptCustId(), scheduleOpt.getPlanDate());
                        // 设置值班计划编号
                        scheduleOpt.setScheduleId(schedule.getScheduleId());
                        // 为空表示新的排班计划
                        if (isOpt == null)
                        {
                            // 插入值班计划
                            scheduleOptMapper.insertScheduleOpt(scheduleOpt);
                        }
                        else
                        {
                            // 更新值班计划
                            scheduleOptMapper.updateTempWorkType(scheduleOpt.getScheduleId(), scheduleOpt
                                    .getOptCustId(), scheduleOpt.getPlanDate(), scheduleOpt.getWorkTypeTemp());
                        }
                    }
                }
            }
            // 返回值班计划编号
            return schedule.getScheduleId();
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_SAVE_SCHEDULE_ERROR
                    .getCode()
                    + ":保存值班计划异常,params[" + schedule + "]", e);
            throw new HsException(TMErrorCode.TM_SAVE_SCHEDULE_ERROR, "保存值班计划异常,params[" + schedule + "]" + e);
        }
    }

    /**
     * 保存值班员排班
     * 
     * @param scheduleOpt
     *            值班员排班信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMWorkPlanService#saveScheduleOpt(com.gy.hsxt.tm.bean.ScheduleOpt)
     */
    @Override
    public void saveScheduleOpt(ScheduleOpt scheduleOpt) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存值班员排班,params[" + scheduleOpt + "]");
        // 值班员排班信息为空
        HsAssert.notNull(scheduleOpt, TMErrorCode.TM_PARAMS_NULL, "保存值班员排班：值班员排班信息参数为空");
        try
        {
            // 新增值班员排班
            scheduleOptMapper.insertScheduleOpt(scheduleOpt);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_SAVE_SCHEDULE_OPT_ERROR
                    .getCode()
                    + ":保存值班员排班异常,params[" + scheduleOpt + "]", e);
            throw new HsException(TMErrorCode.TM_SAVE_SCHEDULE_OPT_ERROR.getCode(), "保存值班员排班异常,params[" + scheduleOpt
                    + "]" + e);
        }
    }

    /**
     * 修改值班员值班状态
     * 
     * @param scheduleOpt
     *            值班信息
     * @see com.gy.hsxt.tm.api.ITMWorkPlanService#updateWorkType(com.gy.hsxt.tm.bean.ScheduleOpt)
     */
    @Override
    @Deprecated
    public void updateWorkType(ScheduleOpt scheduleOpt) {
        // SystemLog.debug(this.getClass().getName(),
        // Thread.currentThread().getStackTrace()[1].getMethodName(),
        // "修改值班员值班状态,params[" + scheduleOpt + "]");
        // // 值班信息为空
        // HsAssert.notNull(scheduleOpt, TMErrorCode.TM_PARAMS_NULL,
        // "修改值班员值班状态：值班信息参数为空");
        // // 校验输入参数不为空
        // try
        // {
        // // 更新值班员状态
        // scheduleOptMapper.updateWorkType(scheduleOpt.getOptCustId(),
        // scheduleOpt.getWorkType());
        // }
        // catch (Exception e)
        // {
        // SystemLog.error(this.getClass().getName(), "method:"
        // + Thread.currentThread().getStackTrace()[1].getMethodName(),
        // TMErrorCode.TM_UPDATE_WORK_TYPE_ERROR
        // .getCode()
        // + ":修改值班员值班状态异常,params[" + scheduleOpt + "]", e);
        // throw new
        // HsException(TMErrorCode.TM_UPDATE_WORK_TYPE_ERROR.getCode(),
        // "修改值班员值班状态异常,params[" + scheduleOpt
        // + "]" + e);
        // }
    }

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
     * @see com.gy.hsxt.tm.api.ITMWorkPlanService#pauseSchedule(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void pauseSchedule(String groupId, String entCustId, String entCustName, String operatorId,
            String operatorName) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "暂停值班计划,params[scheduleId:" + groupId + ",entCustId:" + entCustId + ",entCustName:" + entCustName
                        + ",operatorId:" + operatorId + ",operatorName:" + operatorName + "]");
        // 值班计划编号为空
        HsAssert.hasText(groupId, TMErrorCode.TM_PARAMS_NULL, "暂停值班计划：值班组编号参数为空");
        try
        {
            // 更新值班计划状态
            scheduleMapper.updateScheduleStatusStop(groupId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_PAUSE_SCHEDULE_ERROR
                    .getCode()
                    + ":暂停值班计划异常,params[scheduleId:"
                    + groupId
                    + ",entCustId:"
                    + entCustId
                    + ",entCustName:"
                    + entCustName + ",operatorId:" + operatorId + ",operatorName:" + operatorName + "]", e);
            throw new HsException(TMErrorCode.TM_PAUSE_SCHEDULE_ERROR.getCode(), "暂停值班计划异常,params[scheduleId:"
                    + groupId + ",entCustId:" + entCustId + ",entCustName:" + entCustName + ",operatorId:" + operatorId
                    + ",operatorName:" + operatorName + "]" + e);
        }
    }

    /**
     * 执行值班计划
     * 
     * @param customScheduleOpt
     *            值班信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMWorkPlanService#executeSchedule(com.gy.hsxt.tm.bean.CustomScheduleOpt)
     */
    @Override
    @Transactional
    public String executeSchedule(CustomScheduleOpt customScheduleOpt) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "执行值班计划,params[" + customScheduleOpt + "]");
        HsAssert.notNull(customScheduleOpt, TMErrorCode.TM_PARAMS_NULL, "执行值班计划：值班信息参数对象为空");
        HsAssert.hasText(customScheduleOpt.getGroupId(), TMErrorCode.TM_PARAMS_NULL, "执行值班计划：值班组编号参数为空");

        try
        {
            // 查询是否有值班计划
            Schedule schedule = scheduleMapper.findExistsSchedule(customScheduleOpt.getPlanYear(), customScheduleOpt
                    .getPlanMonth(), customScheduleOpt.getGroupId());

            if (schedule == null)
            {
                // 保存排班临时状态
                if (customScheduleOpt.getScheduleOptList().size() > 0)
                {
                    schedule = new Schedule();
                    // 设置计划编号
                    schedule.setScheduleId(StringUtil.getTmGuid(tmConfig.getAppNode()));
                    schedule.setGroupId(customScheduleOpt.getGroupId());
                    schedule.setPlanYear(customScheduleOpt.getPlanYear());
                    schedule.setPlanMonth(customScheduleOpt.getPlanMonth());
                    schedule.setStatus(ScheduleStauts.START.getCode());
                    schedule.setScheduleOptList(customScheduleOpt.getScheduleOptList());
                    // 新增值班计划
                    scheduleMapper.insertSchedule(schedule);
                    // 判断排班列表不为空
                    if (schedule.getScheduleOptList() != null && schedule.getScheduleOptList().size() > 0)
                    {
                        // 获取排班列表
                        List<ScheduleOpt> scheduleOptList = schedule.getScheduleOptList();
                        // 迭代排班列表
                        for (Iterator<ScheduleOpt> iterator = scheduleOptList.iterator(); iterator.hasNext();)
                        {
                            ScheduleOpt scheduleOpt = (ScheduleOpt) iterator.next();
                            // 设置值班计划ID
                            scheduleOpt.setScheduleId(schedule.getScheduleId());
                            // 插入值班计划
                            scheduleOptMapper.insertScheduleOpt(scheduleOpt);
                        }
                    }

                    // 更新排班状态：临时到正式
                    scheduleOptMapper.updateTempWorkTypeToWorkType(schedule.getScheduleId());

                    // 给参数对象中的计划编号设值
                    customScheduleOpt.setScheduleId(schedule.getScheduleId());

                    return schedule.getScheduleId();
                }
            }
            else
            {
                // 更新排班状态：临时到正式
                scheduleOptMapper.updateTempWorkTypeToWorkType(customScheduleOpt.getScheduleId());

                // 获取排班列表
                List<ScheduleOpt> scheduleOptList = customScheduleOpt.getScheduleOptList();
                // 迭代排班列表
                for (Iterator<ScheduleOpt> iterator = scheduleOptList.iterator(); iterator.hasNext();)
                {
                    ScheduleOpt scheduleOpt = (ScheduleOpt) iterator.next();
                    // 查询是否已排班
                    ScheduleOpt isOpt = scheduleOptMapper.findScheduleOpt(customScheduleOpt.getScheduleId(),
                            scheduleOpt.getOptCustId(), scheduleOpt.getPlanDate());
                    // 为空表示新的排班计划
                    if (isOpt == null)
                    {
                        // 设置值班计划编号
                        scheduleOpt.setScheduleId(schedule.getScheduleId());
                        scheduleOpt.setWorkType(scheduleOpt.getWorkType());
                        scheduleOpt.setWorkTypeTemp(null);
                        // 插入值班计划
                        scheduleOptMapper.insertExecuteScheduleOpt(scheduleOpt);
                    }
                    else
                    {
                        // 更新值班计划
                        scheduleOptMapper.updateTempWorkTypeWorkType(customScheduleOpt.getScheduleId(), scheduleOpt
                                .getOptCustId(), scheduleOpt.getPlanDate(), scheduleOpt.getWorkTypeTemp());
                    }
                }
            }
            // 更新值班计划状态
            scheduleMapper.updateScheduleStatusStart(customScheduleOpt.getGroupId());
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_EXECUTE_SCHEDULE_ERROR
                    .getCode()
                    + ":执行值班计划异常,params[" + customScheduleOpt + "]", e);
            throw new HsException(TMErrorCode.TM_EXECUTE_SCHEDULE_ERROR.getCode(), "执行值班计划异常,params["
                    + customScheduleOpt + "]" + e);
        }
        return customScheduleOpt.getScheduleId();
    }

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
     * @see com.gy.hsxt.tm.api.ITMWorkPlanService#changeTheShift(com.gy.hsxt.tm.bean.ScheduleOpt,
     *      java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void changeTheShift(ScheduleOpt scheduleOpt, String planDate1, String planDate2) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "调班,params[" + scheduleOpt + ",planDate1:" + planDate1 + ",planDate2:" + planDate2 + "]");

        // 排班信息为空
        HsAssert.notNull(scheduleOpt, TMErrorCode.TM_PARAMS_NULL, "调班：排班信息参数为空");
        HsAssert.hasText(planDate1, TMErrorCode.TM_PARAMS_NULL, "调班：排班日期参数为空");
        HsAssert.hasText(planDate2, TMErrorCode.TM_PARAMS_NULL, "调班：排班日期参数为空");

        // 查询返回记录数
        int returnNum = 0;
        // 检验月份是否相同
        int month = DateUtil.compareDate(planDate1, planDate2, 1);
        // 调班月份不相同
        HsAssert.isTrue(month != 0, TMErrorCode.TM_CHANGE_SHIFT_MONTH_NOT_SAME, "调班：月份不相同");
        try
        {
            // 查询未执行的值班计划：0表示无执行计划，1表示只有一个日期排班
            returnNum = scheduleOptMapper.findUnStartWorkPlan(scheduleOpt.getOptCustId(), scheduleOpt.getScheduleId(),
                    new String[] { planDate1, planDate2 });

            HsAssert.isTrue(returnNum != 0, TMErrorCode.TM_CHANGE_SHIFT_NO_SCHEDULE_OR_SCHOPT, "调班：无值班计划或无排班");
            HsAssert.isTrue(returnNum != 1, TMErrorCode.TM_SCHEDULEOPT_NUM_ERROR, "调班：须有两个排班才可调班");

            // 查询日期1的信息
            ScheduleOpt sOpt1 = scheduleOptMapper.findScheduleOpt(scheduleOpt.getScheduleId(), scheduleOpt
                    .getOptCustId(), planDate1);
            ScheduleOpt sOpt2 = scheduleOptMapper.findScheduleOpt(scheduleOpt.getScheduleId(), scheduleOpt
                    .getOptCustId(), planDate2);

            // 对调两个值班状态
            scheduleOptMapper.updateWorkTypeByPlanDate(scheduleOpt.getScheduleId(), scheduleOpt.getOptCustId(), sOpt2
                    .getWorkType(), planDate1);
            scheduleOptMapper.updateWorkTypeByPlanDate(scheduleOpt.getScheduleId(), scheduleOpt.getOptCustId(), sOpt1
                    .getWorkType(), planDate2);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_CHANGE_SHIFT_ERROR
                    .getCode()
                    + ":调班异常,params[" + scheduleOpt + ",planDate1:" + planDate1 + ",planDate2:" + planDate2 + "]", e);
            throw new HsException(TMErrorCode.TM_CHANGE_SHIFT_ERROR.getCode(), "调班异常,params[" + scheduleOpt
                    + ",planDate1:" + planDate1 + ",planDate2:" + planDate2 + "]" + e);
        }
    }

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
     * @see com.gy.hsxt.tm.api.ITMWorkPlanService#changeOfShift(com.gy.hsxt.tm.bean.ScheduleOpt,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void changeOfShift(ScheduleOpt scheduleOpt, String optCustId1, String optCustId2) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "换班,params[" + scheduleOpt + ",optCustId1:" + optCustId1 + ",optCustId2:" + optCustId2 + "]");

        // 排班信息为空
        HsAssert.notNull(scheduleOpt, TMErrorCode.TM_PARAMS_NULL, "换班：排班信息参数为空");
        HsAssert.hasText(optCustId1, TMErrorCode.TM_PARAMS_NULL, "换班：值班员编号参数为空");
        HsAssert.hasText(optCustId2, TMErrorCode.TM_PARAMS_NULL, "换班：值班员编号参数为空");
        try
        {
            // 设置值班员编号
            scheduleOpt.setOptCustId(optCustId1);
            // 值班员人的排班信息
            ScheduleOpt schOpt1 = scheduleOptMapper.findUnStartWorkPlanSimDate(scheduleOpt);
            // 调换两个值班员同一天的值班状态
            scheduleOptMapper.updateWorkTypeByPlanDate(scheduleOpt.getScheduleId(), scheduleOpt.getOptCustId(), schOpt1
                    .getWorkType(), scheduleOpt.getPlanDate());

            // 设置值班员编号
            scheduleOpt.setOptCustId(optCustId2);
            // 值班员人的排班信息
            ScheduleOpt schOpt2 = scheduleOptMapper.findUnStartWorkPlanSimDate(scheduleOpt);
            // 调换两个值班员同一天的值班状态
            scheduleOptMapper.updateWorkTypeByPlanDate(scheduleOpt.getScheduleId(), scheduleOpt.getOptCustId(), schOpt2
                    .getWorkType(), scheduleOpt.getPlanDate());
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_CHANGE_OF_SHIFT_ERROR
                    .getCode()
                    + ":调班异常,params[" + scheduleOpt + ",optCustId1:" + optCustId1 + ",optCustId2:" + optCustId2 + "]",
                    e);
            throw new HsException(TMErrorCode.TM_CHANGE_OF_SHIFT_ERROR.getCode(), "换班异常,params[" + scheduleOpt
                    + ",optCustId1:" + optCustId1 + ",optCustId2:" + optCustId2 + "]" + e);
        }
    }
}
