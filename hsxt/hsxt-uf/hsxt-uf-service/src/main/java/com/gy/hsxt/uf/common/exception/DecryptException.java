/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.exception;

import com.gy.hsxt.uf.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.exception
 * 
 *  File Name       : DecryptException.java
 * 
 *  Creation Date   : 2015-10-10
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 解密失败异常类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DecryptException extends Exception {
	private static final long serialVersionUID = -5765152940120668401L;

	private int errorCode = -1;
	private String errorMsg = "";

	public DecryptException() {
	}

	public DecryptException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public DecryptException(int errorCode, String errorMsg) {
		super(errorMsg);

		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	public DecryptException(int errorCode, String errorMsg, Throwable throwable) {
		this(errorCode, errorMsg);
	}

	public DecryptException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public DecryptException(Throwable throwable) {
		super(throwable);
	}

	@Override
	public String getMessage() {
		if (!StringUtils.isEmpty(errorMsg)) {
			return format(errorMsg);
		}

		return format(super.getMessage());
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		String errorMessage = this.getMessage();

		return format(errorMessage);
	}

	/**
	 * 添加日志提示前缀
	 * 
	 * @param logMsg
	 * @return
	 */
	private String format(String logMsg) {
		return logMsg;
	}
}