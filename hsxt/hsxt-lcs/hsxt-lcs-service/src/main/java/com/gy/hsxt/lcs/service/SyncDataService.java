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

package com.gy.hsxt.lcs.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.BizRoute;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.bean.EnumItem;
import com.gy.hsxt.lcs.bean.ErrorMsg;
import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.bean.PointAssignRule;
import com.gy.hsxt.lcs.bean.PromptMsg;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.bean.ResNoRoute;
import com.gy.hsxt.lcs.bean.Subsys;
import com.gy.hsxt.lcs.bean.Unit;
import com.gy.hsxt.lcs.cache.CacheLoader;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IBizRouteService;
import com.gy.hsxt.lcs.interfaces.ICityService;
import com.gy.hsxt.lcs.interfaces.ICountryService;
import com.gy.hsxt.lcs.interfaces.ICurrencyService;
import com.gy.hsxt.lcs.interfaces.IEnumItemService;
import com.gy.hsxt.lcs.interfaces.IErrorMsgService;
import com.gy.hsxt.lcs.interfaces.ILanguageService;
import com.gy.hsxt.lcs.interfaces.IPlatService;
import com.gy.hsxt.lcs.interfaces.IPointAssignRuleService;
import com.gy.hsxt.lcs.interfaces.IPromptMsgService;
import com.gy.hsxt.lcs.interfaces.IProvinceService;
import com.gy.hsxt.lcs.interfaces.IResNoRouteService;
import com.gy.hsxt.lcs.interfaces.ISubsysService;
import com.gy.hsxt.lcs.interfaces.ISyncDataService;
import com.gy.hsxt.lcs.interfaces.IUnitService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.uc.as.api.util.CommonUtil;
import com.gy.hsxt.uc.as.bean.common.AsPosBaseInfo;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

/** 
 * @Description: TODO
 *
 * @Package: com.gy.hsxt.lcs.service  
 * @ClassName: SyncDataService 
 * @author: yangjianguo 
 * @date: 2016-5-9 下午6:25:53 
 * @version V1.0  
 */
@Service("syncDataService")
public class SyncDataService implements ISyncDataService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IVersionService versionService;

    @Autowired
    IBizRouteService bizRouteService;

    @Autowired
    ICityService cityService;

    @Autowired
    ICountryService countryService;

    @Autowired
    ICurrencyService currencyService;

    @Autowired
    IEnumItemService enumItemService;

    @Autowired
    IErrorMsgService errorMsgService;

    @Autowired
    ILanguageService languageService;

    @Autowired
    IPlatService platService;

    @Autowired
    IPromptMsgService promptMsgService;

    @Autowired
    IProvinceService provinceService;

    @Autowired
    IResNoRouteService resNoRouteService;

    @Autowired
    ISubsysService subsysService;

    @Autowired
    IUnitService unitService;

    @Autowired
    IPointAssignRuleService pointAssignRuleService;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    CacheLoader cacheLoader;
    
    @Autowired
    IUFRegionPacketService ufRegionPacketService;
    
    /**  
     * @param tableCode
     * @param version 
     * @see com.gy.hsxt.lcs.interfaces.ISyncDataService#syncData(java.lang.String, java.lang.Long) 
     */
    @Override
    public void syncData(String tableCode, Long version) {
        logger.debug("开始更新表" + tableCode + ",版本号" + version);
        try
        {
            String centerPlatNo = platService.getLocalInfo().getCenterPlatNo();
            RegionPacketHeader packetHeader = RegionPacketHeader.build().setDestPlatformId(centerPlatNo)
                    .setDestBizCode(AcrossPlatBizCode.TO_CENTER_GET_NEW_DATA.name());
            JSONObject param = new JSONObject();
            param.put("tableCode", tableCode);
            param.put("version", version);
            // 组装报文体
            RegionPacketBody packetBody = RegionPacketBody.build(param.toString());
            // 发送跨地区平台报文
            String data = (String) ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
            if (null == data || data.isEmpty())
            {
                logger.debug("更新表" + tableCode + "时没有增量数据");
            }
            else
            {
                JSONObject jsonObject = (JSONObject) JSON.parseObject(data);
                Long returnVersion = jsonObject.getLongValue("version");
                String dataList = jsonObject.getString("dataList");
                
                if (Constant.GL_ERROR_MSG.equals(tableCode))
                {
                    List<ErrorMsg> list = JSON.parseArray(dataList, ErrorMsg.class);
                    // 更新提示信息后需要更新缓存
                    for (ErrorMsg errorMsg : list)
                    {
                        cacheLoader.loadErrorMsg(errorMsg.getLanguageCode(), "" + errorMsg.getErrorCode(), errorMsg);
                    }
                    errorMsgService.addOrUpdateErrorMsg(list, returnVersion);
                }
                else if (Constant.GL_PROMPT_MSG.equals(tableCode))
                {
                    List<PromptMsg> list = JSON.parseArray(dataList, PromptMsg.class);
                    promptMsgService.addOrUpdatePromptMsg(list, returnVersion);
                    // 更新提示信息后需要更新缓存
                    for (PromptMsg promptMsg : list)
                    {
                        cacheLoader.loadPromptMsg(promptMsg.getLanguageCode(), promptMsg.getPromptCode(), promptMsg);
                    }
                }
                else if (Constant.GL_BIZ_ROUTE.equals(tableCode))
                {
                    List<BizRoute> list = JSON.parseArray(dataList, BizRoute.class);
                    bizRouteService.addOrUpdateBizRoute(list, returnVersion);
                }
                else if (Constant.GL_RESNO_ROUTE.equals(tableCode))
                {
                    //添加增量资源路由关系
                    List<ResNoRoute> updateList = JSON.parseArray(dataList, ResNoRoute.class);
                    resNoRouteService.addOrUpdateResNoRoute(updateList, returnVersion);
                    
                    //资源路由关系发生变化后需重新加载缓存
                    Map<String, String> map = new HashMap<String, String>();
                    List<ResNoRoute> result = resNoRouteService.getResNoRouteList();
                    for (ResNoRoute resNoRoute : result)
                    {
                        map.put(resNoRoute.getResNo(), resNoRoute.getPlatNo());
                    }
                    cacheLoader.loadResNoRouteMap(map);
                }
                else if (Constant.GL_ENUM_ITEM.equals(tableCode))
                {
                    List<EnumItem> list = JSON.parseArray(dataList, EnumItem.class);
                    enumItemService.addOrUpdateEnumItem(list, returnVersion);

                    // 更新缓存中枚举类型数据,只更新受影响的枚举组
                    Set<String> cacheKey = new HashSet<String>();
                    for (EnumItem item : list)
                    {
                        cacheKey.add(item.getLanguageCode() + ":" + item.getGroupCode());
                    }
                    for (String key : cacheKey)
                    {
                        String[] str = key.split(":");
                        List<EnumItem> groupItems = enumItemService.queryEnumItemByGroup(str[0], str[1]);
                        cacheLoader.loadEnumItemByGroup(str[0], str[1], groupItems);
                    }
                }
                else if (Constant.GL_LANGUAGE.equals(tableCode))
                {
                    List<Language> list = JSON.parseArray(dataList, Language.class);
                    languageService.addOrUpdateLanguage(list, returnVersion);
                    // 更新语言信息后重新加载缓存
                    cacheLoader.loadLanguageAll(languageService.queryLanguageAll());
                }
                else if (Constant.GL_UNIT.equals(tableCode))
                {
                    List<Unit> list = JSON.parseArray(dataList, Unit.class);
                    unitService.addOrUpdateUnit(list, returnVersion);

                    // 更新缓存中枚举类型数据,只更新受影响的枚举组
                    Set<String> cacheKey = new HashSet<String>();
                    for (Unit item : list)
                    {
                        cacheKey.add(item.getLanguageCode());
                    }
                    for (String languageCode : cacheKey)
                    {
                        List<Unit> units = unitService.queryUnitByLanguage(languageCode);
                        cacheLoader.loadUnitByLanguage(languageCode, units);
                    }
                }
                else if (Constant.GL_SUBSYS.equals(tableCode))
                {
                    List<Subsys> list = JSON.parseArray(dataList, Subsys.class);
                    subsysService.addOrUpdateSubsys(list, returnVersion);

                }
                else if (Constant.GL_PLAT.equals(tableCode))
                {
                    List<Plat> list = JSON.parseArray(dataList, Plat.class);
                    platService.addOrUpdatePlat(list, returnVersion);
                    // 平台信息同步时刷新一下缓存中的平台信息列表
                    cacheLoader.loadPlatAll(platService.queryPlatAll());
                    // 平台信息同步时刷新一下缓存中的当前平台信息，有可能被更新
                    cacheLoader.loadLocalInfo(platService.getLocalInfo());
                }
                else if (Constant.GL_COUNTRY.equals(tableCode))
                {
                    List<Country> list = JSON.parseArray(dataList, Country.class);
                    countryService.addOrUpdateCountry(list, returnVersion);
                    // 更新国家后需要重新加载国家缓存
                    cacheLoader.loadContryAll(countryService.findContryAll());
                }
                else if (Constant.GL_PROVINCE.equals(tableCode))
                {
                    List<Province> list = JSON.parseArray(dataList, Province.class);
                    provinceService.addOrUpdateProvince(list, returnVersion);

                    // 省信息同步更新后，需要刷新受影响的国家下的省列表
                    Set<String> countrySet = new HashSet<String>();
                    for (Province province : list)
                    {
                        countrySet.add(province.getCountryNo());
                    }
                    for (String countryNo : countrySet)
                    {
                        cacheLoader.loadProvinceForParent(countryNo, provinceService.findProvinceByParent(countryNo));
                    }

                }
                else if (Constant.GL_CITY.equals(tableCode))
                {
                    List<City> list = JSON.parseArray(dataList, City.class);
                    cityService.addOrUpdateCity(list, returnVersion);
                    // 城市信息同步更新后，需要刷新受影响的省份下的城市列表
                    Set<String> provinceSet = new HashSet<String>();
                    for (City city : list)
                    {
                        // 因为省信息是国家代码+省代码复合主键，所以需要拼接国家代码与省代码才能标识唯一省
                        provinceSet.add(city.getCountryNo() + "_" + city.getProvinceNo());
                    }
                    for (String provinceKey : provinceSet)
                    {
                        String[] str = provinceKey.split("_");
                        cacheLoader.loadCityForParent(str[0], str[1], cityService.queryCityByParent(str[0], str[1]));
                    }
                }
                else if (Constant.GL_CURRENCY.equals(tableCode))
                {
                    // 在更新货币信息前，先获取平台信息，其中包含了平台当前货币转换比率
                    LocalInfo localInfo = platService.getLocalInfo();
                    List<Currency> list = JSON.parseArray(dataList, Currency.class);
                    // 新增或者更新货币信息
                    currencyService.addOrUpdateCurrency(list, returnVersion);
                    // 刷新缓存中货币列表
                    cacheLoader.loadCurrencyAll(currencyService.queryCurrencyAll());
                    for (Currency currency : list)
                    {
                        // 如果平台本币信息被修改，并且是货币转换比例发生改变，调用用户中心接口更新
                        if (currency.getCurrencyNo().equals(localInfo.getCurrencyNo())
                                && !currency.getExchangeRate().equals(localInfo.getExchangeRate()))
                        {
                            AsPosBaseInfo posBaseInfo = new AsPosBaseInfo();
                            posBaseInfo.setHsbExchangeCurrencyRate(currency.getExchangeRate());
                            // 调用户中心接口更新货币转换比例
                            commonUtil.updatePosBaseInfo(posBaseInfo);
                            // 更新货币信息后重新取一次更新后的内容，刷新公共缓存中LocalInfo
                            localInfo = platService.getLocalInfo();
                            cacheLoader.loadLocalInfo(localInfo);
                            break;
                        }
                    }
                }
                else if (Constant.GL_POINT_ASSIGN_RULE.equals(tableCode))
                {
                    List<PointAssignRule> list = JSON.parseArray(dataList, PointAssignRule.class);
                    pointAssignRuleService.addOrUpdateAssignRule(list, returnVersion);
                }
            }
        }
        catch (HsException e)
        {
            logger.error("更新表" + tableCode + "异常:" + e);
        }
        logger.debug("完成更新表" + tableCode + ",版本号" + version);
    }

}
