/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.LCSErrorCode;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
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
import com.gy.hsxt.lcs.bean.ResNoRoute;
import com.gy.hsxt.lcs.bean.Unit;
import com.gy.hsxt.lcs.cache.CacheLoader;
import com.gy.hsxt.lcs.interfaces.IBankService;
import com.gy.hsxt.lcs.interfaces.ICityService;
import com.gy.hsxt.lcs.interfaces.ICountryCurrencyService;
import com.gy.hsxt.lcs.interfaces.ICountryService;
import com.gy.hsxt.lcs.interfaces.IEnumItemService;
import com.gy.hsxt.lcs.interfaces.IErrorMsgService;
import com.gy.hsxt.lcs.interfaces.ILanguageService;
import com.gy.hsxt.lcs.interfaces.IPayBankService;
import com.gy.hsxt.lcs.interfaces.IPlatService;
import com.gy.hsxt.lcs.interfaces.IPromptMsgService;
import com.gy.hsxt.lcs.interfaces.IProvinceService;
import com.gy.hsxt.lcs.interfaces.IResNoRouteService;
import com.gy.hsxt.lcs.interfaces.IUnitService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : BaseDataService.java
 * 
 *  Creation Date   : 2015-8-20
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 地区平台基础数据服务
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("baseDataService")
public class BaseDataService implements ILCSBaseDataService {

    @Autowired
    CacheLoader cacheLoader;

    @Autowired
    private ICountryService countryService;

    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ICityService cityService;

    @Autowired
    private IEnumItemService enumItemService;

    @Autowired
    private IPromptMsgService promptMsgService;

    @Autowired
    private IErrorMsgService errorMsgService;

    @Autowired
    private IUnitService unitService;

    @Autowired
    private IPlatService platService;

    @Autowired
    private IPayBankService payBankService;

    @Autowired
    private IBankService bankService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    IResNoRouteService resNoRouteService;

    @Autowired
    ICountryCurrencyService countryCurrencyService;

    @Autowired
    ILanguageService languageService;

    /********************** 国家、省、城市查询 ********************************/
    @Override
    public List<Country> findContryAll() throws HsException {
        List<Country> list = countryService.findContryAll();
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadContryAll(list);
        return list;
    }

    @Override
    public Country getContryById(String countryNo) throws HsException {
        if (countryNo == null || countryNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "国家代码不应该为空");
        }
        return countryService.getCountry(countryNo);
    }

    @Override
    public List<Province> queryProvinceByParent(String countryNo) throws HsException {
        if (countryNo == null || countryNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "国家代码不应该为空");
        }
        List<Province> list = provinceService.findProvinceByParent(countryNo);
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadProvinceForParent(countryNo, list);
        return list;
    }

    @Override
    public Province getProvinceById(String countryNo, String provinceNo) throws HsException {
        if (countryNo == null || countryNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "国家代码不应该为空");
        }
        if (provinceNo == null || provinceNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "省份代码不应该为空");
        }
        return provinceService.getProvince(countryNo, provinceNo);
    }

    @Override
    public List<City> queryCityByParent(String countryNo, String provinceNo) throws HsException {
        if (countryNo == null || countryNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "国家代码不应该为空");
        }
        if (provinceNo == null || provinceNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "省份代码不应该为空");
        }
        List<City> list = cityService.queryCityByParent(countryNo, provinceNo);
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadCityForParent(countryNo, provinceNo, list);
        return list;
    }

    @Override
    public City getCityById(String countryNo, String provinceNo, String cityNo) throws HsException {
        if (countryNo == null || countryNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "国家代码不应该为空");
        }
        if (provinceNo == null || provinceNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "省代码不应该为空");
        }
        if (cityNo == null || cityNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "城市代码不应该为空");
        }
        return cityService.getCity(countryNo, provinceNo, cityNo);
    }

    /********************** 国家、省、城市查询 ********************************/

    /******************************* 枚举查询 ********************************/
    @Override
    public List<EnumItem> queryEnumItemByGroup(String languageCode, String groupCode) throws HsException {
        if (languageCode == null || languageCode.isEmpty() || groupCode == null || groupCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码或者枚举组代码不应该为空");
        }
        List<EnumItem> list = enumItemService.queryEnumItemByGroup(languageCode, groupCode);
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadEnumItemByGroup(languageCode, groupCode, list);
        return list;
    }

    @Override
    public List<EnumItem> queryEnumItemByGroupExclude(String languageCode, String groupCode,
            List<String> excludeItemCodes) throws HsException {
        if (languageCode == null || languageCode.isEmpty() || groupCode == null || groupCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码或者枚举组代码不应该为空");
        }
        return enumItemService.queryEnumItemByGroupExclude(languageCode, groupCode, excludeItemCodes);
    }

    @Override
    public List<EnumItem> queryEnumItemByGroupInclude(String languageCode, String groupCode,
            List<String> includeItemCodes) throws HsException {
        if (languageCode == null || languageCode.isEmpty() || groupCode == null || groupCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码或者枚举组代码不应该为空");
        }
        return enumItemService.queryEnumItemByGroupInclude(languageCode, groupCode, includeItemCodes);
    }

    @Override
    public EnumItem getEnumItemById(String languageCode, String groupCode, String itemCode) throws HsException {
        if (languageCode == null || languageCode.isEmpty() || groupCode == null || groupCode.isEmpty()
                || itemCode == null || itemCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码、枚举组代码或者枚举值代码不应该为空");
        }
        return enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode);
    }

    /******************************* 枚举查询 ********************************/

    /******************************* 提示、错误信息查询 ********************************/
    @Override
    public String getPromptMsg(String languageCode, String promptCode) throws HsException {
        if (languageCode == null || languageCode.isEmpty() || promptCode == null || promptCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码或者提示信息代码不应该为空");
        }
        PromptMsg result = promptMsgService.queryPromptMsgWithPK(languageCode, promptCode);
        if (result == null)
        {
            return null;
        }
        else
        {
            // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
            cacheLoader.loadPromptMsg(languageCode, promptCode, result);
            return result.getPromptMsg();
        }
    }

    @Override
    public String getErrorMsg(String languageCode, int errorCode) throws HsException {
        if (languageCode == null || languageCode.isEmpty() || errorCode == 0)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码或者错误代码不应该为空");
        }
        ErrorMsg result = errorMsgService.queryErrorMsgWithPK(languageCode, errorCode);
        if (result == null)
        {
            return null;
        }
        else
        {
            // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
            cacheLoader.loadErrorMsg(languageCode, "" + errorCode, result);
            return result.getErrorMsg();
        }
    }

    /******************************* 提示、错误信息查询 ********************************/

    /******************************* 计量单位查询 ********************************/
    @Override
    public List<Unit> queryUnitByLanguage(String languageCode) throws HsException {
        if (languageCode == null || languageCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码不应该为空");
        }
        List<Unit> list = unitService.queryUnitByLanguage(languageCode);
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadUnitByLanguage(languageCode, list);
        return list;
    }

    @Override
    public Unit getUnitById(String languageCode, String unitCode) throws HsException {
        if (languageCode == null || languageCode.isEmpty() || unitCode == null || unitCode.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "语言代码或者计量单位代码不应该为空");
        }
        return unitService.queryUnitWithPK(languageCode, unitCode);
    }

    /******************************* 计量单位查询 ********************************/

    @Override
    public String getPlatByResNo(String resNo) throws HsException {
        if (resNo == null || resNo.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "服务资源号不应该为空");
        }
        ResNoRoute resNoRoute = resNoRouteService.queryResNoRouteWithPK(resNo.substring(0, 5));
        if (null == resNoRoute || resNoRoute.isDelFlag())
        {
            throw new HsException(LCSErrorCode.DATA_NOT_FOUND.getCode(), "没有找到有效的路由目标平台代码");
        }
        return resNoRoute.getPlatNo();
    }

    @Override
    public Map<String, String> getResNoRouteMap() {
        Map<String, String> map = new HashMap<String, String>();
        List<ResNoRoute> list = resNoRouteService.getResNoRouteList();
        for (ResNoRoute resNoRoute : list)
        {
            map.put(resNoRoute.getResNo(), resNoRoute.getPlatNo());
        }
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadResNoRouteMap(map);
        return map;
    }

    @Override
    public boolean isAcrossPlat(String perResNo) throws HsException {
        String platNo = getPlatByResNo(perResNo);
        LocalInfo localInfo = platService.getLocalInfo();
        return platNo.equals(localInfo.getPlatNo());
    }

    @Override
    public LocalInfo getLocalInfo() throws HsException {
        LocalInfo localInfo = platService.getLocalInfo();
        if (localInfo == null)
        {
            throw new HsException(LCSErrorCode.DATA_NOT_FOUND.getCode(), "获取本地平台初始化数据失败");
        }
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadLocalInfo(localInfo);
        return localInfo;
    }

    /**
     * 查询银联所有支付列表
     * 
     * @return
     * @see com.gy.hsxt.lcs.api.IPubParamService#queryBankAll()
     */
    public List<PayBank> queryPayBankAll() {
        List<PayBank> list = payBankService.queryPayBankAll();
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadPayBankAll(list);
        return list;
    }

    /**
     * 根据银行简码查询银行信息
     * 
     * @param bankCode
     * @return
     * @see com.gy.hsxt.lcs.api.IPubParamService#queryPayBankByCode(java.lang.String)
     */
    public PayBank queryPayBankByCode(String bankCode) {
        return payBankService.queryPayBankByCode(bankCode);
    }

    @Override
    public List<Bank> queryBankAll() {
        List<Bank> list = bankService.getBankList();
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadTransBankAll(list);
        return list;
    }

    @Override
    public String getBankName(String bankNo) {
        return bankService.getBankName(bankNo);
    }

    /**
     * 查询所有货币信息
     * 
     * @return
     * @see com.gy.hsxt.lcs.api.IPubParamService#queryCurrencyAll()
     */
    public List<Currency> queryCurrencyAll() {
        List<Currency> list = currencyService.queryCurrencyAll();
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadCurrencyAll(list);
        return list;
    }

    /**
     * 根据货币代码查询货币信息
     * 
     * @param currencyCode
     * @return
     * @see com.gy.hsxt.lcs.api.IPubParamService#queryCurrencyByCode(java.lang.String)
     */
    public Currency queryCurrencyByCode(String currencyCode) {
        return currencyService.queryCurrencyByCode(currencyCode);
    }

    /**
     * 查询当前地区平台常用六种货币
     * 
     * @return
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#queryCountryCurrency()
     */
    public List<CountryCurrency> queryCountryCurrency() {
        List<CountryCurrency> list = countryCurrencyService.queryCountryCurrency();
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadCountryCurrencyAll(list);
        return list;
    }

    @Override
    public Currency getCurrencyById(String currencyNo) {
        return currencyService.getCurrencyById(currencyNo);
    }

    /**
     * 查询全部有效语言列表
     * 
     * @return
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#queryLanguageAll()
     */
    @Override
    public List<Language> queryLanguageAll() {
        List<Language> list = languageService.queryLanguageAll();
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadLanguageAll(list);
        return list;
    }

    /**
     * 获取所有有效平台信息
     * 
     * @return
     * @throws HsException
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#queryPlatAll()
     */
    @Override
    public List<Plat> queryPlatAll() throws HsException {
        List<Plat> list = platService.queryPlatAll();
        // 通过dubbo调用LCS接口时加载缓存，故理论上第一次查询时加载缓存
        cacheLoader.loadPlatAll(list);
        return list;
    }

    /**
     * 查询增量国家列表
     * 
     * @param version
     *            当前版本号
     * @return
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#findCountryForDelta(int)
     */
    @Override
    public List<Country> findCountryForDelta(long version) {
        List<Country> list = countryService.findCountryForDelta(version);
        return list;
    }
}
