/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.task;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.BaseParam;

/**
 * 任务单查询条件包装类，包装所有可能用到的条件，各接口方法使用时按需传参
 *
 * @Package: com.gy.hsxt.bs.bean.task
 * @ClassName: TaskQueryParam
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-9-29 下午4:38:43
 * @version V1.0
 */
public class TaskQueryParam extends BaseParam {

    private static final long serialVersionUID = 8419809998328190773L;

    /** 任务状态 **/
    private Integer taskStat;

    /** 任务类型 **/
    private Integer taskType;

    /** 受理人 **/
    private String acceptor;

    /** 执行状态 **/
    private Integer executeStatus;

    /** 任务描述 **/
    private String taskDesc;

    public Integer getTaskStat() {
        return taskStat;
    }

    public void setTaskStat(Integer taskStat) {
        this.taskStat = taskStat;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public Integer getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(Integer executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
