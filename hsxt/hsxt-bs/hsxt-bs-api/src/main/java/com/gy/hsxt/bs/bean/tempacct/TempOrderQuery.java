/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tempacct;

import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;

/**
 * 临账订单查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.tempacct
 * @ClassName : TempOrderQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/1 9:40
 * @Version V3.0.0.0
 */
public class TempOrderQuery extends Query {

    private static final long serialVersionUID = 7024434746746592560L;

    /**
     * 企业互生号
     */
    private String hsResNo;

    /**
     * 企业客户名称
     */
    private String custName;

    /**
     * 订单类型
     *
     * @see com.gy.hsxt.bs.common.enumtype.order.OrderType
     */
    private String orderType;

    /**
     * 支付状态
     *
     * @see com.gy.hsxt.bs.common.enumtype.order.PayStatus
     */
    private Integer payStatus;

    /**
     * 订单状态
     *
     * @see com.gy.hsxt.bs.common.enumtype.order.OrderStatus
     */
    private Integer orderStatus;

    /**
     * 支付方式
     *
     * @see com.gy.hsxt.common.constant.PayChannel
     */
    private Integer payChannel;

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * 校验订单类型
     *
     * @return this
     * @throws HsException
     */
    public TempOrderQuery checkOrderType() throws HsException {
        if (StringUtils.isNotBlank(this.getOrderType())) {
            HsAssert.isTrue(OrderType.checkType(this.getOrderType()), RespCode.PARAM_ERROR, "订单类型[orderType]不正确");
        }
        return this;
    }

}
