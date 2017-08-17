/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.invoice;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.HsResNoUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 发票申请实体
 *
 * @Package :com.gy.hsxt.bs.bean.invoice
 * @ClassName : Invoice
 * @Description : 发票申请实体
 * @Author : chenm
 * @Date : 2015/12/15 15:13
 * @Version V3.0.0.0
 */
public abstract class Invoice implements Serializable {

    private static final long serialVersionUID = -2924733073303946194L;
    /**
     * 发票编号
     */
    private String invoiceId;
    /**
     * 企业客户号
     */
    private String custId;
    /**
     * 企业互生号
     */
    private String resNo;

    /**
     * 客户类型
     */
    private Integer custType;
    /**
     * 企业名称
     */
    private String custName;
    /**
     * 发票状态 0-待审核1-待配送/开票成功 2-待签收/已配送 3-已签收 4-审核驳回 5-拒签
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.InvoiceStatus
     */
    private Integer status;
    
    /**
     * 拒签原因
     */
    private String refuseRemark;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 发票类型 0-普通增值税发票 1-增值税专用发票
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.InvoiceType
     */
    private Integer invoiceType;
    /**
     * 发票申请金额
     */
    private String allAmount;
    /**
     * 银行支行名称
     */
    private String bankBranchName;
    /**
     * 银行账号
     */
    private String bankNo;
    /**
     * 地址
     */
    private String address;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 纳税人识别号
     */
    private String taxpayerNo;

    /**
     * 开票内容
     */
    private String openContent;
    /**
     * 开票时间
     */
    private String openTime;
    /**
     * 经办人
     */
    private String openOperator;
    /**
     * 快递公司名称
     */
    private String expressName;

    /**
     * 运单号
     */
    private String trackingNo;

    /**
     * 配送方式 ： 0-快递 1-自取 2-其他
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.PostWay
     */
    private Integer postWay;

    /**
     * 签收方式：0-物流单号确认签收、1-电话确认签收、2-到期自动签收
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.ReceiveWay
     */
    private Integer receiveWay;
    
    /**
     * 更新时间
     */
    private String updatedDate;

    /**
     * 更新者
     */
    private String updatedBy;


    /**
     * 发票清单列表
     */
    private List<InvoiceList> invoiceLists;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Integer getCustType() {
        return custType;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
        this.custType = HsResNoUtils.getCustTypeByHsResNo(resNo);
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTaxpayerNo() {
        return taxpayerNo;
    }

    public void setTaxpayerNo(String taxpayerNo) {
        this.taxpayerNo = taxpayerNo;
    }

    public String getOpenContent() {
        return openContent;
    }

    public void setOpenContent(String openContent) {
        this.openContent = openContent;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenOperator() {
        return openOperator;
    }

    public void setOpenOperator(String openOperator) {
        this.openOperator = openOperator;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Integer getPostWay() {
        return postWay;
    }

    public void setPostWay(Integer postWay) {
        this.postWay = postWay;
    }

    public Integer getReceiveWay() {
        return receiveWay;
    }

    public void setReceiveWay(Integer receiveWay) {
        this.receiveWay = receiveWay;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<InvoiceList> getInvoiceLists() {
        return invoiceLists;
    }

    public void setInvoiceLists(List<InvoiceList> invoiceLists) {
        this.invoiceLists = invoiceLists;
    }
    
    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
