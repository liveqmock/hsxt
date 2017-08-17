package com.gy.hsxt.access.pos.model;

/**
 * pos固件升级版本检测请求报文
 * @author liuzh
 *
 */
public class PosUpgradeCheckReq {
	/**
	 * pos设备类型
	 */
	private String posDeviceType;
	
	/**
	 * pos机机器号
	 */
	private String posMachineNo;
	
	/**
	 * pos设备当前固件版本号
	 */
	private String posCurrVerNo;

	public String getPosDeviceType() {
		return posDeviceType;
	}

	public void setPosDeviceType(String posDeviceType) {
		this.posDeviceType = posDeviceType;
	}

	public String getPosCurrVerNo() {
		return posCurrVerNo;
	}

	public void setPosCurrVerNo(String posCurrVerNo) {
		this.posCurrVerNo = posCurrVerNo;
	}

	public String getPosMachineNo() {
		return posMachineNo;
	}

	public void setPosMachineNo(String posMachineNo) {
		this.posMachineNo = posMachineNo;
	}
	
}
