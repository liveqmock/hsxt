package com.gy.hsxt.access.web.person.bean;

/**
 * 收件信息
 * @author zhucy
 *
 */
public class ReceiveAddress {

	/** 地址编号（ID） **/
	private String id;
	/** 收件人姓名 **/
	private String receiverName;
	/** 收件人手机 **/
	private String phone;
	/** 收件人固定电话 **/
	private String fixedTelephone;
	/** 收件人所在省 **/
	private String province;
	/** 所在省编号 **/
	private String provinceCode;
	/** 收件人所在市**/
	private String city;
	/** 所在城市编号 **/
	private String cityCode;
	/** 收件人所在区/县**/
	private String area;
	/** 所在区县编号 **/
	private String areaCode;
	/** 收件人详细地址 **/
	private String address;
	/** 收件人邮编 **/
	private String postcode;
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFixedTelephone() {
		return fixedTelephone;
	}
	public void setFixedTelephone(String fixedTelephone) {
		this.fixedTelephone = fixedTelephone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}
