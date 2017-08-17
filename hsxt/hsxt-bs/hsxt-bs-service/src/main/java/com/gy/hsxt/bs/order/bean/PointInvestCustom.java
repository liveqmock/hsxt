/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 积分投资实体类
 *
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: PointInvest
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-9-29 下午3:04:32
 * @version V1.0
 */
public class PointInvestCustom implements Serializable {

    private static final long serialVersionUID = 2869357699433263145L;

    /** 投资流水号 **/
    private String investNo;

    /** 投资日期 **/
    private String investDate;

    /** 投资积分数 **/
    private String investAmount;

    /** 客户号 **/
    private String custId;

    /** 客户互生号 **/
    private String hsResNo;

    /** 客户名称 **/
    private String custName;

    /** 客户类型 **/
    private Integer custType;

    /** 投资天数 **/
    private Integer investDays;

    public Integer getInvestDays() {
        return investDays;
    }

    public void setInvestDays(Integer investDays) {
        this.investDays = investDays;
    }

    public String getInvestNo() {
        return investNo;
    }

    public void setInvestNo(String investNo) {
        this.investNo = investNo;
    }

    public String getInvestDate() {
        return investDate;
    }

    public void setInvestDate(String investDate) {
        this.investDate = investDate;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getHsResNo() {
        return hsResNo;
    }

    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
