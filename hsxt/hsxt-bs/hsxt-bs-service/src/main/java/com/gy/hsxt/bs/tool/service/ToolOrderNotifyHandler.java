/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.annotation.PaymentInfo;
import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyHandler;
import com.gy.hsxt.bs.order.service.OrderService;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具订单支付回调
 * 
 * @Package: com.gy.hsxt.bs.tool.service
 * @ClassName: ToolOrderNotifyHandler
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月16日 下午8:06:57
 * @company: gyist
 * @version V3.0.0
 */
@PaymentInfo(payChannel = { PayChannel.E_BANK_PAY, PayChannel.QUICK_PAY, PayChannel.MOBILE_PAY,
		PayChannel.TRANSFER_REMITTANCE, PayChannel.CARD_PAY}, orderType = { OrderType.BUY_TOOL, OrderType.AFTER_SERVICE,
				OrderType.REMAKE_MY_CARD, OrderType.REMAKE_BATCH_CARD, OrderType.CARD_STYLE_FEE,
				OrderType.APPLY_PERSON_RESOURCE })
public class ToolOrderNotifyHandler implements IPaymentNotifyHandler {

	/** 内部实现类 **/
	@Autowired
	private InsideInvokeCall insideInvokeCall;

	/** 订单Service **/
	@Autowired
	private OrderService orderService;

	@Override
	public boolean handle(PaymentNotify bean) throws HsException
	{

		try
		{
			// 支付成功
			if (PayStatus.PAY_FINISH.getCode() == bean.getPayStatus().getCode())
			{
				insideInvokeCall.toolEbankOrTempAcctPaySucc(bean.getOrder(), bean.getBankTransNo());
				// 非成功(支付中或失败)
			} else
			{
				// 订单实体
				Order order = bean.getOrder();
				if (PayStatus.PAY_FAIL.getCode() == bean.getPayStatus().getCode())
				{
					order.setPayStatus(PayStatus.PAY_FAIL.getCode());
					order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
				} else if (PayStatus.PROCESSING.getCode() == bean.getPayStatus().getCode())
				{
					order.setPayStatus(PayStatus.PROCESSING.getCode());
					order.setOrderStatus(OrderStatus.PROCESSING.getCode());
				}
				// 修改订单支付状态
				orderService.updateOrderAllStatus(order);
			}
		} catch (HsException ex)
		{
			// 网银(网银、快捷、手机)
			if (PayChannel.isGainPayAddress(bean.getPayChannel().getCode()))
			{
				return false;
			} else
			{// 临账
				throw ex;
			}
		}
		return true;
	}
}
