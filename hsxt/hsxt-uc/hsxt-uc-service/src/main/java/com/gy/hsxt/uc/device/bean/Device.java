/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.device.bean;

import java.io.Serializable;

import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.device.AsDevice;
import com.gy.hsxt.uc.bs.bean.device.BsDevice;

/**
 * 设备抽象类
 * 
 * @Package: com.gy.hsxt.uc.device.bean
 * @ClassName: Device
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-30 上午9:53:03
 * @version V1.0
 */
public class Device implements Serializable {
	private static final String SEPARATOR = "\\|";
	private static final long serialVersionUID = 1L;
	/** 设备客户号 */
	private String deviceCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 企业客户号 */
	private String entCustId;
	/** 设备序号（终端编号 4位数字 由平台定义） */
	private String deviceSeq;
	/** 设备机器码 由设备生产厂家定义 */
	private String machineNo;
	/** 设备状态 0 未启用 1 启用、2 停用、3 返修、4冻结备注：未启用、冻结 是平台发起的 其它是企业自己发起的 */
	private Integer deviceStatus;
	/** 设备积分比例 */
	private String pointRate;
	/** 积分比例版本号 */
	private String pointRateVer;
	/** 设备类型 */
	private Integer deviceType;

	/**
	 * @return the 设备客户号
	 */
	public String getDeviceCustId() {
		return deviceCustId;
	}

	/**
	 * @param 设备客户号
	 *            the deviceCustId to set
	 */
	public void setDeviceCustId(String deviceCustId) {
		this.deviceCustId = deviceCustId;
	}

	/**
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 设备序号（终端编号4位数字由平台定义）
	 */
	public String getDeviceSeq() {
		return deviceSeq;
	}

	/**
	 * @param 设备序号
	 *            （终端编号4位数字由平台定义） the deviceSeq to set
	 */
	public void setDeviceSeq(String deviceSeq) {
		this.deviceSeq = deviceSeq;
	}

	/**
	 * @return the 设备机器码由设备生产厂家定义
	 */
	public String getMachineNo() {
		return machineNo;
	}

	/**
	 * @param 设备机器码由设备生产厂家定义
	 *            the machineNo to set
	 */
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	/**
	 * @return the 设备状态 0 未启用 1 启用、2 停用、3 返修、4冻结 备注：未启用、冻结 是平台发起的 其它是企业自己发起的
	 */
	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * @param 设备状态
	 *            0 未启用 1 启用、2 停用、3 返修、4冻结 备注：未启用、冻结 是平台发起的 其它是企业自己发起的 the
	 *            deviceStatus to set
	 */
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	/**
	 * @return the 设备积分比例
	 */
	public String getPointRate() {
		return pointRate;
	}

	/**
	 * @param 设备积分比例
	 *            the pointRate to set
	 */
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}

	/**
	 * @return the 积分比例版本号
	 */
	public String getPointRateVer() {
		return pointRateVer;
	}

	/**
	 * @param 积分比例版本号
	 *            the pointRateVer to set
	 */
	public void setPointRateVer(String pointRateVer) {
		this.pointRateVer = pointRateVer;
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
	 * 生成AsDevice view对象
	 * 
	 * @return
	 */
	public AsDevice generateAsDevice() {
		AsDevice device = new AsDevice();
		device.setDeviceCustId(this.deviceCustId);
		device.setDeviceNO(this.deviceSeq);
		device.setDeviceType(this.deviceType);
		device.setEntCustId(this.entCustId);
		device.setEntResNo(this.entResNo);
		device.setMachineNo(this.machineNo);
		device.setStatus(this.deviceStatus);
		if(!StringUtils.isBlank(this.pointRate) && this.pointRate.contains("|")){
			device.setPointRate(this.pointRate.split(SEPARATOR));
		}
		return device;
	}


	/**
	 * 生成BsDevice view对象
	 * 
	 * @return
	 */
	public BsDevice generateBsDevice() {
		BsDevice device = new BsDevice();
		device.setDeviceCustId(this.deviceCustId);
		device.setDeviceNO(this.deviceSeq);
		device.setDeviceType(this.deviceType);
		device.setEntCustId(this.entCustId);
		device.setEntResNo(this.entResNo);
		device.setMachineNo(this.machineNo);
		device.setStatus(this.deviceStatus);
		device.setPointRate(this.pointRate.split(SEPARATOR));
		return device;
	}
}
