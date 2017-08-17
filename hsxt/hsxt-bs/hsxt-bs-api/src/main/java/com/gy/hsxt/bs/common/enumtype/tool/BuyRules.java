/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 工具购买规则枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: BuyRules
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:37:37
 * @company: gyist
 * @version V3.0.0
 */
public enum BuyRules {

	/** 不可购买 **/
	NO_BUY(0),

	/** 仅成员企业可 **/
	B_BUY(1),

	/** 仅托管企业可购买 **/
	T_BUY(2),

	/** 托管和成员企业可购买 **/
	BT_BUY(3),

	/** 不限制购买 **/
	ALL(4);

	private int code;

	BuyRules(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
