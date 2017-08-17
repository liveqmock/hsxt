/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.tm.common;

import com.gy.hsxt.common.bean.Page;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-bs-service
 * 
 *  Package Name    : com.gy.hsxt.bs.common
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
public class PageContext {

	private static ThreadLocal<Page> context = new ThreadLocal<Page>();

	public static Page getPage() {
		return  context.get();
	}
	
	public static void setPage(Page page){
		context.set(page);
	}

	public static void removeContext() {
		context.remove();
	}

	protected void initialize() {

	}

}
