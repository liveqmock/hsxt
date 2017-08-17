/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.constant
 * 
 *  File Name       : PGConstant.java
 * 
 *  Creation Date   : 2015年9月24日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 常量类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGConstant {
	/** utf-8编码 **/
	public static final String ENCODING_UTF8 = "UTF-8";

	/** 交易类型枚举值定义 **/
	public static interface TransType {
		/** 10:支付 **/
		public static final int PAYMENT = 10;

		/** 20:退款 **/
		public static final int REFUND = 20;

		/** 30:提现 **/
		public static final int TRANS_CASH = 30;
	}

	/** 支付渠道 **/
	public static interface PGPayChannel {
		/** 100-网银支付 **/
		public static final int B2C = 100;

		/** 101-手机移动支付(包括UPMP、UPACP) **/
		public static final int UPMP = 101;

		/** 102-快捷支付 **/
		public static final int UPOP = 102;
	}

	/** 支付结果枚举值定义 **/
	public static interface PGPaymentStateCode {
		/** -1:待支付/待退款，待处理的其他业务 **/
		public static final int READY = -1;

		/** -2:无效 **/
		public static final int INVALID = -2;

		/** 100:支付成功 **/
		public static final int PAY_SUCCESS = 100;

		/** 101:支付失败 **/
		public static final int PAY_FAILED = 101;

		/** 102:支付处理中 **/
		public static final int PAY_HANDLING = 102;

		/** 200:退款成功 **/
		public static final int REFUND_SUCCESS = 200;

		/** 201:退款失败 **/
		public static final int REFUND_FAILED = 201;

		/** 202:退款处理中 **/
		public static final int REFUND_HANDLING = 202;
	}

	/** 提现结果枚举值定义 **/
	public static interface PGTransStateCode {
		/** -1:待提交银行 **/
		public static final int READY = -1;
		
		/** -99:交易错误 **/
		public static final int TRANS_ERROR = -99;

		/** 1000:提现成功 **/
		public static final int TRANS_SUCCESS = 1000;

		/** 1001:提现失败 **/
		public static final int TRANS_FAILED = 1001;

		/** 1002:银行处理中 **/
		public static final int BANK_HANDLING = 1002;

		/** 1003:被退票 **/
		public static final int REFUNDED = 1003;
	}

	/** 异常错误码定义 [35000 - 35999] **/
	public static interface PGErrorCode {
		/** 35000:请求参数无效 **/
		public static final int INVALID_PARAM = 35000;

		/** 35001:内部处理错误 **/
		public static final int INNER_ERROR = 35001;

		/** 35002:支付网关与银行间通信错误 **/
		public static final int NET_ERROR = 35002;
		
		/** 35003:已经在处理中, 不能重复提交 **/
		public static final int REPEAT_SUBMIT = 35003;

		/** 35100:银行支付单号重复 **/
		public static final int DATA_EXIST = 35100;

		/** 35101:银行支付单号不存在 **/
		public static final int DATA_NOT_EXIST = 35101;
	}

	/** 商户类型 **/
	public static interface MerType {
		/** 100-互生线(默认) **/
		public static final int HSXT = 100;

		/** 101-互商线 **/
		public static final int HSEC = 101;

		/** 200-第三方接入商户（预留，指淘宝、京东等） **/
		public static final int THIRD_PARTNER = 200;
	}
	
	/** 银行卡类型 **/
	public static interface BankCardType {
		/** 1：借记卡; **/
		public static final int DEBIT_CARD = 1;

		/** 2：贷记卡 **/
		public static final int CREDIT_CARD = 2;
	}

	/** 非首次支付请求url参数key **/
	public static interface SecondQuickPayReqKey {
		public static final String MER_TYPE = "merType";
		public static final String BANK_ORDERNO = "bankOrderNo";
		public static final String BINDING_NO = "bindingNo";
		public static final String SMS_CODE = "smsCode";
	}

	/** 支付网关jump时传递的响应参数key **/
	public static interface PgCallbackJumpRespKey {
		public static final String ORDER_NO = "orderNo";
		public static final String ORDER_DATE = "orderDate";
		public static final String TRANS_AMOUNT = "transAmount";
		public static final String TRANS_STATUS = "transStatus";
		public static final String CURRENCY_CODE = "currencyCode";
		public static final String PAY_CHANNEL = "payChannel";
		public static final String FAIL_REASON = "failReason";
	}
	
	/** 货币代码 **/
	public static interface PGCurrencyCode {
		/** CNY:人民币 **/
		public static final String CNY = "CNY";
	}
}
