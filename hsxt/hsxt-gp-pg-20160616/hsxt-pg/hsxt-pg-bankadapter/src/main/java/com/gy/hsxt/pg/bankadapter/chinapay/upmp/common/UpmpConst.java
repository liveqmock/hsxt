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
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp.common
 * 
 *  File Name       : UpmpConst.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : Upmp常量类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpmpConst {
	
	/** 版本号 **/
	public static final String CHINAPAY_SDK_VERSION = "1.0.0";
	
	/** UPMP手机支付交易类型 **/
	public static interface UpmpBankTransType {
		/** UPMP支付交易 **/
		public static final String PAY = "01";
		
		/** UPMP预授权 **/
		public static final String PRE_AUTH = "02";

		/** UPMP退款交易 **/
		public static final String REFUND = "04";
	}
	
	/** 银联手机支付响应码 **/
	public static interface CpUpmpRespCode {
		/** 00-请求成功 **/
		public static final String REQ_SUCCESS_00 = "00";

		/** 01-请求发生错误 **/
		public static final String REQ_ERR_01 = "01";
		
		/** 42-交易超限, 不可重复提交 **/
		public static final String REQ_OVER_LIMIT_42 = "42";
	}
	
	/** 银联手机支付交易状态码 **/
	public static interface CpUpmpTransStatus {
		/** 00-交易成功 **/
		public static final String TRANS_SUCCESS_00 = "00";

		/** 01-交易处理中 **/
		public static final String TRANS_PROCESSING_01 = "01";
	}
}
