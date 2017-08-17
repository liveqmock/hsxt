/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ao.bean.AccountingEntry;

/**
 * 记帐分解mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: AccountingMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午8:47:51
 * @version V3.0.0
 */
public interface AccountingMapper {

    /**
     * 插入记帐分解
     * 
     * @param accountingEntry
     *            记帐信息
     * @throws Exception
     */
    public int insertAccounting(AccountingEntry accountingEntry);

    /**
     * 批量插入记帐分解
     * 
     * @param details
     *            记帐分解列表
     */
    public void insertBatchAccounting(@Param("details") List<AccountingEntry> details);

    /**
     * 更新记帐状态
     * 
     * @param transNo
     *            交易流水号
     * @param status
     *            记帐状态
     * @return 成功记录数
     */
    public int updateStatus(@Param("transNo") String transNo, @Param("status") Integer status);

    /**
     * 删除前几个月的记账分解数据
     * 
     * @param month
     *            前几个月
     */
    public void deleteAccountingLastMath(@Param("month") Integer month);

    /**
     * 查询当日所有记账分解记录中已记账的记录
     * 
     * @param startTransNo
     *            查询交易流水号范围，开始流水号
     * @param endTransNo
     *            查询交易流水号范围，结束流水号，包头包尾
     * @param startDate
     *            查询会计日期范围，开始日期
     * @param endDate
     *            查询会计日期范围，结束日期，包头不包尾
     * @return
     */
    public List<AccountingEntry> findAccountingEntryRange(@Param("startTransNo") String startTransNo,
            @Param("endTransNo") String endTransNo, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询指定会计日期所有记账分解记录中已记账的交易流水号
     * 
     * @param startDate
     *            查询会计日期范围，开始日期
     * @param endDate
     *            查询会计日期范围，结束日期，包头不包尾
     * @return
     */
    public List<String> findTransNoByFiscalDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询是否已存在记录分解记录
     * 
     * @param bizNo
     *            原业务流水号
     * @return 记录数
     */
    public int findExistGenAccDetail(@Param("bizNo") String bizNo);
}
