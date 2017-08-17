/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.beans.vo;

/**
 * 业务执行详情VO类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.detail.vo
 * @ClassName: ExecuteDetailVo
 * @Description: TODO
 *
 * @author: prgma15
 * @date: 2015年10月20日 上午11:00:17
 * @version V1.0
 */
public class ExecuteDetailVo {

	/**
	 * 业务名称
	 */
	private String serviceName;

	/**
	 * 业务组
	 */
	private String serviceGroup;

	/**
	 * 状态
	 */
	private String serviceState;

	/**
	 * 上报状态对应的描述
	 */
	private String desc;

	/**
	 * 执行参数
	 */
	private String executeParam;

	/**
	 * 状态上报时间
	 */
	private String reportDate;

	/**
	 * 执行耗时时间
	 */
	private String timeEclipsed;

	/**
	 * 执行id
	 */
	private String executeId;

	/**
	 * 执行方式
	 */
	private String executeMethod;

	/**
	 * 执行方式
	 */
	private boolean isFullDesc;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public String getServiceState() {
		return serviceState;
	}

	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getExecuteParam() {
		return executeParam;
	}

	public void setExecuteParam(String executeParam) {
		this.executeParam = executeParam;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getExecuteId() {
		return executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public String getExecuteMethod() {
		return executeMethod;
	}

	public void setExecuteMethod(String executeMethod) {
		this.executeMethod = executeMethod;
	}

	public boolean isFullDesc() {
		return isFullDesc;
	}

	public void setFullDesc(boolean isFullDesc) {
		this.isFullDesc = isFullDesc;
	}

	public String getTimeEclipsed() {
		return timeEclipsed;
	}

	public void setTimeEclipsed(String timeEclipsed) {
		this.timeEclipsed = timeEclipsed;
	}

}
