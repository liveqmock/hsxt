/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.task.IBSTaskService;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.task.mapper.TaskMapper;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.enumtype.TaskSrc;

/**
 * 工单Service实现类
 * 
 * @Package: com.gy.hsxt.bs.task.service
 * @ClassName: TaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-29 下午8:57:43
 * @version V3.0.0
 */
@Service("bsTaskService")
public class TaskService implements ITaskService, IBSTaskService {
    /** 业务服务私有配置参数 **/
    @Autowired
    private BsConfig bsConfig;

    // 注入工单mapper
    @Autowired
    TaskMapper taskMapper;

    /**
     * 工单系统：保存工单
     */
    @Resource
    private ITMTaskService tmTaskService;

    /**
     * 保存工单内部接口实现
     * 
     * @param task
     *            工单信息
     * @see com.gy.hsxt.bs.task.interfaces.ITaskService#saveTask(com.gy.hsxt.bs.task.bean.Task)
     */
    @Override
    @Transactional
    public void saveTask(Task task) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存工单,params[" + task + "]");
        HsAssert.notNull(task, BSRespCode.BS_PARAMS_NULL, "保存工单：实体参数为空");
        // 设置任务ID
        if (StringUtils.isBlank(task.getTaskId()))
        {
            task.setTaskId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
        }
        try
        {
            task.setTaskSrc(TaskSrc.BS.getCode());
            // 新增任务单
            taskMapper.insertTask(task);
        }
        catch (Exception e)
        {
            throw new HsException(BSRespCode.BS_SAVE_TASK_ERROR.getCode(), "保存工单异常,param：" + task + "\n" + e);
        }
        // 同步保存到工单系统
        try
        {
            TmTask tmTask = new TmTask();
            BeanUtils.copyProperties(task, tmTask);
            tmTaskService.saveTMTask(tmTask);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_SAVE_COMMON_ORDER_ERROR
                    .getCode()
                    + "保存工单：同步到工单系统异常，param：" + task, e);
            throw new HsException(BSRespCode.BS_SAVE_TASK_ERROR.getCode(), "保存工单：同步到工单系统异常，param：" + task + "\n" + e);
        }
    }

    /**
     * 批量保存工单
     * 
     * @Description:
     * @param beans
     * @throws HsException
     */
    @Override
    @Transactional
    public void batchSaveTask(List<Task> beans) throws HsException {
        // 记录业务日志
        BizLog.biz(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(),
                "params[" + JSON.toJSONString(beans) + "]", "批量插入工单");

        HsAssert.notNull(beans, BSRespCode.BS_PARAMS_NULL, "批量插入工单参数为空");
        // 工单列表
        List<TmTask> tms = null;
        try
        {
            // 批量插入任务单
            taskMapper.batchInsertTask(beans);
            // 生成工单数据
            tms = JSONArray.parseArray(JSON.toJSONString(beans), TmTask.class);

            // 插入工单至工单系统
            tmTaskService.saveTMTask(tms);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception ex)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_SAVE_TASK_ERROR
                    .getCode()
                    + ":批量插入工单异常,params[" + JSON.toJSONString(beans) + "]", ex);
            throw new HsException(BSRespCode.BS_SAVE_TASK_ERROR.getCode(), "批量插入工单异常,params["
                    + JSON.toJSONString(beans) + "]" + ex);
        }
    }

    /**
     * 更新任务状态
     * 
     * @param taskId
     *            任务id
     * @param taskStatus
     *            任务状态
     * @throws HsException
     * @see com.gy.hsxt.bs.task.interfaces.ITaskService#updateTaskStatus(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    @Transactional
    // 疑问，加了事物可能无法同步更新
    public void updateTaskStatus(String taskId, Integer taskStatus) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新任务状态,params[taskId:" + taskId + ",taskStatus:" + taskStatus + "]");

        HsAssert.hasText(taskId, BSRespCode.BS_PARAMS_NULL, "更新任务状态：任务编号参数为空");
        try
        {
            // 业务系统执行更新任务状态
            taskMapper.updateTaskStatus(taskId, taskStatus);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_UPDATE_TASK_STATUS_ERROR
                    .getCode()
                    + "更新任务状态异常,params[taskId:" + taskId + ",taskStatus:" + taskStatus + "]", e);
            throw new HsException(BSRespCode.BS_UPDATE_TASK_STATUS_ERROR.getCode(), "更新任务状态异常,params[taskId:" + taskId
                    + ",taskStatus:" + taskStatus + "]" + e);
        }

        try
        {
            // 同步更新工单系统状态
            tmTaskService.updateTaskStatus(taskId, taskStatus, "");
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_SYNC_UPDATE_TM_TASK_ERROR.getCode() + "同步更新工单系统任务状态异常,params[taskId:" + taskId
                            + ",taskStatus:" + taskStatus + "]", e);
            throw new HsException(BSRespCode.BS_SYNC_UPDATE_TM_TASK_ERROR.getCode(), "同步更新工单系统任务状态异常,params[taskId:"
                    + taskId + ",taskStatus:" + taskStatus + "]" + e);
        }
    }

    /**
     * 获取任务ID
     * 
     * @param bizNo
     *            业务编号
     * @param exeCustId
     *            经办人编号
     * @return 任务ID
     * @throws HsException
     * @see com.gy.hsxt.bs.task.interfaces.ITaskService#getSrcTask(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public String getSrcTask(String bizNo, String exeCustId) throws HsException {
        HsAssert.hasText(bizNo, BSRespCode.BS_PARAMS_NULL, "获取任务编号：原业务编号参数为空");
        HsAssert.hasText(exeCustId, BSRespCode.BS_PARAMS_NULL, "获取任务编号：经办人编号参数为空");
        return taskMapper.findSrcTaskId(bizNo, exeCustId);
    }

    /**
     * 更新原来业务任务状态
     * 
     * @param bizNo
     *            原业务编号
     * @param taskStatus
     *            任务状态
     * @param exeCustId
     *            经办人编号
     * @throws HsException
     * @see com.gy.hsxt.bs.api.task.IBSTaskService#updateTaskStatus(java.lang.String,
     *      java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void updateTaskStatus(String bizNo, Integer taskStatus, String exeCustId, String remark) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新原来业务任务状态,params[bizNo:" + bizNo + ",taskStatus:" + taskStatus + ",exeCustId:" + exeCustId
                        + ",remark:" + remark + "]");
        HsAssert.hasText(bizNo, BSRespCode.BS_PARAMS_NULL, "更新原来业务任务状态：原业务编号参数为空");
        HsAssert.notNull(taskStatus, BSRespCode.BS_PARAMS_NULL, "更新原来业务任务状态：任务状态参数为空");
        HsAssert.hasText(exeCustId, BSRespCode.BS_PARAMS_NULL, "更新原来业务任务状态：经办人参数为空");
        String taskId = getSrcTask(bizNo, exeCustId);
        HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "更新原来业务任务状态：任务状态不是办理中");
        if (StringUtils.isNotBlank(taskId))
        {
            try
            {
                // 业务系统执行更新任务状态
                taskMapper.updateTaskStatus(taskId, taskStatus);
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "method:"
                        + Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_UPDATE_TASK_STATUS_ERROR.getCode() + "更新任务状态异常,params[taskId:" + taskId
                                + ",taskStatus:" + taskStatus + "]", e);
                throw new HsException(BSRespCode.BS_UPDATE_TASK_STATUS_ERROR.getCode(), "更新任务状态异常,params[taskId:"
                        + taskId + ",taskStatus:" + taskStatus + "]" + e);
            }

            try
            {
                // 同步更新工单系统状态
                tmTaskService.updateTaskStatus(taskId, taskStatus, remark);
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "method:"
                        + Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_SYNC_UPDATE_TM_TASK_ERROR.getCode() + "同步更新工单系统任务状态异常,params[taskId:" + taskId
                                + ",taskStatus:" + taskStatus + "]", e);
                throw new HsException(BSRespCode.BS_SYNC_UPDATE_TM_TASK_ERROR.getCode(),
                        "同步更新工单系统任务状态异常,params[taskId:" + taskId + ",taskStatus:" + taskStatus + "]" + e);
            }
        }
    }

    @Deprecated
    @Transactional
    public void updateTaskStatus(String bizNo, Integer taskStatus, String exeCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新原来业务任务状态,params[bizNo:" + bizNo + ",taskStatus:" + taskStatus + ",exeCustId:" + exeCustId + "]");
        HsAssert.hasText(bizNo, BSRespCode.BS_PARAMS_NULL, "更新原来业务任务状态：原业务编号参数为空");
        HsAssert.notNull(taskStatus, BSRespCode.BS_PARAMS_NULL, "更新原来业务任务状态：任务状态参数为空");
        HsAssert.hasText(exeCustId, BSRespCode.BS_PARAMS_NULL, "更新原来业务任务状态：经办人参数为空");
        String taskId = getSrcTask(bizNo, exeCustId);
        HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "更新原来业务任务状态：任务状态不是办理中");
        if (StringUtils.isNotBlank(taskId))
        {
            try
            {
                // 业务系统执行更新任务状态
                taskMapper.updateTaskStatus(taskId, taskStatus);
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "method:"
                        + Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_UPDATE_TASK_STATUS_ERROR.getCode() + "更新任务状态异常,params[taskId:" + taskId
                                + ",taskStatus:" + taskStatus + "]", e);
                throw new HsException(BSRespCode.BS_UPDATE_TASK_STATUS_ERROR.getCode(), "更新任务状态异常,params[taskId:"
                        + taskId + ",taskStatus:" + taskStatus + "]" + e);
            }

            try
            {
                // 同步更新工单系统状态
                tmTaskService.updateTaskStatus(taskId, taskStatus, "");
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "method:"
                        + Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_SYNC_UPDATE_TM_TASK_ERROR.getCode() + "同步更新工单系统任务状态异常,params[taskId:" + taskId
                                + ",taskStatus:" + taskStatus + "]", e);
                throw new HsException(BSRespCode.BS_SYNC_UPDATE_TM_TASK_ERROR.getCode(),
                        "同步更新工单系统任务状态异常,params[taskId:" + taskId + ",taskStatus:" + taskStatus + "]" + e);
            }
        }
    }
}
