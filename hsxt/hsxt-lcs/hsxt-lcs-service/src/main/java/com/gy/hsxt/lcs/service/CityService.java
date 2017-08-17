package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.ICityService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.CityMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : CityService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 城市代码CityService接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("cityService")
public class CityService implements ICityService {

    @Resource(name = "cityMapper")
    private CityMapper cityMapper;

    @Autowired
    private IVersionService veresionService;

    /**
     * 读取某条记录
     * 
     * @param countryNo
     *            国家代码，非null
     * @param provinceNo
     *            省代码，非null
     * @param cityNo
     *            城市代码 必须，非null
     * @return 返回实体类City
     */
    public City getCity(String countryNo, String provinceNo, String cityNo) {
        return cityMapper.getCity(countryNo, provinceNo, cityNo);
    }

    /**
     * 读取某个省份的所以城市记录
     * 
     * @param countryNo
     *            国家代码，非null
     * @param provinceNo
     *            省份代码 必须，非null
     * 
     * @return 返回List<City>,数据为空，返回空List<City>
     */
    public List<City> queryCityByParent(String countryNo, String provinceNo) {
        return cityMapper.queryCityByParent(countryNo, provinceNo);
    }

    /**
     * 批量更新数据
     * 
     * @param list
     *            获取变的版本号的数据表示 必须
     * @param version
     *            版本号
     * @return 返回int 1成功，其他失败
     */
    @Transactional
    public int addOrUpdateCity(List<City> list, Long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_CITY, version, true));
        List<City> cityListAdd = new ArrayList<City>();
        List<City> cityListUpdate = new ArrayList<City>();

        for (City obj : list)
        {
            if ("1".equals(cityMapper.existCity(obj.getCountryNo(), obj.getProvinceNo(), obj.getCityNo())))
            {
                cityListUpdate.add(obj);
            }
            else
            {
                cityListAdd.add(obj);
            }
        }

        if (cityListUpdate.size() > 0)
        {
            cityMapper.batchUpdate(cityListUpdate);
        }

        if (cityListAdd.size() > 0)
        {
            cityMapper.batchInsert(cityListAdd);
        }
        return 1;
    }
}
