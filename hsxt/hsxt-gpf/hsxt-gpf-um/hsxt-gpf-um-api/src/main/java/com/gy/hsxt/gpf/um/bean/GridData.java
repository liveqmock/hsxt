/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * Grid数据封装类
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : GridData
 * @Description : Grid数据封装类
 * 针对jquery.bsgrid表格分页插件，所制定的数据传输对象
 * @Author : chenm
 * @Date : 2016/1/28 12:03
 * @Version V3.0.0.0
 */
public class GridData<T> implements Serializable {

    private static final long serialVersionUID = 1808366347464171320L;
    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 总记录数
     */
    private int totalRows;

    /**
     * 当前页
     */
    private int curPage;

    /**
     * 查询结果
     */
    private List<T> data;

    /**
     * 含参构造函数
     *
     * @param success   查询是否成功
     * @param totalRows 总记录数
     * @param curPage   当前页
     * @param data      数据
     */
    private GridData(boolean success, int totalRows, int curPage, List<T> data) {
        this.success = success;
        this.totalRows = totalRows;
        this.curPage = curPage;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 构建返回数据
     *
     * @param success   查询是否成功
     * @param totalRows 总记录数
     * @param curPage   当前页
     * @param data      数据
     * @param <T>       泛型
     * @return bean
     */
    public static <T> GridData<T> bulid(boolean success, int totalRows, int curPage, List<T> data) {
        return new GridData<>(success, totalRows, curPage, data);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
