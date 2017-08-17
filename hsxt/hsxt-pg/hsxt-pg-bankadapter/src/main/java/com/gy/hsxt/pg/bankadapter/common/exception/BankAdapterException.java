/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.exception;

import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.exception
 * 
 *  File Name       : BankAdapterException.java
 * 
 *  Creation Date   : 2015-09-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : PGBankAdapterException异常类定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class BankAdapterException extends IllegalArgumentException {
	private static final long serialVersionUID = 186647518409265960L;

	protected ErrorCode errorCode = ErrorCode.FAILED;
	protected String errorMsg = "";
	protected String bankErrorCode;

	public BankAdapterException(String errorMsg) {
		this(ErrorCode.FAILED, errorMsg);
	}

	public BankAdapterException(ErrorCode errorCode, String errorMsg) {
		this(errorCode, errorMsg, null);
	}

	public BankAdapterException(ErrorCode errorCode, String errorMsg,
			Throwable throwable) {

		super(StringHelper.decodeUrlString(errorMsg), throwable);

		this.setErrorCode(errorCode);

		this.setErrorMsg(errorMsg);
	}

	public String getMessage(boolean isWithPrefix) {

		if (isWithPrefix) {
			return this.getMessage();
		}

		return super.getMessage();
	}

	@Override
	public String getMessage() {

		if (!StringHelper.isEmpty(errorMsg)) {
			return errorMsg;
		}

		return super.getMessage();
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		if(null != errorCode) {
			this.errorCode = errorCode;
		}
	}

	public String getBankErrorCode() {
		return bankErrorCode;
	}

	public void setBankErrorCode(String bankErrorCode) {
		this.bankErrorCode = bankErrorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = StringHelper.decodeUrlString(errorMsg);
	}

	public String getErrorMsg() {
		return this.getMessage();
	}
}