/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 积分卡出入库状态枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: CardInOutFalg
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:37:52
 * @company: gyist
 * @version V3.0.0
 */
public enum CardInOutFalg {

	/** 入库 **/
	IN(1),

	/** 出库 **/
	OUT(2),

	/** 盘库 **/
	CHECK(3),

	/** 待入库 **/
	WAIT_IN(4);

	private int code;

	CardInOutFalg(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
