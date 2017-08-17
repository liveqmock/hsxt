/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** 
 * 
 * @Package: com.gy.hsxt.bs.apply.bean  
 * @ClassName: ExpiryDeclare 
 * @Description: 过期的申报申请
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:47:37 
 * @version V1.0 
 

 */ 
public class ExpiryDeclare implements Serializable{

    private static final long serialVersionUID = -5035306831076453081L;

    /** 申报编号 **/
    private String applyId;

    /** 客户类型 **/
    private Integer custType;

    /** 企业互生号 **/
    private String entResNo;

    /** 订单编号 **/
    private String orderNo;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
