package com.gy.hsi.fs.mapper.vo.dbbs01;

public class TBsPerChangeInfoData extends TBsPerChangeInfoDataKey {
    private String beforeValue;

    private String afterValue;

    public String getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }
}