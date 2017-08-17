/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.common.TransTypeUtil;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.EntryAllot;

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
     * @param tradeType 业务类型
     * @return
     */
    public static EntryAllot pointEntryData(List<Alloc> pointList, int tradeType) {

        EntryAllot entryAllot = new EntryAllot();
        //生成分路流水号
        String entryNo = GuidAgent.getStringGuid(Constants.TRANS_NO_POINT10 + PsTools.getInstanceNo());
        entryAllot.setEntryNo(entryNo);
        for (Alloc alloc : pointList) {
            alloc.setEntryNo(entryNo);
        }

        //新对象，防止传入对象值篡改
        List<Alloc> newPointList = new ArrayList<>();
        newPointList.addAll(pointList);

        for (Alloc alloc : newPointList) {
            EntryHandle.basicInfo(entryAllot, alloc, tradeType);

            EntryHandle.consumerInfo(entryAllot, alloc, tradeType);

            EntryHandle.enterpriseInfo(entryAllot, alloc, tradeType);
        }

        String payMode3 = TransTypeUtil.transWay(entryAllot.getTransType());
        String payMode4 = TransTypeUtil.transStatus(entryAllot.getTransType());
        if (payMode3.equals(Constants.POINT_HSB)||payMode3.equals(Constants.POINT_CYBER)) {
            if (payMode4.equals(Constants.POINT_BUSS_TYPE0)||payMode4.equals(Constants.POINT_BUSS_TYPE8)) {
                if (entryAllot.getEntAddAmount() != null && entryAllot.getEntSubPointAmount() != null) {
                    BigDecimal settleAmount = Compute.sub(entryAllot.getEntAddAmount(),
                            entryAllot.getEntSubPointAmount());
                    entryAllot.setSettleAmount(settleAmount);
                }
            }
        }
        if (payMode3.equals(Constants.POINT_CASH) && payMode4.equals(Constants.POINT_BUSS_TYPE0))
        {
            if (entryAllot.getEntSubPointAmount() != null) {
                entryAllot.setSettleAmount(entryAllot.getEntSubPointAmount().negate());
            }
        }
        else {
            BigDecimal settleAmount;
            if (entryAllot.getEntAddPointAmount() != null) {
                if(entryAllot.getEntSubAmount() == null){
                    settleAmount=entryAllot.getEntAddPointAmount();
                }
                else {
                    settleAmount = Compute.sub(entryAllot.getEntSubAmount(),
                            entryAllot.getEntAddPointAmount()).negate();
                }
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
    public static void basicInfo(EntryAllot entryAllot, Alloc alloc, int tradeType) {
        // entryAllot.setEntryNo(alloc.getEntryNo());
        if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType) {
            entryAllot.setBatchNo(DateUtil.DateToString(DateUtil.today()));
        } else {
            entryAllot.setBatchNo(alloc.getBatchNo());
        }
        entryAllot.setRelTransNo(alloc.getRelTransNo());
        entryAllot.setSourceTransNo(alloc.getSourceTransNo());
        entryAllot.setTransType(alloc.getTransType());
        entryAllot.setWriteBack(alloc.getWriteBack());
        entryAllot.setIsSettle(alloc.getIsSettle());
        entryAllot.setSourceTransDate(alloc.getSourceTransDate());
    }

    /**
     * 消费者资金信息
     *
     * @param entryAllot
     * @param alloc
     * @param tradeType  业务类型
     */
    public static void consumerInfo(EntryAllot entryAllot, Alloc alloc, int tradeType) {
        if (alloc.getCustType() == CustType.PERSON.getCode()) {
            entryAllot.setPerHsNo(alloc.getHsResNo());
            entryAllot.setPerCustId(alloc.getCustId());
            if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode())) {
                if (Constants.HSB_POINT == tradeType) {
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setPerSubAmount(alloc.getSubAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType) {
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setPerAddAmount(alloc.getAddAmount());
                    }
                }

                if (Constants.HSB_POINT_CORRECT == tradeType) {
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setPerAddAmount(alloc.getAddAmount());
                    }
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setPerSubAmount(alloc.getSubAmount());
                    }
                }

            } else if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode())) {
                if (Constants.HSB_POINT == tradeType) {
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setPerAddPointAmount(alloc.getAddAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType) {
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setPerSubPointAmount(alloc.getSubAmount());
                    }
                }

                if (Constants.HSB_POINT_CORRECT == tradeType) {
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setPerAddPointAmount(alloc.getAddAmount());
                    }
                    if (null != alloc.getSubAmount()) {
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
     * @param alloc      * @param tradeType 业务类型
     */
    public static void enterpriseInfo(EntryAllot entryAllot, Alloc alloc, int tradeType) {
        if (alloc.getCustType() == CustType.MEMBER_ENT.getCode()
                || alloc.getCustType() == CustType.TRUSTEESHIP_ENT.getCode()) {
            entryAllot.setEntHsNo(alloc.getHsResNo());
            entryAllot.setEntCustId(alloc.getCustId());
            if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode())) {
                if (Constants.HSB_POINT == tradeType) {
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setEntSubPointAmount(alloc.getSubAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType) {
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setEntSubAmount(alloc.getSubAmount());
                    }
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setEntAddPointAmount(alloc.getAddAmount());
                    }
                }
                if (Constants.HSB_POINT_CORRECT == tradeType) {
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setEntSubPointAmount(alloc.getSubAmount());
                    }
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setEntAddPointAmount(alloc.getAddAmount());
                    }
                }
            } else if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20111.getCode())) {
                if (Constants.HSB_POINT == tradeType) {
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setEntAddAmount(alloc.getAddAmount());
                    }
                }
                if (Constants.HSB_POINT_CANCEL == tradeType || Constants.HSB_POINT_BACK == tradeType) {
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setEntSubAmount(alloc.getSubAmount());
                    }
                }

                if (Constants.HSB_POINT_CORRECT == tradeType) {
                    if (null != alloc.getSubAmount()) {
                        entryAllot.setEntSubAmount(alloc.getSubAmount());
                    }
                    if (null != alloc.getAddAmount()) {
                        entryAllot.setEntAddAmount(alloc.getAddAmount());
                    }
                }
            }
        }
    }
}

