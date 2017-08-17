/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.device;

/**
 * 刷卡器设备
 * 
 * @Package: com.gy.hsxt.uc.as.bean.device
 * @ClassName: AsCardReader
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午5:03:27
 * @version V1.0
 */
public class AsCardReader extends AsDevice {
	private static final long serialVersionUID = -2859990455733591298L;
	/** 刷卡器类型 1:积分刷卡器 2:消费刷卡器 */
	private Integer cardReaderType;
	/** KSN码1 */
	private String ksnCode1;
	/** KSN码2 */
	private String ksnCode2;
	/** KSN码3 */
	private String ksnCode3;
	/** KSN码密文1 */
	private String cipherText1;
	/** KSN码密文2 */
	private String cipherText2;
	/** KSN码密文3 */
	private String cipherText3;
	/** KSN码验证1 */
	private String ksnValid1;
	/** KSN码验证2 */
	private String ksnValid2;
	/** KSN码验证3 */
	private String ksnValid3;

	/**
	 * @return the 刷卡器类型1:积分刷卡器2:消费刷卡器
	 */
	public Integer getCardReaderType() {
		return cardReaderType;
	}

	/**
	 * @param 刷卡器类型1
	 *            :积分刷卡器2:消费刷卡器 the cardReaderType to set
	 */
	public void setCardReaderType(Integer cardReaderType) {
		this.cardReaderType = cardReaderType;
	}

	/**
	 * @return the KSN码1
	 */
	public String getKsnCode1() {
		return ksnCode1;
	}

	/**
	 * @param KSN码1
	 *            the ksnCode1 to set
	 */
	public void setKsnCode1(String ksnCode1) {
		this.ksnCode1 = ksnCode1;
	}

	/**
	 * @return the KSN码2
	 */
	public String getKsnCode2() {
		return ksnCode2;
	}

	/**
	 * @param KSN码2
	 *            the ksnCode2 to set
	 */
	public void setKsnCode2(String ksnCode2) {
		this.ksnCode2 = ksnCode2;
	}

	/**
	 * @return the KSN码3
	 */
	public String getKsnCode3() {
		return ksnCode3;
	}

	/**
	 * @param KSN码3
	 *            the ksnCode3 to set
	 */
	public void setKsnCode3(String ksnCode3) {
		this.ksnCode3 = ksnCode3;
	}

	/**
	 * @return the KSN码密文1
	 */
	public String getCipherText1() {
		return cipherText1;
	}

	/**
	 * @param KSN码密文1
	 *            the cipherText1 to set
	 */
	public void setCipherText1(String cipherText1) {
		this.cipherText1 = cipherText1;
	}

	/**
	 * @return the KSN码密文2
	 */
	public String getCipherText2() {
		return cipherText2;
	}

	/**
	 * @param KSN码密文2
	 *            the cipherText2 to set
	 */
	public void setCipherText2(String cipherText2) {
		this.cipherText2 = cipherText2;
	}

	/**
	 * @return the KSN码密文3
	 */
	public String getCipherText3() {
		return cipherText3;
	}

	/**
	 * @param KSN码密文3
	 *            the cipherText3 to set
	 */
	public void setCipherText3(String cipherText3) {
		this.cipherText3 = cipherText3;
	}

	/**
	 * @return the KSN码验证1
	 */
	public String getKsnValid1() {
		return ksnValid1;
	}

	/**
	 * @param KSN码验证1
	 *            the ksnValid1 to set
	 */
	public void setKsnValid1(String ksnValid1) {
		this.ksnValid1 = ksnValid1;
	}

	/**
	 * @return the KSN码验证2
	 */
	public String getKsnValid2() {
		return ksnValid2;
	}

	/**
	 * @param KSN码验证2
	 *            the ksnValid2 to set
	 */
	public void setKsnValid2(String ksnValid2) {
		this.ksnValid2 = ksnValid2;
	}

	/**
	 * @return the KSN码验证3
	 */
	public String getKsnValid3() {
		return ksnValid3;
	}

	/**
	 * @param KSN码验证3
	 *            the ksnValid3 to set
	 */
	public void setKsnValid3(String ksnValid3) {
		this.ksnValid3 = ksnValid3;
	}

}
