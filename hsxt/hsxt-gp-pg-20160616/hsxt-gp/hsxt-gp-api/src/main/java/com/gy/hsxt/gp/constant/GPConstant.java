/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.constant
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
public class GPConstant {
	/** utf-8编码 **/
	public static final String ENCODING_UTF8 = "UTF-8";
	
	/** 通用付款账户 **/
	public static final int COMMON_OUT_BANK_ACC_TYPE = 100;

	/** 交易类型枚举值定义 **/
	public static interface GPTransType {
		/** 10:支付交易 **/
		public static final int PAYMENT = 10;
		
		/** 20:退款交易 **/
		public static final int REFUND = 20;
		
		/** 30:提现 **/
		public static final int TRANS_CASH = 30;
	}

	/** 支付渠道 **/
	public static interface PayChannel {
		/** 100-网银支付 **/
		public static final int B2C = 100;

		/** 101-手机移动支付 **/
		public static final int UPMP = 101;

		/** 102-快捷支付 **/
		public static final int UPOP = 102;
	}

	/** 支付结果枚举值定义 **/
	public static interface PaymentStateCode {
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
	public static interface TransStateCode {
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

	/** 异常错误码定义 [15000 - 15999] **/
	public static interface GPErrorCode {
		/** 15000:请求参数无效 **/
		public static final int INVALID_PARAM = 15000;

		/** 15001:内部处理错误 **/
		public static final int INNER_ERROR = 15001;

		/** 15002:支付系统与支付网关间通信错误 **/
		public static final int NET_ERROR = 15002;

		/** 15003:商户号错误 **/
		public static final int MER_NO_ERROR = 15003;

		/** 15100:已经处理成功，不能重复提交 **/
		public static final int HAS_SUCCESS = 15100;

		/** 15101:正在处理中，不能重复提交 **/
		public static final int BE_HANDLING = 15101;

		/** 15102:记录不存在 **/
		public static final int DATA_NOT_EXIST = 15102;

		/** 15103:该银行卡已经签约，不能重复签约 **/
		public static final int REPEAT_BINDING = 15103;
		
		/** 15104:重复提交 **/
		public static final int REPEAT_SUBMIT = 15104;
	}

	/** 银行卡类型 **/
	public static interface BankCardType {
		/** 1：借记卡; **/
		public static final int DEBIT_CARD = 1;

		/** 2：贷记卡 **/
		public static final int CREDIT_CARD = 2;
	}

	/** 商户号 **/
	public static interface MerId {
		/** 互生线(默认) **/
		public static final String HS = "1100000007";

		/** 互商线 **/
		public static final String HSEC = "1100000001";
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
	
	/** 转账加急标志 **/
	public static interface GPTransSysFlag {
		/** 1-大额(>=5万)，等同Y **/
		public static final String LARGE_AMOUNT = "1";
		
		/** 2-小额(<5万)，等同N **/
		public static final String SMALL_AMOUNT = "2";

		/** S：特急 **/
		public static final String MOST_URGENT = "S";

		/** Y：加急 **/
		public static final String MORE_URGENT = "Y";
		
		/** N：普通（默认） **/
		public static final String URGENT = "N";
	}
	
	/** 货币代码 **/
	public static interface GPCurrencyCode {
		/** CNY:人民币 **/
		public static final String CNY = "CNY";
	}
}
