package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.Subsys;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.ISubsysService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.SubsysMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : SubsysService.java
 * 
 *  Creation Date   : 2015-7-15
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 系统代码SubsysService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@Service("subsysService")
public class SubsysService implements ISubsysService {

    @Resource(name = "subsysMapper")
    private SubsysMapper subsysMapper;

    @Autowired
    private IVersionService veresionService;

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
    public int addOrUpdateSubsys(List<Subsys> list, Long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_SUBSYS, version, true));
        List<Subsys> cityListAdd = new ArrayList<Subsys>();
        List<Subsys> cityListUpdate = new ArrayList<Subsys>();

        for (Subsys obj : list)
        {
            if ("1".equals(subsysMapper.existSubsys(obj.getSubsysCode())))
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
            subsysMapper.batchUpdate(cityListUpdate);
        }

        if (cityListAdd.size() > 0)
        {
            subsysMapper.batchInsert(cityListAdd);
        }
        return 1;
    }

}
