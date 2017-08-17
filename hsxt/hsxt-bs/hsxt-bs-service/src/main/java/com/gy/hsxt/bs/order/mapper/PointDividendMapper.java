/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.order.PointDividend;

/**
 * 积分投资分红mapper dao映射
 * 
 * @Package: com.gy.hsxt.bs.order.mapper
 * @ClassName: PointDividendMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-16 下午3:07:01
 * @version V3.0.0
 */
@Repository
public interface PointDividendMapper {

    /**
     * 插入投资分红记录
     * 
     * @param pointDividend
     *            分红信息
     * @return 记录数
     */
    public int insertPointDividend(PointDividend pointDividend);

    /**
     * 查询积分投资分红列表
     * 
     * @param custId
     *            客户号
     * @param startYear
     *            开始日期
     * @param endYear
     *            结束日期
     * @return 积分投资w分红信息
     */
    public List<PointDividend> findPointDividendListPage(@Param(value = "custId") String custId,
            @Param(value = "startYear") String startYear, @Param(value = "endYear") String endYear);

    /**
     * 查询地区平台积分投资分红列表
     * 
     * @param custType
     *            客户类型
     * @param hsResNo
     *            互生号
     * @param custName
     *            客户名称
     * @param startYear
     *            开始日期
     * @param endYear
     *            结束日期
     * @return 分红列表
     */
    public List<PointDividend> findPlatformPointDividendListPage(@Param("custType") int custType,
            @Param(value = "hsResNo") String hsResNo, @Param("custName") String custName,
            @Param(value = "startYear") String startYear, @Param(value = "endYear") String endYear);

    /**
     * 查询年度分红
     * 
     * @param hsResNo
     *            互生号
     * @param dividendYear
     *            分红年份
     * @return 年度分红信息
     */
    public PointDividend findYearPointDividend(@Param(value = "hsResNo") String hsResNo,
            @Param(value = "dividendYear") String dividendYear);

    /**
     * 查询分红详情
     * 
     * @param dividendNo
     *            分红流水号
     * @return 分红信息
     */
    public PointDividend findPointDividendInfo(@Param("dividendNo") String dividendNo);
}
