/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype;

/**
 * 设备标示枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: DeviceFalg
 * @Description:
 * @author: likui
 * @date: 2016年2月27日 上午10:47:41
 * @company: gyist
 * @version V3.0.0
 */
public enum DeviceFalg {

	/** 存在 **/
	EXIST(1),

	/** 不存在 **/
	NOT_EXIST(2),

	/** 不是已使用状态 **/
	NOT_USED(3);

	private int code;

	DeviceFalg(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
