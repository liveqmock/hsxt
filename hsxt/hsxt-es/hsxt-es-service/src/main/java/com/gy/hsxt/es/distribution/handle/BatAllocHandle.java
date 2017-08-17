package com.gy.hsxt.es.distribution.handle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsTools;
import com.gy.hsxt.es.common.TransTypeUtil;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.PointAllot;
import com.gy.hsxt.es.points.bean.BackDetail;
import com.gy.hsxt.es.points.bean.CancellationDetail;
import com.gy.hsxt.es.points.bean.PointDetail;
import com.gy.hsxt.es.points.handle.PointHandle;

/**
 * Simple to Introduction
 * 
 * @description 组装积分
 * @author chenhongzhi
 * @createDate 2015-8-13 下午5:01:39
 * @updateUser chenhongzhi
 * @updateDate 2015-8-13 下午5:01:39
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class BatAllocHandle extends AllocItem
{

	private LinkedList<Alloc> list = new LinkedList<Alloc>();
	private List<PointAllot> pointList = new ArrayList<PointAllot>();
	/**
	 * 消费积分详细分配(平台)
	 * 
	 * @param pd
	 */
	private void insert(PointDetail pd)
	{
		BeanCopierUtils.copy(pd, this);
		this.init(pd.getEntPoint(), pd.getCurrencyRate());
		// this.init(transAmount, pd.getEntPoint());
		if(!EsTools.isEmpty(pd.getPerResNo()))
		{
		    this.initHsRes(this.perResNo);
		}
	}

	/**
	 * 退货详细分配(平台)
	 * 
	 * @param pd
	 */
	private void insert(BackDetail bd)
	{
		BeanCopierUtils.copy(bd, this);
		this.init(bd.getEntPoint(), bd.getCurrencyRate());
		// this.init(transAmount, new BigDecimal(bd.getBackPoint()));
		this.initHsRes(this.perResNo);
	}

	/**
	 * 撤单详细分配(平台)
	 * 
	 * @param pdd
	 */
	private void insert(CancellationDetail cd)
	{
		BeanCopierUtils.copy(cd, this);
		this.init(cd.getEntPoint(), cd.getCurrencyRate());
		// this.init(transAmount, cd.getEntPoint());
		this.initHsRes(this.perResNo);
	}

	/**
	 * 冲正详细分配(平台)
	 * 
	 * @param
	 */
	private void insertCorrectDetail(PointDetail pd)
	{
		BeanCopierUtils.copy(pd, this);
		this.init(pd.getEntPoint(), pd.getCurrencyRate());
		// this.init(transAmount, pd.getEntPoint());
		this.initHsRes(this.perResNo);
	}

	/**
	 * 获取积分分配的明细信息
	 * @return
	 */
	public List<PointAllot> getPointList()
	{
		return this.pointList;
	}
	/**
	 * 获取积分分配的明细信息
	 * @return
	 */
	public void getPointAllotData()
	{
		pointList.add(PointHandle.pointAllotData(list, businessType));
	}
	/**
	 * 获取积分分配撤单、退货的明细信息
	 * @return
	 */
	public PointAllot getPointAllotBackData()
	{
		return PointHandle.pointAllotData(list, businessType);
	}

	/**
	 * 装入消费积分
	 * @param pd
	 * 	  		积分明细信息
	 */
	public void insertPoint(PointDetail pd)
	{
		// 积分分配基本信息处理、积分计算处理
		this.insert(pd);

		// 获取装载积分
		this.addAllocPoint();
	}
	
    /**
     * 装入消费积分
     *
     * @param pd 积分明细信息
     */
    public void insertUpPoint(PointDetail pd) {
        // 积分分配基本信息处理、积分计算处理
        this.insert(pd);

        // 获取装载积分
        this.addAllocUpPoint();
    }

	/**
	 * 增向装载积分(托管公司、服务公司、管理公司、平台、结余)
	 */
	public void addAllocPoint()
	{
		// 托管公司
		list.add(this.getTrusteeAddPoint());

		// 服务公司
		list.add(this.getServiceAddPoint());

		// 管理公司
		list.add(this.getManageAddPoint());

		// 平台
		list.add(this.getAllPaasAddPoint());

		// 结余
		list.add(this.getResidueAddPoint());

	}
	
    /**
     * 增向装载积分(托管公司、服务公司、管理公司、平台、结余)
     */
    public void addAllocUpPoint() {
        if (StringUtils.isNotEmpty(this.trusteeResNo) && StringUtils.isNotEmpty(this.serviceResNo) && StringUtils.isNotEmpty(this.manageResNo)) {
            // 托管公司
            list.add(this.getTrusteeUpAddPoint());
            // 服务公司
            list.add(this.getServiceUpAddPoint());
            // 管理公司
            list.add(this.getManageUpAddPoint());
            // 平台
            list.add(this.getAllPaasUpAddPoint());
            // 结余
           // list.add(this.getResidueAddPoint());
        } else {
            list.add(getAllNoCardPaasAddPoint());
        }
    }
	
	/**
	 * 装入退货积分
	 */
	public void insertBack(BackDetail bd)
	{
		this.insert(bd);
		this.addBackList();
	}

	/**
	 * 装入撤单积分
	 */
	public void insertCancel(CancellationDetail cd)
	{
		this.insert(cd);
		this.addCancelList();
	}

	/**
	 * 装入冲正积分
	 */
	public void insertCorrect(PointDetail pd)
	{
		this.insertCorrectDetail(pd);
		this.addCorrectList();
	}

	public List<Alloc> getAllocList()
	{
		return this.list;
	}

	/**
	 * 获取所有的list
	 * 
	 * @throws HsException
	 */
	private void addList()
	{
		String type = TransTypeUtil.traitWay(transType);
		switch (type)
		{
		case Constants.POINT_LOCAL:
		case Constants.POINT_LOCAL4:
			this.addLoacl();
			break;
		case Constants.POINT_CARD_ALLOPATRY:
		case Constants.POINT_CARD_ALLOPATRY4:
			this.addLoaclCard();
			break;
		case Constants.POINT_ALLOPATRY_CARD:
		case Constants.POINT_LOCAL_NOT_CARD:
		case Constants.POINT_LOCAL_NOT_CARD4:
			this.addCardOrNot();
			break;
		default:
			break;
		}
	}

	/**
	 * 退货list
	 */
	private void addBackList()
	{
		String type = TransTypeUtil.traitWay(transType);
		switch (type)
		{
		case Constants.BACK_LOCAL:
			this.addSubLoacl();
			break;
		case Constants.BACK_CARD_ALLOPATRY:
			this.addSubLoaclCard();
			break;
		case Constants.BACK_ALLOPATRY_CARD:
			this.subCardOrNot();
			break;
		default:
			break;
		}
	}

	/**
	 * 撤单list
	 */
	private void addCancelList()
	{
		String type = TransTypeUtil.traitWay(transType);
		switch (type)
		{
		// 持卡人本地撤单
		case Constants.CANCEL_LOCAL:
			this.addSubLoacl();
			break;
		// 持卡人本地撤单
		case Constants.CANCEL_CARD_ALLOPATRY:
			this.addSubLoaclCard();
			break;
		// 异地持卡人本地撤单
		case Constants.CANCEL_ALLOPATRY_CARD:
			this.subCardOrNot();
			break;
		default:
			break;
		}
	}

	/**
	 * 冲正list
	 */
	private void addCorrectList()
	{
		String type = TransTypeUtil.traitWay(transType);
		switch (type)
		{
		// 持卡人本地撤单
		case Constants.CANCEL_LOCAL:
			this.addSubLoacl();
			break;
		// 持卡人本地撤单
		case Constants.CANCEL_CARD_ALLOPATRY:
			this.addSubLoaclCard();
			break;
		// 异地持卡人本地撤单
		case Constants.CANCEL_ALLOPATRY_CARD:
			this.subCardOrNot();
			break;
		default:
			break;
		}
	}

	/**
	 * 获取消费积分
	 */
	private void addLoacl()
	{
		this.addCardOrNot();
		this.addLoaclCard();
	}

	/**
	 * 获取退货、撤单积分
	 */
	private void addSubLoacl()
	{
		this.subCardOrNot();
		this.addSubLoaclCard();
	}

	/**
	 * 装载积分
	 */
	private void addLoaclCard()
	{
		list.add(this.getTrusteeAddPoint());
		list.add(this.getServiceAddPoint());
		list.add(this.getManageAddPoint());
	}

	/**
	 * 装载退货、撤单积分
	 */
	private void addSubLoaclCard()
	{
		list.add(this.getTrusteeSubPoint1());
		list.add(this.getServiceSubPoint1());
		list.add(this.getManageSubPoint1());
	}

	/**
	 * 退货、撤单 企业加向互生币 异地卡本地消费、非持卡人积分、非持卡人结单
	 */
	private void addCardOrNot()
	{
		// if (Constants.POINT_HSB1.equals(transWay) ||
		// Constants.POINT_CYBER.equals(transWay))
		// {
		// list.add(this.getEntAddHsb());
		// }
	}

	/**
	 * 积分 企业减向互生币
	 */
	private void subCardOrNot()
	{
		// if (Constants.POINT_HSB1.equals(transWay) ||
		// Constants.POINT_CYBER.equals(transWay))
		// {
		// list.add(this.getEntSubHsb());
		// }
	}

	/**
	 * 积分平台汇总分配
	 */
	public void addPaas()
	{
		list.add(this.getAllPaasPoint());
		list.add(this.getResidueAddPoint());

		// list.add(this.getPaasAddTax());
		// list.add(this.getPaasAddOpex());
		// list.add(this.getPaasAddSalvage());
		// list.add(this.getPaasAddInsurance());
		// list.add(this.getPaasAddIncrement());
		// list.add(this.getPaasAddReserve());
		// list.add(this.getPaasAddAccident());
	}

	/**
	 * 退货平台汇总分配
	 */
	public void addSubPaas()
	{
		list.add(this.getAllPaasPoint());
		list.add(this.getResidueSubPoint());

		// list.add(this.getPaasSubTax());
		// list.add(this.getPaasSubOpex());
		// list.add(this.getPaasSubSalvage());
		// list.add(this.getPaasSubInsurance());
		// list.add(this.getPaasSubIncrement());
		// list.add(this.getPaasSubReserve());
		// list.add(this.getPaasSubAccident());
	}

}
