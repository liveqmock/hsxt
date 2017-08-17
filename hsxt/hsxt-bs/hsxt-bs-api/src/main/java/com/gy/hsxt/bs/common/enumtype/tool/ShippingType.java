/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 发货单类别枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: ShippingType
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:39:38
 * @company: gyist
 * @version V3.0.0
 */
public enum ShippingType {

	/** 申报发货 **/
	APPLY_TOOL(1),

	/** 新增发货 **/
	BUY_TOOL(2),

	/** 售后发货 **/
	AFTER_TOOL(3);

	private int code;

	ShippingType(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
