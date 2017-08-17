/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 积分投资分红实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: PointDividend
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-14 下午2:13:22
 * @version V1.0
 */
public class PointDividend implements Serializable {

    private static final long serialVersionUID = 2869357699433263145L;

    /** 分红流水号 **/
    private String dividendNo;

    /** 分红年份 **/
    private String dividendYear;

    /** 本笔分红流通币 **/
    private String normalDividend;

    /** 本笔分红定向消费币/慈善基金 **/
    private String directionalDividend;

    /** 本笔分红合计 **/
    private String totalDividend;

    /** 客户号 **/
    private String custId;

    /** 客户互生号 **/
    private String hsResNo;

    /** 客户类型 **/
    private Integer custType;

    /** 分红时间 **/
    private String dividendTime;

    public String getDividendTime() {
        return dividendTime;
    }

    public void setDividendTime(String dividendTime) {
        this.dividendTime = dividendTime;
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

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getDividendNo() {
        return dividendNo;
    }

    public void setDividendNo(String dividendNo) {
        this.dividendNo = dividendNo;
    }

    public String getDividendYear() {
        return dividendYear;
    }

    public void setDividendYear(String dividendYear) {
        this.dividendYear = dividendYear;
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
