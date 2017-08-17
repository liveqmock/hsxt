/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: ResFeeDebtOrder
 * @Description: 系统销售费欠款单
 * 
 * @author: xiaofl
 * @date: 2015-12-16 上午9:39:34
 * @version V1.0
 */
public class ResFeeDebtOrder implements Serializable {

    private static final long serialVersionUID = -5291222108758926939L;

    /** 企业名称 **/
    private String entName;

    /** 企业互生号 **/
    private String entResNo;

    /** 开启系统时间 **/
    private String openSysDate;

    /** 系统销售费 **/
    private String resFee;

    /** 付款状态 **/
    private Integer payStatus;

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getOpenSysDate() {
        return openSysDate;
    }

    public void setOpenSysDate(String openSysDate) {
        this.openSysDate = openSysDate;
    }

    public String getResFee() {
        return resFee;
    }

    public void setResFee(String resFee) {
        this.resFee = resFee;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
