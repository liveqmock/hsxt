/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.annualfee.mapper;

import java.util.List;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeePriceQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeePrice;

/**
 * 年费价格方案Dao层接口
 *
 * @Package : com.hsxt.bs.annualfee.mapper
 * @ClassName : AnnualFeeMapper
 * @Description : 年费价格方案 mapper接口
 *
 * @author : liuhq
 * @date : 2015-9-28 下午5:26:50
 * @version V1.0
 */
@Repository
public interface AnnualFeePriceMapper {
    /**
     * 创建 年费价格方案
     *
     * @param annualFeePrice 实体类 非null
     *                       <p/>
     *                       无异常便成功，Exception失败
     * @return int 插入条数
     */
    int insertAnnualFeePrice(AnnualFeePrice annualFeePrice);

    /**
     * 查询 年费价格方案列表
     *
     * @param annualFeePriceQuery 条件查询
     * @return 返回查询结果列表
     */
    List<AnnualFeePrice> selectAnnualFeePriceList(AnnualFeePriceQuery annualFeePriceQuery);

    /**
     * 获取 某一条年费价格方案
     *
     * @param priceId 方案编号 非nullfss
     * @return 返回某一条年费价格方案记录
     */
    AnnualFeePrice selectById(@Param(value = "priceId") String priceId);

    /**
     * 审批 年费价格方案
     *
     * @param annualFeePrice 实体类 非null
     *                       <p/>
     *                       无异常便成功，Exception失败
     * @return int 影响记录数
     */
    int apprAnnualFeePrice(AnnualFeePrice annualFeePrice);

    /**
     * 禁用 已启用的年费价格方案
     */
    int disableAnnualFeePrice(@Param(value = "custType") Integer custType);

    /**
     * 获取 某一个企业类型已启用的年费价格方案
     *
     * @param custType 企业类型
     * @return price 年费
     */
    String selectPriceForEnabled(@Param(value = "custType") Integer custType);

    /**
     * 获取 某一个企业类型待审批的年费价格方案
     *
     * @param custType 企业类型
     * @return String 年费方案ID
     */
    String selectPriceIdForPending(@Param(value = "custType") Integer custType);

}
