/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.distribution.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsException;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.points.bean.PointDetail;

/**
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: PointAllocHandle
 * @Description: 积分分配
 * 
 * @author: chenhongzhi
 * @date: 2015-12-3 上午10:51:29
 * @version V3.0
 */

public class PointAllocHandle extends AllocItem
{

	// 创建存储分配的积分信息list
	private List<Alloc> list = new ArrayList<Alloc>();

	public PointAllocHandle()
	{
	}

	/**
	 * 初始化积分对象
	 * 
	 * @param pd
	 *            积分明细对象信息
	 * @throws HsException
	 */
	public PointAllocHandle(PointDetail pd) throws HsException
	{
		// 把积分明细信息拷贝到PointItem实体中
		BeanCopierUtils.copy(pd, this);

		// 调用AllocItem对象的初始化方法计算积分,传入金额、货币转换率
		this.init(pd.getEntPoint(), pd.getCurrencyRate());

		// 判断积分是否小于积分最小值
		if (this.getPointSum().compareTo(BigDecimal.ZERO)==1) {
			// 设置消费者的积分
			pd.setPerPoint(this.getPerPoint());

			// 设置企业的应付积分款
			pd.setEntPoint(this.getEntPoint());
		}

		/*// 判断积分是否小于积分最小值
		if (this.getPointSum().doubleValue() < Constants.MIN_POINT)
		{
			// 积分小于最小值则抛出互生异常
			new EsException(new Throwable().getStackTrace()[0], RespCode.PS_POINT_NO_MIN.getCode(),
					"积分小于最小值");
		}*/
	}

	/**
	 * 获取积分服务组装List
	 * 
	 * @param type
	 *            交易类型
	 * @return 返回积分分配list
	 * @throws HsException
	 */
	public List<Alloc> getList(String type) throws HsException
	{
		switch (type)
		{
		// 持卡人本地消费
		case Constants.POINT_LOCAL:
			this.getLocal();
			break;

		// 持卡人本地消费 预留
		case Constants.POINT_LOCAL3:
			this.getLocal3();
			break;

		// 持卡人本地退货(退货预留)
		case Constants.BACK_LOCAL_WITHHOLD5:
			this.getBackLocalback5();
			break;

		// 持卡人本地消费 预留结单
		case Constants.POINT_LOCAL4:
			this.getLocal4();
			break;

		// 非持卡人本地消费
		case Constants.POINT_LOCAL_NOT_CARD:
			this.getNotCard();
			break;

		// 非持卡人本地消费 预留
		case Constants.POINT_LOCAL_NOT_CARD3:
			this.getNotCard3();
			break;

		// 非持卡人本地消费 预留结单
		case Constants.POINT_LOCAL_NOT_CARD4:
			this.getNotCard4();
			break;

		// 本地持卡人异地消费
		case Constants.POINT_CARD_ALLOPATRY:
			this.getLocalCard();
			break;

		// 本地持卡人异地消费 预留
		case Constants.POINT_CARD_ALLOPATRY3:
			this.getLocalCard3();
			break;

		// 本地持卡人异地退货 退货预留
		case Constants.BACK_CARD_ALLOPATRY_WITHHOLD5:
			this.getBackLocalCard5();
			break;

		// 本地持卡人异地消费 预留结单
		case Constants.POINT_CARD_ALLOPATRY4:
			this.getLocalCard4();
			break;

		// 异地持卡人本地消费
		case Constants.POINT_ALLOPATRY_CARD:
			this.getAllopatryCard();
			break;

		// 异地持卡人本地消费 预留
		case Constants.POINT_ALLOPATRY_CARD3:
			this.getAllopatryCard3();
			break;

		// 异地持卡人本地退货 退货预留
		case Constants.BACK_ALLOPATRY_CARD_WITHHOLD5:
			this.getBackAllopatryCard5();
			break;

		// 异地持卡人本地消费 预留结单
		case Constants.POINT_ALLOPATRY_CARD4:
			this.getAllopatryCard4();
			break;
		default:

			// 如果交易类型不匹配,则抛出互生异常
			new EsException(new Throwable().getStackTrace()[0],
					RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
		}
		// 返回
		return list;
	}

	public List<Alloc> getSettleList() throws HsException
	{
		return this.list;
	}

	/**
	 * 持卡人本地消费积分
	 * 
	 * @return
	 */
	private void getLocal()
	{
		// 扣企业积分款
		list.add(this.getEntSubPoints());

		// 消费者增加积分
		list.add(this.getPerAddPoint());

		// 判断交易方式如果是互生币
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			// 扣除消费者互生币(货款)
			list.add(this.getPerSubHsb1());

			// 企业增向互生币暂存(消费积分专用待清算临时账户)
			list.add(this.getEntAddHsb());
		}
	}

	/**
	 * 持卡人本地消费积分 预留
	 * 
	 * @return
	 */
	private void getLocal3()
	{
		// 扣企业积分款
		list.add(this.getEntSubPoints());

		// 判断交易方式如果是互生币或者网银
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			// 扣消费者互生币货款
			list.add(this.getPerSubHsb1());
		}
	}

	/**
	 * 持卡人本地消费积分 结单
	 * 
	 * @return
	 */
	private void getLocal4()
	{
		// 扣企业积分款
		list.add(this.getEntSubPoints());
		// 消费者积分款加
		list.add(this.getPerAddPoint());
		// 判断交易方式如果是互生币
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			// 企业增向互生币暂存(消费积分专用待清算临时账户)
			list.add(this.getEntAddHsb());
		}
	}

	/**
	 * 非持卡人消费积分
	 * 
	 * @return
	 */
	private void getNotCard()
	{
		// 扣除企业积分款
		list.add(this.getEntSubPoints());

		// 判断交易方式如果是互生币
        if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
        {
            // 扣除扣除非持卡人的流通币互生币(货款)
            list.add(this.getNotCardSubHsb1());

            // 企业增向互生币暂存(消费积分专用待清算临时账户)
            list.add(this.getEntAddHsb());
        }
		
//		// 非持卡人积分存入平台沉淀积分
//		list.add(this.getPaasSedimentAddPoint());

	}

	/**
	 * 非持卡人消费积分 预留
	 * 
	 * @return
	 */
	private void getNotCard3()
	{
		// 扣企业积分款
		list.add(this.getEntSubPoints());
		// 判断交易方式如果是互生币或者网银
        if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
        {
            // 扣消费者互生币货款
            list.add(this.getNotCardSubHsb1());
        }
		/* list.add(this.getEntAddPointsByRes()); */

	}

	/**
	 * 非持卡人消费积分 预留结单
	 * 
	 * @return
	 */
	private void getNotCard4()
	{
	    // 扣企业积分款
        list.add(this.getEntSubPoints());
        // 判断交易方式如果是互生币
        if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
        {
            // 企业增向互生币暂存(消费积分专用待清算临时账户)
            list.add(this.getEntAddHsb());
        }
	}

	/**
	 * 本地卡异地消费
	 * 
	 * @return
	 */
	private void getLocalCard()
	{
		// 消费者积分款加
		list.add(this.getPerAddPoint());

		// 判断交易方式如果是互生币
		if (Constants.POINT_HSB.equals(transWay))
		{
			// 扣消费者互生币货款
			list.add(this.getPerSubHsb1());
			
			// 企业增向互生币暂存(消费积分专用待清算临时账户)
			list.add(this.getEntAddHsb());
		}

	}

	/**
	 * 本地卡异地消费 预留
	 * 
	 * @return
	 */
	private void getLocalCard3()
	{
		// 判断交易方式如果是互生币
		if (Constants.POINT_HSB.equals(transWay))
		{
			// 扣消费者互生币货款
			list.add(this.getPerSubHsb1());
		}
	}

	/**
	 * 本地卡异地消费 结单
	 * 
	 * @return
	 */
	private void getLocalCard4()
	{
		// 消费者积分款加
		list.add(this.getPerAddPoint());
	}

	/**
	 * 异地卡本地消费
	 * 
	 * @return
	 */
	private void getAllopatryCard()
	{
		// 扣除企业积分款
		list.add(this.getEntSubPoints());
	}

	/**
	 * 异地卡本地消费 预留
	 * 
	 * @return
	 */
	private void getAllopatryCard3()
	{
		// 扣除企业积分款
		list.add(this.getEntSubPoints());
	}

	/**
	 * 异地卡本地消费 结单
	 * 
	 * @return
	 */
	private void getAllopatryCard4()
	{
		// 建议开通国际结算中心相关账务存储账户
		// list.add(this.getEntSubPointsByRes());
	}
	
	/************************************* 退货预扣款  *******************************/
	/**
	 * 持卡人本地退货预扣
	 */
	public void getBackLocalback5()
	{
		// 获取消费者减向积分
		list.add(this.getPerSubPoint());
		if (Constants.POINT_HSB.equals(transWay) || Constants.POINT_CYBER.equals(transWay))
		{
			// 获取企业减向互生币
			list.add(this.getEntSubHsb20110());
		}
	}

	/**
	 * 本地持卡人异地退货预扣
	 */
	public void getBackLocalCard5()
	{
		list.add(this.getPerSubPoint());
	}

	/**
	 * 异地持卡人本地退货预扣
	 */
	public void getBackAllopatryCard5()
	{
		list.add(this.getEntSubHsb20110());
	}

	/**
	 * 持卡人本地消费积分日终结算
	 * 
	 * @return
	 */
	public void getDayEndSettle()
	{
		list.add(this.getEntAddHsb());
	}

}
