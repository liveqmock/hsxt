/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.queryReport.testcase;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: TransType
 * @Description: TODO
 * 
 * @author: chenhz
 * @date: 2016-1-13 上午11:36:18
 * @version V3.0
 */
public class TransType
{
	/** 网上订单消费积分账户(积分账户) **/
	public static final String POINT_ONLINE = "10";

	/** 网上订单互生币支付(互生币账户) **/
	public static final String HSB_ONLINE = "10";
	
	/** 线下订单消费积分(积分账户) **/
	public static final String POINT_OFFLINE = "20";

	/** 线下订单互生币支付(互生币账户) **/
	public static final String HSB_OFFLINE = "20";

	/** 网上订单支付撤单 **/
	public static final String CANCEL_ONLINE = "11";

	/** 网上订单支付退货 **/
	public static final String BACK_ONLINE = "12";
	
	/** 线下订单撤单 **/
	public static final String CANCEL_OFFLINE = "21";

	/** 线下订单退货 **/
	public static final String BACK_OFFLINE = "22";

	/**
	 * 消费积分交易类型(积分账户)
	 * 
	 * @param transStatus
	 * @return
	 */
	public static String getPointTransType(String transType)
	{
		String transStatus = getTransStatus(transType);
		switch (transStatus)
		{
		case TransType.POINT_ONLINE:
			return "网上订单消费积分";
		case TransType.POINT_OFFLINE:
			return "线下订单消费积分";
		case TransType.CANCEL_ONLINE:
			return "网上消费积分撤单";
		case TransType.BACK_ONLINE:
			return "网上消费积分撤单";
		case TransType.CANCEL_OFFLINE:
			return "线下订单消费积分撤单";
		case TransType.BACK_OFFLINE:
			return "线下订单消费积分撤单";
		default:
			return "未知交易类型";
		}
	}

	/**
	 * 互生币交易类型(互生币账户)
	 * 
	 * @param transStatus
	 * @return
	 */
	public static String getHsbTransType(String transType)
	{
		String transStatus = getTransStatus(transType);
		switch (transStatus)
		{
		case TransType.HSB_ONLINE:
			return "网上订单互生币支付";
		case TransType.HSB_OFFLINE:
			return "线下订单互生币支付";
		case TransType.CANCEL_ONLINE:
			return "网上订单支付撤单";
		case TransType.BACK_ONLINE:
			return "网上订单支付退货";
		case TransType.CANCEL_OFFLINE:
			return "线下订单互生币支付撤单";
		case TransType.BACK_OFFLINE:
			return "线下订单互生币支付退货";
		default:
			throw new HsException(RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
		}
	}
	
	/**
	 * 获取订单状态
	 * 
	 * @param transType
	 * @return
	 */
	public static String getTransStatus(String transType)
	{
		String[] str = transType.split("");
		return str[2] + str[4];
	}
	
	public static void main(String[] args)
	{
		String tt = TransType.getHsbTransType("A11200");
		String ttp = TransType.getPointTransType("A21100");
		System.out.println(tt);
		System.out.println(ttp);
	}

}
