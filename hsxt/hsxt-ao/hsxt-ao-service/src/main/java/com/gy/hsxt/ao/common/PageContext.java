/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.gy.hsxt.ao.common;

import com.gy.hsxt.common.bean.Page;

/**
 * 分页对象
 * 
 * @Package: com.gy.hsxt.ao.common
 * @ClassName: PageContext
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-17 上午11:26:26
 * @version V1.0
 */
public class PageContext {

	private static ThreadLocal<Page> context = new ThreadLocal<Page>();

	public static Page getPage() {
		return context.get();
	}

	public static void setPage(Page page) {
		context.set(page);
	}

	public static void removeContext() {
		context.remove();
	}

	protected void initialize() {

	}

}
