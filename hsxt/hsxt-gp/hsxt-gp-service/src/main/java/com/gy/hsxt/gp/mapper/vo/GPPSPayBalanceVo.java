/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.mapper.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.gy.hsxt.gp.common.utils.DateUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.mapper.vo
 * 
 *  File Name       : GPPSPayBalanceVo.java
 * 
 *  Creation Date   : 2015-11-13
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 支付对账文件字段: 交易流水号｜业务订单号｜交易类型｜交易金额｜支付单状态｜交易时间
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class GPPSPayBalanceVo extends BalanceParentVo {

	private String transSeqId;

	private String orderNo;

	private Integer transType;

	private BigDecimal transAmount;

	private Integer transStatus;

	private Date orderDate;
	
	public GPPSPayBalanceVo() {
	}
	
	public GPPSPayBalanceVo(boolean isEndLine) {
		super.setEndLine(isEndLine);
	}

	public String getTransSeqId() {
		return transSeqId;
	}

	public void setTransSeqId(String transSeqId) {
		this.transSeqId = transSeqId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		
		// 最后一行显示end
		if(isEndLine()) {
			return super.toString();
		}
		
		// 对账文件字段: 交易流水号｜业务订单号｜交易类型｜交易金额｜支付单状态｜交易时间		
		return transSeqId + "|" + orderNo + "|" + transType + "|" + transAmount
				+ "|" + transStatus + "|"  + DateUtils.dateToString(orderDate, "yyyy-MM-dd HH:mm:ss");
	}

}
