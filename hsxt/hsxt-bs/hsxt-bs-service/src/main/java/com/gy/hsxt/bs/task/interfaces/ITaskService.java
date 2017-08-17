/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.task.interfaces;

import java.util.List;

import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工单接口定义
 * 
 * @Package: com.gy.hsxt.bs.task.interfaces
 * @ClassName: ITaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-29 下午8:59:56
 * @version V3.0.0
 */
public interface ITaskService {

    /**
     * 保存工单
     * 
     * @param task
     *            工单信息
     * @throws HsException
     */
    public void saveTask(Task task) throws HsException;

    /**
     * 批量保存工单
     * 
     * @Description:
     * @author: likui
     * @created: 2015年12月30日 上午11:23:02
     * @param beans
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void batchSaveTask(List<Task> beans) throws HsException;

    /**
     * 更新任务状态
     * 
     * @param taskId
     *            任务id
     * @param taskStatus
     *            任务状态
     * @throws HsException
     */
    public void updateTaskStatus(String taskId, Integer taskStatus) throws HsException;

    /**
     * 获取任务ID
     * 
     * @param bizNo
     *            业务编号
     * @param exeCustId
     *            经办人编号
     * @return 任务ID
     * @throws HsException
     */
    public String getSrcTask(String bizNo, String exeCustId) throws HsException;
}
