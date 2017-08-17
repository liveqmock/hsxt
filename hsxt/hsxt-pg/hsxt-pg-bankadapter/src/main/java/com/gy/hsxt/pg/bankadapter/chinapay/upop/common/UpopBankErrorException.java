/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.common;

import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.common
 * 
 *  File Name       : UpopBankErrorException.java
 * 
 *  Creation Date   : 2016年5月12日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : UPOP快捷支付异常
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class UpopBankErrorException extends BankAdapterException {

	private static final long serialVersionUID = -338102926213954906L;

	public UpopBankErrorException(String errorMsg) {
		this(null, errorMsg);
	}

	public UpopBankErrorException(ErrorCode errorCode, String errorMsg) {
		super(errorCode, errorMsg);

		setBankErrorCode(UpopRespCode.OPT_FAILED.getCode());
	}
}
