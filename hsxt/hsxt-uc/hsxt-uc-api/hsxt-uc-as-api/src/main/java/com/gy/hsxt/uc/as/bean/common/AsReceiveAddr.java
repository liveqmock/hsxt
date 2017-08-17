/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

/**
 * 收货地址
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsReceiveAddr 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-24 上午9:26:37 
 * @version V1.0
 */
public class AsReceiveAddr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1078898401014475429L;
	/**
	 * 客户号
	 */
	String custId;
	/**
	 * 接收人
	 */
	String receiver;
	/** 国家代码 */
	String countryNo;
	/** 省份代码 */
	String provinceNo;
	/** 城市代码 */
	String cityNo;
	/**
	 * 固定电话
	 */
	String telphone;
	/**
	 * 区域
	 */
	String area;
	/**
	 * 地址
	 */
	String address;
	/**
	 * 邮编
	 */
	String postCode;
	/**
	 * 手机号
	 */
	String mobile;
	/**
	 * 是否默认
	 */
	Integer isDefault;
	/**
	 * 地址ID
	 */
	Long addrId;

	/**
	 * @return Id
	 */
	public Long getAddrId() {
		return addrId;
	}

	/**
	 * @param Id
	 */
	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	/**
	 * 客户号
	 * 
	 * @return
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 客户号
	 * 
	 * @param custId
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 收货人
	 * 
	 * @return
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * 收货人
	 * 
	 * @param receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the 国家代码
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 国家代码
	 *            the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 省份代码
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 省份代码
	 *            the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 城市代码
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 城市代码
	 *            the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	/**
	 * @return 固定电话
	 */
	public String getTelphone() {
		return telphone;
	}

	/**
	 * @param 固定电话
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}


	/**
	 * 区域名称
	 * 
	 * @return
	 */
	public String getArea() {
		return area;
	}

	/**
	 * 区域名称
	 * 
	 * @param areaName
	 */
	public void setArea(String areaName) {
		this.area = areaName;
	}

	/**
	 * 地址名称
	 * 
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 地址名称
	 * 
	 * @param addressName
	 */
	public void setAddress(String addressName) {
		this.address = addressName;
	}

	/**
	 * 邮编
	 * 
	 * @return
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * 邮编
	 * 
	 * @param postCode
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 手机
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 手机
	 * 
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 是否默认
	 * 
	 * @return
	 */
	public Integer getIsDefault() {
		return isDefault;
	}

	/**
	 * 是否默认
	 * 
	 * @param isDefault
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
