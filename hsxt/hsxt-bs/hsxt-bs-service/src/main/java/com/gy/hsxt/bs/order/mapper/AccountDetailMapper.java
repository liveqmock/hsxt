/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.mapper;

import java.util.List;

import com.gy.hsxt.bs.order.bean.AccountDetailQuery;
import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.stereotype.Repository;

/**
 * 记帐分解mapper接口
 * 
 * @Package: com.gy.hsxt.bs.order.mapper
 * @ClassName: AccountDetailMapper
 * @Description: 记帐分解mapper接口
 * 
 * @author: kongsl
 * @date: 2015-10-10 下午5:56:22
 * @version V1.0
 */
@Repository
public interface AccountDetailMapper {
    /**
     * 插入记帐分解
     *
     * @param accountDetail 记帐信息
     */
    int insertAccountDetail(AccountDetail accountDetail);

    /**
     * 批量插入记帐分解
     *
     * @param details
     * @return
     */
    void bathcInsertAccountDetail(@Param("details") List<AccountDetail> details);

    /**
     * 按条件查询记账分解记录
     *
     * @param accountDetailQuery 查询实体
     * @return list
     */
    List<AccountDetail> selectBeanListByQuery(AccountDetailQuery accountDetailQuery);

    /**
     * 按条件查询记账流水号(有去重)
     *
     * @param accountDetailQuery 查询实体
     * @return list
     */
    List<String> selectBizNosByQuery(AccountDetailQuery accountDetailQuery);

    /**
     * 获取投资分红记帐分解记录
     *
     * @param transType 交易类型
     * @return 投资分红记帐分解记录
     */
    List<AccountDetail> findInvestAccountDetail(@Param("transType") String transType);

    /**
     * 更新 记帐分解记录状态
     *
     * @param bizNo 交易流水号 必须 非null
     * @throws HsException
     */
    void updateBizNoStatus(String bizNo);

    /**
     * 查询年度分红记录
     *
     * @param beginRN 查询条件
     * @return 分红记录
     */
    List<AccountDetail> findPointDvidendByRownum(@Param("transCode") String[] transCode,
                                                 @Param("beginRN") Integer beginRN, @Param("endRN") Integer endRN);

    /**
     * 查询投资分红年度总记录数
     *
     * @param dividendYear 分红年份
     * @return 年度分红总记录数
     */
    int findCountByTransType(@Param("transCode") String[] transCode, @Param("dividendYear") String dividendYear);
}
