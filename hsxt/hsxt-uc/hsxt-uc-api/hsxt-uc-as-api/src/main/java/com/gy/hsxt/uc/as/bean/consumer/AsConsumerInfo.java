/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消费者资源信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.consumer
 * @ClassName: AsConsumerInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-11 下午3:55:20
 * @version V1.0
 */
public class AsConsumerInfo implements Serializable {
	private static final long serialVersionUID = 1219844536794436405L;
	/** 消费者客户号 */
	private String custId;
	/** 消费互生号 */
	private String perResNo;
	/** 企业互生号 */
	private String entResNO;
	/** 消费者姓名 */
	private String realName;
	/** 性别1：男 0：女 */
	private Integer sex;
	/** 国家代码 */
	private String countryCode;
	/** 基本状态1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销 */
	private Integer baseStatus;
	/** 实名注册完成时间 */
	private String realnameAuthDate;
	/** 实名注册完成时间 */
	private String realnameRegDate;
	/** 开卡时间 */
	private String createDate;
	/** 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 */
	private Integer realnameStatus;
	/** 卡状态1：启用、2：停用、3：挂失 */
	private Integer cardStatus;
	/** 手机号码 */
	private String mobile;
	/** 户籍地址 */
	private String birthAddress;
	/** 证件类型 */
	private Integer idType;
	/** 证件号码 */
	private String idNo;
	
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the 消费者客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 消费者客户号
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 消费互生号
	 */
	public String getPerResNo() {
		return perResNo;
	}

	/**
	 * @param 消费互生号
	 *            the perResNo to set
	 */
	public void setPerResNo(String perResNo) {
		this.perResNo = perResNo;
	}

	/**
	 * @return the 消费者姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param 消费者姓名
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the 企业互生号
	 */
	public String getEntResNO() {
		return entResNO;
	}

	/**
	 * @param 企业互生号
	 *            the entResNO to set
	 */
	public void setEntResNO(String entResNO) {
		this.entResNO = entResNO;
	}

	/**
	 * @return the 性别1：男0：女
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param 性别1
	 *            ：男0：女 the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return the 卡状态1：启用、2：停用、3：挂失
	 */
	public Integer getCardStatus() {
		return cardStatus;
	}

	/**
	 * @param 卡状态1
	 *            ：启用、2：停用、3：挂失 the cardStatus to set
	 */
	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the 国家代码
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param 国家代码
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the 基本状态1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销
	 */
	public Integer getBaseStatus() {
		return baseStatus;
	}

	/**
	 * @param 基本状态1
	 *            ：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销 the baseStatus to set
	 */
	public void setBaseStatus(Integer baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 */
	public Integer getRealnameStatus() {
		return realnameStatus;
	}

	/**
	 * @param 实名状态1
	 *            ：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 the realnameStatus to set
	 */
	public void setRealnameStatus(Integer realnameStatus) {
		this.realnameStatus = realnameStatus;
	}

	/**
	 * @return the 手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param 手机号码
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the 户籍地址
	 */
	public String getBirthAddress() {
		return birthAddress;
	}

	/**
	 * @param 户籍地址
	 *            the birthAddress to set
	 */
	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}

	/**
	 * @return the 实名注册完成时间
	 */
	public String getRealnameAuthDate() {
		return realnameAuthDate;
	}

	/**
	 * @param 实名注册完成时间
	 *            the realnameAuthDate to set
	 */
	public void setRealnameAuthDate(String realnameAuthDate) {
		this.realnameAuthDate = realnameAuthDate;
	}

	/**
	 * @return the 实名注册完成时间
	 */
	public String getRealnameRegDate() {
		return realnameRegDate;
	}

	/**
	 * @param 实名注册完成时间
	 *            the realnameRegDate to set
	 */
	public void setRealnameRegDate(String realnameRegDate) {
		this.realnameRegDate = realnameRegDate;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	

}
