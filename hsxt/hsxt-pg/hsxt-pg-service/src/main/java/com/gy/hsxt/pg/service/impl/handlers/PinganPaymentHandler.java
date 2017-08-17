/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.PinganB2cFacade;
import com.gy.hsxt.pg.bankadapter.pingan.b2c.params.PinganB2cPayParam;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganNetPayBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPayBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPayBindingBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPaySmsCodeBean;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.impl.check.BeanCheck;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl.handlers
 * 
 *  File Name       : ChinapayPaymentHandler.java
 * 
 *  Creation Date   : 2015-10-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : PG支付接口服务
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("pinganPaymentHandler")
public class PinganPaymentHandler extends AbstractPaymentHandler {

	@Autowired
	private PinganB2cFacade pinganB2cFacade;

	@Autowired
	private TPgBankPaymentOrderMapper chinapayOrderMapper;

	public PinganPaymentHandler() {
	}

	@Override
	protected String getChannelProvider() {
		return PGChannelProvider.PINGAN_PAY;
	}

	/**
	 * 获取网银支付url地址
	 * 
	 * @param netPayBean
	 * @return
	 * @throws Exception
	 */
	public String doGetPinganNetPayUrl(PGPinganNetPayBean netPayBean)
			throws Exception {
		// 校验请求参数是否有效
		BeanCheck.isvalid(netPayBean);

		// 判断订单号是否存在
		if (checkOrderExist(netPayBean.getBankOrderNo())) {
			throw new HsException(PGErrorCode.DATA_EXIST, "支付单已存在，请不要重复提交");
		}

		// 组装支付单数据保存至数据库
		{
			TPgBankPaymentOrder chinapayOrder = compateData(netPayBean);
			chinapayOrder.setPayChannel(PGPayChannel.B2C);

			chinapayOrderMapper.insert(chinapayOrder);
		}

		// 组装查询所用数据调用银行接口，获取URL
		{
			// 组装查询所用数据
			int payChannel = PGPayChannel.B2C;
			int merType = netPayBean.getMerType();
			int bizType = PGCallbackBizType.CALLBACK_PAY;

			PinganB2cPayParam params = new PinganB2cPayParam();
			params.setGoodsName(netPayBean.getGoodsName());
			params.setOrderDate(netPayBean.getTransDate());
			params.setOrderNo(netPayBean.getBankOrderNo());
			params.setRemark(netPayBean.getRemark());
			params.setPaymentUIType(netPayBean.getPaymentUIType());
			params.setPayAmount(MoneyAmountHelper.fromYuanToFen(netPayBean
					.getTransAmount()));
			params.setJumpURL(CallbackUrlHelper.getJumpUrl(channelProvider,
					payChannel, merType, bizType));
			params.setNotifyURL(CallbackUrlHelper.getNotifyUrl(channelProvider,
					payChannel, merType, bizType));

			// 调用银行接口获取URL
			return pinganB2cFacade.getB2cPaymentWorker().buildB2cPayUrl(params);
		}
	}
	
	/**
	 * 获取平安银行签约快捷支付URL接口(平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public String doGetPinganQuickPayBindingUrl(PGPinganQuickPayBindingBean bean)
			throws HsException {
		return null;
	}
	
	/**
	 * 获取平安银行快捷支付短信验证码接口(平安银行)
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public boolean doGetPinganQuickPaySmsCode(PGPinganQuickPaySmsCodeBean bean)
			throws HsException {
		return false;
	}
	
	/**
	 * 获取网银支付URL接口
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public String doGetPinganQuickPayUrl(PGPinganQuickPayBean bean)
			throws HsException {
		return null;
	}
}