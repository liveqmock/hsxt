/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.task;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.gy.hsi.common.utils.DistributedSyncLock;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gp.common.bean.GPCountDownLatch;
import com.gy.hsxt.gp.common.bean.Period;
import com.gy.hsxt.gp.common.constant.ConfigConst;
import com.gy.hsxt.gp.common.constant.Constant;
import com.gy.hsxt.gp.common.constant.Constant.RetryStatus;
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.mapper.TGpAbandonRetryMapper;
import com.gy.hsxt.gp.mapper.TGpRetryMapper;
import com.gy.hsxt.gp.mapper.TGpRetryPeriodMapper;
import com.gy.hsxt.gp.mapper.vo.TGpAbandonRetry;
import com.gy.hsxt.gp.mapper.vo.TGpRetry;
import com.gy.hsxt.gp.mapper.vo.TGpRetryPeriod;
import com.gy.hsxt.gp.service.IBizRetryWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.task
 * 
 *  File Name       : RetryManager.java
 * 
 *  Creation Date   : 2015-10-16
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 重试模块管理
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component("retryManager")
@Scope("singleton")
public class RetryManager {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IBizRetryWorker bizRetryWorker;

	@Autowired
	private TGpRetryMapper retryMapper;

	@Autowired
	private TGpRetryPeriodMapper retryPeriodMapper;

	@Autowired
	private TGpAbandonRetryMapper abandonRetryMapper;
	
	@Resource(name = "retryExecutor")
	private ThreadPoolTaskExecutor retryExecutor;

	/** 重试周期数据 */
	private static Map<Integer, Period> periodMap = new ConcurrentHashMap<Integer, Period>();

	/** 分布式同步锁 */
	private DistributedSyncLock syncLock;

	/**
	 * 构造函数
	 */
	public RetryManager() {
	}

	/**
	 * 重试 (执行频率：5分钟/次)
	 */
	@Scheduled(initialDelay = 120000, fixedDelay = 30000)
	protected void retry() {

		// 初始化分布式同步锁对象
		if (null == syncLock) {
			String dubboRegAddress = HsPropertiesConfigurer
					.getProperty(ConfigConst.DUBBO_REG_ADDRESS);

			syncLock = DistributedSyncLock.build(Constant.ZK_ROOT_NODE,
					Constant.ZK_SUB_NODE, dubboRegAddress);
			
			// 从数据库初始化周期数据
			this.initRetryPeriodFromDB();
		}
		
		// 获取分布式同步锁
		if (syncLock.getLock()) {
			this.actionRetry();
		}
	}

	/**
	 * 插入或更新重试记录
	 * 
	 * @param bussinessId
	 * @param businessType
	 * @param success
	 * @return 是否继续重试
	 */
	public boolean insertOrUpdateRetry(String bussinessId,
			Integer businessType, boolean success) {

		if (StringUtils.isEmpty(bussinessId) || (null == businessType)) {
			return false;
		}

		// 根据bussinessId,businessType查询是否存在
		TGpRetry gpRetry = retryMapper.selectByBusinessId(bussinessId,
				businessType);

		// 存在则更新
		if (null != gpRetry) {
			return this.updateRetry(success, gpRetry);
		}
		// 如果不存在, 只有失败才插入
		else if (!success) {
			this.insertRetry(bussinessId, businessType);
		}

		return (success) ? false : true;
	}

	/**
	 * 将数据插入重试表
	 * 
	 * @param bussinessId
	 * @param businessType
	 */
	private void insertRetry(String bussinessId, Integer businessType) {
		if (StringUtils.isEmpty(bussinessId) || (null == businessType)) {
			return;
		}

		Date currentDate = DateUtil.getCurrentDate();

		TGpRetry retry = new TGpRetry();
		retry.setBusinessId(bussinessId);
		retry.setRetryBusinessType(businessType);
		retry.setRetryStatus(RetryStatus.FAIL);
		retry.setRetryCounts(0);
		retry.setCreatedDate(currentDate);
		retry.setLastRetryDate(currentDate);
		retry.setNextRetryDate(getNextRetryDate(retry));

		retryMapper.insert(retry);
	}

	/**
	 * 根据notify状态更新重试表
	 * 
	 * @param notifySussess
	 * @param bussinessId
	 * @param retryBussinessType
	 */
	@SuppressWarnings("unused")
	private void updateRetry(boolean notifySussess, String bussinessId,
			Integer retryBussinessType) {
		if (StringUtils.isEmpty(bussinessId) || (null == retryBussinessType)) {
			return;
		}

		TGpRetry retry = new TGpRetry();
		retry.setBusinessId(bussinessId);
		retry.setRetryBusinessType(retryBussinessType);

		this.updateRetry(notifySussess, retry);
	}

	/**
	 * 根据notify状态更新重试表
	 * 
	 * @param notifySussess
	 * @param retry
	 * @return 是否继续重试
	 */
	private boolean updateRetry(boolean notifySussess, TGpRetry retry) {
		
		// 当前时间
		Date currentDate = DateUtil.getCurrentDate();
		
		try {
			retry.setUpdatedDate(currentDate);
			retry.setLastRetryDate(currentDate);
			retry.setRetryCounts(retry.getRetryCounts() + 1); // 重试次数加1
			
			// 通知成功则状态设置为成功
			if (!notifySussess) {
				// 判断是否达到最大重试次数, 达到则放弃重试
				if (isLastTimeRetry(retry)) {
					this.abandonRetry(retry);

					return false;
				}

				// 未达到则计算并设置下次重试时间
				retry.setNextRetryDate(getNextRetryDate(retry));
				retry.setRetryStatus(RetryStatus.FAIL);
			} else {
				retry.setRetryStatus(RetryStatus.SUCCESS);
			}

			retryMapper.updateByBusinessId(retry);

			return (notifySussess) ? false : true;
		} catch (Exception e) {
			logger.error("更新数据库发生异常: ", e);
			
			return false;
		}
	}

	/**
	 * 进行重试逻辑
	 */
	private void actionRetry() {

		int start = 0;
		int pageSize = 20;

		List<TGpRetry> retryList;
		GPCountDownLatch latch;

		while (true) {
			// 分页查询数据库中所有需要重试的数据
			retryList = retryMapper.selectRetryByPage(start, pageSize);

			if ((null == retryList) || (0 >= retryList.size())) {
				break;
			}

			latch = new GPCountDownLatch(retryList.size());

			// 在线程池中进行重试处理, 避免死锁
			for (TGpRetry retry : retryList) {
				actionRetryInThreadpool(retry, latch);
			}

			// 最多等待20分钟
			latch.await(20, TimeUnit.MINUTES);

			start += pageSize;
		}
	}

	/**
	 * 执行重试
	 * 
	 * @param retry
	 * @param latch
	 */
	private void actionRetryInThreadpool(final TGpRetry retry,
			final GPCountDownLatch latch) {
		try {
			// 线程池中运行
			retryExecutor.execute(new Runnable() {
				@Override
				public void run() {
					boolean isHandleSuccess = false;

					try {
						// 处理本次重试
						isHandleSuccess = bizRetryWorker.handleRetryTask(retry,
								latch);
					} catch (Exception e) {
						logger.error("重试失败：retry id = " + retry.getRetryId()
								+ ", bussiness id = " + retry.getBusinessId(),
								e);
					}

					// 更新重试结果状态, 重要！！！！！！！！！！！！！！
					updateRetry(isHandleSuccess, retry);
				}
			});
		} catch (TaskRejectedException ex) {
			latch.countDown();
		}
	}

	/**
	 * 从数据库初始化周期数据
	 */
	private void initRetryPeriodFromDB() {

		List<TGpRetryPeriod> retryPeriods = retryPeriodMapper.list();

		for (TGpRetryPeriod retryPeriod : retryPeriods) {
			String intervals[] = retryPeriod.getRetryIntervals().split("\\|");

			int count = intervals.length;
			int[] interval = new int[count];

			for (int i = 0; i < count; i++) {
				interval[i] = Integer.parseInt(intervals[i]);
			}

			Period period = new Period();
			period.setCount(count);
			period.setInterval(interval);

			periodMap.put(Integer.parseInt(retryPeriod.getRetryBusinessType()),
					period);
		}
	}

	/**
	 * 判断是否达到最大重试次数
	 * 
	 * @param retry
	 * @return
	 */
	private boolean isLastTimeRetry(TGpRetry retry) {

		Integer retryBissinessType = retry.getRetryBusinessType();

		// 根据重试类型查询重试周期
		Period period = periodMap.get(retryBissinessType);

		// 判断是否达到最大重试次数
		if (retry.getRetryCounts() >= period.getCount()) {
			return true;
		}

		return false;
	}

	/**
	 * 放弃重试
	 * 
	 * @param retry
	 */
	private void abandonRetry(TGpRetry retry) {

		// 删除重试表中数据
		retryMapper.deleteByBusinessIdAndType(retry.getBusinessId(),
				retry.getRetryBusinessType());

		// 插入至放弃重试表中
		Date currentDate = DateUtil.getCurrentDate();

		TGpAbandonRetry abandonRetry = new TGpAbandonRetry();

		BeanUtils.copyProperties(retry, abandonRetry);

		abandonRetry.setRetryId(null); // 避免主键重复
		abandonRetry.setRetryCounts(abandonRetry.getRetryCounts());
		abandonRetry.setLastRetryDate(currentDate);
		abandonRetry.setUpdatedDate(currentDate);

		try {
			abandonRetryMapper.insert(abandonRetry);
		} catch (Exception e) {
		}
	}

	/**
	 * 获取下次重试时间
	 * 
	 * @param retry
	 * @return
	 */
	private Date getNextRetryDate(TGpRetry retry) {

		Integer retryBissinessType = retry.getRetryBusinessType();

		Period period = periodMap.get(retryBissinessType);

		if (period == null) {
			initRetryPeriodFromDB();

			period = periodMap.get(retryBissinessType);
		}

		Date currentDate = DateUtil.getCurrentDate();
		int addMiniters = period.getInterval()[retry.getRetryCounts()];

		// 计算并设置下次重试时间
		return DateUtil.addMinutes(currentDate, addMiniters);
	}
}
