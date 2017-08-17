/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tax.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tax.interfaces.ITaxrateChangeAllService;
import com.gy.hsxt.bs.tax.interfaces.ITaxrateChangeService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 企业税率调整申请 实现类
 * 
 * @Package : com.gy.hsxt.bs.taxratechange.service
 * @ClassName : TaxrateChangeAllService
 * @Description : 企业税率调整申请
 * @Author : liuhq
 * @Date : 2015-10-15 下午7:34:58
 * @Version V3.0
 */
@Service
public class TaxrateChangeAllService implements ITaxrateChangeAllService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 税率调整申请业务接口
     */
    @Resource
    private ITaxrateChangeService taxrateChangeService;

    /**
     * 创建企业税率调整申请
     * 
     * @param taxrateChange
     *            实体类 非null
     *            <p/>
     *            无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void createTaxrateChange(TaxrateChange taxrateChange) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createTaxrateChange", "创建企业税率调整申请参数[taxrateChange]:"
                + taxrateChange);
        // 创建企业税率调整申请
        taxrateChangeService.saveBean(taxrateChange);
    }

    /**
     * 判断企业是否存在审批中的税率申请
     * true - 存在 false - 不存在
     *
     * @param entCustId 企业客户号
     * @return {@code boolean}
     * @throws HsException
     */
    @Override
    public boolean checkTaxrateChangePending(String entCustId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:checkTaxrateChangePending", "判断企业是否存在审批中的税率申请参数[entCustId]:"+ entCustId);
        return taxrateChangeService.checkTaxrateChangePending(entCustId);
    }

    /**
     * 分页查询企业税率调整申请列表
     * 
     * @param page
     *            分页对象 必须 非null
     * @param taxrateChangeQuery
     *            条件查询实体
     * @return 返回按条件分好页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<TaxrateChange> queryTaxrateChangeListPage(Page page, TaxrateChangeQuery taxrateChangeQuery)
            throws HsException {

        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTaxrateChangeListPage",
                "分页查询企业税率调整申请列表参数[taxrateChangeQuery]:" + taxrateChangeQuery);
        // 分页查询企业税率调整申请列表
        List<TaxrateChange> taxrateChanges = taxrateChangeService.queryListForPage(page, taxrateChangeQuery);

        return PageData.bulid(page.getCount(), taxrateChanges);
    }

    /**
     * 分页查询企业税率调整审批/复核列表
     * 
     * @param page
     *            分页对象 必须 非null
     * @param taxrateChangeQuery
     *            条件查询实体
     * @return 返回按条件分好页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<TaxrateChange> queryTaskListPage(Page page, TaxrateChangeQuery taxrateChangeQuery)
            throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTaskListPage", "分页查询企业税率调整审批/复核列表参数[taxrateChangeQuery]:"
                + taxrateChangeQuery);
        // 分页查询企业税率调整申请列表
        List<TaxrateChange> taxrateChanges = taxrateChangeService.queryTaskListPage(page, taxrateChangeQuery);

        return PageData.bulid(page.getCount(), taxrateChanges);
    }

    /**
     * 查询企业税率调整申请记录
     * 
     * @param applyId
     *            申请编号
     * @return 返回一个实体对象
     * @throws HsException
     */
    @Override
    public TaxrateChange queryTaxrateChangeById(String applyId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTaxrateChangeById", "查询企业税率调整申请记录的申请ID[applyId]:"
                + applyId);

        return taxrateChangeService.queryBeanById(applyId);
    }

    /**
     * 审批/复核企业税率调整申请
     * 
     * @param taxrateChange
     *            企业税率调整申请
     * @throws HsException
     */
    @Override
    public void apprTaxrateChange(TaxrateChange taxrateChange) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:apprTaxrateChange", "审批/复核企业税率调整申请参数[taxrateChange]:"
                + taxrateChange);
        // 审批/复核企业税率调整申请
        taxrateChangeService.modifyBean(taxrateChange);
    }

    /**
     * 查询企业税率调整的最新申请
     * 
     * @param custId
     *            客户号
     * @return his
     * @throws HsException
     */
    @Override
    public TaxrateChange queryLastHisByCustId(String custId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryLastHisByCustId", "查询企业税率调整的最新申请参数[custId]:" + custId);
        // 查询企业税率调整的最新申请
        return taxrateChangeService.queryLastHisByCustId(custId);
    }
}
