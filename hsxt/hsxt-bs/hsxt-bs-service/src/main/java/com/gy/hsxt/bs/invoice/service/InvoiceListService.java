/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.invoice.InvoiceList;
import com.gy.hsxt.bs.bean.invoice.InvoiceListQuery;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.invoice.interfaces.IInvoiceListService;
import com.gy.hsxt.bs.invoice.mapper.InvoiceListMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 发票清单业务层实现
 *
 * @Package :com.gy.hsxt.bs.invoice.service
 * @ClassName : InvoiceListService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 21:21
 * @Version V3.0.0.0
 */
@Service
public class InvoiceListService implements IInvoiceListService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 发票清单DAO接口
     */
    @Resource
    private InvoiceListMapper invoiceListMapper;

    /**
     * 保存实体
     *
     * @param invoiceList 实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(InvoiceList invoiceList) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存发票清单参数：" + invoiceList);
        //校验参数
        HsAssert.notNull(invoiceList, RespCode.PARAM_ERROR, "发票清单实体[invoiceList]为null");
        HsAssert.hasText(invoiceList.getInvoiceId(), RespCode.PARAM_ERROR, "发票ID[invoiceId]为空");
        HsAssert.hasText(invoiceList.getInvoiceNo(), RespCode.PARAM_ERROR, "发票号[invoiceNo]为空");
        HsAssert.hasText(invoiceList.getInvoiceCode(), RespCode.PARAM_ERROR, "发票代码[invoiceCode]为空");
        HsAssert.hasText(invoiceList.getInvoiceAmount(), RespCode.PARAM_ERROR, "发票金额[invoiceAmount]为空");

        try {
            //设置主键
            invoiceList.setListId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            //保存发票清单
            invoiceListMapper.insertInvoiceList(invoiceList);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + invoiceList, "保存发票清单成功");
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存发票清单失败，参数[invoiceList]:" + invoiceList, e);

            throw new HsException(BSRespCode.BS_INVOICE_LIST_DB_ERROR, "保存发票清单失败,原因：" + e.getMessage());
        }
        return invoiceList.getListId();
    }

    /**
     * 更新实体
     *
     * @param invoiceList 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(InvoiceList invoiceList) throws HsException {
        return 0;
    }

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    @Override
    public InvoiceList queryBeanById(String id) throws HsException {
        return null;
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<InvoiceList> queryListByQuery(Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询发票清单列表参数[query]:" + query);

        InvoiceListQuery invoiceListQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(InvoiceListQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是InvoiceListQuery类型");
            invoiceListQuery = ((InvoiceListQuery) query);
        }
        try {
            return invoiceListMapper.selectListByQuery(invoiceListQuery);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", "查询发票清单列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_INVOICE_LIST_DB_ERROR, "查询发票清单列表失败,原因：" + e.getMessage());
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
    public List<InvoiceList> queryListForPage(Page page, Query query) throws HsException {
        return null;
    }
}
