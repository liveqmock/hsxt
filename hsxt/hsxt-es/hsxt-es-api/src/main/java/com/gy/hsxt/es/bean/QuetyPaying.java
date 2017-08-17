/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Package: com.gy.hsxt.ps.bean
 * @ClassName: Result
 * @Description: 返回应答结果
 * 
 * @author: chenhongzhi
 * @date: 2015-10-27 下午2:40:31
 * @version V3.0
 */
public class QuetyPaying implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 477512678427465261L;

    /**订单号*/
    private  String orderNo;
    
    /*** 交易类型 */
    private String transType;
    
    /*** 原始交易号 */
    private String sourceTransNo;
    
    /*** 交易流水号 */
    private String transNo;
    
    /** 积分 */
    private String perPoint;
    
    /** 会计时间 */
    private Timestamp sourceTransDate;

    /**
     * @return the 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param 订单号 the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the 交易类型
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param 交易类型 the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * @return the 原始交易号
     */
    public String getSourceTransNo() {
        return sourceTransNo;
    }

    /**
     * @param 原始交易号 the sourceTransNo to set
     */
    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

    /**
     * @return the 交易流水号
     */
    public String getTransNo() {
        return transNo;
    }

    /**
     * @param 交易流水号 the transNo to set
     */
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    /**
     * @return the 积分
     */
    public String getPerPoint() {
        return perPoint;
    }

    /**
     * @param 积分 the perPoint to set
     */
    public void setPerPoint(String perPoint) {
        this.perPoint = perPoint;
    }

    /**
     * @return the 会计时间
     */
    public Timestamp getSourceTransDate() {
        return sourceTransDate;
    }

    /**
     * @param 会计时间 the sourceTransDt to set
     */
    public void setSourceTransDate(Timestamp sourceTransDate) {
        this.sourceTransDate = sourceTransDate;
    }
}
