package com.gy.hsxt.access.pos.model;

/***************************************************************************
 * <PRE>
 *  Project Name    : PosBusServer
 * 
 *  Package Name    : com.gy.point.model
 * 
 *  File Name       : PointDetail.java
 * 
 *  Creation Date   : 2014-6-17
 * 
 *  Author          : linghongjun
 * 
 *  Purpose         : 积分明细实体对象
 *  GengLian：DataCenter起来后，可以删除有关这个的全部东东了。
 *  
 *  History         : 
 * 
 * </PRE>
 ***************************************************************************/

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PointDetail extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1228195873894298000L;
	/**
	 * 中心流水号(订单流水号)
	 */
	private String originNo;
	/**
	 * 订单业务类型：01积分、02积分撤单,09 冲正,11互生币消费,12互生币撤单,13互生币退货,21互生钱包消费,22互生钱包撤单,23互生钱包退货
	 */
	private String originType;
	/**
	 * 订单类型名称
	 */
	private String originTypeName;
	/**
	 * 企业编号 res_no
	 */
	private String entNo;
	/**
	 * 企业名称 custName
	 */
	private String entName;	
	/**
	 * pos编号
	 */
	private String posNo;
	/**
	 * 店铺编号
	 */
	private String shopNo;
	/**
	 * 店铺编号	
	 */
	private String shopName;
	/**
	 * 渠道类型：POS, WEB, ANDROID
	 */
	private String channelType;
	/**
	 * 终端流水号(业务流水号)
	 */
	private String termRunCode;
	/**
	 * 积分卡号
	 */
	private String cardNo;	
	/**
	 * 交易时间 YYYY-MM-DD HH:mm:ss
	 */
	private Timestamp orderDate;
	/**
	 * 交易金额（终端原始币种金额转换后的本币金额）
	 */
	private BigDecimal orderAmount;
	/**
	 * 原始币种金额
	 */
	private BigDecimal cashAmount;
	/**
	 * 积分比例
	 */
	private BigDecimal pointRate;
	/**
	 * 货币代号
	 */
	private String currency;
	/**
	 * 积分转现比率
	 */
	private BigDecimal exchangeRate;
	/**
	 * 本次积分
	 */
	private BigDecimal pointsValue;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 原订单流水号
	 */
	private String originalNo;
	/**
	 * 操作员
	 */
	private String operNo;
	/**
	 * 预付款金额(积分金额)
	 */
	private BigDecimal assureOutValue;
	/**
	 * 业务状态，1账务失败2账务成功
	 */
	private String status; 
	/**
	 * C 已撤单，N 正常订单 ,R 已冲正
	 */
	private String orderStatus;
	/**
	 * 明细类型（LCLP本地卡本地积分,FCLP 外地卡本地积分, LCFP本地卡外地积分）
	 */
	private String detailType;
	/**
	 * 是否已结算Y/N
	 */
	private String isSettle;
	/**
	 * 终端交易码 6
	 */
	private String termTradeCode;
	/**
	 * 终端类型码 2
	 */
	private String termTypeCode;
	/**
	 * 终端服务条件码
	 */
	private String termServiceCode;	
	/**
	 * 终端冲正原因码
	 */
	private String termReverseCode;		
	/**
	 * 消费者客户号
	 */
	private String cardCustId;
	/**
	 * 企业客户号
	 */
	private String custId;
	/**
	 * 企业类型
	 */
	private char entType;
	/**
	 * 互生订单号
	 */
	private String hsOrder;
	
	public PointDetail(){}
	
	public PointDetail(String originNo,String originType,String originTypeName,
			String entNo,String entName,String posNo,String shopNo,String shopName,
			String channelType,String termRunCode,String cardNo,
			Timestamp orderDate,BigDecimal orderAmount,BigDecimal cashAmount,BigDecimal pointRate,
			String currency,BigDecimal exchangeRate,BigDecimal pointsValue,
			String batchNo,String originalNo,String operNo,BigDecimal assureOutValue,
			String status,String orderStatus,String detailType,String isSettle,
			String termTradeCode,String termTypeCode,String termServiceCode,
			String termReverseCode,String cardCustId,String custId,
			char entType,String hsOrder){
		this.originNo = originNo;
		this.originType = originType;
		this.originTypeName = originTypeName;
		this.entNo = entNo;
		this.entName = entName;
		this.posNo = posNo;
		this.shopNo = shopNo;
		this.shopName = shopName;
		this.channelType = channelType;
		this.termRunCode = termRunCode;
		this.cardNo = cardNo;
		this.orderDate = orderDate;
		this.orderAmount = orderAmount;
		this.cashAmount = cashAmount;
		this.pointRate = pointRate;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.pointsValue = pointsValue;
		this.batchNo = batchNo;
		this.originalNo = originalNo;
		this.operNo = operNo;
		this.assureOutValue = assureOutValue;
		this.status = status;
		this.orderStatus = orderStatus;
		this.detailType = detailType;
		this.isSettle = isSettle;
		this.termTradeCode = termTradeCode;
		this.termTypeCode = termTypeCode;
		this.termServiceCode = termServiceCode;
		this.termReverseCode = termReverseCode;
		this.cardCustId = cardCustId;
		this.custId = custId;
		this.entType = entType;
		this.hsOrder = hsOrder;
	}
	
	public PointDetail(String originNo,String originType,String originTypeName,
			String entNo,String posNo,String shopNo,String shopName,
			String channelType,String termRunCode,String cardNo,
			Timestamp orderDate,BigDecimal orderAmount,BigDecimal pointRate,
			String currency,BigDecimal exchangeRate,BigDecimal pointsValue,
			String batchNo,String originalNo,String operNo,BigDecimal assureOutValue,
			String orderStatus){
		this.originNo = originNo;
		this.originType = originType;
		this.originTypeName = originTypeName;
		this.entNo = entNo;
		this.posNo = posNo;
		this.shopNo = shopNo;
		this.shopName = shopName;
		this.channelType = channelType;
		this.termRunCode = termRunCode;
		this.cardNo = cardNo;
		this.orderDate = orderDate;
		this.orderAmount = orderAmount;
		this.pointRate = pointRate;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.pointsValue = pointsValue;
		this.batchNo = batchNo;
		this.originalNo = originalNo;
		this.operNo = operNo;
		this.assureOutValue = assureOutValue;
		this.orderStatus = orderStatus;
	}
	
	public PointDetail(String originNo,String entNo,String posNo){
		this.originNo = originNo;
		this.entNo = entNo;
		this.posNo = posNo;
	}
	public PointDetail(String entNo,String posNo,String channelType,
			String termRunCode,String originType,String cardNo,BigDecimal orderAmount,String batchNo,
			Timestamp orderDate){
		this.entNo = entNo;
		this.posNo = posNo;
		this.channelType = channelType;
		this.termRunCode = termRunCode;
		this.originType = originType;
		this.cardNo = cardNo;
		this.orderAmount = orderAmount;
		this.batchNo = batchNo;
		this.orderDate = orderDate;
	}
	public String getOriginNo() {
		return originNo;
	}

	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	public String getOriginType() {
		return originType;
	}

	public void setOriginType(String originType) {
		this.originType = originType;
	}

	public String getOriginTypeName() {
		return originTypeName;
	}

	public void setOriginTypeName(String originTypeName) {
		this.originTypeName = originTypeName;
	}

	public String getEntNo() {
		return entNo;
	}

	public void setEntNo(String entNo) {
		this.entNo = entNo;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getTermRunCode() {
		return termRunCode;
	}

	public void setTermRunCode(String termRunCode) {
		this.termRunCode = termRunCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getPointRate() {
		return pointRate;
	}

	public void setPointRate(BigDecimal pointRate) {
		this.pointRate = pointRate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getPointsValue() {
		return pointsValue;
	}

	public void setPointsValue(BigDecimal pointsValue) {
		this.pointsValue = pointsValue;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getOriginalNo() {
		return originalNo;
	}

	public void setOriginalNo(String originalNo) {
		this.originalNo = originalNo;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public BigDecimal getAssureOutValue() {
		return assureOutValue;
	}

	public void setAssureOutValue(BigDecimal assureOutValue) {
		this.assureOutValue = assureOutValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(String isSettle) {
		this.isSettle = isSettle;
	}

	public String getTermTradeCode() {
		return termTradeCode;
	}

	public void setTermTradeCode(String termTradeCode) {
		this.termTradeCode = termTradeCode;
	}

	public String getTermTypeCode() {
		return termTypeCode;
	}

	public void setTermTypeCode(String termTypeCode) {
		this.termTypeCode = termTypeCode;
	}

	public String getTermServiceCode() {
		return termServiceCode;
	}

	public void setTermServiceCode(String termServiceCode) {
		this.termServiceCode = termServiceCode;
	}

	public String getTermReverseCode() {
		return termReverseCode;
	}

	public void setTermReverseCode(String termReverseCode) {
		this.termReverseCode = termReverseCode;
	}

	public String getCardCustId() {
		return cardCustId;
	}

	public void setCardCustId(String cardCustId) {
		this.cardCustId = cardCustId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public char getEntType() {
		return entType;
	}

	public void setEntType(char entType) {
		this.entType = entType;
	}

	public String getHsOrder() {
		return hsOrder;
	}

	public void setHsOrder(String hsOrder) {
		this.hsOrder = hsOrder;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PointDetail)){
			return false;
		}
		PointDetail model = (PointDetail)obj;
		return model.getTermRunCode().equals(termRunCode) &&
			model.getCardNo().equals(cardNo) && 	
			model.getOrderAmount() == orderAmount && 
			model.getPointRate() == pointRate && 
			model.getAssureOutValue() == assureOutValue && 
			model.getPointsValue() == pointsValue;
	}
/*
	@Override
	public int hashCode() {
		int result = 17;
		long tolong = Double.doubleToLongBits(orderAmount);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		tolong = Double.doubleToLongBits(pointRate);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));	
		tolong = Double.doubleToLongBits(assureOutValue);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));	
		tolong = Double.doubleToLongBits(pointsValue);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));			
		return result;
	}
	*/
}
