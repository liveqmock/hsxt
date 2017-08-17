/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.ent.runnable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
import com.gy.hsxt.uc.util.HttpRequest;

public class UpdateEntMapLocation extends Thread {
	CommonCacheService commonCacheService;
	EntExtendInfoMapper entExtendInfoMapper;
	EntExtendInfo extendInfo;
	LcsClient lcsClient;

	public UpdateEntMapLocation(LcsClient lcsClient,
			CommonCacheService commonCacheService,
			EntExtendInfoMapper entExtendInfoMapper, EntExtendInfo extendInfo) {
		this.lcsClient = lcsClient;
		this.commonCacheService = commonCacheService;
		this.entExtendInfoMapper = entExtendInfoMapper;
		this.extendInfo = extendInfo;

	}

	@Override
	public void run() {
		if (commonCacheService == null) {
			System.err.println("UpdateEntMapLocation commonCacheService==null");
			return;
		}
		if (entExtendInfoMapper == null) {
			System.err
					.println("UpdateEntMapLocation entExtendInfoMapper==null");
			return;
		}
		if (extendInfo == null) {
			System.err.println("UpdateEntMapLocation extendInfo==null");
			return;
		}
//		Integer custType = extendInfo.getCustType();
//		if (custType == null) {
//			System.out.println("UpdateEntMapLocation custType==null");
//			return;
//		}
		// if (4 != custType) {//服务公司
		// return;
		// }
		try {
			JSONObject location = getMapLocation();
			System.out.println("----------------location="+location);
			if (location == null) {
				
				return;
			}
			
			//更新企业百度地图经纬度
			String entCustId=extendInfo.getEntCustId();
			EntExtendInfo extInfo = new EntExtendInfo();
			extInfo.setEntCustId(entCustId);
			extInfo.setLongitude(location.getString("lng"));// 经度 LONGITUDE
			extInfo.setLatitude(location.getString("lat"));// 纬度 LATITUDE
			int count=entExtendInfoMapper.updateByPrimaryKeySelective(extInfo);
			if(count==0){
				System.err.println("更新地图坐标失败："+JSON.toJSONString(extInfo));
				return;
			}
			commonCacheService.removeEntExtendInfoCache(entCustId);// 更新缓存
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取地图位置
	 * @return
	 */
	private JSONObject getMapLocation() {
		String address = extendInfo.getEntRegAddr();
		String cityName = getCityName();
		if (address == null && address == null) {
			System.out.println("UpdateEntMapLocation address==null");
			return null;
		}
		if (address == null) {
			address = cityName;
		}
		if (cityName == null) {
			cityName = address;
			;
		}
		JSONObject ret = HttpRequest
				.getLocationByBaiduMapApi(address, cityName);
		System.out.println("-----------------------------"+ret);
		if (ret == null) {
			return null;
		}
		// {"result":{"confidence":80,"level":"道路","location":{"lat":22.550696775819,"lng":114.07607551346},"precise":1},"status":0}
		JSONObject result = ret.getJSONObject("result");
//		System.out.println("-----------------------------"+result);
		if (result == null) {
			return null;
		}
		JSONObject location = result.getJSONObject("location");
		return location;
	}

	/**
	 * 获取城市名称
	 * @return
	 */
	private String getCityName() {
		String cityNo = extendInfo.getCityNo();
		String provNo = extendInfo.getProvinceNo();
		String countryNo = extendInfo.getCountryNo();
		City city = lcsClient.getCityById(countryNo, provNo, cityNo);
		if (city == null) {
			return null;
		} else {
			return city.getCityName();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
