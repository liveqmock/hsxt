/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.tm.bean.Schedule;

/**
 * 值班计划mapper dao映射
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassName: ScheduleMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-12 下午8:16:31
 * @version V3.0.0
 */
public interface ScheduleMapper {

    /**
     * 新增值班计划
     * 
     * @param schedule
     *            值班计划信息
     * @return 成功记录数
     */
    public int insertSchedule(Schedule schedule);

    /**
     * 查找已存在值班计划
     * 
     * @param planYear
     *            值班组编号
     * @param planMonth
     *            计划年份
     * @param groupId
     *            计划月份
     * @return 记录数
     */
    public Schedule findExistsSchedule(@Param("planYear") String planYear, @Param("planMonth") String planMonth,
            @Param("groupId") String groupId);

    /**
     * 查找已存在值班计划
     * 
     * @param groupId
     *            值班组编号
     * @return 记录数
     */
    public String findScheduleByGroupId(@Param("groupId") String groupId);

    /**
     * 查询当前时间以后的值班计划编号
     * 
     * @param groupId
     *            值班组编号
     * @return 值班计划列表
     */
    public List<String> findNowAfterScheduleId(@Param("groupId") String groupId);

    /**
     * 更新值班计划状态
     * 
     * @param scheduleId
     *            计划编号
     * @param status
     *            计划状态
     * @return 成功记录数
     */
    public int updateScheduleStatus(@Param("scheduleId") String scheduleId, @Param("status") Integer status);

    /**
     * 暂停值班计划
     * 
     * @param groupId
     *            值班组编号
     * @return 成功记录数
     */
    public int updateScheduleStatusStop(@Param("groupId") String groupId);

    /**
     * 执行值班计划
     * 
     * @param groupId
     *            值班组编号
     * @return 成功记录数
     */
    public int updateScheduleStatusStart(@Param("groupId") String groupId);

}
