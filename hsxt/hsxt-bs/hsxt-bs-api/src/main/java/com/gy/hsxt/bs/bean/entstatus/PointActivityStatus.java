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
 * @ClassName: PointActivityStatus
 * @Description: 积分活动状态信息
 * 
 * @author: xiaofl
 * @date: 2016-1-11 下午4:06:09
 * @version V1.0
 */
public class PointActivityStatus implements Serializable {

    private static final long serialVersionUID = -9160257364680066372L;

    /** 申请编号 **/
    private String applyId;

    /** 申请类型 */
    private Integer applyType;

    /** 申请时间 **/
    private String applyDate;

    /** 审批结果 **/
    private Integer status;

    /** 审批时间 **/
    private String apprDate;

    /** 审批意见 **/
    private String apprRemark;

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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
