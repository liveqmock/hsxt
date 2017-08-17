package com.gy.hsxt.access.pos.model;

/**
 * 交易单据二维码数据结构
 * @author liuzh
 * 
 * 交易单据二维码”字符串原始定义：（2位字母数字）类型&11位企业互生号&4位pos终端编号&6位批次号&6位pos机凭证号&14位日期时间（YYYYMMDDhh24mmss）
 * &3位货币代码（49）&12位交易金额（4）&4位积分比例（48用法六）&12企业承兑积分额（48用法六）&12互生币金额（48用法六）&8位随机扰码（数字型字符串）&8位mac校验位
 */
public class QrCodeTrans {
	
	/**
	 * 二维码类型 （2位字母数字）
	 */
	private String qrType;
	
	/**
	 * 企业互生号 11位
	 */
	private String entResNo;
	
	/**
	 * pos终端编号 4位
	 */
	private String posTermNo;
	
	/**
	 * 批次号 6位
	 */
	private String posBatchNo;
	
	/**
	 * pos凭证号 6位
	 */	
	private String posTermRunCode;
	
	/**
	 * 交易日期时间  14位 （YYYYMMDDhh24mmss）
	 */
	private String transTime;
	
	/**
	 * 货币代码   3位
	 */
	private String currencyCode;
	
	/**
	 * 交易金额  12位
	 */
	private String transAmount;
	
	/**
	 * 积分比例  4位
	 */
	private String pointRate;
	
	/**
	 * 企业承兑积分额  12位
	 */
	private String entPointAmount;
	
	/**
	 * 互生币金额 12位
	 */
	private String hsbTransAmount;
	
	//start--added by liuzh on 2016-06-22
	/**
	 * 
	 * 实付金额金额 12位
	 */
	private String actualAmount;
	/**
	 * 抵扣券张数6位
	 */
	private String deductionVoucherCount;
	//end--added by liuzh on 2016-06-22
	
	/**
	 * 随机扰码  8位（数字型字符串)
	 */
	private String randomScrambling;
	
	/**
	 * mac校验位 8位
	 */
	private String mac;

	
	public QrCodeTrans() {
		super();
	}

	public String getQrType() {
		return qrType;
	}

	public void setQrType(String qrType) {
		this.qrType = qrType;
	}

	public String getEntResNo() {
		return entResNo;
	}

	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	public String getPosTermNo() {
		return posTermNo;
	}

	public void setPosTermNo(String posTermNo) {
		this.posTermNo = posTermNo;
	}

	public String getEntPointAmount() {
		return entPointAmount;
	}

	public void setEntPointAmount(String entPointAmount) {
		this.entPointAmount = entPointAmount;
	}

	public String getPosBatchNo() {
		return posBatchNo;
	}

	public void setPosBatchNo(String posBatchNo) {
		this.posBatchNo = posBatchNo;
	}

	public String getPosTermRunCode() {
		return posTermRunCode;
	}

	public void setPosTermRunCode(String posTermRunCode) {
		this.posTermRunCode = posTermRunCode;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getPointRate() {
		return pointRate;
	}

	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}

	public String getHsbTransAmount() {
		return hsbTransAmount;
	}

	public void setHsbTransAmount(String hsbTransAmount) {
		this.hsbTransAmount = hsbTransAmount;
	}

	public String getRandomScrambling() {
		return randomScrambling;
	}

	public void setRandomScrambling(String randomScrambling) {
		this.randomScrambling = randomScrambling;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	//start--added by liuzh on 2016-06-22
	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public String getDeductionVoucherCount() {
		return deductionVoucherCount;
	}

	public void setDeductionVoucherCount(String deductionVoucherCount) {
		this.deductionVoucherCount = deductionVoucherCount;
	}
	//end--added by liuzh on 2016-06-22
	
}
