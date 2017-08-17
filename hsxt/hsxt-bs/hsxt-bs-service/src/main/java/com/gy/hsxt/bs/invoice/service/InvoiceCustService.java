/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.invoice.*;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.invoice.*;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.invoice.interfaces.IInvoiceCustService;
import com.gy.hsxt.bs.invoice.mapper.InvoiceCustMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户发票业务接口实现
 *
 * @Package :com.gy.hsxt.bs.invoice.service
 * @ClassName : InvoiceCustService
 * @Description : 客户发票业务接口实现
 * @Author : chenm
 * @Date : 2015/12/16 15:11
 * @Version V3.0.0.0
 */
@Service
public class InvoiceCustService implements IInvoiceCustService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 客户发票DAO接口
     */
    @Resource
    private InvoiceCustMapper invoiceCustMapper;

    /**
     * 发票池业务接口
     */
    @Resource
    private InvoicePoolService invoicePoolService;

    /**
     * 发票清单业务接口
     */
    @Resource
    private InvoiceListService invoiceListService;

    /**
     * 保存实体
     *
     * @param invoiceCust 实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(InvoiceCust invoiceCust) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存客户发票信息参数：" + invoiceCust);
        //校验参数
        HsAssert.notNull(invoiceCust, RespCode.PARAM_ERROR, "客户发票信息实体[invoiceCust]为null");
        HsAssert.hasText(invoiceCust.getCustId(), RespCode.PARAM_ERROR, "客户号[custId]为空");
        HsAssert.hasText(invoiceCust.getResNo(), RespCode.PARAM_ERROR, "互生号[resNo]为空");
        HsAssert.hasText(invoiceCust.getCustName(), RespCode.PARAM_ERROR, "客户名称[custName]为空");
        HsAssert.hasText(invoiceCust.getAddress(), RespCode.PARAM_ERROR, "地址[address]为空");
        HsAssert.hasText(invoiceCust.getAllAmount(), RespCode.PARAM_ERROR, "发票金额[allAmount]为空");
        HsAssert.hasText(invoiceCust.getBankBranchName(), RespCode.PARAM_ERROR, "银行支行名称[bankBranchName]为空");
        HsAssert.hasText(invoiceCust.getBankNo(), RespCode.PARAM_ERROR, "银行账号[bankNo]为空");
        HsAssert.hasText(invoiceCust.getOpenContent(), RespCode.PARAM_ERROR, "开票内容[openContent]为空");
//        HsAssert.hasText(invoiceCust.getOpenOperator(), RespCode.PARAM_ERROR, "开票人[openOperator]为空");
        HsAssert.hasText(invoiceCust.getTaxpayerNo(), RespCode.PARAM_ERROR, "纳税人识别号[taxpayerNo]为空");
        HsAssert.hasText(invoiceCust.getTelephone(), RespCode.PARAM_ERROR, "联系电话[telephone]为空");
        HsAssert.isTrue(InvoiceType.check(invoiceCust.getInvoiceType()), RespCode.PARAM_ERROR, "发票类型[invoiceType]错误");
        HsAssert.isTrue(CollectionUtils.isNotEmpty(invoiceCust.getInvoiceLists()), RespCode.PARAM_ERROR, "发票清单列表[invoiceLists]为空");
        //查询发票统计数据
        InvoicePool invoicePool = invoicePoolService.queryBeanByIdAndType(invoiceCust.getCustId(), BizType.CP_ALL_INVOICE.getBizCode());
        //发票申请必须一次性全部申请完剩余金额
        if (invoicePool != null) {
            HsAssert.isTrue(BigDecimalUtils.compareTo(invoiceCust.getAllAmount(), invoicePool.getRemainAmount()) >= 0, BSRespCode.BS_INVOICE_NOT_ENOUGH, "发票金额[allAmount]必须大于应开发票金额");
        }

        try {
            //设置发票ID
            invoiceCust.setInvoiceId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));

            int count = invoiceCustMapper.insertInvoiceCust(invoiceCust);

            //计算发票池金额
            calcInvoicePool(invoiceCust);
            //保存成功之后，保存发票清单
            if (count == 1) {
                for (InvoiceList invoiceList : invoiceCust.getInvoiceLists()) {
                    invoiceList.setInvoiceId(invoiceCust.getInvoiceId());
                    //保存发票清单
                    invoiceListService.saveBean(invoiceList);
                }
            }
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + invoiceCust, "保存客户发票信息成功");
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存客户发票信息失败，参数[invoiceCust]:" + invoiceCust, e);
            throw new HsException(BSRespCode.BS_INVOICE_CUST_DB_ERROR, "保存客户发票信息失败,原因:" + e.getMessage());
        }
        return invoiceCust.getInvoiceId();
    }

    /**
     * 更新实体
     *
     * @param invoiceCust 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(InvoiceCust invoiceCust) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "更新发票参数[invoiceCust]:" + invoiceCust);
        //校验参数
        HsAssert.notNull(invoiceCust, RespCode.PARAM_ERROR, "客户发票[invoiceCust]为空");
        HsAssert.hasText(invoiceCust.getInvoiceId(), RespCode.PARAM_ERROR, "发票ID[invoiceId]为空");
        HsAssert.hasText(invoiceCust.getUpdatedBy(), RespCode.PARAM_ERROR, "操作者[updatedBy]为空");
        try {
            InvoiceCust invoiceInDb = invoiceCustMapper.selectBeanById(invoiceCust.getInvoiceId());
            HsAssert.notNull(invoiceInDb, BSRespCode.BS_PARAMS_NULL, "发票ID[invoiceId]对应发票不存在");
            if (invoiceCust.getStatus() == InvoiceStatus.RECEIVED.ordinal()) {
                HsAssert.isTrue(invoiceInDb.getStatus() != InvoiceStatus.RECEIVED.ordinal(),BSRespCode.BS_INVOICE_ALREADY_SIGN,"发票已签收，请勿重复提交");
            }
            invoiceCust.setCustId(invoiceInDb.getCustId());
            invoiceCust.setAllAmount(invoiceInDb.getAllAmount());
            invoiceCust.setResNo(invoiceInDb.getResNo());
            invoiceCust.setCustName(invoiceInDb.getCustName());
            //已配送状态下的校验
            if (invoiceCust.getStatus() == InvoiceStatus.RECEIVING.ordinal()) {
                HsAssert.isTrue(PostWay.check(invoiceCust.getPostWay()), RespCode.PARAM_ERROR, "配送方式[postWay]错误");
                //快递方式需要校验
                if (PostWay.EXPRESS.ordinal() == invoiceCust.getPostWay()) {
                    HsAssert.hasText(invoiceCust.getExpressName(), RespCode.PARAM_ERROR, "快递公司名称[expressName]为空");
                    HsAssert.hasText(invoiceCust.getTrackingNo(), RespCode.PARAM_ERROR, "运单号[trackingNo]为空");
                }
            }
            //计算发票池金额
            calcInvoicePool(invoiceCust);

            //更新发票记录
            return invoiceCustMapper.updateBean(invoiceCust);
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "更新客户发票信息失败，参数[invoiceCust]:" + invoiceCust, e);
            throw new HsException(BSRespCode.BS_INVOICE_CUST_DB_ERROR, "更新客户发票信息失败，原因:" + e.getMessage());
        }
    }

    /**
     * 计算发票池金额
     *
     * @param invoiceCust 客户发票
     */
    private void calcInvoicePool(InvoiceCust invoiceCust) {
        //签收情况下，才进行总数计算
        if (invoiceCust.getStatus() == InvoiceStatus.RECEIVED.ordinal()) {
            //查询发票统计数据
            InvoicePool invoicePool = invoicePoolService.queryBeanByIdAndType(invoiceCust.getCustId(), BizType.CP_ALL_INVOICE.getBizCode());
            if (invoicePool != null) {
                //发票申请必须一次性全部申请完剩余金额
                HsAssert.isTrue(BigDecimalUtils.compareTo(invoiceCust.getAllAmount(), invoicePool.getRemainAmount()) >= 0, BSRespCode.BS_INVOICE_NOT_ENOUGH, "发票金额[allAmount]必须大于应开发票金额");
                BigDecimal remain = BigDecimalUtils.floorSub(invoicePool.getRemainAmount(), invoiceCust.getAllAmount());
                //构建更新发票池实体
                InvoicePool updatePool = InvoicePool.bulid(invoiceCust.getCustId(), invoicePool.getBizType());
                //设置剩余金额为0
                updatePool.setRemainAmount(remain.toPlainString());
                //增加计算已开票金额
                updatePool.setOpenedAmount(BigDecimalUtils.ceilingAdd(invoiceCust.getAllAmount(), invoicePool.getOpenedAmount()).toPlainString());
                //更新发票统计数据
                invoicePoolService.modifyBean(updatePool);
            } else {
                invoicePool = InvoicePool.bulid(invoiceCust.getCustId(), BizType.CP_ALL_INVOICE.getBizCode());
                invoicePool.setResNo(invoiceCust.getResNo());
                invoicePool.setCustName(invoiceCust.getCustName());
                invoicePool.setInvoiceMaker(InvoiceMaker.CUST.ordinal());
                invoicePool.setAllAmount("0");
                invoicePool.setRemainAmount(BigDecimalUtils.floorSub("0", invoiceCust.getAllAmount()).toPlainString());
                invoicePool.setOpenedAmount(invoiceCust.getAllAmount());
                invoicePool.setLastDate(FssDateUtil.obtainToday(FssDateUtil.DEFAULT_DATE_FORMAT));
                invoicePoolService.saveBean(invoicePool);
            }
        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    @Override
    public InvoiceCust queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询客户发票[invoiceId]:" + id);
        //校验参数
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "发票ID[invoiceId]为空");
        try {
            InvoiceCust invoiceCust = invoiceCustMapper.selectBeanById(id);
            if (invoiceCust != null) {
                InvoiceListQuery invoiceListQuery = new InvoiceListQuery();
                invoiceListQuery.setInvoiceId(id);
                //查询发票清单列表
                List<InvoiceList> invoiceLists = invoiceListService.queryListByQuery(invoiceListQuery);
                invoiceCust.setInvoiceLists(invoiceLists);
            }
            return invoiceCust;
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "查询客户[" + id + "]发票数据失败", e);
            throw new HsException(BSRespCode.BS_INVOICE_CUST_DB_ERROR, "查询客户发票数据失败,原因：" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<InvoiceCust> queryListByQuery(Query query) throws HsException {
        return null;
    }

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<InvoiceCust> queryListForPage(Page page, Query query) throws HsException {

        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询客户发票数据列表参数[query]:" + query);
        //校验参数
        HsAssert.notNull(query, RespCode.PARAM_ERROR, "查询实体[query]为null");
        HsAssert.isInstanceOf(InvoiceQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是InvoiceQuery类型");

        InvoiceQuery invoiceQuery = (InvoiceQuery) query;
        //校验日期
        invoiceQuery.checkDateFormat();
        //设置分页上下文
        PageContext.setPage(page);

        try {
            return invoiceCustMapper.selectBeanListPage(invoiceQuery);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询客户发票数据列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_INVOICE_CUST_DB_ERROR, "分页查询客户发票数据列表失败,原因：" + e.getMessage());
        }
    }
}
