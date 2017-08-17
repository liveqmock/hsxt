/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.api;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.*;

/**
 * @Package: com.gy.hsxt.ps.api
 * @ClassName: IPsQueryService
 * @Description: 查询服务接口
 * 
 * @author: chenhongzhi
 * @date: 2015-09-13 上午9:48:38
 * @version V3.0
 */

public interface IPsQueryService {

    // 单笔查询
    QueryResult singleQuery(QuerySingle querySingle) throws HsException;

    // 根据流水号查询详情
    QueryResult queryDetailsByTransNo(String transNo) throws HsException;

    // 查询积分分配明细汇总
    AllocDetailSum queryPointDetailSum(QueryDetail queryDetail) throws HsException;

    // 查询积分分配明细列表
    PageData<AllocDetail> queryPointDetail(QueryDetail queryDetail) throws HsException;

    // POS单笔查询
    QueryResult singlePosQuery(QuerySingle querySingle) throws HsException;

    // POS单笔查询
    QueryResult singlePosQuery(QueryPosSingle queryPosSingle) throws HsException;

    // POS定金单查询
    PageData<PosEarnest> searchPosEarnest(QuerySingle querySingle, Page page) throws HsException;

    // 消费积分记录
    List<PointRecordResult> pointRecord(QueryPointRecord queryPointRecord) throws HsException;

    // 网上积分登记查询
    PageData<PointRecordResult> pointRegisterRecord(QueryPointRecord queryPointRecord) throws HsException;

    // 根据流水号查询收入详情(商城、线下)
    ProceedsResult proceedsDetail(Proceeds proceeds) throws HsException;

    // 根据流水号查询税收详情
    TaxResult queryTaxDetail(Proceeds proceeds) throws HsException;

    // 查询企业收入明细(线下)
    PageData<QueryEntry> proceedsEntryList(QueryDetail queryDetail) throws HsException;

    // 查询企业收入明细(线上)
    PageData<QueryEntry> proceedsOnLineEntryList(QueryDetail queryDetail) throws HsException;
    
    /**
     * 根据企业custId查询是否有未结单的交易
     * @param entCustId
     * @return
     * @throws HsException
     */
    boolean queryIsSettleByEntCustId(String entCustId) throws HsException;
    
    /**
     * 根据custId查询是否有未结算的预付定金的交易
     * @param custId 客户ID
     * @return false ：有，ture : 无
     * @throws HsException
     */
    boolean queryPrePayByCustId(String custId) throws HsException;
    
    /**
     * 查询消费积分分配明细
     * @param queryDetail 查询条件对象
     * @return 消费积分分配统计明细集合
     * @throws HsException
     */
    PageData<AllocDetailSum> queryPointDetailSumPage(QueryDetail queryDetail,Page page) throws HsException;


    /**
     * 积分记录(给平板上用，正常单，退货单，撤单单)
     * @return QueryPageResult 还回对象
     * @throws HsException
     */
    PageData<QueryPageResult> queryPointNBCPage(QueryPage queryPage) throws HsException;

}
