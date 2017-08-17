/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.service.ILCSBaseInfoCacheService;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl
 * 
 *  File Name       : LCSBaseInfoCacheService.java
 * 
 *  Creation Date   : 2016年6月13日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : LCS基础数据操作
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("lcsBaseInfoCacheService")
public class LCSBaseInfoCacheService implements ILCSBaseInfoCacheService {

	private Logger logger = Logger.getLogger(this.getClass());

	/** 缓存银行id和银行名称映射关系(数据量很小) **/
	private static final Map<String, String> BANK_ID_NAME_MAP = new ConcurrentHashMap<String, String>(
			50);

	/** 缓存城市id和城市名称映射关系(数据量很小) **/
	private static final Map<String, String> CITY_ID_NAME_MAP = new ConcurrentHashMap<String, String>(
			50);

	private static String currCountryCode = null;

	@Autowired
	private ILCSBaseDataService lcsBaseDataService;

	@Override
	public String queryBankNameFromLCS(String bankId) {
		// 从缓存中取
		String bankName = BANK_ID_NAME_MAP.get(bankId);

		if (!StringUtils.isEmpty(bankName)) {
			return bankName;
		}

		try {
			PayBank payBank = lcsBaseDataService.queryPayBankByCode(bankId);

			if (null != payBank) {
				bankName = payBank.getBankName();

				// 放入缓存中...
				BANK_ID_NAME_MAP.put(bankId, bankName);
			}
		} catch (Exception e) {
			logger.error("根据快捷支付银行id查询银行名称失败！！ bankId=" + bankId
					+ ", 请检查LCS服务是否正常！", e);
		}

		return bankName;
	}

	@Override
	public String queryCityNameFromLCS(String provinceCode, String cityCode) {

		if (StringUtils.isEmpty(currCountryCode)) {
			currCountryCode = getCurrCountryCode();
		}

		if (StringUtils.isExistEmpty(provinceCode, cityCode)) {
			return "";
		}

		// 城市名称
		String cityName = CITY_ID_NAME_MAP.get(cityCode);

		if (StringUtils.isEmptyTrim(cityName)) {
			// 从LCS查询城市名称
			cityName = getCityNameByCityCode(currCountryCode, provinceCode,
					cityCode);

			if (!StringUtils.isEmptyTrim(cityName)) {
				CITY_ID_NAME_MAP.put(cityCode, cityName);
			}
		}

		return cityName;
	}

	/**
	 * 获取国家代码
	 * 
	 * @return
	 */
	private String getCurrCountryCode() {

		try {
			LocalInfo localInfo = lcsBaseDataService.getLocalInfo();

			if (null != localInfo) {
				return localInfo.getCountryNo();
			}
		} catch (Exception e) {
			logger.info("从LCS查询国家代码失败：", e);
		}

		return "156";
	}

	/**
	 * 获取国家代码
	 * 
	 * @param countryCode
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	private String getCityNameByCityCode(String countryCode,
			String provinceCode, String cityCode) {

		try {
			City city = lcsBaseDataService.getCityById(countryCode,
					provinceCode, cityCode);

			if (null != city) {
				return city.getCityNameCn();
			}
		} catch (Exception e) {
			logger.info("从LCS查询城市名称失败：", e);
		}

		return "";
	}

}
