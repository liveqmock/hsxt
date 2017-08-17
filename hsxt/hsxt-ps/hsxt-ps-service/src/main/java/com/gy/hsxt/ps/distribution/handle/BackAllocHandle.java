package com.gy.hsxt.ps.distribution.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.TransTypeUtil;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointRate;
import com.gy.hsxt.ps.points.bean.BackDetail;
import com.gy.hsxt.ps.points.bean.PointDetail;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.hsxt.ps.common.PointAlloc.java
 * @className PointAlloc
 * @description 退货积分分配
 * @author chenhongzhi
 * @createDate 2015-7-29 下午5:03:55
 * @updateUser liuchao
 * @updateDate 2015-7-29 下午5:03:55
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */

public class BackAllocHandle extends AllocItem
{
	private List<Alloc> list = new ArrayList<Alloc>();

	public BackAllocHandle()
	{

	}

	/**
     * 初始化 做运算处理 计算企业应付积分、消费者应得积分款、积分总额、待分配积分
     * 
     * @param transAmout
     * @throws HsException
     */
    public void init(BigDecimal transAmout, BigDecimal pointRate, BigDecimal currencyRate)
    {
        // 计算企业应付积分
        this.entPoint = Compute.mulFloor(transAmout, pointRate, Constants.SURPLUS_TWO);

        // 积分总额
//        this.pointSum = Compute.divFloor(entPoint, currencyRate, Constants.SURPLUS_TWO);
        this.pointSum = this.entPoint;

        // 消费者应得积分款
        this.perPoint = Compute.mulCeiling(pointSum, PointRate.perRate, Constants.SURPLUS_TWO);

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
     * 初始化 做运算处理 计算企业应付积分、消费者应得积分款、积分总额、待分配积分
     * 
     * @param transAmout
     * @throws HsException
     */
    public void init(PointDetail pointDetail)
    {
        // 计算企业应付积分
        this.entPoint = pointDetail.getEntPoint();

        // 积分总额
//        this.pointSum = Compute.divFloor(entPoint, currencyRate, Constants.SURPLUS_TWO);
        this.pointSum = this.entPoint;

        // 消费者应得积分款
        this.perPoint = pointDetail.getPerPoint();

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
	 * 初始化退货积分对象
	 * 
	 * @param bd
	 * @param pointDetail
	 * @throws HsException
	 */
	public BackAllocHandle(BackDetail bd, PointDetail pointDetail) throws HsException
	{
		BeanCopierUtils.copy(bd, this);
		this.setSourceTransDate(bd.getSourceTransDate());
		if(transAmount.compareTo(pointDetail.getTransAmount())==0)
		{
		    init(pointDetail);
		}
		else
		{
		    this.init(transAmount, bd.getPointRate(), bd.getCurrencyRate());
		}
		this.setTransAmount(bd.getTransAmount());
		bd.setPerPoint(this.getPerPoint());
		bd.setEntPoint(this.getEntPoint());
		this.relTransNo = pointDetail.getTransNo();
		this.relTransType = pointDetail.getTransType();
	}

	/**
	 * 获取当日退货组装List
	 *
	 * @param type
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> getCurBackList(String type) throws HsException
	{
		switch (type)
		{
		case Constants.BACK_LOCAL:
			this.getCurBack();
			break;
		case Constants.BACK_CARD_ALLOPATRY:
			this.getCurLoaclBack();
			break;
		case Constants.BACK_ALLOPATRY_CARD:
			this.getCurAllopatryBack();
			break;
		default:
			 PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
		}
		return list;
	}

	/**
	 * 获取非当日退货组装List
	 *
	 * @param type
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> getNotCurBackList(String type) throws HsException
	{
		switch (type)
		{
		case Constants.BACK_LOCAL:
			this.getNotCurBack();
			break;
		case Constants.BACK_CARD_ALLOPATRY:
			this.getNotCurLoaclBack();
			break;
		case Constants.BACK_ALLOPATRY_CARD:
			this.getNotCurAllopatryBack();
			break;
		default:
			 PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
		}
		return list;
	}

	/**
	 * 持卡人本地当日退货
	 * 
	 * @return
	 */
	private void getCurBack()
	{
		/*if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{*/
			list.add(this.getPerSubPoint());
/*		}*/
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
			//list.add(this.getPaasSubHsb());
			list.add(this.getEntBackSubHsb());
		}/* else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerAddHsb2());
			list.add(this.getPaasSubHsb());
		}*/
		list.add(this.getEntAddPoints());
	}

	/**
	 * 本地持卡人异地当日退货
	 * 
	 * @return
	 */
	private void getCurLoaclBack()
	{
		/*if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{*/
			list.add(this.getPerSubPoint());
	/*	}*/
		if (Constants.POINT_HSB.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}
		}

	/**
	 * 异地持卡人本地当日退货
	 * 
	 * @return
	 */
	private void getCurAllopatryBack()
	{
		/*if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{*/
			list.add(this.getEntAddPoints());
		/*}*/
	}

	/**
	 * 非当日退货
	 * 
	 * @return
	 */
	private void getNotCurBack()
	{
		/*if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{*/
			this.initHsRes(this.perResNo);
			// paasSumPoint =this.getOrgPoint(PointRate.paasRate);
			list.add(this.getPerSubPoint());
			list.add(this.getEntAddPoints());
			list.add(this.getTrusteeSubPoint());
//			list.add(this.getTrusteeSubTax());
			list.add(this.getServiceSubPoint());
//			list.add(this.getServiceSubTax());
			list.add(this.getManageSubPoint());
//			list.add(this.getManageSubTax());
			/*list.add(this.getPaasSubTax());
			list.add(this.getPaasSubOpex());
			list.add(this.getPaasSubInsurance());
			list.add(this.getPaasSubIncrement());
			list.add(this.getPaasSubAccident());
			list.add(this.getPaasSubReserve());
			list.add(this.getPaasSubSalvage());*/
			list.add(this.getResidueSubPoint());
// 			list.add(getTrusteeServiceSubTax());

		/*}*/
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}/* else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerAddHsb2());
		}*/
		list.add(this.getEntSubHsb());

	}

	/**
	 * 本地持卡人异地 非当日退货
	 * 
	 * @return
	 */
	private void getNotCurLoaclBack()
	{
		/*if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{*/
			this.initHsRes(this.perResNo);
			// paasSumPoint =this.getOrgPoint(PointRate.paasRate);
			list.add(this.getPerSubPoint());
			list.add(this.getTrusteeSubPoint());
			list.add(this.getTrusteeSubTax());
			list.add(this.getServiceSubPoint());
			list.add(this.getServiceSubTax());
			list.add(this.getManageSubPoint());
			list.add(this.getManageSubTax());
		/*	list.add(this.getPaasSubTax());
			list.add(this.getPaasSubOpex());
			list.add(this.getPaasSubIncrement());
			list.add(this.getPaasSubAccident());
			list.add(this.getPaasSubReserve());
			list.add(this.getPaasSubSalvage());*/
			list.add(this.getResidueSubPoint());
			list.add(getTrusteeServiceSubTax());
		/*}*/
		if (Constants.POINT_HSB.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		} /*else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerAddHsb2());
		}*/
	}

	/**
	 * 异地持卡人本地 非当日退货
	 * 
	 * @return
	 */
	private void getNotCurAllopatryBack()
	{
	/*	if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{*/
			list.add(this.getEntAddPoints());
		/*}*/
		list.add(this.getEntSubHsb());
	}
}
