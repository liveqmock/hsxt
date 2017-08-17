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

package com.gy.hsxt.lcs.client;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.lcs.LCSErrorCode;
import com.gy.hsxt.lcs.LcsConstant;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 地区平台基础数据查询客户端 地区平台基础数据查询接口客户端，优先从redis公共缓存中查询，
 * 当在公共缓存中查不到数据时通过dubbo调用LCS远程接口查询。
 * 
 * @Package: com.gy.hsxt.client
 * @ClassName: LcsClient
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-10 下午6:51:37
 * @version V1.0
 */
public class LcsClient implements ILCSBaseDataService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * redis缓存工具类
     */
    @SuppressWarnings("rawtypes")
    private RedisUtil redisUtil;

    /**
     * LCS的dubbo接口，直接调用LCS远程服务用
     */
    private ILCSBaseDataService baseDataService;

    @SuppressWarnings("rawtypes")
    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @SuppressWarnings("rawtypes")
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public ILCSBaseDataService getBaseDataService() {
        return baseDataService;
    }

    public void setBaseDataService(ILCSBaseDataService baseDataService) {
        this.baseDataService = baseDataService;
    }

    /**
     * 查询国家列表
     * 
     * @return
     * @exception HsException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Country> findContryAll() throws HsException {

        List<Country> list = redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_COUNTRY, Country.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有国家列表");
            // 直接调dubbo接口查询
            list = baseDataService.findContryAll();
        }
        return list;
    }

    /**
     * 根据国家代码获取国家信息
     * 
     * @param countryNo
     *            国家代码
     * @return
     * @throws HsException
     */
    @Override
    public Country getContryById(String countryNo) throws HsException {
        List<Country> list = findContryAll();
        if (list != null)
        {
            for (Country country : list)
            {
                if (countryNo.equals(country.getCountryNo()))
                {
                    return country;
                }
            }
        }
        return null;
    }

    /**
     * 查询国家查询下级省列表
     * 
     * @param countryNo
     *            国家代码
     * @return
     * @throws HsException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Province> queryProvinceByParent(String countryNo) throws HsException {
        List<Province> list = redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + countryNo + "."
                + LcsConstant.ALL_PROVINCE, Province.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有[" + countryNo + "]下的省份列表");
            // 直接调dubbo接口查询
            list = baseDataService.queryProvinceByParent(countryNo);
        }
        return list;
    }

    /**
     * 根据国家代码 +省代码查询省信息
     * 
     * @param countryNo
     * @param provinceNo
     *            省代码
     * @return
     * @throws HsException
     */
    @Override
    public Province getProvinceById(String countryNo, String provinceNo) throws HsException {
        List<Province> list = queryProvinceByParent(countryNo);
        if (list != null)
        {
            for (Province province : list)
            {
                // 因为每个国家的身份列表是一个单独的key-list存储，key已经进行过国家过滤了，只需要比较省份代码即可
                if (provinceNo.equals(province.getProvinceNo()))
                {
                    return province;
                }
            }
        }
        return null;
    }

    /**
     * 查询省下级城市列表
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @return
     * @throws HsException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<City> queryCityByParent(String countryNo, String provinceNo) throws HsException {
        List<City> list = redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + countryNo + "." + provinceNo + "."
                + LcsConstant.ALL_CITY, City.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有[" + countryNo + "][" + provinceNo + "]下的城市列表");
            // 直接调dubbo接口查询
            list = baseDataService.queryCityByParent(countryNo, provinceNo);
        }
        return list;
    }

    /**
     * 根据国家代码+省代码+城市代码查询城市信息
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @return
     * @throws HsException
     */
    @Override
    public City getCityById(String countryNo, String provinceNo, String cityNo) throws HsException {
        List<City> list = queryCityByParent(countryNo, provinceNo);
        if (list != null)
        {
            for (City city : list)
            {
                // 因为每个国家省份下的城市列表是一个单独的key-list存储，key已经进行过国家省份过滤了，只需要比较城市代码即可
                if (cityNo.equals(city.getCityNo()))
                {
                    return city;
                }
            }
        }
        return null;
    }

    /**
     * 按语言代码查询计量单位列表
     * 
     * @param languageCode
     *            语言代码
     * @return
     * @throws HsException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Unit> queryUnitByLanguage(String languageCode) throws HsException {
        List<Unit> list = redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + languageCode + "." + LcsConstant.ALL_UNIT,
                Unit.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有[" + languageCode + "]语言的计量单位列表");
            // 如果缓存中没有，就调dubbo接口取
            list = baseDataService.queryUnitByLanguage(languageCode);
        }
        return list;
    }

    /**
     * 根据语言代码+计量单位代码查询计量单位名称接口
     * 
     * @param languageCode
     *            语言代码
     * @param unitCode
     *            计量单位代码
     * @return
     * @throws HsException
     */
    @Override
    public Unit getUnitById(String languageCode, String unitCode) throws HsException {
        List<Unit> list = queryUnitByLanguage(languageCode);
        if (list != null)
        {
            for (Unit unit : list)
            {
                if (unitCode.equals(unit.getUnitCode()))
                {
                    return unit;
                }
            }
        }
        return null;
    }

    /**
     * 根据语言代码+提示信息代码查询提示信息内容接口
     * 
     * @param languageCode
     *            语言代码
     * @param promptCode
     *            提示信息代码
     * @return
     * @throws HsException
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getPromptMsg(String languageCode, String promptCode) throws HsException {
        PromptMsg promptMsg = (PromptMsg) redisUtil.get(LcsConstant.CACHE_KEY_PREFIX + languageCode + "."
                + LcsConstant.ALL_PROMPT_MSG, promptCode, PromptMsg.class);
        if (promptMsg == null)
        {
            logger.debug("缓存中没有[" + languageCode + "]语言[" + promptCode + "]代码的提示信息");
            // 如果缓存中没有，就调dubbo接口取
            return baseDataService.getPromptMsg(languageCode, promptCode);
        }
        // 如果该提示信息被逻辑删除了，返回null
        return promptMsg.isDelFlag() ? null : promptMsg.getPromptMsg();
    }

    /**
     * 根据语言代码+提示错误代码查询错误信息内容接口
     * 
     * @param languageCode
     *            语言代码
     * @param errorCode
     *            错误代码
     * @return
     * @throws HsException
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getErrorMsg(String languageCode, int errorCode) throws HsException {
        ErrorMsg errorMsg = (ErrorMsg) redisUtil.get(LcsConstant.CACHE_KEY_PREFIX + languageCode + "."
                + LcsConstant.ALL_ERROR_MSG, "" + errorCode, ErrorMsg.class);
        if (errorMsg == null)
        {
            logger.debug("缓存中没有[" + languageCode + "]语言[" + errorCode + "]代码的错误信息");
            // 如果缓存中没有，就调dubbo接口取
            return baseDataService.getErrorMsg(languageCode, errorCode);
        }
        // 如果该错误信息被逻辑删除了，返回null
        return errorMsg.isDelFlag() ? null : errorMsg.getErrorMsg();
    }

    /**
     * 根据语言代码+枚举组类型查询枚举信息列表接口
     * 
     * @param languageCode
     *            语言代码
     * @param groupCode
     *            枚举组代码
     * @return
     * @throws HsException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<EnumItem> queryEnumItemByGroup(String languageCode, String groupCode) throws HsException {
        List<EnumItem> list = (List<EnumItem>) redisUtil.get(LcsConstant.CACHE_KEY_PREFIX + languageCode + "."
                + LcsConstant.ALL_ENUM_GROUP, groupCode, List.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有[" + languageCode + "]语言[" + groupCode + "]代码的枚举列表");
            // 如果缓存中没有，就调dubbo接口取
            list = baseDataService.queryEnumItemByGroup(languageCode, groupCode);
        }
        return list;
    }

    /**
     * 根据语言代码+枚举组类型+枚举项代码查询枚举信息接口
     * 
     * @param languageCode
     *            语言代码
     * @param groupCode
     *            枚举组代码
     * @param itemCode
     *            枚举项代码
     * @return
     * @throws HsException
     */
    public EnumItem getEnumItemById(String languageCode, String groupCode, String itemCode) throws HsException {
        List<EnumItem> list = queryEnumItemByGroup(languageCode, groupCode);
        if (list != null)
        {
            for (EnumItem item : list)
            {
                if (itemCode.equals(item.getItemCode()))
                {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * 根据语言代码+枚举组类型查询,不包含excludeItemCodes中的枚举信息列表
     * 
     * @param languageCode
     *            语言代码
     * @param groupCode
     *            枚举组代码
     * @param excludeItemCodes
     *            枚举项代码列表
     * @return
     * @throws HsException
     */
    public List<EnumItem> queryEnumItemByGroupExclude(String languageCode, String groupCode,
            List<String> excludeItemCodes) throws HsException {
        List<EnumItem> filterResult = new ArrayList<EnumItem>();
        List<EnumItem> list = queryEnumItemByGroup(languageCode, groupCode);
        // 如果没有要排除的枚举项，返回整个枚举组
        if (excludeItemCodes == null || excludeItemCodes.isEmpty())
        {
            return list;
        }
        if (list != null)
        {
            for (EnumItem item : list)
            {
                // 过滤枚举组中要排除的枚举项
                if (!excludeItemCodes.contains(item.getItemCode()))
                {
                    filterResult.add(item);
                }
            }
        }
        return filterResult;
    }

    /**
     * 根据语言代码+枚举组类型查询,包含includeItemCodes中的枚举信息列表
     * 
     * @param languageCode
     *            语言代码
     * @param groupCode
     *            枚举组代码
     * @param includeItemCodes
     *            枚举项代码列表
     * @return
     * @throws HsException
     */
    public List<EnumItem> queryEnumItemByGroupInclude(String languageCode, String groupCode,
            List<String> includeItemCodes) throws HsException {
        List<EnumItem> filterResult = new ArrayList<EnumItem>();
        // 如果没有要包含的枚举项，返回空列表
        if (includeItemCodes == null || includeItemCodes.isEmpty())
        {
            return filterResult;
        }
        List<EnumItem> list = queryEnumItemByGroup(languageCode, groupCode);
        if (list != null)
        {
            for (EnumItem item : list)
            {
                // 值返回包含的枚举项列表
                if (includeItemCodes.contains(item.getItemCode()))
                {
                    filterResult.add(item);
                }
            }
        }
        return filterResult;
    }

    /**
     * 获取全部资源号与平台的路由映射关系
     * 
     * @return 返回 资源号前5位为key,平台代码为value的Map结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> getResNoRouteMap() {
        Map<String, String> map = (Map<String, String>) redisUtil.get(LcsConstant.CACHE_KEY_PREFIX
                + LcsConstant.RES_ROUTE_MAP, Map.class);
        if (CollectionUtils.isEmpty(map))
        {
            logger.debug("缓存中没有资源路由映射数据");
            // 缓存中没有，直接调远程接口获取
            map = baseDataService.getResNoRouteMap();
        }
        return map;
    }

    /**
     * 根据资源号获取资源号所属平台代码
     * 
     * @param resNo
     *            资源号
     * @return 返回平台代码
     * @throws HsException
     */
    public String getPlatByResNo(String resNo) throws HsException {
        Map<String, String> map = getResNoRouteMap();
        if (map != null)
        {
            // 获取互生号所在地区平台代码，根据资源号前5位来获取映射
            return map.get(resNo.substring(0, 5));
        }
        return null;
    }

    /**
     * 判断是否异地消费者互生号
     * 
     * @param perResNo
     *            消费者互生号
     * @return
     * @throws HsException
     */
    public boolean isAcrossPlat(String perResNo) throws HsException {
        HsAssert.notNull(perResNo, RespCode.PARAM_ERROR, "判断是否异地互生号时参数消费者互生号不能为空");
        String platNo = getPlatByResNo(perResNo);
        // 如果没有资源号与平台的映射关系，说明该号码不合法
        HsAssert.notNull(platNo, LCSErrorCode.DATA_NOT_FOUND, "根据消费者互生号未找到对应平台，param=" + perResNo);
        LocalInfo localInfo = getLocalInfo();
        return !platNo.equals(localInfo.getPlatNo());
    }

    /**
     * 获取本平台详细信息
     * 
     * @return
     * @throws HsException
     */
    @Override
    @SuppressWarnings("unchecked")
    public LocalInfo getLocalInfo() throws HsException {
        LocalInfo localInfo = (LocalInfo) redisUtil.get(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.LOCAL_INFO,
                LocalInfo.class);
        if (localInfo == null)
        {
            logger.debug("缓存中没有本地平台初始化数据");
            // 缓存中没有，直接调远程接口获取
            localInfo = baseDataService.getLocalInfo();
        }
        HsAssert.notNull(localInfo, LCSErrorCode.INIT_LOCAL_INFO_NOT_FOUND, "初始化地区平台信息未找到");
        return localInfo;
    }

    /**
     * 查询所有银联快捷支付银行列表
     * 
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PayBank> queryPayBankAll() {
        List<PayBank> list = (List<PayBank>) redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_PAYBANK,
                PayBank.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有快捷支付银行列表");
            // 缓存中没有，直接调远程接口获取
            return baseDataService.queryPayBankAll();
        }
        return list;
    }

    /**
     * 根据银行简码查询银联快捷支付银行信息
     * 
     * @param bankCode
     * @return
     */
    public PayBank queryPayBankByCode(String bankCode) {
        List<PayBank> list = queryPayBankAll();
        if (list != null)
        {
            for (PayBank payBank : list)
            {
                if (bankCode.equals(payBank.getBankCode()))
                {
                    return payBank;
                }
            }
        }
        return null;
    }

    /**
     * 查询转账银行列表
     * 
     * @return 返回全部转账银行列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Bank> queryBankAll() {
        List<Bank> list = (List<Bank>) redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_TRANSBANK,
                Bank.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有转账提现银行列表");
            // 缓存中没有，直接调远程接口获取
            return baseDataService.queryBankAll();
        }
        return list;
    }

    /**
     * 根据银行编号获取转账银行名称
     * 
     * @param bankNo
     * @return 转账银行名称
     */
    public String getBankName(String bankNo) {
        List<Bank> list = queryBankAll();
        if (list != null)
        {
            for (Bank bank : list)
            {
                if (bankNo.equals(bank.getBankNo()))
                {
                    return bank.getBankName();
                }
            }
        }
        return null;
    }

    /**
     * 查询所有货币信息
     * 
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Currency> queryCurrencyAll() {
        List<Currency> list = redisUtil
                .getList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_CURRENCY, Currency.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有货币列表");
            // 缓存中没有，直接调远程接口获取
            return baseDataService.queryCurrencyAll();
        }
        return list;
    }

    /**
     * 根据货币代码查询货币信息
     * 
     * @param currencyCode
     * @return
     */
    public Currency queryCurrencyByCode(String currencyCode) {
        List<Currency> list = queryCurrencyAll();
        if (list != null)
        {
            for (Currency currency : list)
            {
                if (currencyCode.equals(currency.getCurrencyCode()))
                {
                    return currency;
                }
            }
        }
        return null;
    }

    @Override
    public Currency getCurrencyById(String currencyNo) {
        List<Currency> list = queryCurrencyAll();
        if (list != null)
        {
            for (Currency currency : list)
            {
                if (currencyNo.equals(currency.getCurrencyNo()))
                {
                    return currency;
                }
            }
        }
        return null;
    }

    /**
     * 查询国家常用货币列表
     * 
     * @return
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#queryCountryCurrency()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<CountryCurrency> queryCountryCurrency() {
        List<CountryCurrency> list = redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.COUNTRY_CURRENCY,
                CountryCurrency.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有货币列表");
            // 缓存中没有，直接调远程接口获取
            return baseDataService.queryCountryCurrency();
        }
        return list;
    }

    /**
     * 查询全部语言列表
     * 
     * @return
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#queryLanguageAll()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Language> queryLanguageAll() {
        List<Language> list = redisUtil
                .getList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_LANGUAGE, Language.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有语言列表");
            // 缓存中没有，直接调远程接口获取
            return baseDataService.queryLanguageAll();
        }
        return list;
    }

    /**
     * 获取全部平台信息
     * 
     * @return
     * @throws HsException
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#queryPlatAll()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Plat> queryPlatAll() throws HsException {
        List<Plat> list = redisUtil.getList(LcsConstant.CACHE_KEY_PREFIX + LcsConstant.ALL_PLAT, Plat.class);
        if (list == null || list.isEmpty())
        {
            logger.debug("缓存中没有平台信息列表");
            // 缓存中没有，直接调远程接口获取
            return baseDataService.queryPlatAll();
        }
        return list;
    }

    /**
     * 获取指定国家下的行政结构， 只在LcsClient中存在本方法
     * 
     * @param countryNo
     *            国家数字代码
     * @return
     */
    public List<ProvinceTree> queryProvinceTreeOfCountry(String countryNo) {
        List<ProvinceTree> result = new ArrayList<ProvinceTree>();
        List<Province> list = queryProvinceByParent(countryNo);
        if (list != null)
        {
            for (Province province : list)
            {
                ProvinceTree obj = new ProvinceTree();
                obj.setProvince(province);
                obj.setCitys(queryCityByParent(province.getCountryNo(), province.getProvinceNo()));
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * 查询所有地区平台列表，排除总平台信息 ，只在LcsClient中存在本方法
     * 
     * @return
     * @throws HsException
     */
    public List<Plat> queryAreaPlatAll() throws HsException {
        List<Plat> list = queryPlatAll();
        for (Plat plat : list)
        {
            if (GlobalConstant.CENTER_PLAT_NO.equals(plat.getPlatNo()))
            {
                // 只有一条总平台信息，总平台代码为000，移除总平台信息剩下的就是所有地区平台列表
                list.remove(plat);
                break;
            }
        }
        return list;
    }

    /**
     * 获取已开启地区平台的国家名称及对应官网和统一登录后台列表， 只在LcsClient中存在本方法
     * 
     * @return
     * @throws HsException
     */
    public List<OpenCountry> queryOpenCountryAll() throws HsException {
        List<Plat> platList = queryAreaPlatAll();
        List<Country> countryList = findContryAll();
        List<OpenCountry> result = new ArrayList<OpenCountry>();
        for (Plat plat : platList)
        {
            for (Country country : countryList)
            {
                // 过滤有效地区平台对应的国家
                if (plat.getCountryNo().equals(country.getCountryNo()))
                {
                    result.add(new OpenCountry(country.getCountryNo(), country.getCountryName(), plat
                            .getOfficialWebBack(), plat.getUlsWebBack()));
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 查询增量国家列表，主要用于POS机签到增量更新国家列表
     * 
     * @param version
     *            当前版本号
     * @return 返回大于当前版本号的增量数据
     * @see com.gy.hsxt.lcs.api.ILCSBaseDataService#findCountryForDelta(long)
     */
    @Override
    public List<Country> findCountryForDelta(long version) {
        return baseDataService.findCountryForDelta(version);
    }
}
