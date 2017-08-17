/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.order;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.BaseParam;

/**
 * 业务订单查询条件包装类，包装所有可能用到的条件，接口各方法使用时按需传参
 * 
 * @Package: com.gy.hsxt.bs.bean.order
 * @ClassName: OrderQueryParam
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-1 下午8:06:25
 * @version V1.0
 */
public class OrderQueryParam extends BaseParam {

    private static final long serialVersionUID = 4649337934845833226L;

    /** 支付方式 **/
    private Integer payChannel;

    /** 支付状态 **/
    private Integer payStatus;

    /** 企业名称 **/
    private String custName;

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
