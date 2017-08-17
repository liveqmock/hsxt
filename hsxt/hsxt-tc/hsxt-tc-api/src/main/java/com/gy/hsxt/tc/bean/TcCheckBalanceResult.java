/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.bean;

import java.io.Serializable;
import java.util.Date;

import com.gy.hsxt.common.utils.DateUtil;

/**
 * 调账结果
 * @author lixuan
 *
 */
public class TcCheckBalanceResult implements Serializable{
	private static final long serialVersionUID = -391354251906338663L;
	/** 调账审批流水号 */
	private String id;
	/** 调账申请流水号 */
    private String checkBalanceId;
    /** 备注*/
    private String remark;
    /** 审批结果 */
    private Integer checkResult;
    /** 审批时间*/
    private Date checkDate;
    /** 审批人*/
    private String checker;
    /** 状态 备用*/
    private Integer status;
    /** 审批类型，1 初审，2 复核 */
    private Integer checkType;
    /**
     * 审批时间的日期
     */
    private String checkDateString;
    
    /**
     * 获取审批时间的日期
     * @return
     */
    public String getCheckDateString(){
    	checkDateString = DateUtil.DateToString(checkDate);
    	return checkDateString;
    }
	/**
	 * @return the 审批类型，1初审，2复核
	 */
	public Integer getCheckType() {
		return checkType;
	}
	/**
	 * @param 审批类型，1初审，2复核 the checkType to set
	 */
	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}
	/**
	 * @return the 调账审批流水号
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param 调账审批流水号 the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the 调账申请流水号
	 */
	public String getCheckBalanceId() {
		return checkBalanceId;
	}
	/**
	 * @param 调账申请流水号 the checkBalanceId to set
	 */
	public void setCheckBalanceId(String checkBalanceId) {
		this.checkBalanceId = checkBalanceId;
	}
	/**
	 * @return the 备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param 备注 the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the 审批结果 1 通过，0 驳回
	 */
	public Integer getCheckResult() {
		return checkResult;
	}
	/**
	 * @param 审批结果 the checkResult to set
	 */
	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}
	/**
	 * @return the 审批时间
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * @param 审批时间 the checkDate to set
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	/**
	 * @return the 审批人
	 */
	public String getChecker() {
		return checker;
	}
	/**
	 * @param 审批人 the checker to set
	 */
	public void setChecker(String checker) {
		this.checker = checker;
	}
	/**
	 * @return the 状态备用
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param 状态备用 the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}