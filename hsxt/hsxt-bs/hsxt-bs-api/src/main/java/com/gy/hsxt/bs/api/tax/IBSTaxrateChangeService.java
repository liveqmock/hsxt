package com.gy.hsxt.bs.api.tax;

/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 企业税率调整接口
 *
 * @Package : com.gy.hsxt.bs.api.enttaxratechange
 * @ClassName : IBSTaxrateChangeService
 * @Description :企业税率调整接口
 * @Author : liuhq
 * @Date : 2015-9-6 下午3:48:11
 * @Version V3.0
 */
public interface IBSTaxrateChangeService {

    /**
     * 创建企业税率调整申请
     *
     * @param taxrateChange 实体类 非null
     * @throws HsException
     */
    void createTaxrateChange(TaxrateChange taxrateChange) throws HsException;

    /**
     * 判断企业是否存在审批中的税率申请
     * true - 存在 false - 不存在
     *
     * @param entCustId 企业客户号
     * @return {@code boolean}
     * @throws HsException
     */
    boolean checkTaxrateChangePending(String entCustId) throws HsException;

    /**
     * 分页查询 企业税率调整申请列表
     *
     * @param page               分页对象 必须 非null
     * @param taxrateChangeQuery 条件查询实体
     * @return 返回按条件分好页的数据列表
     * @throws HsException
     */
    PageData<TaxrateChange> queryTaxrateChangeListPage(Page page, TaxrateChangeQuery taxrateChangeQuery)
            throws HsException;

    /**
     * 分页查询企业税率调整审批/复核列表
     *
     * @param page               分页对象 必须 非null
     * @param taxrateChangeQuery 条件查询实体
     * @return 返回按条件分好页的数据列表
     * @throws HsException
     */
    PageData<TaxrateChange> queryTaskListPage(Page page, TaxrateChangeQuery taxrateChangeQuery) throws HsException;

    /**
     * 获取 某一条企业税率调整申请记录
     *
     * @param applyId 申请编号 必须 非null
     * @return 返回一个实体对象
     * @throws HsException
     */
    TaxrateChange queryTaxrateChangeById(String applyId) throws HsException;

    /**
     * 审批/复核企业税率调整申请记录
     *
     * @param taxrateChange 审批操作历史的实体类 非null
     * @throws HsException
     */
    void apprTaxrateChange(TaxrateChange taxrateChange) throws HsException;

    /**
     * 查询企业税率调整的最新申请
     *
     * @param custId 客户号
     * @return bean
     * @throws HsException
     */
    TaxrateChange queryLastHisByCustId(String custId) throws HsException;

}
