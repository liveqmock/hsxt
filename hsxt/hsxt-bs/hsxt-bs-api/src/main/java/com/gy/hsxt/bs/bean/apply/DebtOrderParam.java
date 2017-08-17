/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DebtOrderParam
 * @Description: 系统欠款单查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-16 上午9:48:38
 * @version V1.0
 */
public class DebtOrderParam implements Serializable {

    private static final long serialVersionUID = 4457779471392354075L;

    /** 开启系统开始时间 **/
    private String startDate;

    /** 开启系统结束时间 **/
    private String endDate;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业名称 **/
    private String entName;

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

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
