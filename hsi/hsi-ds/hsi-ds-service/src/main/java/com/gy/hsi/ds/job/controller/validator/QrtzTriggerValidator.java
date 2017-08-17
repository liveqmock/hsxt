/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.controller.validator;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.hsi.ds.job.controller.form.TriggerNewForm;
import com.gy.hsi.ds.job.service.IQrtzTriggerMgr;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.FieldException;

/**
 * 
 * 任务处理验证类
 * 
 * @Package: com.gy.hsi.ds.job.web.web.trigger.validator
 * @ClassName: QrtzTriggerValidator
 * @Description: TODO
 *
 * @author: prgma15
 * @date: 2015年10月14日 下午5:41:46
 * @version V1.0
 */
@Component
public class QrtzTriggerValidator {
	@Autowired
	private IQrtzTriggerMgr qrtzTriggerMgr;

	/**
	 * 创建验证
	 * 
	 * @param triggerNewForm
	 */
	public void validatorCreate(TriggerNewForm triggerNewForm) {
		this.validatorExist(triggerNewForm.getTriggerName(),
				triggerNewForm.getTriggerGroup());
		this.validatorCron(triggerNewForm.getCron());

	}

	/**
	 * 验证任务已存在
	 * 
	 * @param triggerName
	 * @param triggerGroup
	 */
	public void validatorExist(String triggerName, String triggerGroup) {

		try {
			if (qrtzTriggerMgr.isExist(triggerName, triggerGroup)) {
				throw new FieldException("triggerNameandGroup",
						"trigger.exist", null);
			}
		} catch (SchedulerException e) {
			throw new FieldException("error", "trigger.exist.error", e);
		}

	}

	/**
	 * 验证任务不存在
	 * 
	 * @param triggerName
	 * @param triggerGroup
	 */
	public void validatorNotExist(String triggerName, String triggerGroup) {

		try {
			if (!qrtzTriggerMgr.isExist(triggerName, triggerGroup)) {
				throw new FieldException("triggerNameandGroup",
						"trigger.notexist", null);
			}
		} catch (SchedulerException e) {
			throw new FieldException("error", "trigger.validator.error", e);
		}

	}

	/**
	 * 验证cron表达式是否正确
	 * 
	 * @param cron
	 */
	public void validatorCron(String cron) {
		if (!qrtzTriggerMgr.checkCron(cron)) {
			throw new FieldException("cron", "cron.error", null);
		}
	}

	/**
	 * 更新验证
	 * 
	 * @param triggerNewForm
	 */
	public void validatorUpdate(TriggerNewForm triggerNewForm) {
		this.validatorNotExist(triggerNewForm.getTriggerName(),
				triggerNewForm.getTriggerGroup());
		this.validatorCron(triggerNewForm.getCron());
	}
}
