package com.gy.hsxt.uc.device.bean;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.gy.hsxt.uc.bs.bean.device.BsCardReader;
import com.gy.hsxt.uc.bs.bean.device.BsDevice.BsDeviceStatusEnum;
import com.gy.hsxt.uc.bs.enumerate.BsDeviceTypeEumn;

/**
 * 刷卡器器设备
 * 
 * @Package: com.gy.hsxt.uc.device.bean
 * @ClassName: CardReaderDevice
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-9 下午7:53:28
 * @version V1.0
 */
public class CardReaderDevice implements Serializable {
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
	/** 刷卡器类型 刷卡器类型1:积分刷卡器 2:消费刷卡器 */
	private Integer swipeType;
	/** KSN码1 */
	private String ksnCode1; //ksnCode1
	/** KSN码2 */
	private String ksnCode2;
	/** KSN码3 */
	private String ksnCode3;
	/** KSN码密文1 */
	private String ciphertext1; //cipherText1
	/** KSN码密文2 */
	private String ciphertext2;
	/** KSN码密文3 */
	private String ciphertext3;
	/** KSN码验证1 */
	private String ksnValid1;
	/** KSN码验证2 */
	private String ksnValid2;
	/** KSN码验证3 */
	private String ksnValid3;
	/** 秘钥版本 */
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
	 * @return the 刷卡器类型刷卡器类型1:积分刷卡器2:消费刷卡器
	 */
	public Integer getSwipeType() {
		return swipeType;
	}

	/**
	 * @param 刷卡器类型刷卡器类型1
	 *            :积分刷卡器2:消费刷卡器 the swipeType to set
	 */
	public void setSwipeType(Integer swipeType) {
		this.swipeType = swipeType;
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
	public String getCiphertext1() {
		return ciphertext1;
	}

	/**
	 * @param KSN码密文1
	 *            the ciphertext1 to set
	 */
	public void setCiphertext1(String ciphertext1) {
		this.ciphertext1 = ciphertext1;
	}

	/**
	 * @return the KSN码密文2
	 */
	public String getCiphertext2() {
		return ciphertext2;
	}

	/**
	 * @param KSN码密文2
	 *            the ciphertext2 to set
	 */
	public void setCiphertext2(String ciphertext2) {
		this.ciphertext2 = ciphertext2;
	}

	/**
	 * @return the KSN码密文3
	 */
	public String getCiphertext3() {
		return ciphertext3;
	}

	/**
	 * @param KSN码密文3
	 *            the ciphertext3 to set
	 */
	public void setCiphertext3(String ciphertext3) {
		this.ciphertext3 = ciphertext3;
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

	/**
	 * @return the 设备客户号
	 */
	public String getPwdVersion() {
		return pwdVersion;
	}

	/**
	 * @param 设备客户号
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
	 * 生成BsCardReader对象
	 * 
	 * @return
	 */
	public BsCardReader generateBsCardReader() {
		BsCardReader carderReader = new BsCardReader();
		carderReader.setDeviceCustId(this.deviceCustId);
		carderReader.setDeviceNO(this.deviceSeq);
		carderReader.setDeviceType(BsDeviceTypeEumn.CARD_READER.getType());
		carderReader.setEntCustId(this.entCustId);
		carderReader.setEntResNo(this.entResNo);
		carderReader.setMachineNo(this.machineNo);
		if(StringUtils.isNotBlank(this.pointRate)){
			carderReader.setPointRate(this.pointRate.split(SEPARATOR));
		}
		carderReader.setPointRateVer(this.pointRateVer);
		carderReader.setStatus(this.deviceStatus);
		if (this.lastLoginDate != null) {
			carderReader.setLastLoginDate(this.lastLoginDate.toString());
		}
		return carderReader;
	}

	/**
	 * 通过CardReaderDevice 通过 BsCardReader对象
	 * 
	 * @param bsCr
	 * @param deviceCustId
	 * @param operator
	 * @return
	 */
	public static CardReaderDevice generateCardReaderDevice(BsCardReader bsCr, String deviceCustId,
			String operator) {
		CardReaderDevice cr = new CardReaderDevice();
		// 更新设备信息
		BeanUtils.copyProperties(bsCr, cr);
		
		cr.setSwipeType(bsCr.getCardReaderType());//刷卡器类型
		
		
		
		cr.setDeviceCustId(deviceCustId); //设备客户号
		cr.setDeviceSeq(bsCr.getDeviceNO()); //设备编号
		cr.setMachineNo(bsCr.getMachineNo()); // 设备机器码
		cr.setCiphertext1(bsCr.getCipherText1());
		cr.setCiphertext2(bsCr.getCipherText2());
		cr.setCiphertext3(bsCr.getCipherText3());
//		cr.setEntCustId(bsCr.getEntCustId());
//		cr.setEntResNo(bsCr.getEntResNo());
		cr.setUpdatedby(operator);
		cr.setUpdateDate(getNowTimestamp());
		cr.setCreatedby(operator);
		cr.setDeviceStatus(BsDeviceStatusEnum.ENABLED.getValue());
//		cr.setPointRate(bsCr.getPointRate());
		bsCr.setDeviceCustId(deviceCustId);
		bsCr.setStatus(BsDeviceStatusEnum.ENABLED.getValue());
		return cr;
	}
}