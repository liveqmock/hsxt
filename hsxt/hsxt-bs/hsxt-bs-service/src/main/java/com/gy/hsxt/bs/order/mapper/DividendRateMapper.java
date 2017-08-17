/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendRate;

/**
 * 积分投资年度分红比率mapper dao映射
 * 
 * @Package: com.gy.hsxt.bs.order.mapper
 * @ClassName: DividendRateMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-16 上午9:12:25
 * @version V3.0.0
 */
@Repository
public interface DividendRateMapper {

    /**
     * 保存年度分红比率记录
     * 
     * @param dividendRate
     *            年度分红比率信息
     * @return 成功记录数
     */
    public int insertDividendRate(DividendRate dividendRate);

    /**
     * 查询是否存在年度分红比率
     * 
     * @param dividendYear
     *            分红年份
     * @return 成功记录数
     */
    public int findExistsYearRate(@Param("dividendYear") String dividendYear);

    /**
     * 查询年度分红比率列表
     * 
     * @return 年度分红比率记录列表
     */
    public List<DividendRate> findDividendRateListPage(@Param(value = "dividendYear") String dividendYear);

    /**
     * 查询历史分红总数列表
     * 
     * @return 历史分红总数列表
     */
    public List<CustomPointDividend> findHisDividendListPage();

    /**
     * 查询新一年的年度分红比率
     * 
     * @param dividendYear
     *            分红年份
     * @return 年度分红比率
     */
    public DividendRate findNewDividendRate(@Param(value = "dividendYear") String dividendYear);

    /**
     * 更新分红状态
     * 
     * @param dividendYear
     *            分红年份
     * @param dividendStatus
     *            分红状态
     * @return
     */
    public int updateDividendStatus(@Param("dividendYear") String dividendYear,
            @Param(value = "dividendStatus") Integer dividendStatus);

    /**
     * 查询最近一年分红年份
     * 
     * @return 分红年份
     */
    public String findMaxDividendYear();
}
