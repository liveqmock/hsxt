/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.bean.pinganpay;

import java.io.Serializable;
import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean.pinganpay
 * 
 *  File Name       : PinganQuickPaySmsCodeBean.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行获取快捷支付短信验证码beans
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganQuickPaySmsCodeBean implements Serializable {

	private static final long serialVersionUID = 7624722353789872153L;

	/** 商户号，1100000001：互商 1100000007：互生 第三方接入的酌情分配 */
	private String merId;

	/** 业务订单号，最大32位字符 */
	private String orderNo;

	/** 业务订单日期，格式：yyyyMMdd */
	private Date orderDate;

	/** 商品名称, 最大长度120, 必输 */
	private String goodsName;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 交易描述 ,最大长度30 (将会显示到支付页面, 请注意文字组织) */
	private String transDesc;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode;

	/** 传递商户私有数据,用于回调时带回,最大长度60 */
	private String privObligate;

	/** 客户号，用于向用户中心同步签约号,最大长度21 */
	private String custId;

	/** 签约号,最大长度26 */
	private String bindingNo;

	/**
	 * 构造函数
	 */
	public PinganQuickPaySmsCodeBean() {
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPrivObligate() {
		return privObligate;
	}

	public void setPrivObligate(String privObligate) {
		this.privObligate = privObligate;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	@Override
	public String toString() {
		return "PinganQuickPaySmsCodeBean [merId=" + merId + ", orderNo="
				+ orderNo + ", orderDate=" + orderDate + ", goodsName="
				+ goodsName + ", transAmount=" + transAmount + ", transDesc="
				+ transDesc + ", currencyCode=" + currencyCode
				+ ", privObligate=" + privObligate + ", custId=" + custId
				+ ", bindingNo=" + bindingNo + "]";
	}

}
