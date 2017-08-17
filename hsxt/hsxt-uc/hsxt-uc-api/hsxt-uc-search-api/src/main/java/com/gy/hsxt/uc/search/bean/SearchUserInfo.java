/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户信息对象
 * 
 * @Package: com.gy.hsxt.uc.index.bean
 * @ClassName: UserInfo
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-16 下午4:37:42
 * @version V1.0
 */
public class SearchUserInfo extends Search implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 3180953862534064730L;

	public final static String CORE_NAME = "userInfo";
	/**
	 * 用户名
	 */
	@Field("username")
	String username;
	
	@Field("age")
	Integer age;
	
	/**
	 * 名称
	 */
	@Field("name")
	String name;
	/**
	 * 企业资源号
	 */
	@Field("entResNo")
	String entResNo;

	/**
	 * 上一级或所属企业资源号
	 */
	@Field("parentEntResNo")
	String parentEntResNo;
	/**
	 * 真实姓名
	 */
	@Field("realName")
	String realName;
	
	/**
	 * 性别
	 */
	@Field("sex")
	String sex;
	/**
	 * 头像
	 */
	@Field("headImage")
	String headImage;
	/**
	 * 地址
	 */
	@Field("area")
	String area;
	
	@Field("city")
	String city;
	
	@Field("province")
	String province;
	/**
	 * 爱好
	 */
	@Field("hobby")
	String hobby;
	/**
	 * 签名
	 */
	@Field("sign")
	String sign;

	/**
	 * 手机号
	 */
	@Field("mobile")
	String mobile;
	/**
	 * 客户号
	 */
	@Field("custId")
	String custId;

	/**
	 * 互生号
	 */
	@Field("resNo")
	String resNo;

	/**
	 * 昵称
	 */
	@Field("nickName")
	String nickName;
	/**
	 * 用户类型
	 */
	@Field("userType")
	Integer userType;
	/** 真实职务*/
	@Field("operDuty")
	String operDuty;
	/** 企业客户号*/
	@Field("entCustId")
	String entCustId;
	/** 操作员的手机号 */
	@Field("operPhone")
	String operPhone;
	/** 操作员名称 */
	@Field("operName")
	String operName;
	@Field("operEmail")
	/** 操作员email */
	String operEmail;
	
	String keyword;
	
	String ageScope;
	
	/**
	 * 是否已登录
	 */
	@Field("isLogin")
	Integer isLogin;
	
	List<String> usernames;
	/**
	 * 多个客户号
	 */
	List<String> custIds;
	/**
	 * 用户角色
	 */
	List<SearchUserRole> userRole;
	
	/** 搜索类型  */
	Integer searchType = 0;
	
	/** 是否允许搜索 */
	@Field("isAddFirend")
	String isAddFirend;
	/**
	 * 搜索好友类型；0  不允许搜索；1  模糊搜索；2持卡人允许使用精确互生号搜索；3非持卡人使用精确手机号搜索
	 */
	@Field("searchMode")
	String searchMode;

	/**
	 * @return the 是否允许搜索
	 */
	public String getIsAddFirend() {
		return isAddFirend;
	}

	/**
	 * @param 是否允许搜索 the isAddFirend to set
	 */
	public void setIsAddFirend(String isAddFirend) {
		this.isAddFirend = isAddFirend;
	}

	/**
	 * @return the 搜索好友类型；0不允许搜索；1模糊搜索；2持卡人允许使用精确互生号搜索；3非持卡人使用精确手机号搜索
	 */
	public String getSearchMode() {
		return searchMode;
	}

	/**
	 * @param 搜索好友类型；0不允许搜索；1模糊搜索；2持卡人允许使用精确互生号搜索；3非持卡人使用精确手机号搜索 the searchMode to set
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * @return the 是否已登录 
	 */
	public Integer getIsLogin() {
		return isLogin;
	}

	/**
	 * @param 是否已登录 the isLogin to set
	 */
	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * @return the 多个客户号
	 */
	public List<String> getCustIds() {
		return custIds;
	}

	/**
	 * @param 多个客户号 the custIds to set
	 */
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}

	/**
	 * @return the 用户角色
	 */
	public List<SearchUserRole> getUserRole() {
		return userRole;
	}

	/**
	 * @param 用户角色 the userRole to set
	 */
	public void setUserRole(List<SearchUserRole> userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the 真实职务
	 */
	public String getOperDuty() {
		return operDuty;
	}

	/**
	 * @param 真实职务 the operDuty to set
	 */
	public void setOperDuty(String operDuty) {
		this.operDuty = operDuty;
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
	 * @return the 操作员的手机号
	 */
	public String getOperPhone() {
		return operPhone;
	}

	/**
	 * @param 操作员的手机号 the operPhone to set
	 */
	public void setOperPhone(String operPhone) {
		this.operPhone = operPhone;
	}

	/**
	 * @return the 操作员名称
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * @param 操作员名称 the operName to set
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * @return the 操作员email
	 */
	public String getOperEmail() {
		return operEmail;
	}

	/**
	 * @param 操作员email the operEmail to set
	 */
	public void setOperEmail(String operEmail) {
		this.operEmail = operEmail;
	}

	/**
	 * @return the 搜索类型
	 */
	public Integer getSearchType() {
		return searchType;
	}

	/**
	 * @param 搜索类型 the searchType to set
	 */
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the usernames
	 */
	public List<String> getUsernames() {
		return usernames;
	}

	/**
	 * @param usernames the usernames to set
	 */
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	/**
	 * @return the ageScope
	 */
	public String getAgeScope() {
		return ageScope;
	}

	/**
	 * @param ageScope the ageScope to set
	 */
	public void setAgeScope(String ageScope) {
		this.ageScope = ageScope;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param 用户名 the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param 名称 the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the 上一级所属企业资源号
	 */
	public String getParentEntResNo() {
		return parentEntResNo;
	}

	/**
	 * @param 上一级所属企业资源号 the parentEntResNo to set
	 */
	public void setParentEntResNo(String parentEntResNo) {
		this.parentEntResNo = parentEntResNo;
	}

	/**
	 * @return the 真实姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param 真实姓名 the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the 性别
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param 性别 the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the 头像
	 */
	public String getHeadImage() {
		return headImage;
	}

	/**
	 * @param 头像 the headImage to set
	 */
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	
	/**
	 * @return the 地址
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param 地址 the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return 城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param 城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return 省份
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param 省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the 爱好
	 */
	public String getHobby() {
		return hobby;
	}

	/**
	 * @param 爱好 the hobby to set
	 */
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	/**
	 * @return the 签名
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param 签名 the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the 手机号
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param 手机号
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the 客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户号
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 互生号
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * @param 互生号
	 *            the resNo to set
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * @return the 昵称
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param 昵称
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the 用户类型
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * @param 用户类型
	 *            the userType to set
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
