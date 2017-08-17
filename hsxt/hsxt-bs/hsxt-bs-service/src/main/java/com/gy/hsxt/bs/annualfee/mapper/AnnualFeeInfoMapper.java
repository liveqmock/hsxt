/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.annualfee.mapper;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfoQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 企业年费信息 Mapper 接口
 *
 * @Package : com.hsxt.bs.annualfee.mapper
 * @ClassName : EntAnnualFeeInfoMapper
 * @Description : 企业年费信息 Mapper 接口
 * @Author : liuhq
 * @Date : 2015-9-28 下午5:30:05
 * @Version V3.0
 */
@Repository
public interface AnnualFeeInfoMapper {
    /**
     * 创建 企业年费信息
     *
     * @param annualFeeInfo 实体类 非null
     * @return int
     */
    int insertAnnualFeeInfo(AnnualFeeInfo annualFeeInfo);

    /**
     * 判断是否存在 企业年费信息
     *
     * @param entCustId 企业客户号 必须 非null
     * @return 返回总记录数
     */
    int existEntAnnualFeeInfo(@Param(value = "entCustId") String entCustId);

    /**
     * 获取 某一个企业年费信息
     *
     * @param entCustId 企业客户号 必须 非null
     * @return 返回实体类
     */
    AnnualFeeInfo selectBeanById(@Param(value = "entCustId") String entCustId);

    /**
     * 更新 企业年费信息
     *
     * @param annualFeeInfo 实体类 非null
     */
    int updateBean(AnnualFeeInfo annualFeeInfo);

    /**
     * 修改支付完成的年费信息
     *
     * @param annualFeeInfo 年费信息
     * @return int
     */
    int updateBeanForPaid(AnnualFeeInfo annualFeeInfo);

    /**
     * 根据查询条件查询年费信息列表
     *
     * @param annualFeeInfoQuery 查询实体
     * @return list
     */
    List<AnnualFeeInfo> selectListByQuery(AnnualFeeInfoQuery annualFeeInfoQuery);

    /**
     * 根据查询条件分页查询年费信息列表
     *
     * @param annualFeeInfoQuery 查询实体
     * @return list
     */
    List<AnnualFeeInfo> selectBeanListPage(AnnualFeeInfoQuery annualFeeInfoQuery);
}
