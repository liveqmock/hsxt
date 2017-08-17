/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upacp.params;

import java.util.Map;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upacp.params
 * 
 *  File Name       : UpacpBankRespParam.java
 * 
 *  Creation Date   : 2016-04-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联UPACP银行响应参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class UpacpBankRespParam {

	Map<String, String> responseMap;

	// 必填 商户订单号
	private String orderNo;

	// 支付金额(单位： 分)
	private String payAmount;

	// 币种
	private String crrrency;

	// 交易类型
	private String transType;

	// 交易时间
	private String tranTime;

	// 查询流水号(注意：只有UPMP/UPACP手机支付才有QN, 是原订单支付成功后银联notify的qn字段的值)
	private String qn;

	// 私有域
	private String priv1;

	// 响应码
	private String respCode;

	// 响应信息
	private String respMsg;

	public UpacpBankRespParam(Map<String, String> responseMap) {
		this.responseMap = responseMap;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public UpacpBankRespParam setOrderNoKey(String key) {
		this.orderNo = responseMap.get(key);

		return this;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public UpacpBankRespParam setPayAmountKey(String key) {
		this.payAmount = responseMap.get(key);

		return this;
	}

	public String getCurrency() {
		return crrrency;
	}

	public UpacpBankRespParam setCurrencyKey(String key) {
		this.crrrency = responseMap.get(key);

		return this;
	}

	public String getTransType() {
		return transType;
	}

	public UpacpBankRespParam setTransTypeKey(String key) {
		this.transType = responseMap.get(key);

		return this;
	}

	public String getTranTime() {
		return tranTime;
	}

	public UpacpBankRespParam setTranTimeKey(String key) {
		this.tranTime = responseMap.get(key);

		return this;
	}

	public String getQn() {
		return qn;
	}

	public UpacpBankRespParam setQnKey(String key) {
		this.qn = responseMap.get(key);

		return this;
	}

	public String getPriv1() {
		return priv1;
	}

	public UpacpBankRespParam setPriv1Key(String key) {
		this.priv1 = responseMap.get(key);

		return this;
	}

	public String getRespCode() {
		return respCode;
	}

	public UpacpBankRespParam setRespCodeKey(String key) {
		this.respCode = responseMap.get(key);

		return this;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public UpacpBankRespParam setRespMsgKey(String key) {
		this.respMsg = responseMap.get(key);

		return this;
	}

}
