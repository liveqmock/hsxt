/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.mapper.TaskMapper;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.tm.api.ITMSyncTaskService;

/**
 * 工单系统同步接口实现类
 * 
 * @Package: com.gy.hsxt.bs.task.service
 * @ClassName: SyncTaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-20 上午11:18:14
 * @version V3.0.0
 */
@Service("syncTaskService")
public class SyncTaskService implements ITMSyncTaskService {
    // 注入工单mapper
    @Autowired
    TaskMapper taskMapper;

    /**
     * 更新源任务执行人
     * 
     * @param exeCustId
     *            经办人编号
     * @param exeCustName
     *            经办人名称
     * @param assignHis
     *            派单历史
     * @param taskId
     *            任务编号
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMSyncTaskService#updateSrcTaskExecutor(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int updateSrcTaskExecutor(String exeCustId, String exeCustName, String assignHis, String taskId)
            throws HsException {
        // 记录业务日志
        BizLog.biz(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(),
                "params[exeCustId:" + exeCustId + ",exeCustName:" + exeCustName + ",assignHis:" + assignHis
                        + ",taskId:" + taskId + "]", "更新源任务执行人");
        int returnNum = 0;
        // 校验输入参数
        if (StringUtils.isNotBlank(exeCustId) && StringUtils.isNotBlank(exeCustName)
                && StringUtils.isNotBlank(assignHis) && StringUtils.isNotBlank(taskId))
        {
            try
            {
                Task task = taskMapper.findTaskById(taskId);
                if (task != null)
                {
                    // 更新任务执行人
                    returnNum = taskMapper.updateTaskExecutor(exeCustId, exeCustName, assignHis, TaskStatus.DEALLING
                            .getCode(), taskId);
                }
            }
            catch (Exception e)
            {
                SystemLog
                        .error(this.getClass().getName(), "method:"
                                + Thread.currentThread().getStackTrace()[1].getMethodName(),
                                BSRespCode.BS_UPDATE_TASK_STATUS_ERROR.getCode() + ":更新任务状态异常,params[exeCustId:"
                                        + exeCustId + ",exeCustName:" + exeCustName + ",assignHis:" + assignHis
                                        + ",taskId:" + taskId + "]", e);
            }
        }
        else
        {
            // 更新任务状态为已挂启
            returnNum = taskMapper.updateTaskExecutor(exeCustId, exeCustName, assignHis, TaskStatus.HANG_UP.getCode(),
                    taskId);
        }
        return returnNum;
    }

    /**
     * 更新任务状态：根据业务类型，BS通过单独任务表关联固不需要实现此方法
     * 
     * @param exeCustId
     * @param exeCustName
     * @param assignHis
     * @param bizType
     * @param taskId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMSyncTaskService#updateSrcTaskExecutor(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public int updateSrcTaskExecutor(String exeCustId, String exeCustName, String assignHis, String bizType,
            String taskId) throws HsException {
        // TODO Auto-generated method stub
        return 0;
    }
}
