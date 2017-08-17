/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.gp.common.bean.GPCountDownLatch;
import com.gy.hsxt.gp.common.utils.MerIdUtils;
import com.gy.hsxt.gp.constant.GPConstant.GPTransType;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.TGpTransOrderMapper;
import com.gy.hsxt.gp.mapper.vo.RefundVo;
import com.gy.hsxt.gp.mapper.vo.TGpRetry;
import com.gy.hsxt.gp.service.IBizRetryWorker;
import com.gy.hsxt.gp.service.impl.parent.AbstractAppContextAwareServcie;
import com.gy.hsxt.pg.api.IPGPaymentService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl
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

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TGpPaymentOrderMapper paymentOrderMapper;

	@Autowired
	private IPGPaymentService pgPaymentService;

	@Autowired
	private TGpTransOrderMapper transOrderMapper;

	@Override
	public boolean handleRetryTask(TGpRetry retry, GPCountDownLatch latch) {
		// 根据业务类型判断要执行的操作
		int bussinessType = retry.getRetryBusinessType();

		switch (bussinessType) {
			// 支付状态回调通知重试
			case GPTransType.PAYMENT: {
				return this.doPaymentRetry(retry, latch);
			}
			// 单笔提现状态回调通知重试
			case GPTransType.TRANS_CASH: {
				return this.doTransRetry(retry, latch);
			}
			// 退款请求重试
			case GPTransType.REFUND: {
				return this.doRefundRetry(retry, latch);
			}
			default: {
			}
		}

		return true;
	}

	/**
	 * 执行支付通知
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 * @throws HsException
	 */
	private boolean doPaymentRetry(TGpRetry retry, GPCountDownLatch latch)
			throws HsException {

		try {
			// 根据银行支付单号查询
			PaymentOrderState paymentState = paymentOrderMapper
					.selectOrderStateByPrimary(retry.getBusinessId());

			if (null != paymentState) {
				// 来源子系统id
				String srcSubsysId = paymentState.getSrcSubsysId();

				// 调用BS系统，通知支付结果
				return getNotifyService(srcSubsysId).notifyPaymentOrderState(
						paymentState);
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
	 * 执行提现通知
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 * @throws HsException
	 */
	private boolean doTransRetry(TGpRetry retry, GPCountDownLatch latch)
			throws HsException {
		try {
			// 根据银行支付单号查询
			TransCashOrderState transCashOrderState = transOrderMapper
					.selectOrderStateByPrimary(retry.getBusinessId());

			if (null != transCashOrderState) {
				// 来源子系统id
				String srcSubsysId = transCashOrderState.getSrcSubsysId();

				// 调用BS系统，通知提现结果
				return getNotifyService(srcSubsysId).notifyTransCashOrderState(
						transCashOrderState);
			}
		} catch (Exception e) {
			logger.debug("提现结果状态通知重试再次失败:", e);

			return false;
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return true;
	}

	/**
	 * 退款请求
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	private boolean doRefundRetry(TGpRetry retry, GPCountDownLatch latch) {
		try {
			// 根据银行支付单号查询
			RefundVo refundVo = paymentOrderMapper.selectRefundByPrimary(retry
					.getBusinessId());

			if (null != refundVo) {
				// 调用支付网关进行退款操作
				return pgPaymentService.refund(refundVo.getBankOrderNo(),
						refundVo.getOrigOrderNo(),
						MerIdUtils.merId2Mertype(refundVo.getMerId()));
			}
		} catch (Exception e) {
			logger.debug("退款请求重试再次失败:", e);

			return false;
		} finally {
			// 注意: 不可遗漏 !!!!!!!!!!!!!!!!!
			latch.countDown();
		}

		return true;
	}

}
