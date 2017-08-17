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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.exception
 * 
 *  File Name       : FsServerNotAvailableException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 服务器不可用异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsServerNotAvailableException extends FsException {

	private static final long serialVersionUID = -2400206881898241816L;

	public FsServerNotAvailableException() {
	}

	public FsServerNotAvailableException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsServerNotAvailableException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsServerNotAvailableException(Throwable throwable) {
		super(throwable);
	}

	@Override
	public int getErrorCode() {
		return FileOptResultCode.SERVER_NOT_AVAILABLE.getValue();
	}
}