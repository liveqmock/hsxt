/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.params;

import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.params
 * 
 *  File Name       : UpopCardBindingParam.java
 * 
 *  Creation Date   : 2015年9月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付银行卡签约请求参数对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpopCardBindingParam {
	// 卡号, 长度： 19(MAX), 必输 ，数字
	private String cardNo;

	// 借贷记标识, 长度： 2, 必输，数字，定长(01：借记卡; 02：贷记卡)
	private String cardType;

	// 银行代码, 长度： 4, 必输，参照银行名称-简码对照表
	private String bankId;

	// 后台交易接收URL, 长度： 80(MAX)
	private String notifyURL;

	// 订单交易日期, 长度：8, 必输，数字，格式：yyyyMMdd
	private String orderDate;

	public UpopCardBindingParam() {
		// 设置默认订单日期
		this.setOrderDate(DateUtils.format2yyyyMMddDate(new Date()));
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

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

}
