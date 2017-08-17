/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.common;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.common
 * 
 *  File Name       : PageContext.java
 * 
 *  Creation Date   : 2015-7-2
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 分页信息，用来传送分页参数
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class PageContext extends PageInfo {

	private static final long serialVersionUID = 4647507850697884950L;
	
	private static ThreadLocal<PageContext> context = new ThreadLocal<PageContext>();

	public static PageContext getContext() {
		PageContext ci = context.get();
		if (ci == null) {
			ci = new PageContext();
			context.set(ci);
		}
		return ci;
	}

	public static void removeContext() {
		context.remove();
	}

	protected void initialize() {

	}

}
