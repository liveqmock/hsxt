/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.controller.form;

import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.common.utils.StringUtils;
import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase;

/**
 * 
 * 业务执行详情列表Form类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.detail.form
 * @ClassName: DetailListForm
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月20日 上午10:58:11
 * @version V3.0
 */
public class DetailListForm extends RequestListBase {

	/**
	 * 自动生成的序列号
	 */
	private static final long serialVersionUID = 3555355503012844121L;
	/**
	 * 业务名称
	 */
	private String serviceName;

	/**
	 * 业务组
	 */
	private String serviceGroup;

	/**
	 * 执行状态
	 */
	private String executeState;

	/**
	 * 查询开始时间
	 */
	private String startDate;

	/**
	 * 查询结束时间
	 */
	private String endDate;

	/**
	 * 查询最大记录数
	 */
	private String maxCounts;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = StringUtils.trim(serviceName);
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = StringUtils.trim(serviceGroup);
	}

	public String getExecuteState() {
		return executeState;
	}

	public void setExecuteState(String executeState) {
		this.executeState = StringUtils.trim(executeState);
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {

		if (StringUtils.isEmpty(startDate)) {
			this.startDate = DateUtils.getNowDate();
		} else {
			this.startDate = StringUtils.trim(startDate);
		}
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {

		if (StringUtils.isEmpty(endDate)) {
			this.endDate = DateUtils.getNowDate();
		} else {
			this.endDate = StringUtils.trim(endDate);
		}
	}

	public String getMaxCounts() {
		return maxCounts;
	}

	public void setMaxCounts(String maxCounts) {
		this.maxCounts = StringUtils.trim(maxCounts);
	}

	@Override
	public String toString() {
		return "DetailListForm [serviceName=" + serviceName + ", serviceGroup="
				+ serviceGroup + ", executeState=" + executeState
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
