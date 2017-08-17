/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.distribution.handle;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.es.common.*;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.EntryAllot;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: EntryHandle
 * @Description: 分录数据处理
 * @author: chenhz
 * @date: 2016-2-24 下午2:05:55
 */

public class EntryHandle {

    /**
     * 分录数据处理(积分、支付)
     * 
     * @param pointList
     * @param tradeType
     *            业务类型
     * @return
     */
    public static EntryAllot mergeEntryData(List<Alloc> pointList, int tradeType) {

        EntryAllot entryAllot = new EntryAllot();
        // 生成分路流水号
        String entryNo = EsTools.GUID(Constants.TRANS_NO_POINT10);
        entryAllot.setEntryNo(entryNo);
        for (Alloc alloc : pointList)
        {
            alloc.setEntryNo(entryNo);
        }

        // 新对象，防止传入对象值篡改
        List<Alloc> newPointList = new ArrayList<>();
        newPointList.addAll(pointList);

        for (Alloc alloc : newPointList)
        {
            EntryHandle.basicInfo(entryAllot, alloc);

            EntryHandle.consumerInfo(entryAllot, alloc, tradeType);

            EntryHandle.enterpriseInfo(entryAllot, alloc, tradeType);
        }
        String payMode4 = TransTypeUtil.transStatus(entryAllot.getTransType());
        String payMode3 = TransTypeUtil.transWay(entryAllot.getTransType());
        if (payMode4.equals(Constants.POINT_BUSS_TYPE4) || payMode4.equals(Constants.POINT_BUSS_TYPE0))
        {
            // 正常业务
            if (entryAllot.getEntAddAmount() != null && entryAllot.getEntSubPointAmount() != null)
            {
                BigDecimal entSubServiceFee = null;
                // 是否收取商业服务费，由电商穿过来的。
                if (entryAllot.getIsDeduction() == 0)
                {
                    // 商业服务费
                    entSubServiceFee = Compute.mulCeiling(entryAllot.getEntAddAmount(), EsRedisUtil
                            .getBusinessServiceRate(), Constants.SURPLUS_TWO);
                    entryAllot.setEntSubServiceFee(entSubServiceFee);
                }
                if (!payMode3.equals(Constants.POINT_CASH))
                {
                    BigDecimal settleAmount = Compute.sub(entryAllot.getEntAddAmount(), entryAllot
                            .getEntSubPointAmount());
                    settleAmount = Compute.sub(settleAmount, entSubServiceFee);
                    entryAllot.setSettleAmount(settleAmount);
                }
            }
        }

        if (Constants.HSB_POINT_BACK == tradeType || Constants.HSB_POINT_CANCEL == tradeType)
        {
            // 退货撤单
            if (entryAllot.getEntSubAmount() != null && entryAllot.getEntAddPointAmount() != null)
            {
                BigDecimal entAddServiceFee = BigDecimal.ZERO;
                // 是否收取商业服务费，由电商穿过来的。
                if (entryAllot.getIsDeduction() == 0)
                {
                    if (entryAllot.getIsSettle() != 0)
                    {
                        // 商业服务费
                        entAddServiceFee = Compute.mulCeiling(entryAllot.getEntSubAmount(), EsRedisUtil
                                .getBusinessServiceRate(), Constants.SURPLUS_TWO);
                        entryAllot.setEntAddServiceFee(entAddServiceFee);
                    }
                }
                BigDecimal settleAmount = Compute.sub(Compute.add(entryAllot.getEntAddPointAmount(), entAddServiceFee),
                        entryAllot.getEntSubAmount());
                entryAllot.setSettleAmount(settleAmount);

            }
        }

        return entryAllot;
    }

    /**
     * 分录基本信息
     * 
     * @param entryAllot
     * @param alloc
     */
    private static void basicInfo(EntryAllot entryAllot, Alloc alloc) {
        entryAllot.setIsSettle(alloc.getIsSettle());
        entryAllot.setIsDeduction(alloc.getIsDeduction());
        entryAllot.setRelTransNo(alloc.getRelTransNo());
        entryAllot.setSourceTransNo(alloc.getSourceTransNo());
        entryAllot.setRelEntryNo(alloc.getEntryNo());
        entryAllot.setEntryNo(alloc.getEntryNo());
        entryAllot.setBatchNo(alloc.getBatchNo());
        entryAllot.setTransType(alloc.getTransType());
        entryAllot.setWriteBack(alloc.getWriteBack());
        entryAllot.setSourceTransDate(alloc.getSourceTransDate());
    }

    /**
     * 消费者资金信息
     * 
     * @param entryAllot
     * @param alloc
     * @param tradeType
     *            业务类型
     */
    private static void consumerInfo(EntryAllot entryAllot, Alloc alloc, int tradeType) {
        if (alloc.getCustType() == CustType.PERSON.getCode() || alloc.getCustType() == CustType.NOT_HS_PERSON.getCode())
        {
            entryAllot.setPerHsNo(alloc.getHsResNo());
            entryAllot.setPerCustId(alloc.getCustId());
            if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode()))
            {
                if (Constants.HSB_POINT == tradeType)
                {
                    if (null != alloc.getSubAmount())
                    {
                        entryAllot.setPerSubAmount(alloc.getSubAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType)
                {
                    if (null != alloc.getAddAmount())
                    {
                        entryAllot.setPerAddAmount(alloc.getAddAmount());
                    }
                }

            }
            else if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode()))
            {
                if (Constants.HSB_POINT == tradeType)
                {
                    if (null != alloc.getAddAmount() && alloc.getCustType() == CustType.PERSON.getCode())
                    {
                        entryAllot.setPerAddPointAmount(alloc.getAddAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType)
                {
                    if (null != alloc.getSubAmount() && alloc.getCustType() == CustType.PERSON.getCode())
                    {
                        entryAllot.setPerSubPointAmount(alloc.getSubAmount());
                    }
                }
            }
        }
    }

    /**
     * 企业资金信息
     * 
     * @param entryAllot
     * @param alloc
     * @param tradeType
     *            业务类型
     */
    private static void enterpriseInfo(EntryAllot entryAllot, Alloc alloc, int tradeType) {
        if (alloc.getCustType() == CustType.MEMBER_ENT.getCode()
                || alloc.getCustType() == CustType.TRUSTEESHIP_ENT.getCode())
        {
            entryAllot.setEntHsNo(alloc.getHsResNo());
            entryAllot.setEntCustId(alloc.getCustId());
            if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode()))
            {
                if (Constants.HSB_POINT == tradeType)
                {
                    if (null != alloc.getSubAmount())
                    {
                        entryAllot.setEntSubPointAmount(alloc.getSubAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType)
                {
                    if (null != alloc.getSubAmount())
                    {
                        entryAllot.setEntSubAmount(alloc.getSubAmount());
                    }
                    if (null != alloc.getAddAmount())
                    {
                        entryAllot.setEntAddPointAmount(alloc.getAddAmount());
                    }

                }
            }
            else if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20111.getCode()))
            {
                if (Constants.HSB_POINT == tradeType)
                {
                    if (null != alloc.getAddAmount())
                    {
                        entryAllot.setEntAddAmount(alloc.getAddAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType)
                {
                    if (null != alloc.getSubAmount())
                    {
                        entryAllot.setEntSubAmount(alloc.getSubAmount());
                    }
                }
            }
        }
    }

    /**
     * 计算现金商业服务费
     * 
     * @param amount
     *            金额
     * @param transType
     *            业务类型
     */
    public static EntryAllot cashBusinessServerFee(String transType, BigDecimal amount, BigDecimal pointAmount,
            int isDeduction) {
        if (StringUtils.isEmpty(transType))
        {
            EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR.getCode(),
                    "cashBusinessServerFee方法中transType不能为空");
        }
        EntryAllot entryAllot = null;
        // 计算出商业服务
        String payMode3 = TransTypeUtil.transWay(transType);
        if (payMode3.equals(Constants.POINT_CASH))
        {
            entryAllot = new EntryAllot();
            BigDecimal settleAmount = pointAmount;
            // 是否收取商业服务费，由电商穿过来的。
            if (isDeduction == 0)
            {
                BigDecimal entSubServiceFee = Compute.mulCeiling(amount, EsRedisUtil.getBusinessServiceRate(),
                        Constants.SURPLUS_TWO);
                settleAmount = Compute.add(pointAmount, entSubServiceFee);
                entryAllot.setEntSubServiceFee(entSubServiceFee);
            }
            entryAllot.setSettleAmount(settleAmount.negate());
        }
        return entryAllot;
    }

    /**
     * 计算部分退货商业服务费
     * 
     * @param amount
     *            金额
     * @param transType
     *            业务类型
     */
    public static EntryAllot backBusinessServerFee(String transType, BigDecimal amount, BigDecimal pointAmount,
            int isSettle, int isDeduction) {
        if (StringUtils.isEmpty(transType))
        {
            EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR.getCode(),
                    "cashBusinessServerFee方法中transType不能为空");
        }
        EntryAllot entryAllot = new EntryAllot();
        BigDecimal settleAmount = pointAmount;
        // 是否收取商业服务费，由电商穿过来的。
        if (isDeduction == 0)
        {
            if (isSettle != 0)
            {
                entryAllot = new EntryAllot();
                BigDecimal entAddServiceFee = Compute.mulFloor(amount, EsRedisUtil.getBusinessServiceRate(),
                        Constants.SURPLUS_TWO);
                settleAmount = Compute.add(pointAmount, entAddServiceFee);
                entryAllot.setEntAddServiceFee(entAddServiceFee);
            }
        }
        entryAllot.setSettleAmount(settleAmount);

        return entryAllot;
    }

    /**
     * 计算现金商业服务费撤单退货
     * 
     * @param amount
     *            金额
     * @param transType
     *            业务类型
     */
    public static EntryAllot cashBCBusinessServerFee(String transType, BigDecimal amount, BigDecimal pointAmount,
            int isSettle, int isDeduction) {
        if (StringUtils.isEmpty(transType))
        {
            EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR.getCode(),
                    "cashBusinessServerFee方法中transType不能为空");
        }
        EntryAllot entryAllot = null;
        String payMode3 = TransTypeUtil.transWay(transType);
        if (payMode3.equals(Constants.POINT_CASH))
        {
            entryAllot = new EntryAllot();
            BigDecimal settleAmount = pointAmount;
            // 是否收取商业服务费，由电商穿过来的。
            if (isDeduction == 0)
            {
                if (isSettle != 0)
                {
                    BigDecimal entAddServiceFee = Compute.mulCeiling(amount, EsRedisUtil.getBusinessServiceRate(),
                            Constants.SURPLUS_TWO);
                    settleAmount = Compute.add(pointAmount, entAddServiceFee);
                    entryAllot.setEntAddServiceFee(entAddServiceFee);
                }
            }
            entryAllot.setSettleAmount(settleAmount);

        }
        return entryAllot;
    }
}
