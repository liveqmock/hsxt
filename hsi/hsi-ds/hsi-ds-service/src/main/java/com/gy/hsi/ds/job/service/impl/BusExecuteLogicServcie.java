/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import com.gy.hsi.common.spring.transaction.TransactionHandler;
import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.common.constant.ConfigConstant;
import com.gy.hsi.ds.common.constant.JobConstant;
import com.gy.hsi.ds.common.constant.JobConstant.ServiceState;
import com.gy.hsi.ds.common.constant.JobConstant.StrValue;
import com.gy.hsi.ds.common.constant.JobConstant.TempArgsKey;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.common.utils.DubboSupport;
import com.gy.hsi.ds.common.utils.StringUtils;
import com.gy.hsi.ds.common.utils.ThreadHelper;
import com.gy.hsi.ds.common.utils.ValuesUtils;
import com.gy.hsi.ds.job.beans.DubboComsumerConfig;
import com.gy.hsi.ds.job.beans.bo.FrontPostJob;
import com.gy.hsi.ds.job.beans.bo.JobExecuteDetails;
import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.beans.bo.JobTempArgs;
import com.gy.hsi.ds.job.dao.IFrontPostJobDao;
import com.gy.hsi.ds.job.dao.IJobExecuteDetailsDao;
import com.gy.hsi.ds.job.dao.IJobStatusDao;
import com.gy.hsi.ds.job.dao.IJobTempArgsDao;
import com.gy.hsi.ds.job.service.IBusExecuteLogicServcie;
import com.gy.hsi.ds.job.service.IExecutionTempArgsCacheService;

/**
 * 通用业务逻辑处理类
 */
@Service
@Scope("singleton")
public class BusExecuteLogicServcie implements IBusExecuteLogicServcie,
		ApplicationContextAware, java.io.Serializable {

	private static final long serialVersionUID = 2569848668894654239L;

	private static Logger logger = LoggerFactory
			.getLogger(BusExecuteLogicServcie.class);
	
	@Autowired
	private DataSourceTransactionManager transactionMgr;

	@Autowired
	private IFrontPostJobDao frontPostJobDao;

	@Autowired
	private IJobStatusDao jobStatusDao;

	@Autowired
	private IJobExecuteDetailsDao jobExecuteDetailsDao;
	
	@Autowired
	private IJobTempArgsDao jobTempArgsDao;

	@Autowired
	private DubboComsumerConfig dubboConsumerConfig;

	@Autowired
	private IExecutionTempArgsCacheService tempArgsCache;

	private ApplicationContext ctx;

	/**
	 * 递归执行业务，如果有后置任务，则所有的后置任务执行完成为止
	 * 
	 * @param name
	 * @param group
	 */
	public void recursiveServiceByQuartz(String name, String group) {
		
		// 将临时传递的key-value格式参数转换为map
		Map<String, String> tempArgsMap = new HashMap<String, String>(1);
		tempArgsMap.put(TempArgsKey.KEY_DS_BATCH_DATE,
				DateUtils.getyyyyMMddNowDate());

		this.doRecursiveService(name, group, null, tempArgsMap);
	}
	
	/**
	 * 递归执行业务 (由其他子系统触发, 目前只有支付网关用)
	 * 
	 * @param name
	 * @param group
	 * @param externalArgs
	 */
	@Override
	public void recursiveServiceByExternal(String name, String group,
			Map<String, String> externalArgs) {
		// 将临时传递的key-value格式参数转换为map
		Map<String, String> tempArgsMap = new HashMap<String, String>();

		if ((null != externalArgs) && (0 < externalArgs.size())) {
			tempArgsMap.putAll(externalArgs);
		}

		// 进行递归调用, 执行任务
		this.doRecursiveService(name, group, null, tempArgsMap);
	}

	/**
	 * 手工执行
	 * 
	 * @param name
	 * @param group
	 * @param tempArgs
	 */
	public void recursiveServiceByManual(String name, String group,
			String tempArgs) {
		// 将临时传递的key-value格式参数转换为map
		Map<String, String> tempArgsMap = ValuesUtils.getParmMap(tempArgs);
		tempArgsMap.put(TempArgsKey.KEY_IS_MANUAL, StrValue.TRUE);
		
		// 进行递归调用, 执行任务
		this.doRecursiveService(name, group, null, tempArgsMap);
	}

	/**
	 * 静默形式执行
	 * 
	 * @param name
	 * @param group
	 * @param newExecuteId
	 */
	public void recursiveServiceByFront(String name, String group,
			String newExecuteId) {
		this.doRecursiveService(name, group, newExecuteId, null);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx = applicationContext;
	}

	/**
	 * 递归执行业务，如果有后置任务，则所有的后置任务执行完成为止
	 * 
	 * @param name
	 *            业务名称
	 * @param group
	 *            业务组
	 * @param newExecuteId
	 *            新的执行id
	 * @param tempArgs
	 *            临时参数
	 */
	private void doRecursiveService(String name, String group,
			String newExecuteId, Map<String, String> tempArgs) {

		logger.info(StringUtils.joinString("doRecursiveService --> 任务名：", name,
				", 任务组：", group));

		String executeId = "";

		{
			// 标志是手工还是自动
			String prefix = tempArgsCache.parseExecuteIdPrefix(tempArgs);

			// 执行id
			executeId = StringUtils.isEmpty(newExecuteId) ? (prefix + UUID
					.randomUUID().toString()) : newExecuteId;
		}

		// 是否由前置触发, 安静模式, 校验时发生异常不进行记录
		boolean isQuiet = StringUtils.isEmpty(newExecuteId) ? false : true;

		// 数据库记录一条执行详情记录
		this.createJobDetailsRecordInDB(executeId, name, group, tempArgs);

		// 1- 获取业务状态
		JobStatus jobStatus = queryJobStatus(name, group, executeId, isQuiet);

		if (null == jobStatus) {
			logger.info("--> jobStatus is null: 任务名：" + name + ", 任务组：" + group);

			return;
		}

		// 2- 校验前置业务是否成功
		if (checkFrontBussiness(executeId, name, group, isQuiet)) {
			// 3- 获取dubbo服务
			IDSBatchService batchService = getDSBatchService(name, group,
					jobStatus.getVersion(), executeId);

			// 重置为'未上报'状态, 解决多个前置同时并发各触发的同一个后置业务问题, 即：并发触发了多次
			boolean isPermitted = resetJobStateInTransaction(executeId, name, group);

			if ((null != batchService) && isPermitted) {
				try {
					// 组装执行时需要传递的参数
					Map<String, String> param = assembleExecuteParam(executeId,
							tempArgs, jobStatus.getServicePara());

					// 4- 业务处理逻辑执行(调用远程dubbo服务, 开始执行), 采用dubbo异步方式调用, 均返回false
					batchService.excuteBatch(executeId, param);
				} catch (Exception e) {
					logger.error("调用dubbo服务发生异常：", e);

					this.doReportFailedStatus(executeId, "调用dubbo服务发生异常：", e);
				}
			} else {
				logger.info(StringUtils.joinString(
						"间隔时间未到或者获取dubbo服务失败, 不能拉起业务, 任务名：", name, ", 任务组：",
						group));
			}
		}
	}
	
	/**
	 * 组装执行时传递的参数
	 * 
	 * @param executeId
	 * @param tempArgs
	 * @param serviceParaInDB
	 * @return
	 */
	private Map<String, String> assembleExecuteParam(String executeId,
			Map<String, String> tempArgs, String serviceParaInDB) {

		// 组装执行时需要传递的参数
		Map<String, String> param = ValuesUtils.getParmMap(serviceParaInDB);

		if ((null != tempArgs) && (0 < tempArgs.size())) {
			param.putAll(tempArgs);
		}

		JobTempArgs tmpArgs = tempArgsCache.getTempArgs(executeId);

		if (null != tmpArgs) {
			param.putAll(tmpArgs.getTempArgsMap());
		}

		return param;
	}
	
	/**
	 * 校验前置业务是否成功
	 * 
	 * @param executeId
	 * @param name
	 * @param group
	 * @param isQuiet
	 * @return
	 */
	private boolean checkFrontBussiness(String executeId, String name,
			String group, boolean isQuiet) {

		// 如果不检查前置, 直接返回true
		if (tempArgsCache.isIgnoreFront(executeId)) {
			return true;
		}

		int noneDoneState = DSTaskStatus.NON_DONE;
		boolean isFrontSuccess = false;
		
		StringBuilder hunkParam = null;

		for (int i = 0; i < 2; i++) {
			hunkParam = new StringBuilder();

			isFrontSuccess = isFrontSuccess(name, group, hunkParam);

			if (!isFrontSuccess) {
				ThreadHelper.sleepAmomentByRandom();

				continue;
			}
		}

		// 2- 判断前置任务是否已经执行成功
		if (!isFrontSuccess) {
			if (!isQuiet) {
				this.doReportFailedStatus(executeId, "该任务还有前置任务未执行成功："
						+ hunkParam.toString(), false, noneDoneState);
			} else {
				String errorDesc = "暂不执行, 前置业务尚未执行成功：" + hunkParam.toString();

				// 为了避免spring循环依赖注入, 所以采用ApplicationContext.getBean方式获取
				((DSBatchServiceListener) ctx.getBean(ConfigConstant.LISTENER))
						.reportStatusBySelf(executeId, noneDoneState,
								errorDesc);
			}

			// 莫删, 重要 !!!!!!!!!!!!!!!!!!!!!!!!
			return false;
		}

		return true;
	}
	
	/**
	 * 判断任务是否存在前置任务，前置任务的执行情况
	 * 
	 * @param name
	 * @param group
	 * @param hunkParam
	 * @return
	 */
	private boolean isFrontSuccess(String name, String group,
			final StringBuilder hunkParam) {

		final List<FrontPostJob> frontJobList = frontPostJobDao
				.getFrontPostList(name, group, JobConstant.FRONT_FLAG);

		// 重要 !!!!
		if ((null == frontJobList) || (0 >= frontJobList.size())) {
			return true;
		}

		TransactionHandler<Boolean> transaction = new TransactionHandler<Boolean>(
				transactionMgr) {
			@Override
			protected Boolean doInTransaction() throws Exception {
				return actionCheckFrontSuccess(frontJobList, hunkParam);
			}

			@Override
			protected void doWhenException(Exception e) {
				logger.error("", e);
			}
		};

		transaction.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
		transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		transaction.setTimeout(300000); // 5分钟超时, 自我修复

		try {
			return transaction.getResult();
		} catch (Exception ex) {
		}

		return false;
	}

	/**
	 * 判断任务是否存在前置任务，前置任务的执行情况
	 * 
	 * @param frontJobList
	 * @param hunkParam
	 * @return
	 */
	private boolean actionCheckFrontSuccess(List<FrontPostJob> frontJobList,
			StringBuilder hunkParam) {

		FrontPostJob frontJob = null;
		JobStatus jobStatus = null;

		boolean isAllSucess = true;

		// 避免多线程并发时的判断失误
		for (int i = 0; i < frontJobList.size(); i++) {
			frontJob = frontJobList.get(i);

			if (null == frontJob) {
				continue;
			}

			String serviceName = frontJob.getFpServiceName();
			String serviceGroup = frontJob.getFpServiceGroup();

			if (StringUtils.isEmpty(serviceName)
					|| StringUtils.isEmpty(serviceGroup)) {
				continue;
			}

			jobStatus = jobStatusDao.getByNameAndGroup(serviceName,
					serviceGroup);

			if (null == jobStatus) {
				continue;
			}

			// 状态更新时间必须是今天的时间, 否则也不算成功
			boolean isTodayState = DateUtils.isTodayDate(jobStatus
					.getStateUpdateTime());

			if ((ServiceState.SUCCESS != jobStatus.getServiceState())
					|| (!isTodayState)) {
				isAllSucess = false;

				if (0 < hunkParam.length()) {
					hunkParam.append(", ");
				}

				hunkParam.append(serviceName);
			}
		}

		return isAllSucess;
	}

	/**
	 * 报告状态
	 * 
	 * @param executeId
	 * @param errorInfo
	 * @param e
	 */
	private void doReportFailedStatus(String executeId, String errorInfo,
			Exception e) {
		String msg = doReportFailedStatusInQuiet(executeId, errorInfo, e,
				DSTaskStatus.FAILED);

		throw new IllegalStateException(msg);
	}

	/**
	 * 报告状态
	 * 
	 * @param executeId
	 * @param errorInfo
	 * @param isQuiet
	 * @param state
	 */
	private void doReportFailedStatus(String executeId, String errorInfo,
			boolean isQuiet, int state) {
		String msg = doReportFailedStatusInQuiet(executeId, errorInfo, null,
				state);

		if (!isQuiet) {
			throw new IllegalStateException(msg);
		}
	}

	/**
	 * 报告状态, 安静模式, 不抛出异常, 只安静的记录日志
	 * 
	 * @param executeId
	 * @param errorInfo
	 * @param ex
	 * @param state
	 * @return
	 */
	private String doReportFailedStatusInQuiet(String executeId,
			String errorInfo, Exception ex, int state) {

		String msg = tempArgsCache.isManual(executeId) ? "手工执行失败! --> "
				: "任务执行失败! ";

		msg += errorInfo;

		if ((null == ex) && !StringUtils.isEmpty(msg)) {
			logger.error(msg);
		} else if (null != ex) {
			logger.error(msg, ex);

			String exMsg = ex.getMessage();

			if (StringUtils.isEmpty(exMsg) || "null".equalsIgnoreCase(exMsg)) {
				exMsg = " 原因未知！";
			}

			msg += exMsg;
		}

		// 为了避免spring循环依赖注入, 所以采用ApplicationContext.getBean方式获取
		((DSBatchServiceListener) ctx.getBean(ConfigConstant.LISTENER))
				.reportStatusBySelf(executeId, state, msg);

		return msg;
	}
	
	/**
	 * 创建任务详情, 初始化任务初始状态[在事务中执行]
	 * 
	 * @param executeId
	 * @param name
	 * @param group
	 */
	private boolean resetJobStateInTransaction(final String executeId,
			final String name, final String group) {

		TransactionHandler<Boolean> transaction = new TransactionHandler<Boolean>(
				transactionMgr) {
			@Override
			protected Boolean doInTransaction() {
				JobStatus jobStatus = jobStatusDao.getByNameAndGroup(name,
						group);

				if (null == jobStatus) {
					return false;
				}

				boolean isPermitted = true;

				if (null != jobStatus.getStateUpdateTime()) {
					// 至少间隔10秒
					isPermitted = 10000 < (DateUtils.getCurrentDate().getTime() - jobStatus
							.getStateUpdateTime().getTime());
				}

				// 初始化状态(未上报)
				if (isPermitted) {
					jobStatusDao.updateStatusBySelf(name, group,
							ServiceState.NON_REPORT, executeId);
				}

				return isPermitted;
			}

			@Override
			protected void doWhenException(Exception e) {
				logger.error("", e);
			}
		};

		transaction.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
		transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		transaction.setTimeout(30000);
		
		try {
			return transaction.getResult();
		} catch (Exception ex) {
		}
		
		return false;
	}

	/**
	 * 在T_DS_JOB_EXECUTE_DETAILS表中记录一条详情数据(不带状态和描述)
	 * 
	 * @param executeId
	 * @param name
	 * @param group
	 * @param tempArgs
	 */
	private void createJobDetailsRecordInDB(String executeId, String name,
			String group, Map<String, String> tempArgs) {
		
		int nonReportState = ServiceState.NON_REPORT;
		boolean isManual = tempArgsCache.isManual(executeId);

		JobExecuteDetails detail = new JobExecuteDetails();
		detail.setExecuteId(executeId);
		detail.setServiceName(name);
		detail.setServiceGroup(group);
		detail.setExecuteMethod(isManual ? 1 : 0);
		detail.setServiceState(nonReportState);
		detail.setCreateDate(new Date());

		// 创建任务详情
		jobExecuteDetailsDao.create(detail);

		// 设置传递参数
		tempArgsCache.saveTempArgsCache(executeId, name, tempArgs);
	}
	
	/**
	 * 校验Service名称和group
	 * 
	 * @param name
	 * @param group
	 * @param executeId
	 * @param isQuiet
	 * @return
	 */
	private JobStatus queryJobStatus(String name, String group,
			String executeId, boolean isQuiet) {

		int failedState = ServiceState.FAILED;

		if (StringUtils.isExistEmpty(name, group)) {
			this.doReportFailedStatus(executeId,
					"非法任务名或任务组, 定时任务未执行任务操作终止, 任务名：" + group, isQuiet,
					failedState);

			return null;
		}

		// 1- 获取业务参数及状态
		JobStatus jobStatus = jobStatusDao.getByNameAndGroup(name, group);

		if (null == jobStatus) {
			this.doReportFailedStatus(executeId, "未在T_DS_JOB_STATES表中找到此任务："
					+ group, isQuiet, failedState);

			return null;
		}

		return jobStatus;
	}
	
	/**
	 * 获取IDSBatchService
	 * 
	 * @param name
	 * @param group
	 * @param version
	 * @param executeId
	 * @return
	 */
	private IDSBatchService getDSBatchService(String name, String group,
			String version, String executeId) {

		try {
			return DubboSupport.getDSBatchService(name, group, version,
					dubboConsumerConfig);
		} catch (Exception e) {
			logger.error("获取dubbo服务失败：", e);

			this.doReportFailedStatus(executeId, "获取dubbo服务失败：", e);
		}

		return null;
	}

	// private boolean checkFrontBussiness(String executeId, String name,
	// String group, boolean isQuiet) {
	//
	// // 如果不检查前置, 直接返回true
	// if (tempArgsCache.isIgnoreFront(executeId)) {
	// return true;
	// }
	//
	// int noneDoneState = DSTaskStatus.NON_DONE;
	//
	// StringBuilder hunkParam = new StringBuilder();
	//
	// // 2- 判断前置任务是否已经执行成功
	// if (!isFrontSuccess(name, group, hunkParam)) {
	// if (!isQuiet) {
	// this.doReportFailedStatus(executeId, "该任务还有前置任务未执行成功："
	// + hunkParam.toString(), false, noneDoneState);
	// } else {
	// String errorDesc = "暂不执行, 前置业务尚未执行成功：" + hunkParam.toString();
	//
	// // 为了避免spring循环依赖注入, 所以采用ApplicationContext.getBean方式获取
	// ((DSBatchServiceListener) ctx.getBean(ConfigConstant.LISTENER))
	// .reportStatusBySelf(executeId, noneDoneState,
	// errorDesc);
	// }
	//
	// // 莫删, 重要 !!!!!!!!!!!!!!!!!!!!!!!!
	// return false;
	// }
	//
	// return true;
	// }
}