package com.gy.hsi.fs.server.common.framework.mybatis.mapvo;

import java.util.Date;

public class TFsPermissionRule {
    private Integer id;

    private String ruleMatrixX;

    private String ruleMatrixY;

    private String remark;

    private Date createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleMatrixX() {
        return ruleMatrixX;
    }

    public void setRuleMatrixX(String ruleMatrixX) {
        this.ruleMatrixX = ruleMatrixX;
    }

    public String getRuleMatrixY() {
        return ruleMatrixY;
    }

    public void setRuleMatrixY(String ruleMatrixY) {
        this.ruleMatrixY = ruleMatrixY;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}