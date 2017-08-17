package com.gy.hsi.fs.server.common.framework.mybatis.mapvo;

import java.util.Date;

public class TFsFileOperationLog {
    private Long id;

    private String fileId;

    private String functionId;

    private String functionParam;

    private Integer optStatus;

    private String optErrDesc;

    private String optIpAddr;

    private String optUserId;

    private String secureToken;

    private String channel;

    private Date createdDate;

    private String optErrDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionParam() {
        return functionParam;
    }

    public void setFunctionParam(String functionParam) {
        this.functionParam = functionParam;
    }

    public Integer getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(Integer optStatus) {
        this.optStatus = optStatus;
    }

    public String getOptErrDesc() {
        return optErrDesc;
    }

    public void setOptErrDesc(String optErrDesc) {
        this.optErrDesc = optErrDesc;
    }

    public String getOptIpAddr() {
        return optIpAddr;
    }

    public void setOptIpAddr(String optIpAddr) {
        this.optIpAddr = optIpAddr;
    }

    public String getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(String optUserId) {
        this.optUserId = optUserId;
    }

    public String getSecureToken() {
        return secureToken;
    }

    public void setSecureToken(String secureToken) {
        this.secureToken = secureToken;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getOptErrDetail() {
        return optErrDetail;
    }

    public void setOptErrDetail(String optErrDetail) {
        this.optErrDetail = optErrDetail;
    }
}