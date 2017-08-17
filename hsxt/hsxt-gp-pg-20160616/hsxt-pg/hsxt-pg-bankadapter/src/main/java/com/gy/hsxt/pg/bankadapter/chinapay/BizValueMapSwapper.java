/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay;

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.common.UpacpConst.UpacpBankTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upmp.common.UpmpConst.UpmpBankTransType;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay
 * 
 *  File Name       : BizValueMapSwapper.java
 * 
 *  Creation Date   : 2015年10月15日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 值映射转换器
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class BizValueMapSwapper {
	/**
	 * 将银行的状态转换为ufe的'订单'类型
	 * 
	 * @param bankTransType
	 * @return
	 */
	public static int toPgTransType(String bankTransType) {
		
		// ******************重要： 将银行的状态转换为网关的'订单'状态 **********************
		// B2C/NC/UPMP/UPOP, 支付业务, (UPOP和B2C共用一套)
		if (BankTransType.PAY.equals(bankTransType)
				|| UpmpBankTransType.PAY.equals(bankTransType)
				|| UpacpBankTransType.PAY.equals(bankTransType)) {
			return ChinapayOrderTransType.TYPE_PAY.getValue();
		}

		// B2C/NC/UPMP/UPOP, 退款业务, (UPOP和B2C共用一套)
		if (BankTransType.REFUND.equals(bankTransType)
				|| UpmpBankTransType.REFUND.equals(bankTransType)
				|| UpacpBankTransType.REFUND.equals(bankTransType)) {
			return ChinapayOrderTransType.TYPE_REFUND.getValue();
		}

		return ChinapayOrderTransType.UNKNOWN.getValue();
	}

	/**
	 * 将支付网关的状态转换为银行的'订单'状态(B2C/NC/UPOP)
	 * 
	 * @param pgTransType
	 * @return
	 */
	public static String toBankTransType(int pgTransType) {
		// ******************重要： 将支付网关的状态转换为银行的'订单'状态 **********************

		// B2C/NC/UPOP, 支付业务(UPOP和B2C共用一套)
		if (ChinapayOrderTransType.TYPE_PAY.getValue() == pgTransType) {
			return BankTransType.PAY;
		}

		// B2C/NC/UPOP, 退款业务(UPOP和B2C共用一套)
		if (ChinapayOrderTransType.TYPE_REFUND.getValue() == pgTransType) {
			return BankTransType.REFUND;
		}

		return BankTransType.PAY;
	}

	/**
	 * 将支付网关的状态转换为银行的'订单'状态 (UPMP)
	 * 
	 * @param pgTransType
	 * @return
	 */
	public static String toUpmpBankTransType(int pgTransType) {
		// ******************重要： 将支付网关的状态转换为银行的'订单'状态 **********************

		// UPMP, 支付业务
		if (ChinapayOrderTransType.TYPE_PAY.getValue() == pgTransType) {
			return UpmpBankTransType.PAY;
		}

		// UPMP, 退款业务
		if (ChinapayOrderTransType.TYPE_REFUND.getValue() == pgTransType) {
			return UpmpBankTransType.REFUND;
		}

		return UpmpBankTransType.PAY;
	}
	
	/**
	 * 将支付网关的状态转换为银行的'订单'状态 (UPACP)
	 * 
	 * @param pgTransType
	 * @return
	 */
	public static String toUpacpBankTransType(int pgTransType) {
		// ******************重要： 将支付网关的状态转换为银行的'订单'状态 **********************

		// UPACP, 支付业务
		if (ChinapayOrderTransType.TYPE_PAY.getValue() == pgTransType) {
			return UpacpBankTransType.PAY;
		}

		// UPACP, 退款业务
		if (ChinapayOrderTransType.TYPE_REFUND.getValue() == pgTransType) {
			return UpacpBankTransType.REFUND;
		}

		return UpacpBankTransType.PAY;
	}
}