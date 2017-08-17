/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 分页对象
 *
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : PageData
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/7 11:27
 * @Version V3.0.0.0
 */
public class PageData<T> {

    /**
     * 记录总条数
     */
    private int recordsTotal;

    /**
     * 显示的记录总数
     */
    private int recordsFiltered;

    /**
     * 当前页数据
     */
    private List<T> data;

    private PageData(int recordsTotal, List<T> data) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
        this.data = data;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    /**
     * 构建分页对象的方法
     *
     * @param total 记录总数
     * @param data  查询数据
     * @return 分页对象
     */
    public static <T> PageData<T> build(int total, List<T> data) {

        return new PageData<>(total, data);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
