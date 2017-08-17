/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 配置状态枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: ConfStatus
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:38:29
 * @company: gyist
 * @version V3.0.0
 */
public enum ConfStatus {

	/** 待付款 **/
	WAIT_PAY(0),

	/** 待确认 **/
	WAIT_CONFIRM(1),

	/** 待配置 **/
	WAIT_CONFIG(2),

	/** 待制作 **/
	WAIT_MARK(3),

	/** 待入库 **/
	WAIT_ENTER(4),

	/** 待发货 **/
	WAIT_SEND(5),

	/** 已发货 **/
	SENDED(6),

	/** 已签收 **/
	SIGNED(7),

	/** 已取消 **/
	CANCELED(8);

	private int code;

	ConfStatus(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
