/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

/**
 * 分页上下文
 *
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : PageContext
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/7 11:58
 * @Version V3.0.0.0
 */
public abstract class PageContext {

    /**
     *分页开始编号
     */
    private static ThreadLocal<Integer> offset = new ThreadLocal<>();

    /**
     * 分页大小
     */
    private static ThreadLocal<Integer> pageSize = new ThreadLocal<>();

    /**
     * 总记录数
     */
    private static ThreadLocal<Integer> total = new ThreadLocal<>();

    public static Integer getOffset() {
        return offset.get();
    }

    public static void setOffset(Integer os) {
        offset.set(os);
    }

    public static Integer getPageSize() {
        return pageSize.get();
    }

    public static void setPageSize(Integer ps) {
        pageSize.set(ps);
    }

    public static Integer getTotal() {
        return total.get();
    }

    public static void setTotal(Integer t) {
        total.set(t);
    }
}
