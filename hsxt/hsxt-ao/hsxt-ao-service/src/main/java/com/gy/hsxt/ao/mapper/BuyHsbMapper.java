/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ao.bean.BuyHsb;

/**
 * 兑换互生币mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: BuyHsbMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-27 下午6:30:10
 * @version V3.0.0
 */
public interface BuyHsbMapper {

    /**
     * 插入兑换互生币记录
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @return 成功记录数
     */
    public int insertBuyHsb(BuyHsb buyHsb);

    /**
     * 更新交易结果
     * 
     * @param transNo
     *            交易流水号
     * @param transResult
     *            交易结果
     * @param remark
     *            备注
     * @return 成功记录数
     */
    public int updateTransResult(@Param("transNo") String transNo, @Param("transResult") Integer transResult,
            @Param("remark") String remark);

    /**
     * 更新状态
     * 
     * @param transNo
     *            交易流水号
     * @param payModel
     *            支付方式
     * @param transResult
     *            交易结果
     * @param payStatus
     *            支付状态
     * @param remark
     *            备注
     * @param bankTransNo
     *            银行流水号
     * @return 成功记录数
     */
    public int updateBuyHsbStatus(@Param("transNo") String transNo, @Param("payModel") Integer payModel,
            @Param("transResult") Integer transResult, @Param("payStatus") Integer payStatus,
            @Param("remark") String remark, @Param("bankTransNo") String bankTransNo);

    /**
     * 更新支付状态
     * 
     * @param transNo
     *            交易流水号
     * @param payStatus
     *            支付状态
     * @param remark
     *            备注
     * @return 成功记录数
     */
    public int updatePayStatus(@Param("transNo") String transNo, @Param("payStatus") Integer payStatus,
            @Param("remark") String remark);

    /**
     * 更新支付方式
     * 
     * @param transNo
     *            交易流水号
     * @param payModel
     *            支付方式
     * @return 成功记录数
     */
    public int updatePayModel(@Param("transNo") String transNo, @Param("payModel") Integer payModel);

    /**
     * 查询兑换互生币详情
     * 
     * @param transNo
     *            交易流水号
     * @return 兑换互生币信息
     */
    public BuyHsb findBuyHsbInfo(@Param("transNo") String transNo);

    /**
     * * 查询兑换互生币详情：根据互生号、终端编号、批次号、终端凭证号
     * 
     * @param hsResNo
     *            互生号
     * @param termNo
     *            终端编号
     * @param batchNo
     *            批次号
     * @param termRuncode
     *            终端凭证号
     * @return 兑换互生币信息
     */
    public BuyHsb findInfoByTermRuncode(@Param("hsResNo") String hsResNo, @Param("termNo") String termNo,
            @Param("batchNo") String batchNo, @Param("termRuncode") String termRuncode);

    /**
     * 查询兑换互生币详情根据客户号和POS流水号
     * 
     * @param custId
     *            客户号
     * @param originNo
     *            POS流水号
     * @return 记录数
     */
    public int findInfoByCustIdOriginNo(@Param("custId") String custId, @Param("originNo") String originNo);

    /**
     * * 查询兑换互生币详情：根据互生号、终端编号、POS流水号
     * 
     * @param hsResNo
     *            互生号
     * @param originNo
     *            终端凭证号
     * @return 兑换互生币信息
     */
    public BuyHsb findPosBuyHsbInfo(@Param("hsResNo") String hsResNo, @Param("originNo") String originNo);

    /**
     * 根据（企业客户号(客户号))、终端渠道、终端编号、批次编号）统计兑换互生币总金额与总笔数
     * 
     * @param entCustId
     *            客户号
     * @param channel
     *            终端渠道
     * @param termNo
     *            终端编号
     * @param batchNo
     *            批次编号
     * @return
     */
    public Map<String, Object> findBuyHsbInfoByBatchNo(@Param("entCustId") String entCustId,
            @Param("channel") Integer channel, @Param("termNo") String termNo, @Param("batchNo") String batchNo);

    /**
     * 查询未支付且未过期的兑换互生币信息
     * 
     * @param transNo
     *            交易流水号
     * @return 兑换互生币信息
     */
    public BuyHsb findUnPayBuyHsb(@Param("transNo") String transNo);

    /**
     * 根据订单日期查询网银兑换互生币数据,支付对账用
     * 
     * @param startDate
     *            开始订单日期
     * @param endDate
     *            结束订单日期，不包含
     * @return
     */
    public List<BuyHsb> findNetPayBuyHsb(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 更新过期且未支付的交易结果为已过期（4）
     * 
     * @return
     */
    public List<String> updateExpireAndNoPayList();

}
