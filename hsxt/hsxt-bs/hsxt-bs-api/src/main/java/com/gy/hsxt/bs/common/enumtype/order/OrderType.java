/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.order;

import com.gy.hsxt.common.constant.GoodsName;

/**
 * 订单类型的枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.order
 * @ClassName: OrderStatus
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum OrderType
{

	/** 系统使用年费 **/
	ANNUAL_FEE("100"),

	/** 系统销售费订单 **/
	RES_FEE_ALLOT("101"),

	/** 兑换互生币 **/
	BUY_HSB("102"),

	/** 申购工具 **/
	BUY_TOOL("103"),

	/** 售后服务,即刷卡工具维修换货收费 **/
	AFTER_SERVICE("104"),

	/** 个人补卡 **/
	REMAKE_MY_CARD("105"),

	/** 企业重做卡 **/
	REMAKE_BATCH_CARD("106"),

	/** 定制卡样费用 **/
	CARD_STYLE_FEE("107"),

	/** 缴纳积分预付款，迁移2.0旧数据需要,3.0没有此类型订单 **/
	PREPAY_FOR_PV("108"),

	/** 申报申购工具 **/
	APPLY_BUY_TOOL("109"),

	/** 新增申购消费者系统资源 **/
	APPLY_PERSON_RESOURCE("110");

	private String code;

	OrderType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	/**
	 * 检查订单类型
	 * 
	 * @param orderType
	 *            订单类型
	 * @return true or false
	 */
	public static final boolean checkType(String orderType)
	{
		boolean success = false;
		for (OrderType type : OrderType.values())
		{
			if (type.getCode().equals(orderType))
			{
				success = true;
				break;
			}
		}
		return success;
	}

	/**
	 * 根据值查找对象
	 * 
	 * @param code
	 * @return
	 */
	public static OrderType getOrderType(String code)
	{
		OrderType[] orderTypeEnums = OrderType.values();
		for (OrderType ote : orderTypeEnums)
		{
			if (ote.getCode().equals(code))
			{
				return ote;
			}
		}
		return null;
	}

	/**
	 * 检查订单类型是否是工具订单类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月15日 上午9:45:29
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean checkIsToolOrder(String code)
	{
		switch (valueOf(getOrderType(code).name().toUpperCase()))
		{
		case APPLY_BUY_TOOL:
			return true;
		case BUY_TOOL:
			return true;
		case APPLY_PERSON_RESOURCE:
			return true;
		case AFTER_SERVICE:
			return true;
		case REMAKE_MY_CARD:
			return true;
		case REMAKE_BATCH_CARD:
			return true;
		case CARD_STYLE_FEE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 需要确认的订单类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 下午12:13:42
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isConfirmToolOrder(String code)
	{
		switch (valueOf(getOrderType(code).name().toUpperCase()))
		{
		case BUY_TOOL:
			return true;
		case APPLY_PERSON_RESOURCE:
			return true;
		case REMAKE_BATCH_CARD:
			return true;
		case REMAKE_MY_CARD:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 包含工具配置单的工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 下午2:26:45
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isHasConfigToolOrder(String code)
	{
		if (checkIsToolOrder(code))
		{
			switch (valueOf(getOrderType(code).name().toUpperCase()))
			{
			case CARD_STYLE_FEE:
				return false;
			default:
				return true;
			}
		}
		return false;
	}

	/**
	 * 可以关闭的工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月14日 上午11:28:41
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean mayCloseToolOrder(String code)
	{
		if (checkIsToolOrder(code))
		{
			switch (valueOf(getOrderType(code).name().toUpperCase()))
			{
			case APPLY_BUY_TOOL:
				return false;
			default:
				return true;
			}
		}
		return false;
	}

	/**
	 * 可以平台代购的订单类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月11日 下午8:03:41
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean mayPlatProxyOrderType(String code)
	{
		if (checkIsToolOrder(code))
		{
			switch (valueOf(getOrderType(code).name().toUpperCase()))
			{
			case APPLY_PERSON_RESOURCE:
				return true;
			case BUY_TOOL:
				return true;
			case CARD_STYLE_FEE:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据订单类型获取商品名称
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/7/1 10:02
	 * @param: code
	 * @eturn: String
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public static String getGoodNameByOrderType(String code)
	{
		switch (valueOf(getOrderType(code).name().toUpperCase()))
		{
			case ANNUAL_FEE:
				return GoodsName.ANNUAL_FEE.getCode();
			case RES_FEE_ALLOT:
				return GoodsName.RES_FEE_ALLOT.getCode();
			case BUY_HSB:
				return GoodsName.BUY_HSB.getCode();
			case BUY_TOOL:
				return GoodsName.BUY_TOOL.getCode();
			case AFTER_SERVICE:
				return GoodsName.AFTER_SERVICE.getCode();
			case REMAKE_MY_CARD:
				return GoodsName.REMAKE_MY_CARD.getCode();
			case REMAKE_BATCH_CARD:
				return GoodsName.REMAKE_BATCH_CARD.getCode();
			case CARD_STYLE_FEE:
				return GoodsName.CARD_STYLE_FEE.getCode();
			case APPLY_PERSON_RESOURCE:
				return GoodsName.APPLY_PERSON_RESOURCE.getCode();
			default:
				return null;
		}
	}
}
