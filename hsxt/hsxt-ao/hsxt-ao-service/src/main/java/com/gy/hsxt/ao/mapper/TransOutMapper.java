/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.bean.TransOutCustom;
import com.gy.hsxt.ao.bean.TransOutQueryParam;

/**
 * 银行转帐mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: TransOutMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-30 下午6:01:07
 * @version V3.0.0
 */
public interface TransOutMapper {

    /**
     * 插入银行转帐记录
     * 
     * @param transOut
     *            银行转帐信息
     * @return 成功记录数
     */
    public int insertTransOut(TransOut transOut);

    /**
     * 查询银行转帐详情
     * 
     * @param transNo
     *            交易流水号
     * @return 银行转帐详情
     */
    public TransOut findTransOutInfo(@Param("transNo") String transNo);

    /**
     * 查询未转账成功的银行转帐详情
     * 
     * @param transNo
     *            交易流水号
     * @return 银行转帐详情
     */
    public TransOut findUnTransOutInfo(@Param("transNo") String transNo);

    /**
     * 查询银行转帐撤单详情
     * 
     * @param transNo
     *            交易流水号
     * @return 银行转帐详情
     */
    public TransOut findTransOutRevokeInfo(@Param("transNo") String transNo);

    /**
     * 查询银行转帐复核详情
     * 
     * @param transNo
     *            交易流水号
     * @return 银行转帐详情
     */
    public TransOut findTransOutReview(@Param("transNo") String transNo);

    /**
     * 分页条件查询银行转帐列表
     * 
     * @param transOutQueryParam
     *            查询条件
     * @return 银行转帐
     */
    public List<TransOut> findTransOutListPage(TransOutQueryParam transOutQueryParam);

    /**
     * 条件查询银行转帐列表
     * 
     * @param transOutQueryParam
     *            查询条件
     * @return 银行转帐
     */
    public List<TransOut> findTransOutList(TransOutQueryParam transOutQueryParam);

    /**
     * 查询转账列表统计信息
     * 
     * @param transOutQueryParam
     *            查询条件
     * @return 统计信息
     */
    public TransOutCustom findTransOutListCount(TransOutQueryParam transOutQueryParam);

    /**
     * 条件查询银行转帐对账列表
     * 
     * @param transOutQueryParam
     *            查询条件
     * @return 银行转帐
     */
    public List<TransOut> findCheckUpListPage(TransOutQueryParam transOutQueryParam);

    /**
     * 查询银行转帐失败列表（退款业务处理）
     * 
     * @return 银行转帐失败列表
     */
    public List<TransOut> findTransFailListPage();

    /**
     * 查询销户退款退票列表
     * 
     * @return 销户退款退票列表
     */
    public List<TransOut> findCloseTransBackListPage();

    /**
     * 获取批量自动提交列表
     * 
     * @param autoCommitTime
     *            批量提交时间
     * @return 待复核的转账交易流水号列表
     */
    public Set<String> findBatchAutoCommit(@Param("autoCommitTime") Integer autoCommitTime);

    /**
     * 更新转账状态
     * 
     * @param transNo
     *            交易流水号
     * @param transStatus
     *            转账状态
     * @param apprOptId
     *            复核操作员编号
     * @param apprOptName
     *            复核操作员名称
     * @param apprRemark
     *            复核备注
     * @return 成功记录数
     */
    public int updateTransStatus(@Param("transNo") String transNo, @Param("transStatus") Integer transStatus,
            @Param("apprOptId") String apprOptId, @Param("apprOptName") String apprOptName,
            @Param("apprRemark") String apprRemark);

    /**
     * 更新转账撤单状态
     * 
     * @param transNo
     *            交易流水号
     * @param transStatus
     *            转账状态
     * @param apprOptId
     *            复核操作员编号
     * @param apprOptName
     *            复核操作员名称
     * @param apprRemark
     *            复核备注
     * @return 成功记录数
     */
    public int updateTransRevokeStatus(@Param("transNo") String transNo, @Param("transStatus") Integer transStatus,
            @Param("apprOptId") String apprOptId, @Param("apprOptName") String apprOptName,
            @Param("apprRemark") String apprRemark);

    /**
     * 更新转账失败退回状态
     * 
     * @param transNo
     *            交易流水号
     * @param transStatus
     *            转账状态
     * @return 成功记录数
     */
    public int updateTransFailBackStatus(@Param("transNo") String transNo, @Param("transStatus") Integer transStatus);

    /**
     * 更新银行转帐结果
     * 
     * @param transNo
     *            交易流水号
     * @param transStatus
     *            转账状态
     * @param transResult
     *            转帐结果
     * @param resultTime
     *            转账结果时间
     * @param failReason
     *            失败原因
     * @param feeAmt
     *            手续费
     * @param bankTransNo
     *            银行流水号
     * @return 成功记录数
     */
    public int updateTransFail(@Param("transNo") String transNo, @Param("transStatus") Integer transStatus,
            @Param("transResult") Integer transResult, @Param("resultTime") String resultTime,
            @Param("failReason") String failReason, @Param("feeAmt") String feeAmt,
            @Param("bankTransNo") String bankTransNo);

    /**
     * 转账成功更新
     * 
     * @param realAmt
     *            实转金额
     * @param transNo
     *            交易流水号
     * @param transStatus
     *            转账状态
     * @param transResult
     *            转账结果
     * @param feeAmt
     *            手续费金额
     * @param bankTransNo
     *            银行流水号
     * @param resultTime
     *            转账结果时间
     * @return 成功记录数
     */
    public int updateTransSuccess(@Param("realAmt") String realAmt, @Param("transNo") String transNo,
            @Param("transStatus") Integer transStatus, @Param("transResult") Integer transResult,
            @Param("feeAmt") String feeAmt, @Param("bankTransNo") String bankTransNo,
            @Param("resultTime") String resultTime);

    /**
     * 批量转帐更新转帐单
     * 
     * @param transNos
     *            交易流水号列表
     * @param batchNo
     *            批次号
     * @param transStatus
     *            转账状态
     * @param apprOptId
     *            复核操作员编号
     * @param apprOptName
     *            复核操作员名称
     * @param apprRemark
     *            复核备注
     * @return 成功记录数
     */
    public int updateBatchTransOut(@Param("transNos") Set<String> transNos, @Param("batchNo") String batchNo,
            @Param("transStatus") Integer transStatus, @Param("apprOptId") String apprOptId,
            @Param("apprOptName") String apprOptName, @Param("apprRemark") String apprRemark);

    /**
     * 批量更新提交类型
     * 
     * @param transNos
     *            交易流水号列表
     * @param commitType
     *            提交类型
     * @return 成功记录数
     */
    public int updateBatchCommitType(@Param("transNos") Set<String> transNos, @Param("commitType") Integer commitType);

    /**
     * 更新转账结果
     * 
     * @param transResult
     *            转账结果
     * @param transNo
     *            交易流水号
     * @return 成功记录数
     */
    public int updateTransResult(@Param("transResult") Integer transResult, @Param("transNo") String transNo);
}
