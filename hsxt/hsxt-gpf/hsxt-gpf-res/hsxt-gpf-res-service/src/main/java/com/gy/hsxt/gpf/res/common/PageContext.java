/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.res.common;

import com.gy.hsxt.common.bean.Page;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.common
 * @ClassName: PageContext
 * @Description: 分页信息，用来传送分页参数
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:58:10
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
