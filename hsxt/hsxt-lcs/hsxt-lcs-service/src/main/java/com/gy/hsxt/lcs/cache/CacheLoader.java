/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.lcs.cache;

import java.util.List;
import java.util.Map;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.lcs.LcsConstant;
import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.CountryCurrency;
import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.bean.EnumItem;
import com.gy.hsxt.lcs.bean.ErrorMsg;
import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.bean.PromptMsg;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.bean.Unit;

/**
 * 缓存数据装载工具 LCS客户端优先从redis缓存中获取，如果缓存中获取不到，则调LCS接口查询，查询时进行缓存装载。
 * 当总平台全局配置中心更新数据，地区平台同步更新后也需要重新装载相关数据。
 * 
 * @Package: com.gy.hsxt.lcs.service
 * @ClassName: CacheLoader
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-11 下午4:18:24
 * @version V1.0
 */
public class CacheLoader {

    /**
     * redis客户端
     */
    @SuppressWarnings("rawtypes")
    private RedisUtil redisUtil;

    @SuppressWarnings("rawtypes")
    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @SuppressWarnings("rawtypes")
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 加载有效国家列表
     * 
     * @param list
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadContryAll(List<Country> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_COUNTRY);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_COUNTRY, list, Country.class);
    }

    /**
     * 加载指定国家下级有效省份列表
     * 
     * @param countryNo
     *            国家代码
     * @param list
     *            下级省份列表
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadProvinceForParent(String countryNo, List<Province> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + countryNo + "." + LcsConstant.ALL_PROVINCE);
        if (list != null && !list.isEmpty())
        {
            redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + countryNo + "." + LcsConstant.ALL_PROVINCE, list,
                    Province.class);
        }
    }

    /**
     * 加载指定省份下级全部城市列表
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省份代码
     * @param list
     *            下级城市列表
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadCityForParent(String countryNo, String provinceNo, List<City> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + countryNo + "." + provinceNo + "." + LcsConstant.ALL_CITY);
        if (list != null && !list.isEmpty())
        {
            redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + countryNo + "." + provinceNo + "." + LcsConstant.ALL_CITY,
                    list, City.class);
        }
    }

    /**
     * 加载枚举组
     * 
     * @param languageCode
     * @param groupCode
     * @param list
     */
    @SuppressWarnings("unchecked")
    public void loadEnumItemByGroup(String languageCode, String groupCode, List<EnumItem> list) {
        redisUtil.add(LcsConstant.CACHE_KEY_PREFIX + languageCode + "." + LcsConstant.ALL_ENUM_GROUP, groupCode, list);
    }

    /**
     * 加载提示信息缓存
     * 
     * @param languageCode
     *            语言代码
     * @param promptCode
     *            提示信息代码
     * @param promptMsg
     *            提示信息
     */
    @SuppressWarnings("unchecked")
    public void loadPromptMsg(String languageCode, String promptCode, PromptMsg promptMsg) {
        redisUtil.add(LcsConstant.CACHE_KEY_PREFIX + languageCode + "." + LcsConstant.ALL_PROMPT_MSG, promptCode,
                promptMsg);
    }

    /**
     * 加载错误信息缓存
     * 
     * @param languageCode
     *            语言代码
     * @param errorCode
     *            错误代码
     * @param errorMsg
     *            错误信息
     */
    @SuppressWarnings("unchecked")
    public void loadErrorMsg(String languageCode, String errorCode, ErrorMsg errorMsg) {
        redisUtil.add(LcsConstant.CACHE_KEY_PREFIX + languageCode + "." + LcsConstant.ALL_ERROR_MSG, errorCode,
                errorMsg);
    }

    /**
     * 加载计量单位列表，语言分类，每一种语言一个列表
     * 
     * @param languageCode
     * @param list
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadUnitByLanguage(String languageCode, List<Unit> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + languageCode + "." + LcsConstant.ALL_UNIT);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + languageCode + "." + LcsConstant.ALL_UNIT, list, Unit.class);
    }

    /**
     * 加载资源号路由映射关系
     * 
     * @param map
     *            资源号(前5位)与平台代码的映射关系
     */
    @SuppressWarnings("unchecked")
    public void loadResNoRouteMap(Map<String, String> map) {
        redisUtil.add(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.RES_ROUTE_MAP, map);
    }

    /**
     * 加载当前平台信息
     * 
     * @param localInfo
     */
    @SuppressWarnings("unchecked")
    public void loadLocalInfo(LocalInfo localInfo) {
        redisUtil.add(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.LOCAL_INFO, localInfo);
    }

    /**
     * 加载银联所有支付列表
     * 
     * @param list
     *            全部支付银行列表
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadPayBankAll(List<PayBank> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_PAYBANK);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_PAYBANK, list, PayBank.class);
    }

    /**
     * 加载所有转账银行列表
     * 
     * @param list
     *            全部转账银行列表
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadTransBankAll(List<Bank> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_TRANSBANK);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_TRANSBANK, list, Bank.class);
    }

    /**
     * 加载所有货币信息
     * 
     * @param list
     *            全部有效货币列表
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadCurrencyAll(List<Currency> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_CURRENCY);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_CURRENCY, list, Currency.class);
    }

    /**
     * 加载语言列表，语言分类，每一种语言一个列表
     * 
     * @param languageCode
     * @param list
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadLanguageAll(List<Language> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_LANGUAGE);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_LANGUAGE, list, Language.class);
    }

    /**
     * 加载国家常用货币信息
     * 
     * @param list
     *            国家常用货币列表
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadCountryCurrencyAll(List<CountryCurrency> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.COUNTRY_CURRENCY);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.COUNTRY_CURRENCY, list, CountryCurrency.class);
    }

    /**
     * 加载所有有效平台信息到缓存
     * 
     * @param list
     */
    @SuppressWarnings("unchecked")
    public synchronized void loadPlatAll(List<Plat> list) {
        redisUtil.remove(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_PLAT);
        redisUtil.addList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_PLAT, list, Plat.class);
    }
}
