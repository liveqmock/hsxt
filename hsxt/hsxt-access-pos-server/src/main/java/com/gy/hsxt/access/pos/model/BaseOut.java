package com.gy.hsxt.access.pos.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 以前有messageId，因为历史原因。现在不需要了，跟acct更加没有关系。
 */
public class BaseOut implements Serializable {

	private static final long serialVersionUID = 90565434142311L;
	/**
	 * 清算日期，MMDD
	 */
	private String settleDate;
	/**
	 * 积分，肯定需要这个。对应了DB的P_Cust_Id，卡的客户ID。
	 * 今后，冲正/撤单/互生币/互生钱包，都需要这个。
	 */
	private String cardCustId;
	/**
	 * 积分，肯定需要这个。对应了DB的Cust_Id，企业的客户ID。
	 * 今后，冲正/撤单/互生币/互生钱包，都需要这个。
	 */
	private String entCustId;

	public BaseOut() {
	}

	public BaseOut(String settleDate, String cardCustId, String entCustId) {
		super();
		this.settleDate = settleDate;
		this.cardCustId = cardCustId;
		this.entCustId = entCustId;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getCardCustId() {
		return cardCustId;
	}

	public void setCardCustId(String cardCustId) {
		this.cardCustId = cardCustId;
	}

	public String getEntCustId() {
		return entCustId;
	}

	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
