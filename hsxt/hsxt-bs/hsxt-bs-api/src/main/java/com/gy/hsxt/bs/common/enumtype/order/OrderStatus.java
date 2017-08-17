/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.order;

import com.gy.hsxt.bs.common.util.EnumCheckUtil;

/**
 * 订单状态的枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.order
 * @ClassName: OrderStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum OrderStatus {

	/** 待付款 **/
	WAIT_PAY(1),
	/** 待配货 **/
	WAIT_CONFIG_GOODS(2),
	/** 已完成 **/
	IS_COMPLETE(3),
	/** 已过期 **/
	IS_EXPIRE(4),
	/** 已关闭 **/
	IS_CLOSED(5),
	/** 待确认 **/
	WAIT_CONFIRM(6),
	/** 已撤单 **/
	CANCELED(7),
	/** 支付处理中 **/
	PROCESSING(8),
	/** 已发货 **/
	SENDED(9);

	private int code;

	OrderStatus(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	/**
	 * 校验状态代码
	 * 
	 * @param code
	 *            代码
	 * @return boolean
	 */
	public static boolean checkStatus(int code)
	{
		for (OrderStatus orderStatus : OrderStatus.values())
		{
			if (orderStatus.getCode() == code)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查订单是否支付过
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 上午10:41:15
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean checkOrderIsPay(int code)
	{
		switch (valueOf(EnumCheckUtil.getEnumByCode(OrderStatus.class, code).name().toUpperCase()))
		{
		case WAIT_CONFIG_GOODS:
			return false;
		case IS_COMPLETE:
			return false;
		case CANCELED:
			return false;
		case WAIT_CONFIRM:
			return false;
		case SENDED:
			return false;
		default:
			return true;
		}
	}

	/**
	 * 检查订单是否失效
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 上午10:49:55
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean checkOrderIsInvalid(int code)
	{
		switch (valueOf(EnumCheckUtil.getEnumByCode(OrderStatus.class, code).name().toUpperCase()))
		{
		case IS_EXPIRE:
			return false;
		case IS_CLOSED:
			return false;
		case CANCELED:
			return false;
		default:
			return true;
		}
	}
}
