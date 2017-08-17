/**
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 * <p>
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: BuyStatus
 * @Description: 资源段购买状态枚举类
 * @author: likui
 * @date: 2016/6/12 12:21
 * @company: gyist
 * @version V3.0.0
 */
public enum BuyStatus
{
	/** 已购买 **/
	BOUGHT("1"),

	/** 已下单 **/
	ORDER("2"),

	/** 待购买 **/
	MAY_BUY("3");

	private String code;

	BuyStatus(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
