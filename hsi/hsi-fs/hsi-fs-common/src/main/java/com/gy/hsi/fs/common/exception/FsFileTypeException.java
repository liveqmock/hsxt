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
 *  File Name       : FsFileTypeException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件类型非法异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsFileTypeException extends FsException {

	private static final long serialVersionUID = 2667764734463730890L;


	public FsFileTypeException() {
	}

	public FsFileTypeException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsFileTypeException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsFileTypeException(Throwable throwable) {
		super(throwable);
	}
	
	
	@Override
	public int getErrorCode() {
		return FileOptResultCode.FILE_TYPE_ILLEGAL.getValue();
	}
}