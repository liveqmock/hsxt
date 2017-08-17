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
 *  Purpose         : 参数传递错误异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsParameterException extends FsException {

	private static final long serialVersionUID = 6299194732735896969L;

	public FsParameterException() {
	}

	public FsParameterException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsParameterException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsParameterException(Throwable throwable) {
		super(throwable);
	}
	
	
	@Override
	public int getErrorCode() {
		return FileOptResultCode.PARAM_INVALID.getValue();
	}
}