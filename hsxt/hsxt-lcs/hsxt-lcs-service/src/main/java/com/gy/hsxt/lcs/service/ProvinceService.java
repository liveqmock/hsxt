package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IProvinceService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.ProvinceMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : ProvinceService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 省份代码ProvinceService实现类 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@Service("provinceService")
public class ProvinceService implements IProvinceService {

    @Resource(name = "provinceMapper")
    private ProvinceMapper provinceMapper;

    @Autowired
    private IVersionService veresionService;

    /**
     * 读取某条记录
     * 
     * @param countryNo
     *            国家代码 必须 非空
     * @param provinceNo
     *            省份代码 必须 非空
     * @return 返回Province实体类
     */
    public Province getProvince(String countryNo, String provinceNo) {
        return provinceMapper.getProvince(countryNo, provinceNo);
    }

    /**
     * 根据国家获取下级有效省份列表
     * 
     * @param countryNo
     *            国家代码 必须 非空
     * @return 返回List<Province>列表
     */
    public List<Province> findProvinceByParent(String countryNo) {
        return provinceMapper.queryProvinceByParent(countryNo);
    }

    /**
     * 批量更新数据
     * 
     * @param list
     *            获取变的版本号的数据表示 必须
     * @param version
     *            版本号
     * @return 返回int 1成功，其他失败·
     */
    @Transactional
    public int addOrUpdateProvince(List<Province> list, Long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_PROVINCE, version, true));
        List<Province> cityListAdd = new ArrayList<Province>();
        List<Province> cityListUpdate = new ArrayList<Province>();

        for (Province obj : list)
        {
            if ("1".equals(provinceMapper.existProvince(obj.getCountryNo(), obj.getProvinceNo())))
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
            provinceMapper.batchUpdate(cityListUpdate);
        }

        if (cityListAdd.size() > 0)
        {
            provinceMapper.batchInsert(cityListAdd);
        }
        return 1;
    }
}
