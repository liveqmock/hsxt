/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.keyserver;

import java.io.Serializable;

/**
 * 设备的秘钥信息
 * 
 * @Package: com.gy.hsxt.uc.cache.model  
 * @ClassName: DeviceTokenCache 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-10-29 下午5:02:38 
 * @version V1.0
 */
public class DeviceToken implements Serializable{
	private static final long serialVersionUID = -1605885458965377477L;

	private byte[] pik;
	private byte[] pmk;
	private byte[] mak;
	private byte[] pikmak;
	 
	/**
	 * @return the pik
	 */
	public byte[] getPik() {
		return pik;
	}
	/**
	 * @param pik the pik to set
	 */
	public void setPik(byte[] pik) {
		this.pik = pik;
	}
	/**
	 * @return the pmk
	 */
	public byte[] getPmk() {
		return pmk;
	}
	/**
	 * @param pmk the pmk to set
	 */
	public void setPmk(byte[] pmk) {
		this.pmk = pmk;
	}
	/**
	 * @return the mak
	 */
	public byte[] getMak() {
		return mak;
	}
	/**
	 * @param mak the mak to set
	 */
	public void setMak(byte[] mak) {
		this.mak = mak;
	}
	public byte[] getPikmak() {
		return pikmak;
	}
	public void setPikmak(byte[] pikmak) {
		this.pikmak = pikmak;
	}
	
	

}
