package com.gy.hsxt.access.web.person.bean;

public class CardReapplyOrder {
	/** 订单号 **/
	private String applyCode;
	/** 客户id **/
	private String custId;
	/** 互生号 **/
	private String resNo;
	/** 接收人 **/
	private String receiverName;
	/** 客户电话 **/
	private String phone;
	/** 邮编 **/
	private String postcode;
	/** 收货地址 **/
	private String address;
	/** 补卡原因 **/
	private String reason;
	/** 支付金额 **/
	private String payPrice;
	/** 支付方式 **/
	private String payType;
	/** 交易密码 **/
	private String dealPwd;
	/** 补卡状态 **/
	private String status;
	
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getDealPwd() {
		return dealPwd;
	}
	public void setDealPwd(String dealPwd) {
		this.dealPwd = dealPwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
