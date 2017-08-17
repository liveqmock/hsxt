/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.order;

import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.DividendRate;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 积分投资分红接口
 * 
 * @Package: com.gy.hsxt.bs.api.order
 * @ClassName: IBSInvestProfitService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-14 下午12:13:32
 * @version V1.0
 */
public interface IBSInvestProfitService {

    /**
     * 新增年度分红比率记录
     * 
     * @param dividendRate
     *            非空年度分红比率信息
     * @return 无异常表示成功
     * @throws HsException
     */
    public void saveDividendRate(DividendRate dividendRate) throws HsException;

    /**
     * 新增积分投资记录
     * 
     * @param pointInvest
     *            非空积分投资信息
     * @return 无异常表示成功
     * @throws HsException
     */
    public void savePointInvest(PointInvest pointInvest) throws HsException;

    /**
     * 获取年度分红比率列表
     * 
     * @param dividendYear
     *            分红年份
     * @param page
     *            分页信息
     * @return 年度分红比率列表
     * @throws HsException
     */
    public PageData<DividendRate> getDividendRateList(String dividendYear, Page page) throws HsException;

    /**
     * 获取历史分红列表
     * 
     * @param page
     *            分页信息
     * @return 历史分红列表
     * @throws HsException
     */
    public PageData<CustomPointDividend> getHisDividendList(Page page) throws HsException;

    /**
     * 获取积分投资列表
     * 
     * @param custId
     *            非空客户号
     * @param startDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @param page
     *            分页信息
     * @return 返回积分投资列表
     * @throws HsException
     */
    public PageData<PointInvest> getPointInvestList(String custId, String startDate, String endDate, Page page)
            throws HsException;

    /**
     * 获取积分投资分红列表
     * 
     * @param custId
     *            非空客户号
     * @param startYear
     *            开始年份
     * @param endYear
     *            结束年份
     * @param page
     *            分页信息
     * @return 返回积分投资分红列表
     * @throws HsException
     */
    public PageData<PointDividend> getPointDividendList(String custId, String startYear, String endYear, Page page)
            throws HsException;

    /**
     * 地区平台获取积分投资分红列表
     * 
     * @param custType
     *            客户类型：1为持卡人，2为企业
     * @param hsResNo
     *            互生号
     * @param custName
     *            客户名称
     * @param startYear
     *            开始日期
     * @param endYear
     *            结束日期
     * @param page
     *            分布信息
     * @return 分红列表
     * @throws HsException
     */
    public PageData<PointDividend> getPlatformDividendList(int custType, String hsResNo, String custName,
            String startYear, String endYear, Page page) throws HsException;

    /**
     * 获取投资分红明细列表
     * 
     * @param hsResNo
     *            互生号
     * @param dividendYear
     *            分红年份
     * @param page
     *            分页信息
     * @return 返回分红明细
     * @throws HsException
     */
    public PageData<DividendDetail> getDividendDetailList(String hsResNo, String dividendYear, Page page)
            throws HsException;

    /**
     * 查询积分投资分红信息
     * 
     * @param hsResNo
     *            互生号
     * @param dividendYear
     *            分红年份
     * @return 积分投资分红信息
     */
    public CustomPointDividend getInvestDividendInfo(String hsResNo, String dividendYear) throws HsException;

    /**
     * 查询积分投资详情
     * 
     * @param investNo
     *            投资流水号
     * @return 积分投资详情
     * @throws HsException
     */
    public PointInvest getPointInvestInfo(String investNo) throws HsException;

    /**
     * 查询积分投资分红详情
     * 
     * @param dividendNo
     *            分红流水号
     * @return 分红详情
     * @throws HsException
     */
    public PointDividend getPointDividendInfo(String dividendNo) throws HsException;

    /**
     * 查询积分投资分红计算详情
     * 
     * @param dividendNo
     *            分红流水号
     * @return 分红计算详情
     * @throws HsException
     */
    public DividendDetail getDividendDetailInfo(String dividendNo) throws HsException;
}
