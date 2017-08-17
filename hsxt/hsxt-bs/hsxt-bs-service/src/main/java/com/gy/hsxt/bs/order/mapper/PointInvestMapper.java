/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.bs.order.bean.PointInvestCustom;

/**
 * 积分投资mapper dao映射
 * 
 * @Package: com.gy.hsxt.bs.order.mapper
 * @ClassName: PointInvestMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-16 下午12:01:45
 * @version V3.0.0
 */
@Repository
public interface PointInvestMapper {

    /**
     * 插入积分投资
     * 
     * @param pointInvest
     *            积分投资信息
     * @return 成功记录数
     */
    public int insertPointInvest(PointInvest pointInvest);

    /**
     * 查询积分投资
     * 
     * @param custId
     *            客户号
     * @param startDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 积分投资信息
     */
    public List<PointInvest> findPointInvestListPage(@Param(value = "custId") String custId,
            @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

    /**
     * 查询积分投资客户信息
     * 
     * @param custId
     *            客户号
     * @return 积分投资客户信息
     */
    public PointInvest findPointInvestByCustId(@Param(value = "custId") String custId);

    /**
     * 查询积分投资信息
     * 
     * @param custId
     *            客户号
     * @return 积分投资客户信息
     */
    public PointInvest findPointInvestInfo(@Param(value = "investNo") String investNo);

    /**
     * 根据客户号查询积分投资小于365天的记录
     * 
     * @param custId
     *            客户号
     * @return 积分投资记录
     */
    public List<PointInvestCustom> findPointInvestLessThan365(@Param(value = "custId") String custId);

    /**
     * 查询投资记录超过365天并还未分红的记录
     * 
     * @return 投资记录
     */
    public List<PointInvest> findPointInvestMoreThan365();

    /**
     * 查询积分投资小于365天且无大于365天的记录
     * 
     * @return 投资记录
     */
    public List<PointInvestCustom> findUnPointInvestLessThan365();

    /**
     * 查询指定年份还未分红的记录数
     * 
     * @param DividendYear
     *            分红年份
     * @return 未分红的记录数
     */
    public int findUnDividendCount(@Param(value = "dividendYear") String DividendYear);

    /**
     * 调用存储过程进行投资分红
     * 
     * @param map
     *            用于获取存储过程out参数的map
     */
    public void callGenInvestDividend(Map<String, Object> map);

    /**
     * 调用存储过程进行投资分红记帐分解
     * 
     * @param map
     *            用于获取存储过程out参数的map
     */
    public void callGenInvestDividendAC(Map<String, Object> map);

    /**
     * 获取累计积分投资总数
     * 
     * @param hsResNo
     *            客户互生号
     * @return 累计积分投资总数
     */
    public String findInvestCount(@Param("hsResNo") String hsResNo);

    /**
     * 获取分红积分投资总数
     * 
     * @param hsResNo
     *            客户互生号
     * @param dividendYear
     *            分红截止日期
     * @return 累计积分投资总数
     */
    public String findDividendInvestCount(@Param("hsResNo") String hsResNo, @Param("dividendYear") String dividendYear);

}
