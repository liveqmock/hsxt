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
 *  File Name       : FsFileExpiredException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件过期异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsFileExpiredException extends FsException {

	private static final long serialVersionUID = 7128109940764259538L;

	public FsFileExpiredException() {
	}

	public FsFileExpiredException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsFileExpiredException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsFileExpiredException(Throwable throwable) {
		super(throwable);
	}

	@Override
	public int getErrorCode() {
		return FileOptResultCode.FILE_EXPIRED.getValue();
	}
}