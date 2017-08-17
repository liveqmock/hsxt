/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.bean
 * 
 *  File Name       : TransCash.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行单笔转账bean
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class TransCash implements Serializable {

	private static final long serialVersionUID = -3005447155005372828L;

	/** 付款账户类型， 如果不传则默认100 100 - 通用付款账户(默认） 其他枚举酌情根据需求扩展定义。 */
	private int outBankAccType = 100;

	/** 收款人账户,最大32位字符 */
	private String inAccNo;

	/** 收款人账户名，最大60位字符 */
	private String inAccName;

	/** 收款人开户行名称，最大60位字符 */
	private String inAccBankName;

	/** 收款人开户银行代码，该字段用于区分“跨行”或“同行”，同时也用于获取联行号,最大10位字符 */
	private String inAccBankNode;

	/** 收款账户银行开户省代码或省名称， 非必输，建议跨行转账输入，对照码参考“附录-省对照表”，也可输入“附录-省对照表”中的省名称，最大2位字符 */
	private String inAccProvinceCode;

	/** 收款账户开户城市名称， 非必输，建议跨行转账输入，主要为了减少跨行转账落单率，最大12位字符 */
	private String inAccCityName;

	/** 收款账户开户城市代码， 必输，用来判断是“同城”还是“异地”，最大10位字符 */
	private String inAccCityCode;

	/** 转账通知的手机号码， 最多不能超过5个手机号码 */
	private List<String> notifyMobile;

	/** 转账加急标志: 1-大额(>=5万)，等同Y, 2-小额(<5万)，等同N,  S：特急,  Y：加急, N：普通(默认) */
	private String sysFlag = "N";

	/** 资金用途备注，最大30位字符 */
	private String useDesc;

	/** 业务订单号，最大32位字符 */
	private String orderNo;

	/** 业务订单日期，格式：yyyyMMdd */
	private Date orderDate;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode = "CNY";
	
	/**
	 * 构造函数
	 */
	public TransCash() {
	}

	/**
	 * 构造函数
	 * 
	 * @param inAccNo
	 * @param inAccName
	 * @param inAccBankName
	 * @param inAccBankNode
	 * @param inAccCityCode
	 * @param orderNo
	 * @param orderDate
	 * @param transAmount
	 */
	public TransCash(String inAccNo, String inAccName, String inAccBankName,
			String inAccBankNode, String inAccCityCode, String orderNo,
			Date orderDate, String transAmount) {
		this.inAccNo = inAccNo;
		this.inAccName = inAccName;
		this.inAccBankName = inAccBankName;
		this.inAccBankNode = inAccBankNode;
		this.inAccCityCode = inAccCityCode;
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.transAmount = transAmount;
	}

	public int getOutBankAccType() {
		return outBankAccType;
	}

	public void setOutBankAccType(int outBankAccType) {
		this.outBankAccType = outBankAccType;
	}

	public String getInAccNo() {
		return inAccNo;
	}

	public void setInAccNo(String inAccNo) {
		this.inAccNo = inAccNo;
	}

	public String getInAccName() {
		return inAccName;
	}

	public void setInAccName(String inAccName) {
		this.inAccName = inAccName;
	}

	public String getInAccBankName() {
		return inAccBankName;
	}

	public void setInAccBankName(String inAccBankName) {
		this.inAccBankName = inAccBankName;
	}

	public String getInAccProvinceCode() {
		return inAccProvinceCode;
	}

	public void setInAccProvinceCode(String inAccProvinceCode) {
		this.inAccProvinceCode = inAccProvinceCode;
	}

	public String getInAccCityName() {
		return inAccCityName;
	}

	public void setInAccCityName(String inAccCityName) {
		this.inAccCityName = inAccCityName;
	}

	public String getInAccCityCode() {
		return inAccCityCode;
	}

	public void setInAccCityCode(String inAccCityCode) {
		this.inAccCityCode = inAccCityCode;
	}

	public List<String> getNotifyMobile() {
		return notifyMobile;
	}

	public void setNotifyMobile(List<String> notifyMobile) {
		this.notifyMobile = notifyMobile;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getUseDesc() {
		return useDesc;
	}

	public void setUseDesc(String useDesc) {
		this.useDesc = useDesc;
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

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getInAccBankNode() {
		return inAccBankNode;
	}

	public void setInAccBankNode(String inAccBankNode) {
		this.inAccBankNode = inAccBankNode;
	}

	@Override
	public String toString() {
		return "TransCash [outBankAccType=" + outBankAccType + ", inAccNo="
				+ inAccNo + ", inAccName=" + inAccName + ", inAccBankName="
				+ inAccBankName + ", inAccBankNode=" + inAccBankNode
				+ ", inAccProvinceCode=" + inAccProvinceCode
				+ ", inAccCityName=" + inAccCityName + ", inAccCityCode="
				+ inAccCityCode + ", notifyMobile=" + notifyMobile
				+ ", sysFlag=" + sysFlag + ", useDesc=" + useDesc
				+ ", orderNo=" + orderNo + ", orderDate=" + orderDate
				+ ", transAmount=" + transAmount + ", currencyCode="
				+ currencyCode + "]";
	}
}
