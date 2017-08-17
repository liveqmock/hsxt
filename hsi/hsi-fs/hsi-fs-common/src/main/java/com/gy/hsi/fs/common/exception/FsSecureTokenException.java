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
 *  File Name       : FsSecureTokenException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 安全令牌无效异常[令牌为空或过期]
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsSecureTokenException extends FsException {

	private static final long serialVersionUID = -7153307165322914063L;

	public FsSecureTokenException() {
	}

	public FsSecureTokenException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsSecureTokenException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsSecureTokenException(Throwable throwable) {
		super(throwable);
	}

	@Override
	public int getErrorCode() {
		return FileOptResultCode.SECURE_TOKEN_INVALID.getValue();
	}
}