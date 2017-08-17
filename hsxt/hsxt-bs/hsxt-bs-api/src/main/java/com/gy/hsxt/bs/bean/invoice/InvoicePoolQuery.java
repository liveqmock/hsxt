/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.invoice;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 发票池查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.invoice
 * @ClassName : InvoicePoolQuery
 * @Description : 发票池查询实体
 * @Author : chenm
 * @Date : 2015/12/16 11:42
 * @Version V3.0.0.0
 */
public class InvoicePoolQuery extends Query {

    private static final long serialVersionUID = -4422269924554893058L;
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
     *
     * @see com.gy.hsxt.common.constant.CustType
     */
    private Integer custType;
    /**
     * 客户名称
     */
    private String custName;
    /**
     * 开票方 默认为0-平台
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker
     */
    private int invoiceMaker;

    /**
     * 业务类型
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.BizType
     */
    private String bizType;

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

    public int getInvoiceMaker() {
        return invoiceMaker;
    }

    public void setInvoiceMaker(int invoiceMaker) {
        this.invoiceMaker = invoiceMaker;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
