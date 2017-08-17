package com.gy.hsxt.uf.mapper.vo;

import java.util.Date;

public class TUfPacketLogDetail {
    private String logStackTraceId;

    private Date createDate;

    private String logStackTraceDetail;

    public String getLogStackTraceId() {
        return logStackTraceId;
    }

    public void setLogStackTraceId(String logStackTraceId) {
        this.logStackTraceId = logStackTraceId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLogStackTraceDetail() {
        return logStackTraceDetail;
    }

    public void setLogStackTraceDetail(String logStackTraceDetail) {
        this.logStackTraceDetail = logStackTraceDetail;
    }
}