/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

import chinapay.SecureLink;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay
 * 
 *  File Name       : ChinapayBalanceRowItem.java
 * 
 *  Creation Date   : 2015-01-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联对账明细数据
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayBalanceRowItem {

	// 交易时间|商户号|定单号|交易类型|交易金额(分)|交易状态|商户日期|交易网关|交易货币|CP日期|CP流水号|商户私有域|签名
	private static final String[] FIELD_KEYS = "ChinapayTime|MerId|OrdId|TransType|TransAmt|TransStat|TransDate|GateId|Curid|CpDate|CpSeqId|Priv1|CheckValue"
			.split("\\|");

	// 交易时间
	private String chinapayTime;
	// 商户号
	private String merId;
	// 定单号
	private String ordId;
	// 交易类型
	private String transType;
	// 交易金额(分)
	private String transAmt;
	// 交易状态
	private String transStat;
	// 商户日期
	private String transDate;
	// 交易网关
	private String gateId;
	// 交易货币
	private String curid;
	// CP日期
	private String cpDate;
	// CP流水号//
	private String cpSeqId;
	// 商户私有域
	private String priv1;
	// 签名
	private String checkValue;

	// 额外域：是否合法
	private boolean isValidAuthToken = false;

	public ChinapayBalanceRowItem(String lineString, SecureLink secureLink) {
		this.actionInit(lineString, secureLink);
	}

	public String getChinapayTime() {
		return chinapayTime;
	}

	public void setChinapayTime(String chinapayTime) {
		this.chinapayTime = chinapayTime;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getTransStat() {
		return transStat;
	}

	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getCurid() {
		return curid;
	}

	public void setCurid(String curid) {
		this.curid = curid;
	}

	public String getCpDate() {
		return cpDate;
	}

	public void setCpDate(String cpDate) {
		this.cpDate = cpDate;
	}

	public String getCpSeqId() {
		return cpSeqId;
	}

	public void setCpSeqId(String cpSeqId) {
		this.cpSeqId = cpSeqId;
	}

	public String getPriv1() {
		return priv1;
	}

	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	/**
	 * 判断是否为有效的签名
	 * 
	 * @return
	 */
	public boolean checkAuthToken() {
		return this.isValidAuthToken;
	}

	/**
	 * 执行初始化
	 * 
	 * @param lineString
	 * @param secureLink
	 */
	private void actionInit(String lineString, SecureLink secureLink) {
		if (StringHelper.isEmpty(lineString) || (null == secureLink)) {
			return;
		}

		Method method;

		StringBuilder plainDataBuf = new StringBuilder();
		String[] columnValues = lineString.split("\\|");

		for (int i = 0; i < columnValues.length; i++) {
			try {
				method = getClass().getDeclaredMethod("set" + FIELD_KEYS[i],
						String.class);

				method.invoke(this, columnValues[i]);
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}

			// 拼装验证签名的plainData
			if (columnValues.length - 1 != i) {
				plainDataBuf.append(columnValues[i]);
			}
		}

		try {
			this.isValidAuthToken = secureLink.verifyAuthToken(
					plainDataBuf.toString(), getCheckValue());
		} catch (Exception e) {
		}
	}
}
