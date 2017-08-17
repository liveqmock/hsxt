/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.service.impl;

import java.text.ParseException;
import java.util.TimeZone;
import java.util.UUID;

import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.JobConstant;
import com.gy.hsi.ds.common.constant.JobConstant.TaskFlag;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.DataTransfer;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.ServiceUtil;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.beans.bo.QrtzTrigger;
import com.gy.hsi.ds.job.beans.vo.QrtzTriggerVo;
import com.gy.hsi.ds.job.controller.form.TriggerListForm;
import com.gy.hsi.ds.job.controller.form.TriggerNewForm;
import com.gy.hsi.ds.job.dao.IJobStatusDao;
import com.gy.hsi.ds.job.dao.IQrtzTriggerDao;
import com.gy.hsi.ds.job.service.IQrtzTriggerMgr;

/**
 * 定时任务操作业务处理类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.trigger.service.impl
 * @ClassName: QrtzTriggerMgrImpl
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月14日 下午4:25:27
 * @version V3.0
 */
@Service
public class QrtzTriggerMgrImpl implements IQrtzTriggerMgr {

	protected static final Logger LOG = LoggerFactory
			.getLogger(QrtzTriggerMgrImpl.class);

	@Autowired
	private IQrtzTriggerDao qrtzTriggerDao;

	@Autowired
	private IJobStatusDao jobStatusDao;

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private JobDetail jobDetail;

	@Override
	public DaoPageResult<QrtzTriggerVo> getTriggerList(
			TriggerListForm triggerListForm) {
		DaoPageResult<QrtzTrigger> qrtzTrigger = qrtzTriggerDao
				.getTriggerList(triggerListForm);
		
		DaoPageResult<QrtzTriggerVo> qrtzTriggerVo = ServiceUtil.getResult(
				qrtzTrigger, new DataTransfer<QrtzTrigger, QrtzTriggerVo>() {
					@Override
					public QrtzTriggerVo transfer(QrtzTrigger input) {

						QrtzTriggerVo qrtzTriggerVo = convert(input);

						return qrtzTriggerVo;
					}
				});
		
		return qrtzTriggerVo;
	}

	/**
	 * QrtzTrigger转换成QrtzTriggerVo
	 * 
	 * @param input
	 * @return
	 */
	private QrtzTriggerVo convert(QrtzTrigger qrtzTrigger) {
		
		String prevFireTime = DateUtils.formatLongDate(qrtzTrigger.getPrevFireTime());
		String nextFireTime = DateUtils.formatLongDate(qrtzTrigger.getNextFireTime());
		String startTime = DateUtils.formatLongDate(qrtzTrigger.getStartTime());
		String endTime = DateUtils.formatLongDate(qrtzTrigger.getEndTime());
		
		String id = qrtzTrigger.getId();
		String group = qrtzTrigger.getTriggerGroup();
		String state = qrtzTrigger.getTriggerState();
		
		QrtzTriggerVo qrtzTriggerVo = new QrtzTriggerVo();
		qrtzTriggerVo.setRandomId(UUID.randomUUID().toString());
		qrtzTriggerVo.setTriggerName(id);
		qrtzTriggerVo.setTriggerGroup(group);
		qrtzTriggerVo.setPrevFireTime(prevFireTime);
		qrtzTriggerVo.setNextFireTime(nextFireTime);
		qrtzTriggerVo.setStartTime(startTime);
		qrtzTriggerVo.setEndTime(endTime);
		qrtzTriggerVo.setDesc(qrtzTrigger.getDesc());
		
		TriggerKey triggerKey = new TriggerKey(id, group);
		qrtzTriggerVo.setTriggerState(JobConstant.TRIGGER_STATE.get(state));
		
		try {
			CronTriggerImpl trigger = (CronTriggerImpl) scheduler
					.getTrigger(triggerKey);
			
			qrtzTriggerVo.setCron(trigger.getCronExpression());
		} catch (SchedulerException e) {
			LOG.error("获取定时任务信息cron表达式失败！", e);
		}
		
		return qrtzTriggerVo;
	}

	@Override
	public boolean checkCron(String cron) {
		return CronExpression.isValidExpression(cron);
	}

	@Override
	public boolean isExist(String triggerName, String triggerGroup)
			throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		
		return scheduler.checkExists(triggerKey);
	}

	@Override
	public String create(TriggerNewForm triggerNewForm) {
		String result = "创建定时任务成功";
		
		try {
			scheduler(triggerNewForm, true);
			
			jobStatusDao.updateTaskFlag(triggerNewForm.getTriggerName(),
							triggerNewForm.getTriggerGroup(),
							TaskFlag.HAS_TASK_FLAG);
		} catch (Exception e) {
			result = "创建定时任务失败";
			LOG.error("创建定时任务失败！", e);
		}
		
		return result;
	}

	/**
	 * 创建更新定时任务
	 * 
	 * @param triggerNewForm
	 * @param flag
	 *            true 新建 false 修改
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	private void scheduler(TriggerNewForm triggerNewForm, boolean flag)
			throws ParseException, SchedulerException {
		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setCronExpression(triggerNewForm.getCron());
		trigger.setTimeZone(TimeZone.getDefault()); // 设置当前的时区, added by:zhangysh
		
		TriggerKey triggerKey = new TriggerKey(triggerNewForm.getTriggerName(),
				triggerNewForm.getTriggerGroup());
		trigger.setJobName(jobDetail.getKey().getName());
		trigger.setKey(triggerKey);
		trigger.setDescription(triggerNewForm.getDesc());
		
		scheduler.addJob(jobDetail, true);
				
		if (flag) {
			scheduler.scheduleJob(trigger);
		} else {
			scheduler.rescheduleJob(triggerKey, trigger);
		}

	}

	@Override
	public String update(TriggerNewForm triggerNewForm) {
		String result = "更新定时任务成功";
		
		try {
			scheduler(triggerNewForm, false);
		} catch (Exception e) {
			result = "更新定时任务失败";
			LOG.error("更新定时任务失败！", e);
		}
		
		return result;
	}

	@Override
	public String delete(String triggerName, String triggerGroup) {
		String result = "删除定时任务成功";
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		
		try {
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			jobStatusDao.updateTaskFlag(triggerName, triggerGroup,
					TaskFlag.NOT_TASK_FLAG);
		} catch (SchedulerException e) {
			result = "删除定时任务失败";
			
			LOG.error("删除定时任务失败！", e);
		}
		
		return result;
	}

	@Override
	public String resume(String triggerName, String triggerGroup) {
		String result = "恢复定时任务成功";
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		
		try {
			scheduler.resumeTrigger(triggerKey);// 恢复触发器
		} catch (SchedulerException e) {
			result = "恢复定时任务失败";
			LOG.error("恢复定时任务失败！", e);
		}
		
		return result;
	}

	@Override
	public String pause(String triggerName, String triggerGroup) {
		String result = "暂停定时任务成功";
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		
		try {
			scheduler.pauseTrigger(triggerKey);// 停止触发器
		} catch (SchedulerException e) {
			result = "暂停定时任务失败";
			LOG.error("暂停定时任务失败！", e);
		}
		
		return result;
	}

	@Override
	public QrtzTriggerVo get(String triggerName, String triggerGroup) {
		QrtzTriggerVo vo = new QrtzTriggerVo();
		vo.setTriggerName(triggerName);
		vo.setTriggerGroup(triggerGroup);
		
		try {
			JobStatus jobStatus = jobStatusDao.getByNameAndGroup(triggerName, triggerGroup);
			
			CronTriggerImpl trigger = (CronTriggerImpl) scheduler
					.getTrigger(new TriggerKey(triggerName, triggerGroup));
			vo.setCron(trigger.getCronExpression());
			vo.setDesc(trigger.getDescription());
			vo.setVersion(jobStatus.getVersion());
		} catch (SchedulerException e) {
			LOG.error("获取定时任务信息失败！", e);
		}
		
		return vo;
	}
}
