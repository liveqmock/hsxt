/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.task;

import com.gy.hsxt.common.exception.HsException;

/**
 * 工单任务接口
 * 
 * @Package: com.gy.hsxt.bs.common.interfaces
 * @ClassName: IBSTaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-18 上午11:08:17
 * @version V3.0.0
 */
public interface IBSTaskService {

    /**
     * 更新原来业务任务状态
     * 
     * @param bizNo
     *            原业务编号
     * @param taskStatus
     *            任务状态
     * @param exeCustId
     *            经办人编号
     * @param remark
     *            备注
     * @throws HsException
     */
    public void updateTaskStatus(String bizNo, Integer taskStatus, String exeCustId) throws HsException;

    public void updateTaskStatus(String bizNo, Integer taskStatus, String exeCustId, String remark) throws HsException;

}
