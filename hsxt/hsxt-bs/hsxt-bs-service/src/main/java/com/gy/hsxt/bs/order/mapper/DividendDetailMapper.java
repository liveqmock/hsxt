/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.order.DividendDetail;

/**
 * 分红计算明细mapper dao映射
 * 
 * @Package: com.gy.hsxt.bs.order.mapper
 * @ClassName: DividendDetailMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-16 下午3:44:55
 * @version V3.0.0
 */
@Repository
public interface DividendDetailMapper {

    /**
     * 查询积分投资分红计算明细
     * 
     * @param hsResNo
     *            互生号
     * @param dividendYear
     *            分红年份
     * @return 积分投资分红明细
     */
    public List<DividendDetail> findDividendDetailListPage(@Param(value = "hsResNo") String hsResNo,
            @Param(value = "dividendYear") String dividendYear);

    /**
     * 查询积分投资分红计算详情
     * 
     * @param dividendNo
     *            分红流水号
     * @return 分红计算详情
     */
    public DividendDetail findDividendDetailInfo(@Param("dividendNo") String dividendNo);
}
