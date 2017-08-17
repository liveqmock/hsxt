/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 工具服务枚举类型
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: ToolService
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月22日 上午10:00:08
 * @company: gyist
 * @version V3.0.0
 */
public enum ToolService {

	/** 刷卡工具 **/
	SWIPE_CARD,

	/** 卡工具 **/
	CARD,

	/** 免费工具 **/
	FREE,

	/** 普通工具 **/
	NORMAL;

	/**
	 * 根据工具服务获取工具类别代码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月22日 上午10:18:05
	 * @param code
	 * @return
	 * @return : String[]
	 * @version V3.0.0
	 */
	public static String[] getToolCategoryCode(String code)
	{
		switch (valueOf(code.toUpperCase()))
		{
		case SWIPE_CARD:
			return new String[] { CategoryCode.P_POS.name(), CategoryCode.CONSUME_MCR.name(),
					CategoryCode.POINT_MCR.name(), CategoryCode.TABLET.name() };
		case CARD:
			return new String[] { CategoryCode.P_CARD.name() };
		case FREE:
			return new String[] { CategoryCode.GIFT.name(), CategoryCode.SUPPORT.name() };
		case NORMAL:
			return new String[] { CategoryCode.NORMAL.name() };
		default:
			return new String[] {};
		}
	}
}
