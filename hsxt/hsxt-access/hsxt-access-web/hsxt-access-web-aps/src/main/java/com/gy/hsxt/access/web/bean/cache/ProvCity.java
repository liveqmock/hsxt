package com.gy.hsxt.access.web.bean.cache;

import java.util.LinkedHashMap;
import java.util.List;

import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.client.ProvinceTree;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.bean.cache
 * @className     : ProvCity.java
 * @description   : 国家-省份-城市缓存对象
 * @author        : maocy
 * @createDate    : 2015-12-31
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
public class ProvCity {
	
	/**国家名称*/
	private String countryName;
	
	/**国家代码*/
	private String countryCode;
	
	/**省份列表*/
	private LinkedHashMap<String, String> provMap = new LinkedHashMap<String, String>();
	
	/**城市列表*/
	private LinkedHashMap<String, LinkedHashMap<String, String>> cityMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
	
	public ProvCity() {
		super();
	}

	/**
	 * 构造函数
	 * @param countryName 国家名称
	 * @param countryCode 国家代码
	 * @param provList 省份-城市列表
	 */
	public ProvCity(String countryName, String countryCode, List<ProvinceTree> provList) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
		for(ProvinceTree tree: provList){
			LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
			List<City> cityList = tree.getCitys();
			if(cityList != null){
				for(City city : cityList){
					tempMap.put(city.getCityNo(), city.getCityName());
				}
			}
			this.cityMap.put(tree.getProvince().getProvinceNo(), tempMap);
			this.provMap.put(tree.getProvince().getProvinceNo(), tree.getProvince().getProvinceName());
		}
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public LinkedHashMap<String, String> getProvMap() {
		return provMap;
	}

	public void setProvMap(LinkedHashMap<String, String> provMap) {
		this.provMap = provMap;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getCityMap() {
		return cityMap;
	}

	public void setCityMap(
			LinkedHashMap<String, LinkedHashMap<String, String>> cityMap) {
		this.cityMap = cityMap;
	}

}
