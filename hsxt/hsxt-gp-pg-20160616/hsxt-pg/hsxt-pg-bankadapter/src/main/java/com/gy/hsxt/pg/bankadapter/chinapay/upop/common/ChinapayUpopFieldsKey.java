/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.common;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.common
 * 
 *  File Name       : ChinapayUpopFieldsKey.java
 * 
 *  Creation Date   : 2015年10月12日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : Upop请求字段常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayUpopFieldsKey {
	/** 签约号查询请求字段定义 **/
	public static interface BindingNoQryReqKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String CARD_NO = "CardNo";
		public static final String BANK_ID = "BankId";
		public static final String CARD_TYPE = "CardType";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** 签约号查询响应字段定义 **/
	public static interface BindingNoQryRespKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String STAT = "Stat";
		public static final String CARD_NO = "CardNo";
		public static final String BINDING_NO = "BindingNo";
		public static final String EXPIRY = "expiry";
		public static final String TRANS_LIMIT = "transLimit";
		public static final String SUM_LIMIT = "sumLimit";
		public static final String CHK_VALUE = "ChkValue";
		public static final String RESP_CODE = "RespCode";
		public static final String ISSUER_CODE = "IssuerCode";
	}

	/** [首次支付]请求字段定义 **/
	public static interface PayFirstReqParamKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String TRANS_TYPE = "TransType";
		public static final String TRANS_DATE = "TransDate";
		public static final String ORDER_ID = "OrdId";
		public static final String SUB_TRANS_TYPE = "SubTransType";
		public static final String CARD_NO = "CardNo";
		public static final String CARD_TYPE = "CardType";
		public static final String BANK_ID = "BankId";
		public static final String TRANS_AMT = "TransAmt";
		public static final String CURY_ID = "CuryId";
		public static final String VERSION = "Version";
		public static final String PAGE_RET_URL = "PageRetUrl";
		public static final String BG_RET_URL = "BgRetUrl";
		public static final String PRIV1 = "Priv1";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** 短信验证码请求字段定义 **/
	public static interface SmsCodeReqKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String TRANS_TYPE = "TransType";
		public static final String ORDER_ID = "OrdId";
		public static final String TRANS_DATE = "TransDate";
		public static final String TRANS_AMT = "TransAmt";
		public static final String CURY_ID = "CuryId";
		public static final String VERSION = "Version";
		public static final String PAGE_RET_URL = "PageRetUrl";
		public static final String BG_RET_URL = "BgRetUrl";
		public static final String PRIV1 = "Priv1";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** 短信验证码响应字段定义 **/
	public static interface SmsCodeRespKey {
		public static String RESP_CODE = "RespCode";

		public static String MER_ID = "MerId";
		public static String GATE_ID = "GateId";
		public static String TRANS_TYPE = "TransType";
		public static String ORDER_ID = "OrdId";
		public static String TRANS_DATE = "TransDate";
		public static String SUB_TRANS_TYPE = "SubTransType";
		public static String CHK_VALUE = "ChkValue";
	}

	/** [非首次支付]同步请求字段定义 **/
	public static interface PaySecondReqKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String TRANS_TYPE = "TransType";
		public static final String ORDER_ID = "OrdId";
		public static final String TRANS_DATE = "TransDate";
		public static final String TRANS_AMT = "TransAmt";
		public static final String CURY_ID = "CuryId";
		public static final String SUB_TRANS_TYPE = "SubTransType";
		public static final String BINDING_NO = "BindingNo";
		public static final String SMS = "SMS";
		public static final String VERSION = "Version";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** [非首次支付]同步响应字段定义 **/
	public static interface PaySecondRespKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String TRANS_TYPE = "TransType";
		public static final String ORDER_ID = "OrdId";
		public static final String TRANS_DATE = "TransDate";
		public static final String TRANS_AMT = "TransAmt";
		public static final String CURY_ID = "CuryId";
		public static final String SUB_TRANS_TYPE = "SubTransType";
		public static final String RESP_CODE = "RespCode";
		public static final String TRANS_STAUT = "TransStaut";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** [非首次支付]同步请求字段定义 **/
	public static interface CardBindingReqParamKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String TRANS_TYPE = "TransType";
		public static final String TRANS_DATE = "TransDate";
		public static final String SUB_TRANS_TYPE = "SubTransType";
		public static final String CARD_NO = "CardNo";
		public static final String CARD_TYPE = "CardType";
		public static final String BANK_ID = "BankId";
		public static final String BG_RET_URL = "BgRetUrl";
		public static final String CHK_VALUE = "ChkValue";
	}

	/** [非首次支付]同步响应字段定义 **/
	public static interface CardBindingCbParamKey {
		public static final String MER_ID = "MerId";
		public static final String GATE_ID = "GateId";
		public static final String TRANS_TYPE = "TransType";
		public static final String SUB_TRANS_TYPE = "SubTransType";
		public static final String STAT = "Stat";
		public static final String CARD_NO = "CardNo";
		public static final String BINDING_NO = "BindingNo";
		public static final String EXPIRY = "expiry";
		public static final String SUM_LIMIT = "sumLimit";
		public static final String TRANS_LIMIT = "transLimit";
		public static final String CHK_VALUE = "ChkValue";
	}
}
