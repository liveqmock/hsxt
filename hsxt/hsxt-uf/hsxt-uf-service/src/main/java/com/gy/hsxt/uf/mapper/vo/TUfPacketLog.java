package com.gy.hsxt.uf.mapper.vo;

import java.util.Date;

public class TUfPacketLog {
    private Long logId;

    private String packetId;

    private String srcPlatformId;

    private String srcSubsysId;

    private String destPlatformId;

    private String destSubsysId;

    private String destBizCode;

    private Integer packetOptType;

    private Integer packetOptStatus;

    private Long reqPacketSize;

    private Long respPacketSize;

    private Float totalRespTime;

    private String logErrDesc;

    private String logStackTraceId;

    private Date createDate;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public String getSrcPlatformId() {
        return srcPlatformId;
    }

    public void setSrcPlatformId(String srcPlatformId) {
        this.srcPlatformId = srcPlatformId;
    }

    public String getSrcSubsysId() {
        return srcSubsysId;
    }

    public void setSrcSubsysId(String srcSubsysId) {
        this.srcSubsysId = srcSubsysId;
    }

    public String getDestPlatformId() {
        return destPlatformId;
    }

    public void setDestPlatformId(String destPlatformId) {
        this.destPlatformId = destPlatformId;
    }

    public String getDestSubsysId() {
        return destSubsysId;
    }

    public void setDestSubsysId(String destSubsysId) {
        this.destSubsysId = destSubsysId;
    }

    public String getDestBizCode() {
        return destBizCode;
    }

    public void setDestBizCode(String destBizCode) {
        this.destBizCode = destBizCode;
    }

    public Integer getPacketOptType() {
        return packetOptType;
    }

    public void setPacketOptType(Integer packetOptType) {
        this.packetOptType = packetOptType;
    }

    public Integer getPacketOptStatus() {
        return packetOptStatus;
    }

    public void setPacketOptStatus(Integer packetOptStatus) {
        this.packetOptStatus = packetOptStatus;
    }

    public Long getReqPacketSize() {
        return reqPacketSize;
    }

    public void setReqPacketSize(Long reqPacketSize) {
        this.reqPacketSize = reqPacketSize;
    }

    public Long getRespPacketSize() {
        return respPacketSize;
    }

    public void setRespPacketSize(Long respPacketSize) {
        this.respPacketSize = respPacketSize;
    }

    public Float getTotalRespTime() {
        return totalRespTime;
    }

    public void setTotalRespTime(Float totalRespTime) {
        this.totalRespTime = totalRespTime;
    }

    public String getLogErrDesc() {
        return logErrDesc;
    }

    public void setLogErrDesc(String logErrDesc) {
        this.logErrDesc = logErrDesc;
    }

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
}