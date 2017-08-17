/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.exception;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.common.exception
 * 
 *  File Name       : PortBindedException.java
 * 
 *  Creation Date   : 2015-10-9
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 端口已经被绑定异常类定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PortBindedException extends Exception {
	private static final long serialVersionUID = -7908311803616139196L;

	public PortBindedException() {
	}

	public PortBindedException(String errorMsg) {
		super(errorMsg);
	}

	public PortBindedException(int errorCode, String errorMsg) {
		super(errorMsg);
	}

	public PortBindedException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public PortBindedException(Throwable throwable) {
		super(throwable);
	}
}
