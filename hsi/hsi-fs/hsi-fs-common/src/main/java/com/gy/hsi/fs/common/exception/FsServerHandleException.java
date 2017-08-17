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
 *  File Name       : FsServerHandleException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 服务器处理异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsServerHandleException extends FsException {
	
	private static final long serialVersionUID = -7538274599188093839L;

	public FsServerHandleException() {
	}

	public FsServerHandleException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}
	
	public FsServerHandleException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsServerHandleException(Throwable throwable) {
		super(throwable);
	}
	
	@Override
	public int getErrorCode() {
		return FileOptResultCode.OPT_FAILED.getValue();
	}
}