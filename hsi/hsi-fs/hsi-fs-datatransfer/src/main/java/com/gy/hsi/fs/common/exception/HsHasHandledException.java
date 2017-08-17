/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.exception;

import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.exception
 * 
 *  File Name       : HasHandledException.java
 * 
 *  Creation Date   : 2015-5-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 已经处理过
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HsHasHandledException extends RuntimeException {

	private static final long serialVersionUID = -7249572867399040181L;
	protected String errorMsg = "";
	
	protected String fileId;

	public HsHasHandledException() {
	}

	public HsHasHandledException(String errorMsg, String fileId) {
		super(errorMsg);
		this.setErrorMsg(errorMsg);
		
		this.fileId = fileId;
	}

	public HsHasHandledException(Throwable throwable) {
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	
}