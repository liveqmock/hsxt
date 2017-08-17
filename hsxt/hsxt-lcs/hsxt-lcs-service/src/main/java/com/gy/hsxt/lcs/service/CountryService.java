package com.gy.hsxt.lcs.service;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : CountryService.java
 * 
 *  Creation Date   : 2015-7-11
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 国家代码CountryService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.ICountryService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.CountryMapper;

@Service("countryService")
public class CountryService implements ICountryService {

    @Resource(name = "countryMapper")
    private CountryMapper countryMapper;

    @Autowired
    private IVersionService veresionService;

    /**
     * 读取某条记录
     * 
     * @param countryNo
     *            国家代码 必须 非空
     * @return 返回Country实体类
     */
    public Country getCountry(String countryNo) {
        return countryMapper.getCountry(countryNo);
    }

    /**
     * 获取全部字段的下拉菜单列表
     * 
     * @return 返回List<Country>列表，数据为空，返回空List<Country>
     */
    public List<Country> findContryAll() {
        return countryMapper.findContryAll();
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
    public int addOrUpdateCountry(List<Country> list, Long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_COUNTRY, version, true));
        List<Country> cityListAdd = new ArrayList<Country>();
        List<Country> cityListUpdate = new ArrayList<Country>();

        for (Country obj : list)
        {
            if ("1".equals(countryMapper.existCountry(obj.getCountryNo())))
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
            countryMapper.batchUpdate(cityListUpdate);
        }

        if (cityListAdd.size() > 0)
        {
            countryMapper.batchInsert(cityListAdd);
        }
        return 1;
    }

    /**
     * 查询增量国家列表
     * 
     * @param version
     *            当前版本号
     * @return
     * @see com.gy.hsxt.lcs.interfaces.ICountryService#findCountryForDelta(long)
     */
    @Override
    public List<Country> findCountryForDelta(long version) {
        return countryMapper.findCountryForDelta(version);
    }
}
