/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bean.parent;

import java.io.Serializable;
import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean.parent
 * 
 *  File Name       : PGParentBean.java
 * 
 *  Creation Date   : 2015年12月10日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class PGParentBean implements Serializable {

	private static final long serialVersionUID = -4280821264746711882L;

	/** 商户类型 100-互生线（默认） 101-互商线 200-第三方接入商户（预留，指淘宝、京东等） */
	private int merType;

	/** 银行支付单号，最大16位字符 */
	private String bankOrderNo;

	/** 商品名称, 最大长度120, 必输 */
	private String goodsName;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 交易时间，格式：yyyyMMdd */
	private Date transDate;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode = "CNY";

	/** 传递系统内部处理参数, 注意仅用于支付系统与支付网关之间的内部处理需要而引入, 传递的值建议是键值对方式. */
	protected String privObligate2;

	public PGParentBean() {
	}

	public int getMerType() {
		return merType;
	}

	public void setMerType(int merType) {
		this.merType = merType;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
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

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPrivObligate2() {
		return privObligate2;
	}

	public void setPrivObligate2(String privObligate2) {
		this.privObligate2 = privObligate2;
	}
	
}
