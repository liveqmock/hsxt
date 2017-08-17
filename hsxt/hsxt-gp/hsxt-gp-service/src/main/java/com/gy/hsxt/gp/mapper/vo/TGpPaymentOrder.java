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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.mapper.vo
 * 
 *  File Name       : TGpPaymentOrder.java
 * 
 *  Creation Date   : 2015-9-30
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : TGpPaymentOrder
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class TGpPaymentOrder {
	private String transSeqId;

	private String orderNo;

	private Date orderDate;

	private String origOrderNo;

	private String bankOrderNo;

	private String bankOrderSeqId;

	private String merId;

	private Integer transType;

	private BigDecimal transAmount;

	private Integer transStatus;

	private Date transDate;

	private Date expiredDate;

	private Integer payChannel;

	private String channelProvider;

	private String currencyCode;

	private String transDesc;

	private String failReason;

	private String privObligate;

	private Date createdDate;

	private Date updatedDate;

	private String srcSubsysId;

	private Date orderDateS;

	private Date transDateS;

	public String getSrcSubsysId() {
		return srcSubsysId;
	}

	public void setSrcSubsysId(String srcSubsysId) {
		this.srcSubsysId = srcSubsysId;
	}

	public String getTransSeqId() {
		return transSeqId;
	}

	public void setTransSeqId(String transSeqId) {
		this.transSeqId = transSeqId == null ? null : transSeqId.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;

		// 仅用于对账文件生成
		this.setOrderDateS(orderDate);
	}

	public String getOrigOrderNo() {
		return origOrderNo;
	}

	public void setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo == null ? null : origOrderNo.trim();
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo == null ? null : bankOrderNo.trim();
	}

	public String getBankOrderSeqId() {
		return bankOrderSeqId;
	}

	public void setBankOrderSeqId(String bankOrderSeqId) {
		this.bankOrderSeqId = bankOrderSeqId == null ? null : bankOrderSeqId
				.trim();
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId == null ? null : merId.trim();
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

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;

		// 仅用于对账文件生成
		this.setTransDateS(transDate);
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public String getChannelProvider() {
		return channelProvider;
	}

	public void setChannelProvider(String channelProvider) {
		this.channelProvider = channelProvider;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc == null ? null : transDesc.trim();
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason == null ? null : failReason.trim();
	}

	public String getPrivObligate() {
		return privObligate;
	}

	public void setPrivObligate(String privObligate) {
		this.privObligate = privObligate == null ? null : privObligate.trim();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getOrderDateS() {
		return orderDateS;
	}

	public void setOrderDateS(Date orderDateS) {
		this.orderDateS = orderDateS;
	}

	public Date getTransDateS() {
		return transDateS;
	}

	public void setTransDateS(Date transDateS) {
		this.transDateS = transDateS;
	}

	@Override
	public String toString() {
		return "TGpPaymentOrder [transSeqId=" + transSeqId + ", orderNo="
				+ orderNo + ", orderDate=" + orderDate + ", origOrderNo="
				+ origOrderNo + ", bankOrderNo=" + bankOrderNo
				+ ", bankOrderSeqId=" + bankOrderSeqId + ", merId=" + merId
				+ ", transType=" + transType + ", transAmount=" + transAmount
				+ ", transStatus=" + transStatus + ", transDate=" + transDate
				+ ", expiredDate=" + expiredDate + ", payChannel=" + payChannel
				+ ", currencyCode=" + currencyCode + ", transDesc=" + transDesc
				+ ", failReason=" + failReason + ", privObligate="
				+ privObligate + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", srcSubsysId="
				+ srcSubsysId + "]";
	}

}