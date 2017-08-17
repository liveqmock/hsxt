package com.gy.hsxt.ps.common;

import com.gy.hsxt.common.bean.Page;

/**
 * 分页信息类
 * 
 * @Package: com.gy.hsxt.ac.common.bean  
 * @ClassName: PageContext 
 * @Description: 分页信息，用来传送分页参数
 *
 * @author: guopengfei
 * @date: 2015-9-28 下午12:14:42 
 * @version V1.0
 */
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
