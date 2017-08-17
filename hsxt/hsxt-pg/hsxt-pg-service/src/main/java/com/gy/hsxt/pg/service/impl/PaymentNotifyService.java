/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gy.hsxt.pg.api.IPGNotifyService;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.common.constant.Constant.RetryBussinessType;
import com.gy.hsxt.pg.constant.PGConstant.TransType;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.IPaymentNotifyService;
import com.gy.hsxt.pg.service.IChinapayCardBindingService;
import com.gy.hsxt.pg.task.RetryManager;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : PaymentNotifyService.java
 * 
 *  Creation Date   : 2015-10-19
 * 
 *  Author          : 由zhangysh优化
 * 
 *  Purpose         : 处理支付回调的service
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("paymentNotifyService")
public class PaymentNotifyService implements IPaymentNotifyService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TPgBankPaymentOrderMapper payOrderMapper;

	@Autowired
	private IChinapayCardBindingService cardBindingService;

	@Autowired
	private IPGNotifyService pgNotifyService;

	@Autowired
	private RetryManager retryManager;

	@Resource(name = "queryBindingNoExecutor")
	private ThreadPoolTaskExecutor queryBindingNoExecutor;

	/**
	 * 根据支付结果更新数据库并通知重试模块通知支付系统
	 * 
	 * @param paymentOrderState
	 */
	public void notifyOrderState(PGPaymentOrderState paymentOrderState) {

		if (null == paymentOrderState) {
			return;
		}

		boolean notifySuccess = false;

		String bankOrderNo = paymentOrderState.getBankOrderNo();

		TPgBankPaymentOrder payOrder = new TPgBankPaymentOrder();
		payOrder.setBankOrderNo(bankOrderNo);
		payOrder.setBankOrderSeqId(paymentOrderState.getBankOrderSeqId());
		payOrder.setTransStatus(paymentOrderState.getTransStatus());
		payOrder.setFailReason(paymentOrderState.getFailReason());
		payOrder.setBankOrderStatus(paymentOrderState.getBankOrderStatus());

		try {
			// 更新数据库中记录
			int rows = payOrderMapper.updateStatusByBankOrderNo(payOrder);

			// 通知支付系统GP
			if (0 < rows) {
				notifySuccess = pgNotifyService
						.notifyPaymentOrderState(paymentOrderState);
			}
		} catch (Exception e) {
			logger.error("notifyOrderState 通知支付单状态失败: ", e);
		} finally {
			// 重试业务类型
			Integer retryBusinessType = null;

			if (TransType.PAYMENT == paymentOrderState.getTransType()) {
				retryBusinessType = RetryBussinessType.PAYMENT_NT;
			} else if (TransType.REFUND == paymentOrderState.getTransType()) {
				retryBusinessType = RetryBussinessType.REFUND_NT;
			}

			// 插入重试表
			retryManager.insertOrUpdateRetry(
					paymentOrderState.getBankOrderNo(), retryBusinessType,
					notifySuccess);
		}
	}

	/**
	 * 通知签约号
	 * 
	 * @param quickPayBindingNo
	 * @param merType
	 */
	@Override
	public void notifyBindingNo(final PGQuickPayBindingNo quickPayBindingNo,
			final int merType) {

		if (null == quickPayBindingNo) {
			logger.info("quickPayBindingNo is null !!");

			return;
		}

		// 线程池中运行
		queryBindingNoExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 通知GP-->UC
				cardBindingService.notifyBindingNo(quickPayBindingNo, merType);
			}
		});
	}
}
