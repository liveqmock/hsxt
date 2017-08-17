/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.controller.form;

import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestFormBase;
import com.gy.hsi.ds.common.utils.StringUtils;

/**
 * 新增操作Form类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.trigger.form
 * @ClassName: TriggerNewForm
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月14日 下午4:24:45
 * @version V3.0
 */
public class TriggerNewForm extends RequestFormBase {

	/**
	 * 自动生成的序列化标识
	 */
	private static final long serialVersionUID = -2171916762981808304L;

	/**
	 * trigger名称，对应业务名称
	 */
	private String triggerName;

	/**
	 * trigger组名称，对应DUBBO服务名
	 */
	private String triggerGroup;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 任务执行cron表达式
	 */
	private String cron;

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = StringUtils.trim(triggerName);
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = StringUtils.trim(triggerGroup);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = StringUtils.trim(desc);
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = StringUtils.trim(cron);
	}

	@Override
	public String toString() {
		return "TriggerNewForm [triggerName=" + triggerName + ", triggerGroup="
				+ triggerGroup + ", desc=" + desc + ", cron=" + cron + "]";
	}

}
