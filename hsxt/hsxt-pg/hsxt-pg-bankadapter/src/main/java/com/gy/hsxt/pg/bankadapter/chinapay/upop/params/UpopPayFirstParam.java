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
 *  File Name       : UpopPayFirstParam.java
 * 
 *  Creation Date   : 2015年9月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付[首次]请求参数对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpopPayFirstParam {

	// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
	private String orderDate;

	// 交易订单号, 长度： 16, 必输，数字，定长，商户系统当天唯一
	private String orderNo;

	// 卡号, 长度： 19(MAX), 必输 ，数字
	private String cardNo;

	// 借贷记标识, 长度： 2, 必输，数字，定长(01：借记卡; 02：贷记卡)
	private String cardType;

	// 银行代码, 长度： 4, 必输，参照银行名称-简码对照表
	private String bankId;

	// 交易金额(必填, 单位：分), 长度： 12, 必输，数字，定长，单位为分，不足12位，左补零至12位
	private BigInteger payAmount = new BigInteger("0");

	// 后台交易接收URL, 长度： 80(MAX)
	private String notifyURL;

	// 前台交易接收URL, 长度： 80(MAX)
	private String jumpURL;

	// 商户私有域 Priv1, 长度： 60(MAX)
	private String priv1;

	public UpopPayFirstParam() {
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = "0" + cardType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public BigInteger getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigInteger payAmount) {
		this.payAmount = payAmount;
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

	public String getPriv1() {
		return priv1;
	}

	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}

}
