/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 积分投资年度分红比率实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: DividendRate
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-14 下午12:26:48
 * @version V1.0
 */
public class DividendRate implements Serializable {

    private static final long serialVersionUID = 5049478541937365160L;

    /** 分红年份 **/
    private String dividendYear;

    /** 分红比率 **/
    private String dividendRate;

    /** 分红日期 **/
    private String dividendDate;

    /** 分红周期 **/
    private String dividendPeriod;

    /** 分红状态 **/
    private Integer dividendStatus;

    public Integer getDividendStatus() {
        return dividendStatus;
    }

    public void setDividendStatus(Integer dividendStatus) {
        this.dividendStatus = dividendStatus;
    }

    public String getDividendYear() {
        return dividendYear;
    }

    public void setDividendYear(String dividendYear) {
        this.dividendYear = dividendYear;
    }

    public String getDividendRate() {
        return dividendRate;
    }

    public void setDividendRate(String dividendRate) {
        this.dividendRate = dividendRate;
    }

    public String getDividendDate() {
        return dividendDate;
    }

    public void setDividendDate(String dividendDate) {
        this.dividendDate = dividendDate;
    }

    public String getDividendPeriod() {
        return dividendPeriod;
    }

    public void setDividendPeriod(String dividendPeriod) {
        this.dividendPeriod = dividendPeriod;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
