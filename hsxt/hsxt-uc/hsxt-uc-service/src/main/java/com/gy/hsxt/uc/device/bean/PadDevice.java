package com.gy.hsxt.uc.device.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gy.hsxt.uc.bs.bean.device.BsDevice.BsDeviceStatusEnum;
import com.gy.hsxt.uc.bs.bean.device.BsPad;
import com.gy.hsxt.uc.bs.enumerate.BsDeviceTypeEumn;

/**
 * pad设备
 * 
 * @Package: com.gy.hsxt.uc.device.bean
 * @ClassName: PadDevice
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-9 下午8:43:29
 * @version V1.0
 */
public class PadDevice implements Serializable {

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
	/** POS机主秘钥 */
	private String mainSecretKey;
	/** POS机秘钥版本 */
	private String pwdVersion;
	/** 设备状态1 启用、2 停用、3 返修、4冻结 备注：前3个状态为企业自己发起的，后面一个是平台发起的 */
	private Integer deviceStatus;
	/** 最后登录时间 */
	private Timestamp lastLoginDate;
	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;
	/** 创建时间创建时间，取记录创建时的系统时间 */
	private Timestamp createDate;
	/** 创建人由谁创建，值为用户的伪键ID */
	private String createdby;
	/** 更新时间更新时间，取记录更新时的系统时间 */
	private Timestamp updateDate;
	/** 更新人由谁更新，值为用户的伪键ID */
	private String updatedby;
	/** 设备积分比例 */
	private String pointRate;
	/** 积分比例版本号 */
	private String pointRateVer;

	public String getDeviceCustId() {
		return deviceCustId;
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
	 * @return the POS机主秘钥
	 */
	public String getMainSecretKey() {
		return mainSecretKey;
	}

	/**
	 * @param POS机主秘钥
	 *            the mainSecretKey to set
	 */
	public void setMainSecretKey(String mainSecretKey) {
		this.mainSecretKey = mainSecretKey;
	}

	/**
	 * @return the POS机秘钥版本
	 */
	public String getPwdVersion() {
		return pwdVersion;
	}

	/**
	 * @param POS机秘钥版本
	 *            the pwdVersion to set
	 */
	public void setPwdVersion(String pwdVersion) {
		this.pwdVersion = pwdVersion;
	}

	/**
	 * @return the 设备状态1启用、2停用、3返修、4冻结备注：前3个状态为企业自己发起的，后面一个是平台发起的
	 */
	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * @param 设备状态1启用
	 *            、2停用、3返修、4冻结备注：前3个状态为企业自己发起的，后面一个是平台发起的 the deviceStatus to
	 *            set
	 */
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	/**
	 * @return the 最后登录时间
	 */
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param 最后登录时间
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the 标记此条记录的状态Y:可用N:不可用
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * @param 标记此条记录的状态Y
	 *            :可用N:不可用 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	/**
	 * @return the 创建时间创建时间，取记录创建时的系统时间
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param 创建时间创建时间
	 *            ，取记录创建时的系统时间 the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the 创建人由谁创建，值为用户的伪键ID
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param 创建人由谁创建
	 *            ，值为用户的伪键ID the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the 更新时间更新时间，取记录更新时的系统时间
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param 更新时间更新时间
	 *            ，取记录更新时的系统时间 the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the 更新人由谁更新，值为用户的伪键ID
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param 更新人由谁更新
	 *            ，值为用户的伪键ID the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
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
	 * @param 设备客户号
	 *            the deviceCustId to set
	 */
	public void setDeviceCustId(String deviceCustId) {
		this.deviceCustId = deviceCustId;
	}

	/**
	 * 把PadDevice 转换成 BsPad
	 * 
	 * @return
	 */
	public BsPad generateBsPad() {
		BsPad pad = new BsPad();
		pad.setDeviceCustId(this.deviceCustId);
		pad.setDeviceNO(this.deviceSeq);
		pad.setDeviceType(BsDeviceTypeEumn.PAD.getType());
		pad.setEntCustId(this.entCustId);
		pad.setEntResNo(this.entResNo);
		pad.setMachineNo(this.machineNo);
		pad.setPointRate(this.pointRate.split(SEPARATOR));
		pad.setPointRateVer(this.pointRateVer);
		pad.setPmk(this.mainSecretKey);
		pad.setStatus(this.deviceStatus);
		if (this.lastLoginDate != null) {
			pad.setLastLoginDate(this.lastLoginDate.toString());
		}
		return pad;
	}

	/**
	 * 把BsPad 转换成 PadDevice
	 * 
	 * @param bsPad
	 * @param deviceCustId
	 * @param operator
	 * @return
	 */
	public static PadDevice generatePadDevice(BsPad bsPad, String deviceCustId, String operator) {
		PadDevice padDevice = new PadDevice();
		bsPad.setDeviceCustId(deviceCustId);
		bsPad.setStatus(BsDeviceStatusEnum.ENABLED.getValue());
		padDevice.setDeviceCustId(deviceCustId);
		padDevice.setDeviceSeq(bsPad.getDeviceNO());
		padDevice.setEntCustId(bsPad.getEntCustId());
		padDevice.setEntResNo(bsPad.getEntResNo());
		padDevice.setMachineNo(bsPad.getMachineNo());
		padDevice.setCreatedby(operator);
		padDevice.setUpdatedby(operator);
		return padDevice;
	}
}