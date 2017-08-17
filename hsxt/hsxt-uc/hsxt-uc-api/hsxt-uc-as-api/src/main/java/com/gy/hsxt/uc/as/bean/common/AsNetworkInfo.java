/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 网络信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsNetworkInfo 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-24 上午9:26:06 
 * @version V1.0
 */
public class AsNetworkInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6325119820072541265L;
	/**
	 * 客户号
	 */
	String perCustId;
	/**
	 * 昵称
	 */
	String nickname;
	/** 真实姓名 */
	String name;
	/**
	 * 头像
	 */
	String headShot;
	/**
	 * 性别
	 */
	Integer sex;
	/**
	 * 年龄
	 */
	Integer age;
	/**
	 * 国家代码
	 */
	String countryNo;
	/**
	 * 省份代码
	 */
	String provinceNo;
	/**
	 * 城市代码
	 */
	String cityNo;
	/**
	 * 国家
	 */
	String country;
	/**
	 * 省份
	 */
	String province;
	/**
	 * 城市
	 */
	String city;
	/**
	 * 区域
	 */
	String area;
	/**
	 * 地址
	 */
	String address;
	/**
	 * 个人签名
	 */
	String individualSign;
	/**
	 * 工作
	 */
	String job;
	/**
	 * 爱好
	 */
	String hobby;
	/**
	 * 血型
	 */
	Integer blood;
	/**
	 * 邮编
	 */
	String postcode;
	/**
	 * email
	 */
	String email;
	/**
	 * 毕业院校
	 */
	String graduateSchool;
	/**
	 * 微信
	 */
	String weixin;
	/**
	 * QQ
	 */
	String qqNo;
	/**
	 * 电话号
	 */
	String telNo;
	/**
	 * 手机号
	 */
	String mobile;
	/**
	 * 生日
	 */
	String birthday;
	/**
	 * 家庭住址
	 */
	String homeAddr;
	/**
	 * 家庭电话
	 */
	String homePhone;
	/**
	 * 办公室电话
	 */
	String officePhone;
	/**
	 * 传真
	 */
	String officeFax;
	/**
	 * 是否显示
	 */
	Integer isShow;
	/**
	 * 修改次数
	 */
	Integer modifyCount;

	/**
	 * 昵称
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 昵称
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the 真实姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param 真实姓名
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 头像
	 */
	public String getHeadShot() {
		return headShot;
	}

	/**
	 * @param 头像
	 */
	public void setHeadShot(String headShot) {
		this.headShot = headShot;
	}

	/**
	 * 国家编号
	 * @return the countryNo
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * 国家编号
	 * @param countryNo
	 *            the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * 省份编号
	 * @return the provinceNo
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * 省份编号
	 * @param provinceNo
	 *            the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * 城市编号
	 * @return the cityNo
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * 城市编号
	 * @param cityNo
	 *            the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	/**
	 * QQ
	 * @return the qqNo
	 */
	public String getQqNo() {
		return qqNo;
	}

	/**
	 * QQ
	 * @param qqNo
	 *            the qqNo to set
	 */
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	/**
	 * 手机号
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * 手机号
	 * @param 手机号
	 *            the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * 客户号
	 * @return the perCustId
	 */
	public String getPerCustId() {
		return perCustId;
	}

	/**
	 * 客户号
	 * @param perCustId
	 *            the perCustId to set
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}

	/**
	 * 性别
	 * 
	 * @return
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * 性别
	 * 
	 * @param sex
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * 年龄
	 * 
	 * @return
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * 年龄
	 * 
	 * @param age
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * 国家名称
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 国家名称
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 省
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 省
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 城市
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 城市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 区域
	 * 
	 * @return
	 */
	public String getArea() {
		return area;
	}

	/**
	 * 区域
	 * 
	 * @param area
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 地址
	 * 
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 地址
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 个性签名
	 * 
	 * @return
	 */
	public String getIndividualSign() {
		return individualSign;
	}

	/**
	 * 个性签名
	 * 
	 * @param individualSign
	 */
	public void setIndividualSign(String individualSign) {
		this.individualSign = individualSign;
	}

	/**
	 * 职业
	 * 
	 * @return
	 */
	public String getJob() {
		return job;
	}

	/**
	 * 职业
	 * 
	 * @param job
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * 爱好
	 * 
	 * @return
	 */
	public String getHobby() {
		return hobby;
	}

	/**
	 * 爱好
	 * 
	 * @param hobby
	 */
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	/**
	 * 血型
	 * 
	 * @return
	 */
	public Integer getBlood() {
		return blood;
	}

	/**
	 * 血型
	 * 
	 * @param blood
	 */
	public void setBlood(Integer blood) {
		this.blood = blood;
	}

	/**
	 * 邮编
	 * 
	 * @return
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * 邮编
	 * 
	 * @param postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * 邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 邮箱
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 毕业院校
	 * 
	 * @return
	 */
	public String getGraduateSchool() {
		return graduateSchool;
	}

	/**
	 * 毕业院校
	 * 
	 * @param graduateSchool
	 */
	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	/**
	 * 微信号
	 * 
	 * @return
	 */
	public String getWeixin() {
		return weixin;
	}

	/**
	 * 微信号
	 * 
	 * @param weixin
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	/**
	 * 手机号码
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 手机号码
	 * 
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 生日
	 * 
	 * @return
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * 生日
	 * 
	 * @param birthday
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 常驻地址
	 * 
	 * @return
	 */
	public String getHomeAddr() {
		return homeAddr;
	}

	/**
	 * 常驻地址
	 * 
	 * @param homeAddr
	 */
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	/**
	 * 家庭电话
	 * 
	 * @return
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * 家庭电话
	 * 
	 * @param homePhone
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * 办公室电话
	 * 
	 * @return
	 */
	public String getOfficePhone() {
		return officePhone;
	}

	/**
	 * 办公室电话
	 * 
	 * @param officePhone
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	/**
	 * 办公室传真
	 * 
	 * @return
	 */
	public String getOfficeFax() {
		return officeFax;
	}

	/**
	 * 办公室传真
	 * 
	 * @param officeFax
	 */
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}

	/**
	 * 是否显示网络信息
	 * 
	 * @return
	 */
	public Integer getIsShow() {
		return isShow;
	}

	/**
	 * 是否显示网络信息
	 * 
	 * @param isShow
	 */
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	/**
	 * 网络信息修改计数（版本号）
	 * 
	 * @return
	 */
	public Integer getModifyCount() {
		return modifyCount;
	}

	/**
	 * 网络信息修改计数（版本号）
	 * 
	 * @param modifyCount
	 */
	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
