/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.ps.common.*;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointAllot;
import com.gy.hsxt.ps.validator.GeneralValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: AllocHandle
 * @Description: 分配处理(积分、收税、互生币、商业服务费)
 * @author: chenhongzhi
 * @date: 2015-2-5 上午9:20:21
 */

public class AllocHandle {

    private static String path = Constants.AC_SETTLEMENT_FILE_PATH;
    /**
     * 积分分配后扣城市税收
     *
     * @param taxMapMap 积分汇总信息
     * @return 返回积分分配信息
     */
    public static List<Alloc> pointCityTax(List<Alloc> list, Map<String, String> taxMapMap) throws Exception{
        List<Alloc> taxList = new ArrayList<>();
        for (Alloc alloc : list) {
            // 汇总流水号
            alloc.setPvNo(GuidAgent.getStringGuid(Constants.NO_POINT_SUM21 + PsTools.getInstanceNo()));

            if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode())) {
                alloc.setRelPvNo(alloc.getPvNo());
                if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode())) {
                    String hsResNo = alloc.getHsResNo();
                    // 增加税率
                    String taxRate = taxMapMap.get(hsResNo);
                    double rate = Double.parseDouble(taxRate);
                    alloc.setTaxRate(new BigDecimal(taxRate));

                    alloc.setTaxNo(GuidAgent.getStringGuid(Constants.NO_POINT_TAX22 + PsTools.getInstanceNo()));
                    alloc.setTaxAccType(AccountType.ACC_TYPE_POINT10510.getCode());

                    if (rate > 0) {
                        // 服务公司和托管企业有税
                        if (HsResNoUtils.getCustTypeByHsResNo(hsResNo) == CustType.TRUSTEESHIP_ENT.getCode() || HsResNoUtils.getCustTypeByHsResNo(hsResNo) == CustType.SERVICE_CORP.getCode()) {
                            //计算出税收
                            BigDecimal taxM = AllocItem.getTaxPoint(
                                    PsTools.formatBigDec(alloc.getAddAmount() != null ? alloc.getAddAmount() : alloc.getSubAmount()), taxRate);
                            //大于0时放入增向金额否则放入减向金额
                            if (taxM.compareTo(BigDecimal.ZERO) == 1 || taxM.compareTo(BigDecimal.ZERO) == 0) {
                                alloc.setTaxAddAmount(taxM);
                            } else {
                                alloc.setTaxSubAmount(taxM);
                            }
                            taxList.add(alloc);
                        }
                    }
                }
            }
        }
        return taxList;
    }

    /**
     * 日终暂存商业服务费
     *
     * @param aList               电商分录的零售交易订单信息
     * @param businessParamSearch 商业服务费比例
     * @return
     */
    public static List<Alloc> serviceDayCsc(List<Alloc> aList, BusinessParamSearch businessParamSearch) throws Exception {
        List<Alloc> list = new ArrayList<>();
        BusinessCustParamItemsRedis businessCustParamItemsRedis;
        for (Alloc alloc : aList) {
            businessCustParamItemsRedis = businessParamSearch.searchCustParamItemsByIdKey(alloc.getCustId(), BusinessParam.CONFIG_PARA.getCode(), BusinessParam.HS_BUSINESS_SERVICE_RATE.getCode());
            GeneralValidator.notNull(businessCustParamItemsRedis, RespCode.PS_PARAM_ERROR, "商业服务费对象为空！");
            GeneralValidator.notNull(businessCustParamItemsRedis.getSysItemsValue(), RespCode.PS_PARAM_ERROR, "商业服务费比例为空！");
            // 基本信息绑定
            alloc.setTollNo(GuidAgent.getStringGuid(Constants.NO_SERVICE_FEE32 + PsTools.getInstanceNo()));
            //alloc.setBatchNo(PsTools.getBeforeDay());
            alloc.setTransType(Constants.HSB_BUSINESS_TEMPORARY_CSC);
            alloc.setSettleType(Constants.SETTLE_TYPE_DAY1);
            alloc.setCscType(Constants.CSC_TYPE_SERVICE_FEE1);
            alloc.setAmountRate(new BigDecimal(businessCustParamItemsRedis.getSysItemsValue()));
            // 计算商业服务费
            //alloc.setTollAddAmount(AllocItem.getBusinessServiceFee(PsTools.formatBigDec(alloc.getCscTurnover())));
            // 计算商业服务费
            //alloc.setTollAddAmount(Compute.mulFloor(PsTools.formatBigDec(alloc.getCscTurnover()), new BigDecimal(businessCustParamItemsRedis.getSysItemsValue()), Constants.SURPLUS_TWO));
            alloc.setTollAddAmount(alloc.getCscTurnover());
            alloc.setTollAccType(AccountType.ACC_TYPE_POINT20421.getCode());
            alloc.setRelCscNo(alloc.getHsbNo());
            alloc.setIsSettle(Constants.IS_SETTLE1);
            //为零的数据不要
            if (alloc.getTollAddAmount().compareTo(BigDecimal.ZERO) != 0) {
                list.add(alloc);
            }
        }
        return list;
    }

    /**
     * 扣税后汇总
     *
     * @param sumList 积分汇总信息
     * @param taxList 积分扣税信息
     * @return
     */
    public static void taxAfterSum(List<Alloc> sumList, List<Alloc> taxList) throws Exception{
        for (Alloc sumAlloc : sumList) {
            if (HsResNoUtils.getCustTypeByHsResNo(sumAlloc.getHsResNo()) == CustType.TRUSTEESHIP_ENT.getCode() || HsResNoUtils.getCustTypeByHsResNo(sumAlloc.getHsResNo()) == CustType.SERVICE_CORP.getCode()) {
                // 判断客户ID是否相等与账户是否相等
                for (Alloc taxAlloc : taxList) {
                    if (sumAlloc.getCustId().equals(taxAlloc.getCustId())
                            && taxAlloc.getTaxAccType().equals(
                            AccountType.ACC_TYPE_POINT10510.getCode())) {
                        // 计算积分汇总
                        BigDecimal sumPoint = AllocItem.getSumSubTax(sumAlloc.getAddAmount() != null ? sumAlloc.getAddAmount() : sumAlloc.getSubAmount(),
                                taxAlloc.getTaxAddAmount() != null ? taxAlloc.getTaxAddAmount() : (taxAlloc.getTaxSubAmount() != null ? taxAlloc.getTaxSubAmount() : BigDecimal.ZERO));
                        //值为正数放入增向金额，负数放入减向金额
                        if (sumPoint.compareTo(BigDecimal.ZERO) == 1 || sumPoint.compareTo(BigDecimal.ZERO) == 0) {
                            sumAlloc.setAddAmount(sumPoint);
                            sumAlloc.setSubAmount(null);
                        } else {
                            sumAlloc.setSubAmount(sumPoint);
                            sumAlloc.setAddAmount(null);
                        }
                    }
                }
            }
        }

    }

    /**
     * 积分日终汇总
     *
     * @param sumList   积分汇总信息
     * @param custIdMap 积分扣税信息
     * @return
     */
    public static void pointEndDaySum(List<Alloc> sumList, Map<String, String> custIdMap) throws Exception{

        for (Alloc alloc : sumList) {
            alloc.setPvNo(GuidAgent.getStringGuid(Constants.NO_POINT_SUM21 + PsTools.getInstanceNo()));
            // 取客户号
            for (Map.Entry<String, String> hsNo : custIdMap.entrySet()) {
                if (alloc.getHsResNo().equals(hsNo.getKey())) {
                    alloc.setCustId(hsNo.getValue());
                }
            }
        }

    }

    /**
     * 判断互生号在缓存中是否查询都到客户ID和税收
     *
     * @param sumList 积分汇总信息
     * @param map
     * @return
     */
    public static void isFindRedis(List<Alloc> sumList, Map<String, String> map,String describe) throws HsException {

        List<Alloc> noFindResNo = new ArrayList<>();

        List<String> noFindResNos = new ArrayList<>();

        for (Alloc alloc : sumList) {
            String value = map.get(alloc.getHsResNo());
            if (StringUtils.isEmpty(value)) {
                noFindResNo.add(alloc);
                noFindResNos.add(alloc.getHsResNo());
            }
        }
        //没有查到的剔除
        sumList.removeAll(noFindResNo);
        if (!CollectionUtils.isEmpty(noFindResNo)) {
            SystemLog.debug("AllocHandle","isFindRedis",JSON.toJSONString(noFindResNos));
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_REDIS_NOT_VALUE.getCode(), "功能："+describe+"从缓存中查，互生号查不到的是：" + JSON.toJSONString(noFindResNos));
        }
    }

    /**
     * 积分日终汇总拆分
     *
     * @param listPointAllot 积分汇总信息
     *                       积分扣税信息
     * @return
     */
    public static List<Alloc> pointAllotSplit(List<PointAllot> listPointAllot) throws Exception{
        List<Alloc> listAlloc = new ArrayList<>();
        if (!CollectionUtils.isEmpty(listPointAllot)) {
            for (PointAllot pointAllot : listPointAllot) {
                if (pointAllot != null) {
                    //为零时不记账
            /*		if (BigDecimal.ZERO!=pointAllot.getTrusteeAddPoint()) {
                        continue;
					}*/

                    /** 托管企业 */
                    if (StringUtils.isNotBlank(pointAllot.getTrusteeEntHsNo())) {
                        Alloc allocTrusteeEnt = new Alloc();
                        allocTrusteeEnt.setHsResNo(pointAllot.getTrusteeEntHsNo());
                        //值为正数放入增向金额，负数放入减向金额
                        if (pointAllot.getTrusteeAddPoint().compareTo(BigDecimal.ZERO) == 1 || pointAllot.getTrusteeAddPoint().compareTo(BigDecimal.ZERO) == 0) {
                            allocTrusteeEnt.setAddAmount(pointAllot.getTrusteeAddPoint());
                        } else {
                            allocTrusteeEnt.setSubAmount(pointAllot.getTrusteeAddPoint());
                        }
                        allocTrusteeEnt.setIsSettle(Constants.IS_SETTLE0);
                        allocTrusteeEnt.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
                        allocTrusteeEnt.setPvAddCount(pointAllot.getPvAddCount());
                        allocTrusteeEnt.setBatchNo(pointAllot.getBatchNo());
                        allocTrusteeEnt.setAccType(AccountType.ACC_TYPE_POINT10110.getCode());
                        listAlloc.add(allocTrusteeEnt);
                    }
                    /** 服务公司 */
                    if (StringUtils.isNotBlank(pointAllot.getServiceEntHsNo())) {
                        Alloc allocServiceEnt = new Alloc();
                        allocServiceEnt.setHsResNo(pointAllot.getServiceEntHsNo());
                        //值为正数放入增向金额，负数放入减向金额
                        if (pointAllot.getServiceAddPoint().compareTo(BigDecimal.ZERO) == 1 || pointAllot.getServiceAddPoint().compareTo(BigDecimal.ZERO) == 0) {
                            allocServiceEnt.setAddAmount(pointAllot.getServiceAddPoint());
                        } else {
                            allocServiceEnt.setSubAmount(pointAllot.getServiceAddPoint());
                        }
                        allocServiceEnt.setIsSettle(Constants.IS_SETTLE0);
                        allocServiceEnt.setCustType(CustType.SERVICE_CORP.getCode());
                        allocServiceEnt.setPvAddCount(pointAllot.getPvAddCount());
                        allocServiceEnt.setBatchNo(pointAllot.getBatchNo());
                        allocServiceEnt.setAccType(AccountType.ACC_TYPE_POINT10110.getCode());
                        listAlloc.add(allocServiceEnt);
                    }
                    /** 管理公司 */
                    if (StringUtils.isNotBlank(pointAllot.getManageEntHsNo())) {
                        Alloc allocManageEnt = new Alloc();
                        allocManageEnt.setHsResNo(pointAllot.getManageEntHsNo());
                        //值为正数放入增向金额，负数放入减向金额
                        if (pointAllot.getManageAddPoint().compareTo(BigDecimal.ZERO) == 1 || pointAllot.getManageAddPoint().compareTo(BigDecimal.ZERO) == 0) {
                            allocManageEnt.setAddAmount(pointAllot.getManageAddPoint());
                        } else {
                            allocManageEnt.setSubAmount(pointAllot.getManageAddPoint());
                        }
                        allocManageEnt.setIsSettle(Constants.IS_SETTLE0);
                        allocManageEnt.setCustType(CustType.MANAGE_CORP.getCode());
                        allocManageEnt.setPvAddCount(pointAllot.getPvAddCount());
                        allocManageEnt.setBatchNo(pointAllot.getBatchNo());
                        allocManageEnt.setAccType(AccountType.ACC_TYPE_POINT10110.getCode());
                        listAlloc.add(allocManageEnt);
                    }
                    /** 地区平台 */
                    if (StringUtils.isNotBlank(pointAllot.getPaasEntHsNo())) {
                        if (pointAllot.getPaasAddPoint() != null) {
                            Alloc allocPaasEnt = new Alloc();
                            allocPaasEnt.setHsResNo(pointAllot.getPaasEntHsNo());
                            //值为正数放入增向金额，负数放入减向金额
                            if (pointAllot.getPaasAddPoint().compareTo(BigDecimal.ZERO) == 1 || pointAllot.getPaasAddPoint().compareTo(BigDecimal.ZERO) == 0) {
                                allocPaasEnt.setAddAmount(pointAllot.getPaasAddPoint());
                            } else {
                                allocPaasEnt.setSubAmount(pointAllot.getPaasAddPoint());
                            }
                            allocPaasEnt.setIsSettle(Constants.IS_SETTLE0);
                            allocPaasEnt.setCustType(CustType.AREA_PLAT.getCode());
                            allocPaasEnt.setPvAddCount(pointAllot.getPvAddCount());
                            allocPaasEnt.setBatchNo(pointAllot.getBatchNo());
                            allocPaasEnt.setAccType(AccountType.ACC_TYPE_POINT10300.getCode());
                            listAlloc.add(allocPaasEnt);
                        }
                    }
                    /** 地区平台结余增向积分额 */
                    if (StringUtils.isNotBlank(pointAllot.getPaasEntHsNo())) {
                        if (pointAllot.getSurplusAddPoint() != null) {
                            Alloc allocSurplus = new Alloc();
                            allocSurplus.setHsResNo(pointAllot.getPaasEntHsNo());
                            //值为正数放入增向金额，负数放入减向金额
                            if (pointAllot.getSurplusAddPoint().compareTo(BigDecimal.ZERO) == 1 || pointAllot.getSurplusAddPoint().compareTo(BigDecimal.ZERO) == 0) {
                                allocSurplus.setAddAmount(pointAllot.getSurplusAddPoint());
                            } else {
                                allocSurplus.setSubAmount(pointAllot.getSurplusAddPoint());
                            }
                            allocSurplus.setIsSettle(Constants.IS_SETTLE0);
                            allocSurplus.setCustType(CustType.AREA_PLAT.getCode());
                            allocSurplus.setPvAddCount(pointAllot.getPvAddCount());
                            allocSurplus.setBatchNo(pointAllot.getBatchNo());
                            allocSurplus.setAccType(AccountType.ACC_TYPE_POINT10220.getCode());
                            listAlloc.add(allocSurplus);
                        }
                    }

                    /** 地区平台非持卡人收入*/
                    if (StringUtils.isNotBlank(pointAllot.getPaasEntHsNo())) {
                        if (pointAllot.getNoCardPaasAddPoint() != null) {
                            Alloc noCardPaasAddPoint = new Alloc();
                            noCardPaasAddPoint.setHsResNo(pointAllot.getPaasEntHsNo());
                            //值为正数放入增向金额，负数放入减向金额
                            if (pointAllot.getNoCardPaasAddPoint().compareTo(BigDecimal.ZERO) == 1 || pointAllot.getNoCardPaasAddPoint().compareTo(BigDecimal.ZERO) == 0) {
                                noCardPaasAddPoint.setAddAmount(pointAllot.getNoCardPaasAddPoint());
                            } else {
                                noCardPaasAddPoint.setSubAmount(pointAllot.getNoCardPaasAddPoint());
                            }
                            noCardPaasAddPoint.setIsSettle(Constants.IS_SETTLE0);
                            noCardPaasAddPoint.setCustType(CustType.AREA_PLAT.getCode());
                            noCardPaasAddPoint.setPvAddCount(pointAllot.getPvAddCount());
                            noCardPaasAddPoint.setBatchNo(pointAllot.getBatchNo());
                            noCardPaasAddPoint.setAccType(AccountType.ACC_TYPE_POINT10210.getCode());
                            listAlloc.add(noCardPaasAddPoint);
                        }
                    }
                }
            }
        }
        return listAlloc;
    }

    /**
     * 待清算账户减商业服务费后汇总到流通币账户(线下收入)
     *
     * @param hsbList  线下收入信息
     * @param dataList 存储账务系统需要的数据
     * @return
     */
    public static void posHsbSumList(List<Alloc> hsbList,
                                     List<Map<String, String>> dataList, String batchNo) throws Exception{
        // 遍历线下收入汇总
        for (Alloc alloc : hsbList) {
            Map<String, String> map = new HashMap<>();
            String hsbSumNo = GuidAgent.getStringGuid(Constants.NO_HSB_OFFLINE_SUM31 + PsTools.getInstanceNo());
            String accType = AccountType.ACC_TYPE_POINT20110.getCode();
            // String batchNo = PsTools.getBeforeDay();
            alloc.setBatchNo(batchNo);
            alloc.setHsbNo(hsbSumNo);
            alloc.setAccType(accType);
            alloc.setTransType(Constants.HSB_BUSINESS_OFFLINE_CSC);
            if (alloc.getDeductPointAmount() == null) {
                alloc.setDeductPointAmount(BigDecimal.ZERO);
            }

            if (alloc.getDayHsbTurnover() == null) {
                alloc.setDayHsbTurnover(BigDecimal.ZERO);
            }
            //计算日结互生币金额
            BigDecimal hsbAmount = Compute.sub(alloc.getDayHsbTurnover(), alloc.getDeductPointAmount());
            if (alloc.getDayBackPointTurnover() == null) {
                alloc.setDayBackPointTurnover(BigDecimal.ZERO);
            }
            hsbAmount = Compute.add(hsbAmount, alloc.getDayBackPointTurnover());

            if (hsbAmount.compareTo(BigDecimal.ZERO)==1) {
                alloc.setAddAmount(hsbAmount);
                alloc.setSubAmount(null);
            } else {
                alloc.setSubAmount(BigDecimal.valueOf(Math.abs(hsbAmount.doubleValue())));
                alloc.setAddAmount(null);
            }

            if (CustType.MEMBER_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(alloc
                    .getHsResNo())) {
                alloc.setCustType(CustType.MEMBER_ENT.getCode());

            } else if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils
                    .getCustTypeByHsResNo(alloc.getHsResNo())) {
                alloc.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
            }
            map.put("hsbSumNo", hsbSumNo);
            map.put("custId", alloc.getCustId());
            map.put("hsResNo", alloc.getHsResNo());
            map.put("accType", accType);
            map.put("custType", alloc.getCustType().toString());
            map.put("batchNo", batchNo);
            map.put("transType", Constants.HSB_BUSINESS_OFFLINE_CSC);
            map.put("writeBack", TransTypeUtil.writeBack(Constants.HSB_BUSINESS_OFFLINE_CSC));
            map.put("addAmount", StringUtils.isNotEmpty(Objects.toString(alloc.getAddAmount())) ? Objects.toString(alloc.getAddAmount()) : null);
            map.put("subAmount", StringUtils.isNotEmpty(Objects.toString(alloc.getSubAmount())) ? Objects.toString(alloc.getSubAmount()) : null);
            dataList.add(map);
        }
    }

    /**
     * 待清算账户减商业服务费后汇总到流通币账户(商城收入)
     *
     * @param hsbList  企业商城收入信息
     * @param dataList 存储账务系统需要的数据
     * @return
     */
    public static void ecHsbSumList(List<Alloc> hsbList,
                                    List<Map<String, String>> dataList, String batchNo) throws Exception{
        // 遍历商城零售收入汇总
        for (Alloc subAlloc : hsbList) {
            Map<String, String> map = new HashMap<>();
            String hsbNo = GuidAgent.getStringGuid(Constants.NO_HSB_ONLINE_SUM30 + PsTools.getInstanceNo());
            String accType = AccountType.ACC_TYPE_POINT20110.getCode();
            // String batchNo = runDate;
            subAlloc.setBatchNo(batchNo);
            subAlloc.setHsbNo(hsbNo);
            subAlloc.setAccType(accType);
            if (subAlloc.getDeductPointAmount() == null) {
                subAlloc.setDeductPointAmount(BigDecimal.ZERO);
            }
            if (subAlloc.getDayHsbTurnover() == null) {
                subAlloc.setDayHsbTurnover(BigDecimal.ZERO);
            }
            //计算日结互生币金额
            BigDecimal hsbAmount = Compute.sub(subAlloc.getDayHsbTurnover(), subAlloc.getDeductPointAmount());
            if (subAlloc.getCscTurnover() == null) {
                subAlloc.setCscTurnover(BigDecimal.ZERO);
            }
            hsbAmount = Compute.sub(hsbAmount, subAlloc.getCscTurnover());
            if (subAlloc.getDayBackPointTurnover() == null) {
                subAlloc.setDayBackPointTurnover(BigDecimal.ZERO);
            }
            hsbAmount = Compute.add(hsbAmount, subAlloc.getDayBackPointTurnover());
            if (hsbAmount.compareTo(BigDecimal.ZERO) >= 0) {
                subAlloc.setAddAmount(hsbAmount);
                subAlloc.setSubAmount(null);
            } else {
                subAlloc.setSubAmount(BigDecimal.valueOf(Math.abs(hsbAmount.doubleValue())));
                subAlloc.setAddAmount(null);
            }
            subAlloc.setTransType(Constants.HSB_BUSINESS_ONLINE_CSC);

            if (CustType.MEMBER_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(subAlloc
                    .getHsResNo())) {
                subAlloc.setCustType(CustType.MEMBER_ENT.getCode());

            } else if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils
                    .getCustTypeByHsResNo(subAlloc.getHsResNo())) {
                subAlloc.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
            }
            map.put("hsbSumNo", hsbNo);
            map.put("custId", subAlloc.getCustId());
            map.put("custType", subAlloc.getCustType().toString());
            map.put("hsResNo", subAlloc.getHsResNo());
            map.put("accType", accType);
            map.put("batchNo", batchNo);

            map.put("transType", Constants.HSB_BUSINESS_ONLINE_CSC);
            map.put("writeBack", TransTypeUtil.writeBack(Constants.HSB_BUSINESS_ONLINE_CSC));
            map.put("addAmount", StringUtils.isNotEmpty(Objects.toString(subAlloc.getAddAmount())) ? Objects.toString(subAlloc.getAddAmount()) : null);
            map.put("subAmount", StringUtils.isNotEmpty(Objects.toString(subAlloc.getSubAmount())) ? Objects.toString(subAlloc.getSubAmount()) : null);
            dataList.add(map);
        }
    }

    /**
     * 日终互生币汇总(待清算账户转流通币账户) 暂未用
     *
     * @param hsbList
     * @param dataList
     * @return
     */
    public static List<Alloc> hsbSumAdd(List<Alloc> hsbList, List<Map<String, String>> dataList)throws Exception{
        for (int i = 0; i < hsbList.size(); i++) {
            Alloc addAlloc = hsbList.get(i);
            Map<String, String> map = new HashMap<>();
            String hsbSumUid = GuidAgent.getStringGuid(Constants.NO_HSB_ONLINE_SUM30 + PsTools.getInstanceNo());
            String accType = AccountType.ACC_TYPE_POINT20110.getCode();
            map.put("hsbSumNo", hsbSumUid);
            map.put("custId", addAlloc.getCustId());
            map.put("hsResNo", addAlloc.getHsResNo());
            map.put("accType", accType);
            map.put("batchNo", addAlloc.getBatchNo());
            map.put("addAmount", addAlloc.getSubAmount().toString());
            map.put("subAmount", "0.00");
            map.put("addCount", addAlloc.getHsbAddCount().toString());
            addAlloc.setHsbNo(hsbSumUid);
            addAlloc.setAccType(accType);
            addAlloc.setAddAmount(addAlloc.getSubAmount());
            addAlloc.setSubAmount(new BigDecimal(0.00));
            dataList.add(map);
        }
        return hsbList;
    }

    /**
     * 月终服务公司的商业服务费结算
     *
     * @param serviceList 当月的商业服务费信息
     */
    public static List<Alloc> serviceMonthServiceFee(List<Alloc> serviceList,Map<String, String> custIdMap,Map<String, String> taxMap) throws Exception{
        CommercialServiceFee csf = new CommercialServiceFee();
        for (Alloc alloc : serviceList) {
            if (CollectionUtils.isEmpty(taxMap)) {
                alloc.setTaxRate(BigDecimal.ZERO);
            } else {
                alloc.setTaxRate(new BigDecimal(taxMap.get(alloc.getHsResNo())));
            }
            alloc.setCustId(custIdMap.get(alloc.getHsResNo()));
            csf.addCommercialServiceFee(alloc);
        }
        return csf.getAllocList();
    }

    /**
     * 月终服务公司的商业服务费结算(扣税)
     *
     * @param serviceList 当月的商业服务费信息
     */
    public static List<Alloc> serviceMonthServiceFeeTaxTate(List<Alloc> serviceList,
                                                            Map<String, String> custIdMap, Map<String, String> taxMap)  throws Exception {
        List<Alloc> ListTax = new ArrayList<>();

        for (Alloc allocList : serviceList) {
            Alloc alloc =allocList.clone();
            // 生成商业服务费流水号
            alloc.setTollNo(GuidAgent.getStringGuid(Constants.NO_SERVICE_FEE32 + PsTools.getInstanceNo()));
            alloc.setRelPvNo(alloc.getTollNo());
            // 取客户号
            alloc.setCustId(custIdMap.get(alloc.getHsResNo()));
            if (!CollectionUtils.isEmpty(taxMap)) {
                // 取税率
                String objectTaxRate = Objects.toString(taxMap.get(alloc.getHsResNo()), "");
                if (StringUtils.isEmpty(objectTaxRate)) {
                    alloc.setTaxRate(BigDecimal.ZERO);
                } else {
                    alloc.setTaxRate(new BigDecimal(objectTaxRate));
                }
                // 计算服务公司商业服务费税收
                BigDecimal serviceFee = AllocItem.getSumSubServiceMonthFee(alloc.getAddAmount(),
                        alloc.getTaxRate());
                alloc.setTaxAddAmount(serviceFee);
            } else {
                alloc.setTaxAddAmount(alloc.getAddAmount());
            }
            alloc.setTaxNo(GuidAgent.getStringGuid(Constants.NO_POINT_TAX22 + PsTools.getInstanceNo()));
            alloc.setCscType(CustType.SERVICE_CORP.getCode());
            alloc.setTaxAccType(AccountType.ACC_TYPE_POINT20610.getCode());
            if (alloc.getTaxAddAmount().compareTo(BigDecimal.ZERO) != 0) {
                ListTax.add(alloc);
            }
        }
        return ListTax;
    }

    /**
     * 组装积分税后汇总(文件数据信息)
     *
     * @param list 积分汇总信息
     * @return
     */
    public static StringBuffer getPointListData(List<Alloc> list) throws Exception{
        StringBuffer dataBuf = new StringBuffer();
        if (list.size() > 0) {
            for (Alloc alloc : list) {
                dataBuf.append(alloc.getCustId().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getHsResNo().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getCustType().toString());
                dataBuf.append("|");
                dataBuf.append(alloc.getBatchNo().toString().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getAccType().toString().trim());
                dataBuf.append("|");
                dataBuf.append(String.valueOf(alloc.getAddAmount()));
                dataBuf.append("|");
                dataBuf.append(alloc.getSubAmount() != null ? Math.abs(alloc.getSubAmount().doubleValue()) : null);
                dataBuf.append("|");
                dataBuf.append("0");
                dataBuf.append("|");
                dataBuf.append("PS");
                dataBuf.append("|");
                dataBuf.append(Constants.HSB_BUSINESS_POINT);
                dataBuf.append("|");
                dataBuf.append(alloc.getPvNo().trim());
                dataBuf.append("|");
                dataBuf.append(PsTools.getDateTime());
                dataBuf.append("|");
                dataBuf.append(PsTools.setDateFormat(alloc.getBatchNo()));

                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("消费积分汇总结算");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append(System.getProperty("line.separator"));
            }
            dataBuf.append("END");
        }
        return dataBuf;
    }

    /**
     * 组装城市税收(文件数据信息)
     *
     * @param list 积分税收信息
     * @return
     */
    public static StringBuffer getTaxListData(List<Alloc> list) throws Exception{
        StringBuffer dataBuf = new StringBuffer();
        if (list.size() > 0) {
            for (Alloc alloc : list) {

                dataBuf.append(alloc.getCustId().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getHsResNo().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getCustType().toString());
                dataBuf.append("|");
                dataBuf.append(alloc.getBatchNo().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getTaxAccType().trim());
                dataBuf.append("|");
                dataBuf.append(String.valueOf(alloc.getTaxAddAmount()));
                dataBuf.append("|");
                //  dataBuf.append(String.valueOf(alloc.getTaxSubAmount()));
                dataBuf.append(alloc.getTaxSubAmount() != null ? Math.abs(alloc.getTaxSubAmount().doubleValue()) : null);
                dataBuf.append("|");
                dataBuf.append("0");
                dataBuf.append("|");
                dataBuf.append("PS");
                dataBuf.append("|");
                dataBuf.append(Constants.HSB_BUSINESS_POINT_TAX);
                dataBuf.append("|");
                dataBuf.append(alloc.getTaxNo().trim());
                dataBuf.append("|");
                dataBuf.append(PsTools.getDateTime());
                dataBuf.append("|");
                dataBuf.append(PsTools.setDateFormat(alloc.getBatchNo()));

                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("消费积分税收结算");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append(System.getProperty("line.separator"));
            }
            dataBuf.append("END");
        }
        return dataBuf;
    }

    /**
     * 组装互生币减商业服务费后汇总(文件数据信息)
     *
     * @param list 互生币汇总信息
     * @return
     */
    public static StringBuffer getHsbListData(List<Map<String, String>> list) throws Exception{
        StringBuffer dataBuf = new StringBuffer();
        if (list.size() > 0) {
            for (Map<String, String> map : list) {
                dataBuf.append(map.get("custId"));
                dataBuf.append("|");
                dataBuf.append(map.get("hsResNo"));
                dataBuf.append("|");
                dataBuf.append(map.get("custType"));
                dataBuf.append("|");
                dataBuf.append(map.get("batchNo"));
                dataBuf.append("|");
                dataBuf.append(map.get("accType"));
                dataBuf.append("|");
                dataBuf.append(map.get("addAmount"));
                dataBuf.append("|");
                dataBuf.append(map.get("subAmount"));
                dataBuf.append("|");
                dataBuf.append(map.get("writeBack"));
                dataBuf.append("|");
                dataBuf.append("PS");
                dataBuf.append("|");
                dataBuf.append(map.get("transType"));
                dataBuf.append("|");
                dataBuf.append(map.get("hsbSumNo"));
                dataBuf.append("|");
                dataBuf.append(PsTools.getDateTime());
                dataBuf.append("|");
                dataBuf.append(PsTools.setDateFormat(map.get("batchNo")));

                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("互生币汇总结算");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");

                dataBuf.append(System.getProperty("line.separator"));
            }
            dataBuf.append("END");
            dataBuf.append("\n");
        }
        return dataBuf;
    }

    /**
     * 组装商业服务费(文件数据信息)
     *
     * @param list
     * @return
     * @throws UnsupportedEncodingException
     */
    public static StringBuffer getCscListData(List<Alloc> list) throws Exception{
        StringBuffer dataBuf = new StringBuffer();

        if (list.size() > 0) {
            for (Alloc alloc : list) {
                dataBuf.append(alloc.getCustId());
                dataBuf.append("|");
                dataBuf.append(alloc.getHsResNo().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getCustType());
                dataBuf.append("|");
                dataBuf.append(alloc.getBatchNo().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getTollAccType().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getTollAddAmount());
                dataBuf.append("|");
                //dataBuf.append(alloc.getSubAmount());
                dataBuf.append(alloc.getSubAmount() != null ? Math.abs(alloc.getSubAmount().doubleValue()) : null);
                dataBuf.append("|");
                dataBuf.append(alloc.getWriteBack().trim());
                dataBuf.append("|");
                dataBuf.append("PS");
                dataBuf.append("|");
                dataBuf.append(alloc.getTransType().trim());
                dataBuf.append("|");
                dataBuf.append(alloc.getTollNo().trim());
                dataBuf.append("|");
                dataBuf.append(PsTools.getDateTime());
                dataBuf.append("|");
                dataBuf.append(PsTools.setDateFormat(alloc.getBatchNo()));

                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("商业服务费结算");
                dataBuf.append("|");
                dataBuf.append("null");
                dataBuf.append("|");
                dataBuf.append("null");

                dataBuf.append(System.getProperty("line.separator"));
            }
            dataBuf.append("END");
        }
        return dataBuf;
    }

    /**
     * 结算后通过文件反馈结果给电商(文件数据信息)
     *
     * @param list
     * @param succeed
     * @param fail
     */
    public static StringBuffer feedbackData(List<Map<String, String>> list, int succeed, int fail)throws Exception {
        StringBuffer sbData = new StringBuffer();
        sbData.append(String.valueOf(succeed));
        sbData.append("|");
        sbData.append(String.valueOf(fail));
        sbData.append(System.getProperty("line.separator"));
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);
                sbData.append(map.get("sourceTransNo"));
                sbData.append("|");
                sbData.append(map.get("reason"));
                sbData.append(System.getProperty("line.separator"));
            }
        }
        sbData.append("END");
        return sbData;
    }

    /**
     * 生成check文件(文件数据信息)
     *
     * @param fileName
     * @param list
     * @return
     */
    public static StringBuffer checkFileData(String fileName, List<?> list) throws Exception{
        StringBuffer sbData = new StringBuffer();
        sbData.append(fileName);
        sbData.append("|");
        sbData.append(list.size());
        sbData.append(System.getProperty("line.separator"));
        sbData.append("END");
        return sbData;
    }

    /**
     * 生成积分税收数据文件
     *
     * @param list  积分税收信息
     * @param count 文件类型(TAX开头)
     * @param count 文件编号
     */
    public static void generationFile(List<Alloc> list, int count, String dateStr, String settlement) throws Exception{
        String data = "";
        // String checkFileName = PsTools.getBeforeDayDirectory() + "_CHECK";
        String runDate = DateUtil
                .DateToString(DateUtil.StringToDate(dateStr), DateUtil.DATE_FORMAT);
        String checkFileName = runDate + "_CHECK";
        String directory = settlement + File.separator + runDate;
        Map<String, String> map = getFileInfo(directory, checkFileName);
        String dirPath = map.get("dirPath");
        String countOld = map.get("count");
        String fileContxt = map.get("fileContxt");
        if (StringUtils.isNotEmpty(countOld)) {
            count = count + Integer.parseInt(countOld);
        }
        String fileName = runDate + "_" + count;
        // data = GenerateFile.taxColumn() +
        // AllocHandle.getTaxListData(list).toString().trim();
        if (settlement.equals(Constants.SETTLEMENT_TAX)) {
            data = AllocHandle.getTaxListData(list).toString().trim();
        }
        if (settlement.equals(Constants.SETTLEMENT_CSC)) {
            data = AllocHandle.getCscListData(list).toString().trim();
        }
        if (settlement.equals(Constants.SETTLEMENT_POINT)) {
            data = AllocHandle.getPointListData(list).toString().trim();
        }
        writeFile(fileName.toUpperCase(), dirPath, data);
        String recordFileName = fileName + ".txt";
        String size = AllocHandle.checkFileData(recordFileName.toUpperCase(), list).toString()
                .trim();
        if (StringUtils.isNotEmpty(fileContxt)) {
            size = fileContxt + System.getProperty("line.separator") + size;
        }
        writeFile(checkFileName, dirPath, size);
    }

    /**
     * 生成互生币分配数据文件
     *
     * @param list  互生币汇总信息
     * @param count 文件类型(HSB开头)
     * @param count 文件编号
     */
    public static void generationHsbFile(List<Map<String, String>> list, String dateStr, int count) throws Exception{
        String data = "";
        // String checkFileName = PsTools.getBeforeDayDirectory() + "_CHECK";
        String runDate = DateUtil
                .DateToString(DateUtil.StringToDate(dateStr), DateUtil.DATE_FORMAT);
        String checkFileName = runDate + "_CHECK";
        String directory = Constants.SETTLEMENT_HSB + File.separator + runDate;
        Map<String, String> map = getFileInfo(directory, checkFileName);
        String dirPath = map.get("dirPath");
        SystemLog.debug("dirPath====================================================================================","generationHsbFile",dirPath);
        String countOld = map.get("count");
        SystemLog.debug("countOld====================================================================================","generationHsbFile",countOld);
        String fileContxt = map.get("fileContxt");
        if (StringUtils.isNotEmpty(countOld)) {
            count = count + Integer.parseInt(countOld);
        }

        String fileName = runDate + "_" + count;
        // data = GenerateFile.hsbColumn() +
        // AllocHandle.getHsbListData(list).toString().trim();
        data = AllocHandle.getHsbListData(list).toString().trim();

        writeFile(fileName.toUpperCase(), dirPath, data);
        // String checkFileName = typeName + "_" + PsTools.getBeforeDay() +
        // "CHECK";

        String recordFileName = fileName + ".txt";
        String size = AllocHandle.checkFileData(recordFileName.toUpperCase(), list).toString()
                .trim();
        if (StringUtils.isNotEmpty(fileContxt)) {
            size = fileContxt + System.getProperty("line.separator") + size;
        }
        writeFile(checkFileName, dirPath, size);
    }

    /**
     * 创建并写入文件
     *
     * @param fileName 文件名
     * @param data     数据
     */
    public static void writeFile(String fileName, String dirPath, String data) throws Exception{
        try {
            // 创建文件
            GenerateFile.creatTxtFile(fileName, dirPath);
            // 写入文件
            GenerateFile.writeTxtFile(data);
        } catch (IOException e) {
            throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
        }
    }

    /**
     * 生成电商结单的回馈文件
     *
     * @param list     电商批结算信息
     * @param typeName 文件类型(EC开头)
     * @param succeed  成功记录数
     * @param fail     失败记录数
     */
    public static void generationEcFile(List<Map<String, String>> list, int succeed, int fail,
                                        String typeName) throws Exception{
        String data = "";
        String fileName = typeName + "_" + DateUtil.DateToString(DateUtil.today());

        // 结算后通过文件反馈结果给电商(文件数据信息)
        data = AllocHandle.feedbackData(list, succeed, fail).toString().trim();

        // 创建并写入文件
        writeFile(fileName, Constants.SETTLEMENT_EC, data);
    }

    /**
     * list中数据写入文件(积分、税后、商业服务费结算文件, 默认每10万生成1个文件)
     *
     * @param list     分配数据信息
     * @param typeName 文件名
     */
    public static void writeFile(List<Alloc> list, String runDate, String typeName) throws Exception{
        // 计数
        int count = 0;
        // 文件编号
        int index = 0;
        List<Alloc> fileList = new ArrayList<Alloc>();
        for (int i = 0; i < list.size(); i++) {
            fileList.add(list.get(i));
            count++;
            if (count == Constants.DATA_COUNT) {
                index++;
                if (typeName.equals(Constants.SETTLEMENT_POINT)) {
                    // 结余为0的不写文件
                    List<Alloc> deletefileList = new ArrayList<>();
                    for (Alloc alloc : fileList) {
                        if (alloc.getSubAmount() == null && alloc.getAddAmount() == null) {
                            deletefileList.add(alloc);
                        } else {
                            if (alloc.getAddAmount() == null) {
                                if (alloc.getSubAmount().compareTo(BigDecimal.ZERO) == 0) {
                                    deletefileList.add(alloc);
                                }
                            }
                            if (alloc.getSubAmount() == null) {
                                if (alloc.getAddAmount().compareTo(BigDecimal.ZERO) == 0) {
                                    deletefileList.add(alloc);
                                }
                            }
                        }
                    }
                    fileList.removeAll(deletefileList);
                    // 结余为0的不写文件结束

                    // 生成积分分配数据文件
                    generationFile(fileList, index, runDate, Constants.SETTLEMENT_POINT);

                } else if (typeName.equals(Constants.SETTLEMENT_TAX)) {
                    // 生成积分税收数据文件
                    generationFile(fileList, index, runDate, Constants.SETTLEMENT_TAX);

                } else if (typeName.equals(Constants.SETTLEMENT_CSC)) {
                    // 生成企业互生币商业服务费数据文件
                    generationFile(fileList, index, runDate, Constants.SETTLEMENT_CSC);
                }
                // 清空已写入的list信息
                fileList.clear();
                // 重新记数
                count = 0;
            }
        }
        if (count > 0) {
            index += 1;
            if (typeName.equals(Constants.SETTLEMENT_POINT)) {
                // 结余为0的不写文件
                List<Alloc> deletefileList = new ArrayList<Alloc>();
                for (Alloc alloc : fileList) {
                    if (alloc.getSubAmount() == null && alloc.getAddAmount() == null) {
                        deletefileList.add(alloc);
                    } else {
                        if (alloc.getAddAmount() == null) {
                            if (alloc.getSubAmount().compareTo(BigDecimal.ZERO) == 0) {
                                deletefileList.add(alloc);
                            }
                        }
                        if (alloc.getSubAmount() == null) {
                            if (alloc.getAddAmount().compareTo(BigDecimal.ZERO) == 0) {
                                deletefileList.add(alloc);
                            }
                        }
                    }
                }
                fileList.removeAll(deletefileList);
                // 结余为0的不写文件结束

                // 生成积分分配数据文件
                generationFile(fileList, index, runDate, Constants.SETTLEMENT_POINT);

            } else if (typeName.equals(Constants.SETTLEMENT_TAX)) {
                // 生成积分税收数据文件
                generationFile(fileList, index, runDate, Constants.SETTLEMENT_TAX);

            } else if (typeName.equals(Constants.SETTLEMENT_CSC)) {
                // 生成企业互生币商业服务费数据文件
                generationFile(fileList, index, runDate, Constants.SETTLEMENT_CSC);
            }
        }
    }

    /**
     * list中数据写入文件(互生币结算,默认每10万生成1个文件)
     *
     * @param list     互生币结算信息
     * @param typeName 文件名
     */
    public static void writeHsbFile(List<Map<String, String>> list, String runDate, String typeName)throws Exception {
        int index = 0;
        int count = 0;
        List<Map<String, String>> fileList = new ArrayList<>();
        for (Map<String, String> aList : list) {
            fileList.add(aList);
            count++;
            if (count == Constants.DATA_COUNT) {
                index++;
                // 生成互生币分配数据文件
                generationHsbFile(fileList, runDate, index);
                fileList.clear();
                count = 0;
            }
        }
        if (count > 0) {
            index += 1;
            // 生成互生币分配数据文件
            generationHsbFile(fileList, runDate, index);
        }
    }

    /**
     * 获取互生号
     *
     * @param allocList
     * @return
     */
    public static List<String> getHsResNo(List<Alloc> allocList) throws Exception{
        List<String> list = new ArrayList<>();
        for (Alloc alloc : allocList) {
            list.add(StringUtils.trim(alloc.getHsResNo()));
        }
        return list;
    }

    public static Map<String, String> getFileInfo(String directory, String fileName) throws HsException{
        Map<String, String> map = new HashMap<>();
        String fileContxt;
        String dirPath = path + File.separator + Constants.SETTLEMENT_PS + File.separator
                + directory + File.separator;
        map.put("dirPath", dirPath);
        try {
            File file = new File(dirPath + File.separator + fileName + ".TXT");
            if (file.exists()) {
                fileContxt = FileUtils.readFileToString(file);
                String[] s = fileContxt.split(IOUtils.LINE_SEPARATOR);
                map.put("count", String.valueOf(s.length - 1));
                if (StringUtils.isNotEmpty(fileContxt)) {
                    fileContxt = fileContxt.replace(IOUtils.LINE_SEPARATOR + "END", "");
                    map.put("fileContxt", fileContxt);
                }
            }
        } catch (IOException e) {
            throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), e.toString());
        }
        return map;
    }

}