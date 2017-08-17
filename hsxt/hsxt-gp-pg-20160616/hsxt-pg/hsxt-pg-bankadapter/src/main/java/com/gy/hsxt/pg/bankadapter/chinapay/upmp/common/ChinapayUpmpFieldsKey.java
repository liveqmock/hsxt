/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upmp.common;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp.common
 * 
 *  File Name       : ChinapayUpmpFieldsKey.java
 * 
 *  Creation Date   : 2015年10月12日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : Upmp请求字段常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayUpmpFieldsKey {

	/** 支付的请求参数定义 **/
	public static interface PayReqParamKey {
		public static final String VERSION = "version";
		public static final String CHARSET = "charset";
		public static final String TRANS_TYPE = "transType";
		public static final String MER_ID = "merId";
		public static final String BACK_END_URL = "backEndUrl";
		public static final String FRONT_END_URL = "frontEndUrl";
		public static final String ORDER_DESC = "orderDescription";
		public static final String ORDER_TIME = "orderTime";
		public static final String ORDER_TIMEOUT = "orderTimeout";
		public static final String ORDER_NUM = "orderNumber";
		public static final String ORDER_AMOUNT = "orderAmount";
		public static final String ORDER_CURRENCY = "orderCurrency";
		public static final String REQ_RESERVED = "reqReserved";
		public static final String TEST = "test";
		public static final String MER_RESERVED = "merReserved";
	}

	/** 支付的请求参数定义 **/
	public static interface PayRespParamKey {
		public static final String RESP_CODE = "respCode";
		public static final String CHARSET = "charset";
		public static final String TN = "tn";
	}

	/** 支付回调的请求参数定义 **/
	public static interface PayCallbackParamKey {
		public static final String ORDER_NUMBER = "orderNumber";
		public static final String ORDER_TIME = "orderTime";
		public static final String SETTLE_CURRENCY = "settleCurrency";
		public static final String SETTLE_AMOUNT = "settleAmount";
		public static final String TRANS_TYPE = "transType";
		public static final String QN = "qn";
		public static final String TRANS_STATUS = "transStatus";
	}

	/** 退款的请求参数定义 **/
	public static interface RefundReqParamKey {
		public static final String VERSION = "version";
		public static final String CHARSET = "charset";
		public static final String TRANS_TYPE = "transType";
		public static final String MER_ID = "merId";
		public static final String BACK_END_URL = "backEndUrl";
		public static final String ORDER_TIME = "orderTime";
		public static final String ORDER_NUM = "orderNumber";
		public static final String ORDER_AMT = "orderAmount";
		public static final String QN = "qn ";

		public static final String REQ_RESERVED = "reqReserved";
		public static final String TEST = "test";
		public static final String MER_RESERVED = "merReserved";
	}

	/** 退款的响应参数定义 **/
	public static interface RefundRespParamKey {
		public static final String RESP_CODE = "respCode";
		public static final String RESP_MSG = "respMsg";
		public static final String CHARSET = "charset";
	}

	/** 退款回调的请求参数定义 **/
	public static interface RefundCallbackParamKey {
		public static final String ORDER_NUMBER = "orderNumber";
		public static final String ORDER_TIME = "orderTime";
		public static final String SETTLE_CURRENCY = "settleCurrency";
		public static final String SETTLE_AMOUNT = "settleAmount";
		public static final String TRANS_TYPE = "transType";
		public static final String QN = "qn";
		public static final String TRANS_STATUS = "transStatus";
	}

	/** 查询订单状态请求参数定义 **/
	public static interface OrderStateReqKey {
		public static final String VERSION = "version";
		public static final String CHARSET = "charset";
		public static final String TRANS_TYPE = "transType";
		public static final String MER_ID = "merId";
		public static final String ORDER_TIME = "orderTime";
		public static final String ORDER_NUM = "orderNumber";
		public static final String TEST = "test";
		public static final String MER_RESERVED = "merReserved";
	}

	/** 查询订单状态响应参数定义 **/
	public static interface OrderStateRespKey {
		public static final String RESP_CODE = "respCode";
		public static final String RESP_MSG = "respMsg";
		public static final String CHARSET = "charset";
		public static final String ORDER_TIME = "orderTime";
		public static final String AMOUNT = "settleAmount";
		public static final String CURRENCY = "settleCurrency";
		public static final String TRANS_STATUS = "transStatus";
		public static final String ORDER_NUM = "orderNumber";
	}
}
