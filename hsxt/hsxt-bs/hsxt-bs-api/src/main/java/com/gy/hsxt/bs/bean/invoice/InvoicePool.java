/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.invoice;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.common.enumtype.invoice.BizType;

import java.io.Serializable;

/**
 * 发票统计池
 *
 * @Package :com.gy.hsxt.bs.bean.invoice
 * @ClassName : InvoicePool
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 11:23
 * @Version V3.0.0.0
 */
public class InvoicePool implements Serializable {

    private static final long serialVersionUID = -6276819312164044545L;
    /**
     * 客户号
     */
    private String custId;
    /**
     * 互生号
     */
    private String resNo;

    /**
     * 客户类型
     * @see com.gy.hsxt.common.constant.CustType
     */
    private Integer custType;
    /**
     * 客户名称
     */
    private String custName;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 开票方
     */
    private Integer invoiceMaker;
    /**
     * 总金额
     */
    private String allAmount;
    /**
     * 已开发票总金额
     */
    private String openedAmount;
    /**
     * 正在申请的发票总金额
     */
    private String pendingAmount;
    /**
     * 可开发票总金额
     */
    private String remainAmount;
    /**
     * 是否继续统计
     */
    private Integer goOn;
    /**
     * 发票统计到的最后日期
     */
    private String lastDate;
    /**
     * 停止统计日期
     */
    private String stopDate;
    /**
     * 记录创建时间
     */
    private String createTime;

    /**
     * 记录更新时间
     */
    private String updateTime;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Integer getInvoiceMaker() {
        return invoiceMaker;
    }

    public void setInvoiceMaker(Integer invoiceMaker) {
        this.invoiceMaker = invoiceMaker;
    }

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }

    public String getOpenedAmount() {
        return openedAmount;
    }

    public void setOpenedAmount(String openedAmount) {
        this.openedAmount = openedAmount;
    }

    public String getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getGoOn() {
        return goOn;
    }

    public void setGoOn(Integer goOn) {
        this.goOn = goOn;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 构建发票池
     * @param custId 客户号
     * @param bizType 业务类型
     * @return bean
     */
    public static InvoicePool bulid(String custId, String bizType) {
        InvoicePool invoicePool = new InvoicePool();
        invoicePool.setCustId(custId);
        invoicePool.setBizType(bizType);
        return invoicePool;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
