/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.utils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.utils
 * 
 *  File Name       : ExceptionUtils.java
 * 
 *  Creation Date   : 2015年6月8日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ExceptionUtils {
	/**
	 * 从堆栈中把日志信息递归取出
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackTraceInfo(Throwable e) {
		if (null == e) {
			return "";
		}

		StringBuilder exceptionBuffer = new StringBuilder();

		getStackTraceInfo(exceptionBuffer, e, "");

		return exceptionBuffer.toString();
	}

	/**
	 * 从堆栈中把日志信息递归取出
	 * 
	 * @param exceptionBuffer
	 * @param e
	 * @param prefix
	 */
	private static void getStackTraceInfo(StringBuilder exceptionBuffer, Throwable e,
			String prefix) {
		if (null == e) {
			return;
		}

		String exMsg = e.getMessage();
		exceptionBuffer.append("\n").append(prefix);
		exceptionBuffer.append(e.getClass().getName());

		if (null != exMsg) {
			exceptionBuffer.append(":").append(e.getMessage());
		}

		StackTraceElement[] arrayOfStackTraceElement = e.getStackTrace();

		if (null != arrayOfStackTraceElement) {
			for (Object localObject : arrayOfStackTraceElement) {
				exceptionBuffer.append("\n\t at " + localObject);
			}
		}

		Throwable[] suppresseds = e.getSuppressed();

		if (null != suppresseds) {
			for (Throwable throwable : suppresseds) {
				getStackTraceInfo(exceptionBuffer, throwable, "Suppressed: ");
			}
		}

		Throwable cause = e.getCause();

		if (null != cause) {
			getStackTraceInfo(exceptionBuffer, cause, "Caused by: ");
		}
	}
}
