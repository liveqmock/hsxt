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
import com.gy.hsxt.bs.common.enumtype.invoice.BizType;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceStatus;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceType;
import com.gy.hsxt.bs.common.enumtype.invoice.PostWay;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.invoice.interfaces.IInvoicePlatService;
import com.gy.hsxt.bs.invoice.mapper.InvoicePlatMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 平台发票业务层接口实现
 *
 * @Package :com.gy.hsxt.bs.invoice.service
 * @ClassName : InvoiceApplyService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 20:40
 * @Version V3.0.0.0
 */
@Service
public class InvoicePlatService implements IInvoicePlatService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 平台发票DAO接口
     */
    @Resource
    private InvoicePlatMapper invoicePlatMapper;

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
     * @param invoicePlat 实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(InvoicePlat invoicePlat) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存平台发票信息参数：" + invoicePlat);
        //校验参数
        HsAssert.notNull(invoicePlat, RespCode.PARAM_ERROR, "平台发票信息实体[invoicePlat]为null");
        HsAssert.hasText(invoicePlat.getCustId(), RespCode.PARAM_ERROR, "客户号[custId]为空");
        HsAssert.hasText(invoicePlat.getResNo(), RespCode.PARAM_ERROR, "互生号[resNo]为空");
        HsAssert.hasText(invoicePlat.getCustName(), RespCode.PARAM_ERROR, "客户名称[custName]为空");
//        HsAssert.hasText(invoicePlat.getAddress(), RespCode.PARAM_ERROR, "地址[address]为空");
        HsAssert.hasText(invoicePlat.getInvoiceTitle(), RespCode.PARAM_ERROR, "发票抬头[invoiceTile]为空");
        HsAssert.hasText(invoicePlat.getAllAmount(), RespCode.PARAM_ERROR, "发票金额[allAmount]为空");
//        HsAssert.hasText(invoicePlat.getBankBranchName(), RespCode.PARAM_ERROR, "银行支行名称[bankBranchName]为空");
//        HsAssert.hasText(invoicePlat.getBankNo(), RespCode.PARAM_ERROR, "银行账号[bankNo]为空");
        HsAssert.hasText(invoicePlat.getOpenContent(), RespCode.PARAM_ERROR, "开票内容[openContent]为空");
//        HsAssert.hasText(invoicePlat.getTaxpayerNo(), RespCode.PARAM_ERROR, "纳税人识别号[taxpayerNo]为空");
//        HsAssert.hasText(invoicePlat.getTelephone(), RespCode.PARAM_ERROR, "联系电话[telephone]为空");
        HsAssert.isTrue(InvoiceType.check(invoicePlat.getInvoiceType()), RespCode.PARAM_ERROR, "发票类型[invoiceType]错误");
        HsAssert.isTrue(BizType.check(invoicePlat.getBizType()), RespCode.PARAM_ERROR, "业务类型[bizType]错误");
        //消费积分扣除累计金额至少1000元才可开发票
        if (BizType.PC_CONSUME_POINTS.getBizCode().equals(invoicePlat.getBizType())) {
            HsAssert.isTrue(BigDecimalUtils.floor(invoicePlat.getAllAmount()).compareTo(BigDecimal.valueOf(1000)) >= 0, BSRespCode.BS_INVOICE_GREAT_1000, "消费积分扣除累计金额至少1000元才可开发票");
        }
        //设置发票ID
        invoicePlat.setInvoiceId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
        invoicePlat.setApplyTime(DateUtil.getCurrentDateTime());
        //企业主动开票
        if (InvoiceStatus.POSTING.ordinal() == invoicePlat.getStatus()) {
            invoicePlat.setOpenTime(DateUtil.getCurrentDateTime());
            //开票参数校验及发票池更新
            openInvoiceValidate(invoicePlat, true);
        } else if (InvoiceStatus.PENDING.ordinal() == invoicePlat.getStatus()) {
            //申请开票参数校验及发票池更新
            pendingInvoiceValidate(invoicePlat);
        }
        try {
            invoicePlatMapper.insertInvoicePlat(invoicePlat);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + invoicePlat, "保存平台发票信息成功");
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存平台发票信息失败，参数[invoicePlat]:" + invoicePlat, e);
            throw new HsException(BSRespCode.BS_INVOICE_PLAT_DB_ERROR, "保存平台发票信息失败,原因：" + e.getMessage());
        }
        return invoicePlat.getInvoiceId();
    }

    /**
     * 更新实体
     *
     * @param invoicePlat 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(InvoicePlat invoicePlat) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "更新平台发票参数[invoicePlat]:" + invoicePlat);
        //校验参数
        HsAssert.notNull(invoicePlat, RespCode.PARAM_ERROR, "平台发票[invoicePlat]为空");
        HsAssert.hasText(invoicePlat.getInvoiceId(), RespCode.PARAM_ERROR, "发票ID[invoiceId]为空");
        InvoicePlat invoiceDB = queryBeanById(invoicePlat.getInvoiceId());
        HsAssert.notNull(invoiceDB, BSRespCode.BS_INVOICE_PLAT_NULL, "[" + invoicePlat.getInvoiceId() + "]对应的平台发票不存在");
        invoicePlat.setBizType(invoiceDB.getBizType());//设置业务类型
        invoicePlat.setCustId(invoiceDB.getCustId());//设置客户号
        HsAssert.hasText(invoicePlat.getUpdatedBy(), RespCode.PARAM_ERROR, "操作者[updatedBy]为空");
        //有两种情况：一种是审核通过直接开票 ，一种是审核驳回
        //开票参数校验
        if (InvoiceStatus.POSTING.ordinal() == invoicePlat.getStatus()) {
            invoicePlat.setOpenTime(DateUtil.getCurrentDateTime());
            HsAssert.isTrue(BigDecimalUtils.compareTo(invoiceDB.getAllAmount(), invoicePlat.getAllAmount()) == 0, BSRespCode.BS_INVOICE_AMOUNT_NOT_EQUAL, "发票金额[allAmount]错误");
            //开票参数校验及发票池更新
            openInvoiceValidate(invoicePlat, false);
        } else if (InvoiceStatus.REJECTED.ordinal() == invoicePlat.getStatus()) {
            rejectedInvoiceValidate(invoicePlat);
        }
        try {
            //更新发票记录
            return invoicePlatMapper.updateBean(invoicePlat);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "更新平台发票信息失败,参数[invoicePlat]:" + invoicePlat, e);
            throw new HsException(BSRespCode.BS_INVOICE_PLAT_DB_ERROR, "更新平台发票信息失败,原因：" + e.getMessage());
        }
    }

    /**
     * 开票参数校验及发票池更新
     *
     * @param invoicePlat 平台发票
     * @param direct      是否直接开发票
     * @throws HsException
     */
    private void openInvoiceValidate(InvoicePlat invoicePlat, boolean direct) throws HsException {
//        HsAssert.hasText(invoicePlat.getOpenOperator(), RespCode.PARAM_ERROR, "经办人[openOperator]为空");

        HsAssert.isTrue(CollectionUtils.isNotEmpty(invoicePlat.getInvoiceLists()), RespCode.PARAM_ERROR, "发票清单列表[invoiceLists]为空");
        //查询发票统计数据
        InvoicePool invoicePool = invoicePoolService.queryBeanByIdAndType(invoicePlat.getCustId(), invoicePlat.getBizType());
        HsAssert.notNull(invoicePool, BSRespCode.BS_INVOICE_POOL_NULL, "客户号[" + invoicePlat.getCustId() + "]的[" + invoicePlat.getBizType() + "]业务对应的发票统计数据为null");
        if (direct) {
            HsAssert.isTrue(BigDecimalUtils.compareTo(invoicePool.getRemainAmount(), "0") > 0, BSRespCode.BS_INVOICE_POOL_OVER, "该业务类型的发票金额已开完");
        }
        //除了消费积分扣除 ，其他发票申请必须一次性全部开完剩余金额
        //构建更新发票池实体
        InvoicePool updatePool = InvoicePool.bulid(invoicePlat.getCustId(), invoicePlat.getBizType());
        if (direct) {//直接开发票
            BigDecimal remain = BigDecimalUtils.floorSub(invoicePool.getRemainAmount(), invoicePlat.getAllAmount());
            if (!BizType.PC_CONSUME_POINTS.getBizCode().equals(invoicePlat.getBizType())) {
                HsAssert.isTrue(remain.doubleValue() == 0, BSRespCode.BS_INVOICE_AMOUNT_NOT_EQUAL, "发票金额[allAmount]错误，除了消费积分扣除，其他发票申请必须一次性全部开完剩余金额");
            }
            //计算待开发票金额
            updatePool.setRemainAmount(BigDecimalUtils.floor(remain.toPlainString(), 2).toPlainString());
        } else {//审核开发票
            //计算待审批金额
            String pending = BigDecimalUtils.floorSub(invoicePool.getPendingAmount(), invoicePlat.getAllAmount()).toPlainString();
            updatePool.setPendingAmount(BigDecimalUtils.ceiling(pending, 2).toPlainString());
        }
        //增加计算已开票金额
        String opened = BigDecimalUtils.ceilingAdd(invoicePlat.getAllAmount(), invoicePool.getOpenedAmount()).toPlainString();
        updatePool.setOpenedAmount(BigDecimalUtils.ceiling(opened, 2).toPlainString());
        //更新发票统计数据
        invoicePoolService.modifyBean(updatePool);

        //保存发票清单
        for (InvoiceList invoiceList : invoicePlat.getInvoiceLists()) {
            //设置发票ID
            invoiceList.setInvoiceId(invoicePlat.getInvoiceId());
            invoiceListService.saveBean(invoiceList);
        }
    }

    /**
     * 申请开票参数校验及发票池更新
     *
     * @param invoicePlat 平台发票
     * @throws HsException
     */
    private void pendingInvoiceValidate(InvoicePlat invoicePlat) throws HsException {
        //查询发票统计数据
        InvoicePool invoicePool = invoicePoolService.queryBeanByIdAndType(invoicePlat.getCustId(), invoicePlat.getBizType());
        HsAssert.notNull(invoicePool, BSRespCode.BS_INVOICE_POOL_NULL, "客户号[" + invoicePlat.getCustId() + "]的[" + invoicePlat.getBizType() + "]业务对应的发票统计数据为null");
        HsAssert.isTrue(BigDecimalUtils.compareTo(invoicePool.getRemainAmount(), "0") > 0, BSRespCode.BS_INVOICE_POOL_OVER, "该业务类型的发票金额已开完");
        //除了消费积分扣除 ，其他发票申请必须一次性全部开完剩余金额
        BigDecimal remain = BigDecimalUtils.floorSub(invoicePool.getRemainAmount(), invoicePlat.getAllAmount());
        if (!BizType.PC_CONSUME_POINTS.getBizCode().equals(invoicePlat.getBizType())) {
            HsAssert.isTrue(remain.doubleValue() == 0, BSRespCode.BS_INVOICE_AMOUNT_NOT_EQUAL, "发票金额[allAmount]错误，除了消费积分扣除，其他发票申请必须一次性全部开完剩余金额");
        }
        //构建更新发票池实体
        InvoicePool updatePool = InvoicePool.bulid(invoicePlat.getCustId(), invoicePlat.getBizType());
        //设置剩余金额为0
        updatePool.setRemainAmount(BigDecimalUtils.floor(remain.toPlainString(), 2).toPlainString());
        //增加计算申请开票金额
        String pending = BigDecimalUtils.ceilingAdd(invoicePlat.getAllAmount(), invoicePool.getPendingAmount()).toPlainString();
        updatePool.setPendingAmount(BigDecimalUtils.ceiling(pending, 2).toPlainString());
        //更新发票统计数据
        invoicePoolService.modifyBean(updatePool);
    }

    /**
     * 驳回发票申请验证与处理
     *
     * @param invoicePlat 平台发票
     * @throws HsException
     */
    private void rejectedInvoiceValidate(InvoicePlat invoicePlat) throws HsException {
        //查询发票统计数据
        InvoicePool invoicePool = invoicePoolService.queryBeanByIdAndType(invoicePlat.getCustId(), invoicePlat.getBizType());
        HsAssert.notNull(invoicePool, BSRespCode.BS_INVOICE_POOL_NULL, "客户号[" + invoicePlat.getCustId() + "]的[" + invoicePlat.getBizType() + "]业务对应的发票统计数据为null");
        //驳回时，剩余金额=原剩余金额+发票金额
        BigDecimal remain = BigDecimalUtils.floorAdd(invoicePool.getRemainAmount(), invoicePlat.getAllAmount());
        //构建更新发票池实体
        InvoicePool updatePool = InvoicePool.bulid(invoicePlat.getCustId(), invoicePlat.getBizType());
        //设置剩余金额
        updatePool.setRemainAmount(BigDecimalUtils.floor(remain.toPlainString(), 2).toPlainString());
        //减少计算申请开票金额
        String pending = BigDecimalUtils.ceilingSub(invoicePool.getPendingAmount(), invoicePlat.getAllAmount()).toPlainString();
        updatePool.setPendingAmount(BigDecimalUtils.ceiling(pending, 2).toPlainString());
        //更新发票统计数据
        invoicePoolService.modifyBean(updatePool);
    }


    /**
     * 修改配送方式
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    @Override
    public int modifyPostWay(InvoicePlat invoicePlat) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyPostWay", "平台修改配送方式参数[invoicePlat]:" + invoicePlat);
        //校验参数
        HsAssert.notNull(invoicePlat, RespCode.PARAM_ERROR, "平台发票[invoicePlat]为空");
        HsAssert.hasText(invoicePlat.getInvoiceId(), RespCode.PARAM_ERROR, "发票ID[invoiceId]为空");
        InvoicePlat invoiceDB = queryBeanById(invoicePlat.getInvoiceId());
        HsAssert.notNull(invoiceDB, BSRespCode.BS_INVOICE_PLAT_NULL, "[" + invoicePlat.getInvoiceId() + "]对应的平台发票不存在");
        HsAssert.hasText(invoicePlat.getUpdatedBy(), RespCode.PARAM_ERROR, "操作者[updatedBy]为空");
        HsAssert.isTrue(PostWay.check(invoicePlat.getPostWay()), RespCode.PARAM_ERROR, "配送方式[postWay]错误");
        //快递方式需要校验
        if (PostWay.EXPRESS.ordinal() == invoicePlat.getPostWay()) {
            HsAssert.hasText(invoicePlat.getExpressName(), RespCode.PARAM_ERROR, "快递公司名称[expressName]为空");
            HsAssert.hasText(invoicePlat.getTrackingNo(), RespCode.PARAM_ERROR, "运单号[trackingNo]为空");
        }
        try {
            //更新发票记录
            return invoicePlatMapper.updateBean(invoicePlat);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:modifyPostWay", "平台修改配送方式失败，参数[invoicePlat]:" + invoicePlat, e);
            throw new HsException(BSRespCode.BS_INVOICE_PLAT_DB_ERROR, "平台修改配送方式失败,原因：" + e.getMessage());
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
    public InvoicePlat queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询平台发票[invoiceId]:" + id);
        //校验参数
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "发票ID[invoiceId]为空");
        try {
            InvoicePlat invoicePlat = invoicePlatMapper.selectBeanById(id);
            if (invoicePlat != null) {
                InvoiceListQuery invoiceListQuery = new InvoiceListQuery();
                invoiceListQuery.setInvoiceId(invoicePlat.getInvoiceId());
                List<InvoiceList> invoiceLists = invoiceListService.queryListByQuery(invoiceListQuery);
                invoicePlat.setInvoiceLists(invoiceLists);
            }
            return invoicePlat;
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "查询平台发票数据失败,参数[invoiceId]:" + id, e);
            throw new HsException(BSRespCode.BS_INVOICE_PLAT_DB_ERROR, "查询平台发票数据失败,原因:" + e.getMessage());
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
    public List<InvoicePlat> queryListByQuery(Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询平台发票列表参数[query]:" + query);
        InvoiceQuery invoiceQuery = null;
        if (query != null) {
            invoiceQuery = ((InvoiceQuery) query);
        }
        try {
            return invoicePlatMapper.selectBeanListByQuery(invoiceQuery);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "查询平台发票数据失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_INVOICE_PLAT_DB_ERROR, "查询平台发票数据失败，原因：" + e.getMessage());
        }

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
    public List<InvoicePlat> queryListForPage(Page page, Query query) throws HsException {

        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询平台发票数据列表参数[query]:" + query);

        //校验参数
        HsAssert.notNull(query, RespCode.PARAM_ERROR, "查询实体[query]为null");
        HsAssert.isInstanceOf(InvoiceQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是InvoiceQuery类型");
        InvoiceQuery invoiceQuery = ((InvoiceQuery) query);
        //校验日期
        invoiceQuery.checkDateFormat();
        //设置分页上下文
        PageContext.setPage(page);

        try {
            return invoicePlatMapper.selectBeanListPage(invoiceQuery);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询平台发票数据列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_INVOICE_PLAT_DB_ERROR, "分页查询平台发票数据列表失败,原因：" + e.getMessage());
        }
    }
}
