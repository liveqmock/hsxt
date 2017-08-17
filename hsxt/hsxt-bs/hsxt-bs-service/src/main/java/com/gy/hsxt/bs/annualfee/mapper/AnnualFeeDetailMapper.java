/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.annualfee.mapper;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetail;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetailQuery;
import com.gy.hsxt.common.exception.HsException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 企业年费计费单 mapper接口类
 *
 * @version V1.0
 * @Package: com.hsxt.bs.annualfee.mapper
 * @ClassName: EntAnnualFeeDetailMapper
 * @Description: 企业年费计费单 mapper接口类
 * @author: liuhq
 * @date: 2015-9-28 下午5:25:54
 */
@Repository
public interface AnnualFeeDetailMapper {
    /**
     * 创建 企业年费计费单
     *
     * @param annualFeeDetail 实体类 非null
     *                        <p/>
     *                        无异常便成功，Exception失败
     * @return int
     * @throws HsException
     */
    int insertAnnualFeeDetail(AnnualFeeDetail annualFeeDetail) throws HsException;

    /**
     * 根据年费区间和企业客户号查询年费计费单
     *
     * @param annualFeeDetail 年费计费单
     * @return 年费计费单
     */
    AnnualFeeDetail selectOneByAnnualArea(AnnualFeeDetail annualFeeDetail);

    /**
     * 查询年费计费单列表
     *
     * @param annualFeeDetailQuery 查询实体
     * @return list
     */
    List<AnnualFeeDetail> selectAnnualFeeDetailList(AnnualFeeDetailQuery annualFeeDetailQuery);

    /**
     * 支付完成后修改计费单
     *
     * @param orderNo 业务订单编号
     * @return int
     */
    int updateAllBeanForPaid(@Param("orderNo") String orderNo);

    /**
     * 查询未支付年费区间
     *
     * @param annualFeeDetailQuery 查询实体
     * @return bean
     */
    AnnualFeeDetail selectAnnualAreaForArrear(AnnualFeeDetailQuery annualFeeDetailQuery);

}
