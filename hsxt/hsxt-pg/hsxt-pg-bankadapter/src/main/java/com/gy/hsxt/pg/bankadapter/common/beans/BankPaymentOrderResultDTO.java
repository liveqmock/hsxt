package com.gy.hsxt.pg.bankadapter.common.beans;

import java.math.BigInteger;
import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.IPaymentResultAware;
import com.gy.hsxt.pg.bankadapter.common.constants.PaymentOrderStatus;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

public class BankPaymentOrderResultDTO implements IPaymentResultAware {
	
	// 银联流水号|银联订单时间(即:交易时间)|订单号|银联交易类型|交易金额|交易状态
	// 必填 商户订单号
	private String orderNo;

	// 必填, 订单状态：
	// -1: 未知,
	// -2: 无效订单,
	// 100: 支付成功,
	// 101: 支付失败,
	// 102: 支付处理中,
	// 103: 支付取消成功,
	// 200： 退款成功,
	// 201： 退款失败,
	// 202：退款处理中,
	// 203： 退款取消成功
	private PaymentOrderStatus orderStatus = PaymentOrderStatus.UNKNOWN;

	// 支付金额(单位： 分)
	private BigInteger payAmount;

	// 银行结果响应码(只有快捷支付使用)
	private String bankRespCode;

	// 币种
	private String crrrency;

	// 交易类型
	private String transType;

	// 交易时间
	private Date tranTime;

	// 银行流水号(当是银联UPMP/UPACP时就等同于bankQueryId或QN字段功能)
	private String bankSeqNo;

	// 错误信息描述
	private String errorInfo;

	// 私有域
	private String priv1;

	public String getPriv1() {
		return priv1;
	}

	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}

	public BankPaymentOrderResultDTO() {
	}

	public BankPaymentOrderResultDTO(String orderNo,
			PaymentOrderStatus defaultStatus) {
		this.orderNo = orderNo;

		if (null != defaultStatus) {
			this.orderStatus = defaultStatus;
		}
	}

	public BankPaymentOrderResultDTO(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCrrrency() {
		return crrrency;
	}

	public void setCrrrency(String crrrency) {
		this.crrrency = crrrency;
	}

	public String getBankSeqNo() {
		return bankSeqNo;
	}

	public void setBankSeqNo(String bankSeqNo) {
		this.bankSeqNo = bankSeqNo;
	}

	public String getBankRespCode() {
		return bankRespCode;
	}

	public void setBankRespCode(String bankRespCode) {
		this.bankRespCode = bankRespCode;
	}

	public BigInteger getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigInteger payAmount) {
		this.payAmount = payAmount;
	}

	public void setPayAmount(String payAmount) {
		if (!StringHelper.isEmpty(payAmount)) {
			this.payAmount = new BigInteger(payAmount);
		}
	}

	public Date getTranTime() {
		return tranTime;
	}

	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(int pgTransType) {
		this.transType = String.valueOf(pgTransType);
	}

	public PaymentOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(PaymentOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}
