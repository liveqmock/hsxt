/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.gks.bean;

public class UpdateDeviceStatusParam {
	/**
	 * 企业资源号
	 */
	String entResNo;
	/**
	 * 设备编号
	 */
	String deviceNo;
	/**
	 * POS机机器号
	 */
	String terminalNo;
	/**
	 * 状态
	 */
	String status;
	/**
	 * 操作员
	 */
	String operator;
	/**
	 * 企业编号
	 */
	String entCustId;
	/**
	 * 配置编号
	 */
	String confNo;
	
	/**
	 * PMK
	 */
	String pmk;
	
	
	
	/**
	 * @return the PMK
	 */
	public String getPmk() {
		return pmk;
	}
	/**
	 * @param PMK the pmk to set
	 */
	public void setPmk(String pmk) {
		this.pmk = pmk;
	}
	/**
	 * @return the 配置编号
	 */
	public String getConfNo() {
		return confNo;
	}
	/**
	 * @param 配置编号 the confNo to set
	 */
	public void setConfNo(String confNo) {
		this.confNo = confNo;
	}
	/**
	 * @return the 企业资源号
	 */
	public String getEntResNo() {
		return entResNo;
	}
	/**
	 * @param 企业资源号 the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}
	/**
	 * @return the 设备编号
	 */
	public String getDeviceNo() {
		return deviceNo;
	}
	/**
	 * @param 设备编号 the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	/**
	 * @return the POS机机器号
	 */
	public String getTerminalNo() {
		return terminalNo;
	}
	/**
	 * @param POS机机器号 the terminalNo to set
	 */
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	/**
	 * @return the 状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param 状态 the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the 操作员
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param 操作员 the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @return the 企业编号
	 */
	public String getEntCustId() {
		return entCustId;
	}
	/**
	 * @param 企业编号 the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}
	
	
}
