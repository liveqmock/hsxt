/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.ChinapayFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.params.ChinapayRefundParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.ChinapayUpmpFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.params.UpmpRefundParam;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.common.bean.CallbackRouterInfo;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.constant.Constant.RetryBussinessType;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayOrder;
import com.gy.hsxt.pg.service.IRefundPost;
import com.gy.hsxt.pg.task.RetryManager;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : RefundPost.java
 * 
 *  Creation Date   : 2015年12月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 退款处理, 由zhangysh优化
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("refundPost")
public class RefundPost implements IRefundPost {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ChinapayFacade chinapayB2cFacade;

	@Autowired
	private ChinapayUpmpFacade chinapayUpmpFacade;

	@Autowired
	private RetryManager retryManager;

	@Override
	public void refund(TPgChinapayOrder refundOrder) {

		// 根据渠道确定调用的银行接口
		if (PGPayChannel.UPMP == refundOrder.getPayChannel()) {
			// 手机支付退款
			this.actionUpmpRefund(refundOrder);
		} else {
			// 网银支付、快捷支付退款
			this.actionChinapayRefund(refundOrder);
		}
	}

	/**
	 * 组装notifyUrl
	 * 
	 * @param payChannel
	 * @param merType
	 * @param bizType
	 * @return
	 */
	private String getNotifyUrl(Integer payChannel, Integer merType,
			Integer bizType) {
		CallbackRouterInfo routerInfo = new CallbackRouterInfo(payChannel,
				merType, bizType);

		return CallbackUrlHelper.assembleCallbackNotifyUrl(routerInfo);
	}

	/**
	 * 对于中国银联“自动退款”请求,以异步的方式，调用线程池进行银联相应接口的调用 手机支付退款
	 * 
	 * @param refundOrder
	 */
	private void actionUpmpRefund(TPgChinapayOrder refundOrder) {
		UpmpRefundParam params = new UpmpRefundParam();
		params.setQn(refundOrder.getBankOrderSeqId());
		params.setNotifyUrl(getNotifyUrl(refundOrder.getPayChannel(),
				refundOrder.getMerType(), PGCallbackBizType.CALLBACK_REFUND));
		params.setRefundAmount(MoneyAmountHelper.fromYuanToFen(refundOrder
				.getTransAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
				.toString()));
		params.setRefundOrderNo(refundOrder.getBankOrderNo());

		try {
			chinapayUpmpFacade.getRefundWorker().doRefund(params);
		} catch (Exception e) {
			retryManager.insertOrUpdateRetry(params.getRefundOrderNo(),
					RetryBussinessType.REFUND_REQ, false);

			logger.debug("UPMP退款失败发生异常: ", e);
		}
	}

	/**
	 * 对于中国银联“自动退款”请求,以异步的方式，调用线程池进行银联相应接口的调用 网银，快捷支付退款
	 * 
	 * @param refundOrder
	 */
	private void actionChinapayRefund(TPgChinapayOrder refundOrder) {
		String notifyUrl = getNotifyUrl(refundOrder.getPayChannel(),
				refundOrder.getMerType(), PGCallbackBizType.CALLBACK_REFUND);

		BigInteger refundAmount = MoneyAmountHelper.fromYuanToFen(refundOrder
				.getTransAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
				.toString());

		ChinapayRefundParam params = new ChinapayRefundParam();
		params.setNotifyURL(notifyUrl);
		params.setOrderNo(refundOrder.getOrigBankOrderNo());
		params.setRefundAmount(refundAmount);
		// params.setRefundNote(refundNote);
		params.setTranslateDate(refundOrder.getTransDate());

		try {
			@SuppressWarnings("unused")
			ChinapayOrderResultDTO dto = chinapayB2cFacade.getRefundWorker()
					.doRefund(params);
		} catch (Exception e) {
			retryManager.insertOrUpdateRetry(params.getOrderNo(),
					RetryBussinessType.REFUND_REQ, false);

			logger.debug("银联退款失败发生异常: ", e);
		}
	}
}
