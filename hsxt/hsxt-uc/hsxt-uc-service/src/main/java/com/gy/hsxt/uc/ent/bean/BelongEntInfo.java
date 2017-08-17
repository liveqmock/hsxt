/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.ent.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

/**
 * 隶属企业信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.ent
 * @ClassName: BelongEntInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午3:17:30
 * @version V1.0
 */
public class BelongEntInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 企业客户号 */
	private String entCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 企业名称 */
	private String entName;
	/** 企业英文名称 */
	private String entNameEn;
	/** 企业地址 */
	private String entAddr;
	/** 联系人 */
	private String contactPerson;
	/** 联系人电话 */
	private String contactPhone;
	/** 企业注册日期 */
	private String buildDate;
	/** 系统开通日期 */
	private Timestamp openDate;
	/** 实名认证状态 1.已认证、0.未认证 */
	private String realNameAuthSatus;
	/** 企业启用的消费者数量 */
	private Long custNum;
	/**
	 * 积分活动状态
	 * 
	 * 1：正常 成员企业、托管企业 2：预警 成员企业、托管企业
	 * 
	 * 3：休眠 成员企业 4：长眠 成员企业
	 * 
	 * 5：已注销 成员企业6：申请停止积分活动中 托管企业
	 * 
	 * 7：停止积分活动 托管企业 8：注销申请中 成员企业
	 */
	private Integer pointStatus;

	/**
	 * 企业客户类型
	 * 
	 * 2 成员企业;3 托管企业;4 服务公司;5 管理公司;
	 * 
	 * 6 地区平台;7 总平台; 8 其它地区平台52 非互生格式化企业
	 */
	private Integer entCustType;
	
	/** 所在国家 */
	private String countryNo;
	/** 所在省份 */
	private String provinceNo;
	/** 所在城市 */
	private String cityNo;
	/** 启用资源类型 */
	private Integer artResTypest;
	/** 经营类型 */
	private Integer businessType;
	/** 所在城市 */
	private String nature;

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
	 * @return the 企业名称
	 */
	public String getEntName() {
		return entName;
	}

	/**
	 * @param 企业名称
	 *            the entName to set
	 */
	public void setEntName(String entName) {
		this.entName = entName;
	}

	/**
	 * @return the 企业英文名称
	 */
	public String getEntNameEn() {
		return entNameEn;
	}

	/**
	 * @param 企业英文名称
	 *            the entNameEn to set
	 */
	public void setEntNameEn(String entNameEn) {
		this.entNameEn = entNameEn;
	}

	/**
	 * @return the 企业地址
	 */
	public String getEntAddr() {
		return entAddr;
	}

	/**
	 * @param 企业地址
	 *            the entAddr to set
	 */
	public void setEntAddr(String entAddr) {
		this.entAddr = entAddr;
	}

	/**
	 * @return the 联系人
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param 联系人
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the 联系人电话
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param 联系人电话
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the 企业注册日期
	 */
	public String getBuildDate() {
		return buildDate;
	}

	/**
	 * @param 企业注册日期
	 *            the buildDate to set
	 */
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * @return the 系统开通日期
	 */
	public Timestamp getOpenDate() {
		return openDate;
	}

	/**
	 * @param 系统开通日期
	 *            the openDate to set
	 */
	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	/**
	 * @return the 实名认证状态1.已认证、0.未认证
	 */
	public String getRealNameAuthSatus() {
		return realNameAuthSatus;
	}

	/**
	 * @param 实名认证状态1
	 *            .已认证、0.未认证 the realNameAuthSatus to set
	 */
	public void setRealNameAuthSatus(String realNameAuthSatus) {
		this.realNameAuthSatus = realNameAuthSatus;
	}

	/**
	 * @return the 企业启用的消费者数量
	 */
	public Long getCustNum() {
		return custNum;
	}

	/**
	 * @param 企业启用的消费者数量
	 *            the custNum to set
	 */
	public void setCustNum(Long custNum) {
		this.custNum = custNum;
	}

	public Integer getPointStatus() {
		return pointStatus;
	}

	public void setPointStatus(Integer pointStatus) {
		this.pointStatus = pointStatus;
	}

	public Integer getEntCustType() {
		return entCustType;
	}

	public void setEntCustType(Integer entCustType) {
		this.entCustType = entCustType;
	}
	
	

	/**
	 * @return the 所在国家
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 所在国家 the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 所在省份
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 所在省份 the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 所在城市
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 所在城市 the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public Integer getArtResTypest() {
		return artResTypest;
	}

	public void setArtResTypest(Integer artResTypest) {
		this.artResTypest = artResTypest;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}
	
	
}
