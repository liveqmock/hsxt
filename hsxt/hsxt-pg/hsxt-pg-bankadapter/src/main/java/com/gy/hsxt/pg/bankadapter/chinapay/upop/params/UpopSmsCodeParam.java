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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.params
 * 
 *  File Name       : UpopSmsCodeParam.java
 * 
 *  Creation Date   : 2015年9月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付短信验证码请求参数对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpopSmsCodeParam {

	// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
	private String orderDate;

	// 交易订单号, 长度： 16, 必输，数字，定长，商户系统当天唯一
	private String orderNo;

	// 交易金额(必填, 单位：分), 长度： 12, 必输，数字，定长，单位为分，不足12位，左补零至12位
	private BigInteger payAmount = new BigInteger("0");

	// 后台交易接收URL, 长度： 80(MAX)
	private String notifyURL;
	
	// 前台交易接收URL, 长度： 80(MAX), 根据测试, 这个地址没有任何作用
	private String jumpURL;

	// 签约号, 长度：24, 必输，数字，定长
	private String bindingNo;

	public UpopSmsCodeParam() {
	}

	public String getOrderDate() {
		return orderDate;
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

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getJumpURL() {
		return jumpURL;
	}

	public void setJumpURL(String jumpURL) {
		this.jumpURL = jumpURL;
	}

}
