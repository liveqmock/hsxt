/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.api;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.BaseBean;
import com.gy.hsxt.tm.bean.TmBaseParam;
import com.gy.hsxt.tm.bean.TmTask;

/**
 * 任务工单接口
 * 
 * @Package: com.gy.hsxt.tm.api
 * @ClassName: ITMTaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:47:59
 * @version V3.0.0
 */
public interface ITMTaskService {

    /**
     * 保存工单任务
     * 
     * @param task
     *            工单信息
     * @throws HsException
     */
    public void saveTMTask(TmTask task) throws HsException;

    /**
     * 保存批量工单任务
     * 
     * @param task
     *            工单信息列表
     * @throws HsException
     */
    public void saveTMTask(List<TmTask> task) throws HsException;

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
     */
    public void updateTaskStatus(String taskId, Integer taskStatus, String remark) throws HsException;

    /**
     * 更新催办状态
     * 
     * @param taskId
     *            任务编号
     * @param warnFlag
     *            true是，false否
     * @throws HsException
     */
    public void updateWarnFlag(String taskId, boolean warnFlag) throws HsException;

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
     */
    public void sendOrder(Integer pattern, List<String> taskIds, List<String> optCustIds, BaseBean baseBean)
            throws HsException;

    /**
     * 获取工单列表
     * 
     * @param baseParam
     *            查询条件
     * @param page
     *            分页信息
     * @return 分页后的工单列表
     * @throws HsException
     */
    public PageData<TmTask> getTaskList(TmBaseParam baseParam, Page page) throws HsException;

    /**
     * 获取工单信息
     * 
     * @param bizNo
     *            业务单号
     * @return 工单信息
     * @throws HsException
     */
    public TmTask getTmTaskByBizNo(String bizNo) throws HsException;
}
