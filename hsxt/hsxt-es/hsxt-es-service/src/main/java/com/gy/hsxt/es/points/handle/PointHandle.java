/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.points.handle;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.es.bean.*;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsTools;
import com.gy.hsxt.es.common.TransTypeUtil;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.EntryAllot;
import com.gy.hsxt.es.distribution.bean.PointAllot;
import com.gy.hsxt.es.points.bean.BackDetail;
import com.gy.hsxt.es.points.bean.CancellationDetail;
import com.gy.hsxt.es.points.bean.CorrectDetail;
import com.gy.hsxt.es.points.bean.PointDetail;

/**
 * @Package: com.gy.hsxt.ps.points.handle
 * @ClassName: PointHandle
 * @Description: 订单处理
 * 
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 * @version V3.0
 */
public class PointHandle
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
			PointHandle.basicInfo(pointAllot, alloc);

			// 撤单、退货资金信息
			if (businessType == Constants.BUSINESS_TYPE0)
			{
				PointHandle.enterpriseAddAmount(pointAllot, alloc);

			} else if (businessType == Constants.BUSINESS_TYPE1
					|| businessType == Constants.BUSINESS_TYPE2)
			{
				PointHandle.enterpriseSubAmount(pointAllot, alloc);
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
		//pointAllot.setIsSettle(alloc.getIsSettle());
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
		pointAllot.setIsSettle(Constants.IS_SETTLE1);
	}
	/**
	 * 积分明细处理
	 * 
	 * @param pointDetail
	 *            积分明细对象信息
	 * @param point
	 *            pos入参积分对象信息
	 * @return 返回积分明细对象
	 */
	public PointDetail pointDispose(PointDetail pointDetail, Point point)
	{
		// 交易状态
		String transStatus = TransTypeUtil.transStatus(point.getTransType());
		// 设置订单状态
		pointDetail.setTransStatus(Integer.parseInt(transStatus));

		// 设置原交易流水号
		pointDetail.setSourceTransNo(point.getSourceTransNo());

		// 生成流水号
		if (transStatus.equals(Constants.POINT_BUSS_TYPE3)
				|| transStatus.equals(Constants.POINT_BUSS_TYPE0))
		{
			pointDetail.setTransNo(EsTools.GUID(Constants.TRANS_NO_POINT10));

		} else if (transStatus.equals(Constants.POINT_BUSS_TYPE4))
		{
			pointDetail.setTransNo(point.getOldTransNo());
		}

		// 批次号
		pointDetail.setBatchNo(DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));

		// 设置交易金额
		pointDetail.setTransAmount(new BigDecimal(point.getTransAmount()));

		// 设置原始币种金额
		pointDetail.setSourceTransAmount(new BigDecimal(point.getSourceTransAmount()));

		// 设置货币转换率
		pointDetail.setCurrencyRate(new BigDecimal(Constants.CURRENCY_RATE));

		// 设置积分比例
		if (!EsTools.isEmpty(point.getPointRate()))
		{
			pointDetail.setPointRate(new BigDecimal(point.getPointRate()));
		}

		// 积分应付款
		if (!EsTools.isEmpty(point.getEntPoint()))
		{
			pointDetail.setEntPoint(new BigDecimal(point.getEntPoint()));
		}

		// 设置原始交易时间
		pointDetail.setSourceTransDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

		// 设置为不是网上积分登记
		pointDetail.setIsOnlineRegister(Constants.IS_ONLINE_REGISTER1);

		// 设置业务状态
		pointDetail.setStatus(Constants.SUCCESS);
		pointDetail.setIsSettle(Constants.IS_SETTLE1);
		 // 设置原始币种金额转换后金额
        pointDetail.setTargetTransAmount(new BigDecimal(point.getTransAmount()));

		return pointDetail;
	}

	/**
	 * 积分返回处理
	 * 
	 * @param pointResult
	 *            积分返回对象信息
	 * @param pointDetail
	 *            积分明细对象信息
	 * @return 返回积分返回对象
	 */
	public PointResult pointResultDispose(PointResult pointResult, PointDetail pointDetail)
	{
		// 设置交易流水号
		pointResult.setTransNo(pointDetail.getTransNo());

		// 设置原交易流水号
		pointResult.setSourceTransNo(pointDetail.getSourceTransNo());

		if(pointDetail.getPerPoint()==null) {
			pointResult.setPerPoint(String.valueOf(BigDecimal.ZERO));
		}
		else {
			// 设置积分
			pointResult.setPerPoint(pointDetail.getPerPoint().toString());
		}

		// 设置会计时间
		pointResult.setAccountantDate(DateUtil.DateToString(pointDetail.getSourceTransDate(),
				DateUtil.DEFAULT_DATE_TIME_FORMAT));

		return pointResult;
	}

	/**
	 * 积分分录数据处理 数据绑定
	 * 
	 * @param list
	 *            积分分录信息
	 * @param pointDetail
	 *            积分明细对象信息
	 * @return 返回积分分录信息
	 */
	public List<Alloc> pointEntry(List<Alloc> list, PointDetail pointDetail)
	{
		for (int i = 0; i < list.size(); i++)
		{
			Alloc alloc = list.get(i);
			// 设置关联交易流水号
			alloc.setRelTransNo(pointDetail.getTransNo());

			// 设置交易流水号
			alloc.setTransNo(pointDetail.getTransNo());

			// 设置分录表-序号
			alloc.setEntryNo(EsTools.GUID(Constants.TRANS_NO_POINT10));

			// 设置红冲标识
			alloc.setWriteBack(TransTypeUtil.writeBack(alloc.getTransType()));
		}
		return list;
	}

	/**
	 * 积分冲正 数据绑定
	 * 
	 * @param pointDetail
	 *            积分明细
	 * @return 返回账务冲正实体对象
	 */
	public AccountWriteBack writeBackPoint(PointDetail pointDetail)
	{

		// 创建冲正对象
		AccountWriteBack accountWriteBack = new AccountWriteBack();

		// 解析交易类型冲正标志
		int writeBack = Constants.WRITE_BACK_1;

		// 设置冲正标志
		accountWriteBack.setWriteBack(writeBack);

		// 设置积分明细交易类型
		accountWriteBack.setTransType(pointDetail.getTransType());

		// 设置积分明细交易流水号
		accountWriteBack.setRelTransNo(pointDetail.getTransNo());

		// 返回
		return accountWriteBack;
	}

	/**
	 * 退货明细处理 数据绑定
	 * 
	 * @param backDetail
	 *            退货明细对象信息
	 * @param oldOrder
	 *            积分明细对象信息(原积分单)
	 * @return 返回退货明细对象信息
	 */
	public BackDetail backDispose(BackDetail backDetail, PointDetail oldOrder)
	{
		// 设置订单交易流水号
		backDetail.setTransNo(EsTools.GUID(Constants.TRANS_NO_BACK12));

		// 设置批次号
		backDetail
				.setBatchNo(DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));

		// 设置原交易金额
		backDetail.setOldTransAmount(oldOrder.getTransAmount());

		// 设置原企业积分应付款
		backDetail.setOldEntPoint(oldOrder.getEntPoint());

		// 设置原消费者的积分
		backDetail.setOldPerPoint(oldOrder.getPerPoint());

		// 设置订单状态
		backDetail.setTransStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE0));

		// 设置原交易流水号
		backDetail.setOldTransNo(oldOrder.getTransNo());

		// 设置业务状态
		backDetail.setStatus(Constants.SUCCESS);

		return backDetail;
	}

	/**
	 * 退货返回处理 数据绑定
	 * 
	 * @param backResult
	 *            退货返回对象信息
	 * @param backDetail
	 *            退货明细对象信息
	 * @return 返回退货返回对象信息
	 */
	public BackResult backResultDispose(BackResult backResult, BackDetail backDetail)
	{
		// 积分比例
		// backResult.setPointRate(backDetail.getPointRate().toString());

		// 设置原交易币种
		backResult.setCurrency(backDetail.getSourceCurrencyCode());

		// 设置企业互生号
		backResult.setEntNo(backDetail.getEntResNo());

		// 设置积分应付款金额
		backResult.setAssureOutValue(backDetail.getEntPoint().toString());

		// 设置原订单金额
		backResult.setOrderAmount(backDetail.getSourceTransAmount().toString());

		// 设置交易金额
		backResult.setTransAmount(backDetail.getTransAmount().toString());

		// 设置交易流水号
		backResult.setTransNo(backDetail.getTransNo());

		// 设置积分
		backResult.setPerPoint(backDetail.getPerPoint().toString());

		// 设置会计时间
		backResult.setAccountantDate(DateUtil.DateToString(backDetail.getSourceTransDate(),
				DateUtil.DEFAULT_DATE_TIME_FORMAT));

		return backResult;
	}

	/**
	 * 退货分录数据处理
	 * 
	 * @param list
	 *            原分录信息
	 * @param backDetail
	 *            退货明细对象信息
	 * @param oldOrder
	 *            积分明细对象信息(原积分单)
	 * @return 返回退货分录信息
	 */
	public List<Alloc> backEntry(List<Alloc> list, BackDetail backDetail, PointDetail oldOrder)
	{
		for (Alloc alloc : list) {

			// 设置实时分录表-关联分录流水号
			alloc.setRelEntryNo(alloc.getEntryNo());

			// 设置交易流水号
			alloc.setTransNo(oldOrder.getTransNo());

			// 设置关联交易流水号
			alloc.setRelTransNo(backDetail.getTransNo());

			// 设置分录表-序号
			alloc.setEntryNo(EsTools.GUID(Constants.TRANS_NO_BACK12));

			// 设置交易类型
			alloc.setTransType(backDetail.getTransType());

			// 设置红冲标识
			alloc.setWriteBack(TransTypeUtil.writeBack(backDetail.getTransType()));

			// 设置原始交易时间
			alloc.setSourceTransDate(backDetail.getSourceTransDate());
			alloc.setIsSettle(oldOrder.getIsSettle());
			alloc.setIsDeduction(oldOrder.getIsDeduction());
		}
		return list;
	}

	/**
	 * 关联分录号
	 * 
	 * @param allocList
	 * @param entryAllot
	 * @return
	 */
	public void relEntryNoHandle(List<Alloc> allocList, EntryAllot entryAllot) {
		for (Alloc alloc : allocList) {
			if (alloc.getCustType() ==CustType.MEMBER_ENT.getCode()
					||alloc.getCustType()==CustType.TRUSTEESHIP_ENT.getCode()
					||alloc.getCustType()==CustType.PERSON.getCode()
					||alloc.getCustType()==CustType.NOT_HS_PERSON.getCode()) {
				alloc.setEntryNo(entryAllot.getEntryNo());}
		}
	}

	/**
	 * 关联分录号
	 *
	 * @param allocList
	 * @param entryAllot
	 * @return
	 */
	public void relEntryNoHandleBack(List<Alloc> allocList, EntryAllot entryAllot) {
		for (Alloc alloc : allocList) {
			alloc.setRelEntryNo(entryAllot.getRelEntryNo());
			alloc.setRelTransNo(entryAllot.getRelTransNo());
		}

	}

	/**
	 * 退货冲正 数据绑定
	 * 
	 * @param backDetail
	 *            退货明细
	 * @return 返回账务冲正实体对象
	 */
	public AccountWriteBack writeBackPoint(BackDetail backDetail)
	{

		// 创建冲正对象
		AccountWriteBack accountWriteBack = new AccountWriteBack();

		// 解析交易类型冲正标志
		int writeBack = Constants.WRITE_BACK_1;

		// 设置冲正标志
		accountWriteBack.setWriteBack(writeBack);

		// 设置退货明细交易类型
		accountWriteBack.setTransType(backDetail.getTransType());

		// 设置退货明细交易流水号
		accountWriteBack.setRelTransNo(backDetail.getTransNo());

		// 返回
		return accountWriteBack;
	}

	/**
	 * 撤单明细处理 数据绑定
	 * 
	 * @param cancelDetail
	 *            撤单明细对象信息
	 * @param oldOrder
	 *            积分明细对象信息(原积分单)
	 * @return 返回撤单明细对象信息
	 */
	public CancellationDetail cancelDispose(CancellationDetail cancelDetail, EntryAllot oldOrder,Cancel cance)
	{
		String cancelTransNo = EsTools.GUID(Constants.TRANS_NO_CANCEL11);
		// 设置交易流水号
		cancelDetail.setTransNo(cancelTransNo);

		// 设置订单类型
		cancelDetail.setTransStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE0));

		// 设置原交易流水号
		cancelDetail.setOldTransNo(oldOrder.getRelTransNo());

		// 设置业务状态
		cancelDetail.setStatus(Constants.SUCCESS);

		cancelDetail.setSourceTransDate(Timestamp.valueOf(cance.getSourceTransDate()));
		return cancelDetail;
	}

	/**
	 * 撤单返回处理 数据绑定
	 * 
	 * @param cancelResult
	 *            撤单返回对象信息
	 * @param cancelDetail
	 *            撤单明细对象信息
	 * @return 返回撤单返回对象信息
	 */
	public CancelResult cancelResultDispose(CancelResult cancelResult,
			CancellationDetail cancelDetail)
	{
		// 设置交易流水号
		cancelResult.setTransNo(cancelDetail.getTransNo());

		// 设置积分
		cancelResult.setPerPoint(String.valueOf(cancelDetail.getPerPoint()));

		// 设置撤单金额
		cancelResult.setTransAmount(cancelDetail.getTransAmount().toString());

		// 设置积分应付款金额
		cancelResult.setAssureOutValue(cancelDetail.getEntPoint().toString());

		// 设置积分
		cancelResult.setPointsValue(cancelDetail.getEntPoint().toString());

		// 设置会计时间
		cancelResult.setAccountantDate(DateUtil.DateToString(cancelDetail.getSourceTransDate(),
				DateUtil.DEFAULT_DATE_TIME_FORMAT));

		return cancelResult;
	}

	/**
	 * 撤单分录数据处理 数据绑定
	 * 
	 * @param list
	 *            原分录信息
	 * @param cancelDetail
	 *            撤单明细对象信息
	 * @param oldOrder
	 *            积分明细对象信息(原积分单)
	 * @return 返回撤单分录信息
	 */
	public List<Alloc> cancelEntry(List<Alloc> list, CancellationDetail cancelDetail,EntryAllot oldOrder)
	{
		for (Alloc alloc : list) {
			// 设置实时分录表-关联分录流水号
			alloc.setRelEntryNo(alloc.getEntryNo());

			alloc.setSourceTransNo(cancelDetail.getSourceTransNo());

			// 设置交易流水号
			alloc.setTransNo(cancelDetail.getTransNo());

			// 设置分录表-序号
			alloc.setEntryNo(EsTools.GUID(Constants.TRANS_NO_CANCEL11));

			// 设置关联交易流水号
			alloc.setRelTransNo(oldOrder.getRelTransNo());

			// 设置交易类型
			alloc.setTransType(cancelDetail.getTransType());

			// 设置红冲标识
			alloc.setWriteBack(TransTypeUtil.writeBack(cancelDetail.getTransType()));

			alloc.setSourceTransDate(cancelDetail.getSourceTransDate());

			alloc.setIsSettle(oldOrder.getIsSettle());

		}
		return list;
	}

	/**
	 * 撤单冲正 数据绑定
	 * 
	 * @param cancelDetail
	 *            撤单明细
	 * @return 返回账务冲正实体对象
	 */
	public AccountWriteBack writeBackPoint(CancellationDetail cancelDetail)
	{

		// 创建冲正对象
		AccountWriteBack accountWriteBack = new AccountWriteBack();

		// 解析交易类型冲正标志
		int writeBack = Constants.WRITE_BACK_1;

		// 设置冲正标志
		accountWriteBack.setWriteBack(writeBack);

		// 设置撤单明细交易类型
		accountWriteBack.setTransType(cancelDetail.getTransType());

		// 设置撤单明细交易流水号
		accountWriteBack.setRelTransNo(cancelDetail.getTransNo());

		// 返回
		return accountWriteBack;
	}

	/**
	 * 冲正明细处理 数据绑定
	 * 
	 * @param correctDetail
	 *            冲正明细对象信息
	 * @param correct
	 *            POS入参冲正对象信息
	 * @return 返回冲正明细对象信息
	 */
	public CorrectDetail correctDetailDispose(CorrectDetail correctDetail, Correct correct)
	{
		// 冲正流水号
		correctDetail.setReturnNo(EsTools.GUID(Constants.TRANS_NO_CORRECT13));

		// 设置冲正状态
		correctDetail
				.setTransStatus(Integer.parseInt(TransTypeUtil.transStatus(correct.getTransType())));

		// 设置业务状态
		correctDetail.setStatus(Constants.SUCCESS);

		return correctDetail;
	}

	/**
	 * 冲正分录数据处理 数据绑定
	 * 
	 * @param list
	 *            原分录信息
	 * @param correctDetail
	 *            冲正明细对象信息
	 * @param oldOrder
	 *            积分明细对象信息(原积分单)
	 * @return 返回冲正分录信息
	 */
	public List<Alloc> correctEntry(List<Alloc> list, CorrectDetail correctDetail,
			PointDetail oldOrder)
	{
		for (int i = 0; i < list.size(); i++)
		{
			Alloc alloc = (Alloc) list.get(i);
			// 设置实时分录表-关联分录流水号
			alloc.setRelEntryNo(alloc.getEntryNo());

			// 设置关联交易流水号
			alloc.setRelTransNo(correctDetail.getTransNo());

			// 设置交易流水号
			alloc.setTransNo(oldOrder.getTransNo());

			// 设置分录表-序号
			alloc.setEntryNo(EsTools.GUID(Constants.TRANS_NO_CORRECT13));

			// 设置交易类型
			alloc.setTransType(correctDetail.getTransType());

			// 设置红冲标识
			alloc.setWriteBack(TransTypeUtil.writeBack(correctDetail.getTransType()));

			// 设置原始交易时间
			alloc.setSourceTransDate(correctDetail.getSourceTransDate());
		}
		return list;
	}

	/**
	 * 退货退积分信息处理
	 * 
	 * @param list
	 * @return
	 */
	public List<Alloc> backPointHandle(List<Alloc> list)
	{
		List<Alloc> removeList=new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
		{
			Alloc alloc = list.get(i);

			String accType = alloc.getAccType();
			int custType = HsResNoUtils.getCustTypeByHsResNo(alloc.getHsResNo());

			if ( custType == CustType.PERSON.getCode())
			{
				if (accType.equals(AccountType.ACC_TYPE_POINT20110.getCode())||accType.equals(AccountType.ACC_TYPE_POINT20111.getCode()))
				{
					removeList.add(alloc);
				}
			}

			if (custType == CustType.MEMBER_ENT.getCode()
					|| custType == CustType.TRUSTEESHIP_ENT.getCode())
			{
				removeList.add(alloc);
			}
		}
		list.removeAll(removeList);
		return list;
	}
	
	/**
	 * 退货退款信息处理
	 * 
	 * @param list
	 * @return
	 */
	public List<Alloc> backHsbHandle(List<Alloc> list)
	{
		List<Alloc> removeList=new ArrayList<>();
		for (Alloc alloc : list) {
			String accType = alloc.getAccType();
			int custType = HsResNoUtils.getCustTypeByHsResNo(alloc.getHsResNo());

			if (custType == CustType.PERSON.getCode()) {
				if (!accType.equals(AccountType.ACC_TYPE_POINT20110.getCode())) {
					removeList.add(alloc);
				}
			}
			if (custType == CustType.MEMBER_ENT.getCode()
					|| custType == CustType.TRUSTEESHIP_ENT.getCode()) {
				if (!accType.equals(AccountType.ACC_TYPE_POINT20110.getCode())) {
					removeList.add(alloc);
				}
			}
		}
		list.removeAll(removeList);
		return list;
	}

}
