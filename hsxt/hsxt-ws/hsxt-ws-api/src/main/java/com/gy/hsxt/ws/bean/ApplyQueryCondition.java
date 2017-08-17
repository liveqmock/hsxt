/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 积分福利申请记录查询条件
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ApplyQueryCondition
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-3-29 上午11:10:27
 * @version V1.0
 */
public class ApplyQueryCondition implements Serializable {
	private static final long serialVersionUID = 7720391443589774244L;
	/** 申请人客户号 **/
	private String custId;
	/** 福利类型 0 意外伤害 1 免费医疗 2 他人身故 **/
	private Integer welfareType;
	/** 状态 0 受理中 1 已受理 2 驳回 选传 **/
	private Integer approvalStatus;
	/** 开始查询时间(申请时间) **/
	private String startTime;
	/** 结束查询时间(申请时间) **/
	private String endTime;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Integer getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
