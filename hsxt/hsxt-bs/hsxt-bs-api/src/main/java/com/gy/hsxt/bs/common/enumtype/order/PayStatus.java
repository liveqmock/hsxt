/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.order;

/**
 * 订单支付状态的枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.order
 * @ClassName: OrderStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum PayStatus {

	/** 待支付 **/
	WAIT_PAY(0),
	/** 处理中 **/
	PROCESSING(1),
	/** 已付款 **/
	PAY_FINISH(2),
	/** 付款失败 **/
	PAY_FAIL(3);

	private int code;

	PayStatus(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	/**
	 * 校验支付状态代码
	 * 
	 * @param code
	 *            代码
	 * @return boolean
	 */
	public static boolean checkStatus(int code)
	{
		for (PayStatus payStatus : PayStatus.values())
		{
			if (payStatus.getCode() == code)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据值查找对象
	 * 
	 * @param code
	 * @return
	 */
	public static PayStatus getPayStatus(int code)
	{
		PayStatus[] psEnums = PayStatus.values();
		for (PayStatus pe : psEnums)
		{
			if (pe.getCode() == code)
			{
				return pe;
			}
		}
		return null;
	}
}
