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

import com.gy.hsxt.pg.bankadapter.chinapay.IChinapayResultAware;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopRespCode;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.params
 * 
 *  File Name       : UpopBindingNoResponse.java
 * 
 *  Creation Date   : 2015年9月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付查询签约号请求参数对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpopBindingNoResult implements IChinapayResultAware {
	// 商户号,必输，数字，定长
	private String merId;
	// 网关号,必输，固定值8619
	private String gateId;
	// 签约状态,必输，定长
	private String stat;
	// 银行卡号,00-签约成功;01-签约状态未知;02-签约失败;03-未签约;
	private String cardNo;
	// 签约号
	private String bindingNo;
	// 小额临时支付有效期,签约成功后返回
	private String expiry;
	// 小额临时支付单笔限额,开通小额临时支付时返回,8位数字,yyyyMMdd
	private BigInteger transLimit;
	// 小额临时支付总限额,开通小额临时支付时返回，12位数字，以分为单位，不足12位前补0
	private BigInteger sumLimit;
	// 小额临时支付单笔限额,开通小额临时支付时返回,8位数字,yyyyMMdd
	private String strTransLimit;
	// 小额临时支付总限额,开通小额临时支付时返回，12位数字，以分为单位，不足12位前补0
	private String strSumLimit;
	// 响应码
	private String respCode;
	// 发卡商代码
	private String issuerCode;
	// 签名
	private String chkValue;

	public UpopBindingNoResult() {
		this.setRespCode(UpopRespCode.OPT_SUCCESS.getCode());
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBindingNo() {
		return bindingNo;
	}

	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public BigInteger getTransLimit() {
		return transLimit;
	}

	public String getTransLimitStrValue() {
		return strTransLimit;
	}

	public void setTransLimit(String transLimit) {
		if (!StringHelper.isEmptyTrim(transLimit)) {
			this.transLimit = new BigInteger(transLimit);
		}

		this.strTransLimit = transLimit;
	}

	public BigInteger getSumLimit() {
		return sumLimit;
	}

	public String getSumLimitStrValue() {
		return strSumLimit;
	}

	public void setSumLimit(String sumLimit) {
		if (!StringHelper.isEmptyTrim(sumLimit)) {
			this.sumLimit = new BigInteger(sumLimit);
		}

		this.strSumLimit = sumLimit;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}	

	public String getIssuerCode() {
		return issuerCode;
	}

	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
	}

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

}
