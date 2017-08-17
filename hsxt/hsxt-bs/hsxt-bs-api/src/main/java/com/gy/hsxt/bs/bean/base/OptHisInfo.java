/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: OptHisInfo
 * @Description: 审批操作历史
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:12:30
 * @version V1.0
 */
public class OptHisInfo implements Serializable {

    private static final long serialVersionUID = -7630969232135772462L;

    /** 操作历史id **/
    private String optHisId;

    /** 业务申请编号 **/
    private String applyId;

    /** 业务阶段 **/
    private Integer bizAction;

    /** 业务状态 **/
    private Integer bizStatus;

    /** 状态日期或期限 **/
    private String optDate;

    /** 状态操作单位 **/
    private String optEntName;

    /** 操作员ID **/
    private String optId;

    /** 操作员名称 **/
    private String optName;

    /** 双签操作员ID **/
    private String dblOptId;

    /** 双签操作员名称 **/
    private String dblOptName;

    /** 操作说明 **/
    private String optRemark;

    /** 修改内容 json格式： [{属性名：{old:旧值,new:新值}}, {属性名：{old:旧值,new:新值}}] **/
    private String changeContent;

    public String getOptHisId() {
        return optHisId;
    }

    public void setOptHisId(String optHisId) {
        this.optHisId = optHisId;
    }

    public Integer getBizAction() {
        return bizAction;
    }

    public void setBizAction(Integer bizAction) {
        this.bizAction = bizAction;
    }

    public Integer getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(Integer bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getOptDate() {
        return optDate;
    }

    public void setOptDate(String optDate) {
        this.optDate = optDate;
    }

    public String getOptEntName() {
        return optEntName;
    }

    public void setOptEntName(String optEntName) {
        this.optEntName = optEntName;
    }

    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getOptRemark() {
        return optRemark;
    }

    public void setOptRemark(String optRemark) {
        this.optRemark = optRemark;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public String getDblOptId() {
        return dblOptId;
    }

    public void setDblOptId(String dblOptId) {
        this.dblOptId = dblOptId;
    }

    public String getDblOptName() {
        return dblOptName;
    }

    public void setDblOptName(String dblOptName) {
        this.dblOptName = dblOptName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
