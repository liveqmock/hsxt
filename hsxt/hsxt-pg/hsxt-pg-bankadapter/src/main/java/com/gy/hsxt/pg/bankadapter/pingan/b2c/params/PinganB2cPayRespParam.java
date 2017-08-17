/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2c.params;

import com.ecc.emp.data.InvalidArgumentException;
import com.ecc.emp.data.KeyedCollection;
import com.ecc.emp.data.ObjectNotFoundException;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2c.params
 * 
 *  File Name       : PinganB2cPayRespParam.java
 * 
 *  Creation Date   : 2016年6月27日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行B2C网银支付响应对象封装
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganB2cPayRespParam {

	private KeyedCollection output;

	// 必填 商户订单号
	private String orderNo;

	// 支付金额(单位： 分)
	private String payAmount;

	// 币种
	private String currency;

	// 交易下单时间
	private String tranTime;

	// 交易完成时间
	private String tranFinishTime;

	// 交易结果状态
	private String tranStatus;

	// 交易手续费
	private String tranCharge;

	public PinganB2cPayRespParam(KeyedCollection output) {
		this.output = output;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getTranTime() {
		return tranTime;
	}

	public String getTranFinishTime() {
		return tranFinishTime;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public String getTranCharge() {
		return tranCharge;
	}

	public PinganB2cPayRespParam setOrderNoKey(String key) {
		this.orderNo = getFromOutput(key);

		return this;
	}

	public PinganB2cPayRespParam setPayAmountKey(String key) {
		this.payAmount = getFromOutput(key);

		return this;
	}

	public PinganB2cPayRespParam setCurrencyKey(String key) {
		this.currency = MoneyAmountHelper.formatRMB2CNY(getFromOutput(key));

		return this;
	}

	public PinganB2cPayRespParam setTranTimeKey(String key) {
		this.tranTime = getFromOutput(key);

		return this;
	}

	public PinganB2cPayRespParam setTranFinishTimeKey(String key) {
		this.tranFinishTime = getFromOutput(key);

		return this;
	}

	public PinganB2cPayRespParam setTranStatusKey(String key) {
		this.tranStatus = getFromOutput(key);

		return this;
	}

	public PinganB2cPayRespParam setTranChargeKey(String key) {
		this.tranCharge = getFromOutput(key);

		return this;
	}

	private String getFromOutput(String key) {
		try {
			return StringHelper.avoidNull(output.getDataValue(key));
		} catch (ObjectNotFoundException | InvalidArgumentException e) {
		}

		return "";
	}

}
