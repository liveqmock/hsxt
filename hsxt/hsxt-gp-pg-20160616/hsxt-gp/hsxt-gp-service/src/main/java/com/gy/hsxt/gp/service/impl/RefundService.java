/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.gp.common.constant.ConfigConst;
import com.gy.hsxt.gp.common.spring.transaction.TransactionHandler;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.common.utils.MerIdUtils;
import com.gy.hsxt.gp.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.gp.constant.GPConstant.GPTransType;
import com.gy.hsxt.gp.constant.GPConstant.PaymentStateCode;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrder;
import com.gy.hsxt.gp.service.IRefundService;
import com.gy.hsxt.gp.task.RetryManager;
import com.gy.hsxt.pg.api.IPGPaymentService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl
 * 
 *  File Name       : RefundService.java
 * 
 *  Creation Date   : 2016年5月18日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 退款service
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Component("refundService")
public class RefundService implements IRefundService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TGpPaymentOrderMapper tGpPaymentOrderMapper;

	@Autowired
	private IPGPaymentService pgPaymentService;

	@Autowired
	private RetryManager retryManager;

	@Autowired
	private DataSourceTransactionManager transactionMgr;

	private static int expiredtime = HsPropertiesConfigurer
			.getPropertyIntValue(ConfigConst.EXPIRED_DATE);

	@Override
	public void refund() {
		// 扫描支付表中重复的支付单
		List<TGpPaymentOrder> sameOrderList = tGpPaymentOrderMapper
				.listSameOrder();

		String srcSubsysId = HsPropertiesConfigurer
				.getProperty(ConfigConst.SYSTEM_ID);

		for (TGpPaymentOrder paymentOrder : sameOrderList) {
			String transSeqId = paymentOrder.getTransSeqId();

			// 生成银行交易订单号
			String bankOrderNo = TimeSecondsSeqWorker.timeNextId16(srcSubsysId);

			// 根据支付单生成退款单
			TGpPaymentOrder refundOrder = compactRefundOrder(paymentOrder);

			// 设置银行交易订单号
			refundOrder.setBankOrderNo(bankOrderNo);

			// 将原支付单设置为无效状态，插入新退款单
			insertRefund(paymentOrder, refundOrder);

			// 调用支付网关进行退款操作
			boolean notifySuccess = false;

			try {
				notifySuccess = pgPaymentService.refund(bankOrderNo,
						paymentOrder.getBankOrderNo(),
						MerIdUtils.merId2Mertype(paymentOrder.getMerId()));
			} catch (Exception e) {
				logger.debug("发起银联退款请求发生异常:", e);
			}

			if (!notifySuccess) {
				logger.info("发起银联退款请求结果失败? notifySuccess=" + notifySuccess);
			}

			// 根据notify状态更新重试表
			retryManager.insertOrUpdateRetry(transSeqId, GPTransType.REFUND,
					notifySuccess);
		}
	}

	/**
	 * 根据支付单生成退款单
	 * 
	 * @param paymentOrder
	 * @return
	 */
	private TGpPaymentOrder compactRefundOrder(TGpPaymentOrder paymentOrder) {
		TGpPaymentOrder refundOrder = new TGpPaymentOrder();
		BeanUtils.copyProperties(paymentOrder, refundOrder);

		// 当前时间
		Date currentDate = DateUtils.getCurrentDate();

		// 生成支付交易流水号
		String transSeqId = TimeSecondsSeqWorker.timeNextId32();

		refundOrder.setTransSeqId(transSeqId);
		refundOrder.setTransStatus(PaymentStateCode.READY);
		refundOrder.setTransType(GPTransType.REFUND);
		// 原交易流水号做为退款的订单号
		refundOrder.setOrderNo(paymentOrder.getTransSeqId());

		// 原银行支付流水号
		refundOrder.setOrigOrderNo(paymentOrder.getBankOrderNo());
		refundOrder.setTransDate(currentDate);
		refundOrder.setExpiredDate(DateUtils.addMinutes(currentDate,
				expiredtime));
		refundOrder.setMerId(paymentOrder.getMerId());
		refundOrder.setCreatedDate(currentDate);

		return refundOrder;
	}

	/**
	 * 将原支付单设置为无效状态，插入新退款单
	 * 
	 * @param paymentOrder
	 * @param refund
	 */
	private void insertRefund(final TGpPaymentOrder paymentOrder,
			final TGpPaymentOrder refund) {

		// 交给事务进行处理
		TransactionHandler<Boolean> transaction = new TransactionHandler<Boolean>(
				transactionMgr) {
			@Override
			protected Boolean doInTransaction() {
				// 插入新退款单
				tGpPaymentOrderMapper.insert(refund);

				// 将原支付单设置为无效状态
				tGpPaymentOrderMapper.update2InvalidByPrimary(paymentOrder
						.getTransSeqId());

				return true;
			}
		};

		// 避免生产环境报“SQL state [HY000]; error code [1665];”错误, 不可以设置为其他隔离级别
		transaction.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);

		transaction.getResult();
	}
}
