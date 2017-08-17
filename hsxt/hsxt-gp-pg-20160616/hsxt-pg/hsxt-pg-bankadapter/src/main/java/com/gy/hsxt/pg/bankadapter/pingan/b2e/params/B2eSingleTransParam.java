/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2e.params;

import java.math.BigInteger;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2e.params
 * 
 *  File Name       : B2eSingleTransParam.java
 * 
 *  Creation Date   : 2014-10-30
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行转现请求传递的参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class B2eSingleTransParam {
	// 转账凭证号, C(20) [必输] 标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
	private String thirdVoucher;

	// 付款人账户, C(14) [必输] 扣款账户
	private String outAcctNo;

	// 付款人名称, C(60) [必输] 付款账户户名
	private String outAcctName;

	// 货币类型 ,C(3) [必输] RMB-人民币
	private String ccyCode;

	// 收款人开户行行号, C(12) [非必输] 跨行转账建议必输。为人行登记在册的商业银行号
	private String inAcctBankNode;

	// 收款人账户, C(32) [必输]
	private String inAcctNo;

	// 收款人账户户名, C(60) [必输]
	private String inAcctName;

	// 收款人开户行名称, C(60) [必输] 建议格式：xxx银行
	private String inAcctBankName;

	// 收款账户银行开户省代码或省名称, C(10) [非必输] 建议跨行转账输入；对照码参考“附录-省对照表”；也可输入“附录-省对照表”中的省名称
	private String inAcctProvinceCode;

	// 收款账户开户市, C(12) [非必输] 建议跨行转账输入
	private String inAcctCityName;

	// 转出金额, C(16) [必输] (单位：分), 但是平安接口中是以'元'为单位, 小数点后2位, 需要转换一下
	private BigInteger tranAmount = new BigInteger("0");

	// 行内跨行标志, C(1) [必输] 1：行内转账，0：跨行转账
	private int unionFlag;

	// 转账加急标志, C(1) [非必输] ‘1’—大额 ，等同Y, ‘2’—小额”等同N, Y：加急, N：普通, S：特急 ( 默认为N)
	private String sysFlag;

	// 同城/异地标志, C(1) [必输] “1”—同城, “2”—异地
	private int addrFlag;

	// 转账短信通知手机号码 C(100) [非必输] 格式为：“13412341123, 12312341234”，多个手机号码使用半角逗号分隔.
	// 如果为空或者手机号码长度不足11位，就不发送。
	private String mobile;

	public B2eSingleTransParam() {
	}

	public String getThirdVoucher() {
		return thirdVoucher;
	}

	public void setThirdVoucher(String thirdVoucher) {
		this.thirdVoucher = thirdVoucher;
	}

	public String getOutAcctNo() {
		return outAcctNo;
	}

	public void setOutAcctNo(String outAcctNo) {
		this.outAcctNo = outAcctNo;
	}

	public String getOutAcctName() {
		return outAcctName;
	}

	public void setOutAcctName(String outAcctName) {
		this.outAcctName = outAcctName;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public void setCcyCode(String ccyCode) {
		this.ccyCode = ccyCode;
	}

	public String getInAcctBankNode() {
		return inAcctBankNode;
	}

	public void setInAcctBankNode(String inAcctBankNode) {
		this.inAcctBankNode = inAcctBankNode;
	}

	public String getInAcctNo() {
		return inAcctNo;
	}

	public void setInAcctNo(String inAcctNo) {
		this.inAcctNo = inAcctNo;
	}

	public String getInAcctName() {
		return inAcctName;
	}

	public void setInAcctName(String inAcctName) {
		this.inAcctName = inAcctName;
	}

	public String getInAcctBankName() {
		return inAcctBankName;
	}

	public void setInAcctBankName(String inAcctBankName) {
		this.inAcctBankName = inAcctBankName;
	}

	public String getInAcctProvinceCode() {
		return inAcctProvinceCode;
	}

	public void setInAcctProvinceCode(String inAcctProvinceCode) {
		this.inAcctProvinceCode = inAcctProvinceCode;
	}

	public String getInAcctCityName() {
		return inAcctCityName;
	}

	public void setInAcctCityName(String inAcctCityName) {
		this.inAcctCityName = inAcctCityName;
	}

	public BigInteger getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(BigInteger tranAmount) {
		this.tranAmount = tranAmount;
	}

	public int getUnionFlag() {
		return unionFlag;
	}

	public void setUnionFlag(int unionFlag) {
		this.unionFlag = unionFlag;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public int getAddrFlag() {
		return addrFlag;
	}

	public void setAddrFlag(int addrFlag) {
		this.addrFlag = addrFlag;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "B2eSingleTransParam [thirdVoucher=" + thirdVoucher
				+ ", outAcctNo=" + outAcctNo + ", outAcctName=" + outAcctName
				+ ", ccyCode=" + ccyCode + ", inAcctBankNode=" + inAcctBankNode
				+ ", inAcctNo=" + inAcctNo + ", inAcctName=" + inAcctName
				+ ", inAcctBankName=" + inAcctBankName
				+ ", inAcctProvinceCode=" + inAcctProvinceCode
				+ ", inAcctCityName=" + inAcctCityName + ", tranAmount="
				+ tranAmount + ", unionFlag=" + unionFlag + ", sysFlag="
				+ sysFlag + ", addrFlag=" + addrFlag + ", mobile=" + mobile
				+ "]";
	}

}
