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
import java.util.List;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2e.params
 * 
 *  File Name       : B2eBatchTransParam.java
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
public class B2eBatchTransParam {
	// 批量转账凭证号, C(20) 必输 标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
	private String thirdVoucher;
	// 付款人账户, C(14) 必输 扣款账户
	private String outAcctNo;
	// 付款人名称, C(60) 必输 付款账户户名
	private String outAcctName;
	// 扣款类型, C(1) 非必输，默认为0, 0：单笔扣划,1：汇总扣划
	private int payType;
	// 货币类型, C(3) 必输
	private String ccycode;
	// 整批转账加急标志, C(1) 必输 Y：加急 , N：不加急, S：特急（汇总扣款模式不支持）
	private String bSysFlag;
	// 总金额, C(16) 单位：分
	private BigInteger totalAmt = new BigInteger("0");

	// 多条记录
	private List<B2eBatchTransDetailParam> detailList;

	public B2eBatchTransParam() {
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

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getCcycode() {
		return ccycode;
	}

	public void setCcycode(String ccycode) {
		this.ccycode = ccycode;
	}

	public String getbSysFlag() {
		return bSysFlag;
	}

	public void setbSysFlag(String bSysFlag) {
		this.bSysFlag = bSysFlag;
	}

	public BigInteger getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigInteger totalAmt) {
		this.totalAmt = totalAmt;
	}

	public List<B2eBatchTransDetailParam> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<B2eBatchTransDetailParam> detailList) {
		this.detailList = detailList;
	}

}
