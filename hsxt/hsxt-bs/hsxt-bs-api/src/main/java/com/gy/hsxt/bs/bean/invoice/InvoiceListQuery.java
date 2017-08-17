/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.invoice;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * @Package :com.gy.hsxt.bs.bean.invoice
 * @ClassName : InvoiceListQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/17 15:59
 * @Version V3.0.0.0
 */
public class InvoiceListQuery extends Query {

    private static final long serialVersionUID = 5042300418061541886L;

    /**
     * 发票编号
     */
    private String invoiceId;
    /**
     * 发票号码
     */
    private String invoiceNo;
    /**
     * 发票代码
     */
    private String invoiceCode;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
}
