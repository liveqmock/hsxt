package com.gy.hsxt.tc.batch.bean;

public class TcAccountDays {
    private Long accDaysId;

    private String accSys;

    private String accDate;

    private Short accProgress;

    private Short accState;

    private String accDesc;

    private Object updatedDate;

    public Long getAccDaysId() {
        return accDaysId;
    }

    public void setAccDaysId(Long accDaysId) {
        this.accDaysId = accDaysId;
    }

    public String getAccSys() {
        return accSys;
    }

    public void setAccSys(String accSys) {
        this.accSys = accSys;
    }

    public String getAccDate() {
        return accDate;
    }

    public void setAccDate(String accDate) {
        this.accDate = accDate;
    }

    public Short getAccProgress() {
        return accProgress;
    }

    /**
     * 
     * @param accProgress 处理进度：0：预处理,1：明细对账
     */
    public void setAccProgress(Short accProgress) {
        this.accProgress = accProgress;
    }

    public Short getAccState() {
        return accState;
    }

    /**
     * 
     * @param accState 处理状态 0：成功； 1：失败  2：处理中
     */
    public void setAccState(Short accState) {
        this.accState = accState;
    }

    public String getAccDesc() {
        return accDesc;
    }

    public void setAccDesc(String accDesc) {
        this.accDesc = accDesc;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }
}