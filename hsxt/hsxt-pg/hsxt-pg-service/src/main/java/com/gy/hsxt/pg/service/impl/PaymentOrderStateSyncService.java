/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderStatus;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.IPaymentNotifyService;
import com.gy.hsxt.pg.service.IPaymentOrderStateSyncService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : PaymentOrderStateSyncService.java
 * 
 *  Creation Date   : 2016年3月3日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 支付单状态同步操作接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class PaymentOrderStateSyncService implements
		IPaymentOrderStateSyncService {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IPaymentNotifyService callbackService;

	@Autowired
	private TPgBankPaymentOrderMapper payOrderMapper;

	@Resource(name = "syncOrderStatusExecutor")
	private ThreadPoolTaskExecutor syncOrderStatusExecutor;

	@Override
	public void actionOrderStateSync(
			List<BankPaymentOrderResultDTO> orderResultList) {
		if (null == orderResultList) {
			return;
		}

		final List<BankPaymentOrderResultDTO> $orderResultList = new ArrayList<BankPaymentOrderResultDTO>(
				orderResultList.size());

		$orderResultList.addAll(orderResultList);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					beginSyncOrderState($orderResultList);
				} catch (Exception e) {
					logger.info("同步银联的订单状态到本地数据库表失败：", e);
				}
			}
		};

		try {
			syncOrderStatusExecutor.execute(runnable);
		} catch (TaskRejectedException e) {
			runnable.run();
		}
	}

	/**
	 * 开始同步状态
	 * 
	 * @param orderResultList
	 */
	private void beginSyncOrderState(
			List<BankPaymentOrderResultDTO> orderResultList) {
		boolean isExist;
		int orderStatus;

		String bankOrderNo;
		String bankRespCode;
		String failReason;
		TPgBankPaymentOrder chinapayOrder;

		for (BankPaymentOrderResultDTO orderResult : orderResultList) {
			bankOrderNo = orderResult.getOrderNo();
			bankRespCode = orderResult.getBankRespCode();
			failReason = orderResult.getErrorInfo();
			orderStatus = orderResult.getOrderStatus().getIntValue();

			// 不存在这个场景, 但是为了保证万无一失, 加上此判断
			if (PaymentOrderStatus.UNKNOWN.valueEquals(orderStatus)
					|| PaymentOrderStatus.INVALID.valueEquals(orderStatus)) {
				continue;
			}

			isExist = (0 < payOrderMapper
					.isExistOrder(bankOrderNo, orderStatus));

			// 状态一致, 不处理!!!!!!!!!!!!
			if (isExist) {
				continue;
			}

			if (PaymentOrderStatus.PAY_SUCCESS.valueEquals(orderStatus)) {
				bankRespCode = null;
				failReason = null;
			}

			try {
				chinapayOrder = new TPgBankPaymentOrder();
				chinapayOrder.setBankOrderNo(bankOrderNo);
				chinapayOrder.setBankOrderStatus(bankRespCode);
				chinapayOrder.setTransStatus(orderStatus);
				chinapayOrder.setFailReason(failReason);

				// 更新PG支付单表
				payOrderMapper.updateStatusByBankOrderNo(chinapayOrder);

				// 查询支付单状态
				PGPaymentOrderState paymentOrderState = payOrderMapper
						.selectStateByBankOrderNo(bankOrderNo);

				// 通知GP支付系统,进行状态同步
				if (null != paymentOrderState) {
					callbackService.notifyOrderState(paymentOrderState);
				}
			} catch (Exception e) {
			}
		}
	}
}
