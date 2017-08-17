/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.base;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 查询实体的基类
 *
 * @Package :com.gy.hsxt.bs.bean.base
 * @ClassName : Query
 * @Description : 查询实体的基类
 * @Author : chenm
 * @Date : 2015/12/10 11:52
 * @Version V3.0.0.0
 */
public abstract class Query implements Serializable {

    private static final long serialVersionUID = -3231694462784651284L;

    /**
     * 开始日期 格式：yyyy-MM-dd
     */
    private String startDate;

    /**
     * 结束日期 格式：yyyy-MM-dd
     */
    private String endDate;

    /**
     * 操作人客户号
     */
    private String optCustId;

    /**
     * 工单类型
     *
     * @see com.gy.hsxt.common.constant.TaskType
     */
    private String taskType;

    /**
     * 工单状态
     *
     * @see com.gy.hsxt.common.constant.TaskStatus
     */
    private Integer taskStatus;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 校验查询的日期格式
     *
     * @return this
     * @throws HsException
     */
    public Query checkDateFormat() throws HsException {
        //不为空的情况下，进行校验
        if (StringUtils.isNotBlank(this.getStartDate())) {
            //校验日期格式
            HsAssert.notNull(DateUtil.StringToDate(this.getStartDate()), RespCode.PARAM_ERROR, "开始日期[startDate]格式不对，正确格式为:yyyy-MM-dd");
        }
        if (StringUtils.isNotBlank(this.getEndDate())) {
            //校验日期格式
            HsAssert.notNull(DateUtil.StringToDate(this.getEndDate()), RespCode.PARAM_ERROR, "结束日期[endDate]格式不对，正确格式为:yyyy-MM-dd");
            //结束日期加一天 (包头不包尾)
            this.setEndDate(DateUtil.addDaysStr(DateUtil.StringToDate(this.getEndDate()), 1));
        }
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
