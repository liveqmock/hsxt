package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class ExecuteLog implements Serializable {
	public static final String EXECUTE_SERVICE = "WelfareBatchService";
	/** 执行日期 格式20160220 */
	private String executeDate;

	/** 执行服务名称 */
	private String executeService;

	/** 执行状态 0失败、1成功 */
	private Integer status;

	/** 执行失败原因 */
	private String errorDesc;

	/** 创建时间 */
	private Timestamp createDate;

	/** 更新时间 */
	private Timestamp updatedDate;

	private static final long serialVersionUID = 1L;

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate == null ? null : executeDate.trim();
	}

	public String getExecuteService() {
		return executeService;
	}

	public void setExecuteService(String executeService) {
		this.executeService = executeService == null ? null : executeService.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc == null ? null : errorDesc.trim();
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static ExecuteLog bulidExecuteLog() {
		ExecuteLog log = new ExecuteLog();
		log.setExecuteService(EXECUTE_SERVICE);
		return log;
	}
}