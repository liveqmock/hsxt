/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.annualfee;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 年费业务查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.annualfee
 * @ClassName : AnnualFeeOrderQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 16:12
 * @Version V3.0.0.0
 */
public class AnnualFeeOrderQuery extends Query {

    private static final long serialVersionUID = 1451004916965380944L;

    /**
     * 客户号
     */
    private String custId;
    /**
     * 互生号
     */
    private String resNo;
    /**
     * 客户名称
     */
    private String custName;

    /**
     * 订单类型
     *
     * @see com.gy.hsxt.bs.common.enumtype.order.OrderType
     */
    private String orderType;

    /**
     * 订单状态
     *
     * @see com.gy.hsxt.bs.common.enumtype.order.OrderStatus
     */
    private Integer orderStatus;

    /**
     * 支付状态
     *
     * @see com.gy.hsxt.bs.common.enumtype.order.PayStatus
     */
    private Integer payStatus;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}
