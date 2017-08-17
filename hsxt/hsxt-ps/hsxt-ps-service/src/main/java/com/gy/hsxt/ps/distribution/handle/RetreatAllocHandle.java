/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.EntryAllot;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: RetreatAllocHandle
 * @Description: 撤单、退货、冲正金额处理
 * @author: chenhongzhi
 * @date: 2015-12-3 上午10:51:29
 */
public class RetreatAllocHandle {

    /**
     * 当日全部退货、撤单、冲正
     *
     * @param entryList pos分录list信息
     * @return 返回处理后的分录list
     */
    public void  retreatAlloc(List<Alloc> entryList) {

        // 增向金额、减向金额互换处理(退分录)
        this.retreatEntry(entryList);


    }

    /**
     * 隔日全部退货、撤单、冲正
     *
     * @param entryList 积分分配信息
     * @param pointList pos分录信息
     * @return
     */
    public List<Alloc> retreatAlloc(List<Alloc> entryList, List<Alloc> pointList) {
        // 增向金额、减向金额互换处理(退分录明细)
        this.retreatEntry(entryList);

        // 增向金额、减向金额互换处理(退积分明细)
        this.retreatPoint(pointList);

        // 合并list
        entryList.addAll(pointList);

        // 返回
        return entryList;
    }

    /**
     * 隔日冲正
     *
     * @param point
     * @param entry
     * @return
     */
//	public List<Alloc> reversalAlloc(List<Alloc> entryList, List<Alloc> pointList)
//	{
//		// 
//		this.retreatPoint(pointList);
//		entryList.addAll(pointList);
//		return entryList;
//	}

    /**
     * 当日撤单,退货金额处理(退分录)
     *
     * @param entryList 分录list信息
     * @return 返回处理后的分录list
     */
    public void  retreatEntry(List<Alloc> entryList) {
        Iterator<Alloc> listIterator = entryList.iterator();
        while (listIterator.hasNext()) {
            Alloc alloc = listIterator.next();
            // 增向、减向金额互换
            BigDecimal addAmount = alloc.getAddAmount();
            BigDecimal subAmount = alloc.getSubAmount();
            alloc.setSubAmount(addAmount);
            alloc.setAddAmount(subAmount);
        }

    }

    /**
     * 隔日撤单,退货金额处理(退积分)
     *
     * @param pointList 积分list信息
     * @return 返回处理后的积分list信息
     */
    public List<Alloc> retreatPoint(List<Alloc> pointList) {
        for (int i = 0; i < pointList.size(); i++) {
            // 循环遍历积分分配信息
            Alloc alloc = pointList.get(i);

            // 设置分录流水号
            alloc.setEntryNo(alloc.getAllocNo());

            // 根据互生号解析客户类型
//			int hsResNo = PsTools.parsHsNo(alloc.getHsResNo());
//			
//			if (hsResNo == Constants.HS_TRUSTEE || (hsResNo == Constants.HS_SERVICE)
//					|| (hsResNo == Constants.HS_MANAGE) || (hsResNo == Constants.HS_PAAS))
//			{
            // 获取增向金额
            BigDecimal addAmount = alloc.getAddAmount();

            // 设置增向金额
            alloc.setSubAmount(addAmount);
//			}
        }
        // 返回积分分配明细list
        return pointList;
    }


    /**
     * 积分分路拆分
     *
     * @param entryAllot
     * @return
     */
    public static List<Alloc> pointAllotSplit(EntryAllot entryAllot) {
        List<Alloc> listAlloc = new ArrayList<>();

                if (entryAllot != null) {
                    if (StringUtils.isNotEmpty(entryAllot.getEntHsNo()) && StringUtils.isNotEmpty(entryAllot.getPerHsNo())) {
                        /** 企业货款 */
                        if (entryAllot.getEntAddAmount() != null || entryAllot.getEntSubAmount() != null) {
                            Alloc allocEntAmount = new Alloc();
                            allocEntAmount.setCustId(entryAllot.getEntCustId());
                            allocEntAmount.setEntryNo(entryAllot.getEntryNo());
                            allocEntAmount.setHsResNo(entryAllot.getEntHsNo());
                            allocEntAmount.setAddAmount(entryAllot.getEntAddAmount());
                            allocEntAmount.setSubAmount(entryAllot.getEntSubAmount());
                            allocEntAmount.setIsSettle(entryAllot.getIsSettle());
                            allocEntAmount.setCustType(HsResNoUtils.getCustTypeByHsResNo(entryAllot.getEntHsNo()));
                            allocEntAmount.setBatchNo(entryAllot.getBatchNo());
                            allocEntAmount.setAccType(AccountType.ACC_TYPE_POINT20111.getCode());
                            allocEntAmount.setRelTransNo(entryAllot.getRelTransNo());
                            allocEntAmount.setSourceTransNo(entryAllot.getSourceTransNo());
                            allocEntAmount.setTransNo(entryAllot.getEntryNo());
                            listAlloc.add(allocEntAmount);
                        }

                        /** 企业货款积分*/
                        if (entryAllot.getEntAddPointAmount() != null || entryAllot.getEntSubPointAmount() != null) {
                            Alloc allocEntPoint = new Alloc();
                            allocEntPoint.setCustId(entryAllot.getEntCustId());
                            allocEntPoint.setEntryNo(entryAllot.getEntryNo());
                            allocEntPoint.setHsResNo(entryAllot.getEntHsNo());
                            allocEntPoint.setAddAmount(entryAllot.getEntAddPointAmount());
                            allocEntPoint.setSubAmount(entryAllot.getEntSubPointAmount());
                            allocEntPoint.setIsSettle(entryAllot.getIsSettle());
                            allocEntPoint.setCustType(HsResNoUtils.getCustTypeByHsResNo(entryAllot.getEntHsNo()));
                            allocEntPoint.setBatchNo(entryAllot.getBatchNo());
                            allocEntPoint.setAccType(AccountType.ACC_TYPE_POINT20110.getCode());
                            allocEntPoint.setRelTransNo(entryAllot.getRelTransNo());
                            allocEntPoint.setSourceTransNo(entryAllot.getSourceTransNo());
                            allocEntPoint.setTransNo(entryAllot.getEntryNo());
                            listAlloc.add(allocEntPoint);
                        }

                        /** 个人货款 */
                        if (entryAllot.getPerAddAmount() != null || entryAllot.getPerSubAmount() != null) {
                            Alloc allocPerAmount = new Alloc();
                            allocPerAmount.setCustId(entryAllot.getPerCustId());
                            allocPerAmount.setEntryNo(entryAllot.getEntryNo());
                            allocPerAmount.setHsResNo(entryAllot.getPerHsNo());
                            allocPerAmount.setAddAmount(entryAllot.getPerAddAmount());
                            allocPerAmount.setSubAmount(entryAllot.getPerSubAmount());
                            allocPerAmount.setIsSettle(entryAllot.getIsSettle());
                            allocPerAmount.setCustType(HsResNoUtils.getCustTypeByHsResNo(entryAllot.getPerHsNo()));
                            allocPerAmount.setBatchNo(entryAllot.getBatchNo());
                            allocPerAmount.setAccType(AccountType.ACC_TYPE_POINT20110.getCode());
                            allocPerAmount.setRelTransNo(entryAllot.getRelTransNo());
                            allocPerAmount.setSourceTransNo(entryAllot.getSourceTransNo());
                            allocPerAmount.setTransNo(entryAllot.getEntryNo());
                            listAlloc.add(allocPerAmount);
                        }

                        /** 个人 积分*/
                        if (entryAllot.getPerAddPointAmount() != null || entryAllot.getPerSubPointAmount() != null) {
                            Alloc allocPerPoint = new Alloc();
                            allocPerPoint.setCustId(entryAllot.getPerCustId());
                            allocPerPoint.setEntryNo(entryAllot.getEntryNo());
                            allocPerPoint.setHsResNo(entryAllot.getPerHsNo());
                            allocPerPoint.setAddAmount(entryAllot.getPerAddPointAmount());
                            allocPerPoint.setSubAmount(entryAllot.getPerSubPointAmount());
                            allocPerPoint.setIsSettle(entryAllot.getIsSettle());
                            allocPerPoint.setCustType(HsResNoUtils.getCustTypeByHsResNo(entryAllot.getPerHsNo()));
                            allocPerPoint.setBatchNo(entryAllot.getBatchNo());
                            allocPerPoint.setAccType(AccountType.ACC_TYPE_POINT10110.getCode());
                            allocPerPoint.setRelTransNo(entryAllot.getRelTransNo());
                            allocPerPoint.setSourceTransNo(entryAllot.getSourceTransNo());
                            allocPerPoint.setTransNo(entryAllot.getEntryNo());
                            listAlloc.add(allocPerPoint);
                        }
                    }
                }
        return listAlloc;
    }

}
