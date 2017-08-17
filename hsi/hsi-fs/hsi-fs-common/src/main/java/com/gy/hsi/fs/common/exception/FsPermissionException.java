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
 *  File Name       : FsPermissionException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件权限错误异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsPermissionException extends FsException {

	private static final long serialVersionUID = 3574614135354345182L;

	public FsPermissionException() {
	}

	public FsPermissionException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsPermissionException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsPermissionException(Throwable throwable) {
		super(throwable);
	}

	@Override
	public int getErrorCode() {
		return FileOptResultCode.PERMISSION_ERR.getValue();
	}
}