/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.controller.form;

import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase;
import com.gy.hsi.ds.common.utils.StringUtils;

/**
 * 
 * 列表查询Form类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.business.form
 * @ClassName: BusinessListForm
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月13日 下午5:34:00
 * @version V3.0
 */
public class BusinessListForm extends RequestListBase {

	/**
	 * 自动生成的序列号
	 */
	private static final long serialVersionUID = 4097714037911305661L;

	/**
	 * 业务名称
	 */
	private String serviceName;

	/**
	 * 业务组
	 */
	private String serviceGroup;

	/**
	 * 业务状态
	 */
	private String serviceStatus;

	/**
	 * 任务存在标识
	 */
	private String hasTaskFlag;

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

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = StringUtils.trim(serviceStatus);
	}

	public String getHasTaskFlag() {
		return hasTaskFlag;
	}

	public void setHasTaskFlag(String hasTaskFlag) {
		this.hasTaskFlag = StringUtils.trim(hasTaskFlag);
	}

}
