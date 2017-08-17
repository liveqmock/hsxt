/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;
/**
 * 网络信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsNetworkInfoMini 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-25 下午2:11:53 
 * @version V1.0
 */
public class AsNetworkInfoMini implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1955549809016579519L;
	/**
	 * 客户号
	 * 
	 */
	String perCustId;
	/**
	 * 昵称
	 * 
	 */
	String nickname;
	/**
	 * 头像
	 * 
	 */
	String headShot;
	/**
	 * 国家代码
	 * 
	 */
	String countryNo;
	/**
	 * 省份代码
	 * 
	 */
    String provinceNo;
    /**
     * 市代码
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
	 * @return the 客户号
	 */
	public String getPerCustId() {
		return perCustId;
	}

	/**
	 * @param 客户号 the perCustId to set
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}

	/**
	 * @return the 昵称
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param 昵称 the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the 头像
	 */
	public String getHeadShot() {
		return headShot;
	}

	/**
	 * @param 头像 the headShot to set
	 */
	public void setHeadShot(String headShot) {
		this.headShot = headShot;
	}

	/**
	 * @return the 国家代码
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 国家代码 the countryNo to set
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
	 * @param 省份代码 the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 市代码
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 市代码 the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	/**
	 * 国家
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 国家
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 省
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 省
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 城市
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 城市
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}



}
