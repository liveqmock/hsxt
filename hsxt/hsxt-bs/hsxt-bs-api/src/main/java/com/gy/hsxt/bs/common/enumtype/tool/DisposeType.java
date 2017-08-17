/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

import com.gy.hsxt.bs.common.util.EnumCheckUtil;

/**
 * 处理方式枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: DisposeType
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:39:22
 * @company: gyist
 * @version V3.0.0
 */
public enum DisposeType {

	/** 待处理 **/
	WAIT_DISPOSE(0),

	/** 无故障 **/
	NO_DISPOSE(1),

	/** 重新配置 **/
	ANEW_CONFIG(2),

	/** 换货 **/
	BARTER(3),

	/** 维修 **/
	SERVICING(4);

	private int code;

	DisposeType(int code)
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
	public static DisposeType getDisposeType(int code)
	{
		return valueOf(EnumCheckUtil.getEnumByCode(DisposeType.class, code).name().toUpperCase());
	}
}
