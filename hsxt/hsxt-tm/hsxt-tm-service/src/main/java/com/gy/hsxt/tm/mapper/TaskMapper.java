/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.tm.bean.TmBaseParam;
import com.gy.hsxt.tm.bean.TmTask;

/**
 * 工单接口mapper dao映射类
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassName: TMTaskMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-10 上午11:19:52
 * @version V3.0.0
 */
public interface TaskMapper {

    /**
     * 保存工单
     * 
     * @param task
     *            工单信息
     * @return 成功记录数
     */
    public int insertTask(TmTask task);

    /**
     * 批量插入任务工单
     * 
     * @param tasks
     *            任务工单列表
     */
    public void insertBatchTask(@Param("tasks") List<TmTask> tasks);

    /**
     * 获取任务业务编号
     * 
     * @param taskId
     *            任务编号
     * @return 务编号
     */
    public TmTask findTaskBizNo(@Param("taskId") String taskId);

    /**
     * 获取工单信息
     * 
     * @param bizNo
     *            业务编号
     * @return 工单信息
     */
    public TmTask findTaskInfoByBizNo(@Param("bizNo") String bizNo);

    /**
     * 更新任务经办人
     * 
     * @param exeCustId
     *            经办人编号
     * @param exeCustName
     *            经办人名称
     * @param taskId
     *            任务Id
     * @param sendMod
     *            派单模式
     * @param isRedirect
     *            是否转派
     * @return 成功记录数
     */
    public int updateTaskExecutor(@Param("exeCustId") String exeCustId, @Param("exeCustName") String exeCustName,
            @Param("taskId") String taskId, @Param("sendMod") Integer sendMod, @Param("isRedirect") boolean isRedirect);

    /**
     * 更新任务经办人
     * 
     * @param exeCustId
     *            经办人编号
     * @param exeCustName
     *            经办人名称
     * @param taskId
     *            任务Id
     * @return 成功记录数
     */
    public int updateTaskExecutorAuto(@Param("exeCustId") String exeCustId, @Param("exeCustName") String exeCustName,
            @Param("taskId") String taskId);

    /**
     * 更新经办人为空
     * 
     * @param exeCustName
     *            经办人名称
     * @param taskId
     *            任务Id
     * @return 成功记录数
     */
    public int updateTaskExecutorNull(@Param("exeCustName") String exeCustName, @Param("taskId") String taskId);

    /**
     * 更新任务状态
     * 
     * @param taskId
     *            任务ID
     * @param status
     *            任务状态
     * @param remark
     *            备注
     * @return 成功记录数
     */
    public int updateTaskStatus(@Param("taskId") String taskId, @Param("status") Integer status,
            @Param("remark") String remark);

    /**
     * 更新任务催办状态
     * 
     * @param taskId
     *            任务ID
     * @param warnFlag
     *            是否催办
     * @return 成功记录数
     */
    public int updateWarnFlag(@Param("taskId") String taskId, @Param("warnFlag") boolean warnFlag);

    /**
     * 查询任务列表
     * 
     * @param taskQueryParam
     *            包装查询条件
     * @return 任务列表
     */
    public List<TmTask> findTaskListPage(TmBaseParam taskQueryParam);

    /**
     * 查询转派工单列表
     * 
     * @return 转派工单列表
     */
    public List<TmTask> findTurnTaskListPage();

    /**
     * 查询催办紧急工单
     * 
     * @return 工单列表
     */
    public List<TmTask> findUrgencyTask(@Param("timeOut") Integer timeOut);

    /**
     * 查询自动催办紧急工单的经办人客户号
     * 
     * @return 经办人客户号列表
     */
    public List<String> findAutoUrgencyTask(@Param("timeOut") Integer timeOut);

    /**
     * 查询所有未派单的工单
     * 
     * @return
     */
    public List<TmTask> findUnSendTaskList();

    /**
     * 更新任务紧急状态
     * 
     * @param taskId
     *            任务id
     * @return 更新成功记录数
     */
    public int updateTaskPriorityStat(@Param("taskId") String taskId);

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
}
