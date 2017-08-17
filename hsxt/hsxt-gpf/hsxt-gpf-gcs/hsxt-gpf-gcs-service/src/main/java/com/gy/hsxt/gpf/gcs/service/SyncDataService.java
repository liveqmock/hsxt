/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.gcs.GCSErrorCode;
import com.gy.hsxt.gpf.gcs.bean.BizRoute;
import com.gy.hsxt.gpf.gcs.bean.City;
import com.gy.hsxt.gpf.gcs.bean.Country;
import com.gy.hsxt.gpf.gcs.bean.Currency;
import com.gy.hsxt.gpf.gcs.bean.EnumItem;
import com.gy.hsxt.gpf.gcs.bean.ErrorMsg;
import com.gy.hsxt.gpf.gcs.bean.Language;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.gcs.bean.PointAssignRule;
import com.gy.hsxt.gpf.gcs.bean.PromptMsg;
import com.gy.hsxt.gpf.gcs.bean.Province;
import com.gy.hsxt.gpf.gcs.bean.ResNoRoute;
import com.gy.hsxt.gpf.gcs.bean.Subsys;
import com.gy.hsxt.gpf.gcs.bean.Unit;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IBizRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.ICityService;
import com.gy.hsxt.gpf.gcs.interfaces.ICountryService;
import com.gy.hsxt.gpf.gcs.interfaces.ICurrencyService;
import com.gy.hsxt.gpf.gcs.interfaces.IEnumItemService;
import com.gy.hsxt.gpf.gcs.interfaces.IErrorMsgService;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IPlatService;
import com.gy.hsxt.gpf.gcs.interfaces.IPointAssignRuleService;
import com.gy.hsxt.gpf.gcs.interfaces.IPromptMsgService;
import com.gy.hsxt.gpf.gcs.interfaces.IProvinceService;
import com.gy.hsxt.gpf.gcs.interfaces.IResNoRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.ISubsysService;
import com.gy.hsxt.gpf.gcs.interfaces.ISyncDataService;
import com.gy.hsxt.gpf.gcs.interfaces.IUnitService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : SyncDataService.java
 * 
 *  Creation Date   : 2015-8-5
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 同步时获取增量数据
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class SyncDataService implements ISyncDataService {

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

    @Override
    public String querySyncData(String tableCode, Long version) throws HsException {
        HsAssert.notNull(tableCode, RespCode.PARAM_ERROR, "同步数据表代码不应该为空");
        HsAssert.notNull(version, RespCode.PARAM_ERROR, "版本号不应该为空");
        Version versionObj = versionService.queryVersion(tableCode);
        if (versionObj == null)
        {
            throw new HsException(GCSErrorCode.DATA_NOT_FOUND, "表" + tableCode + "内容未找到");
        }
        else
        {
            // 如果总平台的版本号大于子平台，获取数据更新
            if (versionObj.getVersion() > version)
            {

                if (Constant.GL_ERROR_MSG.equals(tableCode))
                {
                    List<ErrorMsg> errorMsgList = errorMsgService.queryErrorMsg4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", errorMsgList);
                    return result.toJSONString();

                }
                else if (Constant.GL_PROMPT_MSG.equals(tableCode))
                {
                    List<PromptMsg> promptMsgList = promptMsgService.queryPromptMsg4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", promptMsgList);
                    return result.toJSONString();

                }
                else if (Constant.GL_BIZ_ROUTE.equals(tableCode))
                {
                    List<BizRoute> bizRouteList = bizRouteService.queryBizRoute4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", bizRouteList);
                    return result.toJSONString();

                }
                else if (Constant.GL_RESNO_ROUTE.equals(tableCode))
                {
                    List<ResNoRoute> resNoRouteList = resNoRouteService.queryResNoRoute4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", resNoRouteList);
                    return result.toJSONString();

                }
                else if (Constant.GL_ENUM_ITEM.equals(tableCode))
                {
                    List<EnumItem> enumItemList = enumItemService.queryEnumItem4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", enumItemList);
                    return result.toJSONString();

                }
                else if (Constant.GL_LANGUAGE.equals(tableCode))
                {
                    List<Language> languageList = languageService.queryLanguage4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", languageList);
                    return result.toJSONString();

                }
                else if (Constant.GL_UNIT.equals(tableCode))
                {
                    List<Unit> unitList = unitService.queryUnit4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", unitList);
                    return result.toJSONString();

                }
                else if (Constant.GL_SUBSYS.equals(tableCode))
                {
                    List<Subsys> subsysList = subsysService.querySubsysSyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", subsysList);
                    return result.toJSONString();

                }
                else if (Constant.GL_PLAT.equals(tableCode))
                {
                    List<Plat> platList = platService.queryPlatSyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", platList);
                    return result.toJSONString();

                }
                else if (Constant.GL_COUNTRY.equals(tableCode))
                {
                    List<Country> countryList = countryService.queryCountrySyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", countryList);
                    return result.toJSONString();

                }
                else if (Constant.GL_PROVINCE.equals(tableCode))
                {
                    List<Province> provinceList = provinceService.queryProvinceSyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", provinceList);
                    return result.toJSONString();

                }
                else if (Constant.GL_CITY.equals(tableCode))
                {
                    List<City> cityList = cityService.queryCitySyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", cityList);
                    return result.toJSONString();

                }
                else if (Constant.GL_CURRENCY.equals(tableCode))
                {
                    List<Currency> currencyList = currencyService.queryCurrencySyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", currencyList);
                    return result.toJSONString();

                }
                else if (Constant.GL_POINT_ASSIGN_RULE.equals(tableCode))
                {
                    List<PointAssignRule> assignRuleList = pointAssignRuleService.queryAssignRule4SyncData(version);
                    JSONObject result = new JSONObject();
                    result.put("version", versionObj.getVersion());
                    result.put("dataList", assignRuleList);
                    return result.toJSONString();
                }
            }
        }

        return null;
    }

}
