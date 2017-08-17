package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.Country;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : ICountryService.java
 * 
 *  Creation Date   : 2015-7-11
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 国家代码ICountryService接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

public interface ICountryService {

    /**
     * 读取某条记录
     * 
     * @param countryNo
     *            国家代码 必须 非空
     * @return 返回Country实体类
     */
    public Country getCountry(String countryNo);

    /**
     * 获取全部字段的下拉菜单列表
     * 
     * @return 返回List<Country>列表，数据为空，返回空List<Country>
     */
    public List<Country> findContryAll();

    /**
     * 批量更新数据
     * 
     * @param list
     *            获取变的版本号的数据表示 必须
     * @param version
     *            版本号
     * @return 返回int 1成功，其他失败
     */
    public int addOrUpdateCountry(List<Country> list, Long version);

    /**
     * 查询增量国家列表
     * 
     * @param version
     *            当前版本
     * @return
     */
    public List<Country> findCountryForDelta(long version);
}
