/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 资源统计对象
 * @author 作者 : 毛粲
 * @ClassName: 类名:ReportsResourceStatistics
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午2:35:52
 * @version 1.0
 */
public class ReportsResourceStatistics implements Serializable{


	private static final long serialVersionUID = -7919797944981742586L;

	/** 客户全局编号 **/
	private String     custId;
	
	/** 互生号  **/
	private String     hsResNo;
	
	/** 客户类型 **/
	private Integer    custType;
	
	/** 企业名称 **/
	private String		entCustName;
	
	/** 系统资源使用数 **/
	private String		systemResourceUsageNumber;
	
	/** 实名注册数 **/
	private Integer		registrationAuthNumber;
	
	/** 实名认证数 **/
	private Integer		realnameAuthNumber;
	
	/** 激活数 **/
	private Integer		activationNumber;
	
	/** 活跃数 **/
	private Integer		activeNumber;
	
	/** 上级服务公司资源号 **/
	private String		serviceEntResNo;
	
	/** 上级管理公司资源号 **/
	private String		adminEntResNo;
	
	/** 上级地方平台资源号 **/
	private String		platformEntResNo;
	
	/** 系统开启时间开始 **/
	private String		openDateBegin;
	
	/** 系统开启时间结束 **/
	private String		openDateEnd;
	
	/** 系统开启时间开始Timestamp **/
	private Timestamp	openDateBeginTim;
	
	/** 系统开启时间结束Timestamp **/
	private Timestamp	openDateEndTim;
	
	
	/**
	 * @return the 客户全局编号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户全局编号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 互生号
	 */
	public String getHsResNo() {
		return hsResNo; 
	}

	/**
	 * @param 互生号 the hsResNo to set
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	/**
	 * @return the 客户类型
	 */
	public Integer getCustType() {
		return custType;
	}

	/**
	 * @param 客户类型 the custType to set
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	/**
	 * @return the 企业名称
	 */
	public String getEntCustName() {
		return entCustName;
	}

	/**
	 * @param 企业名称 the entCustName to set
	 */
	public void setEntCustName(String entCustName) {
		this.entCustName = entCustName;
	}

	/**
	 * @return the 系统资源使用数
	 */
	public String getSystemResourceUsageNumber() {
		return systemResourceUsageNumber;
	}

	/**
	 * @param 系统资源使用数 the systemResourceUsageNumber to set
	 */
	public void setSystemResourceUsageNumber(String systemResourceUsageNumber) {
		this.systemResourceUsageNumber = systemResourceUsageNumber;
	}

	/**
	 * @return the 实名注册数
	 */
	public Integer getRegistrationAuthNumber() {
		return registrationAuthNumber;
	}

	/**
	 * @param 实名注册数 the registrationAuthNumber to set
	 */
	public void setRegistrationAuthNumber(Integer registrationAuthNumber) {
		this.registrationAuthNumber = registrationAuthNumber;
	}

	/**
	 * @return the 实名认证数
	 */
	public Integer getRealnameAuthNumber() {
		return realnameAuthNumber;
	}

	/**
	 * @param 实名认证数 the realnameAuthNumber to set
	 */
	public void setRealnameAuthNumber(Integer realnameAuthNumber) {
		this.realnameAuthNumber = realnameAuthNumber;
	}

	/**
	 * @return the 激活数
	 */
	public Integer getActivationNumber() {
		return activationNumber;
	}

	/**
	 * @param 激活数 the activationNumber to set
	 */
	public void setActivationNumber(Integer activationNumber) {
		this.activationNumber = activationNumber;
	}

	/**
	 * @return the 活跃数
	 */
	public Integer getActiveNumber() {
		return activeNumber;
	}

	/**
	 * @param 活跃数 the activeNumber to set
	 */
	public void setActiveNumber(Integer activeNumber) {
		this.activeNumber = activeNumber;
	}

	/**
	 * @return the 上级服务公司资源号
	 */
	public String getServiceEntResNo() {
		return serviceEntResNo;
	}

	/**
	 * @param 上级服务公司资源号 the serviceEntResNo to set
	 */
	public void setServiceEntResNo(String serviceEntResNo) {
		this.serviceEntResNo = serviceEntResNo;
	}

	/**
	 * @return the 上级管理公司资源号
	 */
	public String getAdminEntResNo() {
		return adminEntResNo;
	}

	/**
	 * @param 上级管理公司资源号 the adminEntResNo to set
	 */
	public void setAdminEntResNo(String adminEntResNo) {
		this.adminEntResNo = adminEntResNo;
	}

	/**
	 * @return the 上级地方平台资源号
	 */
	public String getPlatformEntResNo() {
		return platformEntResNo;
	}

	/**
	 * @param 上级地方平台资源号 the platformEntResNo to set
	 */
	public void setPlatformEntResNo(String platformEntResNo) {
		this.platformEntResNo = platformEntResNo;
	}

	/**
	 * @return the 系统开启时间开始
	 */
	public String getOpenDateBegin() {
		return openDateBegin;
	}

	/**
	 * @param 系统开启时间开始 the openDateBegin to set
	 */
	public void setOpenDateBegin(String openDateBegin) {
		this.openDateBegin = openDateBegin;
	}

	/**
	 * @return the 系统开启时间结束
	 */
	public String getOpenDateEnd() {
		return openDateEnd;
	}

	/**
	 * @param 系统开启时间结束 the openDateEnd to set
	 */
	public void setOpenDateEnd(String openDateEnd) {
		this.openDateEnd = openDateEnd;
	}

	/**
	 * @return the 系统开启时间开始Timestamp
	 */
	public Timestamp getOpenDateBeginTim() {
		return openDateBeginTim;
	}

	/**
	 * @param 系统开启时间开始Timestamp the openDateBeginTim to set
	 */
	public void setOpenDateBeginTim(Timestamp openDateBeginTim) {
		this.openDateBeginTim = openDateBeginTim;
	}

	/**
	 * @return the 系统开启时间结束Timestamp
	 */
	public Timestamp getOpenDateEndTim() {
		return openDateEndTim;
	}

	/**
	 * @param 系统开启时间结束Timestamp the openDateEndTim to set
	 */
	public void setOpenDateEndTim(Timestamp openDateEndTim) {
		this.openDateEndTim = openDateEndTim;
	}
	
}
