/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.api;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.CountryCurrency;
import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.bean.EnumItem;
import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.bean.Unit;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-api
 * 
 *  Package Name    : com.gy.hsxt.lcs.api
 * 
 *  File Name       : ILCSBaseDataService.java
 * 
 *  Creation Date   : 2015-8-5
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         :  公共参数查询服务
 * 
 * 
 *  History         :
 * 
 * </PRE>
 ***************************************************************************/
public interface ILCSBaseDataService {
    /**
     * 查询国家列表
     * 
     * @return
     * @exception HsException
     */
    List<Country> findContryAll() throws HsException;

    /**
     * 查询增量国家信息列表
     * 
     * @param version
     *            当前版本号
     * @return 返回大于当前版本号的增量数据
     * @throws HsException
     */
    List<Country> findCountryForDelta(long version);

    /**
     * 根据国家代码获取国家信息
     * 
     * @param countryNo
     *            国家代码
     * @return
     * @throws HsException
     */
    Country getContryById(String countryNo) throws HsException;

    /**
     * 查询国家查询下级省列表
     * 
     * @param countryNo
     *            国家代码
     * @return
     * @throws HsException
     */
    List<Province> queryProvinceByParent(String countryNo) throws HsException;

    /**
     * 根据国家代码 +省代码查询省信息
     * 
     * @param countryNo
     * @param provinceNo
     *            省代码
     * @return
     * @throws HsException
     */
    Province getProvinceById(String countryNo, String provinceNo) throws HsException;

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
    List<City> queryCityByParent(String countryNo, String provinceNo) throws HsException;

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
    City getCityById(String countryNo, String privinceNo, String cityNo) throws HsException;

    /**
     * 按语言代码查询计量单位列表
     * 
     * @param languageCode
     *            语言代码
     * @return
     * @throws HsException
     */
    List<Unit> queryUnitByLanguage(String languageCode) throws HsException;

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
    Unit getUnitById(String languageCode, String unitCode) throws HsException;

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
    String getPromptMsg(String languageCode, String promptCode) throws HsException;

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
    String getErrorMsg(String languageCode, int errorCode) throws HsException;

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
    List<EnumItem> queryEnumItemByGroup(String languageCode, String groupCode) throws HsException;

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
    EnumItem getEnumItemById(String languageCode, String groupCode, String itemCode) throws HsException;

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
    List<EnumItem> queryEnumItemByGroupExclude(String languageCode, String groupCode, List<String> excludeItemCodes)
            throws HsException;

    /**
     * 根据语言代码+枚举组类型查询,包含excludeItemCodes中的枚举信息列表
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
    List<EnumItem> queryEnumItemByGroupInclude(String languageCode, String groupCode, List<String> includeItemCodes)
            throws HsException;

    /**
     * 获取全部资源号与平台的路由映射关系
     * 
     * @return 返回 资源号前5位为key,平台代码为value的Map结果
     */
    Map<String, String> getResNoRouteMap();

    /**
     * 根据资源号获取资源号所属平台代码
     * 
     * @param resNo
     *            资源号
     * @return 返回平台代码
     * @throws HsException
     */
    String getPlatByResNo(String resNo) throws HsException;

    /**
     * 判断是否异地消费者互生号
     * 
     * @param perResNo
     *            消费者互生号
     * @return
     * @throws HsException
     */
    boolean isAcrossPlat(String perResNo) throws HsException;

    /**
     * 获取本平台详细信息
     * 
     * @return
     * @throws HsException
     */
    LocalInfo getLocalInfo() throws HsException;

    /**
     * 查询所有银联快捷支付银行列表
     * 
     * @return
     */
    List<PayBank> queryPayBankAll();

    /**
     * 根据银行简码查询银联快捷支付银行信息
     * 
     * @param bankCode
     * @return
     */
    PayBank queryPayBankByCode(String bankCode);

    /**
     * 查询转账银行列表
     * 
     * @return 返回全部转账银行列表
     */
    List<Bank> queryBankAll();

    /**
     * 根据银行编号获取转账银行名称
     * 
     * @param bankNo
     * @return 转账银行名称
     */
    String getBankName(String bankNo);

    /**
     * 查询所有货币信息
     * 
     * @return
     */
    public List<Currency> queryCurrencyAll();

    /**
     * 根据货币代码查询货币信息
     * 
     * @param currencyCode
     * @return
     */
    public Currency queryCurrencyByCode(String currencyCode);

    /**
     * 根据货币编号查询货币信息
     * 
     * @param currencyNo
     * @return
     */
    public Currency getCurrencyById(String currencyNo);

    /**
     * 查询当前地区平台常用六种货币
     * 
     * @return
     */
    public List<CountryCurrency> queryCountryCurrency();

    /**
     * 查询所有语言信息
     * 
     * @return
     */
    public List<Language> queryLanguageAll();

    /**
     * 查询所有有效平台信息
     * 
     * @return
     * @throws HsException
     */
    List<Plat> queryPlatAll() throws HsException;
}
