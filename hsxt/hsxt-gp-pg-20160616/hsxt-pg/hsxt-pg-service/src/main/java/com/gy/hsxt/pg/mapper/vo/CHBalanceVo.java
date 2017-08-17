/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.mapper.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.mapper.vo
 * 
 *  File Name       : PGCHBalanceVo.java
 * 
 *  Creation Date   : 2015-11-13
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : CHBalanceVo 中国银联对账文件
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class CHBalanceVo extends BalanceParentVo {

	private String transSeqId;

	private String bankOrderNo;

	private Integer transType;

	private BigDecimal transAmount;

	private Integer transStatus;

	private Date bankOrderDate;
	
	public CHBalanceVo() {
	}
	
	public CHBalanceVo(boolean isEndLine) {
		super.setEndLine(isEndLine);
	}

	public String getTransSeqId() {
		return transSeqId;
	}

	public void setTransSeqId(String transSeqId) {
		this.transSeqId = transSeqId;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public Integer getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}

	public Date getBankOrderDate() {
		return bankOrderDate;
	}

	public void setBankOrderDate(Date bankOrderDate) {
		this.bankOrderDate = bankOrderDate;
	}

	@Override
	public String toString() {

		// 最后一行显示end
		if (isEndLine()) {
			return super.toString();
		}

		// 银联流水号|银联订单时间|订单号|银联交易类型|交易金额|交易状态
		return transSeqId + "|"
				+ DateUtils.dateToString(bankOrderDate, "yyyy-MM-dd HH:mm:ss")
				+ "|" + bankOrderNo + "|" + transType + "|" + transAmount + "|"
				+ transStatus;
	}

}
