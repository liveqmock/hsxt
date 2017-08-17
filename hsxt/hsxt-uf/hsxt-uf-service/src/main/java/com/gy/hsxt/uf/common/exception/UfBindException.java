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
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.exception
 * 
 *  File Name       : UfBindException.java
 * 
 *  Creation Date   : 2015-10-10
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 解签失败异常类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UfBindException extends Exception {
	private static final long serialVersionUID = -8918308449373892219L;

	public UfBindException() {
	}

	public UfBindException(String errorMsg) {
		super(errorMsg);
	}

	public UfBindException(int errorCode, String errorMsg) {
		super(errorMsg);
	}
}