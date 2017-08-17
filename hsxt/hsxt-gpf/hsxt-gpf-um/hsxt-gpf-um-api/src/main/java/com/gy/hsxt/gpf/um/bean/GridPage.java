/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Grid分页请求数据封装类
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : GridPage
 * @Description : Grid分页请求数据封装类
 * @Author : chenm
 * @Date : 2016/1/28 12:27
 * @Version V3.0.0.0
 */
public class GridPage implements Serializable {

    private static final long serialVersionUID = -8164049355716139933L;
    /**
     * 分页大小
     */
    private int pageSize;

    /**
     * 当前页码
     */
    private int curPage;

    /**
     * 起始编号
     */
    private int startNo;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 升序，降序(desc,asc)
     */
    private String sortOrder;


    /**
     * 无参构造函数
     * 初始化数据
     */
    public GridPage() {
        this.pageSize = 10;
        this.curPage = 1;
        this.startNo = 0;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
        this.startNo = (curPage - 1) * pageSize;
    }

    public int getStartNo() {
        return startNo;
    }

    public void setStartNo(int startNo) {
        this.startNo = startNo;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
