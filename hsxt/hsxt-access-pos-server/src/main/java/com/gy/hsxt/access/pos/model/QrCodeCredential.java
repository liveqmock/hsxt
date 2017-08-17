package com.gy.hsxt.access.pos.model;

/**
 * 交易凭据二维码数据结构
 * @author liuzh
 *  “交易凭据二维码”字符串原始定义：(2位字母数字)类型&(2位字母数字)交易类别&12位数字的交易流水号明文
 */
public class QrCodeCredential {
	
	/**
	 * 类型  (2位字母数字)
	 */
	private String qrType;
	
	/**
	 * 交易类别  (2位字母数字)
	 */
	private String tradeType;
	
	/**
	 * 交易流水号明文 12位
	 */
	private String tradeRunCode;

	
	public QrCodeCredential() {
		super();
	}

	public String getQrType() {
		return qrType;
	}

	public void setQrType(String qrType) {
		this.qrType = qrType;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeRunCode() {
		return tradeRunCode;
	}

	public void setTradeRunCode(String tradeRunCode) {
		this.tradeRunCode = tradeRunCode;
	}

	
}
