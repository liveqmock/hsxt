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

import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.common.constant.Constant.RetryBussinessType;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.TransType;
import com.gy.hsxt.pg.mapper.TPgChinapayOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayOrder;
import com.gy.hsxt.pg.service.IChinapayCallbackService;
import com.gy.hsxt.pg.service.IChinapayCardBindingService;
import com.gy.hsxt.pg.service.impl.parent.AbstractAppContextAwareServcie;
import com.gy.hsxt.pg.task.RetryManager;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : ChinapayCallbackService.java
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
@Service("chinapayCallbackService")
public class ChinapayCallbackService extends AbstractAppContextAwareServcie
		implements IChinapayCallbackService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private TPgChinapayOrderMapper payOrderMapper;

	@Autowired
	private IChinapayCardBindingService cardBindingService;

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

		TPgChinapayOrder payOrder = new TPgChinapayOrder();
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
				notifySuccess = getNotifyServiceForPay(bankOrderNo)
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

	/**
	 * 获取并通知签约号到用户中心
	 * 
	 * @param priv1
	 * @param bankOrderNo
	 * @param merType
	 * @throws Exception
	 */
	public void fetchAndNotifyBindingNo(final String priv1,
			final String bankOrderNo, final int merType) throws Exception {

		if (StringUtils.isEmpty(priv1)) {
			return;
		}

		// 线程池中运行
		queryBindingNoExecutor.execute(new Runnable() {
			@Override
			public void run() {

				try {
					// 私有域 (银行卡号,借贷记标识,银行id)
					String[] privs = priv1.split("\\,");

					if ((null == privs) || (3 != privs.length)) {
						return;
					}

					String bankCardNo = privs[0];
					String bankCardType$ = privs[1]; // 卡类型
					String bankId = privs[2];

					// 卡类型
					Integer bankCardType = StringHelper.str2Int(bankCardType$);

					// 获取并通知签约号
					cardBindingService.fetchBindingNoFromCP(bankCardNo,
							bankId, bankCardType, bankOrderNo, merType);
				} catch (Exception e) {
					logger.error("获取并通知GP签约号时, 发生异常！！", e);
				}
			}
		});
	}
}
