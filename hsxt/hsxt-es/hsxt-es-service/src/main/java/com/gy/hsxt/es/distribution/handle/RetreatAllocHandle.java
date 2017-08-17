/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.distribution.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsTools;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.EntryAllot;
import org.apache.commons.lang3.StringUtils;

/**
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: RetreatAllocHandle
 * @Description: 撤单、退货、冲正金额处理
 * 
 * @author: chenhongzhi
 * @date: 2015-12-3 上午10:51:29
 * @version V3.0
 */
public class RetreatAllocHandle
{
	
	/**
	 * 当日退货、撤单
	 * @param entryList
	 * @return
	 */
	public List<Alloc> retreatAlloc(List<Alloc> entryList){
		this.retreatEntry(entryList);
		return entryList;
	}

	/**
	 * 隔日撤单、退货
	 * 
	 * @param entryList
	 * @param pointList
	 * @return
	 */
	public List<Alloc> retreatAlloc(List<Alloc> entryList, List<Alloc> pointList)
	{
		this.retreatEntry(entryList);
		this.retreatPoint(pointList);
		entryList.addAll(pointList);
		return entryList;
	}

	/**
	 * 隔日冲正
	 * 
	 * @param entryList
	 * @param pointList
	 * @return
	 */
	public List<Alloc> reversalAlloc(List<Alloc> entryList, List<Alloc> pointList)
	{
		this.retreatPoint(pointList);
		entryList.addAll(pointList);
		return entryList;
	}

	/**
	 * 隔日撤,退积分
	 *
	 * @param pointList
	 * @return
	 */
	public List<Alloc> retreatPoint(List<Alloc> pointList)
	{
		for (int i = 0; i < pointList.size(); i++)
		{
			Alloc alloc = pointList.get(i);
			alloc.setEntryNo(alloc.getAllocNo());
			if (EsTools.parsHsNo(alloc.getHsResNo()).equals(Constants.HS_TRUSTEE)
					|| EsTools.parsHsNo(alloc.getHsResNo()).equals(Constants.HS_SERVICE)
					|| EsTools.parsHsNo(alloc.getHsResNo()).equals(Constants.HS_MANAGE)
					|| EsTools.parsHsNo(alloc.getHsResNo()).equals(Constants.HS_PAAS))
			{
				BigDecimal addAmount = alloc.getAddAmount();
				// BigDecimal subAmount = alloc.getSubAmount();
				alloc.setSubAmount(addAmount);
				// alloc.setAddAmount(subAmount);
			}
		}
		return pointList;
	}

	/**
	 * 隔日撤,退分录
	 *
	 * @param entryList
	 * @return
	 */
	public List<Alloc> retreatEntry(List<Alloc> entryList)
	{
		Iterator<Alloc> listIterator = entryList.iterator();
		while (listIterator.hasNext())
		{
			Alloc alloc =listIterator.next();
			BigDecimal addAmount = alloc.getAddAmount();
			BigDecimal subAmount = alloc.getSubAmount();
			alloc.setSubAmount(addAmount);
			alloc.setAddAmount(subAmount);
		}
		return entryList;
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
			if (StringUtils.isNotEmpty(entryAllot.getEntCustId()) && StringUtils.isNotEmpty(entryAllot.getPerCustId())) {
				/** 企业货款 */
				if (entryAllot.getEntAddAmount() != null || entryAllot.getEntSubAmount() != null) {
					Alloc allocEntAmount = new Alloc();
					allocEntAmount.setCustId(entryAllot.getEntCustId());
					allocEntAmount.setEntryNo(entryAllot.getEntryNo());
					allocEntAmount.setHsResNo(entryAllot.getEntHsNo());
					allocEntAmount.setAddAmount(entryAllot.getEntAddAmount());
					allocEntAmount.setSubAmount(entryAllot.getEntSubAmount());
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
					//allocEntPoint.setIsSettle(entryAllot.getIsSettle());
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
					//allocPerAmount.setIsSettle(entryAllot.getIsSettle());
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
					//allocPerPoint.setIsSettle(entryAllot.getIsSettle());
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
