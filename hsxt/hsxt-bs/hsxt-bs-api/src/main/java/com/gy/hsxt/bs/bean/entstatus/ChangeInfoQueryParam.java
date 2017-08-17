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
 * @ClassName: ChangeInfoQueryParam
 * @Description: 重要信息变更查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:34:18
 * @version V1.0
 */
public class ChangeInfoQueryParam implements Serializable {

    private static final long serialVersionUID = -492906661874640886L;

    /** 申请开始时间 **/
    private String startDate;

    /** 申请结束时间 **/
    private String endDate;

    /** 状态 **/
    private Integer status;

    /** 企业类型 **/
    private Integer entType;

    /** 互生号 **/
    private String resNo;

    /** 名称 **/
    private String name;

    /** 操作员客户号 **/
    private String optCustId;

    /** 包含状态如 1,2 **/
    private String inStatus;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEntType() {
        return entType;
    }

    public void setEntType(Integer entType) {
        this.entType = entType;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
