/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype;

/**
 * 设备类型枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: DeviceType
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月23日 上午11:27:30
 * @company: gyist
 * @version V3.0.0
 */
public enum DeviceType {

	/** POS机 **/
	P_POS("1"),

	/** 刷卡器 **/
	MCR("2"),

	/** 平板 **/
	TABLET("3");

	private String code;

	DeviceType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
