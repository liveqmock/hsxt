/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 库存状态
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: StoreStatus
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 上午10:25:19
 * @company: gyist
 * @version V3.0.0
 */
public enum StoreStatus {

	/** 待入库 **/
	WAIT_ENTER(0),

	/** 已入库 **/
	ENTERED(1),

	/** 已出库 **/
	OUTED(2);

	private int code;

	StoreStatus(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
