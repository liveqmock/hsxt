/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tax.interfaces;

import java.util.List;

import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

/**
 * 税率调整申请接口
 * 
 * @Package : com.gy.hsxt.bs.tax.interfaces
 * @ClassName : ITaxrateChangeService
 * @Description : 税率调整申请接口
 * @Author : chenm
 * @Date : 2015/12/29 18:49
 * @Version V3.0.0.0
 */
public interface ITaxrateChangeService extends IBaseService<TaxrateChange> {
    /**
     * 查询当前有效的税率
     * 
     * @param custId
     *            企业客户号
     * @return 税率
     * @throws HsException
     */
    String queryValidTaxrateByCustId(String custId) throws HsException;

    /**
     * 条件查询最新通过的税率申请
     * 
     * @param taxrateChangeQuery
     *            查询实体
     * @return list
     * @throws HsException
     */
    List<TaxrateChange> queryEnableTaxrateList(TaxrateChangeQuery taxrateChangeQuery) throws HsException;

    /**
     * 查询企业税率调整的最新申请
     * 
     * @param custId
     *            客户号
     * @return his
     * @throws HsException
     */
    TaxrateChange queryLastHisByCustId(String custId) throws HsException;

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
    List<TaxrateChange> queryTaskListPage(Page page, TaxrateChangeQuery taxrateChangeQuery);

    /**
     * 判断企业是否存在审批中的税率申请
     * true - 存在 false - 不存在
     *
     * @param entCustId 企业客户号
     * @return {@code boolean}
     * @throws HsException
     */
    boolean checkTaxrateChangePending(String entCustId) throws HsException;
}
