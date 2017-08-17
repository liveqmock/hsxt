package com.gy.hsxt.ps.distribution.handle;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.points.bean.CancellationDetail;
import com.gy.hsxt.ps.points.bean.PointDetail;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.hsxt.ps.common.PointAlloc.java
 * @className PointAlloc
 * @description 撤单分配
 * @author chenhongzhi
 * @createDate 2015-7-29 下午5:03:55
 * @updateUser liuchao
 * @updateDate 2015-7-29 下午5:03:55
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */

public class CancelAllocHandle extends AllocItem
{

	private List<Alloc> list = new ArrayList<Alloc>();

	/**
	 * 初始化撤单积分对象
	 * 
	 * @param cd
	 * @param pointDetail
	 * @throws HsException
	 */
	public CancelAllocHandle(CancellationDetail cd, PointDetail pointDetail) throws HsException
	{
		BeanCopierUtils.copy(cd, this);
		if (StringUtils.isNotEmpty(cd.getSourceCurrencyCode()))
		{
			this.setSourceTransDate(cd.getSourceTransDate());
		}

		this.init(transAmount, cd.getPointRate(), cd.getCurrencyRate());
		cd.setPerPoint(this.getPerPoint());
		cd.setSourceBatchNo(pointDetail.getSourceBatchNo());
		this.relTransNo = pointDetail.getTransNo();
		this.relTransType = pointDetail.getTransType();
	}

	/**
	 * 当天撤单组装List
	 *
	 * @param type
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> getCurCancelList(String type) throws HsException
	{
		switch (type)
		{
		case Constants.CANCEL_LOCAL:
			this.getCurCancel();
			break;
		case Constants.CANCEL_LOCAL5:
			this.getCardCancel5();
			break;
		case Constants.CANCEL_CARD_ALLOPATRY:
			this.getCurLoaclCancel();
			break;
		case Constants.CANCEL_CARD_ALLOPATRY5:
			this.getLocalCardCancel5();
			break;
		case Constants.CANCEL_ALLOPATRY_CARD:
			this.getCurAllopatryCancel();
			break;
		case Constants.CANCEL_ALLOPATRY_CARD5:
			this.getAllopatryCardCancel5();
			break;
		case Constants.CANCEL_LOCAL_NOT_CARD:
			this.getNotCardCancel();
			break;
		case Constants.CANCEL_LOCAL_NOT_CARD5:
			this.getNotCardCancel5();
			break;
		default:
			 PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
		}
		//生成流水号
		for (Alloc alloc : list) {
			alloc.setEntryNo(GuidAgent.getStringGuid(Constants.TRANS_NO_POINT10 + PsTools.getInstanceNo()));
		}
		return this.list;
	}

	/**
	 * 非当天撤单组装List
	 *
	 * @param type
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> getNotCurCancelList(String type) throws HsException
	{
		switch (type)
		{
		case Constants.CANCEL_LOCAL:
			this.getNotCurCancel();
			break;
		case Constants.CANCEL_LOCAL5:
			this.getCardCancel5();
			break;
		case Constants.CANCEL_CARD_ALLOPATRY:
			this.getNotCurLoaclCancel();
			break;
		case Constants.CANCEL_CARD_ALLOPATRY5:
			this.getLocalCardCancel5();
			break;
		case Constants.CANCEL_ALLOPATRY_CARD:
			this.getNotCurAllopatryCancel();
			break;
		case Constants.CANCEL_ALLOPATRY_CARD5:
			this.getAllopatryCardCancel5();
			break;
		case Constants.CANCEL_LOCAL_NOT_CARD:
			this.getNotCurNotCardCancel();
			break;
		case Constants.CANCEL_LOCAL_NOT_CARD5:
			this.getNotCardCancel5();
			break;
		default:
			 PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
		}
		//生成流水号
		for (Alloc alloc : list) {
			alloc.setEntryNo(GuidAgent.getStringGuid(Constants.TRANS_NO_POINT10 + PsTools.getInstanceNo()));
		}
		return this.list;
	}

	/**
	 * 持卡人本地当日撤单
	 * 
	 * @return
	 */
	private void getCurCancel()
	{
		list.add(this.getPerSubPoint());
		list.add(this.getEntAddPoints());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}/* else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerAddHsb2());
		}*/

	}

	/**
	 * 本地持卡人异地当日撤单
	 * 
	 * @return
	 */
	private void getCurLoaclCancel()
	{
		list.add(this.getPerSubPoint());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		} /*else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerAddHsb2());
		}*/

	}

	/**
	 * 异地持卡人本地当日撤单
	 * 
	 * @return
	 */
	private void getCurAllopatryCancel()
	{
		list.add(this.getEntAddPoints());

	}

	/**
	 * 非持卡人日终前撤单
	 * 
	 * @return
	 */
	private void getNotCardCancel()
	{
		list.add(this.getEntAddPoints());
		list.add(this.getPaasSedimentSubPoint());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}
	}

	/**
	 * 非当日撤单
	 * 
	 * @return
	 */
	private void getNotCurCancel()
	{
		this.initHsRes(this.perResNo);
		// paasSumPoint =this.getOrgPoint(PointRate.paasRate);
		list.add(this.getPerSubPoint());
		list.add(this.getEntAddPoints());
		list.add(this.getTrusteeSubPoint());
		list.add(this.getServiceSubPoint());
		list.add(this.getManageSubPoint());
		list.add(this.getTrusteeSubTax());
		list.add(this.getServiceSubTax());
		list.add(this.getManageSubTax());
	/*	list.add(this.getPaasSubTax());
		list.add(this.getPaasSubOpex());
		list.add(this.getPaasSubInsurance());
		list.add(this.getPaasSubIncrement());
		list.add(this.getPaasSubAccident());
		list.add(this.getPaasSubReserve());
		list.add(this.getPaasSubSalvage());*/
		list.add(this.getResidueSubPoint());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getEntSubHsb());
			list.add(this.getPerAddHsb1());
		}

	}

	/**
	 * 本地持卡人异地 非当日退货
	 * 
	 * @return
	 */
	private void getNotCurLoaclCancel()
	{
		this.initHsRes(this.perResNo);
		// paasSumPoint =this.getOrgPoint(PointRate.paasRate);
		list.add(this.getPerSubPoint());
		list.add(this.getTrusteeSubPoint());
		list.add(this.getServiceSubPoint());
		list.add(this.getManageSubPoint());
		list.add(this.getTrusteeSubTax());
		list.add(this.getServiceSubTax());
		list.add(this.getManageSubTax());
	/*	list.add(this.getPaasSubTax());
		list.add(this.getPaasSubOpex());
		list.add(this.getPaasSubIncrement());
		list.add(this.getPaasSubAccident());
		list.add(this.getPaasSubReserve());
		list.add(this.getPaasSubSalvage());*/
		list.add(this.getResidueSubPoint());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}/* else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerAddHsb2());
		}*/

	}

	/**
	 * 异地持卡人本地 非当日撤单
	 * 
	 * @return
	 */
	private void getNotCurAllopatryCancel()
	{
		list.add(this.getEntAddPoints());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getEntSubHsb());
		}
	}

	/**
	 * 非持卡人消费积分撤单 非当日撤单
	 * 
	 * @return
	 */
	private void getNotCurNotCardCancel()
	{
		list.add(this.getEntAddPoints());
		list.add(this.getPaasSedimentSubPoint());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getEntSubHsb());
			list.add(this.getPerAddHsb1());
		}

	}

	/**
	 *  消费积分预留撤单交易
	 * 
	 * @return
	 */
	private void getCardCancel5()
	{
		/*list.add(this.getEntSubPointsByRes());*/
		list.add(this.getEntAddPoints());
		if (Constants.POINT_HSB.equals(transWay))
		{
/*			list.add(this.getPerSubHsbByRes1());*/
			list.add(this.getPerAddHsb1());
		}/* else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerSubHsbByRes2());
			list.add(this.getPerAddHsb2());
		}*/ else if (Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}

	}

	/**
	 *  本地卡异地预留消费撤单
	 * 
	 * @return
	 */
	private void getLocalCardCancel5()
	{
		if (Constants.POINT_HSB.equals(transWay))
		{
			/*list.add(this.getPerSubHsbByRes1());*/
			list.add(this.getPerAddHsb1());
		} /*else if (Constants.POINT_HSB2.equals(transWay))
		{
			list.add(this.getPerSubHsbByRes2());
			list.add(this.getPerAddHsb2());
		} */else if (Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}
	}

	/**
	 *  异地卡本地预留消费撤单
	 * 
	 * @return
	 */
	private void getAllopatryCardCancel5()
	{
		/*list.add(this.getEntSubPointsByRes());*/
		list.add(this.getEntAddPoints());
	}

	/**
	 *  非持卡人预留消费撤单
	 * 
	 * @return
	 */
	private void getNotCardCancel5()
	{
		list.add(this.getEntAddPoints());
/*		list.add(this.getEntSubPointsByRes());*/
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			list.add(this.getPerAddHsb1());
		}
	}
}
