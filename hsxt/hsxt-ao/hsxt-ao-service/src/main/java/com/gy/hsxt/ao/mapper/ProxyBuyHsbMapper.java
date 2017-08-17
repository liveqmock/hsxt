/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ao.bean.ProxyBuyHsb;

/**
 * 企业代兑互生币mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: ProxyBuyHsbMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-27 下午8:19:07
 * @version V3.0.0
 */
public interface ProxyBuyHsbMapper {

    /**
     * 插入企业代兑互生币记录
     * 
     * @param proxyBuyHsb
     *            记录信息
     * @return 成功记录数
     */
    public int insertProxyBuyHsb(ProxyBuyHsb proxyBuyHsb);

    /**
     * 插入POS代兑互生币记录
     * 
     * @param proxyBuyHsb
     *            记录信息
     * @return 成功记录数
     */
    public int insertPosBuyHsb(ProxyBuyHsb proxyBuyHsb);

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
     * 查询企业代兑互生币详情
     * 
     * @param transNo
     *            交易流水号
     * @return 代兑互生币详情
     */
    public ProxyBuyHsb findProxyBuyHsbInfo(@Param("transNo") String transNo);

    /**
     * 查询兑换互生币详情：根据互生号、终端编号、批次号、终端凭证号
     * 
     * @param hsResNo
     *            互生号
     * @param termNo
     *            终端编号
     * @param batchNo
     *            批次号
     * @param termRuncode
     *            终端凭证号
     * @return 代兑互生币信息
     */
    public ProxyBuyHsb findInfoByTermRuncode(@Param("hsResNo") String hsResNo, @Param("termNo") String termNo,
            @Param("batchNo") String batchNo, @Param("termRuncode") String termRuncode);

    /**
     * 查询兑换互生币详情：根据企业客户号和POS流水号
     * 
     * @param entCustId
     *            企业客户号
     * @param originNo
     *            POS流水号
     * @return 记录数
     */
    public int findInfoByEntCustIdOriginNo(@Param("entCustId") String entCustId, @Param("originNo") String originNo);

    /**
     * * 查询代兑互生币详情：根据互生号、终端编号、POS流水号
     * 
     * @param hsResNo
     *            互生号
     * @param originNo
     *            终端凭证号
     * @return 兑换互生币信息
     */
    public ProxyBuyHsb findPosBuyHsbInfo(@Param("hsResNo") String hsResNo, @Param("originNo") String originNo);

    /**
     * 根据（企业客户号、终端渠道、终端编号、批次编号）统计代兑互生币总金额与总笔数
     * 
     * @param entCustId
     *            企业客户号
     * @param channel
     *            终端渠道
     * @param termNo
     *            终端编号
     * @param batchNo
     *            批次编号
     * @return
     */
    public Map<String, Object> findProxyBuyHsbInfoByBatchNo(@Param("entCustId") String entCustId,
            @Param("channel") Integer channel, @Param("termNo") String termNo, @Param("batchNo") String batchNo);

}
