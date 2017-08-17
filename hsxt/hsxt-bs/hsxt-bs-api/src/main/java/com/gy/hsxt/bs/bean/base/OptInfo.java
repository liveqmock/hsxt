/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class OptInfo implements Serializable {

    private static final long serialVersionUID = -1020951109759400518L;

    /** 操作员客户号 **/
    private String optCustId;

    /** 操作员名字 **/
    private String optName;

    /** 操作员所属公司名称/个人消费者名称 **/
    private String optEntName;

    /** 双签操作员客户号 **/
    private String dblOptCustId;

    /** 操作时间 **/
    private String optDate;

    /** 操作备注 **/
    private String optRemark;

    /** 修改内容: json格式： [{属性名：{old:旧值,new:新值}}, {属性名：{old:旧值,new:新值}}] **/
    private String changeContent;

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getOptEntName() {
        return optEntName;
    }

    public void setOptEntName(String optEntName) {
        this.optEntName = optEntName;
    }

    public String getDblOptCustId() {
        return dblOptCustId;
    }

    public void setDblOptCustId(String dblOptCustId) {
        this.dblOptCustId = dblOptCustId;
    }

    public String getOptDate() {
        return optDate;
    }

    public void setOptDate(String optDate) {
        this.optDate = optDate;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
