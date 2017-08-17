/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tax.mapper;

import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 企业税率调整申请 Mapper接口
 *
 * @Package : com.gy.hsxt.bs.tax.mapper
 * @ClassName : TaxrateChangeMapper
 * @Description :
 * @Author : chenm
 * @Date : 2015-10-15 下午6:01:49
 * @Version V3.0
 */
@Repository
public interface TaxrateChangeMapper {
    /**
     * 创建 企业税率调整申请
     *
     * @param taxrateChange 实体类 非null
     */
    int insertBean(TaxrateChange taxrateChange);

    /**
     * 分页查询 企业税率调整申请列表（企业）
     *
     * @param taxrateChangeQuery 条件查询实体
     * @return 返回按条件分好页的数据列表
     */
    List<TaxrateChange> selectBeanListPage(TaxrateChangeQuery taxrateChangeQuery);

    /**
     * 分页查询企业税率调整审批/复核列表
     *
     * @param taxrateChangeQuery 条件查询实体
     * @return 返回按条件分好页的数据列表
     */
    List<TaxrateChange> selectTaskListPage(TaxrateChangeQuery taxrateChangeQuery);

    /**
     * 查询企业税率调整申请记录
     *
     * @param applyId 申请编号 必须 非null
     * @return 返回一个实体对象
     */
    TaxrateChange selectBeanById(@Param("applyId") String applyId);

    /**
     * 修改某一条企业税率调整申请记录
     *
     * @param taxrateChange 申请记录
     */
    int updateBean(TaxrateChange taxrateChange);

    /**
     * 停用之前的 税率调整申请
     *
     * @param taxrateChange 审批操作历史的实体类 非null
     */
    int disableTaxrateChange(TaxrateChange taxrateChange);

    /**
     * 判断某企业是否存在待审核的申请记录
     *
     * @param custId 企业客户号 必须 非null
     * @return string
     */
    String selectPendingBeanByCustId(@Param("custId") String custId);

    /**
     * 批量获取企业最新申请记录
     *
     * @param taxrateChangeQuery 查询实体
     * @return List<TaxrateChange>
     */
    List<TaxrateChange> selectEnableBeanListByQuery(TaxrateChangeQuery taxrateChangeQuery);

    /**
     * 获取最新的税率调整申请记录
     *
     * @param custId 企业客户号
     * @param status 审核状态
     * @return bean
     */
    TaxrateChange selectLastBeanByCustId(@Param("custId") String custId,@Param("status") Integer status);
}
