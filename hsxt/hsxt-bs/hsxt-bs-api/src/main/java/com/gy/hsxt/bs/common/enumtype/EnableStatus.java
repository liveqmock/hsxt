/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype;

/**
 * 启用状态枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: EnableStatus
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:36:44
 * @company: gyist
 * @version V3.0.0
 */
public enum EnableStatus {

	/** 未启用 **/
	WAIT_ENABLE(0),

	/** 已启用 **/
	ENABLED(1),

	/** 已停用 **/
	STOP(2);

	private int code;

	EnableStatus(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
