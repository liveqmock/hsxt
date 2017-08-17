/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.api.IPGNotifyService;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGTransCashOrderState;
import com.gy.hsxt.pg.common.bean.PGCountDownLatch;
import com.gy.hsxt.pg.common.constant.Constant.RetryBussinessType;
import com.gy.hsxt.pg.mapper.TPgChinapayDayBalanceMapper;
import com.gy.hsxt.pg.mapper.TPgChinapayOrderMapper;
import com.gy.hsxt.pg.mapper.TPgPinganOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayDayBalance;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayOrder;
import com.gy.hsxt.pg.mapper.vo.TPgPinganOrder;
import com.gy.hsxt.pg.mapper.vo.TPgRetry;
import com.gy.hsxt.pg.service.IBizRetryWorker;
import com.gy.hsxt.pg.service.IChinapayDayBalanceFileService;
import com.gy.hsxt.pg.service.IRefundPost;
import com.gy.hsxt.pg.service.ITransCashPost;
import com.gy.hsxt.pg.service.ITriggerDSBatchService;
import com.gy.hsxt.pg.service.impl.parent.AbstractAppContextAwareServcie;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : BizRetryWorker.java
 * 
 *  Creation Date   : 2015年12月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 重试处理工作者实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("bizRetryWorker")
public class BizRetryWorker extends AbstractAppContextAwareServcie implements
		IBizRetryWorker {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IRefundPost refundPost;

	@Autowired
	private ITransCashPost pgTransCashService;

	@Autowired
	private IChinapayDayBalanceFileService dayBalanceService;

	@Autowired
	private ITriggerDSBatchService triggerDSBatchService;

	@Autowired
	private TPgChinapayOrderMapper chinapayOrderMapper;

	@Autowired
	private TPgPinganOrderMapper pinganOrderMapper;

	@Autowired
	private TPgChinapayDayBalanceMapper dayBalanceMapper;

	@Override
	public boolean handleRetryTask(TPgRetry retry, PGCountDownLatch latch) {
		// 根据业务类型判断要执行的操作
		int bussinessType = retry.getRetryBusinessType();

		switch (bussinessType) {
			// 支付结果通知GP
			case RetryBussinessType.PAYMENT_NT: {
				return doPaymentNotifyRetry(retry, latch);
			}
			// 退款结果通知GP
			case RetryBussinessType.REFUND_NT: {
				return doPaymentNotifyRetry(retry, latch);
			}
			// 单笔转账结果回调通知(内部系统)
			case RetryBussinessType.TRANS_CASH_NT: {
				return doTransNotifyRetry(retry, latch);
			}
			// 批量转账结果回调通知(内部系统, 假批量, 实际也是一个个处理, 处理的需要而设置的常量)
			case RetryBussinessType.TRANS_CASH_BATCH_NT: {
				return doTransBatchNotifyRetry(retry, latch);
			}
			// 转账结果查询(银行)
			case RetryBussinessType.TRANS_CASH_RESULT_REQ: {
				return doTransResultReqRetry(retry, latch);
			}
			// 下载对账文件(银联)
			case RetryBussinessType.DOWNLOAD_BALANCE_REQ: {
				return doDownloadBalanceFile(retry, latch);
			}
			// 单笔转账请求(银行)
			case RetryBussinessType.TRANS_CASH_REQ: {
				return doTransReqRetry(retry, latch);
			}
			// 批量转账请求(银行)
			case RetryBussinessType.TRANS_CASH_BATCH_REQ: {
				return doBatchTransReqRetry(retry, latch);
			}
			// 退款结果通知GP
			case RetryBussinessType.REFUND_REQ: {
				return doRefundReqRetry(retry, latch);
			}
			default: {
			}
		}

		return true;
	}

	/**
	 * 支付通知
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 * @throws HsException
	 */
	private boolean doPaymentNotifyRetry(TPgRetry retry, PGCountDownLatch latch) {
		try {
			// 根据银行支付单号查询
			PGPaymentOrderState pgPaymentState = chinapayOrderMapper
					.selectStateByBankOrderNo(retry.getBusinessId());

			if (null != pgPaymentState) {
				String bankOrderNo = pgPaymentState.getBankOrderNo();

				// 调用GP系统，通知支付结果
				return getNotifyServiceForPay(bankOrderNo)
						.notifyPaymentOrderState(pgPaymentState);
			}
		} catch (Exception e) {
			logger.debug("支付结果状态通知重试再次失败:", e);

			return false;
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return true;
	}

	/**
	 * 转账结果通知
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doTransNotifyRetry(TPgRetry retry, PGCountDownLatch latch) {
		try {
			// 根据银行支付单号查询
			PGTransCashOrderState pgTransCashOrderState = pinganOrderMapper
					.selectStateByBankOrderNo(retry.getBusinessId());

			if (null != pgTransCashOrderState) {
				String bankOrderNo = pgTransCashOrderState.getBankOrderNo();

				// 调用GP系统，通知转账结果
				return getNotifyServiceForTrans(bankOrderNo)
						.notifyTransCashOrderState(pgTransCashOrderState);
			}
		} catch (Exception e) {
			logger.debug("转账结果状态通知重试再次失败:", e);

			return false;
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return true;
	}

	/**
	 * 假批量转账结果通知
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doTransBatchNotifyRetry(TPgRetry retry,
			PGCountDownLatch latch) {

		boolean isAllSuccess = true;

		try {
			// 根据银行支付单号查询
			List<PGTransCashOrderState> pgTransCashOrderStates = pinganOrderMapper
					.selectStateByBatchNo(retry.getBusinessId());

			if ((null != pgTransCashOrderStates)
					&& (0 < pgTransCashOrderStates.size())) {
				IPGNotifyService pgNotifyService = null;

				// 调用GP系统，通知转账结果
				for (PGTransCashOrderState state : pgTransCashOrderStates) {

					if (null == pgNotifyService) {
						pgNotifyService = getNotifyServiceForTrans(state
								.getBankOrderNo());
					}

					if (!pgNotifyService.notifyTransCashOrderState(state)) {
						isAllSuccess = false;
					}
				}
			}
		} catch (Exception e) {
			isAllSuccess = false;

			logger.debug("", e);
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return isAllSuccess;
	}

	/**
	 * 退款通知
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doRefundReqRetry(TPgRetry retry, PGCountDownLatch latch) {

		try {
			// 根据银行支付单号查询
			TPgChinapayOrder chinapayOrder = chinapayOrderMapper
					.selectByBankOrderNo(retry.getBusinessId());

			// 调用银联接口进行退款操作
			if (null != chinapayOrder) {
				refundPost.refund(chinapayOrder);
			}
		} catch (Exception e) {
			logger.debug("", e);

			return false;
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return true;
	}

	/**
	 * 转账请求提交银行
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doTransReqRetry(TPgRetry retry, PGCountDownLatch latch) {

		try {
			// 根据银行支付单号查询数据库
			TPgPinganOrder pinganOrder = pinganOrderMapper
					.selectByBankOrderNo(retry.getBusinessId());

			if (null != pinganOrder) {
				// 根据查询的数据 调用银行接口，提交转账请求
				return pgTransCashService.postTranCash(pinganOrder);
			}
		} catch (Exception e) {
			logger.debug("", e);
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return false;
	}

	/**
	 * 批量转账请求提交银行
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doBatchTransReqRetry(TPgRetry retry, PGCountDownLatch latch) {

		try {
			// 根据批次号查询
			List<TPgPinganOrder> pinganOrders = pinganOrderMapper
					.selectByBatchNo(retry.getBusinessId());

			if (null != pinganOrders) {
				// 调用银行接口，提交批量转账请求
				return pgTransCashService.postBatchTransCash(
						retry.getBusinessId(), pinganOrders);
			}
		} catch (Exception e) {
			logger.debug("", e);
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return false;
	}

	/**
	 * 转账结果查询
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doTransResultReqRetry(TPgRetry retry, PGCountDownLatch latch) {
		// 调用银行接口，提交转账结果查询
		try {
			return pgTransCashService.qryAndNotifySingleTransfer(retry
					.getBusinessId());
		} catch (Exception e) {
			logger.debug("", e);

			return false;
		} finally {
			latch.countDown();
		}
	}

	/**
	 * 下载银行对账文件
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doDownloadBalanceFile(TPgRetry retry, PGCountDownLatch latch) {
		try {
			TPgChinapayDayBalance dayBalance = dayBalanceMapper
					.selectByPrimaryKey(retry.getBusinessId());

			if (null != dayBalance) {
				// 下载对账文件
				boolean downloadSuccess = dayBalanceService
						.dowloadDayBalanceFile(dayBalance, true);

				// 验证签名
				boolean verifySuccess = dayBalanceService
						.verifyDayBalanceFile(dayBalance);

				if (downloadSuccess && verifySuccess) {
					triggerDSBatchService.actionTrigger(dayBalance
							.getBalanceDateStrValue());
				}

				return downloadSuccess;
			}
		} catch (Exception e) {
			logger.debug("", e);

			return false;
		} finally {
			latch.countDown();
		}

		return true;
	}
}