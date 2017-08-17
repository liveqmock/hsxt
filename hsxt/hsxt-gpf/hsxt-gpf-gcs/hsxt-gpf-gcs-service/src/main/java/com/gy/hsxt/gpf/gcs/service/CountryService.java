package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.Country;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ICountryService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.CountryMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : CountryService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 国家代码CountryService实现类
 * 
 * 
 *  History         :
 * 
 * </PRE>
 ***************************************************************************/
@Service("countryService")
@Transactional(readOnly = true)
public class CountryService implements ICountryService {

    @Resource(name = "countryMapper")
    private CountryMapper countryMapper;

    @Autowired
    private IVersionService veresionService;

    /**
     * 插入记录
     * 
     * @param country
     *            实体类 必须，非null
     * @return 返int类型 1成功，其他失败
     */
    @Transactional
    public int insert(Country country) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_COUNTRY);
        country.setVersion(version);
        return countryMapper.insert(country);
    }

    /**
     * 读取某条记录
     * 
     * @param countryNo
     *            国家代码 必须，非null
     * @return 返回实体类Country
     */
    public Country getCountry(String countryNo) {
        return countryMapper.getCountry(countryNo);
    }

    /**
     * 获取数据分页列表
     * 
     * @param Country实体类
     *            必须，非null
     * @return 返回List<Country>,数据为空，返回空List<Country>
     */
    public List<Country> getCountryListPage(Country country) {
        return countryMapper.getCountryListPage(country);
    }

    /**
     * 获取全部有效国家
     * 
     * @return 返回List<Country>,数据为空，返回空List<Country>
     */
    public List<Country> getCountryAll() {
        return countryMapper.getCountryAll();
    }

    /**
     * 获取查询国家全部字段列表
     * 
     * @return 返回List<Country>,数据为空，返回空List<Country>
     */
    public List<Country> findContryAll() {
        return countryMapper.findContryAll();
    }

    /**
     * 更新某条记录
     * 
     * @param Country实体类
     *            必须，非null
     * @return 返回int类型 1成功，其他失败
     */
    @Transactional
    public int update(Country country) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_COUNTRY);
        country.setVersion(version);
        return countryMapper.update(country);
    }

    /**
     * 删除某条记录
     * 
     * @param country
     *            实体类 必须，非null
     * @return 返int类型 1成功，其他失败
     */
    @Transactional
    public int delete(String countryNo) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_COUNTRY);
        Country country = new Country();
        country.setCountryNo(countryNo);
        country.setVersion(version);
        country.setDelFlag(1);
        return countryMapper.delete(country);
    }

    /**
     * 判断是否已存在相同
     * 
     * @param countryNo
     *            国家代码 必须，非null
     * @return 返回String 大于等于1存在，0不存在
     */
    public boolean existCountry(String countryNo) {
        boolean isExist = "1".equals(countryMapper.existCountry(countryNo));
        return isExist;
    }

    /**
     * 读取大于某个版本号的数据
     * 
     * @param version
     *            版本号 必须，非null
     * @return 返回List<Country>,数据为空，返回空List<Country>
     */
    public List<Country> queryCountrySyncData(Long version) {
        return countryMapper.queryCountrySyncData(version);
    }
}
