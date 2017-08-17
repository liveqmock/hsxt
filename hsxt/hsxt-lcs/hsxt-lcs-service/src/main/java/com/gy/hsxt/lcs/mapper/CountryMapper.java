package com.gy.hsxt.lcs.mapper;

import java.util.List;

import com.gy.hsxt.lcs.bean.Country;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
 * 
 *  File Name       : CountryMapper.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 国家代码CountryMapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface CountryMapper {

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
     * 判断是否已存在相同
     * 
     * @param countryNo
     *            国家代码 必须 非空
     * @return 返回String,1已经存在，0不存在
     */
    public String existCountry(String countryNo);

    /**
     * 批量更新数据
     * 
     * @param countryListUpdate
     *            总平台城市变更的数据列表 必须 非空， 无返回值
     */
    public void batchUpdate(List<Country> countryListUpdate);

    /**
     * 批量插入数据
     * 
     * @param countryListAdd
     *            总平台城市变更的数据列表 必须 非空， 无返回值
     */
    public void batchInsert(List<Country> countryListAdd);

    /**
     * 查询增量国家列表
     * 
     * @param version
     *            当前版本号
     * @return
     */
    public List<Country> findCountryForDelta(long version);
}
