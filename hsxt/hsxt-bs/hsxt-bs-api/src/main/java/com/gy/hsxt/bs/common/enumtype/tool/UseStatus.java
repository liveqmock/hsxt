/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tool;

import com.gy.hsxt.bs.common.util.EnumCheckUtil;

/**
 * 设备使用状态
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: UseStatus
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月26日 上午11:55:56
 * @company: gyist
 * @version V3.0.0
 */
public enum UseStatus {

	/** 未使用 **/
	NOT_USED(0),

	/** 已使用 **/
	USED(1),

	/** 已报损 **/
	REPORTED_LOSS(2),

	/** 已领用 **/
	BEEN_USED(3),

	/** 已返修 **/
	BEEN_REPAIRED(4),

	/** 已报废 **/
	BEEN_REJECT(5);

	private int code;

	UseStatus(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	/**
	 * 根据值获取枚举项
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月19日 下午12:09:34
	 * @param code
	 * @return
	 * @return : UseStatus
	 * @version V3.0.0
	 */
	public static UseStatus getUseStatus(int code)
	{
		return valueOf(EnumCheckUtil.getEnumByCode(UseStatus.class, code).name().toUpperCase());
	}
}
