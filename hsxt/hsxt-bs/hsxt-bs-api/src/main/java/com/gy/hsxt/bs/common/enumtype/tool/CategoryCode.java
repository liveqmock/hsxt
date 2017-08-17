/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 工具代码枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: CategoryCode
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:38:17
 * @company: gyist
 * @version V3.0.0
 */
public enum CategoryCode {

	/** POS机 **/
	P_POS,

	/** 积分刷卡器 **/
	POINT_MCR,

	/** 消费刷卡器 **/
	CONSUME_MCR,

	/** 互生平板 **/
	TABLET,

	/** 互生卡 **/
	P_CARD,

	/** 普通工具 **/
	NORMAL,

	/** 赠品 **/
	GIFT,

	/** 配套工具 **/
	SUPPORT;

	public static CategoryCode getCode(String code)
	{
		return valueOf(code.toUpperCase());
	}

	/**
	 * 是否要生成设备信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月19日 上午11:50:52
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isCreateDeviceInfo(String code)
	{
		switch (valueOf(code.toUpperCase()))
		{
		case P_POS:
			return true;
		case POINT_MCR:
			return true;
		case TABLET:
			return true;
		case CONSUME_MCR:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 是否是刷卡器
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月19日 上午11:53:39
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isKsnCode(String code)
	{
		switch (valueOf(code.toUpperCase()))
		{
		case POINT_MCR:
			return true;
		case CONSUME_MCR:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 是否是灌秘钥类别
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月19日 上午11:54:57
	 * @param code
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isSecretKeyCode(String code)
	{
		switch (valueOf(code.toUpperCase()))
		{
		case P_POS:
			return true;
		case TABLET:
			return true;
		default:
			return false;
		}
	}
}
