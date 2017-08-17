/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.interfaces;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.bean.TmTask;

/**
 * 任务工单内部接口
 * 
 * @Package: com.gy.hsxt.tm.interfaces
 * @ClassName: ITaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-10 下午6:01:21
 * @version V3.0.0
 */
public interface ITaskService {

    /**
     * 更新任务状态
     * 
     * @param taskId
     *            任务ID
     * @param status
     *            任务状态
     * @param remark
     *            备注
     * @throws HsException
     */
    public void updateTaskStatus(String taskId, Integer status, String remark) throws HsException;

    /**
     * 查询转派工单列表
     * 
     * @param page
     *            分页信息
     * @return 转派工单列表
     */
    public PageData<TmTask> getTurnTaskListPage(Page page) throws HsException;

    /**
     * 查询催办紧急工单
     * 
     * @return 工单列表
     */
    public List<TmTask> getUrgencyTask() throws HsException;

    /**
     * 更新任务紧急状态
     * 
     * @param taskId
     *            任务id
     * @return 更新成功记录数
     */
    public int updateTaskPriorityStat(String taskId) throws HsException;

    /**
     * 获取当班值班员
     * 
     * @param entCustId
     *            企业客户号
     * @return 当班值班员列表
     */
    public List<String> getOnLineOperatorList(String entCustId) throws HsException;

    /**
     * 获取有权限的值班员列表
     * 
     * @param bizType
     *            业务类型
     * @param operatorList
     *            值班员列表
     */
    public List<String> getAuthOperatorList(String bizType, String entCustId) throws HsException;

    /**
     * 获取值班员信息
     * 
     * @param optCustIds
     *            值班员编号
     * @return Random random = new Random();
     */
    public Operator getScheduleOpt(List<String> optCustIds) throws HsException;

}
