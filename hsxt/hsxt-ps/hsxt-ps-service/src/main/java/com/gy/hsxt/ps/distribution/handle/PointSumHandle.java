/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.;LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import java.util.List;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointAllot;

/**
 * @Package: com.gy.hsxt.ps.distribution.bean
 * @ClassName: PointHandle
 * @Description: 积分分配处理
 * 
 * @author: chenhz
 * @date: 2016-2-24 下午2:05:55
 * @version V3.0
 */

public class PointSumHandle
{
	/**
	 * 积分分配数据处理
	 * 
	 * @param pointList
	 * @return
	 */
	public static PointAllot pointAllotData(List<Alloc> pointList, int businessType)
	{
		PointAllot pointAllot = new PointAllot();

		for (int i = 0; i < pointList.size(); i++)
		{
			Alloc alloc = pointList.get(i);

			// 基本信息
			PointSumHandle.basicInfo(pointAllot, alloc);

			// 撤单、退货资金信息
			if (businessType == Constants.BUSINESS_TYPE0)
			{
				PointSumHandle.enterpriseAddAmount(pointAllot, alloc);

			} else if (businessType == Constants.BUSINESS_TYPE1
					|| businessType == Constants.BUSINESS_TYPE2)
			{
				PointSumHandle.enterpriseSubAmount(pointAllot, alloc);
			}
		}
		return pointAllot;
	}

	/**
	 * 积分分配基本信息
	 * 
	 * @param pointAllot
	 * @param alloc
	 */
	public static void basicInfo(PointAllot pointAllot, Alloc alloc)
	{
		pointAllot.setPerHsNo(alloc.getPerResNo());
		pointAllot.setAllotNo(alloc.getAllocNo());
		pointAllot.setBatchNo(alloc.getBatchNo());
		pointAllot.setRelTransNo(alloc.getRelTransNo());
		pointAllot.setSourceTransNo(alloc.getSourceTransNo());
		pointAllot.setTransType(alloc.getTransType());
		pointAllot.setWriteBack(alloc.getWriteBack());
		pointAllot.setIsSettle(alloc.getIsSettle());
		pointAllot.setSourceTransDate(alloc.getSourceTransDate());
	}

	/**
	 * 企业收入积分资金信息(积分、支付)
	 * 
	 * @param pointAllot
	 * @param alloc
	 */
	public static void enterpriseAddAmount(PointAllot pointAllot, Alloc alloc)
	{
		String hsNo = alloc.getHsResNo();
		if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo))
		{
			pointAllot.setTrusteeEntHsNo(hsNo);
			pointAllot.setTrusteeAddPoint(alloc.getAddAmount());

		} else if (CustType.SERVICE_CORP.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo))
		{
			pointAllot.setServiceEntHsNo(hsNo);
			pointAllot.setServiceAddPoint(alloc.getAddAmount());

		} else if (CustType.MANAGE_CORP.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo))
		{
			pointAllot.setManageEntHsNo(hsNo);
			pointAllot.setManageAddPoint(alloc.getAddAmount());

		} else if (CustType.AREA_PLAT.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo)
				&& alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode()))
		{
			pointAllot.setPaasEntHsNo(hsNo);
			pointAllot.setPaasAddPoint(alloc.getAddAmount());

		} else if (CustType.AREA_PLAT.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo)
				&& alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10220.getCode()))
		{
			pointAllot.setSurplusAddPoint(alloc.getAddAmount());
		}
	}

	/**
	 * 企业支出积分资金信息(退货、撤单)
	 * 
	 * @param pointAllot
	 * @param alloc
	 */
	public static void enterpriseSubAmount(PointAllot pointAllot, Alloc alloc)
	{
		String hsNo = alloc.getHsResNo();
		if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo))
		{
			pointAllot.setTrusteeEntHsNo(hsNo);
			pointAllot.setTrusteeSubPoint(alloc.getAddAmount());

		} else if (CustType.SERVICE_CORP.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo))
		{
			pointAllot.setServiceEntHsNo(hsNo);
			pointAllot.setServiceSubPoint(alloc.getAddAmount());

		} else if (CustType.MANAGE_CORP.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo))
		{
			pointAllot.setManageEntHsNo(hsNo);
			pointAllot.setManageSubPoint(alloc.getAddAmount());

		} else if (CustType.AREA_PLAT.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo)
				&& alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode()))
		{
			pointAllot.setPaasEntHsNo(hsNo);
			pointAllot.setPaasSubPoint(alloc.getAddAmount());

		} else if (CustType.AREA_PLAT.getCode() == HsResNoUtils.getCustTypeByHsResNo(hsNo)
				&& alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10220.getCode()))
		{
			pointAllot.setSurplusSubPoint(alloc.getAddAmount());
		}
	}

}
