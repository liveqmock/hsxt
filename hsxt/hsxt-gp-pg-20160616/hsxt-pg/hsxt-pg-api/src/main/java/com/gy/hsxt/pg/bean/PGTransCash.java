/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-api
 * 
 *  Package Name    : com.gy.hsxt.pg.bean
 * 
 *  File Name       : PGTransCash.java
 * 
 *  Creation Date   : 2015-10-10
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PGTransCash implements Serializable {

	private static final long serialVersionUID = 4839211594078044286L;

	/** 银行支付单号，最大16位字符 */
	private String bankOrderNo;

	/** 交易金额，精度为6，传递值的精度小于6的自动补足，大于6的禁止； */
	private String transAmount;

	/** 交易时间，格式：yyyyMMdd */
	private Date transDate;

	/** 币种 CNY 人民币（默认） */
	private String currencyCode;

	/** 付款银行账户,最大19位字符 */
	private String outBankAccNo;

	/** 付款人账户名，最大60位字符 */
	private String outBankAccName;

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

	/** 转账通知的手机号码， 最多不能超过5个手机号码，最大80位字符 */
	private List<String> notifyMobile;

	/** 行内跨行标志 0：跨行转账 1：行内转账（默认) */
	private int unionFlag = 1;

	/** 1-大额(>=5万)等同Y, 2-小额(<5万) 等同N, S：特急 Y：加急   N：普通(默认) */
	private String sysFlag = "N";

	/** 同城/异地标志 1—同城（默认） 2—异地 */
	private int addrFlag = 1;

	/** 资金用途备注，最大30位字符 */
	private String useDesc;
	
	public PGTransCash() {
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
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

	public String getOutBankAccNo() {
		return outBankAccNo;
	}

	public void setOutBankAccNo(String outBankAccNo) {
		this.outBankAccNo = outBankAccNo;
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

	public int getUnionFlag() {
		return unionFlag;
	}

	public void setUnionFlag(int unionFlag) {
		this.unionFlag = unionFlag;
	}

	public int getAddrFlag() {
		return addrFlag;
	}

	public void setAddrFlag(int addrFlag) {
		this.addrFlag = addrFlag;
	}

	public String getUseDesc() {
		return useDesc;
	}

	public void setUseDesc(String useDesc) {
		this.useDesc = useDesc;
	}

	public String getOutBankAccName() {
		return outBankAccName;
	}

	public void setOutBankAccName(String outBankAccName) {
		this.outBankAccName = outBankAccName;
	}

	public String getInAccBankNode() {
		return inAccBankNode;
	}

	public void setInAccBankNode(String inAccBankNode) {
		this.inAccBankNode = inAccBankNode;
	}

	@Override
	public String toString() {
		return "PGTransCash [bankOrderNo=" + bankOrderNo + ", transAmount="
				+ transAmount + ", transDate=" + transDate + ", currencyCode="
				+ currencyCode + ", outBankAccNo=" + outBankAccNo
				+ ", outBankAccName=" + outBankAccName + ", inAccNo=" + inAccNo
				+ ", inAccName=" + inAccName + ", inAccBankName="
				+ inAccBankName + ", inAccBankNode=" + inAccBankNode
				+ ", inAccProvinceCode=" + inAccProvinceCode
				+ ", inAccCityName=" + inAccCityName + ", notifyMobile="
				+ notifyMobile + ", unionFlag=" + unionFlag + ", sysFlag="
				+ sysFlag + ", addrFlag=" + addrFlag + ", useDesc=" + useDesc
				+ "]";
	}

}
