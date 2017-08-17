/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.bs.bean.ent;

import java.io.Serializable;

public class BsEntInfo implements Serializable {

	private static final long serialVersionUID = 5144860523238290616L;
	private String entCustId;
	private String entResNo;
	private String countryNo;
	private String provinceNo;
	private String cityNo;
	private String entName;
	private String entNameEn;
	private String contactPerson;
	private String contactPhone;
	private String entRegAddr;
	/** 企业经度 */
	private String longitude;
	/** 企业纬度 */
	private String latitude;
	public String getEntCustId() {
		return entCustId;
	}
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}
	public String getEntResNo() {
		return entResNo;
	}
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}
	public String getCountryNo() {
		return countryNo;
	}
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}
	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public String getEntNameEn() {
		return entNameEn;
	}
	public void setEntNameEn(String entNameEn) {
		this.entNameEn = entNameEn;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getEntRegAddr() {
		return entRegAddr;
	}
	public void setEntRegAddr(String entRegAddr) {
		this.entRegAddr = entRegAddr;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
