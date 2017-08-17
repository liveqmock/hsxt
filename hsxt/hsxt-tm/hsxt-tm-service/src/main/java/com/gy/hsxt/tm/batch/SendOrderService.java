/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tm.batch;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMSyncTaskService;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.common.TaskBeanUtil;
import com.gy.hsxt.tm.enumtype.TaskSrc;
import com.gy.hsxt.tm.interfaces.ISendOrderService;
import com.gy.hsxt.tm.interfaces.ITaskService;
import com.gy.hsxt.tm.mapper.OperatorMapper;
import com.gy.hsxt.tm.mapper.TaskMapper;
import com.gy.hsxt.tm.mapper.WorkTypeMapper;

/**
 * 自动派单服务实现类
 * 
 * @Package: com.gy.hsxt.tm.service
 * @ClassName: SendOrderService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-5 上午9:33:07
 * @version V3.0.0
 */
@Service
public class SendOrderService implements ISendOrderService {

    @Autowired
    ITaskService taskService;

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
     * 调度监听器
     */
    @Autowired
    private IDSBatchServiceListener batchServiceListener;

    @Override
    @Transactional
    public boolean excuteBatch(String executeId, Map<String, String> arg0) {
        // SystemLog.debug(this.getClass().getName(),
        // Thread.currentThread().getStackTrace()[1].getMethodName(),
        // "工单系统：定时扫描工单,params[executeId:" + executeId + ",arg0:" + arg0 + "]");
        // 同步更新成功记录数
        int returnNum = 0;
        // 设置执行状态
        batchServiceListener.reportStatus(executeId, 2, "工单系统：开始扫描工单");
        List<String> operatorCustIdList = null;
        // 值班员对象
        Operator executor = null;
        List<TmTask> taskList = taskMapper.findUnSendTaskList();
        // BizLog.biz(this.getClass().getName(),
        // Thread.currentThread().getStackTrace()[1].getMethodName(), "params",
        // "自动派单：1扫描到未分配的单," + taskList);
        if (taskList != null && taskList.size() > 0)
        {
            for (TmTask tmTask : taskList)
            {
                try
                {
                    // 获取目标实现类
                    syncService = TaskBeanUtil.getTargetBean(tmTask.getTaskSrc());
                    // 获取授权值班员ID
                    operatorCustIdList = taskService.getAuthOperatorList(tmTask.getBizType(), tmTask.getEntCustId());
                    // BizLog.biz(this.getClass().getName(),
                    // Thread.currentThread().getStackTrace()[1].getMethodName(),
                    // "params", "自动派单：4授权值班员ID," + operatorCustIdList);
                    // 还未分配值班员并且有可受理此业务的值班员
                    if (operatorCustIdList != null && operatorCustIdList.size() > 0)
                    {
                        // 还未分配经办人
                        if (StringUtils.isBlank(tmTask.getExeCustId()))
                        {
                            // 分配执行人
                            executor = taskService.getScheduleOpt(operatorCustIdList);
                            // BizLog.biz(this.getClass().getName(),
                            // Thread.currentThread().getStackTrace()[1]
                            // .getMethodName(), "params", "自动派单：5随机分派到," +
                            // executor);

                            // 更新工单执行人
                            taskMapper.updateTaskExecutorAuto(executor.getOptCustId(), executor.getOperatorName(),
                                    tmTask.getTaskId());

                            if (tmTask.getTaskSrc() == TaskSrc.WS.getCode())
                            {
                                // 同步源任务执行人
                                returnNum = syncService.updateSrcTaskExecutor(executor.getOptCustId(), executor
                                        .getOperatorName(), executor.getOperatorName(), tmTask.getBizType(), tmTask
                                        .getTaskId());
                            }
                            else
                            {
                                // 同步源任务执行人
                                returnNum = syncService.updateSrcTaskExecutor(executor.getOptCustId(), executor
                                        .getOperatorName(), executor.getOperatorName(), tmTask.getTaskId());
                            }

                            // 同步更新失败
                            if (returnNum <= 0)
                            {
                                // 更新无法办理的工单为停止状态
                                taskMapper.updateTaskStatus(tmTask.getTaskId(), TaskStatus.STOPPED.getCode(), "业务单不存在");
                            }
                        }
                        else
                        {
                            // 经办人列表中已不包含已分配的经办人，即表示已分配经办人不具有办理此业务权限或其它原因（缺岗调岗等）
                            if (StringUtils.isNotBlank(tmTask.getExeCustId())
                                    && operatorCustIdList.indexOf(tmTask.getExeCustId()) == -1)
                            {
                                // 更新经办人为空
                                taskMapper.updateTaskExecutorNull(tmTask.getExeCustName(), tmTask.getTaskId());
                            }
                        }
                    }
                    else
                    {
                        // 更新经办人为空并挂起工单
                        taskMapper.updateTaskExecutorNull(tmTask.getExeCustName(), tmTask.getTaskId());
                    }
                }
                catch (HsException e)
                {
                    SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                            .getMethodName(), "自动派单：同步更新源业务异常，taskId=" + tmTask.getTaskId() + " ErrorCode:"
                            + e.getErrorCode() + ":" + e.getMessage(), e);
                }
                catch (Exception e)
                {
                    SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                            .getMethodName(), "自动派单：同步更新源业务异常，taskId=" + tmTask.getTaskId() + "\n" + e.getMessage(), e);
                }
            }
        }
        // 返回调度状态
        batchServiceListener.reportStatus(executeId, 0, "工单系统：执行派单成功");
        return true;
    }
}
