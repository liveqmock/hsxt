/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.invoice;

import com.gy.hsxt.bs.bean.invoice.*;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 企业发票管理对外接口类
 *
 * @Package :com.gy.hsxt.bs.invoice.interfaces
 * @ClassName : IBSInvoiceService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/16 10:24
 * @Version V3.0.0.0
 */
public interface IBSInvoiceService {

    /**
     * 查询发票统计总数
     *
     * @param invoicePoolQuery 查询实体
     * @return sum
     * @throws HsException
     */
    InvoiceSum queryInvoiceSum(InvoicePoolQuery invoicePoolQuery) throws HsException;


    /**
     * 分页查询发票池数据列表
     *
     * @param page             分页对象
     * @param invoicePoolQuery 查询对象
     * @return list
     * @throws HsException
     */
    PageData<InvoicePool> queryInvoicePoolListForPage(Page page, InvoicePoolQuery invoicePoolQuery) throws HsException;


    /**
     * 分页查询发票
     *
     * @param page         分页对象
     * @param invoiceQuery 查询对象
     * @return data
     * @throws HsException
     */
    PageData<Invoice> queryInvoiceListForPage(Page page, InvoiceQuery invoiceQuery) throws HsException;


    /**
     * 查询发票详情
     *
     * @param invoiceId    发票ID
     * @param invoiceMaker 开票方
     * @return invoice
     * @throws HsException
     */
    Invoice queryInvoiceDetail(String invoiceId, InvoiceMaker invoiceMaker) throws HsException;


    /**
     * 客户开发票
     *
     * @param invoiceCust 发票
     * @return boolean
     * @throws HsException
     */
    boolean custOpenInvoice(InvoiceCust invoiceCust) throws HsException;


    /**
     * 客户向平台申请发票
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    boolean custApplyInvoice(InvoicePlat invoicePlat) throws HsException;

    /**
     * 平台审批发票
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    boolean platApproveInvoice(InvoicePlat invoicePlat) throws HsException;

    /**
     * 平台开发票
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    boolean platOpenInvoice(InvoicePlat invoicePlat) throws HsException;

    /**
     * 发票签收/拒签
     *
     * @param invoice      发票
     * @param invoiceMaker 开票方
     * @return boolean
     * @throws HsException
     */
    boolean signInvoice(Invoice invoice, InvoiceMaker invoiceMaker) throws HsException;

    /**
     * 发票配送
     *
     * @param invoice      发票
     * @return boolean
     * @throws HsException
     */
    boolean changePostWay(Invoice invoice) throws HsException;
}
