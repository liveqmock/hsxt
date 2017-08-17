/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2c.abstracts;

import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;

import com.gy.hsxt.pg.bankadapter.common.beans.BankPaymentOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderStatus;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderTransType;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.params.PinganB2cPayRespParam;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2c.abstracts
 * 
 *  File Name       : AbstractPinganB2cWorker.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安b2c网银支付
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class AbstractPinganB2cWorker {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected String mechantNo;

	protected String commonPaymentUrl;
	protected String b2bPaymentUrl;
	protected String b2cPaymentUrl;

	public AbstractPinganB2cWorker() {
	}

	/**
	 * 转换为BankPaymentOrderResultDTO对象
	 * 
	 * @param bankRespParam
	 * @param pgTransType
	 * @return
	 */
	protected BankPaymentOrderResultDTO convert2BankPaymentOrderResultDTO(
			PinganB2cPayRespParam bankRespParam, int pgTransType) {

		// 订单时间
		Date tranTime = DateUtils.parseDate("yyyy-MM-dd HH:mm:ss",
				bankRespParam.getTranTime());

		// 交易状态
		PaymentOrderStatus orderStatus = toPinganB2cStatus(
				bankRespParam.getTranStatus(), pgTransType);

		// 交易金额
		BigInteger amount = MoneyAmountHelper.fromYuanToFen(bankRespParam
				.getPayAmount());

		BankPaymentOrderResultDTO dto = new BankPaymentOrderResultDTO();
		dto.setCrrrency(bankRespParam.getCurrency());
		dto.setOrderNo(bankRespParam.getOrderNo());
		dto.setOrderStatus(orderStatus);
		dto.setPayAmount(amount);
		dto.setTranTime(tranTime);
		dto.setTransType(pgTransType);

		return dto;
	}

	/**
	 * 将支付网关的状态转换为银行的'订单'状态
	 * 
	 * @param bankOrderStatus
	 * @param pgTransType
	 * @return
	 */
	private PaymentOrderStatus toPinganB2cStatus(String bankOrderStatus,
			int pgTransType) {
		// ******************重要： 将支付网关的状态转换为银行的'订单'状态 **********************

		// UPACP, 支付业务
		if (PaymentOrderTransType.TYPE_PAY.getValue() == pgTransType) {
			if ("01".equals(bankOrderStatus)) {
				return PaymentOrderStatus.PAY_SUCCESS;
			}

			if ("00".equals(bankOrderStatus)) {
				return PaymentOrderStatus.PAY_PROCESSING;
			}

			return PaymentOrderStatus.PAY_FAILED;
		}

		// UPACP, 退款业务
		if (PaymentOrderTransType.TYPE_REFUND.getValue() == pgTransType) {
			if ("01".equals(bankOrderStatus)) {
				return PaymentOrderStatus.REFUND_SUCCESS;
			}

			if ("00".equals(bankOrderStatus)) {
				return PaymentOrderStatus.REFUND_PROCESSING;
			}

			return PaymentOrderStatus.REFUND_FAILED;
		}

		return PaymentOrderStatus.UNKNOWN;
	}

	public String getMechantNo() {
		return mechantNo;
	}

	public void setMechantNo(String mechantNo) {
		this.mechantNo = mechantNo;
	}

	public String getCommonPaymentUrl() {
		return commonPaymentUrl;
	}

	public void setCommonPaymentUrl(String commonPaymentUrl) {
		this.commonPaymentUrl = commonPaymentUrl;
	}

	public String getB2bPaymentUrl() {
		return b2bPaymentUrl;
	}

	public void setB2bPaymentUrl(String b2bPaymentUrl) {
		this.b2bPaymentUrl = b2bPaymentUrl;
	}

	public String getB2cPaymentUrl() {
		return b2cPaymentUrl;
	}

	public void setB2cPaymentUrl(String b2cPaymentUrl) {
		this.b2cPaymentUrl = b2cPaymentUrl;
	}
}
