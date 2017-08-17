/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 互生卡样状态枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: CardStyleApprStatus
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:38:03
 * @company: gyist
 * @version V3.0.0
 */
public enum CardStyleApprStatus {

	/** 申请启用 **/
	APP_ENABLE(0),

	/** 启用 **/
	ENABLE(1),

	/** 拒绝启用 **/
	NOT_ENABLE(2),

	/** 申请停用 **/
	APP_STOP(3),

	/** 停用 **/
	STOP(4),

	/** 拒绝停用 **/
	NOT_STOP(5);

	private int code;

	CardStyleApprStatus(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
