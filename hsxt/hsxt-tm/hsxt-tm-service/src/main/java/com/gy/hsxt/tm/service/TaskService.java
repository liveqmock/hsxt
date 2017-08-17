/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.tm.TMErrorCode;
import com.gy.hsxt.tm.api.ITMSyncTaskService;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.tm.bean.BaseBean;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.bean.TmBaseParam;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.common.PageContext;
import com.gy.hsxt.tm.common.TaskBeanUtil;
import com.gy.hsxt.tm.disconf.TmConfig;
import com.gy.hsxt.tm.enumtype.SendOrderPattern;
import com.gy.hsxt.tm.enumtype.SendTaskMod;
import com.gy.hsxt.tm.enumtype.TaskSrc;
import com.gy.hsxt.tm.interfaces.ITaskService;
import com.gy.hsxt.tm.mapper.OperatorMapper;
import com.gy.hsxt.tm.mapper.TaskMapper;
import com.gy.hsxt.tm.mapper.WorkTypeMapper;

/**
 * 任务dubbo service实现类
 * 
 * @Package: com.gy.hsxt.tm.service
 * @ClassName: TMTaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-10 下午12:24:54
 * @version V3.0.0
 */
@Service("taskService")
public class TaskService implements ITMTaskService, ITaskService {
    /** 业务服务私有配置参数 **/
    @Autowired
    private TmConfig tmConfig;

    /**
     * 注入任务mapper接口
     */
    @Autowired
    TaskMapper taskMapper;

    /**
     * 注入值班员排班mapper
     */
    @Autowired
    OperatorMapper optMapper;

    /**
     * 注入值班状态mapper
     */
    @Autowired
    WorkTypeMapper workTypeMapper;

    /**
     * 同步更新源任务服务
     */
    ITMSyncTaskService syncService;

    /**
     * 实现保存任务外部接口
     * 
     * @param task
     *            任务信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMTaskService#saveTMTask(com.gy.hsxt.tm.bean.TmTask)
     */
    @Override
    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTMTask(TmTask task) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存任务工单,params[" + task + "]");
        // 成功记录数
        // int resultNum = 0;
        try
        {
            // 校验输入参数不能为空
            HsAssert.notNull(task, TMErrorCode.TM_PARAMS_NULL, "保存任务工单：实体参数为空");
            // 业务类型为空
            HsAssert.hasText(task.getBizType(), TMErrorCode.TM_PARAMS_NULL, "保存任务工单：业务类型参数为空");
            // 业务编号为空
            HsAssert.hasText(task.getBizNo(), TMErrorCode.TM_PARAMS_NULL, "保存任务工单：业务编号参数为空");
            // 企业客户号为空
            HsAssert.hasText(task.getEntCustId(), TMErrorCode.TM_PARAMS_NULL, "保存任务工单：企业客户号参数为空");

            // 保存任务
            taskMapper.insertTask(task);

            // TODO 是否需要实时派
            // if (resultNum > 0)
            // {
            // // 自动派单
            // sendOrder(SendOrderPattern.AUTO_SEND.getCode(),
            // task.getTaskId());
            // }
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
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_SAVE_TASK_ERROR
                    .getCode()
                    + "保存任务工单异常,params[" + task + "]", e);
            throw new HsException(TMErrorCode.TM_SAVE_TASK_ERROR.getCode(), "保存任务工单异常,params[" + task + "]" + e);
        }
    }

    /**
     * 派单
     * 
     * @param pattern
     *            派单
     * @param taskIds
     *            工单号列表
     * @param optCustIds
     *            值班员编号列表
     * @param baseBean
     *            公共参数bean
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMTaskService#sendOrder(java.lang.Integer,
     *      java.util.List, java.util.List, com.gy.hsxt.tm.bean.BaseBean)
     */
    @Override
    @Transactional
    public void sendOrder(Integer pattern, List<String> taskIds, List<String> optCustIds, BaseBean baseBean)
            throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "派单,params[pattern:" + pattern + ",taskIds:" + taskIds + ",optCustIds:" + optCustIds + ",baseBean:"
                        + baseBean + "]");
        List<String> operatorCustIdList = null;
        // 任务对象
        TmTask task = null;
        // 值班员对象
        Operator executor = null;
        // 校验派单模式参数为空
        HsAssert.notNull(pattern, TMErrorCode.TM_PARAMS_NULL, "派单：派单模式参数为空");
        try
        {
            // 自动派单
            if (pattern == SendOrderPattern.AUTO_SEND.getCode().intValue())
            {
                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "params[taskIds:" + taskIds + "]", "自动派单");
                if (taskIds != null && taskIds.size() > 0)
                {
                    for (String taskId : taskIds)
                    {
                        // 根据任务ID查询出任务工单
                        task = taskMapper.findTaskBizNo(taskId);
                        // 工单对象为空
                        HsAssert.notNull(task, TMErrorCode.TM_NOT_QUERY_DATA, "自动派单：未查询到当前工单号对应的记录");
                        // 获取目标实现类
                        syncService = TaskBeanUtil.getTargetBean(task.getTaskSrc());

                        // 获取授权值班员ID
                        operatorCustIdList = getAuthOperatorList(task.getBizType(), task.getEntCustId());
                        // HsAssert.isTrue(CollectionUtils.isNotEmpty(operatorCustIdList),
                        // TMErrorCode.TM_NOT_AUTH_OPT,
                        // "自动派单：日程安排没有操作员可办理此业务,params[bizType:" +
                        // task.getBizType() + ",entCustId:"
                        // + task.getEntCustId() + "]");
                        BizLog.biz(this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[1].getMethodName(), "params", "自动派单,有权限的值班员客户号,"
                                        + operatorCustIdList);
                        if (operatorCustIdList != null && operatorCustIdList.size() > 0)
                        {
                            // 分配执行人
                            executor = getScheduleOpt(operatorCustIdList);
                            BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                                    .getMethodName(), "params", "自动派单,随机分派给," + executor);
                            if (executor != null)
                            {
                                // 更新工单执行人
                                taskMapper.updateTaskExecutor(executor.getOptCustId(), executor.getOperatorName(), task
                                        .getTaskId(), SendTaskMod.HAND_SEND.getCode(), true);
                                if (task.getTaskSrc() == TaskSrc.WS.getCode())
                                {
                                    // 同步源任务执行人
                                    syncService.updateSrcTaskExecutor(executor.getOptCustId(), executor
                                            .getOperatorName(), executor.getOperatorName(), task.getBizType(), task
                                            .getTaskId());
                                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                                            .getMethodName(), "params", "自动派单,同步更新业务单完毕");
                                }
                                else
                                {
                                    // 同步源任务执行人
                                    syncService.updateSrcTaskExecutor(executor.getOptCustId(), executor
                                            .getOperatorName(), executor.getOperatorName(), task.getTaskId());
                                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                                            .getMethodName(), "params", "自动派单,同步更新业务单完毕");
                                }
                            }
                        }
                    }
                }
            }

            // 手动派单
            if (pattern == SendOrderPattern.MANUAL_SEND.getCode().intValue())
            {
                if (taskIds != null && taskIds.size() > 0)
                {
                    for (String taskId : taskIds)
                    {
                        task = taskMapper.findTaskBizNo(taskId);
                        BizLog.biz(this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[1].getMethodName(), "params", "手动派单," + task);
                        // 工单对象为空
                        HsAssert.notNull(task, TMErrorCode.TM_NOT_QUERY_DATA, "手动派单：未查询到当前工单号对应的记录");
                        HsAssert.isTrue(task.getStatus() != TaskStatus.COMPLETED.getCode().intValue(),
                                TMErrorCode.TM_NOT_QUERY_DATA, "手动派单：当前工单已办结");
                        // 获取目标实现类
                        syncService = TaskBeanUtil.getTargetBean(task.getTaskSrc());
                        // 分配执行人
                        executor = getScheduleOpt(optCustIds);
                        BizLog.biz(this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[1].getMethodName(), "params", "手动派单,分派给,"
                                        + executor);
                        if (executor != null)
                        {
                            // 更新工单执行人
                            taskMapper.updateTaskExecutor(executor.getOptCustId(), executor.getOperatorName(), task
                                    .getTaskId(), SendTaskMod.HAND_SEND.getCode(), true);
                            if (task.getTaskSrc() == TaskSrc.WS.getCode())
                            {
                                // 同步源任务执行人
                                syncService.updateSrcTaskExecutor(executor.getOptCustId(), executor.getOperatorName(),
                                        executor.getOperatorName(), task.getBizType(), task.getTaskId());
                                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                                        .getMethodName(), "params", "手动派单,同步更新业务单完毕");
                            }
                            else
                            {
                                // 同步源任务执行人
                                syncService.updateSrcTaskExecutor(executor.getOptCustId(), executor.getOperatorName(),
                                        executor.getOperatorName(), task.getTaskId());
                                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                                        .getMethodName(), "params", "手动派单,同步更新业务单完毕");
                            }
                        }
                    }
                }
            }
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
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_SEND_ORDER_ERROR
                    .getCode()
                    + "派单异常,params[pattern:"
                    + pattern
                    + ",taskIds:"
                    + taskIds
                    + ",optCustIds:"
                    + optCustIds
                    + ",baseBean:" + baseBean + "]", e);
            throw new HsException(TMErrorCode.TM_SEND_ORDER_ERROR.getCode(), "派单异常,params[pattern:" + pattern
                    + ",taskIds:" + taskIds + ",optCustIds:" + optCustIds + ",baseBean:" + baseBean + "]" + e);
        }
    }

    /**
     * 保存批量工单任务
     * 
     * @param tasks
     *            工单信息列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMTaskService#saveTMTask(java.util.List)
     */
    @Override
    public void saveTMTask(List<TmTask> tasks) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存批量工单任务,params[" + tasks + "]");
        HsAssert.notNull(tasks, TMErrorCode.TM_PARAMS_NULL, "保存批量工单任务：工单信息列表参数为空");
        try
        {
            // 批量插入工单
            taskMapper.insertBatchTask(tasks);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_BATCH_SAVE_TASK_ERROR
                    .getCode()
                    + ":保存批量工单任务异常,params[" + tasks + "]", e);
            throw new HsException(TMErrorCode.TM_BATCH_SAVE_TASK_ERROR.getCode(), "保存批量工单任务异常,params[" + tasks + "]"
                    + e);
        }
    }

    /**
     * 获取当班值班员
     * 
     * @param entCustId
     *            企业客户号
     * @return 当班值班员列表
     * @throws HsException
     * @see com.gy.hsxt.tm.interfaces.ITaskService#getOnLineOperatorList(java.lang.String)
     */
    @Override
    public List<String> getOnLineOperatorList(String entCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取当班值班员,params[entCustId:" + entCustId + "]");
        List<String> operatorList = null;
        try
        {
            operatorList = optMapper.findWorkOnOperatorByEntCustId(entCustId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_GET_ON_WORK_OPERATOR_ERROR.getCode() + ":查询当班的值班员时异常,params[entCustId:" + entCustId
                            + "]", e);
            throw new HsException(TMErrorCode.TM_GET_ON_WORK_OPERATOR_ERROR.getCode(), "查询当班的值班员时异常,params[entCustId:"
                    + entCustId + "]" + e);
        }
        return operatorList;
    }

    /**
     * * 派单：包装派单方法为指定的参数类型
     * 
     * @param pattern
     *            派单
     * @param taskId
     *            工单号
     */
    public void sendOrder(Integer pattern, String taskId) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "派单,params[pattern:" + pattern + ",taskId:" + taskId + "]");
        List<String> taskIdList = new ArrayList<String>();
        taskIdList.add(taskId);
        // 派单
        sendOrder(pattern, taskIdList, null, null);
    }

    /**
     * 更新任务状态
     * 
     * @param taskId
     *            任务id
     * @param taskStatus
     *            任务状态
     * @param remark
     *            备注
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMTaskService#updateTaskStatus(java.lang.String,
     *      java.lang.Integer, java.lang.String)
     */
    @Override
    public void updateTaskStatus(String taskId, Integer status, String remark) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新任务状态,params[taskId:" + taskId + ",status:" + status + ",remark:" + remark + "]");
        // 任务编号为空
        HsAssert.hasText(taskId, TMErrorCode.TM_PARAMS_NULL, "更新任务状态：任务编号为空");
        // 任务状态为空
        HsAssert.notNull(status, TMErrorCode.TM_PARAMS_NULL, "更新任务状态：任务状态为空");
        TmTask task = null;
        try
        {
            task = taskMapper.findTaskBizNo(taskId);
            HsAssert.isTrue(task.getStatus() != TaskStatus.COMPLETED.getCode().intValue(),
                    TMErrorCode.TM_NOT_QUERY_DATA, "更新任务状态：当前工单已办结");
            // 执行更新
            taskMapper.updateTaskStatus(taskId, status, remark);

            // 如果是积分福利
            if (TaskSrc.WS.getCode() == task.getTaskSrc())
            {
                if (TaskStatus.HANG_UP.getCode().intValue() == status || TaskStatus.UNACCEPT.getCode() == status)
                {
                    // 获取目标实现类
                    syncService = TaskBeanUtil.getTargetBean(task.getTaskSrc());
                    // 同步源任务
                    syncService.updateSrcTaskExecutor("", "", "", task.getBizType(), task.getTaskId());
                    return;
                }
            }

            // 如果工单已挂起，同步更新源业务单为挂起
            if (TaskStatus.HANG_UP.getCode().intValue() == status)
            {
                // 获取目标实现类
                syncService = TaskBeanUtil.getTargetBean(task.getTaskSrc());
                // 同步源任务
                syncService.updateSrcTaskExecutor("", "", "", taskId);
            }
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "更新任务状态异常,params[taskId:" + taskId + ",status:" + status + ",remark:" + remark + "]", e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_UPDATE_TASK_STATUS_ERROR.getCode() + "更新任务状态异常,params[taskId:" + taskId + ",status:"
                            + status + ",remark:" + remark + "]", e);
            throw new HsException(TMErrorCode.TM_UPDATE_TASK_STATUS_ERROR.getCode(), "更新任务状态异常,params[taskId:" + taskId
                    + ",status:" + status + ",remark:" + remark + "]" + e);
        }
    }

    /**
     * 更新任务紧急状态
     * 
     * @param taskId
     *            任务id
     * @return 更新成功记录数
     * @throws HsException
     * @see com.gy.hsxt.tm.interfaces.ITaskService#updateTaskPriorityStat(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public int updateTaskPriorityStat(String taskId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新任务紧急状态,params[taskId:" + taskId + "]");
        // 更新记录数
        int returnNum = 0;
        // 校验任务id不为空
        HsAssert.hasText(taskId, TMErrorCode.TM_PARAMS_NULL, "更新任务紧急状态：任务ID参数为空");
        try
        {
            // 更新任务状态
            returnNum = taskMapper.updateTaskPriorityStat(taskId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_UPDATE_TASK_STATUS_ERROR.getCode() + "更新任务状态异常,params[taskId:" + taskId + "]", e);
            throw new HsException(TMErrorCode.TM_UPDATE_TASK_PRIORITY_STAT_ERROR.getCode(),
                    "更新任务紧急状态时异常,params[taskId:" + taskId + "]" + e);
        }
        return returnNum;
    }

    /**
     * 更新催办状态
     * 
     * @param taskId
     *            任务编号
     * @param warnFlag
     *            true是，false否
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMTaskService#updateWarnFlag(java.lang.String,
     *      boolean)
     */
    @Override
    public void updateWarnFlag(String taskId, boolean warnFlag) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新催办状态,params[taskId:" + taskId + ",warnFlag:" + warnFlag + "]");
        // 校验任务id不为空
        HsAssert.hasText(taskId, TMErrorCode.TM_PARAMS_NULL, "更新催办状态：任务ID参数为空");
        try
        {
            // 更新任务催办状态
            taskMapper.updateWarnFlag(taskId, warnFlag);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_UPDATE_WARN_FLAG_ERROR
                    .getCode()
                    + "更新催办状态异常,params[taskId:" + taskId + ",warnFlag:" + warnFlag + "]", e);
            throw new HsException(TMErrorCode.TM_UPDATE_WARN_FLAG_ERROR.getCode(), "更新催办状态异常,params[taskId:" + taskId
                    + ",warnFlag:" + warnFlag + "]" + e);
        }
    }

    /**
     * 查询转派工单列表
     * 
     * @return 转派工单列表
     * @see com.gy.hsxt.tm.interfaces.ITaskService#getTurnTaskListPage()
     */
    @Override
    public PageData<TmTask> getTurnTaskListPage(Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询转派工单列表,params[" + page + "]");
        // 验证分页参数
        HsAssert.notNull(page, TMErrorCode.TM_PAGE_PARAM_NULL, "查询转派工单列表：分页参数为空");
        PageData<TmTask> taskListPage = null;
        // 设置分页信息
        PageContext.setPage(page);
        try
        {
            // 执行查询
            List<TmTask> taskList = taskMapper.findTurnTaskListPage();
            // 验证查询结果
            if (taskList != null && taskList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                taskListPage = new PageData<TmTask>(page.getCount(), taskList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_QUERY_TURN_TASK_LIST_ERROR.getCode() + "查询转派工单列表异常", e);
            throw new HsException(TMErrorCode.TM_QUERY_TURN_TASK_LIST_ERROR.getCode(), "查询转派工单列表异常");
        }
        return taskListPage;
    }

    /**
     * 查询催办紧急工单
     * 
     * @return 工单列表
     * @throws HsException
     * @see com.gy.hsxt.tm.interfaces.ITaskService#getUrgencyTask(java.lang.Integer)
     */
    @Override
    public List<TmTask> getUrgencyTask() throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询催办紧急工单列表,params[]");
        List<TmTask> urgencyTaskList = null;
        // 获取紧急工单超时时间
        int urgencyTimeOut = tmConfig.getTimeOut();
        try
        {
            // 查询紧急工单
            urgencyTaskList = taskMapper.findUrgencyTask(urgencyTimeOut * 60);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_QUERY_URGENCY_TASK_ERROR.getCode() + "查询催办紧急工单列表异常", e);
            throw new HsException(TMErrorCode.TM_QUERY_URGENCY_TASK_ERROR.getCode(), "查询催办紧急工单列表异常");
        }
        return urgencyTaskList;
    }

    /**
     * 获取值班员信息
     * 
     * @param optCustIds
     *            值班员编号
     * @return Random random = new Random();
     */
    @Override
    public Operator getScheduleOpt(List<String> optCustIds) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班员信息：随机获取一个值班员信息," + optCustIds);
        Operator operator = null;
        Random random = new Random();
        String optCustId = "";
        try
        {
            if (optCustIds != null && optCustIds.size() > 0)
            {
                int index = random.nextInt(optCustIds.size());
                optCustId = optCustIds.get(index);
                operator = optMapper.findOperatorInfo(optCustId);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_GET_RANDOM_OPERATOR_ERROR.getCode() + ":获取随机值班员异常,params[optCustId:" + optCustId
                            + "]", e);
            throw new HsException(TMErrorCode.TM_GET_RANDOM_OPERATOR_ERROR.getCode(), "获取随机值班员异常,params[optCustId:"
                    + optCustId + "]" + e);
        }
        return operator;
    }

    /**
     * 获取有权限的值班员列表
     * 
     * @param bizType
     *            业务类型
     * @param operatorList
     *            值班员列表
     * @return 有权限的值班员列表
     */
    @Override
    public List<String> getAuthOperatorList(String bizType, String entCustId) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "自动派单：获取有权限的值班员列表,params[bizType:" + bizType + ",entCustId:" + entCustId + "]");
        // 授权值班员列表
        List<String> authOperatorList = null;
        Operator authOperator = null;
        // 获取当前值班员ID列表
        List<String> optCustIdList = getOnLineOperatorList(entCustId);
        BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "params",
                "自动派单：2当前值班员ID列表," + optCustIdList);
        if (optCustIdList != null && optCustIdList.size() > 0)
        {
            authOperatorList = new ArrayList<String>();
            String optId = "";
            try
            {
                for (String operatorId : optCustIdList)
                {
                    optId = operatorId;
                    // 查询操作员是否有授权业务
                    authOperator = optMapper.findAuthOperator(bizType, operatorId);
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "自动派单：3操作员是否有授权业务,params[bizType:" + bizType + ",operatorId:" + operatorId
                                    + ",authOperator:" + authOperator + "]");
                    if (authOperator != null)
                    {
                        authOperatorList.add(authOperator.getOptCustId());
                    }
                }
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "method:"
                        + Thread.currentThread().getStackTrace()[1].getMethodName(),
                        TMErrorCode.TM_GET_AUTH_OPERATOR_ERROR.getCode() + ":查询授权值班员时异常,params[bizType:" + bizType
                                + ",operatorId:" + optId + "]", e);
                throw new HsException(TMErrorCode.TM_GET_AUTH_OPERATOR_ERROR.getCode(), "查询授权值班员时异常,params[bizType:"
                        + bizType + ",operatorId:" + optId + "]" + e);
            }
        }
        return authOperatorList;
    }

    /**
     * 获取工单列表
     * 
     * @param baseParam
     *            查询条件
     * @param page
     *            分页信息
     * @return 分页后的工单列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMTaskService#getTaskList(com.gy.hsxt.tm.bean.TmBaseParam,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<TmTask> getTaskList(TmBaseParam baseParam, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "分页查询工单列表,params[" + baseParam + "," + page + "]");
        // 分页数据
        PageData<TmTask> taskListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, TMErrorCode.TM_PAGE_PARAM_NULL, "分页查询工单列表：分页条件参数为空");
        HsAssert.hasText(baseParam.getEntCustId(), TMErrorCode.TM_PARAMS_NULL, "分页查询工单列表：企业客户号参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        // 设置查询日期格式
        baseParam.setQueryDate();

        try
        {
            // 执行查询
            List<TmTask> taskList = taskMapper.findTaskListPage(baseParam);
            if (taskList != null && taskList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                taskListPage = new PageData<TmTask>(page.getCount(), taskList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_QUERY_TASK_LIST_ERROR
                    .getCode()
                    + ":分页查询工单列表异常,params[" + baseParam + "," + page + "]", e);
            throw new HsException(TMErrorCode.TM_QUERY_TASK_LIST_ERROR.getCode(), "分页查询工单列表异常,params[" + baseParam
                    + "," + page + "]" + e);
        }
        return taskListPage;
    }

    /**
     * 获取工单信息
     * 
     * @param bizNo
     *            业务单号
     * @return 工单信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMTaskService#getTmTaskByBizNo(java.lang.String)
     */
    @Override
    public TmTask getTmTaskByBizNo(String bizNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "分页查询工单列表,params[bizNo:" + bizNo + "]");
        return taskMapper.findTaskInfoByBizNo(bizNo);
    }
}
