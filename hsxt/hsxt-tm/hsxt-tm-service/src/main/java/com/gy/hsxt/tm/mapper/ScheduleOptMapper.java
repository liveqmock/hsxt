/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.tm.bean.ScheduleOpt;

/**
 * 值班员排班mapper dao映射类
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassName: ScheduleOptMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-12 下午9:09:34
 * @version V3.0.0
 */
public interface ScheduleOptMapper {

    /**
     * 新增值班员排班
     * 
     * @param scheduleOpt
     *            值班员排班信息
     * @return 成功记录数
     */
    public int insertScheduleOpt(ScheduleOpt scheduleOpt);

    /**
     * 新增值班员排班：执行值班计划
     * 
     * @param scheduleOpt
     *            值班员排班信息
     * @return 成功记录数
     */
    public int insertExecuteScheduleOpt(ScheduleOpt scheduleOpt);

    /**
     * 批量插入值班员排班
     * 
     * @param ScheduleOpts
     *            值班员排班信息列表
     */
    public void insertBatchScheduleOpt(@Param("scheduleOpts") List<ScheduleOpt> scheduleOpts);

    /**
     * 修改值班员状态
     * 
     * @param optCustId
     *            值班员编号
     * @param workType
     *            值班状态
     * @return 成功记录数
     */
    public int updateWorkType(@Param("optCustId") String optCustId, @Param("workType") Integer workType);

    /**
     * 修改值班员临时状态
     * 
     * @param scheduleId
     *            值班计划编号
     * @param optCustId
     *            值班员编号
     * @param planDate
     *            排班日期
     * @param tempWorkType
     *            临时值班状态
     * @return 成功记录数
     */
    public int updateTempWorkType(@Param("scheduleId") String scheduleId, @Param("optCustId") String optCustId,
            @Param("planDate") String planDate, @Param("tempWorkType") Integer tempWorkType);

    /**
     * 修改值班员临时状态到正式状态
     * 
     * @param scheduleId
     *            值班计划编号
     * @param optCustId
     *            值班员编号
     * @param planDate
     *            排班日期
     * @param tempWorkType
     *            临时值班状态
     * @return 成功记录数
     */
    public int updateTempWorkTypeWorkType(@Param("scheduleId") String scheduleId, @Param("optCustId") String optCustId,
            @Param("planDate") String planDate, @Param("tempWorkType") Integer tempWorkType);

    /**
     * 启动值班计划时修改值班员状态：由临时状态改为正式
     * 
     * @return 成功记录数
     */
    public int updateTempWorkTypeToWorkType(@Param("scheduleId") String scheduleId);

    /**
     * 查询值班员排班
     * 
     * @param scheduleId
     *            计划编号
     * @param optCustId
     *            值班员编号
     * @param planDate
     *            排班日期
     * @return 排班信息
     */
    public ScheduleOpt findScheduleOpt(@Param("scheduleId") String scheduleId, @Param("optCustId") String optCustId,
            @Param("planDate") String planDate);

    /**
     * 查询休假天数
     * 
     * @param optCustId
     *            值班员编号
     * @param planDate
     *            排班日期
     * @return 休假天数
     */
    public int findRestNum(@Param("optCustId") String optCustId, @Param("planDate") String planDate);

    /**
     * 查询当天值班状态
     * 
     * @param optCustId
     *            值班员编号
     * @param planDate
     *            排班日期
     * @param scheduleId
     *            值班计划ID
     * @return 当天值班计划
     */
    public ScheduleOpt findWorkType(@Param("optCustId") String optCustId, @Param("planDate") String planDate,
            @Param("scheduleId") String scheduleId);

    /**
     * 查询值班员排班
     * 
     * @param groupId
     *            值班组编号
     * @param planYear
     *            计划年份
     * @param planMonth
     *            计划月份
     * @return 排班信息
     */
    public List<ScheduleOpt> findOperatorScheduleOpt(@Param("groupId") String groupId,
            @Param("planYear") String planYear, @Param("planMonth") String planMonth);

    /**
     * 查询未开始的排班计划
     * 
     * @param optCustId
     *            值班员编号
     * @param scheduleId
     *            计划编号
     * @param arrPlanDate
     *            排班日期数组
     * @return 记录数
     */
    public int findUnStartWorkPlan(@Param("optCustId") String optCustId, @Param("scheduleId") String scheduleId,
            @Param("arrPlanDate") String[] arrPlanDate);

    /**
     * 查询指定日期未开始的排班计划
     * 
     * @param scheduleOpt
     *            排班计划信息
     * @return 排班计划
     */
    public ScheduleOpt findUnStartWorkPlanSimDate(ScheduleOpt scheduleOpt);

    /**
     * 根据日期修改值班员状态
     * 
     * @param optCustId
     *            值班员编号
     * @param workType
     *            值班状态
     * 
     * @return 成功记录数
     */
    public int updateWorkTypeByPlanDate(@Param("scheduleId") String scheduleId, @Param("optCustId") String optCustId,
            @Param("workType") Integer workType, @Param("planDate") String planDate);

    /**
     * 删除当前时间以后的排班
     * 
     * @param scheduleIds
     *            值班计划编号列表
     * @param optCustId
     *            值班员编号
     * @return 成功记录数
     */
    public int deleteScheduleOptNowAfter(@Param("scheduleIds") List<String> scheduleIds,
            @Param("optCustId") String optCustId);

    public int deleteScheduleOpt(@Param("scheduleId") String scheduleId, @Param("optCustIds") Set<String> optCustIds);
}
