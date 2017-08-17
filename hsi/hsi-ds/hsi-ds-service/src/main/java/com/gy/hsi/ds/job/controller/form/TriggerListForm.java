/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.controller.form;

import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase;
import com.gy.hsi.ds.common.utils.StringUtils;

/**
 * Trigger列表查询Form类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.trigger.form
 * @ClassName: TriggerListForm
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月14日 下午4:22:20
 * @version V3.0
 */
public class TriggerListForm extends RequestListBase {

	/**
	 * 自动生成的序列号
	 */
	private static final long serialVersionUID = 1733630519426357434L;

	/**
	 * trigger名称，对应业务名称
	 */
	private String triggerName;

	/**
	 * trigger组名称，对应DUBBO服务名
	 */
	private String triggerGroup;

	private String triggerState;

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

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = StringUtils.trim(triggerState);
	}

}
