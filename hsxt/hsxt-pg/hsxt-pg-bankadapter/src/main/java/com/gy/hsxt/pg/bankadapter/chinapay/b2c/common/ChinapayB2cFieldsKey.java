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
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.common
 * 
 *  File Name       : ChinapayB2cFieldsKey.java
 * 
 *  Creation Date   : 2015年10月12日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : B2c请求字段常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayB2cFieldsKey {

	/** 支付请求参数定义 **/
	public static interface PayReqParamKey {
		public static final String MER_ID = "MerId";
		public static final String ORDER_ID = "OrdId";
		public static final String TRANS_AMT = "TransAmt";
		public static final String CURY_ID = "CuryId";
		public static final String TRANS_DATE = "TransDate";
		public static final String TRANS_TYPE = "TransType";
		public static final String VERSION = "Version";
		public static final String BG_RET_URL = "BgRetUrl";
		public static final String PAGE_RET_URL = "PageRetUrl";
		public static final String PRIV1 = "Priv1";
		public static final String CHK_VALUE = "ChkValue";
		public static final String GATE_ID = "GateId";
		public static final String CLIENT_IP = "ClientIp";
		public static final String EXT_FLAG = "ExtFlag";
	}

	/** 支付回调的请求参数定义 **/
	public static interface PayCallbackParamKey {
		public static final String MER_ID = "merid"; // 15
		public static final String ORDER_NO = "orderno";// 16
		public static final String AMOUNT = "amount";// 12
		public static final String CURRENCY_CODE = "currencycode";// 3
		public static final String TRANS_DATE = "transdate";// 8
		public static final String TRANS_TYPE = "transtype";// 4
		public static final String STATUS = "status"; // status表示交易状态只有"1001"表示支付成功，其他状态均表示未成功的交易。因此验证签名数据通过后，还需要判定交易状态代码是否为"1001"
		public static final String PRIV1 = "Priv1";
		public static final String CHECK_VALUE = "checkvalue";
	}
	
	/** 退款的请求参数定义 **/
	public static interface RefundReqParamKey {
		public static final String MER_ID = "MerID";
		public static final String ORDER_ID = "OrderId";
		public static final String REFUND_AMOUNT = "RefundAmount";
		public static final String TRANS_DATE = "TransDate";
		public static final String TRANS_TYPE = "TransType";
		public static final String VERSION = "Version";
		public static final String RETURN_URL = "ReturnURL";
		public static final String PRIV1 = "Priv1";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** 退款回调的请求参数定义 **/
	public static interface RefundCallbackParamKey {
		public static final String RESP_CODE = "ResponseCode";
		public static final String ERR_MSG = "Message";
		public static final String MER_ID = "MerID";
		public static final String ORDER_ID = "OrderId";
		public static final String TRANS_TYPE = "TransType";
		public static final String REFUND_AMOUT = "RefundAmout";
		public static final String PROCESS_DATE = "ProcessDate";
		public static final String STATUS = "Status";
		public static final String PRIV1 = "Priv1";
		public static final String CHK_VALUE = "CheckValue";
	}

	/** 查询订单状态请求参数定义 **/
	public static interface OrderStateReqKey {
		public static final String MER_ID = "MerId";
		public static final String ORDER_ID = "OrdId";
		public static final String TRANS_DATE = "TransDate";
		public static final String TRANS_TYPE = "TransType";
		public static final String VERSION = "Version";
		public static final String RESV = "Resv";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** 查询订单状态响应参数定义 **/
	public static interface OrderStateRespKey {
		public static final String RESP_CODE = "ResponeseCode";
		public static final String MER_ID = "merid";
		public static final String ORDER_NO = "orderno";
		public static final String AMOUNT = "amount";
		public static final String CCY_CODE = "currencycode";
		public static final String TRANS_DATE = "transdate";
		public static final String TRANS_TYPE = "transtype";
		public static final String STATUS = "status";
		public static final String CHK_VALUE = "checkvalue";
	}
}
