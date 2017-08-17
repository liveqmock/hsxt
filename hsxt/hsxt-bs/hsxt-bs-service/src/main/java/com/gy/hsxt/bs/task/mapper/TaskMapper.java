/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.task.bean.Task;

/**
 * 工单mapper dao映射
 * 
 * @Package: com.gy.hsxt.bs.task.mapper
 * @ClassName: TaskMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-29 下午8:46:01
 * @version V3.0.0
 */
@Repository
public interface TaskMapper {

    /**
     * 插入任务单
     * 
     * @param task
     *            任务数据
     * @return 成功记录数
     */
    public int insertTask(Task task);

    /**
     * 批量插入任务单
     * 
     * @Description:
     * @author: likui
     * @created: 2015年12月30日 上午11:46:14
     * @param tasks
     * @return
     * @return : int
     * @version V3.0.0
     */
    public void batchInsertTask(@Param("tasks") List<Task> tasks);

    /**
     * 更新任务执行人
     * 
     * @param exeCustId
     *            经办人编号
     * @param exeCustName
     *            经办人名称
     * @param assignHis
     *            派单历史
     * @param status
     *            工单状态
     * @param taskId
     *            任务编号
     * @return 成功记录数
     */
    public int updateTaskExecutor(@Param("exeCustId") String exeCustId, @Param("exeCustName") String exeCustName,
            @Param("assignHis") String assignHis, @Param("status") Integer status, @Param("taskId") String taskId);

    /**
     * 更新任务状态
     * 
     * @param taskId
     *            任务ID
     * @param status
     *            任务状态
     * @return 成功记录数
     */
    public int updateTaskStatus(@Param("taskId") String taskId, @Param("status") Integer status);

    /**
     * 获取原业务的任务ID
     * 
     * @param bizNo
     *            业务编号
     * @param exeCustId
     *            经办人编号
     * @return 任务ID
     */
    public String findSrcTaskId(@Param("bizNo") String bizNo, @Param("exeCustId") String exeCustId);

    /**
     * 根据任务ID查询任务
     * 
     * @param taskId
     *            任务ID
     * @return 任务对象
     */
    public Task findTaskById(@Param("taskId") String taskId);
}
