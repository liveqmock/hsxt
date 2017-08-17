/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.bean.enumtype;

/**
 * 企业兑换互生币限额枚举类型
 * 
 * @Package: com.gy.hsxt.access.web.bean.enumtype
 * @ClassName: EntBuyHsbLimit
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月22日 下午12:07:20
 * @company: gyist
 * @version V3.0.0
 */
public enum EntBuyHsbLimit
{

	/** 单笔互生币汇兑最小限额 */
	SINGLE_BUY_HSB_MIN("S_SINGLE_BUY_HSB_MIN"),

	/** 单笔兑换互生币最大限额 */
	SINGLE_BUY_HSB_MAX("S_SINGLE_BUY_HSB_MAX"),

	/** 兑换互生币单日限额 */
	SINGLE_DAY_BUY_HSB_MAX("S_SINGLE_DAY_BUY_HSB_MAX"),

	/** 互生币单日已兑换数量 */
	DAY_BOUGHT_HSB_COUNT("DAY_BOUGHT_HSB_COUNT");

	private String code;

	EntBuyHsbLimit(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
