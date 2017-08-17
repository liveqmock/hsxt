/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.exception;

import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.exception
 * 
 *  File Name       : FsException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统异常类定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsException extends Exception {

	private static final long serialVersionUID = -7249572867399040181L;

	protected int errorCode = FileOptResultCode.OPT_FAILED.getValue();
	protected String errorMsg = "";

	public FsException() {
	}

	public FsException(String errorMsg) {
		super(errorMsg);
		this.setErrorMsg(errorMsg);
	}

	public FsException(int errorCode, String errorMsg) {
		super(errorMsg);
		this.setErrorMsg(errorMsg);
		this.setErrorCode(errorCode);
	}

	public FsException(int errorCode, String errorMsg, Throwable throwable) {

		if (!StringUtils.isEmpty(errorMsg)) {
			this.setErrorMsg(errorMsg);
		} else {
			this.setErrorMsg(throwable.getMessage());
		}

		this.setErrorCode(errorCode);
		this.initCause(throwable);
	}

	public FsException(String errorMsg, Throwable throwable) {
		if (!StringUtils.isEmpty(errorMsg)) {
			this.setErrorMsg(errorMsg);
		} else {
			this.setErrorMsg(throwable.getMessage());
		}

		this.initCause(throwable);
	}

	public FsException(Throwable throwable) {
		this.initCause(throwable);
		this.setErrorMsg(throwable.getMessage());
	}

	@Override
	public String getMessage() {
		if (!StringUtils.isEmpty(errorMsg)) {
			return errorMsg;
		}

		return super.getMessage();
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return this.getMessage();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}