/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tool;

import com.gy.hsxt.common.constant.PayChannel;

/**
 * 工具支付方式
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: ToolPayChannel
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月23日 上午11:47:41
 * @company: gyist
 * @version V3.0.0
 */
public enum ToolPayChannel {

	/** 网银支付 **/
	E_BANK_PAY,

	/** 互生币支付 **/
	HS_COIN_PAY,

	/** 临账支付 **/
	TRANSFER_REMITTANCE;

	/**
	 * 获取工具订单支付方式
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月23日 上午11:59:22
	 * @param code
	 * @return
	 * @return : ToolPayChannel
	 * @version V3.0.0
	 */
	public static ToolPayChannel getToolPayChannel(int code)
	{
		PayChannel channel = PayChannel.getPayChannel(code);
		switch (channel)
		{
		case E_BANK_PAY:
			return ToolPayChannel.E_BANK_PAY;
		case MOBILE_PAY:
			return ToolPayChannel.E_BANK_PAY;
		case QUICK_PAY:
			return ToolPayChannel.E_BANK_PAY;
		case HS_COIN_PAY:
			return ToolPayChannel.HS_COIN_PAY;
		case TRANSFER_REMITTANCE:
			return ToolPayChannel.TRANSFER_REMITTANCE;
		default:
			return null;
		}
	}
}
