package com.gy.hsxt.es.distribution.handle;

import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsException;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.points.bean.BackDetail;
import com.gy.hsxt.es.points.bean.PointDetail;

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
	 * 初始化退货积分对象
	 * 
	 * @param bd
	 * @param pointDetail
	 * @throws HsException
	 */
	public BackAllocHandle(BackDetail bd, PointDetail pointDetail) throws HsException
	{
		BeanCopierUtils.copy(bd, this);
		this.setTransAmount(bd.getTransAmount());
		this.setSourceTransDate(bd.getSourceTransDate());
	//	this.init(transAmount, bd.getPointRate(), bd.getCurrencyRate());
		this.init(bd.getEntPoint(), bd.getCurrencyRate());
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
		case Constants.BACK_LOCAL_DINGJ:
            this.getCurBack();
            break;
		case Constants.BACK_LOCAL_NOT_CARD:
    		this.getNotCardCurBack();
            break;
		case Constants.BACK_LOCAL_NOT_CARD_DINGJ:
            this.getNotCardCurBack();
            break;
		case Constants.BACK_CARD_ALLOPATRY:
			this.getCurLoaclBack();
			break;
		case Constants.BACK_ALLOPATRY_CARD:
			this.getCurAllopatryBack();
			break;
		default:
			new EsException(new Throwable().getStackTrace()[0], RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
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
		case Constants.BACK_LOCAL_DINGJ:
            this.getNotCurBack();
            break;
		case Constants.BACK_LOCAL_NOT_CARD:
            this.getNotCardNotCurBack();
            break;
		case Constants.BACK_CARD_ALLOPATRY:
			this.getNotCurLoaclBack();
			break;
		case Constants.BACK_ALLOPATRY_CARD:
			this.getNotCurAllopatryBack();
			break;
		default:
			new EsException(new Throwable().getStackTrace()[0], RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
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
		if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{
			list.add(this.getPerSubPoint());
		}
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
			//list.add(this.getPaasSubHsb());
			list.add(this.getEntSubHsb());//不能取平台的。2015年11月5号
		}
		list.add(this.getEntAddPoints());
	}
	
	/**
     * 非持卡人持卡人本地当日退货
     * 
     * @return
     */
    private void getNotCardCurBack()
    {
        if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
        {
            list.add(this.getNotCardAddHsb1());
            list.add(this.getEntSubHsb());
        }
        list.add(this.getEntAddPoints());
    }
    
    /**
     * 非持卡人持卡人本地当日退货
     * 
     * @return
     */
    private void getNotCardNotCurBack()
    {
        if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
        {
            list.add(this.getNotCardAddHsb1());
            list.add(this.getEntSubHsb20110());
        }
        list.add(this.getEntAddPoints());
    }

	/**
	 * 本地持卡人异地当日退货
	 * 
	 * @return
	 */
	private void getCurLoaclBack()
	{
		if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{
			list.add(this.getPerSubPoint());
		}
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
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
		if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{
			list.add(this.getEntAddPoints());
		}
	}

	/**
	 * 非当日退货
	 * 
	 * @return
	 */
	private void getNotCurBack()
	{
		if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{
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

		}
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}/* else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerAddHsb2());
		}*/
		list.add(this.getEntSubHsb20110());

	}

	/**
	 * 本地持卡人异地 非当日退货
	 * 
	 * @return
	 */
	private void getNotCurLoaclBack()
	{
		if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{
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
		}
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
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
		if (this.getPointSum().doubleValue() >= Constants.MIN_POINT)
		{
			list.add(this.getEntAddPoints());
		}
		list.add(this.getEntSubHsb20110());
	}
}
