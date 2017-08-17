/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.service.impl;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 通用定时任务处理类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.tasks
 * @ClassName: CommonJobService
 *
 * @author: yangyp
 * @date: 2015年10月9日 下午5:29:25
 * @version V3.0
 */
@PersistJobDataAfterExecution
// DisallowConcurrentExecution // 不允许并发执行
public class CommonJobService extends QuartzJobBean {

	private static final Logger logger = Logger
			.getLogger(CommonJobService.class);

	@Autowired
	private BusExecuteLogicServcie busExecuteLogicServcie;

	/**
	 * quartz.Job调用此方法执行定时逻辑
	 * 
	 * @param context
	 * @throws JobExecutionException
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void executeInternal(final JobExecutionContext context)
			throws JobExecutionException {
		doExecuteInternal(context);
	}

	/**
	 * 执行具体的执行逻辑
	 * 
	 * @param context
	 * @throws JobExecutionException
	 */
	private void doExecuteInternal(JobExecutionContext context) {

		// 递归执行业务，如果有后置任务，则所有的后置任务执行完成为止
		try {
			Trigger trigger = context.getTrigger();
			String name = trigger.getKey().getName();
			String group = trigger.getKey().getGroup();

			busExecuteLogicServcie.recursiveServiceByQuartz(name, group);
		} catch (Exception e) {
			logger.warn("", e);
		}
	}
}