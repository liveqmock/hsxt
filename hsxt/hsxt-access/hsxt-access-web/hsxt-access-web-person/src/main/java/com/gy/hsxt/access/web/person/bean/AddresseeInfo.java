package com.gy.hsxt.access.web.person.bean;
		
/***************************************************************************
 * <PRE>
 * Description 		: 收件人信息实体
 *
 * Project Name   	: hsxt-access-web-person
 *
 * Package Name     : com.gy.hsxt.access.web.person.bean
 *
 * File Name        : AddresseeInfo
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-9-6 下午2:55:26  
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-9-6 下午2:55:26
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
public class AddresseeInfo {
	/** 姓名*/
	private String name;	
	
	/** 手机 */
	private String mobile;	
	
	/** 固定电话 */
	private String phone;	
	
	/** 省份Code */
	private String provinceCode;
	
	/** 城市Code */
	private String cityCode;
	
	/**  区县Code	 */
	private String countyCode;
	
	/** 详细地址	 */
	private String address;
	
	/** 邮编 */
	private String zipCode;

	/**
	 * 获取姓名
	 * @return name 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * @param name 姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取手机
	 * @return mobile 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * @param mobile 手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取固定电话
	 * @return phone 固定电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置固定电话
	 * @param phone 固定电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取省份Code
	 * @return provinceCode 省份Code
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * 设置省份Code
	 * @param provinceCode 省份Code
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * 获取城市Code
	 * @return cityCode 城市Code
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * 设置城市Code
	 * @param cityCode 城市Code
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 获取区县Code
	 * @return countyCode 区县Code
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * 设置区县Code
	 * @param countyCode 区县Code
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * 获取详细地址
	 * @return address 详细地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置详细地址
	 * @param address 详细地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 * @return zipCode 邮编
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * @param zipCode 邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
}

	