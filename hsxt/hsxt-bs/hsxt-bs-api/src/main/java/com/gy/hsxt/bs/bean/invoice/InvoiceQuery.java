/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.invoice;

import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.enumtype.invoice.QueryType;

/**
 * @Package :com.gy.hsxt.bs.bean.invoice
 * @ClassName : InvoiceQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/16 12:28
 * @Version V3.0.0.0
 */
public class InvoiceQuery extends Query {

    private static final long serialVersionUID = 7928707940203929006L;
    /**
     * 企业客户号
     */
    private String custId;
    /**
     * 企业互生号
     */
    private String resNo;
    /**
     * 企业名称
     */
    private String custName;

    /**
     * 发票状态 0-待审核  1-待开票/审核通过 2-待配送/开票成 3-待签收/已配送 4-已签收 5-审核驳回
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.InvoiceStatus
     */
    private Integer status;

    /**
     * 业务类型
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.BizType
     */
    private String bizType;

    /**
     * 发票开票方
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker
     */
    private int invoiceMaker;

    /**
     *查询状态 0-全部查询 1-审核查询 2-配送查询
     *
     * @see QueryType
     */
    private int queryType;

    /**
     * 是否为平台查询 true-为平台查询
     */
    private boolean platQuery;

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

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public int getInvoiceMaker() {
        return invoiceMaker;
    }

    public void setInvoiceMaker(int invoiceMaker) {
        this.invoiceMaker = invoiceMaker;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public boolean isPlatQuery() {
        return platQuery;
    }

    public void setPlatQuery(boolean platQuery) {
        this.platQuery = platQuery;
    }
}
