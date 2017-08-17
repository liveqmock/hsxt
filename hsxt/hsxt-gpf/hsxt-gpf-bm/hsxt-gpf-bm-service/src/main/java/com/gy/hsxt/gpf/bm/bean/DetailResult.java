package com.gy.hsxt.gpf.bm.bean;

import java.io.Serializable;

/**
 * Created by ques134z-erete on 2015/4/14.
 */
public class DetailResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String startDate;
    private String endDate;
    private Integer page;
    private Integer pageSize;
    private Integer totalRow;
    private Integer totalNum;
    private T data;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DetailResult{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", totalRow=" + totalRow +
                ", totalNum=" + totalNum +
                ", data=[" + data.toString() +
               "]}";
    }
}
