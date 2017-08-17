package com.gy.hsxt.uc.device.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 积分比例
 * 
 * @Package: com.gy.hsxt.uc.device.bean
 * @ClassName: PointRate
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-9 下午8:49:43
 * @version V1.0
 */
public class PointRate implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业客户号或者设备客户号 */
	private String custId;
	/** 企业互生号 */
	private String entResNo;
	/** 企业客户号 */
	private String entCustId;
	/** 设备序号（终端编号 4位数字 由平台定义） */
	private String deviceSeq;
	/** 设备积分比例 */
	private String pointRate;
	/** 积分比例版本号 */
	private Integer modifyAcount;
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

	/**
	 * @return the 企业客户号或者设备客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 企业客户号或者设备客户号
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
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
	public Integer getModifyAcount() {
		return modifyAcount;
	}

	/**
	 * @param 积分比例版本号
	 *            the modifyAcount to set
	 */
	public void setModifyAcount(Integer modifyAcount) {
		this.modifyAcount = modifyAcount;
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
	 * 把Device 对象转换成PointRate对象
	 * 
	 * @param device
	 * @param operator
	 * @return
	 */
	public static PointRate generatePointRate(Device device, String operator) {
		PointRate pointRate = new PointRate();
		pointRate.setCreatedby(operator);
		pointRate.setCustId(device.getDeviceCustId());
		pointRate.setDeviceSeq(device.getDeviceSeq());
		pointRate.setEntCustId(device.getEntCustId());
		pointRate.setEntResNo(device.getEntResNo());
		pointRate.setPointRate(device.getPointRate());
		pointRate.setUpdatedby(operator);
		return pointRate;

	}
}