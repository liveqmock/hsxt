/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSExternalTriggerServcie;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.pg.common.constant.ConfigConst.TcConfigConsts;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.constant.Constant.DownloadStatus;
import com.gy.hsxt.pg.common.constant.Constant.LockBizType;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayDayBalance;
import com.gy.hsxt.pg.mapper.vo.TPgOperationLock;
import com.gy.hsxt.pg.service.IChinapayDayBalanceFileService;
import com.gy.hsxt.pg.service.IOperationLockService;
import com.gy.hsxt.pg.service.ITriggerDSBatchService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : TriggerDSBatchService.java
 * 
 *  Creation Date   : 2016年3月2日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 触发调度服务
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service("triggerDSBatchService")
public class TriggerDSBatchService implements ITriggerDSBatchService {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IChinapayDayBalanceFileService dayBalanceService;

	@Autowired
	private IDSExternalTriggerServcie dsTriggerServcie;

	@Autowired
	private IOperationLockService lockService;

	@Override
	public void actionTrigger(String balanceDate) {

		TPgOperationLock lock = lockService.selectLock(balanceDate,
				LockBizType.TRIGGER_DS);

		if ((null != lock) && (lock.isExpired())) {
			lockService.deleteLock(balanceDate, LockBizType.TRIGGER_DS);
		}

		// 判断是否几个对账文件全部已经下载OK
		try {
			boolean isAllBalanceFileOk = isAllBalanceFileOk(balanceDate);

			if (isAllBalanceFileOk) {
				// 加锁, 加锁失败则放弃
				lockService.addLock(balanceDate, LockBizType.TRIGGER_DS);

				// 通知DS调度系统开始调用PG的PGChAccountingFileDSBatchService接口, 生成对账文件
				this.triggerDsByExternal(balanceDate);
			}

			logger.info("是否中国 银联所有对账文件已经准备好? isAllBalanceFileOk="
					+ isAllBalanceFileOk);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 触发调度
	 * 
	 * @param balanceDate
	 */
	private void triggerDsByExternal(String balanceDate) {

		String serviceName = HsPropertiesConfigurer
				.getProperty(TcConfigConsts.KEY_SYS_TC_TRIGGER_DS_JOBNAME);

		String serviceGroup = HsPropertiesConfigurer
				.getProperty(TcConfigConsts.KEY_SYS_TC_TRIGGER_DS_JOBGROUP);

		Map<String, String> tempArgs = new HashMap<String, String>(1);
		tempArgs.put(Constant.ACC_FILE_DATE, balanceDate);

		logger.info("通知DS调度调用PG对账文件生成接口, serviceName=" + serviceName
				+ ", serviceGroup=" + serviceGroup);

		// 通知DS调度系统开始调用PG的PGChAccountingFileDSBatchService接口, 生成对账文件
		dsTriggerServcie.triggerByExternal(serviceName, serviceGroup, tempArgs);
	}

	/**
	 * 是否所有的对账文件已经准备OK
	 * 
	 * @param balanceDate
	 * @return
	 */
	private boolean isAllBalanceFileOk(String balanceDate) {

		Map<Integer, TPgChinapayDayBalance> balanceDatasMap = queryChinapayBalanceInfo(balanceDate);

		// 暂时只有B2C、UPOP才能对账, 手机支付暂时银联不提供自动对账功能
		int[] payChannels = new int[] { PGPayChannel.B2C, PGPayChannel.UPOP };

		for (int payChannel : payChannels) {
			TPgChinapayDayBalance dayBalance = balanceDatasMap.get(payChannel);

			if ((null == dayBalance)
					|| (DownloadStatus.SUCCESS != dayBalance
							.getDownloadStatus())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 转换为map结构
	 * 
	 * @param balanceDate
	 * @return
	 */
	private Map<Integer, TPgChinapayDayBalance> queryChinapayBalanceInfo(
			String balanceDate) {

		List<TPgChinapayDayBalance> balanceDatas = dayBalanceService
				.queryDayBalanceFileInfo(balanceDate);

		Map<Integer, TPgChinapayDayBalance> map = new HashMap<Integer, TPgChinapayDayBalance>(
				3);

		if (null != balanceDatas) {
			for (TPgChinapayDayBalance balance : balanceDatas) {
				map.put(balance.getPayChannel(), balance);
			}
		}

		return map;
	}
}
