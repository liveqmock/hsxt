/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.controller.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestFormBase;
import com.gy.hsi.ds.common.utils.StringUtils;

/**
 * 业务创建Form
 * 
 * @Package: com.gy.hsi.ds.job.web.service.business.form
 * @ClassName: BusinessNewForm
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月12日 下午12:17:12
 * @version V3.0
 */
public class BusinessNewForm extends RequestFormBase {

	/**
	 * 自动生成的序列
	 */
	private static final long serialVersionUID = -1876073448936951977L;

	/**
	 * 业务名称
	 */
	@NotNull(message = "serviceName.empty")
	@NotEmpty(message = "serviceName.empty")
	private String serviceName;

	public final static String SERVICENAME = "serviceName";

	public final static String BUSINESS = "business";

	/**
	 * 业务组
	 */
	@NotNull(message = "serviceGroup.empty")
	@NotEmpty(message = "serviceGroup.empty")
	private String serviceGroup;

	/**
	 * 业务功能描述
	 */
	@NotNull(message = "desc.empty")
	@NotEmpty(message = "desc.empty")
	private String desc;

	/**
	 * dubbo服务版本
	 */
	@NotNull(message = "version.empty")
	@NotEmpty(message = "version.empty")
	private String version;

	/**
	 * 业务参数
	 */
	private String servicePara = "";

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = StringUtils.trim(desc);
	}

	public String getServicePara() {
		return servicePara;
	}

	public void setServicePara(String servicePara) {
		this.servicePara = StringUtils.trim(servicePara);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = StringUtils.trim(version);
	}

}
