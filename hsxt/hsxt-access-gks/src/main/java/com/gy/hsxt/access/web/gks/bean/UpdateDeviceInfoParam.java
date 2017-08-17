/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.gks.bean;

/**
 * 更新设备信息参数类
 * 
 * @Package: com.gy.hsxt.access.web.gks.bean  
 * @ClassName: UpdateDeviceInfoParam 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-12-16 上午10:34:38 
 * @version V1.0
 */
public class UpdateDeviceInfoParam {

	/**
	 * 企业客户号
	 */
	String entCustId;
	/**
	 * 企业资源号
	 */
	String entResNo;
	/**
	 * 订单编号
	 */
	String orderNo;
	/**
	 * 分类code
	 */
	String categoryCode;
	/**
	 * 设备序号，机器编号
	 */
	String deviceSeqNo;
	/**
	 * 配置单号
	 */
	String configNo;
	/**
	 * 新设备号，机器编号
	 */
	String newDeviceSeqNo;
	/**
	 * 终端编号，平台定义
	 */
	String terminalNo;
	/**
	 * 操作员编号
	 */
	String operatorNo;
	/**
	 * 设备状态
	 */
	int deviceStatus;
	
	String pmk;
	
	
	
	/**
	 * @return the pmk
	 */
	public String getPmk() {
		return pmk;
	}
	/**
	 * @param pmk the pmk to set
	 */
	public void setPmk(String pmk) {
		this.pmk = pmk;
	}
	/**
	 * @return the 设备状态
	 */
	public int getDeviceStatus() {
		return deviceStatus;
	}
	/**
	 * @param 设备状态 the deviceStatus to set
	 */
	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}
	/**
	 * @param 企业客户号 the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
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
	 * @return the 订单编号
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * @param 订单编号 the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @return the 分类code
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * @param 分类code the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * @return the 设备序号
	 */
	public String getDeviceSeqNo() {
		return deviceSeqNo;
	}
	/**
	 * @param 设备序号 the deviceSeqNo to set
	 */
	public void setDeviceSeqNo(String deviceSeqNo) {
		this.deviceSeqNo = deviceSeqNo;
	}
	/**
	 * @return the 配置单号
	 */
	public String getConfigNo() {
		return configNo;
	}
	/**
	 * @param 配置单号 the configNo to set
	 */
	public void setConfigNo(String configNo) {
		this.configNo = configNo;
	}
	/**
	 * @return the 新设备号
	 */
	public String getNewDeviceSeqNo() {
		return newDeviceSeqNo;
	}
	/**
	 * @param 新设备号 the newDeviceSeqNo to set
	 */
	public void setNewDeviceSeqNo(String newDeviceSeqNo) {
		this.newDeviceSeqNo = newDeviceSeqNo;
	}
	/**
	 * @return the 终端编号
	 */
	public String getTerminalNo() {
		return terminalNo;
	}
	/**
	 * @param 终端编号 the terminalNo to set
	 */
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	/**
	 * @return the 操作员编号
	 */
	public String getOperatorNo() {
		return operatorNo;
	}
	/**
	 * @param 操作员编号 the operatorNo to set
	 */
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	
}
