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
 *  File Name       : FsFileSizeOverLimitException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件大小超过上限异常定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsFileSizeOverLimitException extends FsException {

	private static final long serialVersionUID = 9029776284332723142L;


	public FsFileSizeOverLimitException() {
	}

	public FsFileSizeOverLimitException(String errorMsg) {
		super(errorMsg);

		this.setErrorMsg(errorMsg);
	}

	public FsFileSizeOverLimitException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public FsFileSizeOverLimitException(Throwable throwable) {
		super(throwable);
	}
	
	
	@Override
	public int getErrorCode() {
		return FileOptResultCode.FILE_SIZE_OVER_LIMIT.getValue();
	}
}