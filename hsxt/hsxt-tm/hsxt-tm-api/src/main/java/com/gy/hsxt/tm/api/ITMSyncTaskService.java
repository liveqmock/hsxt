/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.api;

import com.gy.hsxt.common.exception.HsException;

/**
 * 同步源任务接口：由工单系统定义，其它系统实现
 * 
 * @Package: com.gy.hsxt.tm.api
 * @ClassName: ITMSyncTaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-19 上午10:26:51
 * @version V3.0.0
 */
public interface ITMSyncTaskService {

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
     */
    public int updateSrcTaskExecutor(String exeCustId, String exeCustName, String assignHis, String taskId)
            throws HsException;

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
     *            业务类型
     * @param taskId
     *            任务编号
     * @throws HsException
     */
    public int updateSrcTaskExecutor(String exeCustId, String exeCustName, String assignHis, String bizType,
            String taskId) throws HsException;
}
