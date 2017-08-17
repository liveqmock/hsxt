/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.utils.AnnualAreaUtils;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.invoice.InvoicePool;
import com.gy.hsxt.bs.bean.invoice.InvoicePoolQuery;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.invoice.BizType;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.invoice.enumtype.PoolFlag;
import com.gy.hsxt.bs.invoice.interfaces.IInvoicePoolService;
import com.gy.hsxt.bs.invoice.mapper.InvoicePoolMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 发票池业务接口实现
 *
 * @Package :com.gy.hsxt.bs.invoice.service
 * @ClassName : InvoicePoolService
 * @Description : 发票池业务接口实现
 * @Author : chenm
 * @Date : 2015/12/15 12:32
 * @Version V3.0.0.0
 */
@Service
public class InvoicePoolService implements IInvoicePoolService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 发票池Dao接口
     */
    @Resource
    private InvoicePoolMapper invoicePoolMapper;

    /**
     * 保存实体
     *
     * @param invoicePool 实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(InvoicePool invoicePool) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存发票池数据参数[invoicePool]:" + invoicePool);
        HsAssert.notNull(invoicePool, RespCode.PARAM_ERROR, "发票池数据[invoicePool]为空");
        HsAssert.hasText(invoicePool.getCustId(), RespCode.PARAM_ERROR, "客户ID[custId]为空");
        HsAssert.hasText(invoicePool.getResNo(), RespCode.PARAM_ERROR, "互生号[resNo]为空");
        HsAssert.hasText(invoicePool.getCustName(), RespCode.PARAM_ERROR, "客户名称[custName]为空");
        HsAssert.hasText(invoicePool.getAllAmount(), RespCode.PARAM_ERROR, "总金额[allAmount]为空");
        HsAssert.hasText(invoicePool.getLastDate(), RespCode.PARAM_ERROR, "最后统计的截止日期[lastDate]为空");
        HsAssert.isTrue(BizType.check(invoicePool.getBizType()), RespCode.PARAM_ERROR, "业务类型[bizType]错误");
        HsAssert.isTrue(InvoiceMaker.check(invoicePool.getInvoiceMaker()), RespCode.PARAM_ERROR, "开票方[invoiceMaker]错误");

        invoicePool.setCustType(HsResNoUtils.getCustTypeByHsResNo(invoicePool.getResNo()));
        //如果是系统资源费，统计只有一次
        if (BizType.PC_S_RES_FEE.getBizCode().equals(invoicePool.getBizType())||BizType.PC_M_RES_FEE.getBizCode().equals(invoicePool.getBizType())) {
            //设置统计标识为停止
            invoicePool.setGoOn(PoolFlag.STOP.ordinal());
            invoicePool.setStopDate(AnnualAreaUtils.nextToday(1));//停止统计日期
        } else {
            //其他设置为继续
            invoicePool.setGoOn(PoolFlag.CONTINUE.ordinal());
        }
        //保存发票池数据
        try {
            if (StringUtils.isEmpty(invoicePool.getOpenedAmount())) {
                invoicePool.setOpenedAmount("0.000");
            }
            invoicePoolMapper.insertInvoicePool(invoicePool);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存发票池数据失败,参数[invoicePool]:" + invoicePool, e);
            throw new HsException(BSRespCode.BS_INVOICE_POOL_DB_ERROR, "保存发票池数据失败,原因:" + e.getMessage());
        }
        return invoicePool.getCustId();
    }

    /**
     * 更新实体
     *
     * @param invoicePool 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(InvoicePool invoicePool) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "更新发票池数据参数[invoicePool]:" + invoicePool);
        //校验参数
        HsAssert.notNull(invoicePool, RespCode.PARAM_ERROR, "发票池数据[invoicePool]为空");
        HsAssert.hasText(invoicePool.getCustId(), RespCode.PARAM_ERROR, "客户ID[custId]为空");
        HsAssert.isTrue(BizType.check(invoicePool.getBizType()), RespCode.PARAM_ERROR, "业务类型[bizType]错误");
        if (invoicePool.getGoOn() != null && PoolFlag.STOP.ordinal() == invoicePool.getGoOn()) {
            HsAssert.hasText(invoicePool.getStopDate(), RespCode.PARAM_ERROR, "停止统计日期[stopDate]为空");
        }
        try {
            //更新发票记录
            return invoicePoolMapper.updateBean(invoicePool);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "更新发票池数据失败，参数[invoicePool]:" + invoicePool, e);
            throw new HsException(BSRespCode.BS_INVOICE_POOL_DB_ERROR, "更新发票池数据失败,原因:" + e.getMessage());
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
    public InvoicePool queryBeanById(String id) throws HsException {
        return null;
    }

    /**
     * 根据客户ID和业务类型查询唯一的发票池记录
     *
     * @param custId  客户ID
     * @param bizType 业务类型
     * @return pool
     */
    @Override
    public InvoicePool queryBeanByIdAndType(String custId, String bizType) {
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanByIdAndType", "根据客户ID和业务类型查询唯一的发票池记录[custId]:" + custId + ",[bizType]:" + bizType);
        HsAssert.hasText(custId, RespCode.PARAM_ERROR, "客户ID[custId]为空");
        HsAssert.isTrue(BizType.check(bizType), RespCode.PARAM_ERROR, "业务类型代码[bizType]错误");
        try {
            return invoicePoolMapper.selectBeanByIdAndType(custId, bizType);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanByIdAndType", "根据客户ID和业务类型查询唯一的发票池记录失败,参数[custId]:" + custId + ",[bizType]:" + bizType, e);
            throw new HsException(BSRespCode.BS_INVOICE_POOL_DB_ERROR, "根据客户ID和业务类型查询唯一的发票池记录失败,原因:" + e.getMessage());
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
    public List<InvoicePool> queryListByQuery(Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询发票池数据列表参数[query]:" + query);

        InvoicePoolQuery invoicePoolQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(InvoicePoolQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是InvoicePoolQuery类型");
            invoicePoolQuery = ((InvoicePoolQuery) query);
        }
        try {
            //查询发票池列表
            return invoicePoolMapper.selectListByQuery(invoicePoolQuery);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", "查询发票池数据列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_INVOICE_POOL_DB_ERROR, "查询发票池数据列表失败,原因:" + e.getMessage());
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
    public List<InvoicePool> queryListForPage(Page page, Query query) throws HsException {
        //系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询发票池数据列表参数[query]:" + query);

        //校验参数
        HsAssert.notNull(query, RespCode.PARAM_ERROR, "查询实体[query]为null");
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");
        HsAssert.isInstanceOf(InvoicePoolQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是InvoicePoolQuery类型");
        InvoicePoolQuery invoicePoolQuery = ((InvoicePoolQuery) query);
        //设置分页上下文
        PageContext.setPage(page);

        try {
            return invoicePoolMapper.selectBeanListPage(invoicePoolQuery);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询发票池数据列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_INVOICE_POOL_DB_ERROR, "分页查询发票池数据列表失败,原因:" + e.getMessage());
        }
    }
}
