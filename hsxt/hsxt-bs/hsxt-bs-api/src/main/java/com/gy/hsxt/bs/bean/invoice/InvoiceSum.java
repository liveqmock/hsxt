/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.invoice;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * 发票总数实体
 *
 * @Package :com.gy.hsxt.bs.bean.invoice
 * @ClassName : InvoiceSum
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/16 10:40
 * @Version V3.0.0.0
 */
public class InvoiceSum implements Serializable {

    private static final long serialVersionUID = -3457715141735195574L;

    /**
     * 客户号
     */
    private String custId;
    /**
     * 互生号
     */
    private String resNo;
    /**
     * 客户名称
     */
    private String custName;
    /**
     * 可开发票总金额
     */
    private String remainAllAmount;

    /**
     * 已开发票总金额
     */
    private String openedAllAmount;

    /**
     * 申请中的发票总金额
     */
    private String pendingAllAmount;

    /**
     * 发票池明细
     */
    private List<InvoicePool> invoicePools;

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

    public String getRemainAllAmount() {
        return remainAllAmount;
    }

    public void setRemainAllAmount(String remainAllAmount) {
        this.remainAllAmount = remainAllAmount;
    }

    public String getOpenedAllAmount() {
        return openedAllAmount;
    }

    public void setOpenedAllAmount(String openedAllAmount) {
        this.openedAllAmount = openedAllAmount;
    }

    public String getPendingAllAmount() {
        return pendingAllAmount;
    }

    public void setPendingAllAmount(String pendingAllAmount) {
        this.pendingAllAmount = pendingAllAmount;
    }

    public List<InvoicePool> getInvoicePools() {
        return invoicePools;
    }

    public void setInvoicePools(List<InvoicePool> invoicePools) {
        this.invoicePools = invoicePools;
    }

    /**
     * 构建发票统计实体
     *
     * @param invoicePools 发票池数据列表
     * @return sum
     */
    public static InvoiceSum bulid(List<InvoicePool> invoicePools) {
        BigDecimal remainAll = new BigDecimal("0");
        BigDecimal openedAll = new BigDecimal("0");
        BigDecimal pendingAll = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(invoicePools)) {
            for (InvoicePool pool : invoicePools) {
                remainAll = remainAll.add(new BigDecimal(pool.getRemainAmount(), MathContext.DECIMAL32));
                openedAll = openedAll.add(new BigDecimal(pool.getOpenedAmount(), MathContext.DECIMAL32));
                pendingAll = pendingAll.add(new BigDecimal(pool.getPendingAmount(), MathContext.DECIMAL32));
            }
        }
        InvoiceSum invoiceSum = new InvoiceSum();
        invoiceSum.setRemainAllAmount(remainAll.toPlainString());
        invoiceSum.setOpenedAllAmount(openedAll.toPlainString());
        invoiceSum.setPendingAllAmount(pendingAll.toPlainString());
        invoiceSum.setInvoicePools(invoicePools);
        return invoiceSum;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
