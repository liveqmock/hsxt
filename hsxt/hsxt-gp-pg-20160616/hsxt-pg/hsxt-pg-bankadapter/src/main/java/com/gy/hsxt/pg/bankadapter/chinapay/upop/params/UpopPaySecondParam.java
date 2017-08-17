/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.params;

import java.math.BigInteger;
import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.params
 * 
 *  File Name       : UpopPaySecondParam.java
 * 
 *  Creation Date   : 2015年9月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付[非首次]请求参数对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpopPaySecondParam {

	// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
	private String orderDate;
	
	// 交易订单号, 长度： 16, 必输，数字，定长，商户系统当天唯一
	private String orderNo;
	
	// 交易金额(必填, 单位：分), 长度： 12
	private BigInteger payAmount = new BigInteger("0");
	
	// 签约号, 长度：24, 必输，数字，定长
	private String bindingNo;
	
	// 短信验证码, 长度：6, 必输，数字，定长
	private String smsCode;

	public UpopPaySecondParam() {
	}

	public String getOrderDate() {
		return orderDate;
	}
	
	public Date getOrderDate2() {
		return DateUtils.getYYYYMMddHHmmssDate(orderDate);
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigInteger getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigInteger payAmount) {
		this.payAmount = payAmount;
	}

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
