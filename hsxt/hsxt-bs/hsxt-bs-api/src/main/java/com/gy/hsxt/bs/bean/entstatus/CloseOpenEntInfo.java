/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: CloseOpenEntInfo
 * @Description: 开关系统信息
 * 
 * @author: xiaofl
 * @date: 2015-11-30 上午11:01:01
 * @version V1.0
 */
public class CloseOpenEntInfo implements Serializable {

    private static final long serialVersionUID = 2843113446609567884L;

    /** 申请编号 */
    private String applyId;

    /** 申请类型 */
    private Integer applyType;

    /** 申请操作员客户号 */
    private String applyOptCustId;

    /** 申请操作员名称 */
    private String applyOptCustName;

    /** 申请日期 */
    private String applyDate;

    /** 申请说明 */
    private String applyRemark;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getApplyOptCustId() {
        return applyOptCustId;
    }

    public void setApplyOptCustId(String applyOptCustId) {
        this.applyOptCustId = applyOptCustId;
    }

    public String getApplyOptCustName() {
        return applyOptCustName;
    }

    public void setApplyOptCustName(String applyOptCustName) {
        this.applyOptCustName = applyOptCustName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
