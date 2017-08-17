package com.gy.hsxt.lcs.service;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : PlatService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 平台代码PlatService实现类
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

import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IPlatService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.PlatMapper;

@Service("platService")
public class PlatService implements IPlatService {

    @Resource(name = "platMapper")
    private PlatMapper platMapper;

    @Autowired
    private IVersionService veresionService;

    /**
     * 读取某条记录
     * 
     * @param platNo
     *            国家代码 必须 非空
     * @return 返回Plat实体类
     */
    public Plat getPlat(String platNo) {
        return platMapper.getPlat(platNo);
    }

    /**
     * 根据国家代码获取平台信息
     * 
     * @param countryNo
     *            国家代码 必须 非空
     * @return 返回Plat实体类
     */
    public Plat getPlatByCountryNo(String countryNo) {
        return platMapper.getPlatByCountryNo(countryNo);
    }

    /**
     * 判断平台代码是否存在
     * 
     * @param platNo
     *            国家代码 必须 非空
     * @return 返回String,1已经存在，0不存在
     */
    public boolean isExistIP(String ip) {
        boolean isExistIP = "1".equals(platMapper.existPlatIP(ip));
        return isExistIP;
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
    public int addOrUpdatePlat(List<Plat> list, Long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_PLAT, version, true));
        List<Plat> cityListAdd = new ArrayList<Plat>();
        List<Plat> cityListUpdate = new ArrayList<Plat>();

        for (Plat obj : list)
        {
            if ("1".equals(platMapper.existPlat(obj.getPlatNo())))
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
            platMapper.batchUpdate(cityListUpdate);
        }

        if (cityListAdd.size() > 0)
        {
            platMapper.batchInsert(cityListAdd);
        }
        return 1;
    }

    @Override
    public Plat getLocalPlat() {
        return platMapper.getLocalPlat();
    }

    @Override
    public LocalInfo getLocalInfo() {
        return platMapper.getLocalInfo();
    }

    /**
     * 获取所有有效平台信息
     * 
     * @return
     * @see com.gy.hsxt.lcs.interfaces.IPlatService#queryPlatAll()
     */
    @Override
    public List<Plat> queryPlatAll() {
        return platMapper.queryPlatAll();
    }
}
