/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import java.math.BigDecimal;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.ps.common.*;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointItem;
import com.gy.hsxt.ps.distribution.bean.PointRate;
import org.apache.commons.lang3.StringUtils;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: AllocItem
 * @Description: 积分分配
 * @author: liuchao
 * @updated: chenhongzhi
 * @date: 2015-12-3 上午10:51:29
 */

public class AllocItem extends PointItem
{

	/**
	 * 初始化 做运算处理 计算企业应付积分、消费者应得积分款、积分总额、待分配积分
	 * 
	 * @param transAmout
	 * @throws HsException
	 */
	public void init(BigDecimal transAmout, BigDecimal pointRate, BigDecimal currencyRate)
	{
		// 计算企业应付积分
		this.entPoint = Compute.mulCeiling(transAmout, pointRate, Constants.SURPLUS_TWO);

		// 积分总额
		this.pointSum = Compute.divFloor(entPoint, currencyRate, Constants.SURPLUS_TWO);

		// 消费者应得积分款
		this.perPoint = Compute.mulFloor(pointSum, PointRate.perRate, Constants.SURPLUS_TWO);

		// 待分配积分
		this.waitPoint = getSubPoint(pointSum, perPoint);

		// 结余积分
		this.residuePoint = this.waitPoint;

		// 解析交易类型
		this.transWay = TransTypeUtil.transWay(transType);

		// 解析冲正标识
		this.writeBack = TransTypeUtil.writeBack(transType);

	}

	/**
	 * 初始化 做运算
	 *
	 * @param sumAmount
	 * @param rate
	 * @throws HsException
	 */
	public void init(BigDecimal sumAmount, BigDecimal rate)
	{
		this.entPoint = Compute.roundFloor(sumAmount, Constants.SURPLUS_TWO);
		this.pointSum = Compute.divFloor(entPoint, rate, Constants.SURPLUS_TWO);
		this.perPoint = Compute.mulFloor(pointSum, PointRate.perRate, Constants.SURPLUS_TWO);
		this.waitPoint = getSubPoint(pointSum, perPoint);
		this.residuePoint = this.waitPoint;
		this.transWay = TransTypeUtil.transWay(transType);
		this.writeBack = TransTypeUtil.writeBack(transType);
	}

	/**
	 * 初始化 商业服务费做运算
	 *
	 * @param sumAmount
	 * @param taxRate
	 * @throws HsException
	 */
	public void initCsc(BigDecimal sumAmount, BigDecimal taxRate)
	{
		this.serviceAddAmount = Compute.mulFloor(sumAmount, PointRate.serviceBusinessRate,
				Constants.SURPLUS_TWO);
		this.taxAddAmount = Compute.mulRoundUp(serviceAddAmount, taxRate, Constants.SURPLUS_TWO);
		this.serviceSubTaxAfterAmount = getSubPoint(serviceAddAmount, taxAddAmount);
		this.paasAddAmount = getSubPoint(sumAmount, serviceAddAmount);
	}

	/**
	 * 积分分配基本信息
	 *
	 * @param custId
	 *            客户号
	 * @param custType
	 *            客户类型
	 * @param hsResNo
	 *            互生号
	 * @param accType
	 *            交易类型
	 * @return 返回分配明细信息
	 */
	private Alloc getDetail(String custId, int custType, String hsResNo, String accType)
	{
		// 创建积分分配信息存储对象
		Alloc ac = new Alloc();
		// 设置积分分配表流水号
		ac.setAllocNo(GuidAgent.getStringGuid(Constants.NO_POINT_ALLOC20+PsTools.getInstanceNo()));

		// 设置交易类型
		ac.setAccType(accType);

		// 设置客户类型
		ac.setCustType(custType);

		// 设置客户ID
		ac.setCustId(custId);

		// 设置互生号
		ac.setHsResNo(hsResNo);

		// 设置渠道类型
		ac.setChannelType(this.channelType);

		// 设置设备来源
		ac.setEquipmentType(this.equipmentType);

		// 设置关联流水号
		ac.setRelTransNo(this.transNo);

		// 设置交易类型
		ac.setTransType(this.transType);
		//ac.setTransType(Constants.HSB_BUSINESS_POINT);

		// 设置冲正标识
		ac.setWriteBack(this.writeBack);

		// 设置关联的交易类型
		ac.setRelTransType(this.relTransType);

		// 设置消费者互生号
		ac.setPerResNo(this.perResNo);

		// 设置批次号
		ac.setBatchNo(this.batchNo);

		// 设置原批次号
		ac.setSourceBatchNo(this.sourceBatchNo);

		// 设置原交易流水号
		ac.setSourceTransNo(this.sourceTransNo);

		// 设置原交易时间
		ac.setSourceTransDate(this.sourceTransDate);

		ac.setIsSettle(Constants.IS_SETTLE1);

//		if (accType.equals(AccountType.ACC_TYPE_POINT20111.getCode()))
//		{
//			ac.setIsSettle(Constants.IS_SETTLE1);
//		} else
//		{
//			ac.setIsSettle(Constants.IS_SETTLE0);
//		}
		return ac;
	}

	/**
	 * 商业服务费分配
	 * @param custId
	 * @param custType
	 * @param hsResNo
	 * @param accType
	 * @return
	 */
	private Alloc getCscDetail(String custId, int custType, String hsResNo, String accType)
	{
		// 创建积分分配信息存储对象
		Alloc alloc = new Alloc();
		if (PsRedisUtil.getLocalInfo().getPlatResNo().equals(hsResNo)|| StringUtils.isEmpty(this.getTollNo()))
		{
		// 设置积分分配表流水号(平台的自动生成)
			alloc.setTollNo(GuidAgent.getStringGuid(Constants.NO_SERVICE_FEE32 + PsTools.getInstanceNo()));
		}
		else {
			// 设置积分分配表流水号
			alloc.setTollNo(this.getTollNo());
		}

		alloc.setTollAccType(accType);

		alloc.setHsResNo(hsResNo);

		// 设置客户ID
		alloc.setCustId(custId);

		alloc.setCustType(custType);

		// 设置冲正标识
		alloc.setWriteBack(this.writeBack);
		if (StringUtils.isEmpty(this.batchNo)) {
			// 批次号
			alloc.setBatchNo(PsTools.getBeforeDay());
		}
		else{
			alloc.setBatchNo(this.batchNo);
		}
		// 交易类型
		alloc.setTransType(Constants.HSB_BUSINESS_MONTH_ALLOC_CSC);

		alloc.setWriteBack(TransTypeUtil.writeBack(alloc.getTransType()));
		// 月结
		alloc.setSettleType(Constants.SETTLE_TYPE_MONTH2);
		// 服务费类型
		alloc.setCscType(Constants.CSC_TYPE_SERVICE_FEE1);
		// 费率
		// alloc.setAmountRate(Constants.HSB_CSC_RATE);

		return alloc;
	}

	/**
	 * 获取增向金额
	 *
	 * @param custId
	 * @param hsResNo
	 * @param accType
	 * @return
	 */
	private Alloc getCscAddAmount(String custId, BigDecimal subAmount, int custType,
			String hsResNo, String accType)
	{
		// 积分分配基本信息
		Alloc ac = this.getCscDetail(custId, custType, hsResNo, accType);
		// 设置减向金额
		ac.setTollAddAmount(subAmount);
		return ac;
	}

	/**
	 * 获取减向金额
	 *
	 * @param subAmount
	 * @param custId
	 * @param hsResNo
	 * @param accType
	 * @return
	 */
	@SuppressWarnings("unused")
	private Alloc getCscSubAmount(String custId, BigDecimal subAmount, int custType,
			String hsResNo, String accType)
	{
		// 积分分配基本信息
		Alloc ac = this.getCscDetail(custId, custType, hsResNo, accType);
		// 设置减向金额
		ac.setTollSubAmount(subAmount);
		return ac;
	}

	/**
	 * 获取服务公司商业服务费
	 *
	 * @return
	 */
	public Alloc getServiceHsbAddCsc()
	{

		return this.getCscAddAmount(this.custId, serviceSubTaxAfterAmount,
				CustType.SERVICE_CORP.getCode(), this.serviceResNo,
				AccountType.ACC_TYPE_POINT20110.getCode());
	}

	/**
	 * 获取服务公司商业服务费税收
	 *
	 * @return
	 */
	public Alloc getServiceTaxAddCsc()
	{

		return this.getCscAddAmount(this.custId, serviceSubTaxAfterAmount,
				CustType.SERVICE_CORP.getCode(), this.serviceResNo,
				AccountType.ACC_TYPE_POINT20610.getCode());
	}

	/**
	 * 获取平台商业服务费
	 *
	 * @return
	 */
	public Alloc getPaasHsbAddCsc()
	{

		return this.getCscAddAmount(this.paasCustId, paasAddAmount, CustType.AREA_PLAT.getCode(),
				this.paasResNo, AccountType.ACC_TYPE_POINT20420.getCode());
	}

	/**
	 * 获取减向金额
	 *
	 * @param subAmount
	 * @param custId
	 * @param hsResNo
	 * @param accType
	 * @return
	 */
	private Alloc getAllocSubAmount(BigDecimal subAmount, String custId, int custType,
			String hsResNo, String accType)
	{
		// 积分分配基本信息
		Alloc ac = this.getDetail(custId, custType, hsResNo, accType);
		// 设置减向金额
		ac.setSubAmount(subAmount);
		return ac;
	}

	/**
	 * 获取增向金额
	 *
	 * @param custId
	 * @param hsResNo
	 * @param accType
	 * @param addAmout
	 * @return
	 */
	private Alloc getAllocAddAmount(String custId, int custType, String hsResNo, String accType,
			BigDecimal addAmout)
	{
		// 积分分配基本信息
		Alloc ac = this.getDetail(custId, custType, hsResNo, accType);

		// 设置增向金额
		ac.setAddAmount(addAmout);
		// ac.setDeductionAmount(addAmout);
		return ac;
	}

	/**
	 * 获取个人增向
	 *
	 * @param accType
	 * @param amount
	 * @return
	 */
	private Alloc getAddPer(String accType, BigDecimal amount)
	{
		return this.getAllocAddAmount(this.perCustId, CustType.PERSON.getCode(), this.perResNo,
				accType, amount);
	}

	/**
	 * 获取个人减向
	 *
	 * @param accType
	 * @param amount
	 * @return
	 */
	private Alloc getSubPer(String accType, BigDecimal amount)
	{
		return this.getAllocSubAmount(amount, this.perCustId, CustType.PERSON.getCode(),
				this.perResNo, accType);
	}

	/**
	 * 获取消费者得到的积分
	 *
	 * @return
	 */
	public Alloc getPerAddPoint()
	{
		return this.getAddPer(AccountType.ACC_TYPE_POINT10110.getCode(), this.perPoint);
	}

	/**
	 * 获取消费者减向积分
	 *
	 * @return
	 */
	public Alloc getPerSubPoint()
	{
		return this.getSubPer(AccountType.ACC_TYPE_POINT10110.getCode(), this.perPoint);
	}

	/**
	 * 获取扣除消费者的流通币互生币
	 *
	 * @return
	 */
	public Alloc getPerSubHsb1()
	{
		return this.getSubPer(AccountType.ACC_TYPE_POINT20110.getCode(), this.transAmount);
	}

	/**
	 * 获取扣除消费者的互生定向币
	 *
	 * @return
	 */
	// public Alloc getPerSubHsb2()
	// {
	// return this.getSubPer(AccountType.ACC_TYPE_POINT20120.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取增向消费者的流通币互生币
	 *
	 * @return
	 */
	public Alloc getPerAddHsb1()
	{
		return this.getAddPer(AccountType.ACC_TYPE_POINT20110.getCode(), this.transAmount);
	}

	/**
	 * 获取增向消费者的互生定向币
	 *
	 * @return
	 */
	// public Alloc getPerAddHsb2()
	// {
	// return this.getAddPer(AccountType.ACC_TYPE_POINT20122.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取扣除预留消费者的流通生币
	 *
	 * @param custId
	 * @param hsResNo
	 * @return
	 */
	// public Alloc getPerSubHsbByRes1()
	// {
	// return this.getSubPer(AccountType.ACC_TYPE_POINT20112.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取扣除预留消费者的定向币
	 *
	 * @param custId
	 * @param hsResNo
	 * @return
	 */
	// public Alloc getPerSubHsbByRes2()
	// {
	// return this.getSubPer(AccountType.ACC_TYPE_POINT20122.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取增向预留消费者的流通币
	 *
	 * @param custId
	 * @param hsResNo
	 * @return
	 */
	// public Alloc getPerAddHsbByRes1()
	// {
	// return this.getAddPer(AccountType.ACC_TYPE_POINT20112.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取增向预留消费者的定向币
	 *
	 * @param custId
	 * @param hsResNo
	 * @return
	 */
	// public Alloc getPerAddHsbByRes2()
	// {
	// return this.getAddPer(AccountType.ACC_TYPE_POINT20122.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取企业增向
	 *
	 * @param accType
	 * @param amount
	 * @return
	 */
	private Alloc getAddEnt(String accType, BigDecimal amount)
	{
		if (CustType.MEMBER_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(this.entResNo))
		{

			return this.getAllocAddAmount(this.entCustId, CustType.MEMBER_ENT.getCode(),
					this.entResNo, accType, amount);

		} else if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils
				.getCustTypeByHsResNo(this.entResNo))
		{

			return this.getAllocAddAmount(this.entCustId, CustType.TRUSTEESHIP_ENT.getCode(),
					this.entResNo, accType, amount);
		}
		return null;
	}

	/**
	 * 获取企业减向
	 *
	 * @param accType
	 * @param amount
	 * @return
	 */
	private Alloc getSubEnt(String accType, BigDecimal amount)
	{
		if (CustType.MEMBER_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(this.entResNo))
		{

			return this.getAllocSubAmount(amount, this.entCustId, CustType.MEMBER_ENT.getCode(),
					this.entResNo, accType);

		} else if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils
				.getCustTypeByHsResNo(this.entResNo))
		{

			return this.getAllocSubAmount(amount, this.entCustId,
					CustType.TRUSTEESHIP_ENT.getCode(), this.entResNo, accType);
		}
		return null;
	}

	/**
	 * 获取增加企业积分款
	 *
	 * @return
	 */
	public Alloc getEntAddPoints()
	{
		return this.getAddEnt(AccountType.ACC_TYPE_POINT20110.getCode(), this.getEntPoint());
	}

	/**
	 * 获取扣除企业积分款
	 *
	 * @return
	 */
	public Alloc getEntSubPoints()
	{
		return this.getSubEnt(AccountType.ACC_TYPE_POINT20110.getCode(), this.getEntPoint());
	}

	/**
	 * 获取增加预留企业积分款
	 *
	 * @return
	 */
	// public Alloc getEntAddPointsByRes()
	// {
	// return this.getAddEnt(AccountType.ACC_TYPE_POINT30212.getCode(),
	// this.getEntPoint());
	// }

	/**
	 * 获取扣除预留企业积分款
	 *
	 * @return
	 */
	// public Alloc getEntSubPointsByRes()
	// {
	// return this.getSubEnt(AccountType.ACC_TYPE_POINT30212.getCode(),
	// this.getEntPoint());
	// }

	/**
	 * 获取企业增向互生币
	 *
	 * @return
	 */
	public Alloc getEntAddHsb()
	{
		return this.getAddEnt(AccountType.ACC_TYPE_POINT20111.getCode(), this.getTransAmount());
	}

	/**
	 * 获取企业减向互生币
	 *
	 * @return
	 */
	public Alloc getEntSubHsb()
	{
		return this.getSubEnt(AccountType.ACC_TYPE_POINT20111.getCode(), this.getTransAmount());
	}

	/**
	 * 获取企业减向互生币
	 *
	 * @return
	 */
	public Alloc getEntBackSubHsb()
	{
		return this.getSubEnt(AccountType.ACC_TYPE_POINT20110.getCode(), this.getTransAmount());
	}

	/**
	 * 获取剩余減向积分
	 *
	 * @return
	 */
	public BigDecimal getSubPoint(BigDecimal residue, BigDecimal point)
	{
		return Compute.sub(residue, point);
	}

	/**
	 * 获取企业积分
	 *
	 * @return
	 */
	public BigDecimal getOrgPoint(BigDecimal rate)
	{
		BigDecimal point = Compute.mulFloor(this.pointSum, rate, Constants.SURPLUS_TWO);
		this.residuePoint = getSubPoint(this.residuePoint, point);
		return point;
	}

	/**
	 * 获取企业减向积分
	 *
	 * @return
	 */
	public BigDecimal getOrgSubPoint(BigDecimal rate)
	{
		BigDecimal point = Compute.mulCeiling(this.pointSum, rate, Constants.SURPLUS_TWO);
		this.residuePoint = getSubPoint(this.residuePoint, point);
		return point;
	}
	/**
	 * 获取企业多次算法
	 *
	 * @return
	 */
	private BigDecimal getOrgTax(BigDecimal rate, BigDecimal taxRate)
	{
		BigDecimal point = Compute.mulFloor(rate, taxRate, Constants.SURPLUS_TWO);
		return point;
	}

	/**
	 * 获取托管公司积分
	 *
	 * @return
	 */
	public Alloc getTrusteeAddPoint()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.trusteeRate);
		return this.getAllocAddAmount(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(),
				this.trusteeResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
	}


	/**
	 * 获取托管公司积分
	 *
	 * @return
	 */
	public Alloc getTrusteeUpAddPoint()
	{
		BigDecimal amout = this.getOrgSubPoint(PointRate.trusteeRate);
		return this.getAllocAddAmount(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(),
				this.trusteeResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
	}

	/**
	 * 获取托管公司减向积分
	 *
	 * @return
	 */
	public Alloc getTrusteeSubPoint()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.trusteeRate);
		amout = amout.subtract(this.getOrgTax(amout, PointRate.trusteeTaxRate));
		return this.getAllocSubAmount(amout, this.trusteeCustId,
				CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo,
				AccountType.ACC_TYPE_POINT10110.getCode());
	}

	/**
	 * 获取托管公司减向积分 无扣税
	 *
	 * @return
	 */
	public Alloc getTrusteeSubPoint1()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.trusteeRate);
		return this.getAllocSubAmount(amout, this.trusteeCustId,
				CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo,
				AccountType.ACC_TYPE_POINT10110.getCode());
	}

	/**
	 * 获取增向托管公司待分配互生币
	 *
	 * @return
	 */
	public Alloc getTrusteeAddHsb()
	{
		return this.getAddTrustee(AccountType.ACC_TYPE_POINT20111.getCode(), this.transAmount);
	}

	/**
	 * 获取减向托管公司待分配互生币
	 *
	 * @return
	 */
	public Alloc getTrusteeSubHsb()
	{
		return this.getSubTrustee(AccountType.ACC_TYPE_POINT20111.getCode(), this.transAmount);
	}

	/**
	 * 获取托管公司互生币增向
	 *
	 * @param accType
	 * @param amout
	 * @return
	 */
	private Alloc getAddTrustee(String accType, BigDecimal amout)
	{
		return this.getAllocAddAmount(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(),
				this.trusteeResNo, accType, amout);
	}

	/**
	 * 获取托管公司互生币减向
	 *
	 * @param accType
	 * @param amout
	 * @return
	 */
	private Alloc getSubTrustee(String accType, BigDecimal amout)
	{
		return this.getAllocSubAmount(amout, this.trusteeCustId,
				CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, accType);
	}

	/**
	 * 获取托管公司城市税收
	 *
	 * @return
	 */
	public Alloc getTrusteeAddTax(BigDecimal trusteeRate)
	{
		BigDecimal tax = this.getOrgTax(trusteeRate, PointRate.trusteeTaxRate);
		return this.getAllocAddAmount(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(),
				this.trusteeResNo, AccountType.ACC_TYPE_POINT10510.getCode(), tax);
	}

	/**
	 * 获取托管公司城市税收减向
	 *
	 * @return
	 */
	public Alloc getTrusteeSubTax()
	{
		BigDecimal trusteeRate = Compute.mulFloor(this.pointSum, PointRate.trusteeRate,
				Constants.SURPLUS_TWO);
		BigDecimal tax = this.getOrgTax(trusteeRate, PointRate.trusteeTaxRate);
		return this.getAllocSubAmount(tax, this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(),
				this.trusteeResNo, AccountType.ACC_TYPE_POINT10510.getCode());
	}

	/**
	 * 获取托管公司商业服务费减向
	 *
	 * @return
	 */
	public Alloc getTrusteeServiceSubTax()
	{
		BigDecimal tax = this.getOrgTax(this.transAmount, PointRate.trusteeServiceRate);
		return this.getAllocSubAmount(tax, this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(),
				this.trusteeResNo, AccountType.ACC_TYPE_POINT20421.getCode());
	}

	/**
	 * 获取服务公司获得积分
	 *
	 * @return
	 */
	public Alloc getServiceAddPoint()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.serviceRate);
		return this.getAllocAddAmount(this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
	}

	/**
	 * 获取服务公司获得积分
	 *
	 * @return
	 */
	public Alloc getServiceUpAddPoint()
	{
		BigDecimal amout = this.getOrgSubPoint(PointRate.serviceRate);
		return this.getAllocAddAmount(this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
	}

	/**
	 * 获取服务公司减向积分
	 *
	 * @return
	 */
	public Alloc getServiceSubPoint()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.serviceRate);
		amout = amout.subtract(this.getOrgTax(amout, PointRate.serviceTaxRate));
		return this.getAllocSubAmount(amout, this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode());
	}

	/**
	 * 获取服务公司减向积分 无扣税
	 *
	 * @return
	 */
	public Alloc getServiceSubPoint1()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.serviceRate);
		return this.getAllocSubAmount(amout, this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode());
	}

	/**
	 * 获取服务公司城市税收
	 *
	 * @return
	 */
	public Alloc getServiceAddTax(BigDecimal serviceRate)
	{
		BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
		return this.getAllocAddAmount(this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT10510.getCode(), tax);
	}

	/**
	 * 获取服务公司城市税收减向
	 *
	 * @return
	 */
	public Alloc getServiceSubTax()
	{
		BigDecimal serviceRate = Compute.mulFloor(this.pointSum, PointRate.serviceRate,
				Constants.SURPLUS_TWO);
		BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
		return this.getAllocSubAmount(tax, this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT10510.getCode());
	}

	/**
	 * 获取服务公司城市税收
	 *
	 * @return
	 */
	public Alloc getServiceAddHsbTax(BigDecimal serviceRate)
	{
		BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
		return this.getAllocAddAmount(this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT20610.getCode(), tax);
	}

	/**
	 * 获取服务公司城市税收减向
	 *
	 * @return
	 */
	public Alloc getServiceSubHsbTax()
	{
		BigDecimal serviceRate = Compute.mulFloor(this.pointSum, PointRate.serviceRate,
				Constants.SURPLUS_TWO);
		BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
		return this.getAllocSubAmount(tax, this.serviceCustId, CustType.SERVICE_CORP.getCode(),
				this.serviceResNo, AccountType.ACC_TYPE_POINT20610.getCode());
	}

	/**
	 * 获取管理公司获得积分
	 *
	 * @return
	 */
	public Alloc getManageAddPoint()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.manageRate);
		return this.getAllocAddAmount(this.manageCustId, CustType.MANAGE_CORP.getCode(),
				this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
	}

	/**
	 * 获取管理公司获得积分
	 *
	 * @return
	 */
	public Alloc getManageUpAddPoint()
	{
		BigDecimal amout = this.getOrgSubPoint(PointRate.manageRate);
		return this.getAllocAddAmount(this.manageCustId, CustType.MANAGE_CORP.getCode(),
				this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
	}

	/**
	 * 获取管理公司减向积分
	 *
	 * @return
	 */
	public Alloc getManageSubPoint()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.manageRate);
		amout = amout.subtract(this.getOrgTax(amout, PointRate.manageTaxRate));
		return this.getAllocSubAmount(amout, this.manageCustId, CustType.MANAGE_CORP.getCode(),
				this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode());
	}

	/**
	 * 获取管理公司减向积分 无扣税
	 *
	 * @return
	 */
	public Alloc getManageSubPoint1()
	{
		BigDecimal amout = this.getOrgPoint(PointRate.manageRate);
		return this.getAllocSubAmount(amout, this.manageCustId, CustType.MANAGE_CORP.getCode(),
				this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode());
	}

	/**
	 * 获取管理公司城市税收
	 *
	 * @return
	 */
	public Alloc getManageAddTax(BigDecimal manageRate)
	{
		BigDecimal tax = this.getOrgTax(manageRate, PointRate.manageTaxRate);
		return this.getAllocAddAmount(this.manageCustId, CustType.MANAGE_CORP.getCode(),
				this.manageResNo, AccountType.ACC_TYPE_POINT10510.getCode(), tax);
	}

	/**
	 * 获取管理公司城市税收减向
	 *
	 * @return
	 */
	public Alloc getManageSubTax()
	{
		BigDecimal manageRate = Compute.mulFloor(this.pointSum, PointRate.manageRate,
				Constants.SURPLUS_TWO);
		BigDecimal tax = this.getOrgTax(manageRate, PointRate.manageTaxRate);
		return this.getAllocSubAmount(tax, this.manageCustId, CustType.MANAGE_CORP.getCode(),
				this.manageResNo, AccountType.ACC_TYPE_POINT10510.getCode());
	}

	/**
	 * 获取平台增向
	 *
	 * @param accType
	 * @param amout
	 * @return
	 */
	private Alloc getAddPaas(String accType, BigDecimal amout)
	{
		return this.getAllocAddAmount(this.paasCustId, CustType.AREA_PLAT.getCode(),
				this.paasResNo, accType, amout);
	}

	/**
	 * 获取平台减向
	 *
	 * @param accType
	 * @param amout
	 * @return
	 */
	private Alloc getSubPaas(String accType, BigDecimal amout)
	{
		return this.getAllocSubAmount(amout, this.paasCustId, CustType.AREA_PLAT.getCode(),
				this.paasResNo, accType);
	}

	/**
	 * 获取增向平台待分配互生币
	 *
	 * @return
	 */
	// public Alloc getPaasAddHsb()
	// {
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT20111.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取减向平台待分配互生币
	 *
	 * @return
	 */
	// public Alloc getPaasSubHsb()
	// {
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT20111.getCode(),
	// this.transAmount);
	// }

	/**
	 * 获取平台分配积分
	 *
	 * @return
	 */
	public Alloc getPaasAddPoint()
	{
		return this.getAddPaas(AccountType.ACC_TYPE_POINT10310.getCode(), this.waitPoint);
	}

	/**
	 * 获取减向平台分配积分
	 *
	 * @return
	 */
	public Alloc getPaasSubPoint()
	{
		return this.getSubPaas(AccountType.ACC_TYPE_POINT10310.getCode(), this.waitPoint);
	}

	/**
	 * 获取平台沉淀积分(非持卡人积分)
	 *
	 * @return
	 */
	public Alloc getPaasSedimentAddPoint()
	{
		return this.getAddPaas(AccountType.ACC_TYPE_POINT10210.getCode(), this.pointSum);
	}

	/**
	 * 获取平台减向沉淀积分(非持卡人积分)
	 *
	 * @return
	 */
	public Alloc getPaasSedimentSubPoint()
	{
		return this.getSubPaas(AccountType.ACC_TYPE_POINT10210.getCode(), this.pointSum);
	}

	/**
	 * 获取平台积分
	 *
	 * @return
	 */
	public Alloc getPaasAddPoints()
	{
		return this.getAddPaas(AccountType.ACC_TYPE_POINT20110.getCode(), this.getEntPoint());
	}

	/**
	 * 平台20%后额度
	 *
	 * @param rate
	 * @return
	 */
	public BigDecimal getPaasPoint(BigDecimal rate)
	{
		BigDecimal point = Compute.mulFloor(this.pointSum, rate, Constants.SURPLUS_TWO);
		this.residuePoint = Compute.sub(this.residuePoint, point);
		return point;
	}


	/**
	 * 平台20%后额度退回（隔日退货)
	 * @return
	 */
	public BigDecimal getPaasUpPoint()
	{
		BigDecimal point = Compute.sub(this.pointSum,this.getTrusteeUpAddPoint().getAddAmount().add(this.getManageUpAddPoint().getAddAmount()).add(this.getServiceUpAddPoint().getAddAmount()).add(Compute.mulCeiling(pointSum, PointRate.perRate, Constants.SURPLUS_TWO)));
		this.residuePoint = Compute.sub(this.residuePoint, point);
		return point;
	}
	/**
	 * 获取平台总共积分
	 *
	 * @return
	 */
	public Alloc getAllPaasAddPoint()
	{
		BigDecimal rate = this.getPaasPoint(PointRate.paasRate);
		return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(), rate);
	}

	/**
	 * 获取平台总共积分
	 *
	 * @return
	 */
	public Alloc getAllPaasUpAddPoint()
	{
		BigDecimal rate = this.getPaasUpPoint();
		return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(), rate);
	}

	/**
	 * 获取平台总共积分(非持卡人积分都给平台)
	 *
	 * @return
	 */
	public Alloc getAllNoCardPaasAddPoint()
	{
		BigDecimal rate = this.getPaasPoint(BigDecimal.ONE);
		return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(),rate);
	}
	/**
	 * 获取平台总共积分
	 *
	 * @return
	 */
	public Alloc getAllPaasSubPoint()
	{
		BigDecimal rate = this.getPaasPoint(PointRate.paasRate);
		return this.getSubPaas(AccountType.ACC_TYPE_POINT10110.getCode(), rate);
	}

	/**
	 * 获取平台税收
	 *
	 * @return
	 */
	// public Alloc getPaasAddTax()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasTaxRate);
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT10310.getCode(), tax);
	// }

	/**
	 * 获取平台税收减向
	 * 
	 * @return
	 */
	// public Alloc getPaasSubTax()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasTaxRate);
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT10310.getCode(), tax);
	// }

	/**
	 * 平台运营成本
	 * 
	 * @return
	 */
	// public Alloc getPaasAddOpex()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasOpexRate);
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT10350.getCode(), tax);
	// }

	/**
	 * 平台运营成本减向
	 * 
	 * @return
	 */
	// public Alloc getPaasSubOpex()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasOpexRate);
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT10350.getCode(), tax);
	// }

	/**
	 * 互生慈善救助基金
	 * 
	 * @return
	 */
	// public Alloc getPaasAddSalvage()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasSalvageRate);
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT10360.getCode(), tax);
	// }

	/**
	 * 互生慈善救助基金减向
	 * 
	 * @return
	 */
	// public Alloc getPaasSubSalvage()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasSalvageRate);
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT10360.getCode(), tax);
	// }

	/**
	 * 社会安全保障基金
	 * 
	 * @return
	 */
	// public Alloc getPaasAddInsurance()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasInsuranceRate);
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT10330.getCode(), tax);
	// }

	/**
	 * 社会安全保障基金减向
	 * 
	 * @return
	 */
	// public Alloc getPaasSubInsurance()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasInsuranceRate);
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT10330.getCode(), tax);
	// }

	/**
	 * 积分企业增值基金
	 * 
	 * @return
	 */
	// public Alloc getPaasAddIncrement()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasIncrementRate);
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT10340.getCode(), tax);
	// }

	/**
	 * 积分企业增值基金减向
	 * 
	 * @return
	 */
	// public Alloc getPaasSubIncrement()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasIncrementRate);
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT10340.getCode(), tax);
	// }

	/**
	 * 社会应急储备基金
	 * 
	 * @return
	 */
	// public Alloc getPaasAddReserve()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasReserveRate);
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT10370.getCode(), tax);
	// }

	/**
	 * 社会应急储备基金减向
	 * 
	 * @return
	 */
	// public Alloc getPaasSubReserve()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasReserveRate);
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT10370.getCode(), tax);
	// }

	/**
	 * 消费者意外保障
	 * 
	 * @return
	 */
	// public Alloc getPaasAddAccident()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasAccidentRate);
	// return this.getAddPaas(AccountType.ACC_TYPE_POINT10320.getCode(), tax);
	// }

	/**
	 * 消费者意外保障 减向
	 * 
	 * @return
	 */
	// public Alloc getPaasSubAccident()
	// {
	// BigDecimal tax = this.getPaasPoint(PointRate.paasAccidentRate);
	// return this.getSubPaas(AccountType.ACC_TYPE_POINT10320.getCode(), tax);
	// }

	/**
	 * 获取结余
	 * 
	 * @return
	 */
	public Alloc getResidueAddPoint()
	{
		return this.getAddPaas(AccountType.ACC_TYPE_POINT10220.getCode(), this.residuePoint);
	}

	/**
	 * 获取结余减向
	 * 
	 * @return
	 */
	public Alloc getResidueSubPoint()
	{
		System.out.println("[this.residuePoint]" + this.residuePoint);
		return this.getSubPaas(AccountType.ACC_TYPE_POINT10220.getCode(), this.residuePoint);
	}

	/**
	 * 日终税收
	 * 
	 * @return
	 */
	public static BigDecimal getTaxPoint(BigDecimal taxAmount, String taxRate)
	{

		/*
		 * BigDecimal rate = new BigDecimal(0.00); if (taxRate ==
		 * Constants.HS_MANAGE) { rate = PointRate.manageTaxRate; } else if
		 * (taxRate == Constants.HS_SERVICE) { rate = PointRate.serviceTaxRate;
		 * } else if (taxRate == Constants.HS_TRUSTEE) { rate =
		 * PointRate.trusteeTaxRate; }
		 */
		BigDecimal rate = new BigDecimal(taxRate);
		BigDecimal tax = Compute.mulFloor(taxAmount, rate, Constants.SURPLUS_TWO);
		return tax;
	}

	/**
	 * 日终商业服务费暂存
	 * 
	 * @param amount
	 * @return
	 */
	public static BigDecimal getBusinessServiceFee(BigDecimal amount)
	{
		return Compute.mulFloor(amount, PointRate.trusteeServiceRate, Constants.SURPLUS_TWO);
	}

	/**
	 * 汇总后减去日终税收、商业服务费
	 * 
	 * @param sum
	 * @param tax
	 * @return
	 */
	public static BigDecimal getSumSubTax(BigDecimal sum, BigDecimal tax)
	{
		return (tax != null) ? Compute.sub(sum, tax) : sum;
	}

	/**
	 * 汇总后减去日终商业服务费
	 * 
	 * @param addAmount
	 * @param serviceFee
	 * @return
	 */
	public static BigDecimal getSumSubServiceDayFee(BigDecimal addAmount, BigDecimal subAmount,
			BigDecimal serviceFee)
	{
		BigDecimal bd = new BigDecimal(0.00);
		if (subAmount == null)
		{
			bd = Compute.sub(addAmount, serviceFee);
		} else if (addAmount != null  && serviceFee != null)
		{
			bd = Compute.sub(addAmount, subAmount, serviceFee);
		}
		return bd;
	}

	/**
	 * 月结商业服务费的税收(服务公司)
	 *
	 * @param serviceFee
	 * @return
	 */
	public static BigDecimal cityTaxService(BigDecimal serviceFee, BigDecimal taxRate)
	{
		return Compute.mulRoundUp(serviceFee, taxRate, Constants.SURPLUS_TWO);
	}

	/**
	 * 月终服务公司商业服务费结算
	 * 
	 * @param serviceFee
	 * @return
	 */
	public static BigDecimal getSumSubServiceMonthFee(BigDecimal serviceFee, BigDecimal taxRate)
	{
		BigDecimal serFee = Compute.mulFloor(serviceFee, PointRate.serviceBusinessRate,
				Constants.SURPLUS_TWO);
		return cityTaxService(serFee, taxRate);
	}

	/**
	 * 月终平台商业服务费结算
	 * 
	 * @param paasFee
	 * @param serviceFee
	 * @return
	 */
	public static BigDecimal getSumSubPaasMonthFee(BigDecimal paasFee, BigDecimal serviceFee)
	{
		return Compute.sub(paasFee, serviceFee);
	}

}
