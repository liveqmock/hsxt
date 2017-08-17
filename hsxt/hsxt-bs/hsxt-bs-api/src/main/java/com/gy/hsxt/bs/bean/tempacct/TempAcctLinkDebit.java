/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tempacct;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 临账订单关联实体
 *
 * @Package : com.gy.hsxt.bs.bean.tempacct
 * @ClassName : TempAcctLinkDebit
 * @Description : 临账关联申请单关联临账记录 Bean 实体类
 * @Author : liuhq
 * @Date : 2015-9-8 下午2:08:00
 * @Version V3.0
 */
public class TempAcctLinkDebit implements Serializable {

    private static final long serialVersionUID = 463637651574520691L;

    /**
     * 申请编号
     */
    private String applyId;

    /**
     * 业务订单号
     **/
    private String orderNo;

    /**
     * 临账记录编号
     **/
    private String debitId;

    /**
     * 本次关联扣除金额,复核时设置关联金额
     **/
    private String linkAmount;

    /**
     * 临账信息实体
     */
    private Debit debit;

    /**
     * 审批备注
     */
    private String remark;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDebitId() {
        return debitId;
    }

    public void setDebitId(String debitId) {
        this.debitId = debitId;
    }

    public String getLinkAmount() {
        return linkAmount;
    }

    public void setLinkAmount(String linkAmount) {
        this.linkAmount = linkAmount;
    }

    public Debit getDebit() {
        return debit;
    }

    public void setDebit(Debit debit) {
        this.debit = debit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
