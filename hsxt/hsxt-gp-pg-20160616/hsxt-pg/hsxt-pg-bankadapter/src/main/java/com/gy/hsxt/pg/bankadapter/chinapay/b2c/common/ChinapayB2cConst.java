/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.common;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.common
 * 
 *  File Name       : ChinapayB2cConst.java
 * 
 *  Creation Date   : 2014-10-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C常量定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayB2cConst {
	/** 银联支付接口版本号 **/
	public static final String CHINAPAY_PAY_VERSION = "20100304";

	/** 银联订单查询接口版本号 **/
	public static final String CHINAPAY_QRY_VERSION = "20070129";

	/** 银联退款接口版本号 **/
	public static final String CHINAPAY_REFUND_VERSION = "20070129";

	/** 银联查询退款版本号 **/
	// private static final String CHINAPAY_QRY_REFUND_VERSION = "20060831";

	/** HTTPS方式 **/
	public static final String HTTP_SSL = "SSL";

	/** 通用商户id **/
	public static final String PUB_MERID = "999999999999999";

	/** B2C、NC、UPOP交易类型 **/
	public static interface BankTransType {
		/** 支付交易 **/
		public static final String PAY = "0001";

		/** 退款交易 **/
		public static final String REFUND = "0002";

		/** 退款撤销交易 **/
		public static final String REFUND_CANCEL = "0005";
	}

	/** 货币枚举 **/
	public static interface CurrencyEnum {
		/** 156：人民币 **/
		public static final String RMB = "156";
	}

	/** 交易状态：1001消费交易成功 1003退款提交成功 1005退款撤销成功 **/
	public static interface BankTransStatus {

		/** 支付成功 **/
		public static final String PAY_SUCCESS = "1001";

		/** 退款提交成功 **/
		public static final String REFUND_SUCCESS = "1003";

		/** 退款撤销成功 **/
		public static final String REFUND_CANCEL_SUCCESS = "1005";
	}

	/** 银联查询订单状态响应码 **/
	public static interface CpRespCode {
		/** 0-请求成功 **/
		public static final String REQ_SUCCESS_0 = "0";

		/** {ResponeseCode=305, Message=超出流量控制范围!} **/
		public static final String EXCEED_FLOW_LIMIT_305 = "305";

		/** {ResponeseCode=307, Message=未查询到匹配数据} **/
		public static final String DATA_NOT_EXIST_307 = "307";

		/** {ResponeseCode=205, Message=重复提交该笔交易} **/
		public static final String DUPLICATE_RECORD_205 = "205";

		/** {ResponeseCode=402, Message=原始记录不存在, 无法退款操作} **/
		public static final String ORI_DATA_NOT_EXIST_402 = "402";
	}

	/** 银联退款结果状态码: 1-退款提交成功, 3-退款成功, 8-退款失败 **/
	public static interface CpRefundStatus {
		/** 1-退款提交成功 **/
		public static final String PROCESSING_1 = "1";

		/** 3-退款成功 **/
		public static final String SUCCESS_3 = "3";

		/** 8-退款失败 **/
		public static final String FAILED_8 = "8";
	}

	/** 中国银联B2C对账通知相关的请求参数定义 **/
	public static interface CpNotifyParam {
		/** 对账文件的下载路径参数 **/
		public static final String PARAM_DOWNLOAD = "download";

		/** 银联'每日对账'请求地址匹配的正则表达式 [B2C、NC通用] **/
		// public static final String CPNOTIFY_URL_REGEX =
		// "^http://[^/|^\\s]{1,}/cpnotify\\?download=\\S{1,}\\.txt$";
		public static final String CPNOTIFY_URL_REGEX = "^http://[^/|^\\s]{1,}\\S{1,}/cpnotify\\?download=\\S{1,}\\.txt$";
	}
}
