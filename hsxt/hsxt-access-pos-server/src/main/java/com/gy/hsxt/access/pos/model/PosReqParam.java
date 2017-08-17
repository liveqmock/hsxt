/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: PosReqParam 
 * @Description:  pos 请求状态参数
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:09:46 
 * @version V1.0
 */
public class PosReqParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 3 终端交易处理码
	 */
	private String termTradeCode;
	
	/**
	 * 25终端服务条件码
	 */
	private String termServiceCode;
	
	/**
	 * 26密码最大明文长度
	 */
	private String servicePinLen;
	
	/**
     * 22.1+2 输入方式(刷 卡，手输)
     */
    private String inputWay;
    
    /**
     * 22.3 交易中是否有PIN
     */
    private String isPin;
    
	/**
	 * 35第二磁道数据
	 * 刷卡时的，卡号
	 */
	private String stripe2;
	
	/**
	 * 52pin码 ,加密的密码
	 */
	private byte[] pinDat;
	
	/**
	 * 60交易类型信息 +批次号+网络管理码
	 */
	private TradeType tradeTypeInfoBean;
	
	/**
	 * 62自定义2(终端设备信息/同步参数)
	 */
	private Object custom2;
	
	/**
	 * //每次请求必校验的
	 * 64mac码
	 */
	private byte[] macDat;
	
	
	/**
	 * 2/35卡号(含暗码)
	 */
	private String cardNo;	
	
	/**
	 * 卡解析的密码
	 */
	private String cardPwd;
	
	/**
	 * 4 交易金额
	 */
    private BigDecimal transAmount;
    
    /**
	 * 11 终端流水号TermRunCode
	 */
    private String posRunCode;
    
	/**
	 * 41	POS编号。
	 */
    private String posNo;
	
	/**
	 * 42企业编号
	 */
    private String entNo;
	
	/**
	 * 48归一科技自定义bean  
	 */
    private Object gyBean;
	
	/**
	 * 49货币代号
	 */
    private String currencyCode;
	
	/**
	 * 63操作员编号
	 */
    private String operNo;
    
    /**
	 * 39 冲正原因代码
	 */    
    private String reverseCode;
    
    /**
	 * 37 冲正或退单的原交易流水号
	 */    
    private String originalNo;
    
    /**
     * 由pin 解密得到 的值 
     */
    private int channelType;
    
    /**
	 * 12 终端交易时间 hhmmss
	 */
	private String tradeTime;
	/**
	 * 13 终端交易日期 MMDD
	 */
	private String tradeDate;
	
	/**
	 * 终端交易时间戳由13和12组成
	 */
	private Timestamp tradeTimestamp;

	public String getTermTradeCode() {
		return termTradeCode;
	}

	public void setTermTradeCode(String termTradeCode) {
		this.termTradeCode = termTradeCode;
	}

	public String getServicePinLen() {
		return servicePinLen;
	}

	public void setServicePinLen(String servicePinLen) {
		this.servicePinLen = servicePinLen;
	}

	public String getStripe2() {
		return stripe2;
	}

	public void setStripe2(String stripe2) {
		this.stripe2 = stripe2;
	}

	public byte[] getPinDat() {
		return pinDat;
	}

	public void setPinDat(byte[] pinDat) {
		this.pinDat = pinDat;
	}

	public TradeType getTradeTypeInfoBean() {
		return tradeTypeInfoBean;
	}

	public void setTradeTypeInfoBean(TradeType tradeTypeInfoBean) {
		this.tradeTypeInfoBean = tradeTypeInfoBean;
	}

	public Object getCustom2() {
		return custom2;
	}

	public void setCustom2(Object custom2) {
		this.custom2 = custom2;
	}

	public byte[] getMacDat() {
		return macDat;
	}

	public void setMacDat(byte[] macDat) {
		this.macDat = macDat;
	}

	public String getTermServiceCode() {
		return termServiceCode;
	}

	public void setTermServiceCode(String termServiceCode) {
		this.termServiceCode = termServiceCode;
	}

	public String getInputWay() {
		return inputWay;
	}

	public void setInputWay(String inputWay) {
		this.inputWay = inputWay;
	}

	public String getIsPin() {
		return isPin;
	}

	public void setIsPin(String isPin) {
		this.isPin = isPin;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getPosRunCode() {
		return posRunCode;
	}

	public void setPosRunCode(String posRunCode) {
		this.posRunCode = posRunCode;
	}

	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String getEntNo() {
		return entNo;
	}

	public void setEntNo(String entNo) {
		this.entNo = entNo;
	}

	public Object getGyBean() {
		return gyBean;
	}

	public void setGyBean(Object gyBean) {
		this.gyBean = gyBean;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getReverseCode() {
		return reverseCode;
	}

	public void setReverseCode(String reverseCode) {
		this.reverseCode = reverseCode;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	public String getOriginalNo() {
		return originalNo;
	}

	public void setOriginalNo(String originalNo) {
		this.originalNo = originalNo;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Timestamp getTradeTimestamp() {
		return tradeTimestamp;
	}

	public void setTradeTimestamp(Timestamp tradeTimestamp) {
		this.tradeTimestamp = tradeTimestamp;
	}

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

}
