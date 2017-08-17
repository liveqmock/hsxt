/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 扩展积分投资分红实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: CustomPointDividend
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-14 下午2:13:22
 * @version V1.0
 */
public class CustomPointDividend implements Serializable {

    private static final long serialVersionUID = 2869357699433263145L;

    /** 累计投资积分数 **/
    private String accumulativeInvestCount;

    /** 年度分红回报率 **/
    private String yearDividendRate;

    /** 本笔分红合计 **/
    private String totalDividend;

    /** 本笔分红投资积分数 **/
    private String dividendInvestTotal;

    /** 年度投资分红流通币 **/
    private String normalDividend;

    /** 年度投资分红定向消费币 **/
    private String directionalDividend;

    /** 分红年份 **/
    private String dividendYear;

    /** 分红天数 **/
    private String dividendPeriod;

    public String getDividendPeriod() {
        return dividendPeriod;
    }

    public void setDividendPeriod(String dividendPeriod) {
        this.dividendPeriod = dividendPeriod;
    }

    public String getDividendInvestTotal() {
        return dividendInvestTotal;
    }

    public void setDividendInvestTotal(String dividendInvestTotal) {
        this.dividendInvestTotal = dividendInvestTotal;
    }

    public String getAccumulativeInvestCount() {
        return accumulativeInvestCount;
    }

    public void setAccumulativeInvestCount(String accumulativeInvestCount) {
        this.accumulativeInvestCount = accumulativeInvestCount;
    }

    public String getYearDividendRate() {
        return yearDividendRate;
    }

    public void setYearDividendRate(String yearDividendRate) {
        this.yearDividendRate = yearDividendRate;
    }

    public String getTotalDividend() {
        return totalDividend;
    }

    public void setTotalDividend(String totalDividend) {
        this.totalDividend = totalDividend;
    }

    public String getNormalDividend() {
        return normalDividend;
    }

    public void setNormalDividend(String normalDividend) {
        this.normalDividend = normalDividend;
    }

    public String getDirectionalDividend() {
        return directionalDividend;
    }

    public void setDirectionalDividend(String directionalDividend) {
        this.directionalDividend = directionalDividend;
    }

    public String getDividendYear() {
        return dividendYear;
    }

    public void setDividendYear(String dividendYear) {
        this.dividendYear = dividendYear;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
