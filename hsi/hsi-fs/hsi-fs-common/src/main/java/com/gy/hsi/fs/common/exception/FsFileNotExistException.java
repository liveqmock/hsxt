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
 *  File Name       : FsFileNotExistException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件不存在异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsFileNotExistException extends FsException {

	private static final long serialVersionUID = -8287983483218350995L;


	public FsFileNotExistException() {
	}

	public FsFileNotExistException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsFileNotExistException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsFileNotExistException(Throwable throwable) {
		super(throwable);
	}
	
	
	@Override
	public int getErrorCode() {
		return FileOptResultCode.FILE_NO_EXIST.getValue();
	}
}