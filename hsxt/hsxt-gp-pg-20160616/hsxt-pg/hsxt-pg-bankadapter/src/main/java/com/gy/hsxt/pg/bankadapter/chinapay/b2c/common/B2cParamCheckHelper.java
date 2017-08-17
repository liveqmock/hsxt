/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.common;

import java.math.BigInteger;
import java.util.Date;

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.params.ChinapayRefundParam;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.StringParamChecker;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.common
 * 
 *  File Name       : B2cParamCheckHelper.java
 * 
 *  Creation Date   : 2014-11-17
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2c传递的参数值校验帮助类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class B2cParamCheckHelper {
	/**
	 * 校验退款参数
	 * 
	 * @param params
	 * @return
	 */
	public static boolean checkRefundParams(ChinapayRefundParam params) {
		// 原订单号
		String orderNo = params.getOrderNo();
		// 退款金额，全额退款则为原交易金额 (单位：分)
		BigInteger refundAmount = params.getRefundAmount();
		// 原交易日期(格式： 20070801)
		Date translateDate = params.getTranslateDate();

		StringParamChecker.check("orderNo", orderNo, 16, 16);

		if (0 >= refundAmount.longValue()) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM, "金额必须大于0！");
		}

		if (null == translateDate) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM, "订单日期不能为空！");
		}

		return true;
	}

	/**
	 * 校验订单状态请求参数
	 * 
	 * @param orderNo
	 * @param orderDate
	 * @return
	 */
	public static boolean checkOrderStateParam(String orderNo, Date orderDate) {
		StringParamChecker.check("orderNo", orderNo, 16, 16);

		if (null == orderDate) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM, "订单日期不能为空！");
		}

		return true;
	}
}
