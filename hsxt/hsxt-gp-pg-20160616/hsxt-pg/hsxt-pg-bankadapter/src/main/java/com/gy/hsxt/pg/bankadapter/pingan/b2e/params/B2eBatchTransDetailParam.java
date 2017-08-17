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

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2e.params
 * 
 *  File Name       : B2eBatchTransDetailParam.java
 * 
 *  Creation Date   : 2014-11-6
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国平安银行B2E大批量转现
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class B2eBatchTransDetailParam {
	// 单笔转账凭证号(批次中的流水号)/序号, C(20) 必输
	// 同一个批次内不能重复，建议按顺序递增生成，若上送返回则按此字段递增排序。；建议为递增序号，如从1开始
	private String sThirdVoucher;
	// 收款人开户行行号, C(12) 非必输, 跨行转账不落地，则必输。为人行登记在册的商业银行号
	private String inAcctBankNode;
	// 接收行行号, C(12) 非必输 跨行转账不落地，则必输。为人行登记在册的商业银行号
	private String inAcctRecCode;
	// 收款人账户, C(32) 必输
	private String inAcctNo;
	// 收款人账户户名, C(60) 必输
	private String inAcctName;
	// 收款人开户行名称, C(60) 必输 建议格式：xxx银行xx分行xx支行
	private String inAcctBankName;
	// 收款账户开户省代码, C(2) 非必输 建议上送，减少跨行转账落单率。对照码参考“附录-省对照表”
	private String inAcctProvinceCode;
	// 收款账户开户市, C(12) 非必输 建议上送，减少跨行转账落单率。
	private String inAcctCityName;
	// 转出金额, C(16), 单位：分
	private BigInteger tranAmount = new BigInteger("0");
	// 资金用途, C(30) 必输
	private String useEx;
	// 行内跨行标志, C(1) 必输 1：行内转账，0：跨行转账
	private int unionFlag;
	// 同城/异地标志, C(1)
	private int addrFlag;

	public B2eBatchTransDetailParam() {
	}

	public String getsThirdVoucher() {
		return sThirdVoucher;
	}

	public void setsThirdVoucher(String sThirdVoucher) {
		this.sThirdVoucher = sThirdVoucher;
	}

	public String getInAcctBankNode() {
		return inAcctBankNode;
	}

	public void setInAcctBankNode(String inAcctBankNode) {
		this.inAcctBankNode = inAcctBankNode;
	}

	public String getInAcctRecCode() {
		return inAcctRecCode;
	}

	public void setInAcctRecCode(String inAcctRecCode) {
		this.inAcctRecCode = inAcctRecCode;
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

	public String getUseEx() {
		return useEx;
	}

	public void setUseEx(String useEx) {
		// 过滤掉平安银行禁止的特殊字符,以免操作失败
		this.useEx = StringHelper.avoidNull(useEx).replaceAll("[:|!]", "");
	}

	public int getUnionFlag() {
		return unionFlag;
	}

	public String getUnionFlagStr() {
		return String.valueOf(unionFlag);
	}

	public void setUnionFlag(int unionFlag) {
		this.unionFlag = unionFlag;
	}

	public int getAddrFlag() {
		return addrFlag;
	}

	public String getAddrFlagStr() {
		return String.valueOf(addrFlag);
	}

	public void setAddrFlag(int addrFlag) {
		this.addrFlag = addrFlag;
	}

	@Override
	public String toString() {
		return "B2eBatchTransDetailParam [sThirdVoucher=" + sThirdVoucher
				+ ", inAcctBankNode=" + inAcctBankNode + ", inAcctRecCode="
				+ inAcctRecCode + ", inAcctNo=" + inAcctNo + ", inAcctName="
				+ inAcctName + ", inAcctBankName=" + inAcctBankName
				+ ", inAcctProvinceCode=" + inAcctProvinceCode
				+ ", inAcctCityName=" + inAcctCityName + ", tranAmount="
				+ tranAmount + ", useEx=" + useEx + ", unionFlag=" + unionFlag
				+ ", addrFlag=" + addrFlag + "]";
	}
}
