package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.math.BigDecimal;
import java.util.Date;

public class TBsToolProduct {
    private String productId;

    private String categoryCode;

    private String productName;

    private String microPic;

    private BigDecimal price;

    private String unit;

    private String description;

    private Integer warningValue;

    private Short enableStatus;

    private Short status;

    private Date lastStatusTime;

    private String lastApplyId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMicroPic() {
        return microPic;
    }

    public void setMicroPic(String microPic) {
        this.microPic = microPic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }

    public Short getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Short enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getLastStatusTime() {
        return lastStatusTime;
    }

    public void setLastStatusTime(Date lastStatusTime) {
        this.lastStatusTime = lastStatusTime;
    }

    public String getLastApplyId() {
        return lastApplyId;
    }

    public void setLastApplyId(String lastApplyId) {
        this.lastApplyId = lastApplyId;
    }
}