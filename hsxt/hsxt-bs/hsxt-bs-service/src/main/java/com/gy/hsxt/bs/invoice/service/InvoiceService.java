/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.invoice.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.invoice.*;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceStatus;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.invoice.interfaces.IInvoiceCustService;
import com.gy.hsxt.bs.invoice.interfaces.IInvoicePlatService;
import com.gy.hsxt.bs.invoice.interfaces.IInvoicePoolService;
import com.gy.hsxt.bs.invoice.interfaces.IInvoiceService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 企业发票管理业务层
 *
 * @Package :com.gy.hsxt.bs.invoice.interfaces
 * @ClassName : IInvoiceService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/16 10:24
 * @Version V3.0.0.0
 */
@Service
public class InvoiceService implements IInvoiceService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 发票池业务接口
     */
    @Resource
    private IInvoicePoolService invoicePoolService;

    /**
     * 平台发票业务接口
     */
    @Resource
    private IInvoicePlatService invoicePlatService;

    /**
     * 客户发票业务接口
     */
    @Resource
    private IInvoiceCustService invoiceCustService;

    /**
     * 查询发票统计总数
     *
     * @param invoicePoolQuery 查询实体
     * @return sum
     * @throws HsException
     */
    @Override
    public InvoiceSum queryInvoiceSum(InvoicePoolQuery invoicePoolQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询发票统计总数参数[invoicePoolQuery]:" + invoicePoolQuery);
        //参数校验不能为空
        HsAssert.notNull(invoicePoolQuery, RespCode.PARAM_ERROR, "查询实体[invoicePoolQuery]为空");
        HsAssert.hasText(invoicePoolQuery.getCustId(), RespCode.PARAM_ERROR, "客户号[custId]为空");
        //查询发票池数据列表
        List<InvoicePool> pools = invoicePoolService.queryListByQuery(invoicePoolQuery);
        //构建发票统计实体
        InvoiceSum sum = InvoiceSum.bulid(pools);
        sum.setCustId(invoicePoolQuery.getCustId());
        return sum;
    }

    /**
     * 分页查询发票池数据列表
     *
     * @param page             分页对象
     * @param invoicePoolQuery 查询对象
     * @return list
     * @throws HsException
     */
    @Override
    public PageData<InvoicePool> queryInvoicePoolListForPage(Page page, InvoicePoolQuery invoicePoolQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryInvoicePoolListForPage", "分页查询发票池数据列表参数[invoicePoolQuery]:" + invoicePoolQuery);
        //分页查询发票池数据列表
        List<InvoicePool> pools = invoicePoolService.queryListForPage(page, invoicePoolQuery);
        //构建数据
        return PageData.bulid(page.getCount(), pools);
    }

    /**
     * 分页查询
     *
     * @param page         分页对象
     * @param invoiceQuery 查询对象
     * @return data
     * @throws HsException
     */
    @Override
    public PageData<Invoice> queryInvoiceListForPage(Page page, InvoiceQuery invoiceQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryInvoiceListForPage", "分页查询发票数据列表参数[invoiceQuery]:" + invoiceQuery);
        //校验参数
        HsAssert.notNull(invoiceQuery, RespCode.PARAM_ERROR, "分页查询发票数据列表参数[invoiceQuery]为null");
        HsAssert.isTrue(InvoiceMaker.check(invoiceQuery.getInvoiceMaker()), RespCode.PARAM_ERROR, "开票方[invoiceMaker]类型错误");

        List<Invoice> details = new ArrayList<>();
        //平台发票查询
        if (InvoiceMaker.PLAT.ordinal() == invoiceQuery.getInvoiceMaker()) {
            List<InvoicePlat> invoicePlats = invoicePlatService.queryListForPage(page, invoiceQuery);
            details.addAll(invoicePlats);
        } else {
            //客户发票查询
            List<InvoiceCust> invoiceCusts = invoiceCustService.queryListForPage(page, invoiceQuery);
            details.addAll(invoiceCusts);
        }
        return PageData.bulid(page.getCount(), details);
    }

    /**
     * 查询发票详情
     *
     * @param invoiceId    发票ID
     * @param invoiceMaker 开票方
     * @return invoice
     * @throws HsException
     */
    @Override
    public Invoice queryInvoiceDetail(String invoiceId, InvoiceMaker invoiceMaker) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryInvoiceDetail", "分页查询发票数据详情参数[invoiceId]:" + invoiceId + ",开票方[invoiceMaker]:" + invoiceMaker);
        Invoice invoice;
        //开票方是平台
        if (InvoiceMaker.PLAT.equals(invoiceMaker)) {
            invoice = invoicePlatService.queryBeanById(invoiceId);
        } else {
            //开票方是客户
            invoice = invoiceCustService.queryBeanById(invoiceId);
        }
        return invoice;
    }

    /**
     * 客户开发票
     *
     * @param invoiceCust 发票
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean custOpenInvoice(InvoiceCust invoiceCust) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:custOpenInvoice", "客户开发票参数[invoiceCust]:" + invoiceCust);
        //参数校验
        HsAssert.notNull(invoiceCust, RespCode.PARAM_ERROR, "发票详情[invoiceCust]为null");
        //设置发票状态为待签收
//        invoiceCust.setStatus(InvoiceStatus.POSTING.ordinal());
        //保存发票详情
        String invoiceId = invoiceCustService.saveBean(invoiceCust);
        //返回发票ID
        return StringUtils.isNotBlank(invoiceId);
    }

    /**
     * 客户向平台申请发票
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean custApplyInvoice(InvoicePlat invoicePlat) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:custApplyInvoice", "客户向平台申请发票参数[invoicePlat]:" + invoicePlat);
        HsAssert.hasText(invoicePlat.getApplyOperator(), RespCode.PARAM_ERROR, "申请操作人[applyOperator]为空");
//        HsAssert.isTrue(StringUtils.isBlank(invoicePlat.getOpenOperator()), RespCode.PARAM_ERROR, "经办人[openOperator]必须为空");
        //设置发票状态 -- 待审核状态
        invoicePlat.setStatus(InvoiceStatus.PENDING.ordinal());
        String invoiceId = invoicePlatService.saveBean(invoicePlat);
        //返回发票ID
        return StringUtils.isNotBlank(invoiceId);
    }

    /**
     * 平台审批发票
     * 包含驳回 和 开发票
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean platApproveInvoice(InvoicePlat invoicePlat) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:platApproveInvoice", "平台审批发票参数[invoicePlat]:" + invoicePlat);
        //参数校验
        HsAssert.notNull(invoicePlat, RespCode.PARAM_ERROR, "平台发票[invoicePlat]为空");
        Integer status = invoicePlat.getStatus();
        //校验审核状态
        boolean check = status != null &&(InvoiceStatus.REJECTED.ordinal() == status || InvoiceStatus.POSTING.ordinal() == status);
        HsAssert.isTrue(check, RespCode.PARAM_ERROR, "发票审核的状态[status]错误，请选择 驳回-REJECTED 或 已开票/待配送 - POSTING");
        //更新平台发票
        int count = invoicePlatService.modifyBean(invoicePlat);

        return count == 1;
    }

    /**
     * 平台开发票
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean platOpenInvoice(InvoicePlat invoicePlat) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:platOpenInvoice", "平台开发票参数[invoicePlat]:" + invoicePlat);
        //参数校验
        HsAssert.notNull(invoicePlat, RespCode.PARAM_ERROR, "平台发票[invoicePlat]为空");
//        HsAssert.isTrue(StringUtils.isBlank(invoicePlat.getApplyOperator()), RespCode.PARAM_ERROR, "经办人[applyOperator]必须为空");
        //设置发票状态 - 待配送/已开票
        if (invoicePlat.getStatus() == null) {
            invoicePlat.setStatus(InvoiceStatus.POSTING.ordinal());
        }

        String invoiceId = invoicePlatService.saveBean(invoicePlat);

        return StringUtils.isNotBlank(invoiceId);
    }

    /**
     * 发票签收/拒签
     *
     * @param invoice      发票
     * @param invoiceMaker 开票方
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean signInvoice(Invoice invoice, InvoiceMaker invoiceMaker) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:signInvoice", "发票签收/拒签参数[invoice]:" + invoice + ",开票方[invoiceMaker]:" + invoiceMaker);
        int count;
        //开票方是平台
        if (InvoiceMaker.PLAT.equals(invoiceMaker)) {
            //参数校验
            HsAssert.isInstanceOf(InvoicePlat.class, invoice, RespCode.PARAM_ERROR, "发票信息[invoice]不是InvoicePlat类型");
//            HsAssert.isTrue(StringUtils.isBlank(invoice.getOpenOperator()), RespCode.PARAM_ERROR, "经办人[openOperator]必须为空");
            InvoicePlat invoicePlat = (InvoicePlat) invoice;
            //更新平台发票
            count = invoicePlatService.modifyBean(invoicePlat);
        } else {//开票方是客户
            //参数校验
            HsAssert.isInstanceOf(InvoiceCust.class, invoice, RespCode.PARAM_ERROR, "发票信息[invoice]不是InvoiceCust类型");
            InvoiceCust invoiceCust = (InvoiceCust) invoice;
            //更新客户发票
            count = invoiceCustService.modifyBean(invoiceCust);
        }
        return count == 1;
    }

    /**
     * 发票配送
     *
     * @param invoice 发票
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean changePostWay(Invoice invoice) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:changePostWay", "平台修改配送方式参数[invoice]:" + invoice);
        HsAssert.notNull(invoice, BSRespCode.BS_PARAMS_NULL, "发票[invoice]为空");
        invoice.setStatus(InvoiceStatus.RECEIVING.ordinal());
        int count;
        if (invoice instanceof InvoicePlat) {
            //平台修改配送方式
            count = invoicePlatService.modifyPostWay((InvoicePlat) invoice);
        } else {
            count = invoiceCustService.modifyBean((InvoiceCust) invoice);
        }
        return count == 1;
    }
}
