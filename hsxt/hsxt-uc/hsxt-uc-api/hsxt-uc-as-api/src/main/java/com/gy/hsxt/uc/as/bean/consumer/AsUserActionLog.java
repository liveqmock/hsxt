package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;

public class AsUserActionLog implements Serializable {

	private static final long serialVersionUID = -1452666630677350641L;

	private String logId;

    private String custId;

    private String action;

    private String remark;

    private String createDate;

    private String createdby;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
    
    
}
