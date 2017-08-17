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
 * @ClassName: RealNameQueryParam
 * @Description: 实名认证查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:41:38
 * @version V1.0
 */
public class RealNameQueryParam implements Serializable {

    private static final long serialVersionUID = 2593716167299285775L;

    /** 实名认证开始时间 **/
    private String startDate;

    /** 实名认证结束时间 **/
    private String endDate;

    /** 状态 **/
    private Integer status;

    /** 互生号 **/
    private String resNo;

    /** 姓名/名称 **/
    private String name;

    /** 企业类型 **/
    private Integer entType;

    /** 操作员客户号 **/
    private String optCustId;

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

    public Integer getEntType() {
        return entType;
    }

    public void setEntType(Integer entType) {
        this.entType = entType;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
