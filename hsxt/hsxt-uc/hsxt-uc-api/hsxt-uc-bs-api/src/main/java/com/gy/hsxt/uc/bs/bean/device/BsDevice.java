/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.bean.device;

import java.io.Serializable;

/**
 * 基础设备信息
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.device
 * @ClassName: BsDevice
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午5:03:27
 * @version V1.0
 */
public class BsDevice implements Serializable {
	private static final long serialVersionUID = -1124154325941117307L;
	/** 设备客户号 */
	private String deviceCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 企业客户号 */
	private String entCustId;
	/** 设备序号（终端编号） 平台给企业定义的4位编码 */
	private String deviceNO;
	/** 机器序号 厂家定义的编号 */
	private String machineNo;
	/** 秘钥版本 */
	private String pwdVersion;
	/** 设备状态 0未启用、1 启用、2 停用、3 返修、4冻结 */
	private Integer status;
	/** 最后登录时间 */
	private String lastLoginDate;
	/** 积分比例 */
	private String[] pointRate;
	/** 积分比例版本号 */
	private String pointRateVer;
	/** 设备类型 1：POS 2：刷卡器 3：平板 */
	private Integer deviceType;

	/**
	 * 设备客户号
	 * 
	 * @return the deviceCustId
	 */
	public String getDeviceCustId() {
		return deviceCustId;
	}

	/**
	 * 设备客户号
	 * 
	 * @param deviceCustId
	 *            the deviceCustId to set
	 */
	public void setDeviceCustId(String deviceCustId) {
		this.deviceCustId = deviceCustId;
	}

	/**
	 * 企业互生号
	 * 
	 * @return
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * 企业互生号
	 * 
	 * @param entResNo
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * 终端编号
	 * 
	 * @return
	 */
	public String getDeviceNO() {
		return deviceNO;
	}

	/**
	 * 终端编号
	 * 
	 * @param deviceNO
	 */
	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
	}

	/**
	 * 机身编号
	 * 
	 * @return
	 */
	public String getMachineNo() {
		return machineNo;
	}

	/**
	 * 机身编号
	 * 
	 * @param machineNo
	 */
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	/**
	 * 密码算法版本
	 * 
	 * @return
	 */
	public String getPwdVersion() {
		return pwdVersion;
	}

	/**
	 * 密码算法版本
	 * 
	 * @param pwdVersion
	 */
	public void setPwdVersion(String pwdVersion) {
		this.pwdVersion = pwdVersion;
	}

	/**
	 * @return the 积分比例
	 */
	public String[] getPointRate() {
		return pointRate;
	}

	/**
	 * @param 积分比例
	 *            the pointRate to set
	 */
	public void setPointRate(String[] pointRate) {
		this.pointRate = pointRate;
	}

	/**
	 * 积分比例版本号
	 * 
	 * @return the pointRateVer
	 */
	public String getPointRateVer() {
		return pointRateVer;
	}

	/**
	 * 积分比例版本号
	 * 
	 * @param pointRateVer
	 *            the pointRateVer to set
	 */
	public void setPointRateVer(String pointRateVer) {
		this.pointRateVer = pointRateVer;
	}

	/**
	 * 企业互生号
	 * 
	 * @return the entCustId
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * 企业客户号
	 * 
	 * @param entCustId
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * 最后登陆时间
	 * 
	 * @return the lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * 最后登陆时间
	 * 
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the 设备状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 设备状态
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the 设备类型
	 */
	public Integer getDeviceType() {
		return deviceType;
	}

	/**
	 * @param 设备类型
	 *            the deviceType to set
	 */
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public enum BsDeviceStatusEnum {
		/** 未启用 */
		NOT_ENABLED(0),
		/** 可用 */
		ENABLED(1),
		/** 禁用 */
		DISABLED(2),
		/** 返修 */
		REPAIRED(3),
		/** 冻结 */
		LOCKED(4);

		int value;

		BsDeviceStatusEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static BsDeviceStatusEnum generateDeviceStatusEnum(int value) {
			if (value == 1)
				return BsDeviceStatusEnum.ENABLED;
			if (value == 2)
				return BsDeviceStatusEnum.DISABLED;
			if (value == 3)
				return BsDeviceStatusEnum.REPAIRED;
			return BsDeviceStatusEnum.LOCKED;
		}
	}

}
