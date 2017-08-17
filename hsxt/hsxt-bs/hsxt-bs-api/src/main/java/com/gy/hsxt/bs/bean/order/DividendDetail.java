/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 分红计算明细实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: DividendDetail
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-21 上午9:52:12
 * @version V1.0
 */
public class DividendDetail implements Serializable {

    private static final long serialVersionUID = -4441737958324950174L;

    /** 分红流水号 **/
    private String dividendNo;

    /** 投资流水号 **/
    private String investNo;

    /** 可参与分红天数 **/
    private Integer dividendDays;

    /** 分红流通币 **/
    private String normalDividend;

    /** 分红定向消费币/慈善救助基金 **/
    private String directionalDividend;

    /** 分红合计 **/
    private String totalDividend;

    /** 投资日期 **/
    private String investDate;

    /** 投资积分数 **/
    private String investAmount;

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

    public String getDividendNo() {
        return dividendNo;
    }

    public void setDividendNo(String dividendNo) {
        this.dividendNo = dividendNo == null ? null : dividendNo.trim();
    }

    public String getInvestNo() {
        return investNo;
    }

    public void setInvestNo(String investNo) {
        this.investNo = investNo == null ? null : investNo.trim();
    }

    public Integer getDividendDays() {
        return dividendDays;
    }

    public void setDividendDays(Integer dividendDays) {
        this.dividendDays = dividendDays;
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

    public String getTotalDividend() {
        return totalDividend;
    }

    public void setTotalDividend(String totalDividend) {
        this.totalDividend = totalDividend;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
