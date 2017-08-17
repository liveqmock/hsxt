/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工单任务实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: TmTask
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:35:01
 * @version V3.0.0
 */
public class TmTask implements Serializable {
    private static final long serialVersionUID = -2769797952459354480L;

    /** 任务编号 **/
    private String taskId;

    /** 业务类型 **/
    private String bizType;

    /** 业务类型名称 **/
    private String bizTypeName;

    /** 业务编号 **/
    private String bizNo;

    /** 任务所属企业客户号 **/
    private String entCustId;

    /** 办理业务客户互生号 **/
    private String hsResNo;

    /** 办理业务客户名称 **/
    private String custName;

    /** 发起时间 **/
    private String createdDate;

    /** 紧急程度 **/
    private Integer priority;

    /** 执行人编号 **/
    private String exeCustId;

    /** 执行人名称 **/
    private String exeCustName;

    /** 派单时间 **/
    private String assignedTime;

    /** 挂起时间 **/
    private String hangUpTime;

    /** 完成时间 **/
    private String finishTime;

    /** 是否催办 **/
    private Integer warnFlag;

    /** 是否转派 **/
    private Integer redirectFlag;

    /** 工单状态 **/
    private Integer status;

    /** 备注 **/
    private String remark;

    /** 派单历史 **/
    private String assignHis;

    /** 工单来源 **/
    private int taskSrc;

    /** 派单模式 **/
    private int sendMod;

    public TmTask() {
        super();
    }

    public TmTask(String taskId, String bizType, String bizNo, String hsResNo, String custName, String createdDate,
            int priority, String exeCustId, String exeCustName, String assignedTime, String hangUpTime,
            String finishTime, Integer warnFlag, Integer redirectFlag, int status, String remark, String assignHis,
            int taskSrc) {
        super();
        this.taskId = taskId;
        this.bizType = bizType;
        this.bizNo = bizNo;
        this.hsResNo = hsResNo;
        this.custName = custName;
        this.createdDate = createdDate;
        this.priority = priority;
        this.exeCustId = exeCustId;
        this.exeCustName = exeCustName;
        this.assignedTime = assignedTime;
        this.hangUpTime = hangUpTime;
        this.finishTime = finishTime;
        this.warnFlag = warnFlag;
        this.redirectFlag = redirectFlag;
        this.status = status;
        this.remark = remark;
        this.assignHis = assignHis;
        this.taskSrc = taskSrc;
    }

    public int getSendMod() {
        return sendMod;
    }

    public void setSendMod(int sendMod) {
        this.sendMod = sendMod;
    }

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName;
    }

    public String getHangUpTime() {
        return hangUpTime;
    }

    public void setHangUpTime(String hangUpTime) {
        this.hangUpTime = hangUpTime;
    }

    public String getHsResNo() {
        return hsResNo;
    }

    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getWarnFlag() {
        return warnFlag;
    }

    public void setWarnFlag(Integer warnFlag) {
        this.warnFlag = warnFlag;
    }

    public Integer getRedirectFlag() {
        return redirectFlag;
    }

    public void setRedirectFlag(Integer redirectFlag) {
        this.redirectFlag = redirectFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getExeCustId() {
        return exeCustId;
    }

    public void setExeCustId(String exeCustId) {
        this.exeCustId = exeCustId;
    }

    public String getExeCustName() {
        return exeCustName;
    }

    public void setExeCustName(String exeCustName) {
        this.exeCustName = exeCustName;
    }

    public String getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(String assignedTime) {
        this.assignedTime = assignedTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAssignHis() {
        return assignHis;
    }

    public void setAssignHis(String assignHis) {
        this.assignHis = assignHis;
    }

    public int getTaskSrc() {
        return taskSrc;
    }

    public void setTaskSrc(int taskSrc) {
        this.taskSrc = taskSrc;
    }

    /**
     * @return the entCustId
     */
    public String getEntCustId() {
        return entCustId;
    }

    /**
     * @param entCustId
     *            the entCustId to set
     */
    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
